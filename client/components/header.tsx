'use client';

import { useRouter } from 'next/navigation';
import { SearchBar } from '@/components/search-bar';
import { Button } from '@/components/ui/button';
import { useAuthStore } from '@/store/auth';

export function Header() {
  const router = useRouter();
  const { user, isAuthenticated, isAdmin, logout } = useAuthStore();

  const handleLogout = () => {
    logout();
    router.push('/login');
  };

  const goToLogin = () => {
    router.push('/login');
  };

  const goToRegister = () => {
    router.push('/register');
  };

  return (
    <header className="bg-white shadow-sm border-b">
      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <div className="flex justify-between h-16 items-center">
          <div className="flex items-center">
            <h1 className="text-xl font-bold text-gray-900">Library Management System</h1>
          </div>

          <div className="flex items-center space-x-4">
            {isAuthenticated && <SearchBar onSearch={(query) => console.log('Search:', query)} />}

            {isAuthenticated ? (
              <div className="flex items-center space-x-4">
                <div className="flex items-center space-x-2">
                  <span className="text-sm text-gray-700">{user?.username}</span>
                  {isAdmin && (
                    <span className="inline-flex items-center px-2.5 py-0.5 rounded-full text-xs font-medium bg-blue-100 text-blue-800">
                      Admin
                    </span>
                  )}
                </div>
                <Button variant="outline" size="sm" onClick={handleLogout}>
                  Logout
                </Button>
              </div>
            ) : (
              <div className="flex items-center space-x-2">
                <Button
                  variant="default"
                  size="sm"
                  onClick={goToLogin}
                  className="hover:cursor-pointer"
                >
                  Login
                </Button>
                <Button
                  variant="default"
                  size="sm"
                  onClick={goToRegister}
                  className="hover:cursor-pointer"
                >
                  Register
                </Button>
              </div>
            )}
          </div>
        </div>
      </div>
    </header>
  );
}
