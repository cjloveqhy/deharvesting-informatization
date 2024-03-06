<script setup lang="ts">
import {MenuType, PermissionTree, UpdatePermissionFo} from "@/store/api/permission/types";
import {constantRouterIcon} from "@/router/icons";
import {MenuOptions, CompOptions} from "@/store/api/permission/options";
import {deepCopy} from "@/utils/copyUtil";
import {useMessage} from "naive-ui";

const props = defineProps<{show: boolean, isAdd: boolean, formData: UpdatePermissionFo, data: PermissionTree[], submit: () => Promise<void>}>()

const emits = defineEmits(['update:show', 'update:formData'])

const currentFormData = ref<UpdatePermissionFo>({
  id: null,
  parentId: null,
  name: null,
  type: MenuType.Menu,
  path: null,
  component: null,
  meta: {
    title: null
  }
})

function titlePromote(data) {
  if (data.length > 0) {
    for (const item of data) {
      item['title'] = item.meta.title
      if (item.children.length > 0) {
        titlePromote(item.children)
      } else {
        item['isLeaf'] = true
      }
    }
  }
}

const showOther = ref<boolean>(false)

const rules = ref()

function refreshRules() {
  rules.value = {
    name: { required: true, message: '请输入菜单名称', trigger: 'blur' },
    type: { required: true, message: '请选择菜单类型', trigger: 'blur' },
    parentId: { required: true, message: '请选择所属上级', trigger: 'blur' },
    meta: {
      title: { required: true, message: '请输入标题', trigger: 'blur' },
    }
  }
}

watch(
  () => currentFormData.value.type,
  (val) => {
    refreshRules()
    if (!val) return;
    if (val === MenuType.Menu) {
      rules.value['path'] = { required: true, message: '请输入请求路径', trigger: 'blur' }
    } else if ([MenuType.Button, MenuType.Content].includes(val)) {
      rules.value['meta']['permissions'] = { required: true, message: '请输入权限', trigger: 'blur' }
    }
  },{
    immediate: true
  }
)

watch(
  () => currentFormData.value.component,
  (val) => {
    if (val?.toLowerCase() === 'iframe') {
      refreshRules()
      rules.value['meta']['frameSrc'] = { required: true, message: '请输入内联外部地址', trigger: 'blur' }
    }
  }
)

const showIcons = ref<boolean>(false)

const sortOptions = ref<any[]>([])

function getSort(_, option) {
  const length = option.children.length
  sortOptions.value = []
  for (let i = 1; i <= length; i++) {
    sortOptions.value.push({label: `${i}`, value: i})
  }
  if (props.isAdd) {
    sortOptions.value.push({label: `${length + 1}`, value: length + 1})
    currentFormData.value.meta.sort = length + 1
  }
}

function getNodeByParentId(option, parentId) {
  for (const item of option) {
    if (item.id === parentId) {
      return item
    } else {
      if (item.children.length > 0) {
        const node = getNodeByParentId(item.children, parentId)
        if (node) return node
      }
    }
  }
}

const loading = ref<boolean>(false)

const message = useMessage()

function updateShow(val) {
  if (loading.value) {
    message.warning('暂时无法关闭，表单正在提交，请稍后关闭')
  } else {
    emits('update:show', val)
  }
}

watch(
  () => currentFormData.value,
  (val) => {
    emits('update:formData', val)
  },{
    deep: true
  }
)

const nodeData = ref()

function drawerInit() {
  currentFormData.value = props.formData
  nodeData.value = deepCopy(props.data)
  titlePromote(nodeData.value)
  getSort(null, getNodeByParentId(props.data, currentFormData.value.parentId))
}

function cancel() {
  updateShow(false)
  if (!loading.value) {
    currentFormData.value = {
      id: null,
      parentId: null,
      name: null,
      type: MenuType.Menu,
      path: null,
      component: null,
      meta: {
        title: null
      }
    }
  }
}

const formRef = ref();

function handleSubmit() {
  formRef.value.validate(errors => {
    if (!errors) {
      loading.value = true
      props.submit().then(() => {
        loading.value = false
        cancel()
      }).catch(() => {
        loading.value = false
      })
    }
  })
}

</script>

<template>
  <n-drawer
    :show="show"
    :width="510"
    @update:show="updateShow"
    @after-leave="showOther = false"
    @after-enter="drawerInit"
  >
    <n-drawer-content>
      <template #header>
        {{ isAdd ? '新增' : '修改' }}菜单
      </template>
      <n-form
        ref="formRef"
        size="large"
        :rules="rules"
        label-placement="top"
        :model="currentFormData"
      >
        <n-form-item label="菜单标题" path="meta.title">
          <n-input v-model:value="currentFormData.meta.title" placeholder="请输入菜单标题" />
        </n-form-item>
        <n-form-item label="菜单类型" path="type">
          <n-select
            :options="MenuOptions"
            v-model:value="currentFormData.type"
            placeholder="请选择菜单类型"
          />
        </n-form-item>
        <n-form-item label="所属上级" path="parentId">
          <n-tree-select
            key-field="id"
            label-field="title"
            :options="nodeData"
            @update:value="getSort"
            placeholder="请选择所属上级"
            v-model:value="currentFormData.parentId"
          />
        </n-form-item>
        <n-form-item label="菜单名称" path="name">
          <n-input v-model:value="currentFormData.name" placeholder="请输入菜单名称" />
        </n-form-item>
        <n-form-item label="请求路径" path="path">
          <n-input v-model:value="currentFormData.path" placeholder="请输入请求路径" />
        </n-form-item>
        <n-form-item label="组件" path="component">
          <n-select
            tag
            clearable
            filterable
            :options="CompOptions"
            placeholder="请选择组件"
            v-model:value="currentFormData.component"
          />
        </n-form-item>
        <n-collapse-transition :show="currentFormData.component?.toLowerCase() === 'iframe'">
          <n-form-item label="内联外部地址" path="meta.frameSrc">
            <n-input v-model:value="currentFormData.meta.frameSrc" placeholder="请输入内联外部地址" />
          </n-form-item>
        </n-collapse-transition>
        <n-form-item label="权限" path="meta.permissions">
          <n-select
            tag
            multiple
            filterable
            :show="false"
            :show-arrow="false"
            placeholder="请输入权限，按回车确认"
            v-model:value="currentFormData.meta.permissions"
          />
        </n-form-item>
        <n-form-item label="排序" path="meta.sort">
          <n-select
            :options="sortOptions"
            v-model:value="currentFormData.meta.sort"
          />
        </n-form-item>
        <n-form-item label="图标" path="meta.icon">
          <n-flex vertical class="w-full">
            <n-input
              readonly
              clearable
              placeholder="请选择图标"
              v-model:value="currentFormData.meta.icon"
              @focus="() => showIcons = true"
              @blur="() => showIcons = false"
            />
            <n-collapse-transition :show="showIcons">
              <n-scrollbar class="max-h-200px">
                <n-radio-group v-model:value="currentFormData.meta.icon">
                  <n-flex size="large">
                    <template v-for="item in constantRouterIcon">
                      <n-radio-button
                        :value="item.name"
                        :style="{border: 0, padding: 0}"
                        class="hover:bg-sky-100 w-30px h-30px !flex items-center justify-center"
                      >
                        <component :is="item.icon" />
                      </n-radio-button>
                    </template>
                  </n-flex>
                </n-radio-group>
              </n-scrollbar>
            </n-collapse-transition>
          </n-flex>
        </n-form-item>
        <n-form-item>
          <template #label>
            <n-flex :wrap="false" align="center">
              <span>额外配置</span>
              <n-switch v-model:value="showOther" />
            </n-flex>
          </template>
          <n-collapse-transition
            :show="showOther"
            class="border-2 rounded-md border-slate-100 border-solid p-10px"
          >
            <n-flex vertical>
              <n-flex :wrap="false" :size="40">
                <n-form-item
                  label-placement="left"
                  :label-style="{alignItems: 'center'}"
                  label="是否可用"
                  :show-feedback="false"
                  path="meta.disabled"
                >
                  <n-switch v-model:value="currentFormData.meta.disabled" :checked-value="false" :unchecked-value="true" />
                </n-form-item>
                <n-form-item
                  label-placement="left"
                  :label-style="{alignItems: 'center'}"
                  label="是否缓存"
                  :show-feedback="false"
                  path="meta.keepAlive"
                >
                  <n-switch v-model:value="currentFormData.meta.keepAlive" />
                </n-form-item>
                <n-form-item
                  label-placement="left"
                  :label-style="{alignItems: 'center'}"
                  label="隐藏菜单"
                  :show-feedback="false"
                  path="meta.hidden"
                >
                  <n-switch v-model:value="currentFormData.meta.hidden" />
                </n-form-item>
              </n-flex>
              <n-flex :wrap="false" :size="40">
                <n-form-item
                  label-placement="left"
                  :label-style="{alignItems: 'center'}"
                  label="自动计算"
                  :show-feedback="false"
                  path="meta.alwaysShow"
                >
                  <n-switch v-model:value="currentFormData.meta.alwaysShow" />
                </n-form-item>
                <n-form-item
                  label-placement="left"
                  :label-style="{alignItems: 'center'}"
                  label="根路由"
                  :show-feedback="false"
                  path="meta.isRoot"
                >
                  <n-switch v-model:value="currentFormData.meta.isRoot" />
                </n-form-item>
                <n-form-item
                  label-placement="left"
                  :label-style="{alignItems: 'center'}"
                  label="是否固定"
                  :show-feedback="false"
                  path="meta.affix"
                >
                  <n-switch v-model:value="currentFormData.meta.affix" />
                </n-form-item>
              </n-flex>
            </n-flex>
          </n-collapse-transition>
        </n-form-item>
      </n-form>
      <template #footer>
        <n-flex class="w-full">
          <n-button type="success" @click="handleSubmit" :loading="loading">提交</n-button>
          <n-button @click="cancel">取消</n-button>
        </n-flex>
      </template>
    </n-drawer-content>
  </n-drawer>
</template>
