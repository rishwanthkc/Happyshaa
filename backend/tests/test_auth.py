import os
import pytest
from fastapi.testclient import TestClient

# Set testing environment variable BEFORE importing settings/app
os.environ["ENV"] = "testing"

from app.main import app
from app.core.config import settings

client = TestClient(app)

def test_read_root():
    response = client.get("/")
    assert response.status_code == 200
    json_data = response.json()
    assert json_data["status"] == "healthy"
    assert json_data["env"] == "testing"

def test_sync_user_unauthorized():
    # Attempting to access auth endpoint without Bearer token should fail with 401
    response = client.post(
        f"{settings.API_V1_STR}/auth/sync",
        json={"display_name": "Test User"}
    )
    assert response.status_code == 401
    assert "Authorization header missing or invalid" in response.json()["detail"]

def test_sync_user_authorized_mock():
    # Sending a mock authorization token should succeed in test mode
    mock_token = "mock_token_testuser123_test@auraai.com"
    headers = {
        "Authorization": f"Bearer {mock_token}"
    }
    
    response = client.post(
        f"{settings.API_V1_STR}/auth/sync",
        headers=headers,
        json={"display_name": "Test User", "photo_url": "http://example.com/photo.jpg"}
    )
    
    # Since Firestore client will not be initialized (no service-account credentials in test),
    # the router will return status "success_mock" and mirror our details.
    assert response.status_code == 200
    json_data = response.json()
    assert json_data["status"] in ["success_mock", "created", "synchronized"]
    
    user = json_data["user"]
    assert user["uid"] == "testuser123"
    assert user["email"] == "test@auraai.com"
    assert user["display_name"] == "Test User"
