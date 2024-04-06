<script setup lang="ts">
import LineType from "@/components/LineType/LineType.vue";

const props = defineProps<{mark: string}>()

</script>

<template>
  <n-flex :wrap="false" class="w-full h-87vh">
    <LineType mark="contractTerms" :type="props.mark" class="w-15% h-full" />
    <n-card class="w-85%">
      <slot />
    </n-card>
  </n-flex>
</template>
