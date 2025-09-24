<script setup lang="ts">
import { ref, computed } from 'vue'
import { useRouter } from 'vue-router'
import { useAuthStore } from '@/stores/auth'
import { useAuthors } from '@/composables/useAuthors'
import AuthorCard from '@/components/shared/AuthorCard.vue'
import { Button } from '@/components/ui/button'
import { Input } from '@/components/ui/input'

const router = useRouter()
const authStore = useAuthStore()
const { useGetAllAuthors } = useAuthors()

const searchQuery = ref('')

// Use TanStack Query to fetch authors
const { data: authors, isLoading, error } = useGetAllAuthors()

const filteredAuthors = computed(() => {
  if (!authors.value || !searchQuery.value) return authors.value

  const query = searchQuery.value.toLowerCase()
  return authors.value.filter(
    (author) =>
      author.name.toLowerCase().includes(query) ||
      author.biography?.toLowerCase().includes(query) ||
      (author.birthDate && new Date(author.birthDate).getFullYear().toString().includes(query)),
  )
})

const isAdmin = computed(() => authStore.isAdmin)

const createAuthor = () => {
  router.push('/dashboard/authors/create')
}
</script>

<template>
  <div class="space-y-6">
    <div class="flex flex-col sm:flex-row sm:items-center sm:justify-between">
      <h1 class="text-3xl font-bold tracking-tight">Authors</h1>
      <div class="mt-4 sm:mt-0 flex space-x-2">
        <Button v-if="isAdmin" @click="createAuthor" variant="default">Add Author</Button>
        <Input v-model="searchQuery" placeholder="Search authors..." class="w-full sm:w-64" />
      </div>
    </div>

    <div v-if="isLoading" class="flex justify-center items-center h-64">
      <p>...Loading</p>
    </div>

    <div v-else-if="error" class="text-center py-12 text-destructive">
      {{ error }}
    </div>

    <div v-else-if="filteredAuthors.length === 0" class="text-center py-12">
      <p class="text-muted-foreground">No authors found</p>
    </div>

    <div v-else class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
      <AuthorCard v-for="author in filteredAuthors" :key="author.id" :author="author" />
    </div>
  </div>
</template>
