<script setup lang="ts">
import LineType from "@/components/LineType/LineType.vue";
import {h} from "vue";
import {NButton, NFlex} from "naive-ui";
let formValue = ref({
  dataTerminal: {
    dataTerminalId: '',
    brand: '',
    isAssociationFarmMachinery: '',
    associationFarmMachineryId: '',
    updateTime: ''
  },
})
// 是否关联农机的下拉框内容
let isAssociationFarmMachineryOption = [
  {
    label: "是",
    value: 1
  },
  {
    label: "否",
    value: 0
  }
]

// 表格的表头信息
let farmUavColumns = ref([
  {
    title: "序号",
    key: "index"
  },
  {
    title: "终端编号",
    key: "terminalId"
  },
  {
    title: "品牌",
    key: "brand"
  },
  {
    title: "型号",
    key: "model"
  },
  {
    title: "更新时间",
    key: "updateTime"
  },
  {
    title: "SIM卡到期时间",
    key: "SIMCardExpirationTime"
  },
  {
    title: "是否关联农机",
    key: "isAssociationFarmMachinery"
  },
  {
    title: "农机编号",
    key: "farmMachineryId"
  },
  {
    title: '操作',
    key: 'actions',
    render(row) {
      return h(NFlex, [
        h(NButton,
          {
            strong: true,
            tertiary: true,
            size: 'small',
            color: "blue",
            onClick: () => editTerminal(row)
          },
          {default: () => '编辑'}),
        h(NButton,
          {
            strong: true,
            tertiary: true,
            size: 'small',
            color: "red",
            onClick: () => deleteTerminal(row)
          },
          {default: () => '删除'}),
      ])
    }
  }
])
// 表格的数据
let farmData = ref([
  {
    index: 1,
    terminalId: 'EADT23071901',
    brand: "慧达",
    model: "701",
    updateTime: "2023年7月19日",
    SIMCardExpirationTime: "2024年7月19日",
    isAssociationFarmMachinery: "是",
    farmMachineryId: "EACM23071901",
  },
  {
    index: 2,
    terminalId: 'EADT23071901',
    brand: "慧达",
    model: "701",
    updateTime: "2023年7月19日",
    SIMCardExpirationTime: "2024年7月19日",
    isAssociationFarmMachinery: "是",
    farmMachineryId: "EACM23071901",
  },
])

// 修改数据终端
const editTerminal = (row)=> {

}

// 删除数据终端
const deleteTerminal = (row)=> {

}
</script>

<template>
  <n-flex :wrap="false" class="w-full h-87vh">
    <LineType mark="farm" type="farmDataTerminal" class="w-15% h-full"></LineType>
    <n-flex vertical class="w-85%">
      <n-card :bordered="false">
        <n-form
          inline
          label-placement="left"
          label-align="left"
        >
          <n-form-item label="终端编号:" path="dataTerminal.dataTerminalId">
            <n-input v-model:value="formValue.dataTerminal.dataTerminalId"/>
          </n-form-item>
          <n-form-item label="品牌:" path="dataTerminal.brand">
            <n-input v-model:value="formValue.dataTerminal.brand"/>
          </n-form-item>
          <n-form-item label="是否关联农机:" path="dataTerminal.isAssociationFarmMachinery">
            <n-select v-model:value="formValue.dataTerminal.isAssociationFarmMachinery" :options="isAssociationFarmMachineryOption"/>
          </n-form-item>
          <n-form-item label="关联农机编号:" path="dataTerminal.associationFarmMachineryId">
            <n-input v-model:value="formValue.dataTerminal.associationFarmMachineryId"/>
          </n-form-item>
          <n-form-item label="更新时间:" path="dataTerminal.updateTime">
            <n-date-picker v-model:value="formValue.dataTerminal.updateTime" type="daterange" clearable
                           style="width: 70%"/>
          </n-form-item>
        </n-form>
        <n-button type="primary">查询</n-button>
      </n-card>
      <n-button type="primary" style="margin: 10px 0px">新机入库</n-button>
      <n-card style="height: calc(87vh - 84px)">
        <n-data-table
          :columns="farmUavColumns"
          :data="farmData"
          :bordered="false"
        />
      </n-card>
    </n-flex>
  </n-flex>
</template>
