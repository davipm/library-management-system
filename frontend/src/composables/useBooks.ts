import { useQuery, useMutation, useQueryClient } from '@tanstack/vue-query'
import { bookService } from '@/services/bookService'

export const useBooks = () => {
  const queryClient = useQueryClient()

  // Queries
  const useGetAllBooks = (options = {}) => {
    return useQuery({
      queryKey: bookService.keys.lists(),
      queryFn: bookService.getAll,
      ...options,
    })
  }

  const useGetBookById = (id: number, options = {}) => {
    return useQuery({
      queryKey: bookService.keys.detail(id),
      queryFn: () => bookService.getById(id),
      enabled: !!id,
      ...options,
    })
  }

  // Mutations
  const useCreateBook = () => {
    return useMutation({
      mutationFn: bookService.create,
      onSuccess: () => {
        queryClient.invalidateQueries({ queryKey: bookService.keys.lists() })
      },
    })
  }

  const useUpdateBook = () => {
    return useMutation({
      mutationFn: bookService.update,
      onSuccess: (_, variables) => {
        queryClient.invalidateQueries({ queryKey: bookService.keys.lists() })
        queryClient.invalidateQueries({ queryKey: bookService.keys.detail(variables.id) })
      },
    })
  }

  const useDeleteBook = () => {
    return useMutation({
      mutationFn: bookService.delete,
      onSuccess: (_, id) => {
        queryClient.invalidateQueries({ queryKey: bookService.keys.lists() })
        queryClient.removeQueries({ queryKey: bookService.keys.detail(id) })
      },
    })
  }

  return {
    useGetAllBooks,
    useGetBookById,
    useCreateBook,
    useUpdateBook,
    useDeleteBook,
  }
}
