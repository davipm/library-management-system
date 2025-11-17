import { toast } from 'sonner';
import { create } from 'zustand';
import authService from '@/services/auth-service';
import type { LoginRequest, RegisterRequest, User } from '@/types';

interface AuthState {
  user: User | null;
  isAuthenticated: boolean;
  isAdmin: boolean;
  login: (credentials: LoginRequest) => Promise<{ success: boolean; message?: string }>;
  register: (userData: RegisterRequest) => Promise<{ success: boolean; message?: string }>;
  fetchCurrentUser: () => Promise<void>;
  logout: () => void;
  initializeAuth: () => Promise<void>;
}

if (typeof window === 'undefined') {
  console.warn('Auth store used on server! This will not work.');
}

export const useAuthStore = create<AuthState>((set, get) => ({
  user: null,
  isAuthenticated: false,
  isAdmin: false,

  login: async (credentials) => {
    try {
      await authService.login(credentials);
      await get().fetchCurrentUser();
      return { success: true };
    } catch (error: unknown) {
      toast.error('Error during login');
      const message =
        (error as { response?: { data?: { message?: string } } }).response?.data?.message ||
        'Login failed';
      return { success: false, message };
    }
  },

  register: async (userData) => {
    try {
      await authService.register(userData);
      return { success: true };
    } catch (error: unknown) {
      const message =
        (error as { response?: { data?: { message?: string } } }).response?.data?.message ||
        'Registration failed';
      return { success: false, message };
    }
  },

  fetchCurrentUser: async () => {
    try {
      const user = await authService.getCurrentUser();
      set({
        user,
        isAuthenticated: true,
        isAdmin: user.role === 'ROLE_ADMIN',
      });
    } catch (error) {
      toast.error('Failed to fetch current user');
      console.error('Failed to fetch current user:', error);
      get().logout();
    }
  },

  logout: () => {
    authService.logout();
    set({ user: null, isAuthenticated: false, isAdmin: false });
  },

  initializeAuth: async () => {
    if (authService.isAuthenticated()) {
      await get().fetchCurrentUser();
    }
  },
}));
