'use client';

import { BookOpenIcon, ShieldCheckIcon, TagIcon, UserGroupIcon } from '@heroicons/react/24/outline';
import { useQuery } from '@tanstack/react-query';
import Link from 'next/link';
import { DashboardCard } from '@/components/dashboard-card';
import { Button } from '@/components/ui/button';
import { Card, CardContent, CardHeader } from '@/components/ui/card';
import { Skeleton } from '@/components/ui/skeleton';
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
  const { user, isAdmin } = useAuthStore();

  const { data: books = [], isLoading: bookLoading } = useQuery({
    queryKey: bookService.keys.list(),
    queryFn: () => bookService.getAll(),
  });

  const { data: authors = [], isLoading: authorLoading } = useQuery<Author[]>({
    queryKey: authorService.keys.list(),
    queryFn: () => authorService.getAll(),
  });

  const { data: genres = [], isLoading: genresLoading } = useQuery<Genre[]>({
    queryKey: genreService.keys.list(),
    queryFn: () => genreService.getAll(),
  });

  return (
    <div className="space-y-6">
      <div>
        <h1 className="text-3xl font-bold tracking-tight">Dashboard</h1>
        <p className="mt-2 text-muted-foreground">
          Welcome back, {user?.username}! Here&apos;s what&apos;s happening with your library today.
        </p>
      </div>

      <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6">
        <DashboardCard
          text="Books"
          length={books.length}
          loading={bookLoading}
          icon={<BookOpenIcon className="h-6 w-6 text-blue-500 mr-2" />}
        />

        <DashboardCard
          text="Authors"
          length={authors.length}
          loading={authorLoading}
          icon={<UserGroupIcon className="h-6 w-6 text-green-500 mr-2" />}
        />

        <DashboardCard
          text="Genres"
          length={genres.length}
          loading={genresLoading}
          icon={<TagIcon className="h-6 w-6 text-purple-500 mr-2" />}
        />

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
              {bookLoading
                ? Array.from({ length: 5 }).map((_, index) => (
                    <div key={index} className="flex items-center gap-3 rounded-md border p-3">
                      <div className="flex flex-1 flex-col gap-2">
                        <Skeleton className="h-4 w-1/4" />
                      </div>
                      <Skeleton className="h-8 w-13 shrink-0 rounded-md" />
                    </div>
                  ))
                : books.slice(0, 5).map((book) => (
                    <div
                      key={book.id}
                      className="flex items-center justify-between p-3 border rounded-md"
                    >
                      <div>
                        <h4 className="font-medium">{book.title}</h4>
                        <p className="text-sm text-muted-foreground">{book.authorName}</p>
                      </div>
                      <Button asChild variant="outline" size="sm">
                        <Link href={`/dashboard/books/${book.id}`}>View</Link>
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
              {authorLoading
                ? Array.from({ length: 5 }).map((_, index) => (
                    <div key={index} className="flex items-center gap-3 rounded-md border p-3">
                      <div className="flex flex-1 flex-col gap-2">
                        <Skeleton className="h-5 w-1/4" />
                        <Skeleton className="h-3 w-1/4" />
                      </div>
                      <Skeleton className="h-8 w-13 shrink-0 rounded-md" />
                    </div>
                  ))
                : authors.slice(0, 5).map((author) => (
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
                      <Button variant="outline" size="sm">
                        <Link href={`/dashboard/authors/${author.id}`}>View</Link>
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
