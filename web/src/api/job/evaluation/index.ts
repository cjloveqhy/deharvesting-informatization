import {http} from '@/utils/http/axios';
import {RequestEnum} from "@/enums/httpEnum";
import {JobEvaluationPageFo} from "@/store/api/job/evaluation";

const getUri = (uri: string) => {
  return `/job-evaluation${uri}`
}

export const api = {
  filterPage: getUri('/filterPage'),
}

/**
 * @description 获取过滤分页数据
 */
export function filterPage(params: JobEvaluationPageFo) {
  return http.request({
    url: api.filterPage,
    method: RequestEnum.GET,
    params
  },{
    joinParamsToUrl: true
  })
}
