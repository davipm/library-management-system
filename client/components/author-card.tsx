'use client';

import Link from 'next/link';
import { Button } from '@/components/ui/button';
import { Card, CardContent, CardFooter, CardHeader } from '@/components/ui/card';
import { useAuthStore } from '@/store/auth';
import type { Author } from '@/types';

interface AuthorCardProps {
  author: Author;
}

export default function AuthorCard({ author }: AuthorCardProps) {
  const { isAdmin } = useAuthStore();

  const birthYear = author.birthDate ? new Date(author.birthDate).getFullYear() : 'N/A';

  const truncatedBio = author.biography
    ? author.biography.length > 100
      ? `${author.biography.substring(0, 100)}...`
      : author.biography
    : '';

  return (
    <Card className="h-full">
      <CardHeader>
        <h3 className="font-semibold leading-none tracking-tight">{author.name}</h3>
      </CardHeader>
      <CardContent>
        <div className="space-y-2">
          <p className="text-sm text-muted-foreground">{birthYear}</p>
          <p className="text-sm">{truncatedBio}</p>
          <div className="flex items-center justify-between">
            <span className="text-xs text-muted-foreground">{author.books?.length || 0} books</span>
          </div>
        </div>
      </CardContent>
      <CardFooter>
        <div className="flex items-center justify-between space-x-2 w-full">
          <Button asChild variant="outline" size="sm">
            <Link href={`/dashboard/authors/${author.id}`}>View Details</Link>
          </Button>
          {isAdmin && (
            <Button asChild variant="outline" size="sm">
              <Link href={`/dashboard/authors/${author.id}/edit`}>Edit</Link>
            </Button>
          )}
        </div>
      </CardFooter>
    </Card>
  );
}
