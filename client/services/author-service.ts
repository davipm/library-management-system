import type { Author, AuthorDTO } from '@/types';
import api from './api';

export const authorService = {
  keys: {
    all: ['authors'] as const,
    lists: () => [...authorService.keys.all, 'list'] as const,
    list: (filters?: any) => [...authorService.keys.lists(), { filters }] as const,
    details: () => [...authorService.keys.all, 'detail'] as const,
    detail: (id: number) => [...authorService.keys.details(), id] as const,
  },

  getAll: async (): Promise<Author[]> => {
    const response = await api.get<Author[]>('/api/v1/authors');
    return response.data;
  },

  getById: async (id: number): Promise<Author> => {
    const response = await api.get<Author>(`/api/v1/authors/${id}`);
    return response.data;
  },

  create: async (author: AuthorDTO): Promise<Author> => {
    const response = await api.post<Author>('/api/v1/authors', author);
    return response.data;
  },

  update: async ({ id, author }: { id: number; author: AuthorDTO }): Promise<Author> => {
    const response = await api.put<Author>(`/api/v1/authors/${id}`, author);
    return response.data;
  },

  delete: async (id: number): Promise<void> => {
    await api.delete(`/api/v1/authors/${id}`);
  },
};
