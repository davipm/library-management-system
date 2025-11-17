import type { Book } from '@/types/book.ts';

export interface Genre {
  id: number;
  name: string;
  description: string;
  books?: Book[];
}

export interface GenreDTO {
  id?: number;
  name: string;
  description: string;
}
