import Cookies from 'js-cookie';
import api from '@/services/api';
import type { AuthResponse, LoginRequest, RegisterRequest, User } from '@/types';

const TOKEN_KEY = 'authToken';

class AuthService {
  public async login(credentials: LoginRequest): Promise<AuthResponse> {
    const response = await api.post<AuthResponse>('/api/v1/auth/login', credentials);
    this.setAuthToken(response.data.token);
    return response.data;
  }

  public async register(userData: RegisterRequest): Promise<User> {
    const response = await api.post<User>('/api/v1/auth/register', userData);
    return response.data;
  }

  public async getCurrentUser(): Promise<User> {
    const response = await api.get<User>('/api/v1/auth/me');
    return response.data;
  }

  public logout(): void {
    this.removeAuthToken();
  }

  public isAuthenticated(): boolean {
    return !!this.getAuthToken();
  }

  public setAuthToken(token: string): void {
    Cookies.set(TOKEN_KEY, token, {
      expires: 7,
      path: '/',
      // Optional: secure in production
      // secure: process.env.NODE_ENV === 'production',
    });
  }

  public removeAuthToken(): void {
    Cookies.remove(TOKEN_KEY, { path: '/' });
  }

  public getAuthToken(): string | null {
    return Cookies.get(TOKEN_KEY) || null;
  }

  public getAuthTokenFromServer() {
    const { cookies } = require('next/headers');
    return cookies().get(TOKEN_KEY)?.value || null;
  }
}

export default new AuthService();
