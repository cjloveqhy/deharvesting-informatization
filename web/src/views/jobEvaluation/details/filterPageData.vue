<script setup lang="ts">
import {
  EvaluationResultOptions,
  JobEvaluationPageFo,
  JobEvaluationPageVo,
  JobType,
} from "@/store/api/job/evaluation";
import {getLabel} from "@/utils/optionUtil";
import {filterPage, getSelfFilterPage} from "@/api/job/evaluation";
import {UserInfo} from "@/store/api/user";
import {getUserRoles} from "@/api/user_role";
import router from "@/router";

const props = defineProps<{ type: JobType, isCheck: boolean }>()

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
    title: "订单编号",
    key: "orderId",
    align: 'center',
    titleAlign: 'center',
  },
  {
    title: "地块名称",
    key: "plotName",
    align: 'center',
    titleAlign: 'center',
    render: (row) => {
      return h('span', row.cottonField.plotName)
    }
  },
  {
    title: "地块地址",
    key: "addr",
    align: 'center',
    titleAlign: 'center',
    render: (row) => {
      return h('span', row.cottonField.addr)
    }
  },
  {
    title: "客户名称",
    key: "username",
    align: 'center',
    titleAlign: 'center',
    render: (row) => {
      return h('span', row.cottonField.contacts.username)
    }
  },
  {
    title: "联系方式",
    key: "phone",
    align: 'center',
    titleAlign: 'center',
    render: (row) => {
      return h('span', row.cottonField.contacts.phone)
    }
  },
  {
    title: "作业时间",
    key: "workTime",
    align: 'center',
    titleAlign: 'center',
  },
  {
    title: "评价结果",
    key: "evaluationResult",
    align: 'center',
    titleAlign: 'center',
    render: (row) => {
      return h('span', getLabel(EvaluationResultOptions, row.evaluationResult))
    }
  },
  {
    title: "操作",
    render(row) {
      return h(
        NButton,
        {
          strong: true,
          tertiary: true,
          size: 'small',
          onClick: () => checkJobEvaluationDetail(row)
        },
        {default: () => '查看'}
      )
    }
  }
])

const pages = ref<number>(0)

const timeRange = ref<string[] | null>()

const formData = ref<JobEvaluationPageFo>({
  page: 1,
  size: 10,
  orderId: null,
  jobType: props.type,
  jobId: null,
  customerId: null,
  evaluationResult: '',
  startTime: null,
  endTime: null,
})

onMounted(() => {
  if (!props.isCheck) {
    updateDataTableTitle(props.type)
  }
})

const data = ref<JobEvaluationPageVo[]>([])
let userRole = ref<string>("")

function reset() {
  formData.value = {
    ...formData.value,
    orderId: null,
    jobType: props.type,
    jobId: null,
    customerId: null,
    evaluationResult: '',
    startTime: null,
    endTime: null,
  }
  timeRange.value = null
}

const loading = ref<boolean>(false)
const item = ref()

function updateDataTableTitle(dataTableTitleType) {
  if (dataTableTitleType === JobType.Uav) {
    item.value = {
      title: "飞手",
      key: "flyer",
      align: 'center',
      titleAlign: 'center',
      render: (row) => {
        return h('span', row.info.username)
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
  columns.value.splice(8, 0, item.value)
}

function getData() {
  loading.value = true
  if (timeRange.value && timeRange.value.length > 0) {
    formData.value.startTime = timeRange.value[0]
    formData.value.endTime = timeRange.value[1]
  } else {
    formData.value.startTime = null
    formData.value.endTime = null
  }
  let form
  if (formData.value.evaluationResult === '') {
    form = {...formData.value, evaluationResult: null}
  } else {
    form = {...formData.value}
  }
  if (!props.isCheck) {
    filterPage(form as JobEvaluationPageFo).then(res => {
      data.value = res.data.records
      pages.value = res.data.pages
    }).finally(() => loading.value = false)
  } else {
    getSelfFilterPage(form as JobEvaluationPageFo).then(res => {
      data.value = res.data.records
      pages.value = res.data.pages
    }).finally(() => loading.value = false)
    getUserRoles().then(res => {
      userRole.value = res[0]
      if (userRole.value == '飞手') {
        updateDataTableTitle(JobType.Uav)
      } else {
        updateDataTableTitle(JobType.Cotton_Picker)
      }
    }).finally(() => loading.value = false)
  }
}

// 查看评价详情
const checkJobEvaluationDetail = (row) => {
  router.push({
    name: 'jobEvaluationMap_details',
    params: {
      cottonFieldId: row.cottonField.id,
      belonger: row.info.id,
    }
  })
}

getData()

watch(
  () => [formData.value.page, formData.value.size],
  () => getData()
)

</script>

<template>
  <n-card>
    <n-flex :wrap="false" justify="space-between">
      <n-flex :wrap="false">
        <n-form-item label-placement="left" :show-feedback="false" label="订单编号:">
          <n-input
            clearable
            class="w-200px"
            placeholder="请输入订单编号"
            v-model:value="formData.orderId"
          />
        </n-form-item>
        <n-form-item label-placement="left" :show-feedback="false" label="客户名称:">
          <n-input
            clearable
            class="w-200px"
            placeholder="请选择客户名称"
            v-model:value="formData.customerId"
          />
        </n-form-item>
        <template v-if="props.type === JobType.Uav">
          <n-form-item label-placement="left" :show-feedback="false" label="飞手:">
            <n-input
              clearable
              class="w-200px"
              placeholder="请选择飞手"
              v-model:value="formData.jobId"
            />
          </n-form-item>
        </template>
        <template v-else>
          <n-form-item label-placement="left" :show-feedback="false" label="采棉机:">
            <n-input
              clearable
              class="w-200px"
              placeholder="请选择采棉机"
              v-model:value="formData.jobId"
            />
          </n-form-item>
        </template>
        <n-form-item label-placement="left" :show-feedback="false" label="评价结果:">
          <n-select
            class="w-200px"
            placeholder="请选择评价结果"
            :options="EvaluationResultOptions"
            v-model:value="formData.evaluationResult"
          />
        </n-form-item>
        <n-form-item label-placement="left" :show-feedback="false" label="作业时间:">
          <n-date-picker
            clearable
            type="daterange"
            class="w-300px"
            v-model:value="timeRange"
          />
        </n-form-item>
      </n-flex>
      <n-flex :wrap="false" justify="end">
        <n-button type="default" @click="reset">重置</n-button>
        <n-button type="info" @click="getData">查询</n-button>
      </n-flex>
    </n-flex>
  </n-card>
  <n-card>
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
  </n-card>
</template>

<style scoped>

::v-deep( .n-data-table .n-data-table-th) {
  font-size: 16px;
  font-weight: bold;
  text-align: center;
}

</style>
