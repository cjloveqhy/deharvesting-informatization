<script setup lang="ts">
import {add, del, filterPage, update} from "@/api/user_role";
import {AddUserRoleFo, UpdateUserRoleFo, UserRolePage} from "@/store/api/userRole";
import {NButton, NDropdown, NImage, NPopconfirm, NTag, useDialog, useMessage} from "naive-ui";
import {getLabel} from "@/utils/optionUtil";
import {SexEnum, SexOptions} from "@/store/api/user";
import {Ref} from "vue";
import {getTreeAll} from "@/utils";
import {getTree} from "@/api/permission";
import {deepCopy} from "@/utils/copyUtil";
import AddAndEditUserDrawer from "@/views/settings/user/details/AddAndEditUserDrawer.vue";
import {getOptions} from "@/api/system/role";
import {BasicOption, LogicalOptions} from "@/store/common";
import {LogicalEnum} from "@/enums/LogicalEnum";
import {forbiddenOrLiftBanAccount} from "@/api/system/user";

const dialog = useDialog()

const loading = ref<boolean>(false)

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
    title: "账户名",
    key: "userInfo.account",
    align: 'center',
    titleAlign: 'center',
  },
  {
    title: "用户名",
    key: "userInfo.username",
    align: 'center',
    titleAlign: 'center',
  },
  {
    title: "头像",
    key: "userInfo.photo",
    align: 'center',
    titleAlign: 'center',
    render: (row) => {
      return h(NImage, {
        width: 30,
        height: 30,
        src: 'https://07akioni.oss-cn-beijing.aliyuncs.com/07akioni.jpeg'
      })
    }
  },
  {
    title: "性别",
    key: "userInfo.sex",
    align: 'center',
    titleAlign: 'center',
    render: (row) => {
      return h('span', getLabel(SexOptions, row.userInfo.sex))
    }
  },
  {
    title: "手机号",
    key: "userInfo.phone",
    align: 'center',
    titleAlign: 'center',
    render: (row) => {
      return row.userInfo.phone || '无'
    }
  },
  {
    title: "邮箱",
    key: "userInfo.email",
    align: 'center',
    titleAlign: 'center',
    render: (row) => {
      return row.userInfo.email || '无'
    }
  },
  {
    title: "角色",
    key: "role.name",
    align: 'center',
    titleAlign: 'center',
    render: (row) => {
      return row.role ? h(NTag, {
        type: 'info'
      }, {
        default: () => row.role?.name
      }) : '暂无分配'
    }
  },
  {
    title: "是否禁用",
    key: "userInfo.status",
    align: 'center',
    titleAlign: 'center',
    render: (row) => {
      return h(NTag, {
        type: row.userInfo.status === LogicalEnum.YES ? 'error' : 'success'
      }, {
        default: () => getLabel(LogicalOptions, row.userInfo.status)
      })
    }
  },
  {
    title: "操作",
    key: "actions",
    align: 'center',
    width: 250,
    titleAlign: 'center',
    render: (row) => {
      return h(NFlex, {
        justify: 'center',
      }, {
        default: () => [
          h(NButton, {
            type: 'info',
            onClick: () => {
              showUpdateDrawer.value = true
              userFormData.value = {
                ...deepCopy(row.userInfo, {createTime: false}),
                roleId: row.role?.id,
                attachedPermission: row.attachedPermission
              }
            }
          }, {
            default: () => '修改'
          }),
          h(NPopconfirm, {
            class: 'w-300px',
            onPositiveClick: () => {
              del(row.id).then(res => {
                message.success(res.data)
                init()
              })
            }
          }, {
            default: () => `您确定要删除账户名为【${row.userInfo.account}】的用户记录吗？`,
            trigger: () => h(NButton,
              {
                type: 'error'
              },
              {default: () => '删除'}
            )
          }),
          h(NDropdown, {
            trigger: 'click',
            options: [
              {
                label: '附加权限',
                key: 'role',
                props: {
                  onClick: () => {
                    showRoleModal.value = true
                    userFormData.value = {
                      ...deepCopy(row.userInfo, {createTime: false}),
                      roleId: row.role?.id,
                      attachedPermission: row.attachedPermission
                    }
                  }
                }
              },
              {
                label: row.userInfo.status === LogicalEnum.YES ? '解禁' : '禁用',
                key: 'forbiddenOrLiftBan',
                props: {
                  onClick: () => {
                    const forbiddenOrLiftBanDialog = dialog.warning({
                      title: '警告',
                      content: `你确定要将账号【${row.userInfo.account}】${row.userInfo.status === LogicalEnum.YES ? '解禁' : '禁用'}吗？`,
                      positiveText: '确定',
                      negativeText: '取消',
                      onPositiveClick: () => {
                        forbiddenOrLiftBanAccount(row.userInfo.id).then(res => {
                          message.success(res.data)
                          forbiddenOrLiftBanDialog.destroy()
                          init()
                        })
                      },
                    })
                  }
                }
              },
              /*{
                label: '封禁',
                key: 'banned',
                disabled: false,
                props: {
                  onClick: () => {

                  }
                }
              },*/
            ],
          }, {
            default: () => h(NButton, {
              type: 'info',
              ghost: true
            }, {
              default: () => '更多'
            })
          }),
        ]
      })
    }
  },
])

const data = ref<[]>([])

const pages = ref<number>(0)

const formData = ref<UserRolePage>({
  page: 1,
  size: 10,
  account: null,
  username: null,
  sex: null,
  phone: null,
  email: null,
  roleId: null,
  status: null,
})

function resetFormData() {
  formData.value = {
    ...formData.value,
    account: null,
    username: null,
    sex: null,
    phone: null,
    email: null,
    roleId: null,
    status: null,
  }
}

function init() {
  loading.value = true
  filterPage(formData.value).then(res => {
    data.value = res.data.records
    pages.value = res.data.pages
  }).finally(() => loading.value = false)
}

init()

const roleOptions = ref<BasicOption[]>([])

getOptions().then(res => {
  roleOptions.value = res.data
})

watch(
  () => [formData.value.page, formData.value.size],
  () => init()
)

const showRoleModal = ref<boolean>(false)

const menuOptions = ref<any[]>([])

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
}

initOptions()

const userFormData = ref<AddUserRoleFo | UpdateUserRoleFo>({
  id: null,
  username: null,
  photo: null,
  sex: SexEnum.Unknown,
  account: null,
  phone: null,
  email: null,
  roleId: null,
  status: LogicalEnum.NO,
  attachedPermission: []
})

function resetUserFormData() {
  userFormData.value = {
    id: null,
    username: null,
    photo: null,
    sex: SexEnum.Unknown,
    account: null,
    phone: null,
    email: null,
    roleId: null,
    status: LogicalEnum.NO,
    attachedPermission: []
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
    childIds(menuOptions.value, expandedKeys)
  }
}

function checkedAllHandle() {
  if (!checkedAll.value) {
    userFormData.value.attachedPermission = getTreeAll(menuOptions.value, 'id')
    checkedAll.value = true
  } else {
    userFormData.value.attachedPermission = []
    checkedAll.value = false
  }
}

const message = useMessage()

const showUpdateDrawer = ref<boolean>(false)

const showAddDrawer = ref<boolean>(false)

function updateData(): Promise<void> {
  return new Promise<void>((resolve, reject) => {
    update(userFormData.value as UpdateUserRoleFo).then(res => {
      message.success(res.data)
      init()
      resolve()
      showRoleModal.value = false
    }).catch(() => reject())
  })
}

function addData(): Promise<void> {
  return new Promise<void>((resolve, reject) => {
    add(userFormData.value).then(res => {
      message.success(res.data)
      init()
      resolve()
    }).catch(() => reject())
  })
}

function addDrawerInit() {
  resetUserFormData()
  showAddDrawer.value = true
}

</script>

<template>
  <n-flex vertical>
    <n-card>
      <n-flex :wrap="false" justify="space-between">
        <n-flex :size="[20, 30]">
          <n-form-item label-placement="left" :show-feedback="false" label="账户名:">
            <n-input clearable v-model:value="formData.account" placeholder="请输入账户名" />
          </n-form-item>
          <n-form-item label-placement="left" :show-feedback="false" label="用户名:">
            <n-input clearable v-model:value="formData.username" placeholder="请输入用户名" />
          </n-form-item>
          <n-form-item label-placement="left" :show-feedback="false" label="角色:">
            <n-select
              clearable
              class="w-200px"
              :options="roleOptions"
              placeholder="请选择角色"
              v-model:value="formData.roleId"
            />
          </n-form-item>
          <n-form-item label-placement="left" :show-feedback="false" label="手机号:">
            <n-input clearable v-model:value="formData.phone" placeholder="请输入手机号" />
          </n-form-item>
          <n-form-item label-placement="left" :show-feedback="false" label="邮箱:">
            <n-input clearable v-model:value="formData.email" placeholder="请输入邮箱" />
          </n-form-item>
          <n-form-item label-placement="left" :show-feedback="false" label="性别:">
            <n-select
              clearable
              class="w-200px"
              :options="SexOptions"
              placeholder="请选择性别"
              v-model:value="formData.sex"
            />
          </n-form-item>
          <n-form-item label-placement="left" :show-feedback="false" label="是否禁用:">
            <n-select
              clearable
              class="w-200px"
              placeholder="请选择"
              :options="LogicalOptions"
              v-model:value="formData.status"
            />
          </n-form-item>
        </n-flex>
        <n-flex :wrap="false" justify="end">
          <n-button type="default" @click="resetFormData">重置</n-button>
          <n-button type="info" @click="init">查询</n-button>
        </n-flex>
      </n-flex>
    </n-card>
    <n-card title="用户管理">
      <template #header-extra>
        <n-button type="success" @click="addDrawerInit">
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
        :title="`分配 ${userFormData.account} 的菜单权限`"
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
          :data="menuOptions"
          v-model:expanded-keys="expandedKeys"
          :render-label="({option}) => option.meta.title"
          v-model:checked-keys="userFormData.attachedPermission"
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
      <add-and-edit-user-drawer
        :submit="updateData"
        :form-data="userFormData"
        v-model:show="showUpdateDrawer"
      />
      <add-and-edit-user-drawer
        is-add
        :submit="addData"
        :form-data="userFormData"
        v-model:show="showAddDrawer"
      />
    </n-card>
  </n-flex>
</template>
