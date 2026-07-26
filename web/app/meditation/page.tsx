"use client";

import { useState, useEffect } from "react";
import AppLayout from "../../components/AppLayout";
import api from "../../lib/api";
import { useAuthStore } from "../../store/authStore";
import { motion, AnimatePresence } from "framer-motion";
import { Wind, Play, Square, Award, Calendar } from "lucide-react";

interface MeditationSession {
  session_id: string;
  breathing_type: string;
  duration_seconds: number;
  timestamp: number;
  coins_reward: number;
  xp_reward: number;
}

export default function MeditationPage() {
  const { updateCoinsAndXp } = useAuthStore();
  const [history, setHistory] = useState<MeditationSession[]>([]);
  const [selectedPattern, setSelectedPattern] = useState("Box Breathing");
  const [selectedDuration, setSelectedDuration] = useState(60); // seconds

  const [isActive, setIsActive] = useState(false);
  const [timeLeft, setTimeLeft] = useState(60);
  const [breathingPhase, setBreathingPhase] = useState("Get Ready"); // Inhale, Hold, Exhale
  const [phaseProgress, setPhaseProgress] = useState(0); // 0.0 to 1.0

  const patterns = ["Box Breathing", "4-7-8 Breathing", "Calm Breathing", "Deep Breathing"];
  const durations = [
    { label: "1 Min", val: 60 },
    { label: "2 Min", val: 120 },
    { label: "5 Min", val: 300 }
  ];

  const fetchHistory = async () => {
    try {
      const response = await api.get("/api/v1/meditation/history");
      setHistory(response.data.sessions.reverse());
    } catch (e) {
      setHistory([
        {
          session_id: "m1",
          breathing_type: "Box Breathing",
          duration_seconds: 60,
          timestamp: Date.now() / 1000,
          coins_reward: 10,
          xp_reward: 20
        }
      ]);
    }
  };

  useEffect(() => {
    fetchHistory();
  }, []);

  // Main Timer loop
  useEffect(() => {
    let interval: NodeJS.Timeout | null = null;
    if (isActive && timeLeft > 0) {
      interval = setInterval(() => {
        setTimeLeft((prev) => prev - 1);
      }, 1000);
    } else if (timeLeft === 0 && isActive) {
      handleComplete();
    }
    return () => {
      if (interval) clearInterval(interval);
    };
  }, [isActive, timeLeft]);

  // Breathing Phase cycle loop
  useEffect(() => {
    let cycleInterval: NodeJS.Timeout | null = null;
    if (isActive && timeLeft > 0) {
      let elapsedSeconds = 0;
      cycleInterval = setInterval(() => {
        elapsedSeconds++;
        const pattern = selectedPattern;

        // Custom cycle configs (pacing intervals)
        if (pattern === "Box Breathing") {
          // 4s Inhale, 4s Hold, 4s Exhale, 4s Hold
          const cycleOffset = elapsedSeconds % 16;
          if (cycleOffset < 4) {
            setBreathingPhase("Inhale");
            setPhaseProgress(cycleOffset / 4);
          } else if (cycleOffset < 8) {
            setBreathingPhase("Hold");
            setPhaseProgress((cycleOffset - 4) / 4);
          } else if (cycleOffset < 12) {
            setBreathingPhase("Exhale");
            setPhaseProgress((cycleOffset - 8) / 4);
          } else {
            setBreathingPhase("Hold");
            setPhaseProgress((cycleOffset - 12) / 4);
          }
        } else if (pattern === "4-7-8 Breathing") {
          // 4s Inhale, 7s Hold, 8s Exhale
          const cycleOffset = elapsedSeconds % 19;
          if (cycleOffset < 4) {
            setBreathingPhase("Inhale");
            setPhaseProgress(cycleOffset / 4);
          } else if (cycleOffset < 11) {
            setBreathingPhase("Hold");
            setPhaseProgress((cycleOffset - 4) / 7);
          } else {
            setBreathingPhase("Exhale");
            setPhaseProgress((cycleOffset - 11) / 8);
          }
        } else {
          // General 5s Inhale, 5s Exhale
          const cycleOffset = elapsedSeconds % 10;
          if (cycleOffset < 5) {
            setBreathingPhase("Inhale");
            setPhaseProgress(cycleOffset / 5);
          } else {
            setBreathingPhase("Exhale");
            setPhaseProgress((cycleOffset - 5) / 5);
          }
        }
      }, 1000);
    } else {
      setBreathingPhase("Get Ready");
      setPhaseProgress(0);
    }
    return () => {
      if (cycleInterval) clearInterval(cycleInterval);
    };
  }, [isActive, selectedPattern, timeLeft]);

  const handleStart = () => {
    setTimeLeft(selectedDuration);
    setIsActive(true);
  };

  const handleStop = () => {
    setIsActive(false);
    setBreathingPhase("Get Ready");
  };

  const handleComplete = async () => {
    setIsActive(false);
    setBreathingPhase("Get Ready");
    
    const coinsReward = 15;
    const xpReward = 30;

    try {
      await api.post("/api/v1/meditation/session", {
        breathing_type: selectedPattern,
        duration_seconds: selectedDuration,
        coins_reward: coinsReward,
        xp_reward: xpReward
      });
      updateCoinsAndXp(coinsReward, xpReward);
      fetchHistory();
      alert("Session completed! You earned +15 Coins and +30 XP!");
    } catch (e) {
      updateCoinsAndXp(coinsReward, xpReward);
      alert("Session logged offline!");
    }
  };

  // Determine breathing scale factor based on active phase
  const circleScale = whenPhaseScale(breathingPhase);

  return (
    <AppLayout>
      <div className="max-w-5xl mx-auto grid grid-cols-1 lg:grid-cols-3 gap-8">
        
        {/* Left Column: Config Panel */}
        <div className="lg:col-span-1 space-y-6">
          <div className="glass-panel rounded-2xl p-6 space-y-4">
            <h3 className="text-lg font-bold flex items-center gap-2">
              <Wind className="h-5 w-5 text-[#00f2fe]" />
              Zen Breathing Guide
            </h3>

            <div>
              <label className="text-white/60 text-xs block mb-2 font-semibold">Breathing Pattern</label>
              <div className="flex flex-col gap-2">
                {patterns.map((pat) => (
                  <button
                    key={pat}
                    onClick={() => setSelectedPattern(pat)}
                    disabled={isActive}
                    className={`p-3 rounded-xl text-left text-sm font-semibold transition cursor-pointer ${
                      selectedPattern === pat 
                        ? "bg-[#00f2fe]/20 border border-[#00f2fe]/50 text-[#00f2fe]" 
                        : "bg-white/5 border border-white/5 hover:bg-white/10 text-white"
                    } disabled:opacity-50`}
                  >
                    {pat}
                  </button>
                ))}
              </div>
            </div>

            <div>
              <label className="text-white/60 text-xs block mb-2 font-semibold">Duration</label>
              <div className="flex gap-2">
                {durations.map((dur) => (
                  <button
                    key={dur.label}
                    onClick={() => {
                      setSelectedDuration(dur.val);
                      setTimeLeft(dur.val);
                    }}
                    disabled={isActive}
                    className={`flex-1 py-1.5 px-3 rounded-lg text-xs font-semibold cursor-pointer ${
                      selectedDuration === dur.val ? "bg-[#00f2fe] text-black" : "bg-white/5 border border-white/5 text-white"
                    } disabled:opacity-50`}
                  >
                    {dur.label}
                  </button>
                ))}
              </div>
            </div>

            {!isActive ? (
              <button
                onClick={handleStart}
                className="w-full bg-gradient-to-r from-[#00f2fe] to-[#4facfe] text-black font-bold p-3 rounded-lg text-sm flex items-center justify-center gap-2 cursor-pointer"
              >
                <Play className="h-4 w-4" /> Start Meditation
              </button>
            ) : (
              <button
                onClick={handleStop}
                className="w-full bg-red-500/20 border border-red-500/30 text-red-400 font-bold p-3 rounded-lg text-sm flex items-center justify-center gap-2 cursor-pointer"
              >
                <Square className="h-4 w-4" /> End Session
              </button>
            )}
          </div>

          {/* Session logs list */}
          <div className="glass-panel rounded-2xl p-6">
            <h3 className="text-lg font-bold mb-4 flex items-center gap-2">
              <Calendar className="h-5 w-5 text-[#00f2fe]" />
              Meditation History
            </h3>
            <div className="space-y-3 max-h-60 overflow-y-auto pr-2">
              {history.map((session, idx) => (
                <div key={idx} className="bg-white/5 border border-white/10 rounded-xl p-4 flex justify-between items-center text-sm">
                  <div>
                    <h4 className="font-bold text-xs text-white">{session.breathing_type}</h4>
                    <span className="text-[10px] text-white/50">{session.duration_seconds} seconds</span>
                  </div>
                  <div className="text-right">
                    <span className="text-xs text-[#00f2fe] font-bold block">+{session.xp_reward} XP</span>
                    <span className="text-[10px] text-yellow-400">+{session.coins_reward} Coins</span>
                  </div>
                </div>
              ))}
            </div>
          </div>
        </div>

        {/* Right Column: Breathing Guide Circle */}
        <div className="lg:col-span-2">
          <div className="glass-panel rounded-2xl p-6 h-full min-h-[400px] flex flex-col items-center justify-center relative overflow-hidden">
            
            {/* Ambient Background Aura */}
            <div className="absolute w-72 h-72 rounded-full bg-[#00f2fe]/5 blur-3xl" />

            <div className="flex flex-col items-center justify-center space-y-8 z-10">
              <div className="text-center">
                <span className="text-[#00f2fe] font-extrabold uppercase tracking-wider text-xs block mb-1">Time Remaining</span>
                <h4 className="text-4xl font-extrabold">{String(Math.floor(timeLeft / 60)).padStart(2, "0")}:{String(timeLeft % 60).padStart(2, "0")}</h4>
              </div>

              {/* Animated Pacing Breathing Circle */}
              <motion.div
                animate={{ scale: circleScale }}
                transition={{ duration: breathingPhase === "Hold" ? 0 : 4, ease: "easeInOut" }}
                className="w-44 h-44 rounded-full bg-gradient-to-br from-[#00f2fe] to-[#4facfe] flex items-center justify-center shadow-lg shadow-[#00f2fe]/20"
              >
                <div className="w-36 h-36 rounded-full bg-[#0c091a] flex flex-col items-center justify-center">
                  <span className="text-[#00f2fe] font-bold text-sm tracking-wide">{breathingPhase}</span>
                </div>
              </motion.div>

              <span className="text-white/60 text-xs italic">
                {breathingPhase === "Inhale" && "Breathe in slowly through your nose..."}
                {breathingPhase === "Hold" && "Hold your breath..."}
                {breathingPhase === "Exhale" && "Exhale gently through your mouth..."}
                {breathingPhase === "Get Ready" && "Find a comfortable seat."}
              </span>
            </div>

          </div>
        </div>

      </div>
    </AppLayout>
  );
}

function whenPhaseScale(phase: string): number {
  switch (phase) {
    case "Inhale": return 1.6;
    case "Exhale": return 1.0;
    case "Hold": return 1.6;
    default: return 1.15;
  }
}
