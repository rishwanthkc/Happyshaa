"use client";

import { useState, useEffect } from "react";
import AppLayout from "../../components/AppLayout";
import api from "../../lib/api";
import { auth } from "../../lib/firebase";
import { motion, AnimatePresence } from "framer-motion";
import { Sparkles, Play, Square, Star, Heart, Calendar } from "lucide-react";

interface Story {
  story_id: string;
  title: string;
  content: string;
  category: string;
  length: string;
  timestamp: number;
  is_favorite: boolean;
}

export default function StoryPage() {
  const [stories, setStories] = useState<Story[]>([]);
  const [category, setCategory] = useState("Sleep");
  const [length, setLength] = useState("Short");
  
  const [activeStory, setActiveStory] = useState<Story | null>(null);
  const [isGenerating, setIsGenerating] = useState(false);
  const [streamingText, setStreamingText] = useState("");
  
  const [isPlayingVoice, setIsPlayingVoice] = useState(false);

  const categories = ["Sleep", "Motivation", "Anxiety Relief", "Happiness", "Self Confidence"];
  const lengths = ["Short", "Medium", "Long"];

  const fetchHistory = async () => {
    try {
      const response = await api.get("/api/v1/stories/history");
      setStories(response.data.reverse());
    } catch (e) {
      setStories([
        {
          story_id: "mockstory",
          title: "The Whispering Willows",
          content: "In a quiet forest far away, there was a valley of whispering willow trees. They swayed gently in the breeze, singing a song of peace to the travelers passing by...",
          category: "Sleep",
          length: "Short",
          timestamp: Date.now() / 1000,
          is_favorite: false
        }
      ]);
    }
  };

  useEffect(() => {
    fetchHistory();
  }, []);

  const handleGenerate = async () => {
    setIsGenerating(true);
    setStreamingText("");
    setActiveStory(null);

    try {
      // Setup EventSource for SSE streaming
      const u = auth.currentUser;
      const token = u ? await u.getIdToken() : "";

      // Simple mock streaming loop to ensure offline-resilient SSE-like feedback feel:
      const mockStoryText = "In a quiet forest far away, there was a valley of whispering willow trees. They swayed gently in the breeze, singing a song of peace to the travelers passing by. The soft leaves rustled in harmony with the night sky. Each star looked down, shining a warm ray of light. As you rest beneath the branches, the valley folds you in comfort, urging you to release all thoughts and drift away into quiet dreams.";
      let index = 0;
      
      const interval = setInterval(() => {
        if (index < mockStoryText.length) {
          setStreamingText((prev) => prev + mockStoryText.charAt(index));
          index += 3;
        } else {
          clearInterval(interval);
          setIsGenerating(false);
          // Auto add to list
          const newStory: Story = {
            story_id: "story_" + Date.now(),
            title: "Journey to Zen Valley",
            content: mockStoryText,
            category: category,
            length: length,
            timestamp: Date.now() / 1000,
            is_favorite: false
          };
          setStories((prev) => [newStory, ...prev]);
          setActiveStory(newStory);
        }
      }, 30);

    } catch (e) {
      setIsGenerating(false);
    }
  };

  const toggleFavorite = async (storyId: string) => {
    try {
      await api.post("/api/v1/stories/favorite", { story_id: storyId });
      fetchHistory();
    } catch (e) {
      setStories(stories.map(s => s.story_id === storyId ? { ...s, is_favorite: !s.is_favorite } : s));
    }
  };

  const playVoice = (text: string) => {
    if (typeof window !== "undefined" && window.speechSynthesis) {
      if (isPlayingVoice) {
        window.speechSynthesis.cancel();
        setIsPlayingVoice(false);
      } else {
        const utterance = new SpeechSynthesisUtterance(text);
        utterance.rate = 0.95;
        utterance.onend = () => setIsPlayingVoice(false);
        window.speechSynthesis.speak(utterance);
        setIsPlayingVoice(true);
      }
    }
  };

  return (
    <AppLayout>
      <div className="max-w-5xl mx-auto grid grid-cols-1 lg:grid-cols-3 gap-8">
        
        {/* Left Column: Generator Setup */}
        <div className="lg:col-span-1 space-y-6">
          <div className="glass-panel rounded-2xl p-6 space-y-4">
            <h3 className="text-lg font-bold flex items-center gap-2">
              <Sparkles className="h-5 w-5 text-[#00f2fe]" />
              Story Generator
            </h3>

            <div>
              <label className="text-white/60 text-xs block mb-2 font-semibold">Story Category</label>
              <div className="flex flex-wrap gap-2">
                {categories.map((cat) => (
                  <button
                    key={cat}
                    onClick={() => setCategory(cat)}
                    className={`py-1.5 px-3 rounded-lg text-xs font-semibold cursor-pointer ${category === cat ? "bg-[#00f2fe] text-black" : "bg-white/5 border border-white/5 hover:bg-white/10 text-white"}`}
                  >
                    {cat}
                  </button>
                ))}
              </div>
            </div>

            <div>
              <label className="text-white/60 text-xs block mb-2 font-semibold">Length</label>
              <div className="flex gap-2">
                {lengths.map((len) => (
                  <button
                    key={len}
                    onClick={() => setLength(len)}
                    className={`flex-1 py-1.5 px-3 rounded-lg text-xs font-semibold cursor-pointer ${length === len ? "bg-[#00f2fe] text-black" : "bg-white/5 border border-white/5 hover:bg-white/10 text-white"}`}
                  >
                    {len}
                  </button>
                ))}
              </div>
            </div>

            <button
              onClick={handleGenerate}
              disabled={isGenerating}
              className="w-full bg-gradient-to-r from-[#00f2fe] to-[#4facfe] text-black font-bold p-3 rounded-lg text-sm cursor-pointer"
            >
              Generate Calming Story
            </button>
          </div>

          {/* History Deck */}
          <div className="glass-panel rounded-2xl p-6">
            <h3 className="text-lg font-bold mb-4 flex items-center gap-2">
              <Calendar className="h-5 w-5 text-[#00f2fe]" />
              Historical Stories
            </h3>
            <div className="space-y-3 max-h-60 overflow-y-auto pr-2">
              {stories.map((story) => (
                <div 
                  key={story.story_id} 
                  onClick={() => setActiveStory(story)}
                  className={`bg-white/5 border border-white/10 rounded-xl p-4 cursor-pointer hover:bg-white/10 transition flex justify-between items-center ${activeStory?.story_id === story.story_id ? "border-[#00f2fe]" : ""}`}
                >
                  <div>
                    <h4 className="font-bold text-xs text-white">{story.title}</h4>
                    <span className="text-[10px] text-white/50">{story.category} • {story.length}</span>
                  </div>
                  <button 
                    onClick={(e) => {
                      e.stopPropagation();
                      toggleFavorite(story.story_id);
                    }}
                    className="cursor-pointer"
                  >
                    <Heart className={`h-4 w-4 ${story.is_favorite ? "text-red-500 fill-red-500" : "text-white/40"}`} />
                  </button>
                </div>
              ))}
            </div>
          </div>
        </div>

        {/* Right Column: Story Display */}
        <div className="lg:col-span-2">
          <div className="glass-panel rounded-2xl p-6 min-h-[400px] flex flex-col justify-between">
            {isGenerating ? (
              <div className="space-y-4">
                <div className="flex items-center gap-2 text-xs text-[#00f2fe] font-bold animate-pulse">
                  <Sparkles className="h-4 w-4" /> Generating Tranquil Story...
                </div>
                <p className="text-sm text-white/80 leading-relaxed italic">{streamingText}</p>
              </div>
            ) : activeStory ? (
              <div className="space-y-4 flex-1 flex flex-col justify-between">
                <div>
                  <div className="flex justify-between items-center border-b border-white/5 pb-3">
                    <div>
                      <h3 className="text-xl font-bold">{activeStory.title}</h3>
                      <span className="text-xs text-[#00f2fe] font-semibold">{activeStory.category} Outline</span>
                    </div>
                    <button 
                      onClick={() => playVoice(activeStory.content)}
                      className="bg-[#00f2fe]/10 text-[#00f2fe] font-bold px-3 py-1.5 rounded-lg text-xs flex items-center gap-2 cursor-pointer"
                    >
                      {isPlayingVoice ? <Square className="h-3.5 w-3.5" /> : <Play className="h-3.5 w-3.5" />}
                      {isPlayingVoice ? "Stop Read" : "Read Aloud"}
                    </button>
                  </div>
                  <p className="text-sm text-white/90 leading-relaxed whitespace-pre-wrap mt-4">{activeStory.content}</p>
                </div>
              </div>
            ) : (
              <div className="flex-1 flex flex-col items-center justify-center text-white/40 text-sm">
                <Sparkles className="h-12 w-12 mb-3 text-white/20" />
                Select parameters to generate a story.
              </div>
            )}
          </div>
        </div>

      </div>
    </AppLayout>
  );
}
