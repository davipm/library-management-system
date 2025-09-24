<template>
  <div class="space-y-6">
    <div class="flex items-center justify-between">
      <h1 class="text-3xl font-bold tracking-tight">Book Details</h1>
      <div v-if="isAdmin" class="flex space-x-2">
        <Button @click="editBook" variant="outline">Edit</Button>
        <Button @click="deleteBook" variant="destructive">Delete</Button>
      </div>
    </div>

    <div v-if="isLoading" class="flex justify-center items-center h-64">
      <!--<LoadingSpinner />-->
      <p>...Loading</p>
    </div>

    <div v-else-if="error" class="text-center py-12 text-destructive">
      {{ error }}
    </div>

    <div v-else-if="book">
      <Card>
        <template #header>
          <h2 class="text-2xl font-bold">{{ book.title }}</h2>
        </template>

        <div class="space-y-4">
          <div>
            <h3 class="text-sm font-medium text-muted-foreground">Author</h3>
            <p class="text-lg">{{ book.authorName }}</p>
          </div>

          <div>
            <h3 class="text-sm font-medium text-muted-foreground">Genre</h3>
            <p class="text-lg">{{ book.genreName }}</p>
          </div>

          <div>
            <h3 class="text-sm font-medium text-muted-foreground">ISBN</h3>
            <p class="text-lg">{{ book.isbn }}</p>
          </div>

          <div>
            <h3 class="text-sm font-medium text-muted-foreground">Publication Date</h3>
            <p class="text-lg">
              {{
                book.publicationDate ? new Date(book.publicationDate).toLocaleDateString() : 'N/A'
              }}
            </p>
          </div>

          <div>
            <h3 class="text-sm font-medium text-muted-foreground">Description</h3>
            <p class="text-lg">{{ book.description }}</p>
          </div>
        </div>
      </Card>
    </div>

    <div class="flex justify-between">
      <Button @click="router.back()" variant="outline"> Back to Books </Button>
    </div>
  </div>
</template>

<script setup lang="ts">
import { computed } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { useAuthStore } from '@/stores/auth'
import { useBooks } from '@/composables/useBooks'
import Card from '@/components/ui/Card.vue'
import { Button } from '@/components/ui/button'
// import LoadingSpinner from '@/components/ui/LoadingSpinner.vue'

const route = useRoute()
const router = useRouter()
const authStore = useAuthStore()
const { useGetBookById, useDeleteBook } = useBooks()

const bookId = computed(() => Number(route.params.id))
const isAdmin = computed(() => authStore.isAdmin)

// Use TanStack Query to fetch book data
const { data: book, isLoading, error } = useGetBookById(bookId.value)

const editBook = () => {
  router.push(`/dashboard/books/${bookId.value}/edit`)
}

const deleteBook = async () => {
  if (confirm('Are you sure you want to delete this book?')) {
    try {
      await useDeleteBook().mutateAsync(bookId.value)
      await router.push('/dashboard/books')
    } catch (error) {
      console.error('Failed to delete book:', error)
    }
  }
}
</script>
