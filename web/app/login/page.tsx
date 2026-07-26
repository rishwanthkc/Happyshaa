"use client";

import { useState, useEffect } from "react";
import { signInWithEmailAndPassword, createUserWithEmailAndPassword, signInWithPopup, sendPasswordResetEmail } from "firebase/auth";
import { useRouter } from "next/navigation";
import { auth, googleProvider } from "../../lib/firebase";
import { useAuthStore } from "../../store/authStore";
import { motion } from "framer-motion";

export default function LoginPage() {
  const router = useRouter();
  const { isAuthenticated, fetchProfile } = useAuthStore();

  const [isSignUp, setIsSignUp] = useState(false);
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [errorMsg, setErrorMsg] = useState<string | null>(null);
  const [infoMsg, setInfoMsg] = useState<string | null>(null);
  const [loading, setLoading] = useState(false);

  // If already authenticated, redirect to dashboard
  useEffect(() => {
    if (isAuthenticated) {
      router.push("/dashboard");
    }
  }, [isAuthenticated, router]);

  const handleAuth = async (e: React.FormEvent) => {
    e.preventDefault();
    setErrorMsg(null);
    setInfoMsg(null);
    setLoading(true);

    try {
      if (isSignUp) {
        await createUserWithEmailAndPassword(auth, email, password);
        setInfoMsg("Account registered successfully!");
      } else {
        await signInWithEmailAndPassword(auth, email, password);
        await fetchProfile();
        router.push("/dashboard");
      }
    } catch (e: any) {
      setErrorMsg(e.message || "Authentication failed. Check your credentials.");
    } finally {
      setLoading(false);
    }
  };

  const handleGoogleLogin = async () => {
    setErrorMsg(null);
    try {
      await signInWithPopup(auth, googleProvider);
      await fetchProfile();
      router.push("/dashboard");
    } catch (e: any) {
      setErrorMsg(e.message || "Google Authentication failed.");
    }
  };

  const handlePasswordReset = async () => {
    if (!email) {
      setErrorMsg("Please enter your email to request reset.");
      return;
    }
    setErrorMsg(null);
    try {
      await sendPasswordResetEmail(auth, email);
      setInfoMsg("Reset instructions sent to your email!");
    } catch (e: any) {
      setErrorMsg(e.message);
    }
  };

  return (
    <div className="flex min-h-screen items-center justify-center p-4">
      <motion.div
        initial={{ opacity: 0, y: 30 }}
        animate={{ opacity: 1, y: 0 }}
        transition={{ duration: 0.6 }}
        className="glass-panel w-full max-w-md rounded-2xl p-8"
      >
        <div className="text-center mb-8 flex flex-col items-center">
          <img src="/logo.jpg" alt="Happyshaa Logo" className="w-24 h-24 rounded-2xl mb-4 shadow-lg shadow-[#00f2fe]/10" />
          <h1 className="text-3xl font-extrabold tracking-tight text-gradient-cyan">Happyshaa</h1>
          <p className="text-white/60 text-sm mt-2">Empathetic AI CBT & Wellness Companion</p>
        </div>

        {errorMsg && (
          <div className="bg-red-500/10 border border-red-500/20 text-red-400 text-xs p-3 rounded-lg mb-4 text-center">
            {errorMsg}
          </div>
        )}

        {infoMsg && (
          <div className="bg-[#00f2fe]/10 border border-[#00f2fe]/20 text-[#00f2fe] text-xs p-3 rounded-lg mb-4 text-center">
            {infoMsg}
          </div>
        )}

        <form onSubmit={handleAuth} className="space-y-4">
          <div>
            <label className="text-white/70 text-xs block mb-1 font-semibold">Email Address</label>
            <input
              type="email"
              required
              value={email}
              onChange={(e) => setEmail(e.target.value)}
              className="glass-input w-full p-3 text-sm"
              placeholder="name@example.com"
            />
          </div>

          <div>
            <label className="text-white/70 text-xs block mb-1 font-semibold">Password</label>
            <input
              type="password"
              required
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              className="glass-input w-full p-3 text-sm"
              placeholder="••••••••"
            />
          </div>

          <button
            type="submit"
            disabled={loading}
            className="w-full bg-gradient-to-r from-[#00f2fe] to-[#4facfe] hover:brightness-110 text-black font-bold p-3 rounded-lg transition text-sm flex items-center justify-center cursor-pointer"
          >
            {loading ? (
              <div className="h-5 w-5 animate-spin rounded-full border-2 border-black border-t-transparent" />
            ) : isSignUp ? (
              "Create Free Account"
            ) : (
              "Sign In"
            )}
          </button>
        </form>

        <div className="mt-4 flex items-center justify-between text-xs text-white/50">
          <span
            onClick={() => setIsSignUp(!isSignUp)}
            className="hover:text-white cursor-pointer transition font-semibold"
          >
            {isSignUp ? "Already have account? Login" : "Create new account"}
          </span>
          {!isSignUp && (
            <span
              onClick={handlePasswordReset}
              className="hover:text-white cursor-pointer transition font-semibold"
            >
              Forgot password?
            </span>
          )}
        </div>

        <div className="relative my-6">
          <div className="absolute inset-0 flex items-center"><div className="w-full border-t border-white/10"></div></div>
          <div className="relative flex justify-center text-xs"><span className="bg-[#10132b] px-2 text-white/40">Or Continue With</span></div>
        </div>

        <button
          onClick={handleGoogleLogin}
          className="w-full bg-white/5 border border-white/10 hover:bg-white/10 text-white font-bold p-3 rounded-lg transition text-sm flex items-center justify-center gap-2 cursor-pointer"
        >
          <svg className="h-4 w-4" viewBox="0 0 24 24">
            <path fill="#EA4335" d="M12 5.04c1.67 0 3.19.57 4.38 1.69l3.27-3.27C17.68 1.54 14.98 1 12 1 7.35 1 3.4 3.65 1.5 7.5l3.86 3c.9-2.7 3.4-4.5 6.64-4.5z"/>
            <path fill="#4285F4" d="M23.5 12.25c0-.82-.07-1.6-.21-2.25H12v4.25h6.48c-.28 1.48-1.12 2.73-2.38 3.58l3.7 2.87c2.16-2 3.7-4.95 3.7-8.45z"/>
            <path fill="#FBBC05" d="M5.36 10.5c-.24-.72-.36-1.48-.36-2.25s.12-1.53.36-2.25l-3.86-3C.56 5.16 0 7.02 0 9s.56 3.84 1.5 5.5l3.86-3z"/>
            <path fill="#34A853" d="M12 23c3.24 0 5.97-1.07 7.96-2.91l-3.7-2.87c-1.03.69-2.35 1.1-4.26 1.1-3.24 0-5.74-1.8-6.64-4.5l-3.86 3C3.4 20.35 7.35 23 12 23z"/>
          </svg>
          Google OAuth
        </button>
      </motion.div>
    </div>
  );
}
