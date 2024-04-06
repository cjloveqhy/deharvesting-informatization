<template>
  <n-flex align="center" justify="center">
    <img class="w-auto h-32px" :src="websiteConfig.logo" alt="" :class="{ 'mr-2': !collapsed }" />
    <h2 v-show="!collapsed">{{ websiteConfig.title }}</h2>
  </n-flex>
</template>

<script setup lang="ts">
import {websiteConfig} from '@/config/website.config';

const props = defineProps<{collapsed: boolean}>()

</script>
