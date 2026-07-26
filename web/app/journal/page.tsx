"use client";

import { useState, useEffect } from "react";
import AppLayout from "../../components/AppLayout";
import api from "../../lib/api";
import { motion, AnimatePresence } from "framer-motion";
import { 
  BookOpen, Edit3, Mic, Square, Trash2, Volume2, 
  Sparkles, Calendar, TrendingUp 
} from "lucide-react";

interface JournalEntry {
  journal_id: string;
  title?: string;
  content: string;
  timestamp: number;
  detected_emotion: string;
  reflection: string;
  gratitude_highlights: string[];
  triggers: string[];
  audio_url?: string;
}

interface WeeklyReport {
  report_id: string;
  timestamp: number;
  dominant_mood: string;
  average_stress_level: number;
  gratitude_summary: string;
  self_care_plan: string[];
}

export default function JournalPage() {
  const [journals, setJournals] = useState<JournalEntry[]>([]);
  const [reports, setReports] = useState<WeeklyReport[]>([]);
  const [tabMode, setTabMode] = useState(0); // 0: Reflections, 1: Weekly Reports

  // Editor states
  const [isEditing, setIsEditing] = useState(false);
  const [titleInput, setTitleInput] = useState("");
  const [contentInput, setContentInput] = useState("");
  
  // Audio Recorder states
  const [isRecording, setIsRecording] = useState(false);
  const [mediaRecorder, setMediaRecorder] = useState<MediaRecorder | null>(null);
  const [recordedChunks, setRecordedChunks] = useState<Blob[]>([]);
  const [recordingSeconds, setRecordingSeconds] = useState(0);

  const [recentAnalysis, setRecentAnalysis] = useState<JournalEntry | null>(null);
  const [loading, setLoading] = useState(false);

  const fetchJournalLogs = async () => {
    try {
      const response = await api.get("/api/v1/journal");
      setJournals(response.data.reverse());

      const reportsResponse = await api.get("/api/v1/journal/weekly");
      setReports(reportsResponse.data.reverse());
    } catch (e) {
      // Mock Fallback
      setJournals([
        {
          journal_id: "mock1",
          title: "Evening Walk Reflection",
          content: "I took a nice long walk around the lake. The sunset was beautiful and I felt relaxed.",
          timestamp: (Date.now() / 1000) - 86400,
          detected_emotion: "Calm",
          reflection: "It sounds like you had a very grounding and peaceful evening. Connecting with nature is a great wellness step.",
          gratitude_highlights: ["Sunsets", "Grounding walk"],
          triggers: []
        }
      ]);
    }
  };

  useEffect(() => {
    fetchJournalLogs();
  }, []);

  // Voice note timer
  useEffect(() => {
    let interval: NodeJS.Timeout | null = null;
    if (isRecording) {
      interval = setInterval(() => {
        setRecordingSeconds((prev) => prev + 1);
      }, 1000);
    } else {
      setRecordingSeconds(0);
    }
    return () => {
      if (interval) clearInterval(interval);
    };
  }, [isRecording]);

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    if (!contentInput.trim()) return;
    setLoading(true);

    try {
      const response = await api.post("/api/v1/journal", {
        title: titleInput,
        content: contentInput,
        audio_url: null
      });
      setRecentAnalysis(response.data);
      setTitleInput("");
      setContentInput("");
      setIsEditing(false);
      fetchJournalLogs();
    } catch (e) {
      alert("Submission failed. Retrying...");
    } finally {
      setLoading(false);
    }
  };

  // HTML5 MediaRecorder voice journaling
  const startRecording = async () => {
    if (typeof window === "undefined" || !navigator.mediaDevices) {
      alert("Microphone recording is not supported in this browser.");
      return;
    }

    try {
      const stream = await navigator.mediaDevices.getUserMedia({ audio: true });
      const recorder = new MediaRecorder(stream);
      setMediaRecorder(recorder);
      setRecordedChunks([]);

      recorder.ondataavailable = (event) => {
        if (event.data.size > 0) {
          setRecordedChunks((prev) => [...prev, event.data]);
        }
      };

      recorder.onstop = async () => {
        setLoading(true);
        // Simulate uploading voice note and requesting summarization
        const mockAudioUrl = "https://storage.googleapis.com/auraai/voice_" + Date.now() + ".mp4";
        try {
          const response = await api.post("/api/v1/journal/voice/summarize", {
            audio_url: mockAudioUrl
          });
          
          // Pre-fill content with transcription and summary
          setContentInput(`Voice note transcription: ${response.data.transcription}\n\nSummary: ${response.data.summary}`);
          setIsEditing(true);
        } catch (e) {
          alert("Failed to summarize voice note.");
        } finally {
          setLoading(false);
        }
      };

      recorder.start();
      setIsRecording(true);
    } catch (e) {
      alert("Failed to access microphone.");
    }
  };

  const stopRecording = () => {
    if (mediaRecorder) {
      mediaRecorder.stop();
      setIsRecording(false);
    }
  };

  const triggerWeeklyReport = async () => {
    setLoading(true);
    try {
      await api.post("/api/v1/journal/weekly/generate");
      fetchJournalLogs();
    } catch (e) {
      alert("Need at least 1 journal entry from the past 7 days to compile a CBT report.");
    } finally {
      setLoading(false);
    }
  };

  return (
    <AppLayout>
      <div className="max-w-5xl mx-auto space-y-6">
        
        {/* Header Tabs */}
        <div className="flex justify-between items-center">
          <div className="flex gap-4">
            <button 
              onClick={() => setTabMode(0)}
              className={`text-lg font-bold border-b-2 pb-1 cursor-pointer transition ${tabMode === 0 ? "border-[#00f2fe] text-[#00f2fe]" : "border-transparent text-white/50"}`}
            >
              Daily Reflections
            </button>
            <button 
              onClick={() => setTabMode(1)}
              className={`text-lg font-bold border-b-2 pb-1 cursor-pointer transition ${tabMode === 1 ? "border-[#00f2fe] text-[#00f2fe]" : "border-transparent text-white/50"}`}
            >
              Weekly CBT Analytics
            </button>
          </div>

          {tabMode === 0 ? (
            <button
              onClick={() => setIsEditing(!isEditing)}
              className="bg-gradient-to-r from-[#00f2fe] to-[#4facfe] text-black font-bold px-4 py-2 rounded-lg text-sm flex items-center gap-2 cursor-pointer"
            >
              <Edit3 className="h-4 w-4" />
              Write Reflection
            </button>
          ) : (
            <button
              onClick={triggerWeeklyReport}
              disabled={loading}
              className="bg-white/10 hover:bg-white/15 border border-white/10 font-bold px-4 py-2 rounded-lg text-sm flex items-center gap-2 cursor-pointer"
            >
              <TrendingUp className="h-4 w-4 text-[#00f2fe]" />
              Generate Weekly Summary
            </button>
          )}
        </div>

        {/* Reflection composer Card */}
        {isEditing && tabMode === 0 && (
          <motion.div 
            initial={{ opacity: 0, y: -10 }}
            animate={{ opacity: 1, y: 0 }}
            className="glass-panel rounded-2xl p-6 space-y-4"
          >
            <div className="flex justify-between items-center">
              <h3 className="font-bold">New Reflection</h3>
              {/* Voice record action */}
              {!isRecording ? (
                <button 
                  onClick={startRecording}
                  className="bg-white/5 hover:bg-white/10 border border-white/5 text-xs text-white/80 font-bold py-1.5 px-3 rounded-lg flex items-center gap-2 cursor-pointer"
                >
                  <Mic className="h-4 w-4 text-red-400" /> Speak Reflection
                </button>
              ) : (
                <button 
                  onClick={stopRecording}
                  className="bg-red-500/20 border border-red-500/30 text-xs text-red-400 font-bold py-1.5 px-3 rounded-lg flex items-center gap-2 animate-pulse cursor-pointer"
                >
                  <Square className="h-4 w-4" /> Stop ({recordingSeconds}s)
                </button>
              )}
            </div>

            <form onSubmit={handleSubmit} className="space-y-4">
              <input
                type="text"
                value={titleInput}
                onChange={(e) => setTitleInput(e.target.value)}
                placeholder="Title (Optional)"
                className="glass-input w-full p-3 text-sm"
              />
              <textarea
                value={contentInput}
                onChange={(e) => setContentInput(e.target.value)}
                placeholder="What is on your mind today? Let it out..."
                required
                className="glass-input w-full p-3 text-sm h-36"
              />
              <div className="flex gap-3 justify-end">
                <button 
                  type="button" 
                  onClick={() => setIsEditing(false)}
                  className="text-white/60 text-sm hover:text-white px-4 cursor-pointer"
                >
                  Cancel
                </button>
                <button
                  type="submit"
                  disabled={loading}
                  className="bg-gradient-to-r from-[#00f2fe] to-[#4facfe] text-black font-bold py-2 px-5 rounded-lg text-sm cursor-pointer"
                >
                  Analyze & Commit
                </button>
              </div>
            </form>
          </motion.div>
        )}

        {/* Reflections List Feed */}
        {tabMode === 0 && (
          <div className="space-y-6">
            {journals.length === 0 ? (
              <div className="text-center py-12 text-white/40 text-sm">
                Write down your thoughts and receive empathetic AI reflections.
              </div>
            ) : (
              journals.map((entry) => (
                <motion.div 
                  key={entry.journal_id}
                  className="glass-panel rounded-2xl p-6 space-y-4"
                >
                  <div className="flex justify-between items-start">
                    <div>
                      <h4 className="font-bold text-lg text-white">{entry.title || "Daily Reflection"}</h4>
                      <span className="text-xs text-white/45">{new Date(entry.timestamp * 1000).toLocaleString()}</span>
                    </div>
                    <div className="bg-white/5 border border-white/10 rounded-lg px-2 py-1 text-xs text-[#00f2fe] font-bold">
                      {entry.detected_emotion}
                    </div>
                  </div>

                  <p className="text-sm text-white/80 leading-relaxed whitespace-pre-wrap">{entry.content}</p>

                  {/* Reflection feedback box */}
                  <div className="bg-[#00f2fe]/5 border border-[#00f2fe]/10 rounded-xl p-4">
                    <h5 className="text-xs text-[#00f2fe] font-extrabold flex items-center gap-1.5 uppercase tracking-wider mb-2">
                      <Sparkles className="h-4 w-4" /> Aura Compassionate Reflection
                    </h5>
                    <p className="text-xs text-white/80 leading-relaxed italic">"{entry.reflection}"</p>
                  </div>
                </motion.div>
              ))
            )}
          </div>
        )}

        {/* Weekly CBT Reports List */}
        {tabMode === 1 && (
          <div className="space-y-6">
            {reports.length === 0 ? (
              <div className="text-center py-12 text-white/40 text-sm">
                Compile weekly summaries after logging reflections.
              </div>
            ) : (
              reports.map((report) => (
                <div key={report.report_id} className="glass-panel rounded-2xl p-6 space-y-4">
                  <div className="flex justify-between items-center border-b border-white/5 pb-3">
                    <span className="text-sm font-extrabold text-[#00f2fe]">CBT PROGRESS SUMMARY</span>
                    <span className="text-xs text-white/40">{new Date(report.timestamp * 1000).toLocaleDateString()}</span>
                  </div>

                  <div className="grid grid-cols-2 gap-4">
                    <div>
                      <span className="text-xs text-white/50 block">Dominant Mood</span>
                      <span className="font-bold text-white text-base">{report.dominant_mood}</span>
                    </div>
                    <div>
                      <span className="text-xs text-white/50 block">Avg Stress Level</span>
                      <span className="font-bold text-white text-base">{report.average_stress_level.toFixed(2)}/10</span>
                    </div>
                  </div>

                  <div>
                    <span className="text-xs text-white/50 block mb-1">Gratitude Summary</span>
                    <p className="text-xs text-white/80 leading-relaxed">{report.gratitude_summary}</p>
                  </div>

                  <div>
                    <span className="text-xs text-[#ffd700] font-bold block mb-2">Your Self-Care Strategy Plan</span>
                    <ul className="space-y-2">
                      {report.self_care_plan.map((item, idx) => (
                        <li key={idx} className="bg-white/5 border border-white/5 rounded-lg p-2.5 text-xs text-white/90">
                          {item}
                        </li>
                      ))}
                    </ul>
                  </div>
                </div>
              ))
            )}
          </div>
        )}

        {/* Analytics Congrats popup */}
        {recentAnalysis && (
          <div className="fixed inset-0 bg-black/60 backdrop-blur-sm z-50 flex items-center justify-center p-4">
            <div className="glass-panel rounded-2xl max-w-md w-full p-6 space-y-4">
              <h3 className="text-lg font-bold text-gradient-cyan">Aura Entry Analytics</h3>
              <div>
                <span className="text-xs text-white/50">Detected Emotion</span>
                <p className="font-bold text-base text-[#00f2fe]">{recentAnalysis.detected_emotion}</p>
              </div>
              <div>
                <span className="text-xs text-white/50 block mb-1">Empathetic Response</span>
                <p className="text-xs text-white/90 leading-relaxed italic">"{recentAnalysis.reflection}"</p>
              </div>
              <button 
                onClick={() => setRecentAnalysis(null)}
                className="w-full bg-[#00f2fe] hover:brightness-110 text-black font-bold p-3 rounded-lg text-sm cursor-pointer"
              >
                Excellent
              </button>
            </div>
          </div>
        )}

      </div>
    </AppLayout>
  );
}
