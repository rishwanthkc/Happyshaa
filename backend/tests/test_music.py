import os
import pytest
from fastapi.testclient import TestClient

os.environ["ENV"] = "testing"

from app.main import app

client = TestClient(app)
mock_token = "mock_token_testuser123_test@auraai.com"

def test_get_songs_success():
    response = client.get(
        "/api/v1/music/songs",
        headers={"Authorization": f"Bearer {mock_token}"}
    )
    assert response.status_code == 200
    data = response.json()
    assert len(data) >= 3
    assert data[0]["song_id"] == "nature_rain"

def test_toggle_favorite_and_retrieve():
    # 1. Toggle Favorite on
    response = client.post(
        "/api/v1/music/favorites/toggle",
        json={"song_id": "nature_rain"},
        headers={"Authorization": f"Bearer {mock_token}"}
    )
    assert response.status_code == 200
    assert response.json()["is_favorite"] is True

    # 2. Retrieve Favorites
    response_get = client.get(
        "/api/v1/music/favorites",
        headers={"Authorization": f"Bearer {mock_token}"}
    )
    assert response_get.status_code == 200
    favorites = response_get.json()
    assert len(favorites) == 1
    assert favorites[0]["song_id"] == "nature_rain"

def test_log_playback_history():
    response = client.post(
        "/api/v1/music/history",
        json={"song_id": "lofi_focus", "duration_sec": 120},
        headers={"Authorization": f"Bearer {mock_token}"}
    )
    assert response.status_code == 200
    assert "history_id" in response.json()

def test_music_recommendations():
    response = client.get(
        "/api/v1/music/recommend",
        headers={"Authorization": f"Bearer {mock_token}"}
    )
    assert response.status_code == 200
    data = response.json()
    assert len(data) >= 1
