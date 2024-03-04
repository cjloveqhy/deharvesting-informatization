<script setup lang="ts">
import {getTree} from "@/api/permission";
import {PermissionTree} from "@/store/api/permission/types";
import {MenuOptions} from "@/store/api/permission/options";
import {NTag, NFlex} from "naive-ui";
import {getLabel} from "@/utils/optionUtil";
import {LogicalEnum} from "@/enums/LogicalEnum";

const data = ref<PermissionTree[]>([])

const loading = ref<boolean>(true)

function init() {
  getTree().then(res => {
    data.value = res.data[0].children
    loading.value = false
  })
}

const columns = ref([
  {
    title: '菜单名称',
    key: 'title',
    render: (row) => {
      return h('span', row.meta.title)
    }
  },
  {
    title: '请求地址',
    key: 'path',
  },
  {
    title: '组件',
    key: 'component',
  },
  {
    title: '类型',
    key: 'type',
    render: (row) => {
      return h(NTag, {
        type: 'info'
      }, {
        default: () => getLabel(MenuOptions, row.type)
      })
    }
  },
  {
    title: '可用',
    key: 'status',
    render: (row) => {
      return h(NTag, {
        type: LogicalEnum.YES === row.status ? 'success' : 'error'
      }, {
        default: () => LogicalEnum.YES === row.status ? '是' : '否'
      })
    }
  },
  {
    title: '权限标识',
    key: 'permissions',
    render: (row) => {
      if (row.permissions && row.permissions.length > 0) {
        return h(NFlex, {}, {
          default: () => row.permissions.map(item => h(NTag, {type: 'default'}, {default: () => item}))
        })
      }
    }
  },
  {
    title: '操作',
    key: 'actions',
  },
])

init()

</script>

<template>
  <n-card title="菜单管理">
    <n-data-table
      :data="data"
      :loading="loading"
      :columns="columns"
      :row-key="(row) => row.id"
    />
  </n-card>
</template>
