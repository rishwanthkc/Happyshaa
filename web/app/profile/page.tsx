"use client";

import AppLayout from "../../components/AppLayout";
import { useAuthStore } from "../../store/authStore";
import { motion } from "framer-motion";
import { Award, User, Star, ShieldCheck } from "lucide-react";

export default function ProfilePage() {
  const { profile } = useAuthStore();

  const badges = [
    { title: "Calm Mind", desc: "Completed 3 breathing pacing guides", icon: "🌊", unlocked: true },
    { title: "CBT Explorer", desc: "Written 5 daily reflections", icon: "🌸", unlocked: true },
    { title: "Aura Master", desc: "Achieved Level 5 status", icon: "👑", unlocked: false }
  ];

  return (
    <AppLayout>
      <div className="max-w-4xl mx-auto space-y-6">
        
        {/* User Card */}
        <div className="glass-panel rounded-2xl p-8 flex flex-col md:flex-row items-center gap-6">
          <div className="h-24 w-24 rounded-full bg-gradient-to-tr from-[#00f2fe] to-[#4facfe] flex items-center justify-center font-extrabold text-3xl text-black">
            {profile?.displayName.substring(0, 1).toUpperCase() || "A"}
          </div>
          <div className="text-center md:text-left space-y-2 flex-1">
            <h2 className="text-2xl font-extrabold">{profile?.displayName || "Aura Member"}</h2>
            <p className="text-sm text-white/50">{profile?.email}</p>
            <div className="flex justify-center md:justify-start gap-4 text-xs font-semibold text-white/80">
              <span className="flex items-center gap-1.5"><Star className="h-4 w-4 text-[#ffd700] fill-[#ffd700]" /> {profile?.coins || 0} Coins</span>
              <span className="flex items-center gap-1.5"><Award className="h-4 w-4 text-[#00f2fe]" /> {profile?.xp || 0} XP</span>
            </div>
          </div>
        </div>

        {/* Badges Grid */}
        <div className="glass-panel rounded-2xl p-6">
          <h3 className="text-lg font-bold mb-4 flex items-center gap-2">
            <ShieldCheck className="h-5 w-5 text-[#00f2fe]" />
            Unlocked Badges & Medals
          </h3>

          <div className="grid grid-cols-1 md:grid-cols-3 gap-4">
            {badges.map((badge, idx) => (
              <div 
                key={idx} 
                className={`bg-white/5 border border-white/10 rounded-xl p-4 flex items-center gap-4 transition hover:bg-white/10 ${
                  !badge.unlocked ? "opacity-40" : ""
                }`}
              >
                <div className="text-3xl">{badge.icon}</div>
                <div>
                  <h4 className="font-bold text-sm text-white">{badge.title}</h4>
                  <p className="text-xs text-white/50 mt-0.5">{badge.desc}</p>
                </div>
              </div>
            ))}
          </div>
        </div>

      </div>
    </AppLayout>
  );
}
