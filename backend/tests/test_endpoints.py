import os
import pytest
from fastapi.testclient import TestClient

os.environ["ENV"] = "testing"

from app.main import app

client = TestClient(app)
mock_token = "mock_token_testuser123_test@auraai.com"

# 1. Test Contacts CRUD
def test_contacts_crud():
    # Create contact
    payload = {
        "name": "Trusted Buddy",
        "phone": "+1234567890",
        "email": "buddy@example.com",
        "relationship_label": "Friend",
        "is_favorite": False,
        "is_emergency": True
    }
    response = client.post(
        "/api/v1/contacts",
        json=payload,
        headers={"Authorization": f"Bearer {mock_token}"}
    )
    assert response.status_code == 200
    data = response.json()
    contact_id = data["contact_id"]
    assert data["name"] == "Trusted Buddy"
    assert data["is_emergency"] is True

    # Get contacts
    response_get = client.get(
        "/api/v1/contacts",
        headers={"Authorization": f"Bearer {mock_token}"}
    )
    assert response_get.status_code == 200
    assert len(response_get.json()) >= 1

    # Toggle favorite
    response_fav = client.post(
        f"/api/v1/contacts/{contact_id}/favorite",
        headers={"Authorization": f"Bearer {mock_token}"}
    )
    assert response_fav.status_code == 200
    assert response_fav.json()["is_favorite"] is True

    # Delete contact
    response_del = client.delete(
        f"/api/v1/contacts/{contact_id}",
        headers={"Authorization": f"Bearer {mock_token}"}
    )
    assert response_del.status_code == 200

# 2. Test Game Center
def test_game_center():
    # Submit score
    response = client.post(
        "/api/v1/games/scores",
        json={"game_id": "bubblepop", "score": 25},
        headers={"Authorization": f"Bearer {mock_token}"}
    )
    assert response.status_code == 200
    data = response.json()
    assert data["coins_earned"] == 50
    assert "score_id" in data

    # Retrieve achievements
    response_ach = client.get(
        "/api/v1/games/achievements",
        headers={"Authorization": f"Bearer {mock_token}"}
    )
    assert response_ach.status_code == 200
    assert isinstance(response_ach.json(), list)

# 3. Test Journal
def test_journal_endpoints():
    # Create journal entry
    payload = {
        "title": "A Good Day",
        "content": "I spent time in nature and listened to soft rainfall today. I feel peaceful."
    }
    response = client.post(
        "/api/v1/journal",
        json=payload,
        headers={"Authorization": f"Bearer {mock_token}"}
    )
    assert response.status_code == 200
    data = response.json()
    assert "reflection" in data
    assert "detected_emotion" in data

    # Generate weekly report
    response_report = client.post(
        "/api/v1/journal/weekly/generate",
        headers={"Authorization": f"Bearer {mock_token}"}
    )
    assert response_report.status_code == 200
    report_data = response_report.json()
    assert "dominant_mood" in report_data
    assert "self_care_plan" in report_data

# 4. Test Recommendations
def test_recommendations():
    response = client.get(
        "/api/v1/recommend",
        headers={"Authorization": f"Bearer {mock_token}"}
    )
    assert response.status_code == 200
    cards = response.json()
    assert len(cards) >= 1
    assert "activity_type" in cards[0]

# 5. Test Notifications
def test_notifications():
    # Save FCM token
    response_token = client.post(
        "/api/v1/notifications/token",
        json={"fcm_token": "fcm_test_token_123"},
        headers={"Authorization": f"Bearer {mock_token}"}
    )
    assert response_token.status_code == 200
    assert response_token.json()["status"] == "success"

    # Trigger test push simulation
    response_test = client.post(
        "/api/v1/notifications/test",
        headers={"Authorization": f"Bearer {mock_token}"}
    )
    assert response_test.status_code == 200
    assert response_test.json()["status"] == "success_simulated"


# 6. Test Story Generator
def test_stories():
    # 1. Test generate stream
    payload = {"category": "Sleep", "length": "Short"}
    response = client.post(
        "/api/v1/stories/generate",
        json=payload,
        headers={"Authorization": f"Bearer {mock_token}"}
    )
    assert response.status_code == 200
    # Retrieve contents from stream
    story_text = response.text
    assert len(story_text) > 0

    # 2. Get history (allow a brief async sleep if background write takes time, 
    # but since our mock is synchronous/fast in test env, it should be immediate)
    response_history = client.get(
        "/api/v1/stories/history",
        headers={"Authorization": f"Bearer {mock_token}"}
    )
    assert response_history.status_code == 200
    history = response_history.json()
    assert len(history) >= 1
    story_id = history[0]["story_id"]
    assert history[0]["category"] == "Sleep"

    # 3. Test favorite
    response_fav = client.post(
        "/api/v1/stories/favorite",
        json={"story_id": story_id},
        headers={"Authorization": f"Bearer {mock_token}"}
    )
    assert response_fav.status_code == 200
    assert response_fav.json()["is_favorite"] is True


# 7. Test Meditation Module
def test_meditation():
    # 1. Submit session
    payload = {
        "breathing_type": "Box Breathing",
        "duration_seconds": 300,
        "coins_reward": 10,
        "xp_reward": 20
    }
    response = client.post(
        "/api/v1/meditation/session",
        json=payload,
        headers={"Authorization": f"Bearer {mock_token}"}
    )
    assert response.status_code == 200
    data = response.json()
    assert data["breathing_type"] == "Box Breathing"
    assert data["duration_seconds"] == 300
    assert data["coins_reward"] == 10

    # 2. Get history logs
    response_history = client.get(
        "/api/v1/meditation/history",
        headers={"Authorization": f"Bearer {mock_token}"}
    )
    assert response_history.status_code == 200
    history = response_history.json()
    assert len(history["sessions"]) >= 1
    assert history["total_xp"] >= 20
    assert history["total_coins"] >= 10
    assert history["streak"] >= 1


# 8. Test Voice Summarization
def test_voice_summarize():
    payload = {"audio_url": "https://storage.googleapis.com/auraai/voice_stress.wav"}
    response = client.post(
        "/api/v1/journal/voice/summarize",
        json=payload,
        headers={"Authorization": f"Bearer {mock_token}"}
    )
    assert response.status_code == 200
    data = response.json()
    assert "transcription" in data
    assert "summary" in data
    assert data["detected_emotion"] == "Stress"


# 9. Test FCM Notifications Dispatch
def test_notifications_send():
    payload = {
        "notification_type": "meditation",
        "title": "Breathe Now",
        "body": "It's time for your 5-min Box Breathing session."
    }
    response = client.post(
        "/api/v1/notifications/send",
        json=payload,
        headers={"Authorization": f"Bearer {mock_token}"}
    )
    assert response.status_code == 200
    data = response.json()
    assert data["status"] == "success_dispatched"
    assert "notification_id" in data
    assert data["notification_type"] == "meditation"



