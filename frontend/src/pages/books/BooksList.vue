<script setup lang="ts">
import { ref, computed } from 'vue'
import { useRouter } from 'vue-router'
import { useAuthStore } from '@/stores/auth'
import { useBooks } from '@/composables/useBooks'
import BookCard from '@/components/shared/BookCard.vue'
import { Button } from '@/components/ui/button'
import { Input } from '@/components/ui/input'

const router = useRouter()
const authStore = useAuthStore()
const { useGetAllBooks } = useBooks()

const searchQuery = ref('')

const { data: books, isLoading, error } = useGetAllBooks()

const filteredBooks = computed(() => {
  if (!books.value || !searchQuery.value) return books.value || []

  const query = searchQuery.value.toLowerCase()
  return books.value.filter(
    (book) =>
      book.title.toLowerCase().includes(query) ||
      book.authorName?.toLowerCase().includes(query) ||
      book.genreName?.toLowerCase().includes(query),
  )
})

const isAdmin = computed(() => authStore.isAdmin)

const createBook = () => {
  router.push('/dashboard/books/create')
}
</script>

<template>
  <div class="space-y-6">
    <div class="flex flex-col sm:flex-row sm:items-center sm:justify-between">
      <h1 class="text-3xl font-bold tracking-tight">Books</h1>
      <div class="mt-4 sm:mt-0 flex space-x-2">
        <Button v-if="isAdmin" @click="createBook" variant="default"> Add Book </Button>
        <Input v-model="searchQuery" placeholder="Search books..." class="w-full sm:w-64" />
      </div>
    </div>

    <div v-if="isLoading" class="flex justify-center items-center h-64">
      <p>...Loading</p>
    </div>

    <div v-else-if="error" class="text-center py-12 text-destructive">
      {{ error }}
    </div>

    <div v-else-if="filteredBooks.length === 0" class="text-center py-12">
      <p class="text-muted-foreground">No books found</p>
    </div>

    <div v-else class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
      <BookCard v-for="book in filteredBooks" :key="book.id" :book="book" />
    </div>
  </div>
</template>
