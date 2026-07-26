# Aura AI Next.js 15 Web Client

This is the premium, glassmorphic Next.js 15 Web Client for the Aura AI wellness companion ecosystem. It hooks directly into the existing FastAPI backend and Firebase database to synchronize user accounts, chat reflection logs, CBT journal notes, coins, and XP in real-time.

---

## 🚀 Getting Started

### Prerequisites
- Node.js (v18.0.0 or higher)
- npm (v9.0.0 or higher)

### Setup & Installation
1. Install client libraries:
   ```bash
   npm install
   ```

2. Setup Local Server API Target in `.env.local`:
   ```env
   NEXT_PUBLIC_API_URL=http://localhost:8000
   NEXT_PUBLIC_FIREBASE_API_KEY=mock_api_key
   NEXT_PUBLIC_FIREBASE_AUTH_DOMAIN=auraai.firebaseapp.com
   NEXT_PUBLIC_FIREBASE_PROJECT_ID=auraai
   NEXT_PUBLIC_FIREBASE_STORAGE_BUCKET=auraai.appspot.com
   ```

3. Run Developer Mode Server:
   ```bash
   npm run dev
   ```

---

## 🛠️ Build & Docker Deployments

### Standalone Production Build
Compile optimized JS assets:
```bash
npm run build
```

### Docker Container Setup
Build the Docker image:
```bash
docker build -t aura-web-client .
```

Run the container mapping local port 3000:
```bash
docker run -p 3000:3000 aura-web-client
```

---

## 📁 Project Architecture & Components
- **`/app`**: Next.js 15 App Router pages mapping Dashboard, chat companions, and wellness metrics.
- **`/store`**: Zustand stores syncing authentication details and ExoPlayer audio states.
- **`/lib`**: Axios instances routing requests with automatic Firebase JWT authorization headers.
