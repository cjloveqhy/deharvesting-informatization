<script setup lang="ts">
import LineType from "@/components/LineType/LineType.vue";
import Editor from "@/components/CKEditor/Editor.vue";
import {Autosave} from "@ckeditor/ckeditor5-autosave";
import {getContent, save} from "@/api/contractTerms";
import {ContractTermsType} from "@/store/api/contractTerms";

const props = defineProps<{mark: string, type: ContractTermsType}>()

const content = ref<string>('')

const editorConfig = ref({
  extraPlugins: [
    Autosave
  ],
  autosave: {
    save: (editor) => {
      return new Promise((resolve, reject) => {
        save({
          type: props.type,
          content: editor.getData()
        }).then(res => {
          getContent(props.type).then(res => {
            content.value = res.data?.content
          })
          resolve(`自动保存${res.data}`)
        }).catch(() => reject('自动保存失败'))
      })
    },
    waitingTime: 5000
  }
})

const loading =ref<boolean>(false)

function init() {
  loading.value = true
  getContent(props.type).then(res => {
    content.value = res.data?.content
  }).finally(() => loading.value = false)
}

init()

</script>

<template>
  <n-flex :wrap="false" class="w-full h-87vh">
    <LineType mark="contractTerms" :type="props.mark" class="w-15% h-full" />
    <n-card class="w-85%">
      <editor v-model:value="content" :loading="loading" :config="editorConfig" />
    </n-card>
  </n-flex>
</template>
