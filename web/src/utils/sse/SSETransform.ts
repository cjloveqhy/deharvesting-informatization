import {SSEConfig, SSEOptions} from "@/utils/sse/types";

export abstract class SSETransform {

  /**
   * 拉取之前处理配置
   */
  beforeRequestHook?: (config: SSEConfig, options: SSEOptions) => SSEConfig

}
