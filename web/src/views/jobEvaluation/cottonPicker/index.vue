<script setup lang="ts">
import * as echarts from 'echarts';
import {FormInst, useMessage} from 'naive-ui';
import {workResultOption} from "@/components/jobEvaluation/cottonPicker";

let timestamp = ref(1183135260000)
// 获取月作业评价结果 ---- 折柱混合图
const getWorkResultChart = () => {
  const workResultDom = document.getElementById('workResultDom');
  const workResultChart = echarts.init(workResultDom);
  workResultOption && workResultChart.setOption(workResultOption);
  const resizeHandler = () => {
    workResultChart.resize(); // 重新绘制图表
  }
  window.addEventListener('resize', resizeHandler);
}
onMounted(() => {
  getWorkResultChart()
})

let flyerRateResultColumns = [
  {
    title: '序号',
    key: 'index'
  },
  {
    title: '采棉机编号',
    key: 'flyerName'
  },
  {
    title: '作业面积(亩)',
    key: 'workArea',
    sorter: 'default'
  },
  {
    title: '作业时间(小时)',
    key: 'workTime',
    sorter: 'default'
  },
  {
    title: '合格率(%)',
    key: 'passRate',
    sorter: 'default'
  }
]

let flyerRateResultData = [
  {
    index: '1',
    flyerName: "EACM23091401",
    workArea: '5100',
    workTime: '51.34',
    passRate: '81.52'
  },
  {
    index: '2',
    flyerName: "EACM23091401",
    workArea: '5200',
    workTime: '52.34',
    passRate: '82.52'
  },
  {
    index: '3',
    flyerName: "EACM23091401",
    workArea: '5300',
    workTime: '53.34',
    passRate: '83.52'
  },
]
let page = ref(2)

const message = useMessage()
let formValue = ref({
  user: {
    contractId: '',
    customerName: '',
    flyer: '',
    rateResult: '',
    workTime: ''
  },
})

let value = ref(null)
let options = ref([
  {
    label: "Everybody's Got Something to Hide Except Me and My Monkey",
    value: 'song0',
    disabled: true
  },
  {
    label: 'Drive My Car',
    value: 'song1'
  },
])

let cottonPickerRateResultColumns = [
  {
    title: "序号",
    key: "index"
  },
  {
    title: "订单编号",
    key: "orderId"
  },
  {
    title: "地块名称",
    key: "massifName"
  },
  {
    title: "地块地址",
    key: "massifAddress"
  },
  {
    title: "客户名称",
    key: "customerName"
  },
  {
    title: "联系方式",
    key: "phone"
  },
  {
    title: "作业时间",
    key: "workTime"
  },
  {
    title: "评价结果",
    key: "rateResult"
  },
  {
    title: "采棉机编号",
    key: "flyer"
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
          onClick: () => checkMassifWorkRateResultDetail(row)
        },
        {default: () => '查看'}
      )
    }
  }
]
const checkMassifWorkRateResultDetail = (row) => {

}

let cottonPickerRateResultData = [
  {
    index: "1",
    orderId: "DD23071901",
    massifName: "XX地块",
    massifAddress: "XXX",
    customerName: "张三",
    phone: "13757886913",
    workTime: "2-23-7-21 17:11:45",
    rateResult: "合格",
    flyer: "EACM23091401",
  },
  {
    index: "1",
    orderId: "DD23071901",
    massifName: "XX地块",
    massifAddress: "XXX",
    customerName: "张三",
    phone: "13757886913",
    workTime: "2-23-7-21 17:11:45",
    rateResult: "合格",
    flyer: "EACM23091401",
  },
]
</script>

<template>
  <div style="display: flex">
    <n-card style="width: 30%;height: 290px">
      <n-thing>
        <n-h6 prefix="bar" style="--n-bar-color: #248DD4">
          <span>综合合格率</span>
        </n-h6>
      </n-thing>
      <div style="display: flex;justify-content: center;align-items: center">
        <n-progress type="circle" :percentage="80" :offset-degree="120" rail-color="#81D3F8"
                    :stroke-width="15" color="#248DD4" :gap-degree="10" style="margin-top: 50px"/>
      </div>

    </n-card>
    <n-card style="height: 290px">
      <n-thing>
        <div style="display: flex;justify-content: space-between;">
          <n-h6 prefix="bar" style="--n-bar-color: #248DD4">
            <span>作业评价结果</span>
          </n-h6>
        </div>
      </n-thing>
      <div id="workResultDom" style="height: 250px;"></div>
    </n-card>
    <n-card style="height: 290px">
      <n-thing>
        <n-h6 prefix="bar" style="--n-bar-color: #248DD4">
          <span>评价结果列表</span>
        </n-h6>
      </n-thing>
      <n-data-table
        :columns="flyerRateResultColumns"
        :data="flyerRateResultData"
        :bordered="false"
      />
    </n-card>
  </div>
  <n-card style="margin-top: 10px">
    <n-form
      ref="formRef"
      inline
      :label-width="80"
      :model="formValue"
      :size="size"
      label-placement="left"
    >
      <n-form-item label="订单编号:" path="user.orderId">
        <n-input v-model:value="formValue.user.orderId"/>
      </n-form-item>
      <n-form-item label="客户名称:" path="user.customerName">
        <n-input v-model:value="formValue.user.customerName"/>
      </n-form-item>
      <n-form-item label="采棉机:" path="user.flyer">
        <n-input v-model:value="formValue.user.flyer"/>
      </n-form-item>
      <n-form-item label="评价结果:" path="user.rateResult">
        <n-select v-model:value="formValue.user.rateResult" :options="options"/>
      </n-form-item>
      <n-form-item label="作业时间:" path="user.workTime">
        <n-date-picker v-model:value="formValue.user.workTime" type="daterange" clearable />
      </n-form-item>
    </n-form>
  </n-card>
  <n-card style="margin-top: 10px">
    <n-data-table
      :columns="cottonPickerRateResultColumns"
      :data="cottonPickerRateResultData"
      :bordered="false"
      align="center"
      titleAlign="center"
    />
  </n-card>
</template>
<style lang="less" scoped>
.n-select {
  width: 150px;
}

::v-deep .n-data-table .n-data-table-th {
  font-size: 16px;
  font-weight: bold;
  text-align: center;
}

::v-deep .n-data-table .n-data-table-td {
  text-align: center;
}
::v-deep .n-card n-card--bordered{
  height: 290px;
}
.n-card.n-card--bordered {
  margin-right: 10px;
}
::v-deep .n-form-item .n-form-item-feedback-wrapper{
  min-height: 0px;
}
</style>
