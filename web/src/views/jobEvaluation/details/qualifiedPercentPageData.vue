<script setup lang="ts">
import {NFlex} from "naive-ui";
import {
  JobQualifiedPercentPageFo,
  JobQualifiedPercentPageVo,
  JobType
} from "@/store/api/job/evaluation";
import CaretGroup from "@/components/icons/CaretGroup.vue";
import {getPage} from "@/api/job/qulifiedPercent";

const props = defineProps<{type: JobType}>()

const columns = ref([
  {
    title: '序号',
    key: 'index',
    justify: 'center',
    align: 'center',
    render: (_, index) => {
      return index + 1
    }
  },
  {
    title: () => h(NFlex, {
      justify: 'center',
      align: 'center',
      size: 0
    }, {
      default: () => [
        h('span', {class: 'whitespace-nowrap'}, '作业面积'),
        h('span', '(亩)')
      ]
    }),
    key: 'area',
    sorter: (row1, row2) => row1.area - row2.area,
    renderSorterIcon: (options: { order }) => {
      return h(CaretGroup, {order: options.order})
    }
  },
  {
    title: () => h(NFlex, {
      justify: 'center',
      align: 'center',
      size: 0
    }, {
      default: () => [
        h('span', {class: 'whitespace-nowrap'}, '作业时间'),
        h('span', '(小时)')
      ]
    }),
    key: 'cumulativeDuration',
    sorter: (row1, row2) => row1.cumulativeDuration - row2.cumulativeDuration,
    renderSorterIcon: (options: { order }) => {
      return h(CaretGroup, {order: options.order})
    }
  },
  {
    title: () => h(NFlex, {
      justify: 'center',
      align: 'center',
      size: 0
    }, {
      default: () => [
        h('span', {class: 'whitespace-nowrap'}, '合格率'),
        h('span', '(%)')
      ]
    }),
    key: 'percentPass',
    sorter: (row1, row2) => row1.percentPass - row2.percentPass,
    renderSorterIcon: (options: { order }) => {
      return h(CaretGroup, {order: options.order})
    }
  }
])

onMounted(() => {
  const item = ref()
  if (props.type === JobType.Uav) {
    item.value = {
      title: "飞手姓名",
      key: "flyerName",
      align: 'center',
      titleAlign: 'center',
      render: (row) => {
        return h('span', row.info?.username || '无')
      }
    }
  } else {
    item.value = {
      title: "采棉机编号",
      key: "cottonPickerId",
      align: 'center',
      titleAlign: 'center',
      render: (row) => {
        return h('span', row.info)
      }
    }
  }
  columns.value.splice(1, 0 , item.value)
})

const loading = ref<boolean>(false)

const pages = ref<number>(0)

const data = ref<JobQualifiedPercentPageVo[]>([])

const formData = ref<JobQualifiedPercentPageFo>({
  page:1,
  size: 10,
  jobType: props.type
})

function getData() {
  loading.value = true
  getPage(formData.value).then(res => {
    data.value = res.data.records
    pages.value = res.data.pages
  }).finally(() => loading.value = false)
}

getData()

</script>

<template>
  <n-card :bordered="false">
    <template #header>
      <slot name="header" />
    </template>
    <n-data-table
      :loading="loading"
      :columns="columns"
      :data="data"
      :bordered="false"
      titleAlign="center"
      :max-height="200"
    />
    <template #footer>
      <n-flex justify="end">
        <n-pagination
          show-quick-jumper
          :page-count="pages"
          size="small"
          :page-slot="5"
          v-model:page="formData.page"
        />
      </n-flex>
    </template>
  </n-card>
</template>

<style scoped>

::v-deep( .n-data-table .n-data-table-th) {
  font-size: 16px;
  font-weight: bold;
  text-align: center;
}

</style>
