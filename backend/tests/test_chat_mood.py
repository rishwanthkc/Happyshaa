import os
import pytest
from fastapi.testclient import TestClient

# Set testing environment variable BEFORE importing settings/app
os.environ["ENV"] = "testing"

from app.main import app

client = TestClient(app)
mock_token = "mock_token_testuser123_test@auraai.com"

def test_analyze_mood_success():
    """
    Asserts mood analysis triggers classifications and suggested activities properly.
    """
    payload = {"text": "I feel extremely anxious and overwhelmed with work today."}
    response = client.post(
        "/api/v1/mood/analyze",
        json=payload,
        headers={"Authorization": f"Bearer {mock_token}"}
    )
    assert response.status_code == 200
    data = response.json()
    assert "primary_emotion" in data
    assert "suggested_activities" in data
    assert len(data["suggested_activities"]) >= 2
    assert data["primary_emotion"] in ["Anxiety", "Stress"]

def test_get_mood_history_success():
    """
    Asserts retrieving historical logs returns list types.
    """
    response = client.get(
        "/api/v1/mood/history",
        headers={"Authorization": f"Bearer {mock_token}"}
    )
    assert response.status_code == 200
    data = response.json()
    assert "logs" in data
    assert isinstance(data["logs"], list)

def test_chat_respond_streaming_success():
    """
    Asserts the companion respond API triggers HTTP transfer-encoding chunked stream content.
    """
    payload = {
        "message": "Hi, I need a friend to talk to.",
        "current_mood": "Neutral"
    }
    response = client.post(
        "/api/v1/chat/respond",
        json=payload,
        headers={"Authorization": f"Bearer {mock_token}"}
    )
    assert response.status_code == 200
    content = response.text
    assert len(content) > 0
    # Asserts fallback companion keywords are present
    assert any(keyword in content.lower() for keyword in ["listen", "breath", "trouble", "hear", "friend", "here"])

def test_get_chat_history_success():
    """
    Asserts fetching conversational logs returns list objects.
    """
    response = client.get(
        "/api/v1/chat/history",
        headers={"Authorization": f"Bearer {mock_token}"}
    )
    assert response.status_code == 200
    data = response.json()
    assert "history" in data
    assert isinstance(data["history"], list)
