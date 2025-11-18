import api from '@/services/api';
import type { Book, BookDTO } from '@/types';

export const bookService = {
  keys: {
    all: ['books'] as const,
    lists: () => [...bookService.keys.all, 'list'] as const,
    list: (filters?: any) => [...bookService.keys.lists(), { filters }] as const,
    details: () => [...bookService.keys.all, 'detail'] as const,
    detail: (id: number) => [...bookService.keys.details(), id] as const,
  },

  getAll: async (): Promise<Book[]> => {
    const response = await api.get<Book[]>('/api/v1/books');
    return response.data;
  },

  getById: async (id: number): Promise<Book> => {
    const response = await api.get<Book>(`/api/v1/books/${id}`);
    return response.data;
  },

  create: async (book: BookDTO): Promise<Book> => {
    const response = await api.post<Book>('/api/v1/books', book);
    return response.data;
  },

  update: async ({ id, book }: { id: number; book: BookDTO }): Promise<Book> => {
    const response = await api.put<Book>(`/api/v1/books/${id}`, book);
    return response.data;
  },

  delete: async (id: number): Promise<void> => {
    await api.delete(`/api/v1/books/${id}`);
  },
};
