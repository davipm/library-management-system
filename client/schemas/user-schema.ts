import z from "zod";

const UsernameSchema = z.string().min(1, "Username is required").max(50).trim();
const EmailSchema = z.string().email("Invalid email").max(100).trim().toLowerCase();
const PasswordSchema = z.string().min(6).max(100);

// REGISTER
export const RegisterUserSchema = z.object({
  username: UsernameSchema,
  email: EmailSchema,
  password: PasswordSchema,
});

export type RegisterUserDTO = z.infer<typeof RegisterUserSchema>;

// LOGIN
export const LoginUserSchema = z.object({
  email: EmailSchema,
  password: z.string().min(1, "Password is required"),
});
export type LoginUserDTO = z.infer<typeof LoginUserSchema>;

// UPDATE PROFILE (user can update own data)
export const UpdateProfileSchema = z.object({
  username: UsernameSchema.optional(),
  email: EmailSchema.optional(),
});
export type UpdateProfileDTO = z.infer<typeof UpdateProfileSchema>;

// ADMIN: UPDATE USER (including role)
export const AdminUpdateUserSchema = UpdateProfileSchema.extend({
  id: z.number().int().positive(),
  role: z.enum(["USER", "ADMIN"]).optional(),
  password: PasswordSchema.optional(),
});
export type AdminUpdateUserDTO = z.infer<typeof AdminUpdateUserSchema>;

// CHANGE PASSWORD
export const ChangePasswordSchema = z.object({
  currentPassword: z.string(),
  newPassword: PasswordSchema,
}).refine((data) => data.currentPassword !== data.newPassword, {
  message: "New password must be different",
  path: ["newPassword"],
});
export type ChangePasswordDTO = z.infer<typeof ChangePasswordSchema>;

// RESPONSE (never send password!)
export const UserResponseSchema = z.object({
  id: z.number().int().positive(),
  username: z.string(),
  email: z.string().email(),
  role: z.enum(["USER", "ADMIN"]).default("USER"),
  createdAt: z.coerce.date().optional(),
  updatedAt: z.coerce.date().optional(),
});
export type UserResponse = z.infer<typeof UserResponseSchema>;
