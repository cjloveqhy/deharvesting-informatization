export const workResultOption = {
  tooltip: {
    trigger: 'axis',
    axisPointer: {
      type: 'cross',
      crossStyle: {
        color: '#999'
      }
    }
  },
  xAxis: [
    {
      type: 'category',
      data: ['01', '02', '03', '04', '05', '06', '07', '08'],
      axisPointer: {
        type: 'shadow'
      }
    }
  ],
  yAxis: [
    {
      type: 'value',
      name: 'noPassNum',
      min: 0,
      max: 250,
      interval: 50,
      show: false,
    },
    {
      type: 'value',
      name: 'passRate',
      min: 0,
      max: 250,
      interval: 50,
      show: false,
    }
  ],
  grid: { // 让图表占满容器
    left: "0px",
    right: "0px",
  },
  dataset: {
    dimensions: ['days', 'noPassNum', 'passNum', 'passRate'],
    source: [
      { days: '01', passNum: 100, noPassNum: 130, passRate: 120 },
      { days: '02', passNum: 50, noPassNum: 50, passRate: 50 },
      { days: '03', passNum: 30, noPassNum: 30, passRate: 30 },
      { days: '04', passNum: 40, noPassNum: 40, passRate: 40 },
      { days: '05', passNum: 60, noPassNum: 60, passRate: 60 },
      { days: '06', passNum: 50, noPassNum: 50, passRate: 50 },
      { days: '07', passNum: 30, noPassNum: 30, passRate: 30 },
      { days: '08', passNum: 100, noPassNum: 100, passRate: 100 }
    ]
  },
  series: [
    {
      type: 'bar',
      itemStyle: {
        normal: {
          color: '#81D3F8'
        }
      }
    },
    {
      type: 'bar',
      itemStyle: {
        normal: {
          color: '#02A7F0'
        }
      }
    },
    {
      type: 'line',
      yAxisIndex: 1,
      itemStyle: {
        normal: {
          color: '#015478'
        }
      },
      showSymbol: false,
    },
  ]
}
