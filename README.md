# Aura AI - AI Emotional Wellness Companion

Aura AI is a production-ready, AI-driven Emotional Wellness Companion designed to act as a supportive, empathetic, and caring friend. It uses AI memory, emotion tracking, journals, audio/music playback, and personalized recommendations to help users manage stress and improve their mental wellness.

## Project Structure

The project is structured as follows:

```
├── android/            # Jetpack Compose Android Client (MVVM + Clean Architecture)
├── backend/            # FastAPI Python Backend (Firebase Admin SDK, Pydantic)
└── README.md           # This file
```

---

## 🚀 Backend Setup

The backend is built with **Python 3.13** and **FastAPI**. It handles token validation, stores user metadata, processes wellness analytics, and connects to AI endpoints.

### Prerequisites
- Python 3.13+ installed
- Firebase Project setup with a Service Account Key downloaded to `backend/secrets/firebase-service-account.json` (or set `FIREBASE_CREDENTIALS_JSON` environment variable)

### Running Locally
1. Navigate to the backend directory:
   ```bash
   cd backend
   ```
2. Create and activate a virtual environment:
   ```bash
   python -m venv venv
   # On Windows:
   .\venv\Scripts\activate
   # On macOS/Linux:
   source venv/bin/activate
   ```
3. Install dependencies:
   ```bash
   pip install -r requirements.txt
   ```
4. Run the development server:
   ```bash
   uvicorn app.main:app --reload --port 8000
   ```
5. View API docs at: [http://localhost:8000/docs](http://localhost:8000/docs)

### Running with Docker
1. Build and run using Docker Compose:
   ```bash
   docker-compose up --build
   ```

---

## 📱 Android Setup

The frontend is built using **Kotlin**, **Jetpack Compose**, and **Hilt** adhering strictly to MVVM and Clean Architecture guidelines.

### Prerequisites
- Android SDK 34+
- `google-services.json` file from Firebase Console placed in `android/app/`

### Build
To build the application, navigate to the `android/` directory and run:
```bash
cd android
./gradlew assembleDebug
```
