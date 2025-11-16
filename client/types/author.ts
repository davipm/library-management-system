import type { Book } from '@/types/book.ts'

export interface Author {
  id: number
  name: string
  biography: string
  birthDate: string | null
  books?: Book[]
}

export interface AuthorDTO {
  id?: number
  name: string
  biography: string
  birthDate: string | null
}
