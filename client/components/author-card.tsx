'use client';

import { useRouter } from 'next/navigation';
import { Button } from '@/components/ui/button';
import { Card, CardContent, CardFooter, CardHeader } from '@/components/ui/card';
import { useAuthStore } from '@/store/auth';
import type { Author } from '@/types';

interface AuthorCardProps {
  author: Author;
}

export default function AuthorCard({ author }: AuthorCardProps) {
  const router = useRouter();
  const { isAdmin } = useAuthStore();

  const viewDetails = () => {
    router.push(`/dashboard/authors/${author.id}`);
  };

  const editAuthor = () => {
    router.push(`/dashboard/authors/${author.id}/edit`);
  };

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
          <Button variant="outline" size="sm" onClick={viewDetails}>
            View Details
          </Button>
          {isAdmin && (
            <Button variant="outline" size="sm" onClick={editAuthor}>
              Edit
            </Button>
          )}
        </div>
      </CardFooter>
    </Card>
  );
}
