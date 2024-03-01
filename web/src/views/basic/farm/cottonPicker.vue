<script setup lang="ts">
import LineType from "@/components/LineType/LineType.vue";
import {h} from "vue";
import {NButton, NFlex} from "naive-ui";

let formValue = ref({
  cottonPicker: {
    brand: '',
    belongingPerson: '',
    holdingAttributes: '',
    isAssociationTerminal: '',
    wareHousingTime: ''
  },
})
// 是否关联终端的下拉框内容
let isAssociationTerminalOption = [
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
    title: "农机编号",
    key: "rackNumber"
  },
  {
    title: "品牌",
    key: "brand"
  },
  {
    title: "归属人",
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
    title: "入库时间",
    key: "wareHousingTime"
  },
  {
    title: "是否关联终端",
    key: "isAssociationTerminal"
  },
  {
    title: "所属公司",
    key: "affiliatedCompany"
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
            onClick: () => editCottonPicker(row)
          },
          {default: () => '编辑'}),
        h(NButton,
          {
            strong: true,
            tertiary: true,
            size: 'small',
            color: "red",
            onClick: () => deleteCottonPicker(row)
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
    rackNumber: 'EACM23071901',
    brand: "约翰迪尔",
    belongingPerson: "张三",
    phone: "13757886913",
    holdingAttributes: "自有",
    wareHousingTime: "2023年7月19日",
    isAssociationTerminal: "是",
    affiliatedCompany: "XXX公司"
  },
  {
    index: 1,
    rackNumber: 'EACM23071901',
    brand: "约翰迪尔",
    belongingPerson: "张三",
    phone: "13757886913",
    holdingAttributes: "自有",
    wareHousingTime: "2023年7月19日",
    isAssociationTerminal: "是",
    affiliatedCompany: "XXX公司"
  },
])

// 修改采棉机
const editCottonPicker = (row)=> {

}

// 删除采棉机
const deleteCottonPicker = (row)=> {

}

</script>

<template>
  <div style="display: flex">
    <LineType mark="farm" type="farmCottonPicker"></LineType>
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
          <n-form-item label="品牌:" path="cottonPicker.brand">
            <n-input v-model:value="formValue.cottonPicker.brand"/>
          </n-form-item>
          <n-form-item label="归属人:" path="cottonPicker.belongingPerson">
            <n-input v-model:value="formValue.cottonPicker.belongingPerson"/>
          </n-form-item>
          <n-form-item label="持有属性:" path="cottonPicker.holdingAttributes">
            <n-select v-model:value="formValue.cottonPicker.holdingAttributes" :options="options"/>
          </n-form-item>
          <n-form-item label="是否关联终端:" path="cottonPicker.isAssociationTerminal">
            <n-select v-model:value="formValue.cottonPicker.isAssociationTerminal"
                      :options="isAssociationTerminalOption"/>
          </n-form-item>
          <n-form-item label="入库时间:" path="cottonPicker.wareHousingTime">
            <n-date-picker v-model:value="formValue.cottonPicker.wareHousingTime" type="daterange" clearable
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
    </div>
  </div>
</template>
<style lang="less" scoped>
.n-select {
  width: 150px;
}

::v-deep .n-form-item .n-form-item-feedback-wrapper {
  min-height: 0px;
}

.actionCard {
  ::v-deep .n-card__content {
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

::v-deep .n-data-table .n-data-table-td {
  text-align: center;
}
</style>
