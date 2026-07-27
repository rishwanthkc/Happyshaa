import os
from typing import Optional
from pydantic_settings import BaseSettings, SettingsConfigDict

class Settings(BaseSettings):
    PROJECT_NAME: str = "Aura AI Backend"
    API_V1_STR: str = "/api/v1"
    
    # Environment mode: 'development', 'production', or 'testing'
    ENV: str = "development"
    
    # Path to firebase service account json key
    FIREBASE_CREDENTIALS_PATH: Optional[str] = None
    
    # Alternatively, raw JSON string of firebase credentials for cloud environments
    FIREBASE_CREDENTIALS_JSON: Optional[str] = None
    
    # Firebase Realtime Database URL
    FIREBASE_DATABASE_URL: Optional[str] = "https://happy-sha-default-rtdb.firebaseio.com"
    
    # AI Engine API Key configurations
    GEMINI_API_KEY: Optional[str] = None

    # Sensitive data local encryption secret key
    ENCRYPTION_SECRET_KEY: str = "super_secret_fallback_key_32_bytes_len!"

    model_config = SettingsConfigDict(
        env_file=".env",
        env_file_encoding="utf-8",
        case_sensitive=True
    )

settings = Settings()
