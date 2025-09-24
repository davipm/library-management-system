import api from './api'
import type { Genre, GenreDTO } from '@/types'

export const genreService = {
  // Query keys
  keys: {
    all: ['genres'] as const,
    lists: () => [...genreService.keys.all, 'list'] as const,
    list: (filters?: any) => [...genreService.keys.lists(), { filters }] as const,
    details: () => [...genreService.keys.all, 'detail'] as const,
    detail: (id: number) => [...genreService.keys.details(), id] as const,
  },

  // Queries
  getAll: async (): Promise<Genre[]> => {
    const response = await api.get<Genre[]>('/api/v1/genres')
    return response.data
  },

  getById: async (id: number): Promise<Genre> => {
    const response = await api.get<Genre>(`/api/v1/genres/${id}`)
    return response.data
  },

  // Mutations
  create: async (genre: GenreDTO): Promise<Genre> => {
    const response = await api.post<Genre>('/api/v1/genres', genre)
    return response.data
  },

  update: async ({ id, genre }: { id: number; genre: GenreDTO }): Promise<Genre> => {
    const response = await api.put<Genre>(`/api/v1/genres/${id}`, genre)
    return response.data
  },

  delete: async (id: number): Promise<void> => {
    await api.delete(`/api/v1/genres/${id}`)
  },
}
