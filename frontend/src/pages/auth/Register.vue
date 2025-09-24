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
  email: '',
  password: '',
  confirmPassword: '',
})

const errors = ref<Record<string, string>>({})
const errorMessage = ref('')
const loading = ref(false)

const validateForm = (): boolean => {
  errors.value = {}

  if (!form.username.trim()) {
    errors.value.username = 'Username is required'
  }

  if (!form.email.trim()) {
    errors.value.email = 'Email is required'
  } else if (!/\S+@\S+\.\S+/.test(form.email)) {
    errors.value.email = 'Email is invalid'
  }

  if (!form.password) {
    errors.value.password = 'Password is required'
  } else if (form.password.length < 6) {
    errors.value.password = 'Password must be at least 6 characters'
  }

  if (!form.confirmPassword) {
    errors.value.confirmPassword = 'Please confirm your password'
  } else if (form.password !== form.confirmPassword) {
    errors.value.confirmPassword = 'Passwords do not match'
  }

  return Object.keys(errors.value).length === 0
}

const handleRegister = async () => {
  if (!validateForm()) return

  loading.value = true
  errorMessage.value = ''

  try {
    const result = await authStore.register({
      username: form.username,
      email: form.email,
      password: form.password,
    })

    if (result.success) {
      // Auto-login after successful registration
      const loginResult = await authStore.login({
        username: form.username,
        password: form.password,
      })

      if (loginResult.success) {
        await router.push('/dashboard')
      } else {
        errorMessage.value = 'Registration successful but login failed'
      }
    } else {
      errorMessage.value = result.message || 'Registration failed'
    }
  } catch (error) {
    console.error('Registration error:', error)
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
          <h2 class="mt-6 text-3xl font-bold text-gray-900">Create a new account</h2>
          <p class="mt-2 text-sm text-gray-600">
            Or
            <router-link to="/login" class="font-medium text-primary hover:text-primary/80">
              sign in to your account
            </router-link>
          </p>
        </div>
      </template>

      <form @submit.prevent="handleRegister" class="space-y-6">
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
          <label for="email" class="block text-sm font-medium text-gray-700">Email</label>
          <div class="mt-1">
            <Input
              id="email"
              v-model="form.email"
              :error="!!errors.email"
              type="email"
              placeholder="Enter your email"
              required
            />
            <p v-if="errors.email" class="mt-1 text-sm text-destructive">{{ errors.email }}</p>
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

        <div>
          <label for="confirmPassword" class="block text-sm font-medium text-gray-700"
            >Confirm Password</label
          >
          <div class="mt-1">
            <Input
              id="confirmPassword"
              v-model="form.confirmPassword"
              :error="!!errors.confirmPassword"
              type="password"
              placeholder="Confirm your password"
              required
            />
            <p v-if="errors.confirmPassword" class="mt-1 text-sm text-destructive">
              {{ errors.confirmPassword }}
            </p>
          </div>
        </div>

        <div>
          <Button type="submit" :disabled="loading" class="w-full">
            <span v-if="loading">Creating account...</span>
            <span v-else>Create Account</span>
          </Button>
        </div>

        <div v-if="errorMessage" class="text-center text-sm text-destructive">
          {{ errorMessage }}
        </div>
      </form>
    </Card>
  </div>
</template>
