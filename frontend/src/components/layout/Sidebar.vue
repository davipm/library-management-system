<script setup lang="ts">
import { RouterLink } from 'vue-router'
import { computed } from 'vue'
import { useAuthStore } from '@/stores/auth'
import {
  HomeIcon,
  BookOpenIcon,
  UserGroupIcon,
  TagIcon,
  UserIcon,
  PlusCircleIcon,
  PencilIcon,
} from '@heroicons/vue/24/outline'

const authStore = useAuthStore()

const isAdmin = computed(() => authStore.isAdmin)

const navigation = [
  { name: 'Dashboard', to: '/dashboard', icon: HomeIcon },
  { name: 'Books', to: '/dashboard/books', icon: BookOpenIcon },
  { name: 'Authors', to: '/dashboard/authors', icon: UserGroupIcon },
  { name: 'Genres', to: '/dashboard/genres', icon: TagIcon },
  { name: 'Profile', to: '/dashboard/profile', icon: UserIcon },
]

const adminNavigation = [
  { name: 'Add Book', to: '/dashboard/books/create', icon: PlusCircleIcon },
  { name: 'Add Author', to: '/dashboard/authors/create', icon: PlusCircleIcon },
  { name: 'Add Genre', to: '/dashboard/genres/create', icon: PlusCircleIcon },
  { name: 'Edit Books', to: '/dashboard/books', icon: PencilIcon },
  { name: 'Edit Authors', to: '/dashboard/authors', icon: PencilIcon },
  { name: 'Edit Genres', to: '/dashboard/genres', icon: PencilIcon },
]
</script>

<template>
  <aside class="w-64 bg-white border-r h-screen sticky top-0">
    <div class="p-4 border-b">
      <h2 class="text-lg font-semibold text-gray-800">Library Management</h2>
    </div>

    <nav class="mt-5 px-2">
      <div class="space-y-1">
        <RouterLink
          v-for="item in navigation"
          :key="item.name"
          :to="item.to"
          :class="[
            $route.path === item.to
              ? 'bg-gray-100 text-gray-900'
              : 'text-gray-600 hover:bg-gray-50 hover:text-gray-900',
            'group flex items-center px-2 py-2 text-sm font-medium rounded-md transition-colors duration-200',
          ]"
        >
          <component
            :is="item.icon"
            :class="[
              $route.path === item.to ? 'text-gray-500' : 'text-gray-400 group-hover:text-gray-500',
              'mr-3 h-6 w-6',
            ]"
            aria-hidden="true"
          />
          {{ item.name }}
        </RouterLink>
      </div>
    </nav>

    <div v-if="isAdmin" class="mt-8 px-4">
      <h3 class="text-xs font-semibold text-gray-500 uppercase tracking-wider">Admin</h3>
      <div class="mt-2 space-y-1">
        <router-link
          v-for="adminItem in adminNavigation"
          :key="adminItem.name"
          :to="adminItem.to"
          :class="[
            $route.path === adminItem.to
              ? 'bg-gray-100 text-gray-900'
              : 'text-gray-600 hover:bg-gray-50 hover:text-gray-900',
            'group flex items-center px-2 py-2 text-sm font-medium rounded-md transition-colors duration-200',
          ]"
        >
          <component
            :is="adminItem.icon"
            :class="[
              $route.path === adminItem.to
                ? 'text-gray-500'
                : 'text-gray-400 group-hover:text-gray-500',
              'mr-3 h-6 w-6',
            ]"
            aria-hidden="true"
          />
          {{ adminItem.name }}
        </router-link>
      </div>
    </div>
  </aside>
</template>
