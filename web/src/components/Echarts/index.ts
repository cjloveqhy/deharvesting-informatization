import * as echarts from 'echarts';

export interface BasicEchartsProps {
  option: echarts.EChartsOption
  data: any[]
  chartClass?: string
  dataField?: string
  theme?: string | object
  opts?: object
}
