"use client";

import { useState, useEffect } from "react";
import AppLayout from "../../components/AppLayout";
import api from "../../lib/api";
import { motion } from "framer-motion";
import { Smile, Award, Calendar, BarChart2 } from "lucide-react";
import { Line } from "react-chartjs-2";
import {
  Chart as ChartJS,
  CategoryScale,
  LinearScale,
  PointElement,
  LineElement,
  Title,
  Tooltip,
  Legend
} from "chart.js";

// Register ChartJS plugins
ChartJS.register(
  CategoryScale,
  LinearScale,
  PointElement,
  LineElement,
  Title,
  Tooltip,
  Legend
);

interface MoodLog {
  timestamp: number;
  mood: string;
  stress_level: number;
  notes: string;
}

export default function MoodPage() {
  const [logs, setLogs] = useState<MoodLog[]>([]);
  const [stressInput, setStressInput] = useState(5);
  const [selectedMood, setSelectedMood] = useState("Calm");
  const [notesInput, setNotesInput] = useState("");
  const [loading, setLoading] = useState(false);

  const moods = [
    { label: "Happiness", emoji: "🌸" },
    { label: "Calm", emoji: "🌊" },
    { label: "Fatigue", emoji: "💤" },
    { label: "Anxiety", emoji: "🌀" },
    { label: "Sadness", emoji: "🌧️" }
  ];

  const fetchMoodLogs = async () => {
    try {
      const response = await api.get("/api/v1/mood/logs");
      const mapped = response.data.map((l: any) => ({
        timestamp: l.timestamp,
        mood: l.mood,
        stress_level: l.stress_level,
        notes: l.notes
      }));
      setLogs(mapped.reverse());
    } catch (e) {
      // Mock offline fallback
      setLogs([
        { timestamp: (Date.now() / 1000) - 2 * 86400, mood: "Calm", stress_level: 3, notes: "Relaxing walk" },
        { timestamp: (Date.now() / 1000) - 86400, mood: "Fatigue", stress_level: 6, notes: "Busy day" },
        { timestamp: Date.now() / 1000, mood: "Happiness", stress_level: 2, notes: "Had a great check-in" }
      ]);
    }
  };

  useEffect(() => {
    fetchMoodLogs();
  }, []);

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setLoading(true);
    try {
      await api.post("/api/v1/mood/log", {
        mood: selectedMood,
        stress_level: stressInput,
        notes: notesInput
      });
      setNotesInput("");
      fetchMoodLogs();
    } catch (e) {
      // Mock log add
      setLogs((prev) => [
        ...prev,
        { timestamp: Date.now() / 1000, mood: selectedMood, stress_level: stressInput, notes: notesInput }
      ]);
      setNotesInput("");
    } finally {
      setLoading(false);
    }
  };

  // Configure Chart Data
  const chartData = {
    labels: logs.map(l => new Date(l.timestamp * 1000).toLocaleDateString(undefined, { month: "short", day: "numeric" })),
    datasets: [
      {
        label: "Stress Levels",
        data: logs.map(l => l.stress_level),
        borderColor: "#00f2fe",
        backgroundColor: "rgba(0, 242, 254, 0.1)",
        tension: 0.3,
        fill: true
      }
    ]
  };

  const chartOptions = {
    responsive: true,
    scales: {
      y: {
        min: 1,
        max: 10,
        ticks: { color: "rgba(255, 255, 255, 0.6)" },
        grid: { color: "rgba(255, 255, 255, 0.05)" }
      },
      x: {
        ticks: { color: "rgba(255, 255, 255, 0.6)" },
        grid: { display: false }
      }
    },
    plugins: {
      legend: { display: false }
    }
  };

  return (
    <AppLayout>
      <div className="max-w-5xl mx-auto grid grid-cols-1 lg:grid-cols-3 gap-8">
        
        {/* Left Column: Input Panel */}
        <div className="lg:col-span-1 space-y-6">
          <motion.div 
            initial={{ opacity: 0, x: -20 }}
            animate={{ opacity: 1, x: 0 }}
            className="glass-panel rounded-2xl p-6"
          >
            <h3 className="text-lg font-bold mb-4 flex items-center gap-2">
              <Smile className="h-5 w-5 text-[#00f2fe]" />
              Check-in Your Mood
            </h3>

            <form onSubmit={handleSubmit} className="space-y-4">
              <div>
                <label className="text-white/60 text-xs block mb-2 font-semibold">How do you feel?</label>
                <div className="grid grid-cols-5 gap-2">
                  {moods.map((m) => (
                    <button
                      key={m.label}
                      type="button"
                      onClick={() => setSelectedMood(m.label)}
                      className={`p-3 rounded-xl text-center text-xl transition cursor-pointer ${
                        selectedMood === m.label 
                          ? "bg-[#00f2fe]/20 border border-[#00f2fe]/50 scale-110" 
                          : "bg-white/5 border border-white/5 hover:bg-white/10"
                      }`}
                    >
                      <div>{m.emoji}</div>
                      <div className="text-[9px] text-white/50 mt-1 font-medium">{m.label.substring(0, 4)}</div>
                    </button>
                  ))}
                </div>
              </div>

              <div>
                <label className="text-white/60 text-xs block mb-2 font-semibold">Stress Rating: {stressInput}/10</label>
                <input
                  type="range"
                  min="1"
                  max="10"
                  value={stressInput}
                  onChange={(e) => setStressInput(Number(e.target.value))}
                  className="w-full h-1 bg-white/10 rounded-lg appearance-none cursor-pointer accent-[#00f2fe]"
                />
              </div>

              <div>
                <label className="text-white/60 text-xs block mb-1 font-semibold">Reflection Notes (Optional)</label>
                <textarea
                  value={notesInput}
                  onChange={(e) => setNotesInput(e.target.value)}
                  className="glass-input w-full p-3 text-sm h-20"
                  placeholder="Any details on what triggered this mood?"
                />
              </div>

              <button
                type="submit"
                disabled={loading}
                className="w-full bg-gradient-to-r from-[#00f2fe] to-[#4facfe] hover:brightness-110 text-black font-bold p-3 rounded-lg transition text-sm cursor-pointer"
              >
                Log Mood Entry
              </button>
            </form>
          </motion.div>
        </div>

        {/* Right Column: Analytics & Charts */}
        <div className="lg:col-span-2 space-y-6">
          <motion.div 
            initial={{ opacity: 0, y: 20 }}
            animate={{ opacity: 1, y: 0 }}
            className="glass-panel rounded-2xl p-6"
          >
            <h3 className="text-lg font-bold mb-4 flex items-center gap-2">
              <BarChart2 className="h-5 w-5 text-[#00f2fe]" />
              Stress Levels History
            </h3>
            {logs.length > 1 ? (
              <div className="h-64 flex items-center justify-center">
                <Line data={chartData} options={chartOptions} />
              </div>
            ) : (
              <div className="h-64 flex items-center justify-center text-white/40 text-sm">
                Log more check-ins to unlock charts.
              </div>
            )}
          </motion.div>

          {/* Historical Logs List */}
          <div className="glass-panel rounded-2xl p-6">
            <h3 className="text-lg font-bold mb-4 flex items-center gap-2">
              <Calendar className="h-5 w-5 text-[#00f2fe]" />
              History Timeline
            </h3>
            <div className="space-y-3 max-h-60 overflow-y-auto pr-2">
              {logs.map((log, idx) => {
                const moodObj = moods.find(m => m.label === log.mood);
                return (
                  <div key={idx} className="bg-white/5 border border-white/10 rounded-xl p-4 flex justify-between items-start text-sm">
                    <div>
                      <div className="flex items-center gap-2">
                        <span className="text-lg">{moodObj?.emoji || "🌊"}</span>
                        <span className="font-bold">{log.mood}</span>
                        <span className="text-xs text-white/40">{new Date(log.timestamp * 1000).toLocaleDateString()}</span>
                      </div>
                      {log.notes && <p className="text-white/70 text-xs mt-2 italic">"{log.notes}"</p>}
                    </div>
                    <div className="bg-white/10 rounded-lg px-2 py-1 text-xs">
                      Stress: <span className="font-bold text-[#00f2fe]">{log.stress_level}</span>
                    </div>
                  </div>
                );
              })}
            </div>
          </div>
        </div>

      </div>
    </AppLayout>
  );
}
