<script setup lang="ts">
import { computed } from 'vue'
import { useRouter } from 'vue-router'
import { useAuthStore } from '@/stores/auth'
import type { Book } from '@/types'
import { Button } from '@/components/ui/button'
import { Badge } from '@/components/ui/badge'
import Card from '@/components/ui/Card.vue'

interface Props {
  book: Book
}

const props = defineProps<Props>()
const router = useRouter()
const authStore = useAuthStore()

const isAdmin = computed(() => authStore.isAdmin)

const viewDetails = () => {
  router.push(`/dashboard/books/${props.book.id}`)
}

const editBook = () => {
  router.push(`/dashboard/books/${props.book.id}/edit`)
}
</script>

<template>
  <Card class="h-full">
    <template #header>
      <h3 class="font-semibold leading-none tracking-tight">{{ book.title }}</h3>
    </template>
    <div class="space-y-2">
      <p class="text-sm text-muted-foreground">by {{ book.authorName }}</p>
      <p class="text-sm">
        {{ book.description?.substring(0, 100) }}{{ book.description?.length > 100 ? '...' : '' }}
      </p>
      <div class="flex items-center justify-between">
        <Badge variant="secondary">{{ book.genreName }}</Badge>
        <span class="text-xs text-muted-foreground">
          {{ book.publicationDate ? new Date(book.publicationDate).getFullYear() : 'N/A' }}
        </span>
      </div>
    </div>
    <template #footer>
      <div class="flex items-center justify-between">
        <Button variant="outline" size="sm" @click="viewDetails">View Details</Button>/>
        <Button v-if="isAdmin" variant="outline" size="sm" @click="editBook">Edit</Button>
      </div>
    </template>
  </Card>
</template>
