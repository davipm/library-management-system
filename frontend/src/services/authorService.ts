import api from './api'
import type { Author, AuthorDTO } from '@/types'

class AuthorService {
  public async getAllAuthors(): Promise<Author[]> {
    const response = await api.get<Author[]>('/api/v1/authors')
    return response.data
  }

  public async getAuthorById(id: number): Promise<Author> {
    const response = await api.get<Author>(`/api/v1/authors/${id}`)
    return response.data
  }

  public async createAuthor(author: AuthorDTO): Promise<Author> {
    const response = await api.post<Author>('/api/v1/authors', author)
    return response.data
  }

  public async updateAuthor(id: number, author: AuthorDTO): Promise<Author> {
    const response = await api.put<Author>(`/api/v1/authors/${id}`, author)
    return response.data
  }

  public async deleteAuthor(id: number): Promise<void> {
    await api.delete(`/api/v1/authors/${id}`)
  }
}

export default new AuthorService()
