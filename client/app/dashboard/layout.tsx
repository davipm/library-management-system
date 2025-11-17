'use client';

import { type ReactNode, useEffect } from 'react';
import { Footer } from '@/components/footer';
import { Header } from '@/components/header';
import { Sidebar } from '@/components/sidebar';
import { useAuthStore } from '@/store/auth';

export default function Layout({ children }: { children: ReactNode }) {
  const { initializeAuth } = useAuthStore();

  useEffect(() => {
    (async () => {
      await initializeAuth();
    })();
  }, [initializeAuth]);

  return (
    <div className="min-h-screen bg-gray-50">
      <Header />
      <div className="flex">
        <Sidebar />
        <main className="flex-1 pb-8">
          <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-6">{children}</div>
        </main>
      </div>
      <Footer />
    </div>
  );
}
