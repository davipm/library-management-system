<script setup lang="ts">
import { computed } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { useAuthStore } from '@/stores/auth'
import { useGenres } from '@/composables/useGenres'
import Card from '@/components/ui/Card.vue'
import { Button } from '@/components/ui/button'

const route = useRoute()
const router = useRouter()
const authStore = useAuthStore()
const { useGetGenreById, useDeleteGenre } = useGenres()

const genreId = computed(() => Number(route.params.id))
const isAdmin = computed(() => authStore.isAdmin)

const { data: genre, isLoading, error } = useGetGenreById(genreId.value)

const editGenre = () => {
  router.push(`/dashboard/genres/${genreId.value}/edit`)
}

const deleteGenre = async () => {
  if (confirm('Are you sure you want to delete this genre?')) {
    try {
      await useDeleteGenre().mutateAsync(genreId.value)
      router.push('/dashboard/genres')
    } catch (error) {
      console.error('Failed to delete genre:', error)
    }
  }
}
</script>

<template>
  <div class="space-y-6">
    <div class="flex items-center justify-between">
      <h1 class="text-3xl font-bold tracking-tight">Genre Details</h1>
      <div v-if="isAdmin" class="flex space-x-2">
        <Button @click="editGenre" variant="outline">Edit</Button>
        <Button @click="deleteGenre" variant="destructive">Delete</Button>
      </div>
    </div>

    <div v-if="isLoading" class="flex justify-center items-center h-64">
      <p>...Loading</p>
    </div>

    <div v-else-if="error" class="text-center py-12 text-destructive">
      {{ error }}
    </div>

    <div v-else-if="genre">
      <Card>
        <template #header>
          <h2 class="text-2xl font-bold">{{ genre.name }}</h2>
        </template>

        <div class="space-y-4">
          <div>
            <h3 class="text-sm font-medium text-muted-foreground">Description</h3>
            <p class="text-lg">{{ genre.description || 'No description available' }}</p>
          </div>

          <div>
            <h3 class="text-sm font-medium text-muted-foreground">Books in this genre</h3>
            <div v-if="genre.books && genre.books.length > 0" class="mt-2">
              <ul class="list-disc list-inside space-y-1">
                <li v-for="book in genre.books" :key="book.id">
                  <span class="font-medium">{{ book.title }}</span> by {{ book.authorName }}
                </li>
              </ul>
            </div>
            <p v-else class="text-muted-foreground">No books in this genre yet</p>
          </div>
        </div>
      </Card>
    </div>

    <div class="flex justify-between">
      <Button @click="router.back()" variant="outline"> Back to Genres </Button>
    </div>
  </div>
</template>
