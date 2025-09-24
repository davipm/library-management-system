<script setup lang="ts">
import { RouterView } from 'vue-router'
import Header from '@/components/layout/Header.vue'
import Sidebar from '@/components/layout/Sidebar.vue'
import Footer from '@/components/layout/Footer.vue'
</script>

<template>
  <div class="min-h-screen bg-gray-50">
    <Header />

    <div class="flex">
      <Sidebar />

      <main class="flex-1 pb-8">
        <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-6">
          <RouterView />
        </div>
      </main>
    </div>

    <Footer />
  </div>
</template>
