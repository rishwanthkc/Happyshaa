from pydantic import BaseModel, EmailStr, Field
from typing import Optional
from datetime import datetime

class UserBase(BaseModel):
    email: EmailStr
    display_name: Optional[str] = None
    photo_url: Optional[str] = None

class UserCreate(UserBase):
    uid: str = Field(..., description="Firebase Unique Identifier")

class UserSyncRequest(BaseModel):
    display_name: Optional[str] = None
    photo_url: Optional[str] = None

class UserProfile(UserBase):
    uid: str
    created_at: datetime
    last_login: datetime
    is_active: bool = True
    
    class Config:
        from_attributes = True

class UserProfileResponse(BaseModel):
    status: str
    user: UserProfile

class MoodAnalysisRequest(BaseModel):
    text: str

class MoodLogEntry(BaseModel):
    log_id: str
    uid: str
    timestamp: float
    primary_emotion: str
    confidence_score: float
    stress_level: float
    anxiety_level: float
    sadness_level: float
    anger_level: float
    happiness_level: float
    confidence_level: float
    suggested_activities: list[str]

class MoodHistoryResponse(BaseModel):
    logs: list[MoodLogEntry]

class ChatMessageRequest(BaseModel):
    message: str
    current_mood: Optional[str] = "Neutral"

class ChatHistoryMessage(BaseModel):
    msg_id: str
    uid: str
    timestamp: float
    sender: str
    content: str

class ChatHistoryResponse(BaseModel):
    history: list[ChatHistoryMessage]

class MusicTrack(BaseModel):
    song_id: str
    title: str
    artist: str
    url: str
    category: str
    mood_tag: str

class FavoriteToggleRequest(BaseModel):
    song_id: str

class HistoryLogRequest(BaseModel):
    song_id: str
    duration_sec: int

class ContactCreateRequest(BaseModel):
    name: str
    phone: str
    email: Optional[str] = None
    relationship_label: str = "Friend"
    is_favorite: bool = False
    is_emergency: bool = False

class ContactUpdateRequest(BaseModel):
    name: Optional[str] = None
    phone: Optional[str] = None
    email: Optional[str] = None
    relationship_label: Optional[str] = None
    is_favorite: Optional[bool] = None
    is_emergency: Optional[bool] = None

class ContactResponse(BaseModel):
    contact_id: str
    uid: str
    name: str
    phone: str
    email: Optional[str] = None
    relationship_label: str
    is_favorite: bool
    is_emergency: bool

class GameScoreSubmitRequest(BaseModel):
    game_id: str
    score: int

class AchievementResponse(BaseModel):
    achievement_id: str
    uid: str
    achievement_type: str
    timestamp: float

class GameProgressResponse(BaseModel):
    score_id: str
    xp_earned: int
    coins_earned: int
    new_balance: int
    unlocked_achievement: Optional[str] = None

class JournalCreateRequest(BaseModel):
    title: Optional[str] = ""
    content: str
    audio_url: Optional[str] = None

class JournalResponse(BaseModel):
    journal_id: str
    uid: str
    timestamp: float
    title: Optional[str] = None
    content: str
    detected_emotion: str
    emotion_confidence: float
    audio_url: Optional[str] = None
    reflection: str
    gratitude_highlights: list[str]
    triggers: list[str]

class WeeklyReportResponse(BaseModel):
    report_id: str
    uid: str
    timestamp: float
    dominant_mood: str
    average_stress_level: float
    gratitude_summary: str
    identified_triggers: list[str]
    self_care_plan: list[str]

class RecommendationCardResponse(BaseModel):
    id: str
    title: str
    description: str
    activity_type: str
    target_route: str
    difficulty: str
    coins_reward: int

class FCMTokenRequest(BaseModel):
    fcm_token: str

class NotificationItemResponse(BaseModel):
    notification_id: str
    uid: str
    timestamp: float
    title: str
    body: str
    is_read: bool


class StoryGenerateRequest(BaseModel):
    category: str
    length: str


class StoryResponse(BaseModel):
    story_id: str
    uid: str
    title: str
    content: str
    category: str
    length: str
    timestamp: float
    is_favorite: bool


class StoryFavoriteRequest(BaseModel):
    story_id: str


class MeditationSessionRequest(BaseModel):
    breathing_type: str
    duration_seconds: int
    coins_reward: int
    xp_reward: int


class MeditationSessionResponse(BaseModel):
    session_id: str
    uid: str
    breathing_type: str
    duration_seconds: int
    timestamp: float
    coins_reward: int
    xp_reward: int


class MeditationHistoryResponse(BaseModel):
    sessions: list[MeditationSessionResponse]
    streak: int
    total_xp: int
    total_coins: int


class VoiceJournalSummarizeRequest(BaseModel):
    audio_url: str


class VoiceJournalSummarizeResponse(BaseModel):
    transcription: str
    summary: str
    detected_emotion: str
    reflection: str


class SendNotificationRequest(BaseModel):
    notification_type: str
    title: str
    body: str


class SendNotificationResponse(BaseModel):
    status: str
    notification_id: str
    notification_type: str
    target_token: str










