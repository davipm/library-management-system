import { defineStore } from 'pinia'
import { ref, computed } from 'vue'
import authorService from '@/services/authorService'
import type { Author, AuthorDTO } from '@/types'

export const useAuthorsStore = defineStore('authors', () => {
  const authors = ref<Author[]>([])
  const currentAuthor = ref<Author | null>(null)
  const loading = ref(false)
  const error = ref<string | null>(null)

  const getAuthorById = computed(() => (id: number) => {
    return authors.value.find((author) => author.id === id) || null
  })

  const fetchAuthors = async () => {
    loading.value = true
    error.value = null
    try {
      const data = await authorService.getAllAuthors()
      authors.value = data
    } catch (err: any) {
      error.value = err.response?.data?.message || 'Failed to fetch authors'
    } finally {
      loading.value = false
    }
  }

  const fetchAuthorById = async (id: number) => {
    loading.value = true
    error.value = null
    try {
      const data = await authorService.getAuthorById(id)
      currentAuthor.value = data
      return data
    } catch (err: any) {
      error.value = err.response?.data?.message || 'Failed to fetch author'
      return null
    } finally {
      loading.value = false
    }
  }

  const createAuthor = async (author: AuthorDTO) => {
    loading.value = true
    error.value = null
    try {
      const data = await authorService.createAuthor(author)
      authors.value.push(data)
      return { success: true, data }
    } catch (err: any) {
      error.value = err.response?.data?.message || 'Failed to create author'
      return { success: false, error: error.value }
    } finally {
      loading.value = false
    }
  }

  const updateAuthor = async (id: number, author: AuthorDTO) => {
    loading.value = true
    error.value = null
    try {
      const data = await authorService.updateAuthor(id, author)
      const index = authors.value.findIndex((a) => a.id === id)
      if (index !== -1) {
        authors.value[index] = data
      }
      if (currentAuthor.value?.id === id) {
        currentAuthor.value = data
      }
      return { success: true, data }
    } catch (err: any) {
      error.value = err.response?.data?.message || 'Failed to update author'
      return { success: false, error: error.value }
    } finally {
      loading.value = false
    }
  }

  const deleteAuthor = async (id: number) => {
    loading.value = true
    error.value = null
    try {
      await authorService.deleteAuthor(id)
      authors.value = authors.value.filter((author) => author.id !== id)
      if (currentAuthor.value?.id === id) {
        currentAuthor.value = null
      }
      return { success: true }
    } catch (err: any) {
      error.value = err.response?.data?.message || 'Failed to delete author'
      return { success: false, error: error.value }
    } finally {
      loading.value = false
    }
  }

  const clearCurrentAuthor = () => {
    currentAuthor.value = null
  }

  return {
    authors,
    currentAuthor,
    loading,
    error,
    getAuthorById,
    fetchAuthors,
    fetchAuthorById,
    createAuthor,
    updateAuthor,
    deleteAuthor,
    clearCurrentAuthor,
  }
})
