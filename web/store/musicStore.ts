import { create } from "zustand";
import api from "../lib/api";

export interface SongTrack {
  songId: string;
  title: string;
  artist: string;
  url: string;
  category: string;
  stressFactor: string;
  isFavorite?: boolean;
}

interface MusicState {
  songs: SongTrack[];
  queue: SongTrack[];
  currentTrack: SongTrack | null;
  isPlaying: boolean;
  playbackPosition: number;
  duration: number;
  sleepTimerMinutes: number;
  audioElement: HTMLAudioElement | null;
  fetchSongs: () => Promise<void>;
  playTrack: (track: SongTrack) => void;
  pause: () => void;
  resume: () => void;
  next: () => void;
  prev: () => void;
  seek: (seconds: number) => void;
  setSleepTimer: (minutes: number) => void;
  toggleFavorite: (track: SongTrack) => Promise<void>;
}

export const useMusicStore = create<MusicState>((set, get) => {
  let timerId: NodeJS.Timeout | null = null;

  return {
    songs: [],
    queue: [],
    currentTrack: null,
    isPlaying: false,
    playbackPosition: 0,
    duration: 0,
    sleepTimerMinutes: 0,
    audioElement: null,

    fetchSongs: async () => {
      try {
        const response = await api.get("/api/v1/music/songs");
        const list = response.data.map((s: any) => ({
          songId: s.song_id,
          title: s.title,
          artist: s.artist,
          url: s.url,
          category: s.category,
          stressFactor: s.stress_factor,
          isFavorite: s.is_favorite || false
        }));
        set({ songs: list, queue: list });
      } catch (e) {
        // Mock offline fallback
        const mockSongs = [
          { songId: "track1", title: "Calm Rain", artist: "Nature Sounds", url: "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-1.mp3", category: "Nature", stressFactor: "Stress" },
          { songId: "track2", title: "Zen Garden", artist: "Binaural Beats", url: "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-2.mp3", category: "Meditation", stressFactor: "Anxiety" },
          { songId: "track3", title: "Morning Walk", artist: "Relax Lofi", url: "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-3.mp3", category: "Lofi", stressFactor: "Fatigue" }
        ];
        set({ songs: mockSongs, queue: mockSongs });
      }
    },

    playTrack: (track: SongTrack) => {
      const state = get();
      if (state.audioElement) {
        state.audioElement.pause();
      }

      const audio = new Audio(track.url);
      audio.play().then(() => {
        set({ isPlaying: true });
      }).catch(() => {
        set({ isPlaying: false });
      });

      audio.addEventListener("timeupdate", () => {
        set({ playbackPosition: audio.currentTime });
      });

      audio.addEventListener("loadedmetadata", () => {
        set({ duration: audio.duration });
      });

      audio.addEventListener("ended", () => {
        get().next();
      });

      set({
        currentTrack: track,
        audioElement: audio,
        playbackPosition: 0,
        duration: 0
      });

      // Log history log to backend
      api.post("/api/v1/music/history", { song_id: track.songId }).catch(() => {});
    },

    pause: () => {
      const state = get();
      if (state.audioElement) {
        state.audioElement.pause();
        set({ isPlaying: false });
      }
    },

    resume: () => {
      const state = get();
      if (state.audioElement) {
        state.audioElement.play().then(() => {
          set({ isPlaying: true });
        });
      }
    },

    next: () => {
      const { queue, currentTrack } = get();
      if (!currentTrack || queue.length === 0) return;
      const idx = queue.findIndex(s => s.songId === currentTrack.songId);
      const nextIdx = (idx + 1) % queue.length;
      get().playTrack(queue[nextIdx]);
    },

    prev: () => {
      const { queue, currentTrack } = get();
      if (!currentTrack || queue.length === 0) return;
      const idx = queue.findIndex(s => s.songId === currentTrack.songId);
      const prevIdx = (idx - 1 + queue.length) % queue.length;
      get().playTrack(queue[prevIdx]);
    },

    seek: (seconds: number) => {
      const { audioElement } = get();
      if (audioElement) {
        audioElement.currentTime = seconds;
        set({ playbackPosition: seconds });
      }
    },

    setSleepTimer: (minutes: number) => {
      if (timerId) {
        clearTimeout(timerId);
      }
      set({ sleepTimerMinutes: minutes });

      if (minutes > 0) {
        timerId = setTimeout(() => {
          get().pause();
          set({ sleepTimerMinutes: 0 });
        }, minutes * 60 * 1000);
      }
    },

    toggleFavorite: async (track: SongTrack) => {
      try {
        await api.post("/api/v1/music/favorite", { song_id: track.songId });
        set({
          songs: get().songs.map(s => s.songId === track.songId ? { ...s, isFavorite: !s.isFavorite } : s),
          queue: get().queue.map(s => s.songId === track.songId ? { ...s, isFavorite: !s.isFavorite } : s),
          currentTrack: get().currentTrack?.songId === track.songId ? { ...get().currentTrack!, isFavorite: !get().currentTrack!.isFavorite } : get().currentTrack
        });
      } catch (e) {
        // Offline toggle
        set({
          songs: get().songs.map(s => s.songId === track.songId ? { ...s, isFavorite: !s.isFavorite } : s),
          queue: get().queue.map(s => s.songId === track.songId ? { ...s, isFavorite: !s.isFavorite } : s),
          currentTrack: get().currentTrack?.songId === track.songId ? { ...get().currentTrack!, isFavorite: !get().currentTrack!.isFavorite } : get().currentTrack
        });
      }
    }
  };
});
