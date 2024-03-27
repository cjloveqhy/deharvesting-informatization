<script setup lang="ts">
import { NButton, useMessage, NFlex } from 'naive-ui'
import {FilterPageFo, HarvestScheduleVo} from "@/store/api/harvestSchedule";
import {checkOrderInfo, filterPage} from "@/api/harvestSchedule";

const columns = ref([
  {
    title: "序号",
    key: 'index',
    render: (_, index) => {
      return index + 1
    }
  },
  {
    title: "调度单编号",
    key: 'dispatchId'
  },
  {
    title: "轧花厂",
    key: 'factoryName'
  },
  {
    title: "地址",
    key: 'addr',
    render: (row) => {
      return h('span', row.addr ?? '暂无地址')
    }
  },
  {
    title: '联系人',
    key: 'username',
    render: (row) => {
      return h('span', row.username ?? '暂无联系人')
    }
  },
  {
    title: '联系方式',
    key: 'phone',
    render: (row) => {
      return h('span', row.phone ?? '暂无联系方式')
    }
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
    key: "createTime"
  },
  {
    title: '操作',
    key: 'actions',
    render (row) {
      return h(NFlex, {}, {
        default: () => [
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
        ]
      })
    }
  }
])

const message = useMessage()

const  exportPDF = (row) => {
  console.log(row)
  message.info(`导出PDF`)
}

const router = useRouter()

const checkDispatchDetail = (row) => {
  router.push({
    name: 'recoveryDispatchRecord_details',
    params: {dispatchId: row.dispatchId}
  })
}

const formData = ref<FilterPageFo>({
  ginneryName: null,
  contacts: null,
  startTime: null,
  endTime: null,
  page: 1,
  size: 10
})

const timeRange = ref<string[] | null>()

const data = ref<HarvestScheduleVo[]>([])

const pages = ref<number>(0)

const loading = ref<boolean>(false)

function getData() {
  loading.value = true
  if (timeRange.value && timeRange.value.length > 0) {
    formData.value.startTime = timeRange.value[0]
    formData.value.endTime = timeRange.value[1]
  } else {
    formData.value.startTime = null
    formData.value.endTime = null
  }
  checkOrderInfo(formData.value).then(res => {
    data.value = res.data.records
    pages.value = res.data.pages
  }).finally(() => loading.value = false)
}

getData()

watch(
  () => [formData.value.page, formData.value.size],
  () => getData()
)

</script>
<template>
  <n-flex vertical>
    <n-card>
      <n-flex :wrap="false" justify="space-between">
        <n-flex :wrap="false">
          <n-form-item label-placement="left" :show-feedback="false" label="轧花厂：">
            <n-input
              clearable
              placeholder="请输入轧花厂/调度单号"
              v-model:value="formData.ginneryName"
            />
          </n-form-item>
          <n-form-item label-placement="left" :show-feedback="false" label="联系人：">
            <n-input
              clearable
              placeholder="请输入联系人"
              v-model:value="formData.contacts"
            />
          </n-form-item>
          <n-form-item label-placement="left" :show-feedback="false" label="调度单时间：">
            <n-date-picker
              clearable
              type="daterange"
              value-format="yyyy-MM-dd HH:mm:ss"
              v-model:formatted-value="timeRange"
            />
          </n-form-item>
        </n-flex>
        <n-button type="info" @click="getData()">查询</n-button>
      </n-flex>
    </n-card>
    <n-card>
      <n-data-table
        :columns="columns"
        :data="data"
        :bordered="false"
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
    </n-card>
  </n-flex>
</template>


<style scoped>

</style>
