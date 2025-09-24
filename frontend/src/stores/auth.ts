import { defineStore } from 'pinia'
import { ref, computed } from 'vue'
import authService from '@/services/authService'
import type { User, LoginRequest, RegisterRequest } from '@/types'

export const useAuthStore = defineStore('auth', () => {
  const user = ref<User | null>(null)
  const isAuthenticated = computed(() => !!user.value)
  const isAdmin = computed(() => user.value?.role === 'ROLE_ADMIN' ?? false)

  const login = async (credentials: LoginRequest) => {
    try {
      const response = await authService.login(credentials)
      authService.setAuthToken(response.token)
      await fetchCurrentUser()
      return { success: true }
    } catch (error: any) {
      return {
        success: false,
        message: error.response?.data?.message || 'Login failed',
      }
    }
  }

  const register = async (userData: RegisterRequest) => {
    try {
      await authService.register(userData)
      return { success: true }
    } catch (error: any) {
      return {
        success: false,
        message: error.response?.data?.message || 'Registration failed',
      }
    }
  }

  const fetchCurrentUser = async () => {
    try {
      const currentUser = await authService.getCurrentUser()
      user.value = currentUser
    } catch (error) {
      console.error('Failed to fetch current user:', error)
      logout()
    }
  }

  const logout = () => {
    authService.logout()
    user.value = null
  }

  const initializeAuth = async () => {
    if (authService.isAuthenticated()) {
      await fetchCurrentUser()
    }
  }

  return {
    user,
    isAuthenticated,
    isAdmin,
    login,
    register,
    logout,
    fetchCurrentUser,
    initializeAuth,
  }
})