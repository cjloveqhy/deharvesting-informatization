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
            justify:'space-between'
          }, {
            default: () => [
              h('div', `棉田地址： ${row.addr}`),
              h(NButton, {
                text: true,
                type: 'info'
              }, {
                default: () => '调度'
              })
            ]}),
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
  })
}

init()

</script>

<template>
  <div class="box relative w-full h-87vh">
    <div class="absolute w-25% top-20px left-20px">
      <n-select
        clearable
        filterable
        @search=""
        :show="false"
        :loading="loading"
        :show-arrow="false"
        placeholder="请输入地块名"
      />
      <n-data-table
        class="mt-10px bg-white"
        single-column
        max-height="250px"
        :columns="columns"
        :summary="summary"
        :data="data"
        />
    </div>
  </div>
</template>

<style lang="less" scoped>
.box{
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
</style>
