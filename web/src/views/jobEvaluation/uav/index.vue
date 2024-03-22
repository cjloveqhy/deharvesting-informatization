<script setup lang="ts">
import {monthHomeWorkResultOption, rankingPassRatesOption} from "@/components/jobEvaluation/uav";
import {NFlex} from 'naive-ui';
import {JobType} from "@/store/api/job/evaluation";
import FilterPageData from "@/views/jobEvaluation/details/filterPageData.vue";
import QualifiedPercentPageData from "@/views/jobEvaluation/details/qualifiedPercentPageData.vue";
import BasicEcharts from "@/components/Echarts/BasicEcharts.vue";
import {getUavPassRateRanking} from "@/api/job/qulifiedPercent";
import {BasicOption} from "@/store/common";

const timestamp = ref(1183135260000)

const uavPassRateRanking = ref<BasicOption[]>([])

function getUavPassRateRankingInfo() {
  getUavPassRateRanking(JobType.Uav).then(res => {
    uavPassRateRanking.value = res.data
  })
}

getUavPassRateRankingInfo()

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
      <basic-echarts class="w-2/6" :bordered="false" :option="monthHomeWorkResultOption" :data="[]" data-field="series[0].data">
        <template #header>
          <n-h6 prefix="bar" style="--n-bar-color: #248DD4; --n-margin: 0">
            <span>月作业评价结果</span>
          </n-h6>
        </template>
        <template #header-extra>
          <n-date-picker size="small" v-model:value="timestamp" type="month" clearable/>
        </template>
      </basic-echarts>
      <basic-echarts class="w-1/6" :bordered="false" :option="rankingPassRatesOption" :data="uavPassRateRanking">
        <template #header>
          <n-h6 prefix="bar" style="--n-bar-color: #248DD4; --n-margin: 0">
            <span>合格率排行TOP3</span>
          </n-h6>
        </template>
      </basic-echarts>
      <qualified-percent-page-data class="w-2/6 min-w-450px" :type="JobType.Uav">
        <template #header>
          <n-h6 prefix="bar" style="--n-bar-color: #248DD4; --n-margin: 0">
            <span>飞手评价结果列表</span>
          </n-h6>
        </template>
      </qualified-percent-page-data>
    </n-flex>
    <filter-page-data :type="JobType.Uav" />
  </n-flex>
</template>
<style lang="less" scoped>

::v-deep(.n-progress.n-progress--circle) {
  width: 80%;
}

</style>
