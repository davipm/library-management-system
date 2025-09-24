<script setup lang="ts">
import { ref, onMounted, onUnmounted } from 'vue'

const searchQuery = ref('')

const emit = defineEmits<{
  (e: 'search', query: string): void
}>()

const handleSearch = () => {
  emit('search', searchQuery.value)
}

// Keyboard shortcut for search
const handleKeyDown = (event: KeyboardEvent) => {
  if ((event.ctrlKey || event.metaKey) && event.key === 'k') {
    event.preventDefault()
    // Focus the search input
    const searchInput = document.querySelector('input[type="text"]') as HTMLInputElement
    if (searchInput) {
      searchInput.focus()
    }
  }
}

onMounted(() => {
  document.addEventListener('keydown', handleKeyDown)
})

onUnmounted(() => {
  document.removeEventListener('keydown', handleKeyDown)
})
</script>

<template>
  <div class="relative">
    <div class="absolute inset-y-0 left-0 pl-3 flex items-center pointer-events-none">
      <svg class="h-5 w-5 text-gray-400" fill="currentColor" viewBox="0 0 20 20">
        <path
          fill-rule="evenodd"
          d="M8 4a4 4 0 100 8 4 4 0 000-8zM2 8a6 6 0 1110.89 3.476l4.817 4.817a1 1 0 01-1.414 1.414l-4.816-4.816A6 6 0 012 8z"
          clip-rule="evenodd"
        />
      </svg>
    </div>
    <input
      type="text"
      v-model="searchQuery"
      class="block w-full pl-10 pr-3 py-2 border border-gray-300 rounded-md leading-5 bg-white placeholder-gray-500 focus:outline-none focus:placeholder-gray-400 focus:ring-1 focus:ring-blue-500 focus:border-blue-500 sm:text-sm"
      placeholder="Search..."
      @input="handleSearch"
    />
  </div>
</template>
