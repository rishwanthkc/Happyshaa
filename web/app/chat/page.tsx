"use client";

import { useState, useEffect, useRef } from "react";
import AppLayout from "../../components/AppLayout";
import api from "../../lib/api";
import { motion, AnimatePresence } from "framer-motion";
import { Send, Mic, Volume2, Copy, Share2, CornerDownLeft } from "lucide-react";
import ReactMarkdown from "react-markdown";

interface Message {
  sender: "user" | "ai";
  text: string;
}

export default function ChatPage() {
  const [messages, setMessages] = useState<Message[]>([
    { sender: "ai", text: "Hello! I am your Aura CBT Wellness companion. How are you feeling today?" }
  ]);
  const [inputVal, setInputVal] = useState("");
  const [loading, setLoading] = useState(false);
  const [isListening, setIsListening] = useState(false);
  const feedEndRef = useRef<HTMLDivElement>(null);

  const scrollToBottom = () => {
    feedEndRef.current?.scrollIntoView({ behavior: "smooth" });
  };

  useEffect(() => {
    scrollToBottom();
  }, [messages]);

  const handleSend = async (e?: React.FormEvent) => {
    e?.preventDefault();
    if (!inputVal.trim() || loading) return;

    const userText = inputVal;
    setMessages((prev) => [...prev, { sender: "user", text: userText }]);
    setInputVal("");
    setLoading(true);

    try {
      const response = await api.post("/api/v1/chat", {
        message: userText,
        current_mood: "Calm"
      });

      // Stream simulation for premium typewriter feel
      const fullResponse = response.data.reply;
      setMessages((prev) => [...prev, { sender: "ai", text: "" }]);

      let currentText = "";
      const words = fullResponse.split(" ");
      let wordIdx = 0;

      const interval = setInterval(() => {
        if (wordIdx < words.length) {
          currentText += (wordIdx === 0 ? "" : " ") + words[wordIdx];
          setMessages((prev) => {
            const copy = [...prev];
            copy[copy.length - 1] = { sender: "ai", text: currentText };
            return copy;
          });
          wordIdx++;
        } else {
          clearInterval(interval);
          setLoading(false);
        }
      }, 50);

    } catch (e) {
      setMessages((prev) => [...prev, { sender: "ai", text: "I'm having trouble connecting right now, but I'm here for you. Take a deep breath." }]);
      setLoading(false);
    }
  };

  // HTML5 SpeechRecognition for Voice Input
  const startSpeechRecognition = () => {
    const SpeechRecognition = (window as any).SpeechRecognition || (window as any).webkitSpeechRecognition;
    if (!SpeechRecognition) {
      alert("Voice input is not supported in this browser.");
      return;
    }

    const recognition = new SpeechRecognition();
    recognition.continuous = false;
    recognition.lang = "en-US";
    recognition.interimResults = false;

    recognition.onstart = () => {
      setIsListening(true);
    };

    recognition.onresult = (event: any) => {
      const transcript = event.results[0][0].transcript;
      setInputVal(transcript);
    };

    recognition.onerror = () => {
      setIsListening(false);
    };

    recognition.onend = () => {
      setIsListening(false);
    };

    recognition.start();
  };

  // SpeechSynthesis for Text To Speech
  const playSpeech = (text: string) => {
    if (typeof window !== "undefined" && window.speechSynthesis) {
      window.speechSynthesis.cancel();
      const utterance = new SpeechSynthesisUtterance(text);
      utterance.rate = 1.0;
      window.speechSynthesis.speak(utterance);
    }
  };

  return (
    <AppLayout>
      <div className="max-w-4xl mx-auto flex flex-col h-[calc(100vh-80px)] md:h-[calc(100vh-64px)]">
        {/* Chat Feed */}
        <div className="flex-1 overflow-y-auto pr-2 space-y-4">
          <AnimatePresence initial={false}>
            {messages.map((msg, idx) => (
              <motion.div
                key={idx}
                initial={{ opacity: 0, y: 10 }}
                animate={{ opacity: 1, y: 0 }}
                className={`flex ${msg.sender === "user" ? "justify-end" : "justify-start"}`}
              >
                <div 
                  className={`max-w-[80%] rounded-2xl p-4 glass-panel ${
                    msg.sender === "user" 
                      ? "bg-gradient-to-br from-[#00f2fe]/15 to-[#4facfe]/15 border-[#00f2fe]/30" 
                      : "bg-white/5"
                  }`}
                >
                  <div className="prose prose-invert text-sm text-white/90 leading-relaxed">
                    <ReactMarkdown>{msg.text}</ReactMarkdown>
                  </div>
                  {msg.sender === "ai" && msg.text && (
                    <div className="flex gap-3 mt-3 border-t border-white/5 pt-2 text-white/40">
                      <button 
                        onClick={() => playSpeech(msg.text)} 
                        className="hover:text-white transition flex items-center gap-1 text-xs cursor-pointer"
                      >
                        <Volume2 className="h-4 w-4" /> Listen
                      </button>
                      <button 
                        onClick={() => {
                          navigator.clipboard.writeText(msg.text);
                          alert("Response copied!");
                        }} 
                        className="hover:text-white transition flex items-center gap-1 text-xs cursor-pointer"
                      >
                        <Copy className="h-4 w-4" /> Copy
                      </button>
                    </div>
                  )}
                </div>
              </motion.div>
            ))}
          </AnimatePresence>
          <div ref={feedEndRef} />
        </div>

        {/* Input Bar */}
        <form onSubmit={handleSend} className="relative mt-4 mb-2">
          <input
            type="text"
            value={inputVal}
            onChange={(e) => setInputVal(e.target.value)}
            placeholder="Describe how you are feeling..."
            className="glass-input w-full p-4 pr-32 text-sm rounded-xl"
            disabled={loading}
          />
          <div className="absolute right-3 top-1/2 -translate-y-1/2 flex items-center gap-2">
            <button
              type="button"
              onClick={startSpeechRecognition}
              className={`p-2 rounded-lg hover:bg-white/5 transition cursor-pointer ${isListening ? "text-red-400 animate-pulse" : "text-white/60"}`}
            >
              <Mic className="h-5 w-5" />
            </button>
            <button
              type="submit"
              disabled={loading || !inputVal.trim()}
              className="bg-gradient-to-r from-[#00f2fe] to-[#4facfe] hover:brightness-110 disabled:opacity-50 text-black p-2.5 rounded-lg transition cursor-pointer"
            >
              <Send className="h-4 w-4" />
            </button>
          </div>
        </form>
      </div>
    </AppLayout>
  );
}
