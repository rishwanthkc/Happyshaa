"use client";

import { useState } from "react";
import AppLayout from "../../components/AppLayout";
import { useAuthStore } from "../../store/authStore";
import { Settings, Volume2, Shield, Eye, LogOut } from "lucide-react";

export default function SettingsPage() {
  const { signOut } = useAuthStore();
  const [notificationsEnabled, setNotificationsEnabled] = useState(true);
  const [fontSize, setFontSize] = useState("Medium");
  const [language, setLanguage] = useState("English");

  return (
    <AppLayout>
      <div className="max-w-4xl mx-auto space-y-6">
        
        <div className="glass-panel rounded-2xl p-6">
          <h3 className="text-lg font-bold mb-4 flex items-center gap-2">
            <Settings className="h-5 w-5 text-[#00f2fe]" />
            Application Preferences
          </h3>

          <div className="space-y-6 divide-y divide-white/5">
            {/* Font size row */}
            <div className="flex justify-between items-center py-4">
              <div>
                <h4 className="font-bold text-sm">Font Size</h4>
                <p className="text-xs text-white/50">Adjust text size in chat feeds and stories</p>
              </div>
              <div className="flex gap-2">
                {["Small", "Medium", "Large"].map((size) => (
                  <button
                    key={size}
                    onClick={() => setFontSize(size)}
                    className={`py-1.5 px-3 rounded-lg text-xs font-semibold cursor-pointer ${
                      fontSize === size ? "bg-[#00f2fe] text-black" : "bg-white/5 hover:bg-white/10"
                    }`}
                  >
                    {size}
                  </button>
                ))}
              </div>
            </div>

            {/* Notifications row */}
            <div className="flex justify-between items-center py-4">
              <div>
                <h4 className="font-bold text-sm">Push Notifications</h4>
                <p className="text-xs text-white/50">Enable browser pushes for wellness reminders</p>
              </div>
              <label className="relative inline-flex items-center cursor-pointer">
                <input
                  type="checkbox"
                  checked={notificationsEnabled}
                  onChange={(e) => setNotificationsEnabled(e.target.checked)}
                  className="sr-only peer"
                />
                <div className="w-11 h-6 bg-white/10 rounded-full peer peer-checked:after:translate-x-full peer-checked:after:border-white after:content-[''] after:absolute after:top-0.5 after:left-[2px] after:bg-white after:border-gray-300 after:border after:rounded-full after:h-5 after:w-5 after:transition-all peer-checked:bg-[#00f2fe]" />
              </label>
            </div>

            {/* Language row */}
            <div className="flex justify-between items-center py-4">
              <div>
                <h4 className="font-bold text-sm">App Language</h4>
                <p className="text-xs text-white/50">Select localized audio translations</p>
              </div>
              <select 
                value={language} 
                onChange={(e) => setLanguage(e.target.value)}
                className="glass-input p-2 text-xs"
              >
                <option value="English" className="text-black">English</option>
                <option value="Spanish" className="text-black">Español</option>
                <option value="French" className="text-black">Français</option>
              </select>
            </div>

            {/* Logout row */}
            <div className="flex justify-between items-center py-4">
              <div>
                <h4 className="font-bold text-sm text-red-400">Logout session</h4>
                <p className="text-xs text-white/50">Disconnect this account from web client</p>
              </div>
              <button 
                onClick={() => signOut()}
                className="bg-red-500/10 border border-red-500/25 hover:bg-red-500/20 text-red-400 font-bold px-4 py-2 rounded-lg text-xs flex items-center gap-2 cursor-pointer"
              >
                <LogOut className="h-4 w-4" /> Sign Out
              </button>
            </div>

          </div>
        </div>

      </div>
    </AppLayout>
  );
}
