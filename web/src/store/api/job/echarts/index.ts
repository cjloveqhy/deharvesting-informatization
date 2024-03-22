export const monthHomeWorkResultOption = {
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
    dimensions: ['days', 'passNum', 'noPassNum', 'passRate'],
    source: []
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

export const rankingPassRatesOption = {
  xAxis: {
    type: 'category',
  },
  yAxis: {
    type: 'value',
    show: false
  },
  dataset: {
    source: []
  },
  series: [
    {
      type: 'bar',
      itemStyle: {
        normal: {
          color: function (params) {
            let colorList = [
              '#43A1CA',
              '#0697D5',
              '#9EC4D4'
            ]
            return colorList[params.dataIndex]
          }
        }
      }
    },
  ]
}

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
    source: []
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
