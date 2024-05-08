<script setup lang="ts">
import {addMenuNode, delMenuNode, getTree, updateMenuNode} from "@/api/permission";
import {MenuOptions, PermissionTree, UpdatePermissionFo} from "@/store/api/permission";
import {NFlex, NPopconfirm, NTag} from "naive-ui";
import {getFieldValue, getLabel} from "@/utils/optionUtil";
import {LogicalEnum} from "@/enums/LogicalEnum";
import {deepCopy} from "@/utils/copyUtil";
import AddAndEditMenuDrawer from "@/views/settings/menu/details/AddAndEditMenuDrawer.vue";
import {constantRouterIcon} from "@/router/icons";

const tableData = ref<PermissionTree[]>([])

const loading = ref<boolean>(false)

const data = ref<PermissionTree[]>([])

function init() {
  loading.value = true
  getTree().then(res => {
    tableData.value = res.data[0].children
    data.value = deepCopy(res.data)
  }).finally(() => loading.value = false)
}

init()

const showUpdateDrawer = ref<boolean>(false)
const showAddDrawer = ref<boolean>(false)

const formData = ref<UpdatePermissionFo>({
  id: null,
  parentId: null,
  name: null,
  type: null,
  path: null,
  component: null,
  meta: {
    title: null
  }
})

const columns = ref([
  {
    title: '菜单标题',
    key: 'title',
    width: 250,
    render: (row) => {
      return h('span', row.meta.title)
    }
  },
  {
    title: '菜单图标',
    key: 'icon',
    width: 100,
    align: 'center',
    render: (row) => {
      if (row.meta.icon) {
        return h(constantRouterIcon[row.meta.icon]?.icon)
      }
    }
  },
  {
    title: '菜单名称',
    key: 'name',
    width: 200
  },
  {
    title: '请求地址',
    key: 'path',
    width: 200
  },
  {
    title: '组件',
    key: 'component',
    width: 250
  },
  {
    title: '类型',
    key: 'type',
    width: 100,
    render: (row) => {
      return h(NTag, {
        type: getFieldValue(MenuOptions, 'value', row.type, 'type')
      }, {
        default: () => getLabel(MenuOptions, row.type)
      })
    }
  },
  {
    title: '可用',
    key: 'disabled',
    width: 100,
    render: (row) => {
      return h(NTag, {
        type: LogicalEnum.YES === row.meta.disabled ? 'error' : 'success'
      }, {
        default: () => LogicalEnum.YES === row.meta.disabled ? '否' : '是'
      })
    }
  },
  {
    title: '权限标识',
    key: 'permissions',
    width: 200,
    render: (row) => {
      if (row.meta.permissions && row.meta.permissions.length > 0) {
        return h(NFlex, {}, {
          default: () => row.meta.permissions.map(item => h(NTag, {type: 'default'}, {default: () => item}))
        })
      }
    }
  },
  {
    title: '排序',
    key: 'sort',
    width: 100,
    render: (row) => {
      return h('span', row.meta.sort)
    }
  },
  {
    fixed: 'right',
    title: '操作',
    titleAlign: 'center',
    key: 'actions',
    width: 200,
    render: (row) => {
      return h(NFlex, {}, {
        default: () => [
          h(NButton, {
            size: 'small',
            type: 'info',
            onClick: () => {
              formData.value = deepCopy(row, {children: false})
              showUpdateDrawer.value = true
            }
          }, {
            default: () => '修改'
          }),
          h(NButton, {
            size: 'small',
            type: 'success',
            onClick: () => {
              formData.value = {
                id: null,
                parentId: row.id,
                name: null,
                type: null,
                path: null,
                component: null,
                meta: {
                  title: null,
                  disabled: false
                }
              }
              showAddDrawer.value = true
            }
          }, {
            default: () => '新增'
          }),
          h(NPopconfirm, {
            onPositiveClick: () => {
              delMenuNode(row.id).then(() => {
                init()
              })
            }
          },{
            trigger: () => h(NButton, {
              size: 'small',
              type: 'error',
            }, {
              default: () => '删除'
            }),
            default: () => `您确定要删除【${row.meta.title}】菜单节点吗？`
          }),
        ]
      })
    }
  },
])

function menuUpdate(): Promise<void> {
  return new Promise<void>((resolve, reject) => {
    updateMenuNode(formData.value).then(() => {
      init()
      resolve()
    }).catch(() => reject())
  })
}

function menuAdd(): Promise<void> {
  return new Promise<void>((resolve, reject) => {
    addMenuNode(formData.value).then(() => {
      init()
      resolve()
    }).catch(() => reject())
  })
}

</script>

<template>
  <div>
    <n-card title="菜单管理">
      <n-data-table
        striped
        :scroll-x="1800"
        :loading="loading"
        :columns="columns"
        :data="tableData"
        :row-key="(row) => row.id"
      />
    </n-card>
    <add-and-edit-menu-drawer
      :data="data"
      :submit="menuUpdate"
      v-model:form-data="formData"
      v-model:show="showUpdateDrawer"
    />
    <add-and-edit-menu-drawer
      is-add
      :data="data"
      :submit="menuAdd"
      v-model:form-data="formData"
      v-model:show="showAddDrawer"
    />
  </div>
</template>
