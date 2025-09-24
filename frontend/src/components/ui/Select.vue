<template>
  <div class="relative">
    <select
      :value="modelValue"
      @change="handleChange"
      :disabled="disabled || loading"
      :class="selectClasses"
      v-bind="$attrs"
    >
      <option value="" v-if="placeholder">{{ placeholder }}</option>
      <option v-for="option in options" :key="option.value" :value="option.value">
        {{ option.label }}
      </option>
    </select>

    <!-- Loading indicator -->
    <div v-if="loading" class="absolute inset-y-0 right-0 flex items-center pr-2">
      <p>...Loading</p>
    </div>

    <!-- Error indicator -->
    <div v-if="error" class="absolute inset-y-0 right-0 flex items-center pr-8">
      <svg class="h-5 w-5 text-destructive" fill="currentColor" viewBox="0 0 20 20">
        <path
          fill-rule="evenodd"
          d="M18 10a8 8 0 11-16 0 8 8 0 0116 0zm-7 4a1 1 0 11-2 0 1 1 0 012 0zm-1-9a1 1 0 00-1 1v4a1 1 0 102 0V6a1 1 0 00-1-1z"
          clip-rule="evenodd"
        />
      </svg>
    </div>
  </div>
</template>

<script setup lang="ts">
import { computed } from 'vue'

interface Option {
  value: string | number
  label: string
}

interface Props {
  modelValue?: string | number
  options: Option[]
  error?: boolean
  disabled?: boolean
  loading?: boolean
  placeholder?: string
}

const props = withDefaults(defineProps<Props>(), {
  modelValue: '',
  error: false,
  disabled: false,
  loading: false,
})

const emit = defineEmits<{
  (e: 'update:modelValue', value: string | number): void
}>()

const selectClasses = computed(() => {
  const baseClasses =
    'block w-full rounded-md border border-input bg-background px-3 py-2 text-sm ring-offset-background file:border-0 file:bg-transparent file:text-sm file:font-medium placeholder:text-muted-foreground focus-visible:outline-none focus-visible:ring-2 focus-visible:ring-ring focus-visible:ring-offset-2 disabled:cursor-not-allowed disabled:opacity-50'

  const errorClass = props.error ? 'border-destructive' : ''
  const loadingClass = props.loading ? 'pr-10' : ''

  return `${baseClasses} ${errorClass} ${loadingClass}`
})

const handleChange = (event: Event) => {
  const target = event.target as HTMLSelectElement
  emit('update:modelValue', target.value)
}
</script>
