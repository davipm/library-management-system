<script setup lang="ts">
import { computed } from 'vue'
import { useRouter } from 'vue-router'
import { useAuthStore } from '@/stores/auth'
import type { Genre } from '@/types'
import Card from '@/components/ui/Card.vue'
import { Button } from '@/components/ui/button'

interface Props {
  genre: Genre
}

const props = defineProps<Props>()
const router = useRouter()
const authStore = useAuthStore()

const isAdmin = computed(() => authStore.isAdmin)

const viewDetails = () => {
  router.push(`/dashboard/genres/${props.genre.id}`)
}

const editGenre = () => {
  router.push(`/dashboard/genres/${props.genre.id}/edit`)
}
</script>

<template>
  <Card class="h-full">
    <template #header>
      <h3 class="font-semibold leading-none tracking-tight">{{ genre.name }}</h3>
    </template>
    <div class="space-y-2">
      <p class="text-sm">
        {{ genre.description?.substring(0, 100) }}{{ genre.description?.length > 100 ? '...' : '' }}
      </p>
      <div class="flex items-center justify-between">
        <span class="text-xs text-muted-foreground"> {{ genre.books?.length || 0 }} books </span>
      </div>
    </div>
    <template #footer>
      <div class="flex items-center justify-between">
        <Button variant="outline" size="sm" @click="viewDetails"> View Details </Button>
        <Button v-if="isAdmin" variant="outline" size="sm" @click="editGenre"> Edit </Button>
      </div>
    </template>
  </Card>
</template>
