"use client";

import AppLayout from "../../components/AppLayout";
import { useAuthStore } from "../../store/authStore";
import { useRouter } from "next/navigation";
import { motion } from "framer-motion";
import { 
  Smile, MessageSquare, BookOpen, Wind, Music, Award, Coins, Flame 
} from "lucide-react";

export default function DashboardPage() {
  const router = useRouter();
  const { profile } = useAuthStore();

  const quickActions = [
    { name: "AI Therapy Chat", desc: "Speak with your CBT guide", icon: MessageSquare, href: "/chat", color: "from-blue-500/20 to-cyan-500/20" },
    { name: "Self Reflection", desc: "Write in your daily journal", icon: BookOpen, href: "/journal", color: "from-purple-500/20 to-pink-500/20" },
    { name: "Box Breathing", desc: "Decompress stress cycles", icon: Wind, href: "/meditation", color: "from-emerald-500/20 to-teal-500/20" },
    { name: "Zen Playlists", desc: "Listen to relaxing soundscapes", icon: Music, href: "/music", color: "from-amber-500/20 to-orange-500/20" }
  ];

  return (
    <AppLayout>
      <div className="max-w-5xl mx-auto space-y-8">
        {/* Welcome Hero Banner */}
        <motion.div 
          initial={{ opacity: 0, y: 15 }}
          animate={{ opacity: 1, y: 0 }}
          className="glass-panel rounded-2xl p-8 relative overflow-hidden"
        >
          <div className="absolute right-0 top-0 translate-x-10 -translate-y-10 w-48 h-48 bg-[#00f2fe]/10 rounded-full blur-3xl" />
          <div className="flex items-center gap-4">
            <div className="bg-[#00f2fe]/10 p-4 rounded-full">
              <Smile className="h-10 w-10 text-[#00f2fe]" />
            </div>
            <div>
              <h2 className="text-2xl md:text-3xl font-extrabold tracking-tight">Welcome, {profile?.displayName || "Aura Member"}!</h2>
              <p className="text-white/60 text-sm mt-1">Take a moment to check-in on your cognitive wellbeing today.</p>
            </div>
          </div>
        </motion.div>

        {/* Stats Grid */}
        <div className="grid grid-cols-2 lg:grid-cols-4 gap-4">
          <CardStat title="User Balance" value={`${profile?.coins || 0} Coins`} icon={Coins} color="text-yellow-400" />
          <CardStat title="Total Progress" value={`${profile?.xp || 0} XP`} icon={Award} color="text-cyan-400" />
          <CardStat title="Day Streak" value="3 Days" icon={Flame} color="text-orange-400" />
          <CardStat title="Current Level" value={`Level ${Math.floor((profile?.xp || 0) / 100) + 1}`} icon={Smile} color="text-[#00f2fe]" />
        </div>

        {/* Daily Recommendations */}
        <div className="glass-panel rounded-2xl p-6">
          <h3 className="text-lg font-bold mb-4 flex items-center gap-2">
            <Award className="h-5 w-5 text-[#00f2fe]" />
            Today's CBT Self-Care Recommendations
          </h3>
          <ul className="space-y-3">
            <li className="bg-white/5 border border-white/10 p-4 rounded-xl text-sm flex justify-between items-center">
              <span>Write 1 journal entry detailing a positive thing that occurred today.</span>
              <span className="text-xs text-[#00f2fe] font-bold">+10 XP</span>
            </li>
            <li className="bg-white/5 border border-white/10 p-4 rounded-xl text-sm flex justify-between items-center">
              <span>Complete a 4-7-8 deep breathing session for 2 minutes.</span>
              <span className="text-xs text-[#00f2fe] font-bold">+15 XP</span>
            </li>
            <li className="bg-white/5 border border-white/10 p-4 rounded-xl text-sm flex justify-between items-center">
              <span>Play one level of Memory Match cards grid.</span>
              <span className="text-xs text-[#00f2fe] font-bold">+10 Coins</span>
            </li>
          </ul>
        </div>

        {/* Quick Actions Grid */}
        <div>
          <h3 className="text-lg font-bold mb-4">Wellness Quick Actions</h3>
          <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-4">
            {quickActions.map((action, idx) => {
              const Icon = action.icon;
              return (
                <motion.div
                  key={action.name}
                  whileHover={{ scale: 1.03 }}
                  onClick={() => router.push(action.href)}
                  className={`glass-panel border-l-4 border-l-[#00f2fe] rounded-xl p-5 cursor-pointer flex flex-col justify-between h-40 bg-gradient-to-br ${action.color}`}
                >
                  <div className="bg-white/10 p-3 rounded-lg w-fit">
                    <Icon className="h-6 w-6 text-white" />
                  </div>
                  <div>
                    <h4 className="font-bold text-sm text-white">{action.name}</h4>
                    <p className="text-xs text-white/50 mt-1">{action.desc}</p>
                  </div>
                </motion.div>
              );
            })}
          </div>
        </div>
      </div>
    </AppLayout>
  );
}

function CardStat({ title, value, icon: Icon, color }: { title: string; value: string; icon: any; color: string }) {
  return (
    <div className="glass-panel rounded-xl p-5 flex items-center justify-between">
      <div>
        <span className="text-xs text-white/50 font-semibold">{title}</span>
        <h4 className="text-xl font-extrabold mt-1">{value}</h4>
      </div>
      <div className={`bg-white/5 p-3 rounded-lg ${color}`}>
        <Icon className="h-6 w-6" />
      </div>
    </div>
  );
}
