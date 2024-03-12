<script setup lang="ts">
import { CaretUp, CaretDown } from '@vicons/ionicons5'

const props = defineProps<{order: 'descend' | 'ascend' | false}>()

</script>

<template>
  <n-flex vertical :size="0">
    <n-icon :component="CaretUp" :color="props.order === 'ascend' ? '#4098fc' : '#c2c2c2'" />
    <n-icon :component="CaretDown" :color="props.order === 'descend' ? '#4098fc' : '#c2c2c2'" />
  </n-flex>
</template>
