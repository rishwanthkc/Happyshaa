from fastapi import FastAPI
from fastapi.middleware.cors import CORSMiddleware
from app.core.config import settings
from app.core.logging import setup_logging
from app.core.exceptions import register_exception_handlers
from app.core.firebase import initialize_firebase
from app.middleware.request_logging import RequestLoggingMiddleware
from app.api.routers import auth, chat, mood, music, contacts, games, journal, recommend, notifications, stories, meditation

# Setup Logger
setup_logging()

# Initialize Firebase (runs on startup)
initialize_firebase()

# Create FastAPI instance
app = FastAPI(
    title=settings.PROJECT_NAME,
    description="Emotional Wellness Companion App Python API Backend",
    version="1.0.0",
    docs_url="/docs",
    redoc_url="/redoc"
)

# Setup Request Logging Middleware
app.add_middleware(RequestLoggingMiddleware)

# Setup CORS middleware for local development
app.add_middleware(
    CORSMiddleware,
    allow_origins=["*"],  # Restrict this to mobile scheme/domain in production
    allow_credentials=True,
    allow_methods=["*"],
    allow_headers=["*"],
)

# Register custom Exception Handlers
register_exception_handlers(app)

# Register routers
app.include_router(
    auth.router,
    prefix=f"{settings.API_V1_STR}/auth",
    tags=["Authentication"]
)

app.include_router(
    chat.router,
    prefix=f"{settings.API_V1_STR}/chat",
    tags=["AI Friend Chat"]
)

app.include_router(
    mood.router,
    prefix=f"{settings.API_V1_STR}/mood",
    tags=["Emotion Detection"]
)

app.include_router(
    music.router,
    prefix=f"{settings.API_V1_STR}/music",
    tags=["Wellness Music Player"]
)

app.include_router(
    contacts.router,
    prefix=f"{settings.API_V1_STR}/contacts",
    tags=["Circle of Support Contacts"]
)

app.include_router(
    games.router,
    prefix=f"{settings.API_V1_STR}/games",
    tags=["Wellness Game Center"]
)

app.include_router(
    journal.router,
    prefix=f"{settings.API_V1_STR}/journal",
    tags=["AI Wellness Journaling"]
)

app.include_router(
    recommend.router,
    prefix=f"{settings.API_V1_STR}/recommend",
    tags=["Personalized Recommendations"]
)

app.include_router(
    notifications.router,
    prefix=f"{settings.API_V1_STR}/notifications",
    tags=["Push Alerts and Reminders"]
)

app.include_router(
    stories.router,
    prefix=f"{settings.API_V1_STR}/stories",
    tags=["Calming Stories Generator"]
)

app.include_router(
    meditation.router,
    prefix=f"{settings.API_V1_STR}/meditation",
    tags=["Mindful Meditation Timer"]
)

@app.get("/")
def read_root():
    return {
        "app": settings.PROJECT_NAME,
        "status": "healthy",
        "env": settings.ENV
    }
