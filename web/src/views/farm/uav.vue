<script setup lang="ts">
import LineType from "@/components/LineType/LineType.vue";
import {FarmUavPageFo, FarmUavVo, HoldingAttrOptions} from "@/store/api/farmUav";
import {getFilterPage} from "@/api/farmUav";
import {getLabel} from "@/utils/optionUtil";

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
    title: "机架号",
    key: "rackNumber",
    align: 'center',
    titleAlign: 'center',
  },
  {
    title: "所属服务商",
    key: "serviceProvider",
    align: 'center',
    titleAlign: 'center',
  },
  {
    title: "所属人",
    key: "username",
    align: 'center',
    titleAlign: 'center',
    render: (row) => {
      return h('span', row.belonger.username)
    }
  },
  {
    title: "联系方式",
    key: "phone",
    align: 'center',
    titleAlign: 'center',
    render: (row) => {
      return h('span', row.belonger.phone)
    }
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
    title: "作业面积(亩)",
    key: "workArea",
    align: 'center',
    titleAlign: 'center',
  },
  {
    title: "作业时间(小时)",
    key: "workTime",
    align: 'center',
    titleAlign: 'center',
  },
  {
    title: "喷洒流量(升/亩)",
    key: "sprayFlowRate",
    align: 'center',
    titleAlign: 'center',
  }
])

const formData = ref<FarmUavPageFo>({
  page: 1,
  size: 10,
  rackNumber: null,
  serviceProvider: null,
  holdingAttr: null,
})

const farmData = ref<FarmUavVo[]>([])

const loading = ref<boolean>(false)

const pages = ref<number>(0)

function getData() {
  loading.value = true
  getFilterPage(formData.value).then(res => {
    farmData.value = res.data.records
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
  <n-flex :wrap="false" class="w-full h-87vh">
    <LineType mark="farm" type="farmUav" class="w-15% h-full"></LineType>
    <n-flex vertical class="w-85%">
      <n-card>
        <n-flex :wrap="false" justify="space-between">
          <n-flex :wrap="false">
            <n-form-item label-placement="left" :show-feedback="false" label="机架号:">
              <n-input v-model:value="formData.rackNumber" placeholder="请输入机架号" clearable />
            </n-form-item>
            <n-form-item label-placement="left" :show-feedback="false" label="所属服务商:">
              <n-input v-model:value="formData.serviceProvider" placeholder="请输入所属服务商" clearable />
            </n-form-item>
            <n-form-item label-placement="left" :show-feedback="false" label="持有属性:">
              <n-select
                clearable
                class="w-200px"
                placeholder="请选择持有属性"
                :options="HoldingAttrOptions"
                v-model:value="formData.holdingAttr"
              />
            </n-form-item>
          </n-flex>
          <n-button type="primary" @click="getData">查询</n-button>
        </n-flex>
      </n-card>
      <n-card>
        <n-data-table
          :loading="loading"
          :columns="farmUavColumns"
          :data="farmData"
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
