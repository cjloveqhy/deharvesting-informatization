<script setup lang="ts">
import * as echarts from 'echarts';
import {onMounted} from 'vue/dist/vue.esm-bundler.js';

const headerLeftData = [
  {
    title: "作业面积",
    workload: "35,432",
    unit: "亩"
  },
  {
    title: "累计运行",
    workload: "28",
    unit: "台"
  },
  {
    title: "平均工作时长",
    workload: "9.25",
    unit: "小时"
  },
]

const headerRightData = [
  {
    title: "作业面积",
    workload: "135,432",
    unit: "亩"
  },
  {
    title: "累计运行",
    workload: "19",
    unit: "台"
  },
  {
    title: "平均工作时长",
    workload: "12.50",
    unit: "小时"
  },
]

const getMyChart = () => {
  const chartDom = document.getElementById('main_right_category');
  const myChart = echarts.init(chartDom);
  let option;
  option = {
    xAxis: {
      type: 'category',
      data: ['9.1', '9.2', '9.3', '9.4', '9.5']
    },
    yAxis: {
      type: 'value',
      min: 0,
      max: 100,
      interval: 20,
      boundaryGap: [0.2, 0.2]
    },
    series: [
      {
        data: [100, 80, 100, 80, 100],
        type: 'line',
        smooth: true,
        itemStyle: {
          normal: {
            lineStyle: {
              color: '#02A7F0'
            }
          }
        },
        showSymbol: false,
      }
    ]
  };
  option && myChart.setOption(option);
  const resizeHandler = () => {
    myChart.resize(); // 重新绘制图表
  }
  window.addEventListener('resize', resizeHandler);
}
onMounted(() => {
  getMyChart()
})
</script>

<template>
  <!--首页头部-->
  <n-card title="今日作业概况" :segmented="{content: true,footer: 'soft'}" style="--n-padding-top: 10px;--n-padding-bottom: 10px;--n-title-font-size: 16px">
    <div style="display: flex">
      <n-card style="margin: 0px 30px" class="header_card_left_border">
        <div style="display: flex;transform: translate(-25px, 20px);">
          <img src="../../assets/images/homePage/u460.png" class="header_card_left_img">
          <n-card :bordered="false" size="small">
              <n-flex size="large">
                <n-space v-for="(item, index) in headerLeftData" :key="index">
                  <n-space vertical style="text-align: center;margin-top: 20px">
                    <n-gradient-text style="font-size: 1rem;color: #81D3F8">
                      <n-space style="text-align: center">
                        <n-gradient-text style="font-size: 1.2rem;color: #81D3F8">{{item.workload }}
                        </n-gradient-text>
                        <n-gradient-text style="font-size: 1rem;color: #81D3F8;transform: translate(0, 10px)">{{item.unit }}
                        </n-gradient-text>
                      </n-space>
                    </n-gradient-text>
                    <n-gradient-text style="color: black;text-align: center">{{item.title }}</n-gradient-text>
                  </n-space>
                  <img src="../../assets/images/homePage/u378.svg" v-if="item.title != '平均工作时长'" style="margin-left: 10%">
                </n-space>
              </n-flex>
          </n-card>
        </div>
      </n-card>
      <n-card style="margin: 0px 30px" class="header_card_right_border">
        <div style="display: flex; transform: translate(-55px, 0px);">
          <img src="../../assets/images/homePage/u462.png" class="header_card_left_img">
          <n-card :bordered="false" size="small">
            <n-flex size="small">
              <n-space v-for="(item, index) in headerRightData" :key="index">
                <n-space vertical style="text-align: center;margin-top: 20px">
                    <n-space>
                      <n-gradient-text style="font-size: 1.2rem;color: #FACD91">{{item.workload }}</n-gradient-text>
                      <n-gradient-text style="font-size: 1rem;color: #FACD91;transform: translate(0, 10px)">{{item.unit }}</n-gradient-text>
                    </n-space>
                  <n-gradient-text style="color: black;text-align: center">{{item.title}}
                  </n-gradient-text>
                </n-space>
                <img src="../../assets/images/homePage/u391.svg" v-if="item.title != '平均工作时长'">
              </n-space>
            </n-flex>
          </n-card>
        </div>
      </n-card>
    </div>
  </n-card>
  <!--首页中部-->
  <div style="display:flex;margin-top: 10px">
    <n-card title="设备监控" :segmented="{content: true,footer: 'soft'}"
            style="margin-right: 10px;width: 140%;">
      <div style="display: flex">
        <n-card :bordered="false" class="clearPadding">
          <n-space vertical>
            <n-card class="main_left_small_top_border">
              <n-space>
                <img src="../../assets/images/homePage/u414.png" width="60" height="60">
                <n-space vertical style="text-align: center">
                  <n-gradient-text style="color: black">无人机总数</n-gradient-text>
                  <n-space>
                    <n-gradient-text size="22" style="transform: translate(5px, -8px);color: #7BCCF9">45</n-gradient-text>
                    <n-gradient-text style="color: black">台</n-gradient-text>
                  </n-space>
                </n-space>
              </n-space>
            </n-card>
            <n-card class="main_left_small_bottom_border">
              <n-space>
                <img src="../../assets/images/homePage/u415.png" width="60" height="60">
                <n-space vertical>
                  <n-gradient-text style="color: black">无人机总数</n-gradient-text>
                  <n-space>
                    <n-gradient-text size="22" style="transform: translate(5px, -8px);color: #FBBC7F">45</n-gradient-text>
                    <n-gradient-text style="color: black">台</n-gradient-text>
                  </n-space>
                </n-space>
              </n-space>
            </n-card>
          </n-space>

        </n-card>
        <n-card class="main_left_big_left_border">
          <div style="position: relative">
            <n-progress type="dashboard" gap-position="bottom" :percentage="40"
                        color="#81CEF9" rail-color="#fff"
                        :stroke-width="12" indicator-text-color="#fff" unit="台"/>
            <div class="main_left_progress_title">当前无人机在线总数</div>
          </div>
        </n-card>
        <n-card class="main_left_big_right_border">
          <div style="position: relative">
            <n-progress type="dashboard" gap-position="bottom" :percentage="40"
                        color="#FBBC7F" rail-color="#fff"
                        :stroke-width="12" indicator-text-color="#fff" unit="台"/>
            <div class="main_right_progress_title">当前无人机在线总数</div>
          </div>
        </n-card>
      </div>

    </n-card>
    <n-card title="统计评价" :segmented="{content: true,footer: 'soft'}">
      <template #header-extra>
        <span>时间选择</span>
        <n-date-picker v-model:value="range" type="daterange" clearable style="margin-left: 5px;width: 18rem" />
        <n-button style="margin-left: 5px" type="primary">查询</n-button>
      </template>
      <n-split direction="horizontal" style="height: 200px" :max="0.75" :min="0.35" :resize-trigger-size="0" :default-size="0.35">
        <template #1>
          <n-progress type="circle" :percentage="80" :offset-degree="120" color="#B4DEF6" :stroke-width="12" rail-color="#fff"  /><br><br>
          <n-gradient-text size="14" style="margin-left: 25px">平均合格率</n-gradient-text>
        </template>
        <template #2>
          <div id="main_right_category" style="height: 300px;transform: translateY(-50px)"></div>
        </template>
      </n-split>
    </n-card>
  </div>
  <div style="display: flex;margin-top: 10px">
    <n-card title="无人机作业信息" :segmented="{content: true,footer: 'soft'}" style="margin-right: 10px; width: 120%">
      <n-flex justify="space-between">
        <n-space vertical>
          <n-space>
            <img src="../../assets/images/homePage/u495.png" class="bottom_left_img" style="background-color: #F2F2F2;border-radius: 10px">
            <n-space vertical>
              <div style="font-size: 1rem">飞行总时长(小时)</div>
              <div style="color: #81CEF9; font-size: 1.2rem;font-weight: bold">40.25</div>
            </n-space>
          </n-space>
          <n-space>
            <img src="../../assets/images/homePage/u498.png" class="bottom_left_img" style="background-color: #F2F2F2;border-radius: 10px">
            <n-space vertical>
              <div style="font-size: 1rem">飞行总距离(小时)</div>
              <div style="color: #81CEF9; font-size: 1.2rem;font-weight: bold">380.88</div>
            </n-space>
          </n-space>
        </n-space>
        <n-space vertical>
          <n-space>
            <img src="../../assets/images/homePage/u501.png" class="bottom_left_img" style="background-color: #F2F2F2;border-radius: 10px">
            <n-space vertical>
              <div style="font-size: 1rem">作业总面积(亩)</div>
              <div style="color: #81CEF9; font-size: 1.2rem;font-weight: bold">1817.05</div>
            </n-space>
          </n-space>
          <n-space>
            <img src="../../assets/images/homePage/u504.png" class="bottom_left_img" style="background-color: #F2F2F2;border-radius: 10px">
            <n-space vertical>
              <div style="font-size: 1rem">总喷洒流量(升)</div>
              <div style="color: #81CEF9; font-size: 1.2rem;font-weight: bold">821.07</div>
            </n-space>
          </n-space>
        </n-space>
      </n-flex>
    </n-card>
    <n-card title="采棉机作业信息" :segmented="{content: true,footer: 'soft'}" style="margin-right: 10px; width: 120%">
      <n-flex justify="space-between">
        <n-space vertical>
          <n-space>
            <img src="../../assets/images/homePage/u507.png" class="bottom_left_img" style="background-color: #F2F2F2;border-radius: 10px">
            <n-space vertical>
              <div style="font-size: 1rem">运行总时长(小时)</div>
              <div style="color: #FACD91; font-size: 1.2rem;font-weight: bold">40.25</div>
            </n-space>
          </n-space>
          <n-space>
            <img src="../../assets/images/homePage/u510.png" class="bottom_left_img" style="background-color: #F2F2F2;border-radius: 10px">
            <n-space vertical>
              <div style="font-size: 1rem">总行驶里程(小时)</div>
              <div style="color: #FACD91; font-size: 1.2rem;font-weight: bold">380.88</div>
            </n-space>
          </n-space>
        </n-space>
        <n-space vertical>
          <n-space>
            <img src="../../assets/images/homePage/u513.png" class="bottom_left_img" style="background-color: #F2F2F2;border-radius: 10px">
            <n-space vertical>
              <div style="font-size: 1rem">作业总面积(亩)</div>
              <div style="color: #FACD91; font-size: 1.2rem;font-weight: bold">1817.05</div>
            </n-space>
          </n-space>
        </n-space>
      </n-flex>
    </n-card>
    <n-card title="作物监控" :segmented="{content: true,footer: 'soft'}" style="min-width: 320px">
      <n-space style="margin-bottom: 20px">
        <img src="../../assets/images/homePage/u516.png" width="40" height="40">
        <n-gradient-text>脱叶率</n-gradient-text>
        <n-progress type="line" :percentage="60" :indicator-placement="'outside'"
                    style="width: 15rem" color="#81CEF9" indicator-text-color="#81CEFA" :height="12" />
      </n-space>
      <n-space style="margin-bottom: 20px">
        <img src="../../assets/images/homePage/u516.png" width="40" height="40">
        <n-gradient-text>脱叶率</n-gradient-text>
        <n-progress type="line" :percentage="60" :indicator-placement="'outside'"
                    style="width: 15rem" color="#81CEF9" indicator-text-color="#81CEFA" :height="12" />
      </n-space>
      <n-space>
        <img src="../../assets/images/homePage/u516.png" width="40" height="40">
        <n-gradient-text>脱叶率</n-gradient-text>
        <n-progress type="line" :percentage="60" :indicator-placement="'outside'"
                    style="width: 15rem" color="#81CEF9" indicator-text-color="#81CEFA" :height="12" />
      </n-space>
    </n-card>
  </div>
</template>
<style lang="less" scoped>
::v-deep .n-card .n-card-header{
  padding: 10px;
  font-size: 16px;
  .n-card .n-card-header{
    padding: 0;
  }
}
.header_card_left_border {
  background-color: rgba(255, 255, 255, 1);
  border-width: 1px;
  border-style: solid;
  border-color: rgba(129, 211, 248, 1);
  border-radius: 5px;
  box-shadow: 5px 5px 5px rgb(0 0 0 / 35%);

  .header_card_left_img {
    width: 10rem;
    height: 6rem;
    background-color: white;
    flex: none;
  }

  ::v-deep .n-card > .n-card__content, .n-card > .n-card__footer {
    padding: 0;
  }

  ::v-deep .n-card > .n-card__content:first-child {
    padding: 0px;
  }
}

.header_card_right_border {
  background-color: rgba(255, 255, 255, 1);
  border-width: 1px;
  border-style: solid;
  border-color: rgba(250, 205, 145, 1);
  border-radius: 5px;
  box-shadow: 5px 5px 5px rgb(0 0 0 / 35%);

  .header_card_left_img {
    width: 10rem;
    height: 8rem;
    background-color: white;
    flex: none;
  }
}

.main_left_small_border {
  background: inherit;
  background-color: rgba(235, 244, 255, 1);
  border-radius: 8px;
  -moz-box-shadow: 5px 5px 5px rgba(0, 0, 0, 0.349019607843137);
  -webkit-box-shadow: 5px 5px 5px rgb(0 0 0 / 35%);
  box-shadow: 5px 5px 5px rgb(0 0 0 / 35%);
}

.main_left_small_top_border {
  background: inherit;
  background-color: rgba(235, 244, 255, 1);
  border-radius: 8px;
  -moz-box-shadow: 5px 5px 5px rgba(0, 0, 0, 0.349019607843137);
  -webkit-box-shadow: 5px 5px 5px rgb(0 0 0 / 35%);
  box-shadow: 5px 5px 5px rgb(0 0 0 / 35%);
  margin-bottom: 10px;
}

.main_left_small_bottom_border {
  background-color: rgba(252, 245, 227, 1);
  border-radius: 8px;
  box-shadow: 5px 5px 5px rgb(0 0 0 / 35%);
}


.main_left_big_left_border {
  background-color: rgba(198, 233, 247, 1);
  border-radius: 8px;
  box-shadow: 5px 5px 5px rgb(0 0 0 / 35%);
  margin-right: 10px;
  margin-top: 10px;
  display: flex;
  justify-content: center;
  align-items: center;
}
.main_left_big_right_border {
  background-color: rgba(247, 233, 210, 1);
  border-radius: 8px;
  box-shadow: 5px 5px 5px rgb(0 0 0 / 35%);
  margin-top: 10px;
  display: flex;
  justify-content: center;
  align-items: center;
}
::v-deep .clearPadding{
  min-width: 232px;
  .n-card__content{
    padding: 0px;
    margin-top: 10px;
    margin-right: 20px;
  }
}

.main_left_progress_title{
  position: absolute;
  top: 90px;
  left: -12px;
  background-color: white;
  padding: 5px 0px;
  border-radius: 20px;
  width: 120%;
  text-align: center;
  color: #81CEF9;
}


.main_right_progress_title{
  position: absolute;
  top: 90px;
  left: -12px;
  background-color: white;
  padding: 5px 0px;
  border-radius: 20px;
  width: 120%;
  text-align: center;
  color: #FBBC7F;
}

::v-deep .n-split-pane-1{
  flex: 0 0 calc(20% + 0px);
}

::v-deep .n-progress.n-progress--line .n-progress-icon.n-progress-icon--as-text{
  font-size: 20px;
  width: 50px;
}

.bottom_left_img{
  width: 3.5rem;
  height: 3.5rem;
}
</style>
