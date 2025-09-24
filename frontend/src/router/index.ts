import { createRouter, createWebHistory, type RouteRecordRaw } from 'vue-router'
import { useAuthStore } from '@/stores/auth'

const routes: Array<RouteRecordRaw> = [
  {
    path: '/',
    redirect: '/dashboard',
  },
  {
    path: '/login',
    name: 'Login',
    component: () => import('@/pages/auth/Login.vue'),
    meta: { requiresGuest: true },
  },
  {
    path: '/register',
    name: 'Register',
    component: () => import('@/pages/auth/Register.vue'),
    meta: { requiresGuest: true },
  },
  {
    path: '/dashboard',
    component: () => import('@/layouts/DashboardLayout.vue'),
    meta: { requiresAuth: true },
    children: [
      {
        path: '',
        name: 'Dashboard',
        component: () => import('@/pages/dashboard/Dashboard.vue'),
      },
      {
        path: 'profile',
        name: 'Profile',
        component: () => import('@/pages/dashboard/Profile.vue'),
      },
      {
        path: 'books',
        children: [
          {
            path: '',
            name: 'BooksList',
            component: () => import('@/pages/books/BooksList.vue'),
          },
          {
            path: 'create',
            name: 'BookCreate',
            component: () => import('@/pages/books/BookForm.vue'),
            meta: { requiresAdmin: true },
          },
          {
            path: ':id',
            name: 'BookDetail',
            component: () => import('@/pages/books/BookDetail.vue'),
            props: true,
          },
          {
            path: ':id/edit',
            name: 'BookEdit',
            component: () => import('@/pages/books/BookForm.vue'),
            props: true,
            meta: { requiresAdmin: true },
          },
        ],
      },
      {
        path: 'authors',
        children: [
          {
            path: '',
            name: 'AuthorsList',
            component: () => import('@/pages/authors/AuthorsList.vue'),
          },
          {
            path: 'create',
            name: 'AuthorCreate',
            component: () => import('@/pages/authors/AuthorForm.vue'),
            meta: { requiresAdmin: true },
          },
          {
            path: ':id',
            name: 'AuthorDetail',
            component: () => import('@/pages/authors/AuthorDetail.vue'),
            props: true,
          },
          {
            path: ':id/edit',
            name: 'AuthorEdit',
            component: () => import('@/pages/authors/AuthorForm.vue'),
            props: true,
            meta: { requiresAdmin: true },
          },
        ],
      },
      {
        path: 'genres',
        children: [
          {
            path: '',
            name: 'GenresList',
            component: () => import('@/pages/genres/GenresList.vue'),
          },
          {
            path: 'create',
            name: 'GenreCreate',
            component: () => import('@/pages/genres/GenreForm.vue'),
            meta: { requiresAdmin: true },
          },
          {
            path: ':id',
            name: 'GenreDetail',
            component: () => import('@/pages/genres/GenreDetail.vue'),
            props: true,
          },
          {
            path: ':id/edit',
            name: 'GenreEdit',
            component: () => import('@/pages/genres/GenreForm.vue'),
            props: true,
            meta: { requiresAdmin: true },
          },
        ],
      },
    ],
  },
  {
    path: '/:pathMatch(.*)*',
    name: 'NotFound',
    component: () => import('@/pages/NotFound.vue'),
  },
]

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes,
})

// Navigation guards
router.beforeEach(async (to, from, next) => {
  const authStore = useAuthStore()

  // Initialize auth if not already done
  if (!authStore.isAuthenticated && localStorage.getItem('authToken')) {
    await authStore.initializeAuth()
  }

  const requiresAuth = to.matched.some((record) => record.meta.requiresAuth)
  const requiresGuest = to.matched.some((record) => record.meta.requiresGuest)
  const requiresAdmin = to.matched.some((record) => record.meta.requiresAdmin)

  if (requiresAuth && !authStore.isAuthenticated) {
    next('/login')
  } else if (requiresGuest && authStore.isAuthenticated) {
    next('/dashboard')
  } else if (requiresAdmin && !authStore.isAdmin) {
    next('/dashboard')
  } else {
    next()
  }
})

export default router
