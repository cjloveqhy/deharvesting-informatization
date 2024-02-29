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

  legend: {
    data: ['Evaporation', 'Precipitation']
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
      name: 'Precipitation',
      min: 0,
      max: 250,
      interval: 50,
      show: false,
    },
    {
      type: 'value',
      name: 'Temperature',
      min: 0,
      max: 250,
      interval: 50,
      show: false,
    }
  ],
  grid:{ // 让图表占满容器
    left:"0px",
    right:"0px",
  },
  series: [
    {
      name: 'Evaporation',
      type: 'bar',
      data: [
        100, 50, 30, 40, 60, 50, 30, 100
      ],
      itemStyle: {
        normal:{
          color: '#81D3F8'
        }
      }
    },
    {
      name: 'Precipitation',
      type: 'bar',
      data: [
        130, 50, 30, 40, 60, 50, 30, 100
      ],
      itemStyle: {
        normal:{
          color: '#02A7F0'
        }
      }
    },
    {
      name: 'Temperature',
      type: 'line',
      yAxisIndex: 1,
      data: [120, 50, 30, 40, 60, 50, 30, 100],
      itemStyle: {
        normal:{
          color: '#015478'
        }
      },
      showSymbol: false,
    },
  ]
}
