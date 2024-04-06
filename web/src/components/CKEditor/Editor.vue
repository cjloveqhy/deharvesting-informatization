<script setup lang="ts">
import ClassicEditor from '@ckeditor/ckeditor5-editor-classic/src/classiceditor';
import {CKEditorConfig} from "@/components/CKEditor/index";
import {EditorConfig} from "ckeditor5/src/core";
import {deepMerge} from "@/utils";

interface Props {
  value?: string,
  loading?: boolean,
  config?: EditorConfig
}

const props = withDefaults(defineProps<Props>(), {
  loading: false
})

const emits = defineEmits(['update:value', 'ready'])

const randomId = "editor_" + Math.random() * 100000000000000000

const content = ref<string>()

const getConfig = computed(() => {
  return deepMerge(CKEditorConfig, props.config ? {...props.config} : {})
})

const instance = shallowRef()

watch(
  () => props.loading,
  (val) => {
    if (!val && props.value && instance.value) {
      instance.value.setData(props.value)
    }
  }, {
    immediate: true
  }
)

onMounted(() => {
  nextTick(() => {
    // @ts-ignore
    ClassicEditor.create(document.querySelector(`#${randomId}`), getConfig.value)
      .then(editor => {
        instance.value = editor
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
  instance,
  getData
})

</script>

<template>
  <n-spin :show="props.loading">
    <div :id="randomId"></div>
  </n-spin>
</template>

<style scoped>

::v-deep(.ck-editor__editable .ck-content .powered-by-ckeditor) {
  display: block;
}
::v-deep(.ck .ck-button__label){
  font-size: 16px !important;
}
::v-deep(.ck .ck-reset .ck-list){
  overflow: auto;
  height: 15rem;
}
</style>
