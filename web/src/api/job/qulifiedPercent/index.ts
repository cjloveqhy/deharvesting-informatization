import {http} from '@/utils/http/axios';
import {RequestEnum} from "@/enums/httpEnum";
import {JobQualifiedPercentPageFo} from "@/store/api/job/evaluation";

const getUri = (uri: string) => {
  return `/job-qualified-percent${uri}`
}

export const api = {
  page: getUri('/page'),
}

/**
 * @description 获取过滤分页数据
 */
export function getPage(params: JobQualifiedPercentPageFo) {
  return http.request({
    url: api.page,
    method: RequestEnum.GET,
    params
  },{
    joinParamsToUrl: true
  })
}
