<script setup lang="ts">
import { ref, reactive, computed, watch } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { useGenres } from '@/composables/useGenres'
import type { GenreDTO } from '@/types'
import Card from '@/components/ui/Card.vue'
import { Input } from '@/components/ui/input'
import { Button } from '@/components/ui/button'

const route = useRoute()
const router = useRouter()
const { useGetGenreById, useCreateGenre, useUpdateGenre } = useGenres()

const isEditing = computed(() => !!route.params.id)
const genreId = computed(() => Number(route.params.id))

const form = reactive<GenreDTO>({
  name: '',
  description: '',
})

const errors = ref<Record<string, string>>({})
const errorMessage = ref('')
const isSubmitting = ref(false)

// Get genre data if editing
const genreQuery = useGetGenreById(genreId.value, {
  enabled: isEditing.value,
})

// Watch for genre data when editing
watch(
  () => genreQuery.data?.value,
  (genre) => {
    if (genre) {
      form.name = genre.name
      form.description = genre.description || ''
    }
  },
  { immediate: true },
)

const validateForm = (): boolean => {
  errors.value = {}

  if (!form.name.trim()) {
    errors.value.name = 'Name is required'
  }

  return Object.keys(errors.value).length === 0
}

const handleSubmit = async () => {
  if (!validateForm()) return

  isSubmitting.value = true
  errorMessage.value = ''

  try {
    let result

    if (isEditing.value) {
      result = await useUpdateGenre().mutateAsync({
        id: genreId.value,
        genre: form,
      })
    } else {
      result = await useCreateGenre().mutateAsync(form)
    }

    if (result) {
      router.push('/dashboard/genres')
    }
  } catch (error: any) {
    errorMessage.value = error.response?.data?.message || 'Operation failed'
  } finally {
    isSubmitting.value = false
  }
}
</script>

<template>
  <div class="space-y-6">
    <div>
      <h1 class="text-3xl font-bold tracking-tight">
        {{ isEditing ? 'Edit Genre' : 'Add Genre' }}
      </h1>
      <p class="text-muted-foreground">
        {{ isEditing ? 'Update genre details' : 'Create a new genre' }}
      </p>
    </div>

    <Card>
      <form @submit.prevent="handleSubmit" class="space-y-6">
        <div class="grid grid-cols-1 md:grid-cols-2 gap-6">
          <div class="md:col-span-2">
            <label for="name" class="block text-sm font-medium text-gray-700">Name *</label>
            <div class="mt-1">
              <Input
                id="name"
                v-model="form.name"
                :error="!!errors.name"
                placeholder="Enter genre name"
                required
              />
              <p v-if="errors.name" class="mt-1 text-sm text-destructive">{{ errors.name }}</p>
            </div>
          </div>

          <div class="md:col-span-2">
            <label for="description" class="block text-sm font-medium text-gray-700"
              >Description</label
            >
            <div class="mt-1">
              <textarea
                id="description"
                v-model="form.description"
                rows="4"
                class="flex w-full rounded-md border border-input bg-background px-3 py-2 text-sm ring-offset-background file:border-0 file:bg-transparent file:text-sm file:font-medium placeholder:text-muted-foreground focus-visible:outline-none focus-visible:ring-2 focus-visible:ring-ring focus-visible:ring-offset-2 disabled:cursor-not-allowed disabled:opacity-50 min-h-[80px]"
                placeholder="Enter genre description"
              />
            </div>
          </div>
        </div>

        <div class="flex items-center justify-end space-x-4">
          <Button type="button" variant="outline" @click="router.back()"> Cancel </Button>
          <Button type="submit" :disabled="isSubmitting">
            <span v-if="isSubmitting">Saving...</span>
            <span v-else>{{ isEditing ? 'Update Genre' : 'Create Genre' }}</span>
          </Button>
        </div>

        <div v-if="errorMessage" class="text-center text-sm text-destructive">
          {{ errorMessage }}
        </div>
      </form>
    </Card>
  </div>
</template>
