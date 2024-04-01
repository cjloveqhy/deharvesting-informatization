<script setup lang="ts">
import {useMessage} from "naive-ui";
import {AddUserRoleFo, UpdateUserRoleFo} from "@/store/api/userRole";
import {SexEnum, SexOptions} from "@/store/api/user";
import {getTree} from "@/api/permission";
import {deepCopy} from "@/utils/copyUtil";
import {Ref} from "vue";
import {getTreeAll} from "@/utils";
import {getOptions} from "@/api/system/role";
import {BasicOption, emailSuffix} from "@/store/common";

const props = withDefaults(defineProps<{show?: boolean, isAdd?: boolean, formData: UpdateUserRoleFo, submit: () => Promise<void>}>(), {
  show: false,
  isAdd: false
})

const emits = defineEmits(['update:show', 'update:formData'])

const currentFormData = ref<AddUserRoleFo | UpdateUserRoleFo>({
  id: null,
  username: null,
  photo: null,
  sex: SexEnum.Unknown,
  account: null,
  phone: null,
  email: null,
  roleId: null,
  attachedPermission: []
})

const loading = ref<boolean>(false)

const message = useMessage()

function updateShow(val) {
  if (loading.value) {
    message.warning('暂时无法关闭，表单正在提交，请稍后关闭')
  } else {
    emits('update:show', val)
  }
}

function drawerInit() {
  currentFormData.value = props.formData
}

function cancel() {
  updateShow(false)
  if (!loading.value) {
    currentFormData.value = {
      id: null,
      username: null,
      photo: null,
      sex: SexEnum.Unknown,
      account: null,
      phone: null,
      email: null,
      roleId: null,
      attachedPermission: []
    }
  }
}

const rules = {
  account: { required: true, message: '请输入账户名', trigger: 'blur' },
}

const menuOptions = ref<any[]>([])

const roleOptions = ref<BasicOption[]>([])

function setLeaf(data) {
  if (data.length > 0) {
    for (const item of data) {
      if (item.children.length > 0) {
        setLeaf(item.children)
      } else {
        item['isLeaf'] = true
      }
    }
  }
}

function initOptions() {
  getTree().then(res => {
    menuOptions.value = deepCopy(res.data[0].children)
    setLeaf(menuOptions.value)
  })
  getOptions().then(res => {
    roleOptions.value = res.data
  })
}

initOptions()

const emailOptions = computed(() => {
  return emailSuffix.map((suffix) => {
    if (currentFormData.value.email) {
      const prefix = currentFormData.value.email.split('@')[0]
      return {
        label: prefix + suffix,
        value: prefix + suffix
      }
    }
  })
})

const checkedAll = ref<boolean>(false)

const expandedKeys = ref<string[]>([])

function reset() {
  checkedAll.value = false
  expandedKeys.value = []
}

function childIds(data: any[], keys: Ref<string[]>) {
  for (let item of data) {
    keys.value.push(item.id)
    if (item.children.length) {
      childIds(item.children, keys)
    }
  }
}

function packHandle() {
  if (expandedKeys.value.length) {
    expandedKeys.value = []
  } else {
    childIds(menuOptions.value, expandedKeys)
  }
}

function checkedAllHandle() {
  if (!checkedAll.value) {
    currentFormData.value.attachedPermission = getTreeAll(menuOptions.value, 'id')
    checkedAll.value = true
  } else {
    currentFormData.value.attachedPermission = []
    checkedAll.value = false
  }
}

const formRef = ref()

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
        <n-form-item label="账户名" path="account">
          <n-input v-model:value="currentFormData.account" placeholder="请输入账户名" />
        </n-form-item>
        <n-form-item label="用户名" path="username">
          <n-input v-model:value="currentFormData.username" placeholder="请输入用户名" />
        </n-form-item>
        <n-form-item label="账户名" path="sex">
          <n-select
            :options="SexOptions"
            v-model:value="currentFormData.sex"
          />
        </n-form-item>
        <n-form-item label="手机号" path="phone">
          <n-input-number class="w-full" :show-button="false" v-model:value="currentFormData.phone" placeholder="请输入手机号" />
        </n-form-item>
        <n-form-item label="邮箱" path="email">
          <n-auto-complete
            clearable
            :options="emailOptions"
            placeholder="请输入邮箱"
            v-model:value="currentFormData.email"
            :input-props="{autocomplete: 'disabled'}"
          />
        </n-form-item>
        <n-form-item label="角色" path="roleId">
          <n-select
            :options="roleOptions"
            placeholder="请输入选择角色"
            v-model:value="currentFormData.roleId"
          />
        </n-form-item>
        <n-form-item label="附加权限" path="attachedPermission">
          <div class="max-h-400px overflow-y-scroll w-full">
            <n-tree
              cascade
              checkable
              show-line
              block-line
              key-field="id"
              check-strategy="child"
              :selectable="false"
              :data="menuOptions"
              v-model:expanded-keys="expandedKeys"
              :render-label="({option}) => option.meta.title"
              v-model:checked-keys="currentFormData.attachedPermission"
            />
          </div>
        </n-form-item>
      </n-form>
      <template #footer>
        <n-flex justify="end">
          <n-button type="info" ghost icon-placement="left" @click="packHandle">
            全部{{ expandedKeys.length ? '收起' : '展开' }}
          </n-button>

          <n-button type="info" ghost icon-placement="left" @click="checkedAllHandle">
            全部{{ checkedAll ? '取消' : '选择' }}
          </n-button>
          <n-button type="primary" @click="handleSubmit" :loading="loading">提交</n-button>
          <n-button @click="cancel">取消</n-button>
        </n-flex>
      </template>
    </n-drawer-content>
  </n-drawer>
</template>
