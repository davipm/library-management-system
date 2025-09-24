import api from './api'
import type { Genre, GenreDTO } from '@/types'

class GenreService {
  public async getAllGenres(): Promise<Genre[]> {
    const response = await api.get<Genre[]>('/api/v1/genres')
    return response.data
  }

  public async getGenreById(id: number): Promise<Genre> {
    const response = await api.get<Genre>(`/api/v1/genres/${id}`)
    return response.data
  }

  public async createGenre(genre: GenreDTO): Promise<Genre> {
    const response = await api.post<Genre>('/api/v1/genres', genre)
    return response.data
  }

  public async updateGenre(id: number, genre: GenreDTO): Promise<Genre> {
    const response = await api.put<Genre>(`/api/v1/genres/${id}`, genre)
    return response.data
  }

  public async deleteGenre(id: number): Promise<void> {
    await api.delete(`/api/v1/genres/${id}`)
  }
}

export default new GenreService()
