import * as echarts from 'echarts';

export interface BasicEchartsProps {
  option: echarts.EChartsOption
  data: any[]
  dataField?: string
  theme?: string | object
  opts?: object
}
