import { useMutation, useQuery, useQueryClient } from '@tanstack/react-query';
import { bookService } from '@/services/book-service';

export const useBooks = () => {
  const queryClient = useQueryClient();

  // Queries
  const useGetAllBooks = (options = {}) => {
    return useQuery({
      queryKey: bookService.keys.lists(),
      queryFn: bookService.getAll,
      ...options,
    });
  };

  const useGetBookById = (id: number, options = {}) => {
    return useQuery({
      queryKey: bookService.keys.detail(id),
      queryFn: () => bookService.getById(id),
      enabled: !!id,
      ...options,
    });
  };

  // Mutations
  const useCreateBook = () => {
    return useMutation({
      mutationFn: bookService.create,
      onSuccess: async () => {
        await queryClient.invalidateQueries({ queryKey: bookService.keys.lists() });
      },
    });
  };

  const useUpdateBook = () => {
    return useMutation({
      mutationFn: bookService.update,
      onSuccess: async (_, variables) => {
        await queryClient.invalidateQueries({ queryKey: bookService.keys.lists() });
        await queryClient.invalidateQueries({ queryKey: bookService.keys.detail(variables.id) });
      },
    });
  };

  const useDeleteBook = () => {
    return useMutation({
      mutationFn: bookService.delete,
      onSuccess: async (_, id) => {
        await queryClient.invalidateQueries({ queryKey: bookService.keys.lists() });
        queryClient.removeQueries({ queryKey: bookService.keys.detail(id) });
      },
    });
  };

  return {
    useGetAllBooks,
    useGetBookById,
    useCreateBook,
    useUpdateBook,
    useDeleteBook,
  };
};
