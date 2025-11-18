import { useMutation, useQuery, useQueryClient } from '@tanstack/react-query';
import { genreService } from '@/services/genre-service';

export const useGenres = () => {
  const queryClient = useQueryClient();

  // Queries
  const useGetAllGenres = (options = {}) => {
    return useQuery({
      queryKey: genreService.keys.lists(),
      queryFn: genreService.getAll,
      ...options,
    });
  };

  const useGetGenreById = (id: number, options = {}) => {
    return useQuery({
      queryKey: genreService.keys.detail(id),
      queryFn: () => genreService.getById(id),
      enabled: !!id,
      ...options,
    });
  };

  // Mutations
  const useCreateGenre = () => {
    return useMutation({
      mutationFn: genreService.create,
      onSuccess: () => {
        queryClient.invalidateQueries({ queryKey: genreService.keys.lists() });
      },
    });
  };

  const useUpdateGenre = () => {
    return useMutation({
      mutationFn: genreService.update,
      onSuccess: (_, variables) => {
        queryClient.invalidateQueries({ queryKey: genreService.keys.lists() });
        queryClient.invalidateQueries({ queryKey: genreService.keys.detail(variables.id) });
      },
    });
  };

  const useDeleteGenre = () => {
    return useMutation({
      mutationFn: genreService.delete,
      onSuccess: (_, id) => {
        queryClient.invalidateQueries({ queryKey: genreService.keys.lists() });
        queryClient.removeQueries({ queryKey: genreService.keys.detail(id) });
      },
    });
  };

  return {
    useGetAllGenres,
    useGetGenreById,
    useCreateGenre,
    useUpdateGenre,
    useDeleteGenre,
  };
};
