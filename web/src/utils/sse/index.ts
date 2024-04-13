import {CreateSSEOptions} from "@/utils/sse/types";
import {VSSE} from "@/utils/sse/SSE";
import {deepMerge, isUrl} from "@/utils";
import {useGlobSetting} from "@/hooks/setting";
import {SSETransform} from "@/utils/sse/SSETransform";
import {isString} from "@/utils/is";
import {setObjToUrlParams} from "@/utils/urlUtils";
import {joinTimestamp} from "@/utils/http/axios/helper";

const globSetting = useGlobSetting();
const urlPrefix = globSetting.urlPrefix || '';

/**
 * 拉取前进行处理
 */
const transform: SSETransform = {

  /**
   * 请求之前处理config
   */
  beforeRequestHook: (config, options) => {
    const { apiUrl, joinPrefix, urlPrefix, joinTime, restful } = options

    const isUrlStr = isUrl(config.url as string);

    if (!isUrlStr && joinPrefix) {
      config.url = `${urlPrefix}${config.url}`
    }

    if (!isUrlStr && apiUrl && isString(apiUrl)) {
      config.url = `${apiUrl}${config.url}`
    }

    const params = Object.assign(config.params || {}, joinTimestamp(joinTime as boolean, restful as boolean))

    config.url = setObjToUrlParams(config.url as string, params)

    return config

  }

}

function createSSE(opt?: Partial<CreateSSEOptions>) {
  return new VSSE(
    deepMerge(
      {
        transform,
        requestOptions: {
          // 接口地址， 不填则使用默认apiUrl
          apiUrl: globSetting.apiUrl,
          // 是否加入url
          joinPrefix: true,
          // 请求拼接路径
          urlPrefix: urlPrefix,
          // 是否解析成JSON
          isParseToJson: true,
          // 是否添加时间戳
          joinTime: true,
          // 是否为restful风格
          restful: false,
        },
        withCredentials: false
      }, opt || {}
    )
  )
}



export const sse = createSSE()
