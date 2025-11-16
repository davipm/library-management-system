export interface Book {
  id: number
  title: string
  isbn: string
  publicationDate: string | null
  description: string
  authorId: number
  genreId: number
  authorName?: string
  genreName?: string
}

export interface BookDTO {
  id?: number
  title: string
  isbn: string
  publicationDate: string | null
  description: string
  authorId: number
  genreId: number
}
