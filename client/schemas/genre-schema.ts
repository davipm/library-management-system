import z from "zod";

const NameSchema = z.string().min(1, "Genre name is required").max(100).trim();
const DescSchema = z.string().max(500).optional().or(z.literal(""));

// CREATE
export const CreateGenreSchema = z.object({
  name: NameSchema,
  description: DescSchema.transform((v) => (v === "" ? null : v)).nullable(),
});

export type CreateGenreDTO = z.infer<typeof CreateGenreSchema>;

// UPDATE
export const UpdateGenreSchema = CreateGenreSchema.partial().extend({
  id: z.number().int().positive(),
});

export type UpdateGenreDTO = z.infer<typeof UpdateGenreSchema>;

// RESPONSE
export const GenreResponseSchema = z.object({
  id: z.number().int().positive(),
  name: z.string(),
  description: z.string().nullable(),
  books: z.array(
    z.object({
      id: z.number(),
      title: z.string(),
    })
  ).default([]),
  createdAt: z.coerce.date().optional(),
  updatedAt: z.coerce.date().optional(),
});

export type GenreResponse = z.infer<typeof GenreResponseSchema>;
