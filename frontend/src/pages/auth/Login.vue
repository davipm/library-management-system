<script setup lang="ts">
import { ref, reactive } from 'vue'
import { useRouter } from 'vue-router'
import { useAuthStore } from '@/stores/auth'
import Card from '@/components/ui/Card.vue'
import { Input } from '@/components/ui/input'
import { Button } from '@/components/ui/button'

const router = useRouter()
const authStore = useAuthStore()

const form = reactive({
  username: '',
  password: '',
})

const errors = ref<Record<string, string>>({})
const errorMessage = ref('')
const loading = ref(false)

const validateForm = (): boolean => {
  errors.value = {}

  if (!form.username.trim()) {
    errors.value.username = 'Username is required'
  }

  if (!form.password) {
    errors.value.password = 'Password is required'
  }

  return Object.keys(errors.value).length === 0
}

const handleLogin = async () => {
  if (!validateForm()) return

  loading.value = true
  errorMessage.value = ''

  try {
    const result = await authStore.login({
      username: form.username,
      password: form.password,
    })

    if (result.success) {
      await router.push('/dashboard')
    } else {
      errorMessage.value = result.message || 'Login failed'
    }
  } catch (error) {
    console.error('Login error:', error)
    errorMessage.value = 'An unexpected error occurred'
  } finally {
    loading.value = false
  }
}
</script>

<template>
  <div class="min-h-screen flex items-center justify-center bg-gray-50 py-12 px-4 sm:px-6 lg:px-8">
    <Card class="w-full max-w-md">
      <template #header>
        <div class="text-center">
          <h2 class="mt-6 text-3xl font-bold text-gray-900">Sign in to your account</h2>
          <p class="mt-2 text-sm text-gray-600">
            Or
            <router-link to="/register" class="font-medium text-primary hover:text-primary/80">
              create a new account
            </router-link>
          </p>
        </div>
      </template>

      <form @submit.prevent="handleLogin" class="space-y-6">
        <div>
          <label for="username" class="block text-sm font-medium text-gray-700">Username</label>
          <div class="mt-1">
            <Input
              id="username"
              v-model="form.username"
              :error="!!errors.username"
              placeholder="Enter your username"
              required
            />
            <p v-if="errors.username" class="mt-1 text-sm text-destructive">
              {{ errors.username }}
            </p>
          </div>
        </div>

        <div>
          <label for="password" class="block text-sm font-medium text-gray-700">Password</label>
          <div class="mt-1">
            <Input
              id="password"
              v-model="form.password"
              :error="!!errors.password"
              type="password"
              placeholder="Enter your password"
              required
            />
            <p v-if="errors.password" class="mt-1 text-sm text-destructive">
              {{ errors.password }}
            </p>
          </div>
        </div>

        <div class="flex items-center justify-between">
          <div class="flex items-center">
            <input
              id="remember-me"
              name="remember-me"
              type="checkbox"
              class="h-4 w-4 text-primary focus:ring-primary border-gray-300 rounded"
            />
            <label for="remember-me" class="ml-2 block text-sm text-gray-900">Remember me</label>
          </div>

          <div class="text-sm">
            <a href="#" class="font-medium text-primary hover:text-primary/80"
              >Forgot your password?</a
            >
          </div>
        </div>

        <div>
          <Button type="submit" :disabled="loading" class="w-full">
            <span v-if="loading">Signing in...</span>
            <span v-else>Sign in</span>
          </Button>
        </div>

        <div v-if="errorMessage" class="text-center text-sm text-destructive">
          {{ errorMessage }}
        </div>
      </form>
    </Card>
  </div>
</template>
