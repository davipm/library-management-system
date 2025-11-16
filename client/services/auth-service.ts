import type { AuthResponse, LoginRequest, RegisterRequest, User } from '@/types';
import api from './api';

class AuthService {
  public async login(credentials: LoginRequest): Promise<AuthResponse> {
    const response = await api.post<AuthResponse>('/api/v1/auth/login', credentials);
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
    localStorage.removeItem('authToken');
  }

  public isAuthenticated(): boolean {
    const token = localStorage.getItem('authToken');
    return !!token;
  }

  public setAuthToken(token: string): void {
    localStorage.setItem('authToken', token);
  }

  public getAuthToken(): string | null {
    return localStorage.getItem('authToken');
  }
}

export default new AuthService();
