<script setup lang="ts">
import * as echarts from 'echarts';
import {workResultOption} from "@/components/jobEvaluation/cottonPicker";
import {JobType} from "@/store/api/job/evaluation";
import FilterPageData from "@/views/jobEvaluation/details/filterPageData.vue";
import QualifiedPercentPageData from "@/views/jobEvaluation/details/qualifiedPercentPageData.vue";

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
      <n-card class="w-5/12" :bordered="false">
        <template #header>
          <n-h6 prefix="bar" style="--n-bar-color: #248DD4; --n-margin: 0">
            <span>作业评价结果</span>
          </n-h6>
        </template>
        <div id="workResultDom" class="w-full h-full"></div>
      </n-card>
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
