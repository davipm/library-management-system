import z from "zod";

// Reusable
const TitleSchema = z.string().min(1, "Book title is required").max(200).trim();
const IsbnSchema = z.string().max(13).optional().or(z.literal(""));
const DescriptionSchema = z.string().max(1000).optional().or(z.literal(""));

// CREATE
export const CreateBookSchema = z.object({
  title: TitleSchema,
  isbn: IsbnSchema.transform((v) => (v === "" ? null : v)).nullable(),
  publicationDate: z.coerce.date().optional().nullable(),
  description: DescriptionSchema.transform((v) => (v === "" ? null : v)).nullable(),
  authorId: z.number().int().positive("Author is required"),
  genreId: z.number().int().positive("Genre is required"),
});

export type CreateBookDTO = z.infer<typeof CreateBookSchema>;

// UPDATE
export const UpdateBookSchema = CreateBookSchema.partial().extend({
  id: z.number().int().positive(),
});

export type UpdateBookDTO = z.infer<typeof UpdateBookSchema>;

export const BookResponseSchema = z.object({
  id: z.number().int().positive(),
  title: z.string(),
  isbn: z.string().nullable(),
  publicationDate: z.coerce.date().nullable(),
  description: z.string().nullable(),
  author: z.object({
    id: z.number(),
    name: z.string(),
  }),
  genre: z.object({
    id: z.number(),
    name: z.string(),
  }),
  createdAt: z.coerce.date().optional(),
  updatedAt: z.coerce.date().optional(),
});

export type BookResponse = z.infer<typeof BookResponseSchema>;
