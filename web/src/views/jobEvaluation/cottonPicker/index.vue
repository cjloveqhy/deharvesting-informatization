<script setup lang="ts">
import {workResultOption} from "@/store/api/job/echarts";
import {JobType} from "@/store/api/job/evaluation";
import FilterPageData from "@/views/jobEvaluation/details/filterPageData.vue";
import QualifiedPercentPageData from "@/views/jobEvaluation/details/qualifiedPercentPageData.vue";
import BasicEcharts from "@/components/Echarts/BasicEcharts.vue";

const data = [
  { days: '01', passNum: 100, noPassNum: 130, passRate: 120 },
  { days: '02', passNum: 50, noPassNum: 50, passRate: 50 },
  { days: '03', passNum: 30, noPassNum: 30, passRate: 30 },
  { days: '04', passNum: 40, noPassNum: 40, passRate: 40 },
  { days: '05', passNum: 60, noPassNum: 60, passRate: 60 },
  { days: '06', passNum: 50, noPassNum: 50, passRate: 50 },
  { days: '07', passNum: 30, noPassNum: 30, passRate: 30 },
  { days: '08', passNum: 100, noPassNum: 100, passRate: 100 }
]

</script>

<template>
  <n-flex vertical>
    <n-flex :wrap="false" class="h-400px min-w-1200px">
      <n-card class="w-1/6" :bordered="false">
        <template #header>
          <n-h6 prefix="bar" style="--n-bar-color: #248DD4; --n-margin: 0">
            <span>综合合格率</span>
          </n-h6>
        </template>
        <n-flex justify="center" align="center" class="w-full h-full">
          <n-progress type="circle" :percentage="80" :offset-degree="120" rail-color="#81D3F8"
                      :stroke-width="15" color="#248DD4" :gap-degree="10" />
        </n-flex>
      </n-card>
      <basic-echarts class="w-5/12" :bordered="false" :option="workResultOption" :data="data">
        <template #header>
          <n-h6 prefix="bar" style="--n-bar-color: #248DD4; --n-margin: 0">
            <span>作业评价结果</span>
          </n-h6>
        </template>
        <template #header-extra>
          <n-flex justify="center" align="center">
            <div class="w-4 h-4 bg-[#81D3F8]"></div>
            <n-h6 style="--n-bar-color: #248DD4; --n-margin: 0">
              不合格
            </n-h6>
            <div class="w-4 h-4 bg-[#02A7F0]"></div>
            <n-h6 style="--n-bar-color: #248DD4; --n-margin: 0">
              合格
            </n-h6>
          </n-flex>
        </template>
      </basic-echarts>
      <qualified-percent-page-data class="w-5/12 min-w-500px" :type="JobType.Cotton_Picker">
        <template #header>
          <n-h6 prefix="bar" style="--n-bar-color: #248DD4; --n-margin: 0">
            <span>采棉机评价结果列表</span>
          </n-h6>
        </template>
      </qualified-percent-page-data>
    </n-flex>
    <filter-page-data :type="JobType.Cotton_Picker" />
  </n-flex>
</template>
<style lang="less" scoped>

::v-deep(.n-progress.n-progress--circle) {
  width: 80%;
}
</style>
