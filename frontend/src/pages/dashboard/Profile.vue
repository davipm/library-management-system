<script setup lang="ts">
import { computed } from 'vue'
import { useRouter } from 'vue-router'
import { useAuthStore } from '@/stores/auth'
import Card from '@/components/ui/Card.vue'
import { Button } from '@/components/ui/button'
import { Badge } from '@/components/ui/badge'
import { UserIcon } from '@heroicons/vue/24/outline'

const router = useRouter()
const authStore = useAuthStore()

const user = computed(() => authStore.user)

const logout = () => {
  authStore.logout()
  router.push('/login')
}

const editProfile = () => {
  console.log('Edit')
}
</script>

<template>
  <div class="space-y-6">
    <div>
      <h1 class="text-3xl font-bold tracking-tight">Profile</h1>
      <p class="mt-2 text-muted-foreground">Manage your account settings and preferences.</p>
    </div>

    <Card>
      <template #header>
        <h2 class="text-xl font-semibold">User Information</h2>
      </template>

      <div class="space-y-4">
        <div class="flex items-center space-x-4">
          <div class="flex-shrink-0">
            <div class="h-16 w-16 rounded-full bg-gray-200 flex items-center justify-center">
              <UserIcon class="h-8 w-8 text-gray-500" />
            </div>
          </div>
          <div>
            <h3 class="text-lg font-medium">{{ user?.username }}</h3>
            <p class="text-sm text-muted-foreground">{{ user?.email }}</p>
          </div>
        </div>

        <div class="grid grid-cols-1 md:grid-cols-2 gap-6">
          <div>
            <label class="block text-sm font-medium text-gray-700">Username</label>
            <p class="mt-1 text-sm">{{ user?.username }}</p>
          </div>

          <div>
            <label class="block text-sm font-medium text-gray-700">Email</label>
            <p class="mt-1 text-sm">{{ user?.email }}</p>
          </div>

          <div>
            <label class="block text-sm font-medium text-gray-700">Role</label>
            <div class="mt-1">
              <Badge v-if="user?.role" variant="secondary">
                {{ user.role }}
              </Badge>
            </div>
          </div>

          <div>
            <label class="block text-sm font-medium text-gray-700">Member Since</label>
            <p class="mt-1 text-sm">January 1, 2023</p>
          </div>
        </div>
      </div>

      <template #footer>
        <div class="flex justify-end space-x-4">
          <Button variant="outline" @click="logout"> Logout </Button>
          <Button variant="default" @click="editProfile"> Edit Profile </Button>
        </div>
      </template>
    </Card>
  </div>
</template>
