<script setup lang="ts">
import LineType from "@/components/LineType/LineType.vue";
import {NButton, NFlex, NPopconfirm, useMessage} from "naive-ui";
import {BasicOption, LogicalOptions} from '@/store/common'
import {DataTerminalFo, DataTerminalPageFo, DataTerminalVo} from "@/store/api/farmDataTerminal";
import {
  addFarmDataTerminal,
  delFarmDataTerminal,
  getBrandOptions,
  getFilterPage,
  updateFarmDataTerminal
} from "@/api/farmDataTerminal";
import {LogicalEnum} from "@/enums/LogicalEnum";
import {getLabel} from "@/utils/optionUtil";

// 表格的表头信息
const farmUavColumns = ref([
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
    title: "终端编号",
    key: "terminalId",
    align: 'center',
    titleAlign: 'center',
  },
  {
    title: "品牌",
    key: "brand",
    align: 'center',
    titleAlign: 'center',
  },
  {
    title: "型号",
    key: "model",
    align: 'center',
    titleAlign: 'center',
  },
  {
    title: "SIM卡到期时间",
    key: "simExpirationTime",
    align: 'center',
    titleAlign: 'center',
  },
  {
    title: "是否关联农机",
    key: "status",
    align: 'center',
    titleAlign: 'center',
    render: (row) => {
      return h('span', getLabel(LogicalOptions, row.status))
    }
  },
  {
    title: "农机编号",
    key: "cottonPickerRackId",
    align: 'center',
    titleAlign: 'center',
  },
  {
    title: "更新时间",
    key: "updateTime",
    align: 'center',
    titleAlign: 'center',
  },
  {
    title: '操作',
    key: 'actions',
    render(row) {
      return h(NFlex, {}, {default: () => [
          h(NButton,
            {
              strong: true,
              tertiary: true,
              size: 'small',
              color: "blue",
              onClick: () => editTerminal(row)
            },
            {default: () => '编辑'}),
          h(NPopconfirm, {
            class: 'w-300px',
            onPositiveClick: () => deleteTerminal(row)
          }, {
            default: () => `您确定要删除终端编号为【${row.terminalId}】的记录，并取消农机编号为【${row.cottonPickerRackId}】的关联吗？`,
            trigger: () => h(NButton,
              {
                strong: true,
                tertiary: true,
                size: 'small',
                color: "red",
              },
              {default: () => '删除'}
            )
          })
        ]
      })
    }
  }
])

const data = ref<DataTerminalVo[]>([])

const timeRange = ref<string[] | null>()

const formData = ref<DataTerminalPageFo>({
  page: 1,
  size: 10,
  terminalId: null,
  brand: null,
  status: null,
  cottonPickerRackId: null,
  startTime: null,
  endTime: null,
})

const brandOptions = ref<BasicOption[]>([])

function initOptions() {
  getBrandOptions().then(res => {
    brandOptions.value = res.data
  })
}

initOptions()

const loading = ref<boolean>(false)

const pages = ref<number>(0)

function getData() {
  loading.value = true
  if (timeRange.value && timeRange.value.length > 0) {
    formData.value.startTime = timeRange.value[0]
    formData.value.endTime = timeRange.value[1]
  } else {
    formData.value.startTime = null
    formData.value.endTime = null
  }
  getFilterPage(formData.value).then(res => {
    data.value = res.data.records
    pages.value = res.data.pages
  }).finally(() => loading.value = false)
}

getData()

watch(
  () => [formData.value.page, formData.value.size],
  () => getData()
)

function reset() {
  formData.value = {
    ...formData.value,
    terminalId: null,
    brand: null,
    status: null,
    cottonPickerRackId: null,
    startTime: null,
    endTime: null,
  }
  timeRange.value = null
}

const show = ref<boolean>(false)

const addOrUpdateFormData = ref<DataTerminalFo>({
  id: null,
  terminalId: null,
  brand: null,
  model: null,
  simExpirationTime: null,
  status: LogicalEnum.NO,
  cottonPickerRackId: null
})

const rules = {
  terminalId: { required: true, message: '请输入终端编号', trigger: 'blur' },
  brand: { required: true, message: '请选择品牌', trigger: 'blur' },
  model: { required: true, message: '请输入型号', trigger: 'blur' },
  cottonPickerRackId: { required: true, message: '请输入农机编号', trigger: 'blur' },
}

function resetFormData() {
  addOrUpdateFormData.value = {
    id: null,
    terminalId: null,
    brand: null,
    model: null,
    simExpirationTime: null,
    status: LogicalEnum.NO,
    cottonPickerRackId: null
  }
}

function cancel() {
  resetFormData()
  show.value = false
}

const formRef = ref()

const submitLoading = ref<boolean>(false)

const message = useMessage()

function basicSubmit(request: () => Promise<any>) {
  formRef.value.validate((errors) => {
    if (!errors) {
      submitLoading.value = true
      request.call(null)
        .then(res => {
          message.success(res.data)
          getData()
          cancel()
          initOptions()
        })
        .finally(() => submitLoading.value = false)
    }
  })
}

const submitType = ref<string>()

function addShow() {
  show.value = true
  resetFormData()
  submitType.value = 'add'
}

function submit() {
  if (submitType.value === 'add') {
    basicSubmit(() => addFarmDataTerminal(addOrUpdateFormData.value))
  } else {
    basicSubmit(() => updateFarmDataTerminal(addOrUpdateFormData.value))
  }
}

// 修改数据终端
const editTerminal = (row)=> {
  show.value = true
  resetFormData()
  addOrUpdateFormData.value = row as DataTerminalFo
  submitType.value = 'edit'
}

// 删除数据终端
const deleteTerminal = (row)=> {
  delFarmDataTerminal(row.id).then(res => {
    message.success(res.data)
    getData()
    initOptions()
  })
}

</script>

<template>
  <n-flex :wrap="false" class="w-full h-87vh">
    <LineType mark="farm" type="farmDataTerminal" class="w-15% h-full"></LineType>
    <n-flex vertical class="w-85%">
      <n-card :bordered="false">
        <n-flex :wrap="false" justify="space-between">
          <n-flex :size="[20, 30]">
            <n-form-item label-placement="left" :show-feedback="false" label="终端编号:">
              <n-input
                clearable
                class="w-200px"
                placeholder="请输入终端编号"
                v-model:value="formData.terminalId"
              />
            </n-form-item>
            <n-form-item label-placement="left" :show-feedback="false" label="品牌:">
              <n-select
                remote
                clearable
                class="w-200px"
                placeholder="请选择品牌"
                :options="brandOptions"
                v-model:value="formData.brand"
              />
            </n-form-item>
            <n-form-item label-placement="left" :show-feedback="false" label="是否关联农机:">
              <n-select
                clearable
                class="w-200px"
                placeholder="请选择是否关联"
                v-model:value="formData.status"
                :options="LogicalOptions"
              />
            </n-form-item>
            <n-form-item label-placement="left" :show-feedback="false" label="关联农机编号:">
              <n-input
                clearable
                class="w-200px"
                placeholder="请输入关联农机编号"
                v-model:value="formData.cottonPickerRackId"
              />
            </n-form-item>
            <n-form-item label-placement="left" :show-feedback="false" label="更新时间:">
              <n-date-picker
                clearable
                type="daterange"
                value-format="yyyy-MM-dd HH:mm:ss"
                v-model:formatted-value="timeRange"
              />
            </n-form-item>
          </n-flex>
          <n-flex :wrap="false" justify="end">
            <n-button type="default" @click="reset">重置</n-button>
            <n-button type="primary" @click="getData">查询</n-button>
          </n-flex>
        </n-flex>
      </n-card>
      <n-flex>
        <n-button type="info" @click="addShow">新机入库</n-button>
        <n-modal
          preset="card"
          class="w-800px"
          v-model:show="show"
          @after-leave="resetFormData"
          content-class="flex justify-center"
          :title="submitType === 'add' ? '新机入库' : '修改'"
        >
          <n-form
            ref="formRef"
            :rules="rules"
            class="w-400px"
            label-width="120px"
            label-align="right"
            label-placement="left"
            :model="addOrUpdateFormData"
          >
            <n-form-item label="终端编号" path="terminalId">
              <n-input
                clearable
                placeholder="请输入终端编号"
                v-model:value="addOrUpdateFormData.terminalId"
              />
            </n-form-item>
            <n-form-item label="品牌" path="brand">
              <n-select
                tag
                clearable
                filterable
                :options="brandOptions"
                placeholder="请选择品牌"
                v-model:value="addOrUpdateFormData.brand"
              />
            </n-form-item>
            <n-form-item label="型号" path="model">
              <n-input
                clearable
                placeholder="请输入型号"
                v-model:value="addOrUpdateFormData.model"
              />
            </n-form-item>
            <n-form-item label="SIM卡到期时间" path="simExpirationTime">
              <n-date-picker
                clearable
                type="date"
                class="w-full"
                format="yyyy年MM月dd日"
                v-model:formatted-value="addOrUpdateFormData.simExpirationTime"
              />
            </n-form-item>
            <n-form-item label="是否关联农机" path="status">
              <n-switch
                checked-value="Y"
                unchecked-value="N"
                v-model:value="addOrUpdateFormData.status"
              />
            </n-form-item>
            <n-collapse-transition :show="addOrUpdateFormData.status === 'Y'">
              <n-form-item label="农机编号" path="cottonPickerRackId">
                <n-input
                  class="w-100px"
                  clearable
                  placeholder="请输入农机编号"
                  v-model:value="addOrUpdateFormData.cottonPickerRackId"
                />
              </n-form-item>
            </n-collapse-transition>
          </n-form>
          <template #action>
            <n-flex justify="end">
              <n-button type="success" @click="submit" :loading="submitLoading">提交</n-button>
              <n-button @click="cancel">取消</n-button>
            </n-flex>
          </template>
        </n-modal>
      </n-flex>
      <n-card>
        <n-data-table
          :loading="loading"
          :columns="farmUavColumns"
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
  </n-flex>
</template>
