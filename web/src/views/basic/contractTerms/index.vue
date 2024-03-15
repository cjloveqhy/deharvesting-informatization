<script setup lang="ts">
import LineType from "@/components/LineType/LineType.vue";
import Editor from "@/components/CKEditor/Editor.vue";

const ed = ref()

const config = ref({
  placeholder: '请输入...',
})

const text = ref('<p>nihao<p>')

</script>

<template>
  <n-flex :wrap="false" class="w-full h-87vh">
    <LineType mark="contractTerms" type="contractTermsCottonFarmer" class="w-15% h-full"></LineType>
    <n-card class="w-85%">
      <editor ref="ed" v-model:value="text" :config="config" />
    </n-card>
  </n-flex>
</template>
