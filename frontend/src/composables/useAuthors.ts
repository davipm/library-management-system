import { useQuery, useMutation, useQueryClient } from '@tanstack/vue-query'
import { authorService } from '@/services/authorService'
import type { AuthorDTO } from '@/types'

export const useAuthors = () => {
  const queryClient = useQueryClient()

  // Queries
  const useGetAllAuthors = (options = {}) => {
    return useQuery({
      queryKey: authorService.keys.lists(),
      queryFn: authorService.getAll,
      ...options,
    })
  }

  const useGetAuthorById = (id: number, options = {}) => {
    return useQuery({
      queryKey: authorService.keys.detail(id),
      queryFn: () => authorService.getById(id),
      enabled: !!id,
      ...options,
    })
  }

  // Mutations
  const useCreateAuthor = () => {
    return useMutation({
      mutationFn: authorService.create,
      onSuccess: () => {
        queryClient.invalidateQueries({ queryKey: authorService.keys.lists() })
      },
    })
  }

  const useUpdateAuthor = () => {
    return useMutation({
      mutationFn: ({ id, author }: { id: number; author: AuthorDTO }) =>
        authorService.update({ id, author }),
      onSuccess: (_, variables) => {
        queryClient.invalidateQueries({ queryKey: authorService.keys.lists() })
        queryClient.invalidateQueries({ queryKey: authorService.keys.detail(variables.id) })
      },
    })
  }

  const useDeleteAuthor = () => {
    return useMutation({
      mutationFn: authorService.delete,
      onSuccess: (_, id) => {
        queryClient.invalidateQueries({ queryKey: authorService.keys.lists() })
        queryClient.removeQueries({ queryKey: authorService.keys.detail(id) })
      },
    })
  }

  return {
    useGetAllAuthors,
    useGetAuthorById,
    useCreateAuthor,
    useUpdateAuthor,
    useDeleteAuthor,
  }
}
