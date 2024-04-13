import {CreateSSEOptions, SSEConfig, SSEOptions} from "@/utils/sse/types";
import { cloneDeep } from 'lodash-es';
import {SSETransform} from "@/utils/sse/SSETransform";
import {isFunction} from "@/utils/is";

/**
 * SSE 服务端推送 模块
 */
export class VSSE {

  private options: CreateSSEOptions;

  constructor(options: CreateSSEOptions) {
    this.options = options
    if (!options.hasOwnProperty('withCredentials')) {
      this.options.withCredentials = false
    }
  }

  /**
   * 拉取方法
   */
  pull(config: SSEConfig, options?: SSEOptions) {
    let conf: SSEConfig = cloneDeep(config)
    const transform = this.getTransform()

    const {requestOptions} = this.options

    const opt: SSEOptions = Object.assign({}, requestOptions, options)

    const { beforeRequestHook } = transform || {}

    if (beforeRequestHook && isFunction(beforeRequestHook)) {
      conf = beforeRequestHook(conf, opt)
    }

    const source = this.getSourceEvent(conf)

    // @ts-ignore
    source.addEventListener(conf.eventName || 'message', conf.listener, conf.eventOptions)

  }

  private getSourceEvent(conf: SSEConfig) {
    return new EventSource(conf.url as string, {
      withCredentials: this.options.withCredentials || false
    })
  }

  private getTransform(): SSETransform {
    const { transform } = this.options
    return transform || {}
  }

}
