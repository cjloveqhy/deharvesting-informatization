import * as echarts from 'echarts';
import {EmptyProps, SpinProps} from "naive-ui";

export interface BasicEchartsProps {
  option: echarts.EChartsOption
  data: any[] | null
  loading?: boolean
  chartClass?: string
  dataField?: string
  spinProps?: Omit<SpinProps, 'show'>
  emptyProps?: EmptyProps
  theme?: string | object
  opts?: object
}
