import { create } from "zustand";
import { User as FirebaseUser, onAuthStateChanged, signOut as fbSignOut } from "firebase/auth";
import { auth } from "../lib/firebase";
import api from "../lib/api";

interface UserProfile {
  uid: string;
  email: string;
  displayName: string;
  coins: number;
  xp: number;
  badges: string[];
}

interface AuthState {
  user: FirebaseUser | null;
  profile: UserProfile | null;
  isAuthenticated: boolean;
  isLoading: boolean;
  initialize: () => () => void;
  fetchProfile: () => Promise<void>;
  signOut: () => Promise<void>;
  updateCoinsAndXp: (coins: number, xp: number) => void;
}

export const useAuthStore = create<AuthState>((set, get) => ({
  user: null,
  profile: null,
  isAuthenticated: false,
  isLoading: true,

  initialize: () => {
    const timeoutId = setTimeout(() => {
      if (get().isLoading) {
        set({ isLoading: false });
      }
    }, 1500);

    const unsubscribe = onAuthStateChanged(auth, async (firebaseUser) => {
      clearTimeout(timeoutId);
      if (firebaseUser) {
        set({ user: firebaseUser, isAuthenticated: true, isLoading: false });
        await get().fetchProfile();
      } else {
        set({ user: null, profile: null, isAuthenticated: false, isLoading: false });
      }
    });

    return () => {
      clearTimeout(timeoutId);
      unsubscribe();
    };
  },

  fetchProfile: async () => {
    try {
      const response = await api.get("/api/v1/auth/profile");
      const data = response.data;
      set({
        profile: {
          uid: data.uid,
          email: data.email,
          displayName: data.display_name || "Aura Member",
          coins: data.coins || 0,
          xp: data.xp || 0,
          badges: data.badges || []
        }
      });
    } catch (e) {
      // Fallback local profile if backend profile lookup fails (offline/mock)
      const user = get().user;
      if (user) {
        set({
          profile: {
            uid: user.uid,
            email: user.email || "",
            displayName: user.displayName || "Aura Member",
            coins: 50,
            xp: 20,
            badges: []
          }
        });
      }
    }
  },

  signOut: async () => {
    await fbSignOut(auth);
    set({ user: null, profile: null, isAuthenticated: false });
  },

  updateCoinsAndXp: (coins: number, xp: number) => {
    const current = get().profile;
    if (current) {
      set({
        profile: {
          ...current,
          coins: current.coins + coins,
          xp: current.xp + xp
        }
      });
    }
  }
}));
