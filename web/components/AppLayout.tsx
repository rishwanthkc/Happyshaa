"use client";

import { useEffect, useState } from "react";
import { useRouter, usePathname } from "next/navigation";
import { useAuthStore } from "../store/authStore";
import Link from "next/link";
import { 
  Home, MessageSquare, BarChart2, BookOpen, BookOpenCheck, 
  Wind, Music, Users, Gamepad2, Settings, LogOut, Star, Award, Menu, X 
} from "lucide-react";

const navItems = [
  { name: "Dashboard", href: "/dashboard", icon: Home },
  { name: "AI Chat", href: "/chat", icon: MessageSquare },
  { name: "Mood Tracker", href: "/mood", icon: BarChart2 },
  { name: "Journal", href: "/journal", icon: BookOpen },
  { name: "Story Generator", href: "/story", icon: BookOpenCheck },
  { name: "Meditation", href: "/meditation", icon: Wind },
  { name: "Music Player", href: "/music", icon: Music },
  { name: "Support Circle", href: "/contacts", icon: Users },
  { name: "Game Center", href: "/games", icon: Gamepad2 },
  { name: "Settings", href: "/settings", icon: Settings }
];

export default function AppLayout({ children }: { children: React.ReactNode }) {
  const router = useRouter();
  const pathname = usePathname();
  const { isAuthenticated, profile, signOut } = useAuthStore();
  const [mobileOpen, setMobileOpen] = useState(false);

  useEffect(() => {
    if (!isAuthenticated) {
      router.push("/login");
    }
  }, [isAuthenticated, router]);

  if (!isAuthenticated) return null;

  return (
    <div className="flex min-h-screen bg-transparent">
      {/* Desktop Sidebar */}
      <aside className="hidden md:flex flex-col w-64 glass-panel border-r border-white/5 p-6 h-screen sticky top-0">
        <div className="mb-8 flex items-center gap-3">
          <img src="/logo.jpg" alt="Happyshaa Logo" className="w-10 h-10 rounded-lg shadow-sm border border-white/10" />
          <div>
            <h1 className="text-xl font-extrabold text-gradient-cyan tracking-tight">Happyshaa</h1>
            <p className="text-[9px] text-white/50 font-bold uppercase tracking-wider">Wellness Ecosystem</p>
          </div>
        </div>

        {/* User Balance card */}
        {profile && (
          <div className="bg-white/5 border border-white/10 rounded-xl p-4 mb-6">
            <div className="flex items-center gap-2 mb-2">
              <Award className="h-4 w-4 text-[#00f2fe]" />
              <span className="text-xs text-white/70 font-semibold">Level {Math.floor(profile.xp / 100) + 1} ({profile.xp} XP)</span>
            </div>
            <div className="flex items-center gap-2">
              <Star className="h-4 w-4 text-[#ffd700] fill-[#ffd700]" />
              <span className="text-sm font-bold">{profile.coins} Coins</span>
            </div>
          </div>
        )}

        <nav className="flex-1 space-y-1">
          {navItems.map((item) => {
            const Icon = item.icon;
            const active = pathname === item.href;
            return (
              <Link 
                key={item.href} 
                href={item.href}
                className={`flex items-center gap-3 px-4 py-3 rounded-lg text-sm font-medium transition cursor-pointer ${
                  active ? "bg-[#00f2fe]/10 text-[#00f2fe] border-l-2 border-[#00f2fe]" : "text-white/60 hover:text-white hover:bg-white/5"
                }`}
              >
                <Icon className="h-5 w-5" />
                {item.name}
              </Link>
            );
          })}
        </nav>

        <button 
          onClick={() => signOut()}
          className="flex items-center gap-3 px-4 py-3 rounded-lg text-sm font-medium text-red-400 hover:bg-red-500/10 transition mt-auto cursor-pointer"
        >
          <LogOut className="h-5 w-5" />
          Logout
        </button>
      </aside>

      {/* Mobile Header Bar */}
      <div className="md:hidden w-full flex flex-col min-h-screen">
        <header className="flex items-center justify-between px-6 py-4 glass-panel border-b border-white/5 sticky top-0 z-50">
          <h1 className="text-xl font-extrabold text-gradient-cyan">Happyshaa</h1>
          <div className="flex items-center gap-4">
            {profile && (
              <div className="flex items-center gap-1 text-xs font-bold text-[#ffd700]">
                <Star className="h-4 w-4 fill-[#ffd700]" />
                {profile.coins}
              </div>
            )}
            <button onClick={() => setMobileOpen(!mobileOpen)} className="text-white">
              {mobileOpen ? <X className="h-6 w-6" /> : <Menu className="h-6 w-6" />}
            </button>
          </div>
        </header>

        {/* Mobile Navigation Drawer */}
        {mobileOpen && (
          <nav className="fixed inset-0 top-[64px] bg-[#0c091a] z-40 p-6 flex flex-col space-y-2">
            {navItems.map((item) => {
              const Icon = item.icon;
              const active = pathname === item.href;
              return (
                <Link 
                  key={item.href} 
                  href={item.href}
                  onClick={() => setMobileOpen(false)}
                  className={`flex items-center gap-3 px-4 py-4 rounded-lg text-lg font-medium ${
                    active ? "bg-[#00f2fe]/10 text-[#00f2fe]" : "text-white/70"
                  }`}
                >
                  <Icon className="h-6 w-6" />
                  {item.name}
                </Link>
              );
            })}
            <button 
              onClick={() => signOut()}
              className="flex items-center gap-3 px-4 py-4 rounded-lg text-lg font-medium text-red-400"
            >
              <LogOut className="h-6 w-6" />
              Logout
            </button>
          </nav>
        )}

        <main className="flex-1 p-6 overflow-y-auto">{children}</main>
      </div>

      {/* Desktop Main Content Container */}
      <main className="hidden md:block flex-1 p-8 overflow-y-auto h-screen">{children}</main>
    </div>
  );
}
