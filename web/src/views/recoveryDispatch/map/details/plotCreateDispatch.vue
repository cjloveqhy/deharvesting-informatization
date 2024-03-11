<script setup lang="ts">
import {RowData} from "naive-ui/es/data-table/src/interface";
import {getCottons} from "@/api/cottonField";
import {NButton, NFlex, NFormItem} from "naive-ui";
import {CottonFieldVo} from "@/store/api/cottonField";

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
                onClick: () => showCreate.value = true
              }, {
                default: () => '调度'
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
    title: '面积'
  },
  {
    key: 'distance',
    title: '距离'
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
    title: '操作'
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
    getAddressOption(data.value)
  })
}

const addressOption = ref<Array<object>>([])

const getAddressOption = (data)=> {
  data.forEach(item => {
    let obj = {
      label: "",
      value: ""
    }
    if (item.addr !== null) {
      obj.label = item.addr
      obj.value = item.id
      addressOption.value.push(obj)
    }
  })
}

const originalData = ref<CottonFieldVo[]>([]);

const searchAddress = (value: string, option: object) => {
  if (value) {
    data.value = originalData.value.filter(item => item.addr && item.addr.includes(value))
  } else {
    // 如果 value 为空，则恢复原始数据
    data.value = originalData.value;
  }
}

init()

const showCreate = ref<boolean>(false)

</script>

<template>
  <div class="box w-full h-87vh p-20px">
    <div class="absolute top-20px right-200px">
      <n-flex :wrap="false" :size="60">
        <n-button text class="text-white" @click="() => showCreate = true">调度单</n-button>
        <n-button text class="text-white">工具箱</n-button>
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
        :options="addressOption"
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
    class="w-800px"
    :mask-closable="false"
  >
    <n-flex vertical size="large">
      <n-form
        label-placement="left"
        :show-feedback="false"
        :label-width="150"
      >
        <n-flex :wrap="false">
          <n-form-item class="w-1/2" label="调度单编号：">

          </n-form-item>
          <n-form-item label="生成时间：">

          </n-form-item>
        </n-flex>
        <n-flex>
          <n-form-item class="w-1/2" label="轧花厂名称：">

          </n-form-item>
          <n-form-item label="地址：">

          </n-form-item>
        </n-flex>
        <n-flex>
          <n-form-item class="w-1/2" label="联系人：">

          </n-form-item>
          <n-form-item label="联系方式：">

          </n-form-item>
        </n-flex>
      </n-form>
      <n-data-table
        :columns="modalColumns"
      />
    </n-flex>
    <template #action>
      <n-flex :size="50" justify="center">
        <n-button type="info">生成调度单</n-button>
        <n-button type="info" ghost @click="showCreate = false">隐藏调度单</n-button>
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