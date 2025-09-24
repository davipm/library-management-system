<script setup lang="ts">
import { ref, reactive, computed, watch } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { useBooks } from '@/composables/useBooks'
import { useAuthors } from '@/composables/useAuthors'
import { useGenres } from '@/composables/useGenres'
import type { BookDTO } from '@/types'
import Card from '@/components/ui/Card.vue'
import { Input } from '@/components/ui/input'
import { Button } from '@/components/ui/button'
import Select from '@/components/ui/Select.vue'
//import { Select } from '@/components/ui/select'

const route = useRoute()
const router = useRouter()

// Use TanStack Query composables
const { useGetAllAuthors } = useAuthors()
const { useGetAllGenres } = useGenres()
const { useGetBookById, useCreateBook, useUpdateBook } = useBooks()

const isEditing = computed(() => !!route.params.id)
const bookId = computed(() => Number(route.params.id))

const form = reactive<BookDTO>({
  title: '',
  isbn: '',
  publicationDate: null,
  description: '',
  authorId: 0,
  genreId: 0,
})

const errors = ref<Record<string, string>>({})
const errorMessage = ref('')
const isSubmitting = ref(false)

// Fetch authors and genres
const authorsQuery = useGetAllAuthors()
const genresQuery = useGetAllGenres()

// Get book data if editing
const bookQuery = useGetBookById(bookId.value, {
  enabled: isEditing.value,
})

// Computed options for selects
const authorOptions = computed(() => {
  if (!authorsQuery.data?.value) return []
  return authorsQuery.data.value.map((author) => ({
    value: author.id,
    label: author.name,
  }))
})

const genreOptions = computed(() => {
  if (!genresQuery.data?.value) return []
  return genresQuery.data.value.map((genre) => ({
    value: genre.id,
    label: genre.name,
  }))
})

// Watch for book data when editing
watch(
  () => bookQuery.data?.value,
  (book) => {
    if (book) {
      form.title = book.title
      form.isbn = book.isbn
      form.publicationDate = book.publicationDate
      form.description = book.description || ''
      form.authorId = book.authorId
      form.genreId = book.genreId
    }
  },
  { immediate: true },
)

const validateForm = (): boolean => {
  errors.value = {}

  if (!form.title.trim()) {
    errors.value.title = 'Title is required'
  }

  if (!form.authorId) {
    errors.value.authorId = 'Author is required'
  }

  if (!form.genreId) {
    errors.value.genreId = 'Genre is required'
  }

  return Object.keys(errors.value).length === 0
}

const handleSubmit = async () => {
  if (!validateForm()) return

  isSubmitting.value = true
  errorMessage.value = ''

  try {
    let result

    if (isEditing.value) {
      result = await useUpdateBook().mutateAsync({
        id: bookId.value,
        book: form,
      })
    } else {
      result = await useCreateBook().mutateAsync(form)
    }

    if (result) {
      router.push('/dashboard/books')
    }
  } catch (error: any) {
    errorMessage.value = error.response?.data?.message || 'Operation failed'
  } finally {
    isSubmitting.value = false
  }
}
</script>

<template>
  <div class="space-y-6">
    <div>
      <h1 class="text-3xl font-bold tracking-tight">
        {{ isEditing ? 'Edit Book' : 'Add Book' }}
      </h1>
      <p class="text-muted-foreground">
        {{ isEditing ? 'Update book details' : 'Create a new book' }}
      </p>
    </div>

    <Card>
      <form @submit.prevent="handleSubmit" class="space-y-6">
        <div class="grid grid-cols-1 md:grid-cols-2 gap-6">
          <div>
            <label for="title" class="block text-sm font-medium text-gray-700">Title *</label>
            <div class="mt-1">
              <Input
                id="title"
                v-model="form.title"
                :error="!!errors.title"
                placeholder="Enter book title"
                required
              />
              <p v-if="errors.title" class="mt-1 text-sm text-destructive">{{ errors.title }}</p>
            </div>
          </div>

          <div>
            <label for="isbn" class="block text-sm font-medium text-gray-700">ISBN</label>
            <div class="mt-1">
              <Input
                id="isbn"
                v-model="form.isbn"
                :error="!!errors.isbn"
                placeholder="Enter ISBN"
              />
              <p v-if="errors.isbn" class="mt-1 text-sm text-destructive">{{ errors.isbn }}</p>
            </div>
          </div>

          <div>
            <label for="author" class="block text-sm font-medium text-gray-700">Author *</label>
            <div class="mt-1">
              <Select
                id="author"
                v-model="form.authorId"
                :options="authorOptions"
                :error="!!errors.authorId"
                placeholder="Select author"
                required
              />
              <p v-if="errors.authorId" class="mt-1 text-sm text-destructive">
                {{ errors.authorId }}
              </p>
            </div>
          </div>

          <div>
            <label for="genre" class="block text-sm font-medium text-gray-700">Genre *</label>
            <div class="mt-1">
              <Select
                id="genre"
                v-model="form.genreId"
                :options="genreOptions"
                :error="!!errors.genreId"
                placeholder="Select genre"
                required
              />
              <p v-if="errors.genreId" class="mt-1 text-sm text-destructive">
                {{ errors.genreId }}
              </p>
            </div>
          </div>

          <div class="md:col-span-2">
            <label for="publicationDate" class="block text-sm font-medium text-gray-700"
              >Publication Date</label
            >
            <div class="mt-1">
              <Input id="publicationDate" v-model="form.publicationDate" type="date" />
            </div>
          </div>

          <div class="md:col-span-2">
            <label for="description" class="block text-sm font-medium text-gray-700"
              >Description</label
            >
            <div class="mt-1">
              <textarea
                id="description"
                v-model="form.description"
                rows="4"
                class="flex w-full rounded-md border border-input bg-background px-3 py-2 text-sm ring-offset-background file:border-0 file:bg-transparent file:text-sm file:font-medium placeholder:text-muted-foreground focus-visible:outline-none focus-visible:ring-2 focus-visible:ring-ring focus-visible:ring-offset-2 disabled:cursor-not-allowed disabled:opacity-50 min-h-[80px]"
                placeholder="Enter book description"
              />
            </div>
          </div>
        </div>

        <div class="flex items-center justify-end space-x-4">
          <Button type="button" variant="outline" @click="router.back()"> Cancel </Button>
          <Button type="submit" :disabled="isSubmitting">
            <span v-if="isSubmitting">Saving...</span>
            <span v-else>{{ isEditing ? 'Update Book' : 'Create Book' }}</span>
          </Button>
        </div>

        <div v-if="errorMessage" class="text-center text-sm text-destructive">
          {{ errorMessage }}
        </div>
      </form>
    </Card>
  </div>
</template>
