<script setup lang="ts">
import * as echarts from 'echarts';
import {onMounted} from 'vue/dist/vue.esm-bundler.js';

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
      boundaryGap : [ 0.2, 0.2 ]
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
  <div class="homePageBox">
    <!--首页头部-->
    <n-card title="今日作业概况" size="small">
      <hr>
      <div class="header_content">
        <div class="header_content header_border header_left">
          <img src="../../assets/images/homePage/u460.png" class="header_left_img">
          <div class="header_text_box">
            <span class="header_text_num">35,432&ensp;</span>
            <span>亩</span>
            <p>作业面积</p>
          </div>
          <n-divider vertical/>
          <div class="header_text_box">
            <span class="header_text_num">28&ensp;</span>
            <span>台</span>
            <p>累计运行</p>
          </div>
          <n-divider vertical/>
          <div class="header_text_box">
            <span class="header_text_num">9.25&ensp;</span>
            <span>小时</span>
            <p>平均工作时长</p>
          </div>
        </div>
        <div class="header_content header_border header_right">
          <img src="../../assets/images/homePage/u462.png" class="header_right_img">
          <div class="header_text_box">
            <span class="header_text_num">35,432&ensp;</span>
            <span>亩</span>
            <p>作业面积</p>
          </div>
          <n-divider vertical/>
          <div class="header_text_box">
            <span class="header_text_num">28&ensp;</span>
            <span>台</span>
            <p>累计运行</p>
          </div>
          <n-divider vertical/>
          <div class="header_text_box">
            <span class="header_text_num">9.25&ensp;</span>
            <span>小时</span>
            <p>平均工作时长</p>
          </div>
        </div>
      </div>
    </n-card>
    <br>
    <!--首页中间-->
    <main class="main_content">
      <n-card title="设备监控" size="small" style="margin-right: 20px;width: 60%;height: 300px">
        <hr>
        <n-grid :x-gap="1" :y-gap="2" :cols="3">
          <n-grid-item span="1">
            <n-grid :x-gap="2" :y-gap="2" :cols="2">
              <n-grid-item span="2">
                <div class="main_left" style="background-color: #EBF4FF">
                  <div class="main_left_img">
                    <img src="../../assets/images/homePage/u414.png">
                  </div>
                  <div class="main_left_text">
                    <p>无人机总数</p>
                    <span class="main_left_num">45</span>
                    <span>台</span>
                  </div>
                </div>
              </n-grid-item>
              <n-grid-item span="2">
                <div class="main_left" style="background-color: #FCF5E3;">
                  <div class="main_left_img">
                    <img src="../../assets/images/homePage/u415.png">
                  </div>
                  <div class="main_left_text">
                    <p>无人机总数</p>
                    <span class="main_left_num" style="color: #FBBC7F">45</span>
                    <span>台</span>
                  </div>
                </div>
              </n-grid-item>
            </n-grid>
          </n-grid-item>
          <n-grid-item span="2">
            <n-grid :x-gap="2" :y-gap="2" :cols="2">
              <n-grid-item class="main_left_progress_box">
                <img src="../../assets/images/homePage/u422.png" class="main_left_progress_topImg">
                <img src="../../assets/images/homePage/u422.png"
                     class="main_left_progress_bottomImg">
                <n-space class="main_left_progress">
                  <n-progress type="circle" :percentage="20" unit="台" color="#81CEF9"
                              border-radius="30" indicator-text-color="#fff" :stroke-width="10"
                              rail-color="#fff"/>
                  <div class="main_left_progress_text">当前无人机在线总数</div>
                </n-space>
              </n-grid-item>
              <n-grid-item class="main_left_progress_box" style="background-color: #F7E9D2">
                <img src="../../assets/images/homePage/u432.png" class="main_left_progress_topImg">
                <img src="../../assets/images/homePage/u432.png"
                     class="main_left_progress_bottomImg">
                <n-space class="main_left_progress">
                  <n-progress type="circle" :percentage="26" unit="台" color="#FBBC7F"
                              border-radius="30" indicator-text-color="#fff" :stroke-width="10"
                              rail-color="#fff"/>
                  <div class="main_left_progress_text" style="color:#FBBC7F;">当前采棉机在线总数</div>
                </n-space>
              </n-grid-item>
            </n-grid>
          </n-grid-item>
        </n-grid>
      </n-card>
      <n-card title="评价统计" size="small" style="width: 40%;height: 300px">
        <template #header-extra>
          <span>时间选择</span>
          <n-date-picker v-model:value="range" type="daterange" clearable style="margin: 0 5px"/>
          <n-button strong secondary type="info">查询</n-button>
        </template>
        <hr>
        <div class="main_right">
          <div class="main_right_progress">
            <n-progress type="circle" :percentage="80" :offset-degree="120" color="#B4DEF6"
                        indicator-text-color="#81CEF9" rail-color="#fff"/>
            <div class="main_right_progress_text">平均合格率</div>
          </div>
          <div class="main_right_category" id="main_right_category"
               style="width: 100%;height: 300px;margin-top: -50px"></div>
        </div>
      </n-card>
    </main>
    <br>
    <!--首页尾部-->
    <footer class="footer_content">
      <n-card title="无人机作业信息" size="small" style="margin-right: 20px;width: 35%;height: 300px">
        <hr>
        <n-grid :x-gap="2" :y-gap="2" :cols="2">
          <n-grid-item>
            <div class="footer_left_item">
              <div class="footer_left_item_img">
                <img src="../../assets/images/homePage/u495.png">
              </div>
              <div class="footer_left_item_text">
                <div>飞行总时长(小时)</div>
                <div class="footer_left_item_num">40.25</div>
              </div>
            </div>
          </n-grid-item>
          <n-grid-item>
            <div class="footer_left_item">
              <div class="footer_left_item_img">
                <img src="../../assets/images/homePage/u501.png">
              </div>
              <div class="footer_left_item_text">
                <div>作业总面积(亩)</div>
                <div class="footer_left_item_num">1817.05</div>
              </div>
            </div>
          </n-grid-item>
          <n-grid-item>
            <div class="footer_left_item">
              <div class="footer_left_item_img">
                <img src="../../assets/images/homePage/u498.png">
              </div>
              <div class="footer_left_item_text">
                <div>飞行总距离(公里)</div>
                <div class="footer_left_item_num">380.88</div>
              </div>
            </div>
          </n-grid-item>
          <n-grid-item>
            <div class="footer_left_item">
              <div class="footer_left_item_img">
                <img src="../../assets/images/homePage/u504.png">
              </div>
              <div class="footer_left_item_text">
                <div>总喷洒量(升)</div>
                <div class="footer_left_item_num">821.07</div>
              </div>
            </div>
          </n-grid-item>
        </n-grid>
      </n-card>
      <n-card title="采棉机作业信息" size="small" style="margin-right: 20px;width: 35%;height: 300px">
        <hr>
        <n-grid :x-gap="2" :y-gap="2" :cols="2">
          <n-grid-item>
            <div class="footer_center_item">
              <div class="footer_center_item_img">
                <img src="../../assets/images/homePage/u507.png">
              </div>
              <div class="footer_center_item_text">
                <div>运行总时长(小时)</div>
                <div class="footer_center_item_num">821.07</div>
              </div>
            </div>
          </n-grid-item>
          <n-grid-item>
            <div class="footer_center_item">
              <div class="footer_center_item_img">
                <img src="../../assets/images/homePage/u513.png">
              </div>
              <div class="footer_center_item_text">
                <div>作业总面积(亩)</div>
                <div class="footer_center_item_num">821.07</div>
              </div>
            </div>
          </n-grid-item>
          <n-grid-item>
            <div class="footer_center_item">
            <div class="footer_center_item_img">
              <img src="../../assets/images/homePage/u510.png">
            </div>
            <div class="footer_center_item_text">
              <div>总行驶里程(公里)</div>
              <div class="footer_center_item_num">821.07</div>
            </div>
          </div>
          </n-grid-item>
        </n-grid>
      </n-card>
      <n-card title="作物监控" size="small" style="width: 40%;height: 300px">
        <hr>
        <div class="footer_right_progress">
          <img src="../../assets/images/homePage/u516.png" class="footer_right_progress_img">
          <span class="footer_right_progress_title">脱叶率</span>
          <n-progress
            type="line"
            :show-indicator="false"
            status="success"
            :percentage="68.03"
            style="width: 50%"
            :stroke-width="20"
            :height="20"
            color="#81CEF9"
          />
          <span class="footer_right_progress_num">68.03</span>
          <span>%</span>
        </div>
        <div class="footer_right_progress">
          <img src="../../assets/images/homePage/u516.png" class="footer_right_progress_img">
          <span class="footer_right_progress_title">吐絮率</span>
          <n-progress
            type="line"
            :show-indicator="false"
            status="success"
            :percentage="50.83"
            style="width: 50%"
            :stroke-width="20"
            :height="20"
            color="#81CEF9"
          />
          <span class="footer_right_progress_num">50.83</span>
          <span>%</span>
        </div>
        <div class="footer_right_progress">
          <img src="../../assets/images/homePage/u516.png" class="footer_right_progress_img">
          <span class="footer_right_progress_title">除净率</span>
          <n-progress
            type="line"
            :show-indicator="false"
            status="success"
            :percentage="88"
            style="width: 50%"
            :stroke-width="20"
            :height="20"
            color="#81CEF9"
          />
          <span class="footer_right_progress_num">较好</span>
          <span>%</span>
        </div>
      </n-card>
    </footer>

  </div>
</template>
<style lang="less" scoped>
.homePageBox {
  margin-left: 20px;

  .header_content {
    margin-top: 10px;
    display: flex;

    .header_left {
      margin-left: 40px;
      border-color: rgba(129, 211, 248, 1);
      min-width: 745px;

      .n-divider.n-divider--vertical {
        height: 4em;
        background-color: #81D3F8FF;
        transform: translate(50px, 0px);
      }

      span {
        color: #81D3F8FF;
      }

      .header_left_img {
        position: absolute;
        left: 35px;
        width: 250px;
        height: 141px;
        background-color: white;
        transform: translate(-75px, 0px);
      }
    }

    .header_right {
      border-color: rgba(250, 205, 145, 1);
      margin-left: 40px;
      min-width: 745px;

      .n-divider.n-divider--vertical {
        height: 4em;
        background-color: rgba(250, 205, 145, 1);
        transform: translate(50px, 0px);
      }

      span {
        color: rgba(250, 205, 145, 1);
      }

      .header_right_img {
        position: absolute;
        left: 10px;
        width: 250px;
        height: 141px;
        background-color: white;
        transform: translate(-75px, 0px);
      }
    }
  }

  .main_content {
    .main_left {
      width: 80%;
      display: flex;
      padding: 20px 20px 0px 20px;
      height: 90px;
      background: inherit;
      background-color: rgba(235, 244, 255, 1);
      border-radius: 8px;
      -moz-box-shadow: 5px 5px 5px rgba(0, 0, 0, 0.349019607843137);
      -webkit-box-shadow: 5px 5px 5px rgb(0 0 0 / 35%);
      box-shadow: 5px 5px 5px rgb(0 0 0 / 35%);
      margin-top: 20px;
      margin-right: 20px;
    }

    .main_right {
      display: flex;
      margin: 20px;

      .main_right_progress {
        .main_right_progress_text {
          text-align: center;
          margin-top: 20px;
        }
      }

      .main_right_category {
        margin-left: 50px;
      }
    }
  }

  .footer_content{
    .footer_left_item{
      display: flex;
      .footer_left_item_img{
        min-width: 80px;
        height: 80px;
        border-radius: 19px;
        background-color: rgba(242, 242, 242, 1);
        margin: 25px 10px 10px;

        img{
          width: 70px;
          margin: 5px;
        }
      }
      .footer_left_item_text{
        margin-top: 35px;
        min-width: 108px;
        .footer_left_item_num{
          height: 32px;
          background: inherit;
          font-family: 'Arial Negreta', 'Arial Normal', 'Arial';
          font-size: 28px;
          color: #81CEF9;
        }
      }
    }
    .footer_center_item{
      display: flex;
      .footer_center_item_img{
        min-width: 80px;
        height: 80px;
        border-radius: 19px;
        background-color: rgba(242, 242, 242, 1);
        margin: 25px 10px 10px;

        img{
          width: 70px;
          margin: 5px;
        }
      }
      .footer_center_item_text{
        margin-top: 35px;
        min-width: 108px;
        .footer_center_item_num{
          height: 32px;
          background: inherit;
          font-family: 'Arial Negreta', 'Arial Normal', 'Arial';
          font-size: 28px;
          color: #FACD91;
        }
      }
    }
    .footer_right_progress{
      display: flex;
      margin: 28px 20px 20px;

      span{
        margin: 0px 10px;
      }

      .footer_right_progress_img{
        width: 40px;
        height: 40px;
        transform: translateY(-5px);
      }
      .footer_right_progress_title{
        min-width: 48px;
      }
      .footer_right_progress_num{
        min-width: 71px;
        background-color: rgba(255, 255, 255, 0);
        font-family: 'Arial Negreta', 'Arial Normal', 'Arial';
        font-weight: 700;
        font-size: 28px;
        color: #81CEF9;
        transform: translate(5px, -10px);
        text-align: center;
      }
    }
  }
}

main, footer {
  display: flex;
}

.n-card__content {
  border-width: 0px;
  width: 100%;
  height: 300px;
  margin-left: 20px;
  padding: 0px;
}

.header_border {
  position: relative;
  width: 50%;
  height: 200px;
  background: inherit;
  background-color: rgba(255, 255, 255, 1);
  border-width: 1px;
  border-style: solid;
  border-radius: 5px;
  box-shadow: 5px 5px 5px rgb(0 0 0 / 35%);
  margin-right: 40px;
  display: flex;
  justify-content: center;
  align-items: center;
  text-align: center;
}

.header_text_box {
  margin: 0px 10px 0px 10px;
  transform: translate(50px, 0px);
  min-width: 100px;
  min-height: 65px;
}

.header_text_num {
  font-size: 20px;
  font-weight: bold;
  display: inline-block;
  margin-bottom: 10px;
}

.main_left_img {
  img {
    width: 60px;
  }
}

.main_left_text {
  margin: 0px 10px 10px 20px;
  min-width: 100px;
}

.main_left_num {
  font-weight: 700;
  font-style: normal;
  font-size: 28px;
  color: #7BCCF9;
  margin-left: 12px;
  margin-right: 10px;
}

.main_left_progress_box {
  height: 200px;
  background: inherit;
  background-color: rgba(198, 233, 247, 1);
  border-radius: 8px;
  -moz-box-shadow: 5px 5px 5px rgba(0, 0, 0, 0.349019607843137);
  -webkit-box-shadow: 5px 5px 5px rgb(0 0 0 / 35%);
  box-shadow: 5px 5px 5px rgb(0 0 0 / 35%);
  margin: 20px;
  display: flex;
  justify-content: center;
  align-items: center;
  position: relative;

  .main_left_progress_topImg {
    position: absolute;
    border-width: 0px;
    left: 0px;
    top: 0px;
    width: 100px;
    height: 100px;
    opacity: 0.2;
  }

  .main_left_progress_bottomImg {
    position: absolute;
    border-width: 0px;
    bottom: 0px;
    right: 0px;
    width: 50px;
    height: 50px;
    opacity: 0.2;
  }

  .main_left_progress {
    position: relative;
  }

  .main_left_progress_text {
    position: absolute;
    align-self: center;
    padding: 8px 8px 8px 8px;
    box-sizing: border-box;
    width: 100%;
    position: absolute;
    left: -50px;
    top: 100px;
    width: 214px;
    height: 37px;
    background: inherit;
    background-color: rgba(255, 255, 255, 1);
    border-radius: 59px;
    font-size: 16px;
    color: #81CEF9;
    text-align: center;
  }
}
.light-green {
  height: 108px;
  background-color: rgba(0, 128, 0, 0.12);
}
.green {
  height: 108px;
  background-color: rgba(0, 128, 0, 0.24);
}
</style>
