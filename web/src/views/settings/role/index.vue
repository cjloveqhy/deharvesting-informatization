<script setup lang="ts">
import {addRole, delRole, getRolePage, updateRole} from "@/api/system/role";
import {AddRole, RolePage, RoleVo, UpdateRole} from "@/store/api/role/types";
import {LogicalEnum} from "@/enums/LogicalEnum";
import {getLabel} from "@/utils/optionUtil";
import {LogicalOptions} from "@/store/common";
import {getTree} from "@/api/permission";
import {deepCopy} from "@/utils/copyUtil";
import {getTreeAll} from '@/utils';
import {Ref} from "vue";
import {NButton, NPopconfirm, useMessage} from "naive-ui";

const columns = ref([
  {
    title: "序号",
    key: "index",
    align: 'center',
    titleAlign: 'center',
    render: (_, index) => {
      return index + 1
    }
  },
  {
    title: "角色名称",
    key: "name",
    align: 'center',
    titleAlign: 'center',
  },
  {
    title: "角色标识",
    key: "value",
    align: 'center',
    titleAlign: 'center',
    render: (row) => {
      return h(NTag, {
        type: 'warning'
      }, {
        default: () => row.value
      })
    }
  },
  {
    title: "是否默认角色",
    key: "status",
    align: 'center',
    titleAlign: 'center',
    render: (row) => {
      return h(NTag, {
        type: row.status === LogicalEnum.YES ? 'success' : 'error'
      }, {
        default: () => getLabel(LogicalOptions, row.status)
      })
    }
  },
  {
    title: "是否禁用",
    key: "disabled",
    align: 'center',
    titleAlign: 'center',
    render: (row) => {
      return h(NTag, {
        type: row.status === LogicalEnum.NO ? 'success' : 'error'
      }, {
        default: () => getLabel(LogicalOptions, row.status)
      })
    }
  },
  {
    title: "操作",
    width: 300,
    titleAlign: 'center',
    render (row) {
      return h(NFlex, {
        justify: 'center',
        onClick: () => {

        }
      }, {
        default: () => [
          h(
            NButton,
            {
              type: 'default',
              onClick: () => {
                showRoleModal.value = true
                roleFormData.value = row
              }
            },
            { default: () => '菜单权限' }
          ),
          h(
            NButton,
            {
              type: 'info',
              onClick: () => {
                submitType.value = 'edit'
                addOrEditRoleModal.value = true
                roleFormData.value = row
              }
            },
            { default: () => '编辑' }
          ),
          h(NPopconfirm, {
            class: 'w-300px',
            onPositiveClick: () => {
              delRole(row.id).then(res => {
                message.success(res.data)
                init()
              })
            }
          }, {
            default: () => `您确定要删除角色名称为【${row.name}】的角色记录吗？`,
            trigger: () => h(NButton,
              {
                type: 'error'
              },
              {default: () => '删除'}
            )
          })
        ]
      })
    }
  }
])

const showRoleModal = ref<boolean>(false)

const addOrEditRoleModal = ref<boolean>(false)

const loading = ref<boolean>(false)

const formData = ref<RolePage>({
  page: 1,
  size: 10,
  name: null,
  value: null,
  status: null,
  disabled: null
})

function resetFormData() {
  formData.value = {
    ...formData.value,
    name: null,
    value: null,
    status: null,
    disabled: null
  }
}

watch(
  () => [formData.value.page, formData.value.size],
  () => init()
)

const pages = ref<number>(0)

const data = ref<RoleVo[]>([])

const roleOptions = ref<any[]>([])

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

function init() {
  loading.value = true
  getRolePage(formData.value).then(res => {
    data.value = res.data.records
    pages.value = res.data.pages
  }).finally(() => loading.value = false)

}

init()

function initOptions() {
  getTree().then(res => {
    roleOptions.value = deepCopy(res.data[0].children)
    setLeaf(roleOptions.value)
  })
}

initOptions()

const roleFormData = ref<AddRole | UpdateRole>({
  id: null,
  name: null,
  level: 0,
  remark: null,
  value: null,
  status: LogicalEnum.NO,
  disabled: LogicalEnum.NO,
  permissions: []
})

function resetRoleFormData() {
  roleFormData.value = {
    id: null,
    name: null,
    level: 0,
    remark: null,
    value: null,
    status: LogicalEnum.NO,
    disabled: LogicalEnum.NO,
    permissions: []
  }
}

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
    childIds(roleOptions.value, expandedKeys)
  }
}

function checkedAllHandle() {
  if (!checkedAll.value) {
    roleFormData.value.permissions = getTreeAll(roleOptions.value, 'id')
    checkedAll.value = true
  } else {
    roleFormData.value.permissions = []
    checkedAll.value = false
  }
}

const submitLoading = ref<boolean>(false)

const message = useMessage()

const submitType = ref<string>('')

const formRef = ref()

function addData() {
  submitLoading.value = true
  addRole(roleFormData.value).then(res => {
    message.success(res.data)
    init()
    addOrEditRoleModal.value = false
  }).finally(() => submitLoading.value = false)
}

function submitData() {
  formRef.value.validate((errors) => {
    if (!errors) {
      if (submitType.value === 'add') {
        addData()
      } else {
        updateData()
      }
    }
  })
}

function updateData() {
  submitLoading.value = true
  updateRole(roleFormData.value as UpdateRole).then(res => {
    message.success(res.data)
    init()
    showRoleModal.value = false
    addOrEditRoleModal.value = false
  }).finally(() => submitLoading.value = false)
}

function add() {
  addOrEditRoleModal.value = true
  submitType.value = 'add'
  resetRoleFormData()
}

const rules = {
  name: { required: true, message: '请输入角色名称', trigger: 'blur' },
  value: { required: true, message: '请输入角色标识', trigger: 'blur' },
}

</script>

<template>
  <n-flex vertical>
    <n-card>
      <n-flex :wrap="false" justify="space-between">
        <n-flex :size="[20, 30]">
          <n-form-item label-placement="left" :show-feedback="false" label="角色名称:">
            <n-input clearable v-model:value="formData.name" placeholder="请输入角色名称" />
          </n-form-item>
          <n-form-item label-placement="left" :show-feedback="false" label="角色标识:">
            <n-input clearable v-model:value="formData.value" placeholder="请输入角色标识" />
          </n-form-item>
          <n-form-item label-placement="left" :show-feedback="false" label="是否默认角色:">
            <n-select
              clearable
              class="w-200px"
              placeholder="请选择"
              :options="LogicalOptions"
              v-model:value="formData.status"
            />
          </n-form-item>
          <n-form-item label-placement="left" :show-feedback="false" label="是否禁用:">
            <n-select
              clearable
              class="w-200px"
              placeholder="请选择"
              :options="LogicalOptions"
              v-model:value="formData.disabled"
            />
          </n-form-item>
        </n-flex>
        <n-flex :wrap="false" justify="end">
          <n-button type="default" @click="resetFormData">重置</n-button>
          <n-button type="info" @click="init">查询</n-button>
        </n-flex>
      </n-flex>
    </n-card>
    <n-card title="角色管理">
      <template #header-extra>
        <n-button type="success" @click="add">
          添加
        </n-button>
      </template>
      <n-data-table
        :loading="loading"
        :columns="columns"
        :data="data"
        :bordered="false"
        titleAlign="center"
      />
      <n-flex justify="end" class="pt-20px">
        <n-pagination
          show-size-picker
          show-quick-jumper
          :page-count="pages"
          v-model:page="formData.page"
          v-model:page-size="formData.size"
          :page-sizes="[10, 20, 30, 40]"
        />
      </n-flex>
      <n-modal
        preset="card"
        @after-leave="reset"
        v-model:show="showRoleModal"
        :title="`分配 ${roleFormData.name} 的菜单权限`"
        class="w-30% h-40% min-w-500px min-h-300px"
        content-class="max-h-500px overflow-y-scroll"
      >
        <n-tree
          cascade
          checkable
          show-line
          block-line
          key-field="id"
          check-strategy="child"
          :selectable="false"
          :data="roleOptions"
          v-model:expanded-keys="expandedKeys"
          v-model:checked-keys="roleFormData.permissions"
          :render-label="({option}) => option.meta.title"
        />
        <template #action>
          <n-flex justify="end">
            <n-button type="info" ghost icon-placement="left" @click="packHandle">
              全部{{ expandedKeys.length ? '收起' : '展开' }}
            </n-button>

            <n-button type="info" ghost icon-placement="left" @click="checkedAllHandle">
              全部{{ checkedAll ? '取消' : '选择' }}
            </n-button>
            <n-button type="primary" @click="updateData">提交</n-button>
          </n-flex>
        </template>
      </n-modal>
      <n-modal
        preset="card"
        @after-leave="reset"
        v-model:show="addOrEditRoleModal"
        class="w-30% h-40% min-w-500px min-h-300px"
        :title="`${submitType === 'add' ? '添加角色权限' : `修改 ${roleFormData.name} 的菜单权限`}`"
      >
        <n-form
          ref="formRef"
          :rules="rules"
          :model="roleFormData"
          label-placement="left"
          label-width="auto"
          label-align="right"
          require-mark-placement="left"
        >
          <n-form-item label="角色名称" path="name">
            <n-input clearable v-model:value="roleFormData.name" placeholder="请输入角色名称" />
          </n-form-item>
          <n-form-item label="角色标签" path="value">
            <n-input clearable v-model:value="roleFormData.value" placeholder="请输入角色标签" />
          </n-form-item>
          <n-form-item label="描述" path="remark">
            <n-input clearable v-model:value="roleFormData.remark" placeholder="请输入角色描述信息" />
          </n-form-item>
          <n-flex>
            <n-form-item label="是否禁用" path="disabled">
              <n-switch
                v-model:value="roleFormData.disabled"
                :checked-value="LogicalEnum.YES"
                :unchecked-value="LogicalEnum.NO"
              >
                <template #checked>
                  <span>禁用</span>
                </template>
                <template #unchecked>
                  <span>启用</span>
                </template>
              </n-switch>
            </n-form-item>
            <n-form-item label="是否默认角色" path="status">
              <n-switch
                v-model:value="roleFormData.status"
                :checked-value="LogicalEnum.YES"
                :unchecked-value="LogicalEnum.NO"
              >
                <template #checked>
                  <span>是</span>
                </template>
                <template #unchecked>
                  <span>否</span>
                </template>
              </n-switch>
            </n-form-item>
          </n-flex>
          <n-form-item label="菜单权限" path="permissions" :show-feedback="false">
            <div class="max-h-400px w-full overflow-y-scroll">
              <n-tree
                cascade
                checkable
                show-line
                block-line
                key-field="id"
                check-strategy="child"
                :selectable="false"
                :data="roleOptions"
                v-model:expanded-keys="expandedKeys"
                v-model:checked-keys="roleFormData.permissions"
                :render-label="({option}) => option.meta.title"
              />
            </div>
          </n-form-item>
        </n-form>
        <template #action>
          <n-flex justify="end">
            <n-button type="info" ghost icon-placement="left" @click="packHandle">
              全部{{ expandedKeys.length ? '收起' : '展开' }}
            </n-button>
            <n-button type="info" ghost icon-placement="left" @click="checkedAllHandle">
              全部{{ checkedAll ? '取消' : '选择' }}
            </n-button>
            <n-button type="primary" @click="submitData">提交</n-button>
          </n-flex>
        </template>
      </n-modal>
    </n-card>
  </n-flex>
</template>
