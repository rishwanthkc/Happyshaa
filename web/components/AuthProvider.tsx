"use client";

import { useEffect } from "react";
import { useAuthStore } from "../store/authStore";

export default function AuthProvider({ children }: { children: React.ReactNode }) {
  const initialize = useAuthStore((state) => state.initialize);
  const isLoading = useAuthStore((state) => state.isLoading);

  useEffect(() => {
    const unsubscribe = initialize();
    return () => unsubscribe();
  }, [initialize]);

  if (isLoading) {
    return (
      <div className="flex h-screen w-screen items-center justify-center bg-[#0F0C1B]">
        <div className="h-10 w-10 animate-spin rounded-full border-4 border-[#00f2fe] border-t-transparent" />
      </div>
    );
  }

  return <>{children}</>;
}
