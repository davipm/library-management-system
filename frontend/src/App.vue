<script setup lang="ts">
import { VueQueryDevtools } from '@tanstack/vue-query-devtools'
import { RouterView } from 'vue-router'
</script>

<template>
  <div id="app" class="min-h-screen">
    <RouterView />
  </div>
  <VueQueryDevtools />
</template>
