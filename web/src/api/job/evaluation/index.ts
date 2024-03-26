import {http} from '@/utils/http/axios';
import {RequestEnum} from "@/enums/httpEnum";
import {JobEvaluationPageFo} from "@/store/api/job/evaluation";

const getUri = (uri: string) => {
  return `/job-evaluation${uri}`
}

export const api = {
  filterPage: getUri('/filterPage'),
  getSelfFilterPage: getUri('/getSelfFilterPage')
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

/**
 * @description 获取过滤分页数据
 */
export function getSelfFilterPage(params: JobEvaluationPageFo) {
  return http.request({
    url: api.getSelfFilterPage,
    method: RequestEnum.GET,
    params
  },{
    joinParamsToUrl: true
  })
}
