<script setup lang="ts">
import {RowData} from "naive-ui/es/data-table/src/interface";
import {getCottons} from "@/api/cottonField";
import {addRecord, createRecord, delCottonField, getRecord} from "@/api/harvestSchedule";
import {NButton, NFlex, NFormItem} from "naive-ui";
import {CottonFieldVo} from "@/store/api/cottonField";
import {
  AddHarvestScheduleFo,
  DeleteHarvestScheduleRecordCottonFieldFo,
  HarvestScheduleRecordPageFo,
  HarvestScheduleRecordPageVo
} from "@/store/api/harvestSchedule";
import {GinneryVo} from "@/store/api/ginnery";
import CaretGroup from '@/components/icons/CaretGroup.vue'
import {BriefcaseOutline, DocumentTextOutline} from '@vicons/ionicons5'

const route = useRoute()

const loading = ref<boolean>(false)

const columns = ref([
  {
    key: 'label',
    title: '排列方式:',
    colSpan: () => 3,
    render: (row) => {
      return h(NFlex, {
        vertical: true
      }, {
        default: () => [
          h(NFlex, {
            wrap: false,
            justify: 'space-between'
          }, {
            default: () => [
              h('div', `棉田地址： ${row.addr}`),
              h(NButton, {
                text: true,
                type: 'info',
                onClick: () => showRecordModal(row)
              }, {
                default: () => '选择此地'
              })
            ]
          }),
          h(NFlex, {
            vertical: true,
          }, {
            default: () => [
              h(NFlex, {
                wrap: false
              }, {
                default: () => [
                  h(NFormItem, {
                    label: '棉花品种：',
                    showFeedback: false,
                    labelPlacement: 'left',
                    class: 'w-1/2'
                  }, {
                    default: () => row.cottonVariety
                  }),
                  h(NFormItem, {
                    label: '种植面积：',
                    showFeedback: false,
                    labelPlacement: 'left'
                  }, {
                    default: () => `${row.cultivatedArea} 亩`
                  }),
                ]
              }),
              h(NFlex, {
                wrap: true
              }, {
                default: () => [
                  h(NFormItem, {
                    label: '联系人：',
                    showFeedback: false,
                    labelPlacement: 'left',
                    class: 'w-1/2'
                  }, {
                    default: () => row.contacts.username ?? '无'
                  }),
                  h(NFormItem, {
                    label: '联系方式：',
                    showFeedback: false,
                    labelPlacement: 'left'
                  }, {
                    default: () => row.contacts.phone ?? '无'
                  }),
                ]
              })
            ]
          })
        ]
      })
    }
  },
  {
    key: 'area',
    title: '面积',
    sorter: (row1, row2) => row1.cultivatedArea - row2.cultivatedArea,
    renderSorterIcon: (options: { order }) => {
      return h(CaretGroup, {order: options.order})
    }
  },
  {
    key: 'distance',
    title: '距离',
    sorter: true,
    renderSorterIcon: (options: { order }) => {
      return h(CaretGroup, {order: options.order})
    }
  },
])

const modalColumns = ref([
  {
    key: 'index',
    title: '序号',
    render: (_, index) => {
      return index + 1
    }
  },
  {
    key: 'plotName',
    title: '地块名称'
  },
  {
    key: 'cottonVariety',
    title: '品种'
  },
  {
    key: 'cultivatedArea',
    title: '面积（亩）'
  },
  {
    key: 'addr',
    title: '地址'
  },
  {
    key: 'username',
    title: '联系人',
    render: (row) => {
      return row.contacts.username
    }
  },
  {
    key: 'phone',
    title: '联系方式',
    render: (row) => {
      return row.contacts.phone
    }
  },
  {
    key: 'createTime',
    title: '调度时间'
  },
  {
    key: 'actions',
    title: '操作',
    render (row) {
      return h(
        NButton,
        {
          strong: true,
          tertiary: true,
          size: 'small',
          class: 'text-red',
          onClick: () => deleteCottonFields(row)
        },
        { default: () => '删除' }
      )
    }
  },
])

const summary = (pageData: RowData[]) => {
  return {
    label: {
      value: h(NFlex, {
        wrap: false
      }, {
        default: () => [
          h('div', {class: 'w-1/2'}, `总数量： ${pageData.length} 块`),
          h('div', {class: 'w-1/2'}, `总面积： ${pageData.reduce((prevValue, row) => prevValue + row.cultivatedArea, 0)} 亩`)
        ]
      }),
      colSpan: 3
    }
  }
}

const data = ref<CottonFieldVo[]>([])

function init() {
  getCottons(route.params.factoryId as string).then(res => {
    data.value = res.data
    originalData.value = res.data;
  })
}
// 保存原始数据
const originalData = ref<CottonFieldVo[]>([]);

// 根据棉地 地址搜索
const searchAddress = (value: string, option: object) => {
  if (value) {
    data.value = originalData.value.filter(item => item.addr && item.addr.includes(value))
  } else {
    // 如果 value 为空，则恢复原始数据
    data.value = originalData.value;
  }
}

// 分页获取调度单信息参数
const getRecordPageInfo = ref<HarvestScheduleRecordPageFo>({
  ginneryId: "",
  page: 1,
  size: 5
})

// 弹窗内调度单信息展示
let recordModalInfo = ref<HarvestScheduleRecordPageVo>()
// 弹窗内调度单表格展示
let recordModalTableData = ref<GinneryVo>();

// 获取调度单信息
const getRecords = async ()=> {
  let result = await getRecord(getRecordPageInfo.value);
  recordModalInfo.value = result.data
  recordModalTableData.value = result.data.cottonFields
  deleteCottonFieldInfo.value.dispatchId = result.data.dispatchId
  dispatchId.value = result.data.dispatchId
}

// 添加调度单记录参数
const addRecordInfo = ref<AddHarvestScheduleFo>({
  ginneryId: "",
  cottonFieldId: ""
})

// 创建调度单
const addRecords = async ()=> {
  await addRecord(addRecordInfo.value)
}

// 显示调度单弹窗
const showRecordModal = async (row) => {
  addRecordInfo.value.ginneryId = row.ginnery.id
  addRecordInfo.value.cottonFieldId = row.id
  getRecordPageInfo.value.ginneryId = row.ginnery.id
  await addRecords()
  await getRecords()
  showCreate.value = true
}

let deleteCottonFieldInfo = ref<DeleteHarvestScheduleRecordCottonFieldFo>({
  dispatchId: "",
  cottonFieldId: ""
})

// 删除调度单内的棉地信息
const deleteCottonFields = async (row) => {
  deleteCottonFieldInfo.value.cottonFieldId = row.id
  await delCottonField(deleteCottonFieldInfo.value)
  await getRecords()
}

let dispatchId = ref<string>("");

// 生成调度单
const createRecords = async () => {
 await createRecord(dispatchId.value)
  showCreate.value = false
}

init()

const showCreate = ref<boolean>(false)

</script>

<template>
  <div class="box relative w-full h-87vh p-20px">
    <div class="absolute top-20px right-200px">
      <n-flex :wrap="false" :size="60">
        <n-button text class="text-white" @click="() => showCreate = true">
          <template #icon>
            <n-icon :component="DocumentTextOutline" />
          </template>
          调度单
        </n-button>
        <n-button text class="text-white">
          <template #icon>
            <n-icon :component="BriefcaseOutline" />
          </template>
          工具箱
        </n-button>
      </n-flex>
    </div>
    <div class="w-25%">
      <n-select
        clearable
        filterable
        @search="searchAddress"
        :loading="loading"
        :show-arrow="false"
        :show="false"
        placeholder="请输入地块名"
        :tag="true"
      />
      <n-data-table
        class="mt-10px bg-white"
        single-column
        max-height="250px"
        :columns="columns"
        :data="data"

      />
    </div>
    <div class="w-25%" style="transform: translateY(-10px)" v-show="data.length > 0">
      <n-data-table
        class="mt-10px bg-white summaryDataTable"
        max-height="250px"
        :summary="summary"
        :columns="columns"
        :data="data"
        summary-placement="top"
        :bordered="false"
      />
    </div>
  </div>
  <n-modal
    v-model:show="showCreate"
    title="调度单"
    preset="card"
    class="w-850px"
    :mask-closable="false"
  >
    <n-flex vertical size="large">
      <n-form
        label-placement="left"
        :show-feedback="false"
        :label-width="150"
        :model="recordModalInfo"
      >
        <n-flex :wrap="false">
          <n-form-item class="w-1/2" label="调度单编号：">
            {{ recordModalInfo.dispatchId }}
          </n-form-item>
          <n-form-item label="生成时间：">
            {{ recordModalInfo.createTime }}
          </n-form-item>
        </n-flex>
        <n-flex>
          <n-form-item class="w-1/2" label="轧花厂名称：">
            {{ recordModalInfo.ginnery.factoryName }}
          </n-form-item>
          <n-form-item label="地址：">
            {{ recordModalInfo.ginnery.addr }}
          </n-form-item>
        </n-flex>
        <n-flex>
          <n-form-item class="w-1/2" label="联系人：">
            {{ recordModalInfo.ginnery.contacts.username }}
          </n-form-item>
          <n-form-item label="联系方式：">
            {{ recordModalInfo.ginnery.contacts.phone }}
          </n-form-item>
        </n-flex>
      </n-form>
      <n-data-table
        :columns="modalColumns"
        :data="recordModalTableData"
      />
    </n-flex>
    <template #action>
      <n-flex :size="50" justify="center">
        <n-button type="info" @click="createRecords">确定提交</n-button>
        <n-button type="info" ghost @click="showCreate = false">取消提交</n-button>
      </n-flex>
    </template>
  </n-modal>
</template>

<style lang="less" scoped>
.box {
  background: url("@/assets/images/recoveryDispatch/u2282.png") no-repeat;
  background-size: 100% 100%;
}

.clickable-button {
  transition: background-color 0.3s ease;
  background-color: white;
}

.clickable-button:hover {
  background-color: #f1f5f9;
}

::v-deep(.summaryDataTable .n-data-table-tr--summary) {
  display: table-row !important;
}

::v-deep(.summaryDataTable .n-data-table-tr) {
  display: none;
}
</style>
