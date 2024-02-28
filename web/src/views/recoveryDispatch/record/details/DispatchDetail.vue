<script setup lang="ts">

</script>

<template>
  <n-list style="padding: 20px">
    <template #header>
      <div style="display: flex">
        <img src="../../../../assets/images/homePage/u516.png" style="width: 30px">
        <span style="color: #289CEB;transform: translate(5px, 5px)">基本信息</span>
      </div>
    </template>
    <template #footer>
      <n-descriptions label-placement="left">
        <n-descriptions-item>
          <template #label>
            轧花厂名称
          </template>
          xxx轧花厂
        </n-descriptions-item>
        <n-descriptions-item>
          <template #label>
            地址
          </template>
          xxx州xxx路
        </n-descriptions-item>
        <n-descriptions-item>
          <template #label>
            联系人
          </template>
          张三
        </n-descriptions-item>
        <n-descriptions-item>
          <template #label>
            联系方式
          </template>
          13757886913
        </n-descriptions-item>
        <n-descriptions-item>
          <template #label>
            加工能力(亩)
          </template>
          10000
        </n-descriptions-item>
        <n-descriptions-item>
          <template #label>
            调度面积(亩)
          </template>
          60000
        </n-descriptions-item>
        <n-descriptions-item>
          <template #label>
            调度单编号
          </template>
          DD2023811001
        </n-descriptions-item>
        <n-descriptions-item>
          <template #label>
            调度单时间
          </template>
          2023-8-21 14:00
        </n-descriptions-item>
      </n-descriptions>
    </template>
  </n-list>
  <n-list style="padding: 20px">
    <template #header>
      <div style="display: flex">
        <img src="../../../../assets/images/homePage/u516.png" style="width: 30px">
        <span style="color: #289CEB;transform: translate(5px, 5px)">调度信息</span>
      </div>
    </template>
    <template #footer>
      <n-table :bordered="false" :single-line="false" style="text-align: center">
        <thead>
        <tr>
          <th>序号</th>
          <th>地块名称</th>
          <th>品种</th>
          <th>面积(亩)</th>
          <th>地址</th>
          <th>联系人</th>
          <th>联系方式</th>
          <th>调度时间</th>
        </tr>
        </thead>
        <tbody>
        <tr>
          <td>1</td>
          <td>xxx地块</td>
          <td>新陆旱76号</td>
          <td>800</td>
          <td>
            <div style="display: flex;justify-content: center">
              <img src="../../../../assets/images/dispatchDetails/u575.png" style="width: 20px;height: 20px">
              <span>伊犁州XXX路</span>
            </div>
          </td>
          <td>张三</td>
          <td>13757886913</td>
          <td>2023-9-14</td>
        </tr>
        <tr>
          <td>1</td>
          <td>xxx地块</td>
          <td>新陆旱76号</td>
          <td>800</td>
          <td>
            <div style="display: flex;justify-content: center">
              <img src="../../../../assets/images/dispatchDetails/u575.png" style="width: 20px;height: 20px">
              <span>伊犁州XXX路</span>
            </div>
          </td>
          <td>张三</td>
          <td>13757886913</td>
          <td>2023-9-14</td>
        </tr>
        </tbody>
      </n-table>
    </template>
  </n-list>
</template>
<style lang="less" scoped>
::v-deep .n-descriptions .n-descriptions-table-wrapper .n-descriptions-table{
  border-spacing: 20px
}
</style>
