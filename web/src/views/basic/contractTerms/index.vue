<script setup lang="ts">
import LineType from "@/components/LineType/LineType.vue";
import Editor from "@/components/CKEditor/Editor.vue";

const ed = ref()

const config = ref({
  placeholder: '请输入...',
})

const text = ref('<img src="https://ckeditor.com/docs/ckeditor5/latest/assets/img/waterfalls/map.jpg" srcset="https://ckeditor.com/docs/ckeditor5/latest/assets/img/waterfalls/map.jpg, https://ckeditor.com/docs/ckeditor5/latest/assets/img/waterfalls/map_2x.jpg 2x" sizes="100vw">')

</script>

<template>
  <n-flex :wrap="false" class="w-full h-87vh">
    <LineType mark="contractTerms" type="contractTermsCottonFarmer" class="w-15% h-full"></LineType>
    <n-card class="w-85%">
      <editor ref="ed" v-model:value="text" :config="config" />
    </n-card>
  </n-flex>
</template>
