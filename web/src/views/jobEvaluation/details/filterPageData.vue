<script setup lang="ts">
import {
  AddJobEvaluationFo,
  AddOptionJobEvaluationVo,
  EvaluationResult,
  EvaluationResultOptions,
  JobEvaluationPageFo,
  JobEvaluationPageVo,
  JobType,
} from "@/store/api/job/evaluation";
import {getLabel} from "@/utils/optionUtil";
import {
  addJobEvaluationInfo,
  filterPage,
  getAddOptions,
  getSelfFilterPage
} from "@/api/job/evaluation";
import {getUserRoles} from "@/api/user_role";
import router from "@/router";
import {useMessage} from 'naive-ui'

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
let addEvaluationShow = ref<boolean>(false);
let addEvaluationFormData = ref<AddJobEvaluationFo>({
  orderId: "",
  cottonFieldId: "",
  workTime: "",
  evaluationResult: EvaluationResult.Pass_Qualified,
  jobType: props.type,
  jobId: "",
})

let submitAddEvaluationLoading = ref<boolean>(false)
let addEvaluationOptions = ref<AddOptionJobEvaluationVo>({
  plotNameOption:[],
  userNameOption: [],
  uavNameOption: [],
  cottonFieldNameOption: []
})

function getAddEvaluationOptions(){
  getAddOptions().then(res => {
    addEvaluationOptions.value = res.data
  })
}

// 添加评价
const addEvaluation = ()=> {
  addEvaluationShow.value = true
  resetAddEvaluationFormData()
}

const resetAddEvaluationFormData = ()=> {
  addEvaluationFormData = ref<AddJobEvaluationFo>({
    orderId: "",
    cottonFieldId: "",
    workTime: null,
    evaluationResult: EvaluationResult.Pass_Qualified,
    jobType: props.type,
    jobId: "",
  })
}
const message = useMessage()
const submitAddEvaluation = ()=> {
  addJobEvaluationInfo(addEvaluationFormData.value).then(res => {
    if (res.data == "添加成功"){
      message.info(
        "添加成功",
        {
          keepAliveOnHover: true
        }
      )
    }
    getData()
  }).finally(() => {
    addEvaluationShow.value = false
  })
}

const cancelAddEvaluation = ()=>{
  resetAddEvaluationFormData()
  addEvaluationShow.value = false
}

getData()
getAddEvaluationOptions()
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
        <n-button type="info" v-if="!props.isCheck" @click="addEvaluation">添加</n-button>
        <n-modal
          preset="card"
          class="w-800px"
          title="添加评价"
          v-model:show="addEvaluationShow"
          @after-leave="resetAddEvaluationFormData"
          content-class="flex justify-center"
        >
          <n-form
            ref="formRef"
            class="w-400px"
            label-width="auto"
            label-align="right"
            label-placement="left"
            :model="addEvaluationFormData"
          >
            <n-form-item label="订单编号" path="orderId">
              <n-input
                clearable
                placeholder="请输入订单编号"
                v-model:value="addEvaluationFormData.orderId"
              />
            </n-form-item>
            <n-form-item label="地块地址" path="plotName">
              <n-select
                tag
                clearable
                filterable
                v-model:value="addEvaluationFormData.cottonFieldId"
                :options="addEvaluationOptions.plotNameOption" />
            </n-form-item>
            <n-form-item label="作业时间" path="workTime">
              <n-date-picker
                clearable
                type="datetime"
                class="w-300px"
                value-format="yyyy-MM-dd HH:mm:ss"
                v-model:formatted-value="addEvaluationFormData.workTime"
              />
            </n-form-item>
            <n-form-item label="评价结果" path="evaluationResult">
              <n-select
                class="w-200px"
                placeholder="请选择评价结果"
                :options="EvaluationResultOptions"
                v-model:value="addEvaluationFormData.evaluationResult"
              />
            </n-form-item>
            <n-form-item label="飞手" path="evaluationResult" v-if="props.type == JobType.Uav">
              <n-select
                class="w-200px"
                placeholder="请选择飞手"
                :options="addEvaluationOptions.uavNameOption"
                v-model:value="addEvaluationFormData.jobId"
              />
            </n-form-item>
            <n-form-item label="采棉机" path="evaluationResult" v-if="props.type == JobType.Cotton_Picker">
              <n-select
                class="w-200px"
                placeholder="请选择采棉机"
                :options="addEvaluationOptions.cottonFieldNameOption"
                v-model:value="addEvaluationFormData.jobId"
              />
            </n-form-item>
          </n-form>
          <template #action>
            <n-flex justify="end">
              <n-button type="success" @click="submitAddEvaluation" :loading="submitAddEvaluationLoading">提交</n-button>
              <n-button @click="cancelAddEvaluation">取消</n-button>
            </n-flex>
          </template>
        </n-modal>
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
