<script setup lang="ts">
import * as echarts from 'echarts';
import {monthHomeWorkResultOption, rankingPassRatesOption} from "@/components/jobEvaluation/uav";
import {FormInst, useMessage} from 'naive-ui';

let timestamp = ref(1183135260000)
// 获取月作业评价结果 ---- 折柱混合图
const getMonthHomeWorkResultChart = () => {
  const monthHomeWorkResultDom = document.getElementById('monthHomeWorkResultDom');
  const monthHomeWorkResultChart = echarts.init(monthHomeWorkResultDom);
  monthHomeWorkResultOption && monthHomeWorkResultChart.setOption(monthHomeWorkResultOption);
  const resizeHandler = () => {
    monthHomeWorkResultChart.resize(); // 重新绘制图表
  }
  window.addEventListener('resize', resizeHandler);
}

// 获取合格率排行 ---- 柱状图
const getRankingPassRatesChart = () => {
  const rankingPassRatesDom = document.getElementById('rankingPassRatesDom');
  const rankingPassRatesChart = echarts.init(rankingPassRatesDom);
  rankingPassRatesOption && rankingPassRatesChart.setOption(rankingPassRatesOption);
  const resizeHandler = () => {
    rankingPassRatesChart.resize(); // 重新绘制图表
  }
  window.addEventListener('resize', resizeHandler);
}

onMounted(() => {
  getMonthHomeWorkResultChart()
  getRankingPassRatesChart()
})

let flyerRateResultColumns = [
  {
    title: '序号',
    key: 'index'
  },
  {
    title: '飞手姓名',
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
    flyerName: "张三",
    workArea: '5100',
    workTime: '51.34',
    passRate: '81.52'
  },
  {
    index: '2',
    flyerName: "李四",
    workArea: '5200',
    workTime: '52.34',
    passRate: '82.52'
  },
  {
    index: '3',
    flyerName: "王五",
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

let massifWorkRateResultColumns = [
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
    title: "飞手",
    key: "flyer"
  },
  {
    title: "操作",
    render (row) {
      return h(
        NButton,
        {
          strong: true,
          tertiary: true,
          size: 'small',
          onClick: () => checkMassifWorkRateResultDetail(row)
        },
        { default: () => '查看' }
      )
    }
  }
]
const checkMassifWorkRateResultDetail = (row)=> {

}

let massifWorkRateResultData = [
  {
    index: "1",
    orderId: "DD23071901",
    massifName: "XX地块",
    massifAddress: "XXX",
    customerName: "张三",
    phone: "13757886913",
    workTime: "2-23-7-21 17:11:45",
    rateResult: "合格",
    flyer: "张三",
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
    flyer: "张三",
  },
]
</script>

<template>
  <div style="display: flex">
    <n-card style="width: 50%;" :bordered="false">
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
    <n-card :bordered="false">
      <n-thing>
        <div style="display: flex;justify-content: space-between;">
          <n-h6 prefix="bar" style="--n-bar-color: #248DD4">
            <span>月作业评价结果</span>
          </n-h6>
          <n-date-picker v-model:value="timestamp" type="month" clearable/>
        </div>
      </n-thing>
      <div id="monthHomeWorkResultDom" style="height: 300px;"></div>
    </n-card>
    <n-card style="width: 50%" :bordered="false">
      <n-thing>
        <n-h6 prefix="bar" style="--n-bar-color: #248DD4">
          <span>合格率排行TOP3</span>
        </n-h6>
      </n-thing>
      <div id="rankingPassRatesDom" style="width: 100%;height: 300px;"></div>
    </n-card>
    <n-card :bordered="false">
      <n-thing>
        <n-h6 prefix="bar" style="--n-bar-color: #248DD4">
          <span>飞手评价结果列表</span>
        </n-h6>
      </n-thing>
      <n-data-table
        :columns="flyerRateResultColumns"
        :data="flyerRateResultData"
        :bordered="false"
      />
      <div class="paginationBox">
        <n-pagination v-model:page="page" :page-count="100"
                      style="margin-top: 20px;text-align: center"/>
      </div>
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
      <n-form-item label="合同编号:" path="user.contractId">
        <n-input v-model:value="formValue.user.contractId"/>
      </n-form-item>
      <n-form-item label="客户名称:" path="user.customerName">
        <n-input v-model:value="formValue.user.customerName"/>
      </n-form-item>
      <n-form-item label="飞手:" path="user.flyer">
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
      :columns="massifWorkRateResultColumns"
      :data="massifWorkRateResultData"
      :bordered="false"
      align="center"
      titleAlign="center"
    />
  </n-card>
</template>
<style lang="less" scoped>
.paginationBox {
  display: flex;
  justify-content: center;
}
.n-select{
  width: 150px;
}
::v-deep .n-data-table .n-data-table-th {
  font-size: 16px;
  font-weight: bold;
  text-align: center;
  min-width: 100px;
}
::v-deep .n-data-table .n-data-table-td{
  text-align: center;
}
::v-deep .n-form-item .n-form-item-feedback-wrapper{
  min-height: 0px;
}
</style>
