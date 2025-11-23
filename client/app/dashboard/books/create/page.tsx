'use client';

import { zodResolver } from '@hookform/resolvers/zod';
import { ArrowLeft, Loader2 } from 'lucide-react';
import { useParams, useRouter } from 'next/navigation';
import { useEffect } from 'react';
import { type SubmitHandler, useForm, type Resolver } from 'react-hook-form';
import { Button } from '@/components/ui/button';
import { Card, CardContent, CardHeader, CardTitle } from '@/components/ui/card';
import { Input } from '@/components/ui/input';
import { Label } from '@/components/ui/label';
import {
  Select,
  SelectContent,
  SelectItem,
  SelectTrigger,
  SelectValue,
} from '@/components/ui/select';
import { Textarea } from '@/components/ui/textarea';
import { useAuthors } from '@/hooks/use-authors';
import { useBooks } from '@/hooks/use-books';
import { useGenres } from '@/hooks/use-genres';
import { CreateBookDTO, CreateBookSchema } from '@/schemas/book-schema';

export type BookFormValues = Omit<CreateBookDTO, 'publicationDate'> & {
  publicationDate: string | null;
};

export default function Page() {
  const router = useRouter();
  const params = useParams();

  const isEditing = params.id && params.id !== 'new';
  const bookId = params.id as string;

  const { useGetAllAuthors } = useAuthors();
  const { useGetAllGenres } = useGenres();
  const { useGetBookById, useCreateBook, useUpdateBook } = useBooks();

  const { data: authors = [], isLoading: loadingAuthors } = useGetAllAuthors();
  const { data: genres = [], isLoading: loadingGenres } = useGetAllGenres();

  const { data: book } = useGetBookById(Number(bookId), {
    enabled: isEditing,
  });

  const { mutate: updateBookMutation, isPending: updateIsPending } = useUpdateBook();
  const { mutate: createBookMutation, isPending: createIsPending } = useCreateBook();

  const {
    register,
    handleSubmit,
    setValue,
    watch,
    formState: { errors, isSubmitting },
  } = useForm<BookFormValues>({
    resolver: zodResolver(CreateBookSchema) as unknown as Resolver<BookFormValues>,
    defaultValues: {
      title: '',
      isbn: '',
      publicationDate: null,
      description: '',
      authorId: 0,
      genreId: 0,
    },
  });

  useEffect(() => {
    if (book && isEditing) {
      setValue('title', book.title);
      setValue('isbn', book.isbn || '');
      setValue('publicationDate', book.publicationDate ? new Date(book.publicationDate).toISOString().split('T')[0] : null);
      setValue('description', book.description || '');
      setValue('authorId', book.authorId);
      setValue('genreId', book.genreId);
    }
  }, [book, isEditing, setValue]);

  const onSuccess = () => {
    return router.push('/dashboard');
  };

  const onSubmit: SubmitHandler<BookFormValues> = (data) => {
    if (isEditing) {
      updateBookMutation({ id: Number(bookId), book: data }, { onSuccess });
    } else {
      createBookMutation(data, { onSuccess});
    }
  };

  const isLoading = loadingAuthors || loadingGenres || (isEditing && !book);

  if (isLoading) {
    return (
      <div className="flex items-center justify-center h-64">
        <Loader2 className="h-8 w-8 animate-spin" />
      </div>
    );
  }

  return (
    <div className="space-y-6 max-w-4xl mx-auto">
      <div>
        <h1 className="text-3xl font-bold tracking-tight">
          {isEditing ? 'Edit Book' : 'Add New Book'}
        </h1>
        <p className="text-muted-foreground">
          {isEditing ? 'Update book information' : 'Create a new book entry'}
        </p>
      </div>

      <Card>
        <CardHeader>
          <CardTitle>Book Details</CardTitle>
        </CardHeader>
        <CardContent>
          <form onSubmit={handleSubmit(onSubmit)} className="space-y-6">
            <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
              <div>
                <Label htmlFor="title">
                  Title <span className="text-destructive">*</span>
                </Label>
                <Input
                  id="title"
                  placeholder="Enter book title"
                  {...register('title')}
                  className="mt-1"
                />
                {errors.title && (
                  <p className="mt-1 text-sm text-destructive">{errors.title.message}</p>
                )}
              </div>

              {/* ISBN */}
              <div>
                <Label htmlFor="isbn">ISBN</Label>
                <Input
                  id="isbn"
                  placeholder="e.g. 978-3-16-148410-0"
                  {...register('isbn')}
                  className="mt-1"
                />
              </div>

              {/* Author */}
              <div>
                <Label htmlFor="authorId">
                  Author <span className="text-destructive">*</span>
                </Label>
                <Select
                  onValueChange={(value) => setValue('authorId', Number(value))}
                  value={watch('authorId')?.toString()}
                >
                  <SelectTrigger className="mt-1">
                    <SelectValue placeholder="Select author" />
                  </SelectTrigger>
                  <SelectContent>
                    {authors.map((author: any) => (
                      <SelectItem key={author.id} value={author.id.toString()}>
                        {author.name}
                      </SelectItem>
                    ))}
                  </SelectContent>
                </Select>
                {errors.authorId && (
                  <p className="mt-1 text-sm text-destructive">{errors.authorId.message}</p>
                )}
              </div>

              {/* Genre */}
              <div>
                <Label htmlFor="genreId">
                  Genre <span className="text-destructive">*</span>
                </Label>
                <Select
                  onValueChange={(value) => setValue('genreId', Number(value))}
                  value={watch('genreId')?.toString()}
                >
                  <SelectTrigger className="mt-1">
                    <SelectValue placeholder="Select genre" />
                  </SelectTrigger>
                  <SelectContent>
                    {genres.map((genre: any) => (
                      <SelectItem key={genre.id} value={genre.id.toString()}>
                        {genre.name}
                      </SelectItem>
                    ))}
                  </SelectContent>
                </Select>
                {errors.genreId && (
                  <p className="mt-1 text-sm text-destructive">{errors.genreId.message}</p>
                )}
              </div>

              {/* Publication Date */}
              <div className="md:col-span-2">
                <Label htmlFor="publicationDate">Publication Date</Label>
                <Input
                  id="publicationDate"
                  type="date"
                  {...register('publicationDate')}
                  className="mt-1 w-full md:w-64"
                />
              </div>

              {/* Description */}
              <div className="md:col-span-2">
                <Label htmlFor="description">Description</Label>
                <Textarea
                  id="description"
                  placeholder="Enter book description (optional)"
                  rows={4}
                  {...register('description')}
                  className="mt-1"
                />
              </div>
            </div>

            {/* Actions */}
            <div className="flex justify-end gap-4 pt-6 border-t">
              <Button
                type="button"
                variant="outline"
                onClick={() => router.back()}
                disabled={isSubmitting}
              >
                <ArrowLeft className="mr-2 h-4 w-4" />
                Cancel
              </Button>
              <Button
                type="submit"
                disabled={isSubmitting || createIsPending || updateIsPending}
              >
                {isSubmitting || createIsPending || updateIsPending? (
                  <>
                    <Loader2 className="mr-2 h-4 w-4 animate-spin" />
                    Saving...
                  </>
                ) : (
                  <>{isEditing ? 'Update Book' : 'Create Book'}</>
                )}
              </Button>
            </div>

            {/* Global Error */}
            {/*{(createBookMutation.error || updateBookMutation.error) && (*/}
            {/*  <p className="text-center text-sm text-destructive">*/}
            {/*    {(createBookMutation.error || updateBookMutation.error)?.message || 'Something went wrong'}*/}
            {/*  </p>*/}
            {/*)}*/}
          </form>
        </CardContent>
      </Card>
    </div>
  );
}
