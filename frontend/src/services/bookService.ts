import api from './api'
import type { Book, BookDTO } from '@/types'

class BookService {
  public async getAllBooks(): Promise<Book[]> {
    const response = await api.get<Book[]>('/api/v1/books')
    return response.data
  }

  public async getBookById(id: number): Promise<Book> {
    const response = await api.get<Book>(`/api/v1/books/${id}`)
    return response.data
  }

  public async createBook(book: BookDTO): Promise<Book> {
    const response = await api.post<Book>('/api/v1/books', book)
    return response.data
  }

  public async updateBook(id: number, book: BookDTO): Promise<Book> {
    const response = await api.put<Book>(`/api/v1/books/${id}`, book)
    return response.data
  }

  public async deleteBook(id: number): Promise<void> {
    await api.delete(`/api/v1/books/${id}`)
  }
}

export default new BookService()
