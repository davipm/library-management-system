import type { NextRequest } from 'next/server';
import { NextResponse } from 'next/server';

const TOKEN_KEY = 'authToken';

const adminPaths = [
  '/dashboard/books/create',
  '/dashboard/books/[id]/edit',
  '/dashboard/authors/create',
  '/dashboard/authors/[id]/edit',
  '/dashboard/genres/create',
  '/dashboard/genres/[id]/edit',
];

function isPathMatch(path: string, pattern: string): boolean {
  if (!pattern.includes('[id]')) return path === pattern;
  const regex = new RegExp(`^${pattern.replace(/\[id\]/g, '[^/]+')}$`);
  return regex.test(path);
}

export async function middleware(request: NextRequest) {
  const path = request.nextUrl.pathname;
  const token = request.cookies.get(TOKEN_KEY)?.value;

  if (path === '/login' || path === '/register') {
    if (token) {
      return NextResponse.redirect(new URL('/dashboard', request.url));
    }
    return NextResponse.next();
  }

  const isAdminRoute = adminPaths.some((p) => isPathMatch(path, p));
  if (isAdminRoute) {
    if (!token) return NextResponse.redirect(new URL('/login', request.url));
    // ⚠️ For full security, verify token payload here (e.g., decode JWT to check role)
    // For now, we assume if token exists, role check happens in API
    // But best practice: verify role in middleware if possible
  }

  if (path.startsWith('/dashboard')) {
    if (!token) {
      return NextResponse.redirect(new URL('/login', request.url));
    }
  }

  return NextResponse.next();
}

export const config = {
  matcher: ['/login', '/register', '/dashboard/:path*'],
};
