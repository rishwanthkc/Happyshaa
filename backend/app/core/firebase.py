import json
import logging
import firebase_admin
from firebase_admin import credentials, firestore, db as realtime_db
from app.core.config import settings

logger = logging.getLogger(__name__)

# Initialize Firebase App
def initialize_firebase():
    if firebase_admin._apps:
        # Already initialized
        return firebase_admin.get_app()

    logger.info("Initializing Firebase Admin SDK...")
    
    options = {}
    if settings.FIREBASE_DATABASE_URL:
        options["databaseURL"] = settings.FIREBASE_DATABASE_URL
    
    # 1. Try raw JSON string credentials (ideal for cloud variables)
    if settings.FIREBASE_CREDENTIALS_JSON:
        try:
            cred_dict = json.loads(settings.FIREBASE_CREDENTIALS_JSON)
            cred = credentials.Certificate(cred_dict)
            return firebase_admin.initialize_app(cred, options)
        except Exception as e:
            logger.error(f"Failed to initialize Firebase using FIREBASE_CREDENTIALS_JSON: {e}")

    # 2. Try file path credentials
    if settings.FIREBASE_CREDENTIALS_PATH:
        try:
            cred = credentials.Certificate(settings.FIREBASE_CREDENTIALS_PATH)
            return firebase_admin.initialize_app(cred, options)
        except Exception as e:
            logger.error(f"Failed to initialize Firebase using path {settings.FIREBASE_CREDENTIALS_PATH}: {e}")

    # 3. Fallback: try default credentials (runs locally/cloud default credentials)
    try:
        # This will attempt to find credentials via standard env vars
        return firebase_admin.initialize_app(options=options)
    except Exception as e:
        logger.warning(f"Could not initialize default Firebase credentials: {e}. Running with mock configuration (for testing).")
        
    return None

# Perform initialization
firebase_app = initialize_firebase()

# Retrieve Firestore Client
def get_db():
    try:
        return firestore.client()
    except Exception as e:
        logger.warning(f"Firestore client not available: {e}. Database functions will fail unless mocked.")
        return None

# Retrieve Realtime Database Client
def get_realtime_db():
    try:
        return realtime_db.reference()
    except Exception as e:
        logger.warning(f"Realtime Database reference not available: {e}. Database functions will fail unless mocked.")
        return None

