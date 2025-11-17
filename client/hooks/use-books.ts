import { useQuery } from '@tanstack/react-query';
import { bookService } from '@/services/book-service';

export const useBooks = () => {
  const {
    data: books = [],
    isLoading: bookLoading,
    ...rest
  } = useQuery({
    queryKey: bookService.keys.list(),
    queryFn: () => bookService.getAll(),
  });

  return { books, bookLoading, ...rest };
};
