import { defineStore } from 'pinia'
import { ref, computed } from 'vue'
import bookService from '@/services/bookService'
import type { Book, BookDTO } from '@/types'

export const useBooksStore = defineStore('books', () => {
  const books = ref<Book[]>([])
  const currentBook = ref<Book | null>(null)
  const loading = ref(false)
  const error = ref<string | null>(null)

  const getBookById = computed(() => (id: number) => {
    return books.value.find((book) => book.id === id) || null
  })

  const fetchBooks = async () => {
    loading.value = true
    error.value = null
    try {
      const data = await bookService.getAllBooks()
      books.value = data
    } catch (err: any) {
      error.value = err.response?.data?.message || 'Failed to fetch books'
    } finally {
      loading.value = false
    }
  }

  const fetchBookById = async (id: number) => {
    loading.value = true
    error.value = null
    try {
      const data = await bookService.getBookById(id)
      currentBook.value = data
      return data
    } catch (err: any) {
      error.value = err.response?.data?.message || 'Failed to fetch book'
      return null
    } finally {
      loading.value = false
    }
  }

  const createBook = async (book: BookDTO) => {
    loading.value = true
    error.value = null
    try {
      const data = await bookService.createBook(book)
      books.value.push(data)
      return { success: true, data }
    } catch (err: any) {
      error.value = err.response?.data?.message || 'Failed to create book'
      return { success: false, error: error.value }
    } finally {
      loading.value = false
    }
  }

  const updateBook = async (id: number, book: BookDTO) => {
    loading.value = true
    error.value = null
    try {
      const data = await bookService.updateBook(id, book)
      const index = books.value.findIndex((b) => b.id === id)
      if (index !== -1) {
        books.value[index] = data
      }
      if (currentBook.value?.id === id) {
        currentBook.value = data
      }
      return { success: true, data }
    } catch (err: any) {
      error.value = err.response?.data?.message || 'Failed to update book'
      return { success: false, error: error.value }
    } finally {
      loading.value = false
    }
  }

  const deleteBook = async (id: number) => {
    loading.value = true
    error.value = null
    try {
      await bookService.deleteBook(id)
      books.value = books.value.filter((book) => book.id !== id)
      if (currentBook.value?.id === id) {
        currentBook.value = null
      }
      return { success: true }
    } catch (err: any) {
      error.value = err.response?.data?.message || 'Failed to delete book'
      return { success: false, error: error.value }
    } finally {
      loading.value = false
    }
  }

  const clearCurrentBook = () => {
    currentBook.value = null
  }

  return {
    books,
    currentBook,
    loading,
    error,
    getBookById,
    fetchBooks,
    fetchBookById,
    createBook,
    updateBook,
    deleteBook,
    clearCurrentBook,
  }
})
