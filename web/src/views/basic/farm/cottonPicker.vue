<script setup lang="ts">
import LineType from "@/components/LineType/LineType.vue";
import {NButton, NFlex, NIcon, NPopconfirm, useMessage} from "naive-ui";
import {
  addFarmCottonPicker, delFarmCottonPicker,
  getBrandOptions,
  getFilterPage,
  updateFarmCottonPicker
} from "@/api/farmCottonPicker";
import {BasicOption, LogicalOptions} from '@/store/common'
import {HoldingAttrOptions} from '@/store/api/farmUav'
import {CottonPickerFo, CottonPickerPageFo, CottonPickerVo} from "@/store/api/farmCottonPicker";
import {getLabel} from "@/utils/optionUtil";
import {useUserApiStore} from "@/store/api/user";
import {UserOutlined, PhoneOutlined} from '@vicons/antd'

const userStore = useUserApiStore()

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
    title: "农机编号",
    key: "rackNumber",
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
    title: "归属人",
    key: "username",
    align: 'center',
    titleAlign: 'center',
  },
  {
    title: "联系方式",
    key: "phone",
    align: 'center',
    titleAlign: 'center',
  },
  {
    title: "持有属性",
    key: "holdingAttr",
    align: 'center',
    titleAlign: 'center',
    render: (row) => {
      return h('span', getLabel(HoldingAttrOptions, row.holdingAttr))
    }
  },
  {
    title: "入库时间",
    key: "createTime",
    align: 'center',
    titleAlign: 'center',
  },
  {
    title: "是否关联终端",
    key: "status",
    align: 'center',
    titleAlign: 'center',
    render: (row) => {
      return h('span', getLabel(LogicalOptions, row.status))
    }
  },
  {
    title: "所属公司",
    key: "affiliatedCompany",
    align: 'center',
    titleAlign: 'center',
  },
  {
    title: '操作',
    key: 'actions',
    render(row) {
      return h(NFlex, {}, {
        default: () => [
          h(NButton,
            {
              strong: true,
              tertiary: true,
              size: 'small',
              color: "blue",
              onClick: () => editCottonPicker(row)
            },
            {default: () => '编辑'}),
          h(NPopconfirm, {
            onPositiveClick: () => deleteCottonPicker(row)
          }, {
            default: () => `您确定要删除农机编号为【${row.rackNumber}】的采棉机吗？`,
            trigger: () => h(NButton,
              {
                strong: true,
                tertiary: true,
                size: 'small',
                color: "red",
              },
              {default: () => '删除'})
          })
        ]
      })
    }
  }
])
// 表格的数据
const data = ref<CottonPickerVo[]>([])

const timeRange = ref<string[] | null>()

const formData = ref<CottonPickerPageFo>({
  page: 1,
  size: 10,
  brand: null,
  belonger: null,
  holdingAttr: null,
  status: null,
  startTime: null,
  endTime: null
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
    brand: null,
    belonger: null,
    holdingAttr: null,
    status: null,
    startTime: null,
    endTime: null
  }
  timeRange.value = null
}

const show = ref<boolean>(false)

const addOrUpdateFormData = ref<CottonPickerFo>({
  id: null,
  rackNumber: null,
  brand: null,
  belonger: null,
  holdingAttr: null,
  affiliatedCompany: null,
})

const rules = {
  rackNumber: { required: true, message: '请输入农机编号', trigger: 'blur' },
  brand: { required: true, message: '请选择品牌', trigger: 'blur' },
  belonger: { required: true, message: '请选择归属者', trigger: 'blur' },
  holdingAttr: { required: true, message: '请选择持有属性', trigger: 'blur' },
}

function resetFormData() {
  addOrUpdateFormData.value = {
    id: null,
    rackNumber: null,
    brand: null,
    belonger: null,
    holdingAttr: null,
    affiliatedCompany: null
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
    basicSubmit(() => addFarmCottonPicker(addOrUpdateFormData.value))
  } else {
    basicSubmit(() => updateFarmCottonPicker(addOrUpdateFormData.value))
  }
}

// 修改采棉机
const editCottonPicker = (row)=> {
  show.value = true
  resetFormData()
  addOrUpdateFormData.value = row as CottonPickerFo
  submitType.value = 'edit'
}

// 删除采棉机
const deleteCottonPicker = (row)=> {
  delFarmCottonPicker(row.id).then(res => {
    message.success(res.data)
    getData()
    initOptions()
  })
}

function renderLabel(option) {
  return h(NFlex, {
    wrap: false
  }, {
    default: () => [
      h(NFlex, {
        wrap: false,
        align: 'center'
      }, {
        default: () => [
          h(NIcon, {component: UserOutlined}),
          h('span', option.username)
        ]
      }),
      h(NFlex, {
        wrap: false,
        align: 'center'
      }, {
        default: () => [
          h(NIcon, {component: PhoneOutlined}),
          h('span', option.phone)
        ]
      })
    ]
  })
}
</script>

<template>
  <n-flex :wrap="false" class="w-full h-87vh">
    <LineType mark="farm" type="farmCottonPicker" class="w-15% h-full"></LineType>
    <n-flex vertical class="w-85%">
      <n-card :bordered="false">
        <n-flex :wrap="false" justify="space-between">
          <n-flex :size="[20, 30]">
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
            <n-form-item label-placement="left" :show-feedback="false" label="归属人:">
              <n-input
                clearable
                placeholder="请输入归属人"
                v-model:value="formData.belonger"
              />
            </n-form-item>
            <n-form-item label-placement="left" :show-feedback="false" label="持有属性:">
              <n-select
                class="w-200px"
                placeholder="请选择持有属性"
                :options="HoldingAttrOptions"
                v-model:value="formData.holdingAttr"
              />
            </n-form-item>
            <n-form-item label-placement="left" :show-feedback="false" label="是否关联终端:">
              <n-select
                clearable
                class="w-200px"
                placeholder="请选择是否关联终端"
                :options="LogicalOptions"
                v-model:value="formData.status"
              />
            </n-form-item>
            <n-form-item label-placement="left" :show-feedback="false" label="入库时间:">
              <n-date-picker
                clearable
                type="daterange"
                value-format="yyyy-MM-dd HH:mm:ss"
                v-model:formatted-value="timeRange"
              />
            </n-form-item>
          </n-flex>
          <n-button type="default" @click="reset">重置</n-button>
          <n-button type="info" @click="getData">查询</n-button>
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
            label-width="auto"
            label-align="right"
            label-placement="left"
            :model="addOrUpdateFormData"
          >
            <n-form-item label="农机编号" path="rackNumber">
              <n-input
                clearable
                placeholder="请输入农机编号"
                v-model:value="addOrUpdateFormData.rackNumber"
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
            <n-form-item label="归属者" path="belonger">
              <n-select
                clearable
                value-field="id"
                label-field="username"
                placeholder="请选择归属者"
                :render-label="renderLabel"
                :options="userStore.userOptions"
                :render-tag="({option}) => option.username"
                v-model:value="addOrUpdateFormData.belonger"
              />
            </n-form-item>
            <n-form-item label="持有属性" path="holdingAttr">
              <n-select
                clearable
                placeholder="请选择持有属性"
                :options="HoldingAttrOptions"
                v-model:value="addOrUpdateFormData.holdingAttr"
              />
            </n-form-item>
            <n-form-item label="所属公司" path="affiliatedCompany">
              <n-input
                clearable
                placeholder="请输入所属公司"
                v-model:value="addOrUpdateFormData.affiliatedCompany"
              />
            </n-form-item>
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
