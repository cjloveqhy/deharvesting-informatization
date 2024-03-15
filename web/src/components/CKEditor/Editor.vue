<script setup lang="ts">
import ClassicEditor from '@ckeditor/ckeditor5-editor-classic/src/classiceditor';
import {CKEditorConfig} from "@/components/CKEditor/index";
import {EditorConfig} from "ckeditor5/src/core";
import {deepMerge} from "@/utils";

const props = defineProps<{value?: string, config?: EditorConfig}>()

const emits = defineEmits(['update:value', 'ready'])

const randomId = "editor_" + Math.random() * 100000000000000000

const content = ref<string>()

const getConfig = computed(() => {
  return deepMerge(CKEditorConfig, props.config ? {...props.config} : {})
})

onMounted(() => {
  nextTick(() => {
    ClassicEditor.create(document.querySelector(`#${randomId}`), getConfig.value)
      .then(editor => {
        if (props.value) editor.setData(props.value)
        // 监听编辑器内容的变化
        editor.model.document.on('change', () => {
          content.value = editor.getData()
          emits('update:value', content.value)
          emits('ready', content.value)
        })
        console.log('editor initialization succeeded')
    })
  }).catch(() => {
    console.log("editor initialization failed")
  })
})

function getData() {
  return content.value
}

defineExpose({
  getData
})

</script>

<template>
  <div :id="randomId"></div>
</template>

<style scoped>

::v-deep(.ck-editor__editable .ck-content .powered-by-ckeditor) {
  display: none;
}

</style>