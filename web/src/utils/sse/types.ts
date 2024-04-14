import {SSETransform} from "@/utils/sse/SSETransform";

export interface SSEConfig {
  // 监听事件名
  eventName?: string
  // 请求路径
  url?: string
  // 请求携带参数
  params?: any
  // 监听
  listener?: (message: MessageEvent, source: EventSource) => void
  // event 监听配置
  eventOptions?: boolean | AddEventListenerOptions
}

export interface CreateSSEOptions extends SSEConfig {
  transform?: SSETransform
  requestOptions?: SSEOptions
  withCredentials?: boolean
}

export interface SSEOptions {
  // 接口地址， 不填则使用默认apiUrl
  apiUrl?: string
  // 是否加入url
  joinPrefix?: boolean
  // 请求拼接路径
  urlPrefix?: string
  // 是否解析成JSON
  isParseToJson?: boolean
  // 是否添加时间戳
  joinTime?: boolean
  // 是否为restful风格
  restful?: boolean
}
