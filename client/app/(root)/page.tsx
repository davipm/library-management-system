'use client';

import { redirect } from 'next/navigation';
import { useAuthStore } from '@/store/auth';

export default function Home() {
  const { isAuthenticated } = useAuthStore();
  if (!isAuthenticated) return redirect('/login');
  return redirect('/dashboard');
}
