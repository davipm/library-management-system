<script setup lang="ts">
import { computed } from 'vue'
import { useRouter } from 'vue-router'
import { useAuthStore } from '@/stores/auth'
import type { Author } from '@/types'
import Card from '@/components/ui/Card.vue'
import { Button } from '@/components/ui/button'

interface Props {
  author: Author
}

const props = defineProps<Props>()
const router = useRouter()
const authStore = useAuthStore()

const isAdmin = computed(() => authStore.isAdmin)

const viewDetails = () => {
  router.push(`/dashboard/authors/${props.author.id}`)
}

const editAuthor = () => {
  router.push(`/dashboard/authors/${props.author.id}/edit`)
}
</script>

<template>
  <Card class="h-full">
    <template #header>
      <h3 class="font-semibold leading-none tracking-tight">{{ author.name }}</h3>
    </template>
    <div class="space-y-2">
      <p class="text-sm text-muted-foreground">
        {{ author.birthDate ? new Date(author.birthDate).getFullYear() : 'N/A' }}
      </p>
      <p class="text-sm">
        {{ author.biography?.substring(0, 100) }}{{ author.biography?.length > 100 ? '...' : '' }}
      </p>
      <div class="flex items-center justify-between">
        <span class="text-xs text-muted-foreground"> {{ author.books?.length || 0 }} books </span>
      </div>
    </div>
    <template #footer>
      <div class="flex items-center justify-between">
        <Button variant="outline" size="sm" @click="viewDetails"> View Details </Button>
        <Button v-if="isAdmin" variant="outline" size="sm" @click="editAuthor"> Edit </Button>
      </div>
    </template>
  </Card>
</template>
