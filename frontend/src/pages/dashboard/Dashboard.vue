<script setup lang="ts">
import { computed } from 'vue'
import { useRouter } from 'vue-router'
import { useAuthStore } from '@/stores/auth'
import { useBooks } from '@/composables/useBooks'
import { useAuthors } from '@/composables/useAuthors'
import { useGenres } from '@/composables/useGenres'
import Card from '@/components/ui/Card.vue'
import { Button } from '@/components/ui/button'
// import LoadingSpinner from '@/components/ui/LoadingSpinner.vue'
import { BookOpenIcon, UserGroupIcon, TagIcon, ShieldCheckIcon } from '@heroicons/vue/24/outline'

const router = useRouter()
const authStore = useAuthStore()
const { useGetAllBooks } = useBooks()
const { useGetAllAuthors } = useAuthors()
const { useGetAllGenres } = useGenres()

// Fetch all data using TanStack Query
const booksQuery = useGetAllBooks()
const authorsQuery = useGetAllAuthors()
const genresQuery = useGetAllGenres()

const user = computed(() => authStore.user)
const isAdmin = computed(() => authStore.isAdmin)

const viewBook = (id: number) => {
  router.push(`/dashboard/books/${id}`)
}

const viewAuthor = (id: number) => {
  router.push(`/dashboard/authors/${id}`)
}
</script>

<template>
  <div class="space-y-6">
    <div>
      <h1 class="text-3xl font-bold tracking-tight">Dashboard</h1>
      <p class="mt-2 text-muted-foreground">
        Welcome back, {{ user?.username }}! Here's what's happening with your library today.
      </p>
    </div>

    <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6">
      <!-- Books Card -->
      <Card>
        <template #header>
          <div class="flex items-center">
            <BookOpenIcon class="h-6 w-6 text-blue-500 mr-2" />
            <h3 class="text-lg font-medium">Books</h3>
          </div>
        </template>
        <div class="text-3xl font-bold">
          <span v-if="booksQuery.isLoading">...</span>
          <span v-else>{{ booksQuery.data?.value?.length || 0 }}</span>
        </div>
        <p class="text-sm text-muted-foreground mt-2">Total books in library</p>
      </Card>

      <!-- Authors Card -->
      <Card>
        <template #header>
          <div class="flex items-center">
            <UserGroupIcon class="h-6 w-6 text-green-500 mr-2" />
            <h3 class="text-lg font-medium">Authors</h3>
          </div>
        </template>
        <div class="text-3xl font-bold">
          <span v-if="authorsQuery.isLoading">...</span>
          <span v-else>{{ authorsQuery.data?.value?.length || 0 }}</span>
        </div>
        <p class="text-sm text-muted-foreground mt-2">Total authors</p>
      </Card>

      <!-- Genres Card -->
      <Card>
        <template #header>
          <div class="flex items-center">
            <TagIcon class="h-6 w-6 text-purple-500 mr-2" />
            <h3 class="text-lg font-medium">Genres</h3>
          </div>
        </template>
        <div class="text-3xl font-bold">
          <span v-if="genresQuery.isLoading">...</span>
          <span v-else>{{ genresQuery.data?.value?.length || 0 }}</span>
        </div>
        <p class="text-sm text-muted-foreground mt-2">Total genres</p>
      </Card>

      <!-- Admin Card -->
      <Card v-if="isAdmin">
        <template #header>
          <div class="flex items-center">
            <ShieldCheckIcon class="h-6 w-6 text-red-500 mr-2" />
            <h3 class="text-lg font-medium">Admin</h3>
          </div>
        </template>
        <div class="text-3xl font-bold">
          <span v-if="isAdmin">Yes</span>
          <span v-else>No</span>
        </div>
        <p class="text-sm text-muted-foreground mt-2">Admin access</p>
      </Card>
    </div>

    <div class="grid grid-cols-1 lg:grid-cols-2 gap-6">
      <!-- Recent Books -->
      <Card>
        <template #header>
          <h3 class="text-lg font-medium">Recent Books</h3>
        </template>
        <div v-if="booksQuery.isLoading" class="flex justify-center items-center h-32">
          <!--<LoadingSpinner />-->
          <p>...Loading</p>
        </div>
        <div v-else-if="booksQuery.error" class="text-center py-4 text-destructive">
          {{ booksQuery.error }}
        </div>
        <div v-else class="space-y-4">
          <div
            v-for="book in booksQuery.data?.value?.slice(0, 5) || []"
            :key="book.id"
            class="flex items-center justify-between p-3 border rounded-md"
          >
            <div>
              <h4 class="font-medium">{{ book.title }}</h4>
              <p class="text-sm text-muted-foreground">{{ book.authorName }}</p>
            </div>
            <Button variant="outline" size="sm" @click="viewBook(book.id)"> View </Button>
          </div>
        </div>
      </Card>

      <!-- Recent Authors -->
      <Card>
        <template #header>
          <h3 class="text-lg font-medium">Recent Authors</h3>
        </template>
        <div v-if="authorsQuery.isLoading" class="flex justify-center items-center h-32">
          <LoadingSpinner />
        </div>
        <div v-else-if="authorsQuery.error" class="text-center py-4 text-destructive">
          {{ authorsQuery.error }}
        </div>
        <div v-else class="space-y-4">
          <div
            v-for="author in authorsQuery.data?.value?.slice(0, 5) || []"
            :key="author.id"
            class="flex items-center justify-between p-3 border rounded-md"
          >
            <div>
              <h4 class="font-medium">{{ author.name }}</h4>
              <p class="text-sm text-muted-foreground">{{ author.books?.length || 0 }} books</p>
            </div>
            <Button variant="outline" size="sm" @click="viewAuthor(author.id)"> View </Button>
          </div>
        </div>
      </Card>
    </div>
  </div>
</template>
