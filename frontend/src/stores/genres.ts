import { defineStore } from 'pinia'
import { ref, computed } from 'vue'
import { genreService } from '@/services/genreService'
import type { Genre, GenreDTO } from '@/types'

export const useGenresStore = defineStore('genres', () => {
  const genres = ref<Genre[]>([])
  const currentGenre = ref<Genre | null>(null)
  const loading = ref(false)
  const error = ref<string | null>(null)

  const getGenreById = computed(() => (id: number) => {
    return genres.value.find((genre) => genre.id === id) || null
  })

  const fetchGenres = async () => {
    loading.value = true
    error.value = null
    try {
      const data = await genreService.getAllGenres()
      genres.value = data
    } catch (err: any) {
      error.value = err.response?.data?.message || 'Failed to fetch genres'
    } finally {
      loading.value = false
    }
  }

  const fetchGenreById = async (id: number) => {
    loading.value = true
    error.value = null
    try {
      const data = await genreService.getGenreById(id)
      currentGenre.value = data
      return data
    } catch (err: any) {
      error.value = err.response?.data?.message || 'Failed to fetch genre'
      return null
    } finally {
      loading.value = false
    }
  }

  const createGenre = async (genre: GenreDTO) => {
    loading.value = true
    error.value = null
    try {
      const data = await genreService.createGenre(genre)
      genres.value.push(data)
      return { success: true, data }
    } catch (err: any) {
      error.value = err.response?.data?.message || 'Failed to create genre'
      return { success: false, error: error.value }
    } finally {
      loading.value = false
    }
  }

  const updateGenre = async (id: number, genre: GenreDTO) => {
    loading.value = true
    error.value = null
    try {
      const data = await genreService.updateGenre(id, genre)
      const index = genres.value.findIndex((g) => g.id === id)
      if (index !== -1) {
        genres.value[index] = data
      }
      if (currentGenre.value?.id === id) {
        currentGenre.value = data
      }
      return { success: true, data }
    } catch (err: any) {
      error.value = err.response?.data?.message || 'Failed to update genre'
      return { success: false, error: error.value }
    } finally {
      loading.value = false
    }
  }

  const deleteGenre = async (id: number) => {
    loading.value = true
    error.value = null
    try {
      await genreService.deleteGenre(id)
      genres.value = genres.value.filter((genre) => genre.id !== id)
      if (currentGenre.value?.id === id) {
        currentGenre.value = null
      }
      return { success: true }
    } catch (err: any) {
      error.value = err.response?.data?.message || 'Failed to delete genre'
      return { success: false, error: error.value }
    } finally {
      loading.value = false
    }
  }

  const clearCurrentGenre = () => {
    currentGenre.value = null
  }

  return {
    genres,
    currentGenre,
    loading,
    error,
    getGenreById,
    fetchGenres,
    fetchGenreById,
    createGenre,
    updateGenre,
    deleteGenre,
    clearCurrentGenre,
  }
})
