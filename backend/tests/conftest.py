import os
import pytest

# Enforce testing environment configuration
os.environ["ENV"] = "testing"
os.environ["FIREBASE_CREDENTIALS_JSON"] = ""
os.environ["FIREBASE_CREDENTIALS_PATH"] = ""

@pytest.fixture(scope="session", autouse=True)
def setup_test_env():
    """
    Setup baseline mock configurations before executing any tests.
    """
    yield
