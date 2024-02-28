<script setup lang="ts">
import { h, defineComponent } from 'vue'
import { NButton, useMessage, NFlex } from 'naive-ui'
import type { DataTableColumns } from 'naive-ui'
  let columns = ref([
    {
      title: "序号",
      key: 'index'
    },
    {
      title: "调度单编号",
      key: 'id'
    },
    {
      title: "轧花厂",
      key: 'factoryName'
    },
    {
      title: "地址",
      key: 'address'
    },
    {
      title: '联系人',
      key: 'contacts'
    },
    {
      title: '联系人',
      key: 'phone'
    },
    {
      title: "调度面积(亩)",
      key: "dispatchArea"
    },
    {
      title: "主要品种",
      key: "mainVarieties"
    },
    {
      title: "调度单时间",
      key: "dispatchOrder"
    },
    {
      title: '操作',
      key: 'actions',
      render (row) {
        return h(NFlex, [
          h(NButton,
            {
              strong: true,
              tertiary: true,
              size: 'small',
              onClick: () => checkDispatchDetail(row)
            },
            { default: () => '查看' }),
          h(NButton,
            {
              strong: true,
              tertiary: true,
              size: 'small',
              onClick: () => exportPDF(row)
            },
            { default: () => '导出PDF' }),
        ])
      }
    }
  ])
  let data = ref([
    {
      index: 1,
      id: "DD20230811001",
      factoryName: "xxx轧花厂",
      address: "xx农场",
      contacts: "张三",
      phone: '13757886913',
      dispatchArea: "23140",
      mainVarieties: "2023-09-01",
      dispatchOrder: "2023-09-30"
    },
    {
      index: 2,
      id: "DD20230811002",
      factoryName: "xxx轧花厂",
      address: "xx农场",
      contacts: "李四",
      phone: '13757886913',
      dispatchArea: "23140",
      mainVarieties: "2023-09-01",
      dispatchOrder: "2023-09-30"
    },
  ])
const message = useMessage()
const  exportPDF = (row) => {
  console.log(row)
  message.info(`导出PDF`)
}
const router = useRouter()
const checkDispatchDetail = (row) => {
  router.push({
    path: "/recoveryDispatch/record/details"
  })
}
</script>

<template>
  <n-card>
    <div style="display: flex;justify-content: space-between">
      <div style="display: flex;align-items: center">
        <span>轧花厂：</span>
        <n-input style="width: 15%"></n-input>
        <span style="margin-left: 30px">联系人：</span>
        <n-input style="width: 15%"></n-input>
        <span style="margin-left: 30px">调度单时间：</span>
        <n-date-picker v-model:value="range" type="daterange" clearable style="width: 15%" />
      </div>
      <n-button type="info">查询</n-button>
    </div>
  </n-card>
  <n-card>
    <n-data-table
      :columns="columns"
      :data="data"
      :bordered="false"
    />
  </n-card>
</template>
