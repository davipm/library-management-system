'use client';

import { useRouter } from 'next/navigation';
import { useMemo, useState } from 'react';
import { BookCard } from '@/app/dashboard/books/_components/book-card';
import { Button } from '@/components/ui/button';
import { Input } from '@/components/ui/input';
import { useBooks } from '@/hooks/use-books';
import { useAuthStore } from '@/store/auth';

export default function BooksPage() {
  const router = useRouter();
  const { isAdmin } = useAuthStore();
  const [searchQuery, setSearchQuery] = useState('');

  const { books, bookLoading, error } = useBooks();

  const filteredBooks = useMemo(() => {
    if (!searchQuery.trim()) return books;

    const query = searchQuery.toLowerCase().trim();
    return books.filter(
      (book) =>
        book.title.toLowerCase().includes(query) ||
        book.authorName?.toLowerCase().includes(query) ||
        book.genreName?.toLowerCase().includes(query),
    );
  }, [books, searchQuery]);

  const handleCreateBook = () => {
    router.push('/dashboard/books/create');
  };

  if (bookLoading) {
    return (
      <div className="flex justify-center items-center h-64">
        <p>...Loading</p>
      </div>
    );
  }

  if (error) {
    return (
      <div className="text-center py-12 text-destructive">
        {error instanceof Error ? error.message : 'Failed to load books'}
      </div>
    );
  }

  return (
    <div className="space-y-6">
      <div className="flex flex-col sm:flex-row sm:items-center sm:justify-between gap-4">
        <h1 className="text-3xl font-bold tracking-tight">Books</h1>
        <div className="flex flex-col sm:flex-row gap-2 w-full sm:w-auto">
          {isAdmin && (
            <Button onClick={handleCreateBook} variant="default">
              Add Book
            </Button>
          )}
          <Input
            value={searchQuery}
            onChange={(e) => setSearchQuery(e.target.value)}
            placeholder="Search books..."
            className="w-full sm:w-64"
          />
        </div>
      </div>

      {filteredBooks.length === 0 ? (
        <div className="text-center py-12">
          <p className="text-muted-foreground">No books found</p>
        </div>
      ) : (
        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
          {filteredBooks.map((book) => (
            <BookCard key={book.id} />
          ))}
        </div>
      )}
    </div>
  );
}
