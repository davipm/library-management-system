'use client';

import { BookOpenIcon, ShieldCheckIcon, TagIcon, UserGroupIcon } from '@heroicons/react/24/outline';
import { useQuery } from '@tanstack/react-query';
import { useRouter } from 'next/navigation';
import { Button } from '@/components/ui/button';
import { Card, CardContent, CardHeader } from '@/components/ui/card';
import { authorService } from '@/services/author-service';
import { bookService } from '@/services/book-service';
import { genreService } from '@/services/genre-service';
import { useAuthStore } from '@/store/auth';

interface Author {
  id: number;
  name: string;
  books?: unknown[];
}

interface Genre {
  id: number;
  name: string;
}

export default function DashboardPage() {
  const router = useRouter();
  const { user, isAdmin } = useAuthStore();

  const { data: books = [] } = useQuery({
    queryKey: bookService.keys.list(),
    queryFn: () => bookService.getAll(),
  });

  const { data: authors = [] } = useQuery<Author[]>({
    queryKey: authorService.keys.list(),
    queryFn: () => authorService.getAll(),
  });

  const { data: genres = [] } = useQuery<Genre[]>({
    queryKey: genreService.keys.list(),
    queryFn: () => genreService.getAll(),
  });

  const viewBook = (id: number) => {
    router.push(`/dashboard/books/${id}`);
  };

  const viewAuthor = (id: number) => {
    router.push(`/dashboard/authors/${id}`);
  };

  return (
    <div className="space-y-6">
      <div>
        <h1 className="text-3xl font-bold tracking-tight">Dashboard</h1>
        <p className="mt-2 text-muted-foreground">
          Welcome back, {user?.username}! Here&apos;s what&apos;s happening with your library today.
        </p>
      </div>

      <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6">
        <Card>
          <CardHeader>
            <div className="flex items-center">
              <BookOpenIcon className="h-6 w-6 text-blue-500 mr-2" />
              <h3 className="text-lg font-medium">Books</h3>
            </div>
          </CardHeader>
          <CardContent>
            <div className="text-3xl font-bold">{books.length}</div>
            <p className="text-sm text-muted-foreground mt-2">Total books in library</p>
          </CardContent>
        </Card>

        <Card>
          <CardHeader>
            <div className="flex items-center">
              <UserGroupIcon className="h-6 w-6 text-green-500 mr-2" />
              <h3 className="text-lg font-medium">Authors</h3>
            </div>
          </CardHeader>
          <CardContent>
            <div className="text-3xl font-bold">{authors.length}</div>
            <p className="text-sm text-muted-foreground mt-2">Total authors</p>
          </CardContent>
        </Card>

        <Card>
          <CardHeader>
            <div className="flex items-center">
              <TagIcon className="h-6 w-6 text-purple-500 mr-2" />
              <h3 className="text-lg font-medium">Genres</h3>
            </div>
          </CardHeader>
          <CardContent>
            <div className="text-3xl font-bold">{genres.length}</div>
            <p className="text-sm text-muted-foreground mt-2">Total genres</p>
          </CardContent>
        </Card>

        {isAdmin && (
          <Card>
            <CardHeader>
              <div className="flex items-center">
                <ShieldCheckIcon className="h-6 w-6 text-red-500 mr-2" />
                <h3 className="text-lg font-medium">Admin</h3>
              </div>
            </CardHeader>
            <CardContent>
              <div className="text-3xl font-bold">Yes</div>
              <p className="text-sm text-muted-foreground mt-2">Admin access</p>
            </CardContent>
          </Card>
        )}
      </div>

      <div className="grid grid-cols-1 lg:grid-cols-2 gap-6">
        <Card>
          <CardHeader>
            <h3 className="text-lg font-medium">Recent Books</h3>
          </CardHeader>
          <CardContent>
            <div className="space-y-4">
              {books.slice(0, 5).map((book) => (
                <div
                  key={book.id}
                  className="flex items-center justify-between p-3 border rounded-md"
                >
                  <div>
                    <h4 className="font-medium">{book.title}</h4>
                    <p className="text-sm text-muted-foreground">{book.authorName}</p>
                  </div>
                  <Button variant="outline" size="sm" onClick={() => viewBook(book.id)}>
                    View
                  </Button>
                </div>
              ))}
            </div>
          </CardContent>
        </Card>

        <Card>
          <CardHeader>
            <h3 className="text-lg font-medium">Recent Authors</h3>
          </CardHeader>
          <CardContent>
            <div className="space-y-4">
              {authors.slice(0, 5).map((author) => (
                <div
                  key={author.id}
                  className="flex items-center justify-between p-3 border rounded-md"
                >
                  <div>
                    <h4 className="font-medium">{author.name}</h4>
                    <p className="text-sm text-muted-foreground">
                      {author.books?.length || 0} books
                    </p>
                  </div>
                  <Button variant="outline" size="sm" onClick={() => viewAuthor(author.id)}>
                    View
                  </Button>
                </div>
              ))}
            </div>
          </CardContent>
        </Card>
      </div>
    </div>
  );
}
