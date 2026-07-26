"use client";

import { useEffect } from "react";
import AppLayout from "../../components/AppLayout";
import { useMusicStore, SongTrack } from "../../store/musicStore";
import { motion } from "framer-motion";
import { 
  Play, Pause, SkipForward, SkipBack, Volume2, 
  Clock, Heart, ListMusic, Sparkles, Music 
} from "lucide-react";

export default function MusicPage() {
  const {
    songs,
    currentTrack,
    isPlaying,
    playbackPosition,
    duration,
    sleepTimerMinutes,
    fetchSongs,
    playTrack,
    pause,
    resume,
    next,
    prev,
    seek,
    setSleepTimer,
    toggleFavorite
  } = useMusicStore();

  useEffect(() => {
    fetchSongs();
  }, [fetchSongs]);

  const handleSeekChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    seek(Number(e.target.value));
  };

  const formatTime = (secs: number) => {
    const mins = Math.floor(secs / 60);
    const remainder = Math.floor(secs % 60);
    return `${mins}:${remainder.toString().padStart(2, "0")}`;
  };

  return (
    <AppLayout>
      <div className="max-w-5xl mx-auto grid grid-cols-1 lg:grid-cols-3 gap-8">
        
        {/* Left Column: Playlist Feed */}
        <div className="lg:col-span-2 space-y-6">
          <div className="glass-panel rounded-2xl p-6">
            <h3 className="text-lg font-bold mb-4 flex items-center gap-2">
              <ListMusic className="h-5 w-5 text-[#00f2fe]" />
              Zen Soundscapes & Lofi
            </h3>

            <div className="space-y-2 max-h-[500px] overflow-y-auto pr-2">
              {songs.map((song) => {
                const isActive = currentTrack?.songId === song.songId;
                return (
                  <div
                    key={song.songId}
                    onClick={() => playTrack(song)}
                    className={`bg-white/5 border border-white/10 rounded-xl p-4 cursor-pointer hover:bg-white/10 transition flex justify-between items-center ${
                      isActive ? "border-[#00f2fe] bg-[#00f2fe]/5" : ""
                    }`}
                  >
                    <div className="flex items-center gap-4">
                      <button className="bg-[#00f2fe]/10 p-2 rounded-lg">
                        {isActive && isPlaying ? (
                          <Pause className="h-4 w-4 text-[#00f2fe]" />
                        ) : (
                          <Play className="h-4 w-4 text-[#00f2fe]" />
                        )}
                      </button>
                      <div>
                        <h4 className={`font-bold text-sm ${isActive ? "text-[#00f2fe]" : "text-white"}`}>{song.title}</h4>
                        <span className="text-xs text-white/50">{song.artist} • {song.category}</span>
                      </div>
                    </div>

                    <button
                      onClick={(e) => {
                        e.stopPropagation();
                        toggleFavorite(song);
                      }}
                      className="cursor-pointer"
                    >
                      <Heart className={`h-4 w-4 ${song.isFavorite ? "text-red-500 fill-red-500" : "text-white/40"}`} />
                    </button>
                  </div>
                );
              })}
            </div>
          </div>
        </div>

        {/* Right Column: Active Player controller */}
        <div className="lg:col-span-1 space-y-6">
          <div className="glass-panel rounded-2xl p-6 flex flex-col justify-between items-center text-center sticky top-24">
            
            <div className="space-y-4 w-full">
              <div className="w-48 h-48 rounded-2xl bg-gradient-to-tr from-[#00f2fe] to-[#4facfe] mx-auto flex items-center justify-center shadow-lg shadow-[#00f2fe]/15 relative overflow-hidden">
                <Music className="h-20 w-20 text-black/60" />
              </div>

              <div>
                <h3 className="text-lg font-bold truncate">{currentTrack?.title || "No Track Selected"}</h3>
                <span className="text-xs text-white/50">{currentTrack?.artist || "Aura Radio"}</span>
              </div>
            </div>

            {/* Slider Seek bar */}
            <div className="w-full mt-6 space-y-1">
              <input
                type="range"
                min="0"
                max={duration || 100}
                value={playbackPosition}
                onChange={handleSeekChange}
                className="w-full h-1 bg-white/10 rounded-lg appearance-none cursor-pointer accent-[#00f2fe]"
              />
              <div className="flex justify-between text-[10px] text-white/50">
                <span>{formatTime(playbackPosition)}</span>
                <span>{formatTime(duration)}</span>
              </div>
            </div>

            {/* Controller row */}
            <div className="flex items-center gap-6 mt-6">
              <button onClick={prev} className="p-2 text-white/60 hover:text-white transition cursor-pointer">
                <SkipBack className="h-6 w-6" />
              </button>
              <button 
                onClick={() => {
                  if (isPlaying) pause();
                  else if (currentTrack) resume();
                  else if (songs.length > 0) playTrack(songs[0]);
                }} 
                className="bg-gradient-to-r from-[#00f2fe] to-[#4facfe] text-black p-4 rounded-full hover:scale-105 transition shadow-md shadow-[#00f2fe]/20 cursor-pointer"
              >
                {isPlaying ? <Pause className="h-6 w-6" /> : <Play className="h-6 w-6" />}
              </button>
              <button onClick={next} className="p-2 text-white/60 hover:text-white transition cursor-pointer">
                <SkipForward className="h-6 w-6" />
              </button>
            </div>

            {/* Sleep Timer button */}
            <div className="w-full border-t border-white/5 pt-6 mt-6">
              <div className="flex items-center justify-between text-xs text-white/60 mb-3">
                <span className="flex items-center gap-1.5"><Clock className="h-4 w-4" /> Sleep Timer</span>
                <span>{sleepTimerMinutes > 0 ? `${sleepTimerMinutes}m left` : "Off"}</span>
              </div>
              <div className="grid grid-cols-4 gap-2">
                {[5, 15, 30, 0].map((min) => (
                  <button
                    key={min}
                    onClick={() => setSleepTimer(min)}
                    className={`py-1.5 px-2 rounded-lg text-xs font-semibold cursor-pointer ${
                      sleepTimerMinutes === min ? "bg-[#00f2fe] text-black" : "bg-white/5 hover:bg-white/10"
                    }`}
                  >
                    {min === 0 ? "Off" : `${min}m`}
                  </button>
                ))}
              </div>
            </div>

          </div>
        </div>

      </div>
    </AppLayout>
  );
}
