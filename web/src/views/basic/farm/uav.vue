<script setup lang="ts">

import LineType from "@/components/LineType/LineType.vue";
let formValue = ref({
  user: {
    contractId: '',
    customerName: '',
    flyer: '',
    rateResult: '',
    workTime: ''
  },
})

let farmUavColumns = ref([
  {
    title: "序号",
    key: "index"
  },
  {
    title: "机架号",
    key: "rackNumber"
  },
  {
    title: "所属服务商",
    key: "serviceProvider"
  },
  {
    title: "所属人",
    key: "belongingPerson"
  },
  {
    title: "联系方式",
    key: "phone"
  },
  {
    title: "持有属性",
    key: "holdingAttributes"
  },
  {
    title: "作业面积(亩)",
    key: "workArea"
  },
  {
    title: "作业时间(小时)",
    key: "workTime"
  },
  {
    title: "喷洒流量(升/亩)",
    key: "sprayFlowRate"
  }
])

let farmData = ref([
  {
    index: 1,
    rackNumber: 'EACM23071901',
    serviceProvider: "农业发展公司",
    belongingPerson: "张三",
    phone: "13757886913",
    holdingAttributes: "自有",
    workArea: 12.22,
    workTime: 123.11,
    sprayFlowRate: 2.2
  },
  {
    index: 1,
    rackNumber: 'EACM23071901',
    serviceProvider: "农业发展公司",
    belongingPerson: "张三",
    phone: "13757886913",
    holdingAttributes: "自有",
    workArea: 12.22,
    workTime: 123.11,
    sprayFlowRate: 2.2
  },
])

</script>
<template>
  <div style="display: flex">
    <LineType mark="farm" type="farmUav"></LineType>
    <div style="width: 100%;">
      <n-card class="actionCard" :bordered="false">
        <n-form
          ref="formRef"
          inline
          :model="formValue"
          :size="size"
          label-placement="left"
          label-align="left"
        >
          <n-form-item label="机架号:" path="user.orderId">
            <n-input v-model:value="formValue.user.orderId"/>
          </n-form-item>
          <n-form-item label="所属服务商:" path="user.customerName">
            <n-input v-model:value="formValue.user.customerName"/>
          </n-form-item>
          <n-form-item label="持有属性:" path="user.flyer">
            <n-select v-model:value="formValue.user.rateResult" :options="options"/>
          </n-form-item>
        </n-form>
        <n-button type="primary">查询</n-button>
      </n-card>
      <n-card style="height: calc(87vh - 84px)">
        <n-data-table
          :columns="farmUavColumns"
          :data="farmData"
          :bordered="false"
        />
      </n-card>
    </div>
  </div>
</template>
<style lang="less" scoped>
.n-select {
  width: 150px;
}
::v-deep .n-form-item .n-form-item-feedback-wrapper{
  min-height: 0px;
}
.actionCard{
  margin-bottom: 10px;
  ::v-deep .n-card__content{
    display: flex;
    justify-content: space-between;
  }
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
</style>
