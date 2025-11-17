'use client';

import {
  BookOpenIcon,
  HomeIcon,
  PencilIcon,
  PlusCircleIcon,
  TagIcon,
  UserGroupIcon,
  UserIcon,
} from '@heroicons/react/24/outline';
import Link from 'next/link';
import { usePathname } from 'next/navigation';
import type { ComponentType } from 'react';
import { useAuthStore } from '@/store/auth';

interface NavItem {
  name: string;
  href: string;
  icon: ComponentType<{ className?: string }>;
}

export default function Sidebar() {
  const pathname = usePathname();
  const { isAdmin } = useAuthStore();

  const navigation: NavItem[] = [
    { name: 'Dashboard', href: '/dashboard', icon: HomeIcon },
    { name: 'Books', href: '/dashboard/books', icon: BookOpenIcon },
    { name: 'Authors', href: '/dashboard/authors', icon: UserGroupIcon },
    { name: 'Genres', href: '/dashboard/genres', icon: TagIcon },
    { name: 'Profile', href: '/dashboard/profile', icon: UserIcon },
  ];

  const adminNavigation: NavItem[] = [
    { name: 'Add Book', href: '/dashboard/books/create', icon: PlusCircleIcon },
    { name: 'Add Author', href: '/dashboard/authors/create', icon: PlusCircleIcon },
    { name: 'Add Genre', href: '/dashboard/genres/create', icon: PlusCircleIcon },
    { name: 'Edit Books', href: '/dashboard/books', icon: PencilIcon },
    { name: 'Edit Authors', href: '/dashboard/authors', icon: PencilIcon },
    { name: 'Edit Genres', href: '/dashboard/genres', icon: PencilIcon },
  ];

  const getLinkClass = (href: string) => {
    return pathname === href
      ? 'bg-gray-100 text-gray-900'
      : 'text-gray-600 hover:bg-gray-50 hover:text-gray-900';
  };

  const getIconClass = (href: string) => {
    return pathname === href ? 'text-gray-500' : 'text-gray-400 group-hover:text-gray-500';
  };

  return (
    <aside className="w-64 bg-white border-r h-screen sticky top-0">
      <div className="p-4 border-b">
        <h2 className="text-lg font-semibold text-gray-800">Library Management</h2>
      </div>

      <nav className="mt-5 px-2">
        <div className="space-y-1">
          {navigation.map((item) => {
            const Icon = item.icon;
            return (
              <Link
                key={item.name}
                href={item.href}
                className={`group flex items-center px-2 py-2 text-sm font-medium rounded-md transition-colors duration-200 ${getLinkClass(
                  item.href,
                )}`}
              >
                <Icon className={`mr-3 h-6 w-6 ${getIconClass(item.href)}`} aria-hidden="true" />
                {item.name}
              </Link>
            );
          })}
        </div>
      </nav>

      {isAdmin && (
        <div className="mt-8 px-4">
          <h3 className="text-xs font-semibold text-gray-500 uppercase tracking-wider">Admin</h3>
          <div className="mt-2 space-y-1">
            {adminNavigation.map((item) => {
              const Icon = item.icon;
              return (
                <Link
                  key={item.name}
                  href={item.href}
                  className={`group flex items-center px-2 py-2 text-sm font-medium rounded-md transition-colors duration-200 ${getLinkClass(
                    item.href,
                  )}`}
                >
                  <Icon className={`mr-3 h-6 w-6 ${getIconClass(item.href)}`} aria-hidden="true" />
                  {item.name}
                </Link>
              );
            })}
          </div>
        </div>
      )}
    </aside>
  );
}
