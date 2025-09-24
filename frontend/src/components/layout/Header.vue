<script setup lang="ts">
import { computed } from 'vue'
import { useRouter } from 'vue-router'
import { useAuthStore } from '@/stores/auth'
import SearchBar from '@/components/shared/SearchBar.vue'
import { Button } from '@/components/ui/button'

const router = useRouter()
const authStore = useAuthStore()

const isAuthenticated = computed(() => authStore.isAuthenticated)
const isAdmin = computed(() => authStore.isAdmin)
const user = computed(() => authStore.user)

const logout = () => {
  authStore.logout()
  router.push('/login')
}

const goToLogin = () => {
  router.push('/login')
}

const goToRegister = () => {
  router.push('/register')
}
</script>

<template>
  <header class="bg-white shadow-sm border-b">
    <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
      <div class="flex justify-between h-16">
        <div class="flex items-center">
          <div class="flex-shrink-0">
            <h1 class="text-xl font-bold text-gray-900">Library Management System</h1>
          </div>
        </div>

        <div class="flex items-center space-x-4">
          <SearchBar v-if="isAuthenticated" />

          <div v-if="isAuthenticated" class="flex items-center space-x-4">
            <div class="flex items-center space-x-2">
              <span class="text-sm text-gray-700">{{ user?.username }}</span>
              <span
                v-if="isAdmin"
                class="inline-flex items-center px-2.5 py-0.5 rounded-full text-xs font-medium bg-blue-100 text-blue-800"
              >
                Admin
              </span>
            </div>

            <Button variant="outline" size="sm" @click="logout"> Logout </Button>
          </div>

          <div v-else class="flex items-center space-x-2">
            <Button variant="outline" size="sm" @click="goToLogin"> Login </Button>
            <Button variant="default" size="sm" @click="goToRegister"> Register </Button>
          </div>
        </div>
      </div>
    </div>
  </header>
</template>
