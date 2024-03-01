<script setup lang="ts">
import {EditFilled} from '@vicons/antd'

interface Props {
  value: string | number | null
  edit?: boolean
  comPlaceholder?: string
  nullPlaceholder?: string
  type?: 'input' | 'select'
  options?: any[]
}

const props = withDefaults(defineProps<Props>(), {
  edit: true,
  comPlaceholder: '请输入',
  nullPlaceholder: '无',
  type: 'input'
})

const emits = defineEmits(['update:value'])

const isEdit = ref<boolean>(false)
const compRef = ref()
const compValue = ref<string | number | null>(props.value)

function handleOnClick () {
  if (!props.edit) return;
  isEdit.value = true
  nextTick(() => {
    compRef.value.focus()
  })
}

function handleChange () {
  if (!props.edit) return;
  emits('update:value', compValue.value)
  isEdit.value = false
}

</script>

<template>
  <div class="min-h-22px w-full" :class="`${props.edit ? 'cursor-pointer' : null}`" @click="handleOnClick">
    <template v-if="isEdit">
      <template v-if="props.type === 'input'">
        <n-input
          ref="compRef"
          :placeholder="props.comPlaceholder"
          v-model:value="compValue"
          @change="handleChange"
          @blur="handleChange"
        />
      </template>
      <template v-else>
        <n-select
          ref="compRef"
          class="w-full"
          :placeholder="props.comPlaceholder"
          v-model:value="compValue"
          :options="props.options"
          @update:value="handleChange"
          @blur="handleChange"
          />
      </template>
    </template>
    <template v-else>
      <n-flex align="center">
        <slot>
          {{ props.value && props.value !== '' ? props.value : props.nullPlaceholder }}
        </slot>
        <n-icon v-if="props.edit" :size="18" :component="EditFilled" />
      </n-flex>
    </template>
  </div>
</template>
