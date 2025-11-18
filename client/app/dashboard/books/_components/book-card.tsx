'use client';

import Link from 'next/link';
import { Badge } from '@/components/ui/badge';
import { Button } from '@/components/ui/button';
import { Card, CardContent, CardFooter, CardHeader, CardTitle } from '@/components/ui/card';
import { useAuthStore } from '@/store/auth';
import type { Book } from '@/types';

interface BookCardProps {
  book: Book;
}

export function BookCard({ book }: BookCardProps) {
  const isAdmin = useAuthStore((state) => state.isAdmin);

  const truncatedDescription =
    book.description && book.description.length > 100
      ? `${book.description.substring(0, 100)}...`
      : book.description || '';

  const publicationYear = book.publicationDate
    ? new Date(book.publicationDate).getFullYear()
    : 'N/A';

  return (
    <Card className="h-full flex flex-col">
      <CardHeader>
        <CardTitle className="text-lg font-semibold leading-none tracking-tight">
          {book.title}
        </CardTitle>
      </CardHeader>

      <CardContent className="flex-1 space-y-3">
        <p className="text-sm text-muted-foreground">by {book.authorName}</p>
        <p className="text-sm">{truncatedDescription}</p>
        <div className="flex items-center justify-between">
          <Badge variant="secondary">{book.genreName}</Badge>
          <span className="text-xs text-muted-foreground">{publicationYear}</span>
        </div>
      </CardContent>

      <CardFooter className="flex justify-between gap-2">
        <Button asChild variant="outline" size="sm" className="hover:cursor-pointer">
          <Link href={`/dashboard/books/${book.id}`}>View Details</Link>
        </Button>
        {isAdmin && (
          <Button variant="outline" size="sm" className="hover:cursor-pointer">
            <Link href={`/dashboard/books/${book.id}/edit`}>Edit</Link>
          </Button>
        )}
      </CardFooter>
    </Card>
  );
}
