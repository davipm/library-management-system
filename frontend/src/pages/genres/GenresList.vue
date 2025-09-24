<script setup lang="ts">
import { ref, computed } from 'vue'
import { useRouter } from 'vue-router'
import { useAuthStore } from '@/stores/auth'
import { useGenres } from '@/composables/useGenres'
import GenreCard from '@/components/shared/GenreCard.vue'
import { Button } from '@/components/ui/button'
import { Input } from '@/components/ui/input'

const router = useRouter()
const authStore = useAuthStore()
const { useGetAllGenres } = useGenres()

const searchQuery = ref('')

const { data: genres, isLoading, error, refetch } = useGetAllGenres()

const filteredGenres = computed(() => {
  if (!genres.value || !searchQuery.value) return genres.value || []

  const query = searchQuery.value.toLowerCase()
  return genres.value.filter(
    (genre) =>
      genre.name.toLowerCase().includes(query) || genre.description?.toLowerCase().includes(query),
  )
})

const isAdmin = computed(() => authStore.isAdmin)

const createGenre = () => {
  router.push('/dashboard/genres/create')
}
</script>

<template>
  <div class="space-y-6">
    <div class="flex flex-col sm:flex-row sm:items-center sm:justify-between">
      <h1 class="text-3xl font-bold tracking-tight">Genres</h1>
      <div class="mt-4 sm:mt-0 flex space-x-2">
        <Button v-if="isAdmin" @click="createGenre" variant="default"> Add Genre </Button>
        <Input v-model="searchQuery" placeholder="Search genres..." class="w-full sm:w-64" />
      </div>
    </div>

    <div v-if="isLoading" class="flex justify-center items-center h-64">
      <p>...Loading</p>
    </div>

    <div v-else-if="error" class="text-center py-12 text-destructive">
      {{ error }}
    </div>

    <div v-else-if="filteredGenres.length === 0" class="text-center py-12">
      <p class="text-muted-foreground">No genres found</p>
    </div>

    <div v-else class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
      <GenreCard v-for="genre in filteredGenres" :key="genre.id" :genre="genre" />
    </div>

    <!-- Refresh button for manual refetch -->
    <div class="flex justify-center mt-4">
      <Button variant="outline" @click="refetch" :disabled="isLoading"> Refresh Data </Button>
    </div>
  </div>
</template>
