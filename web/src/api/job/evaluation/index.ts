import {http} from '@/utils/http/axios';
import {RequestEnum} from "@/enums/httpEnum";
import {AddJobEvaluationFo, JobEvaluationPageFo} from "@/store/api/job/evaluation";

const getUri = (uri: string) => {
  return `/job-evaluation${uri}`
}

export const api = {
  filterPage: getUri('/filterPage'),
  getSelfFilterPage: getUri('/getSelfFilterPage'),
  getAddOption: getUri("/getAddOption"),
  addJobEvaluation: getUri("/add")
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

/**
 * @description 获取添加评价信息表单中的option
 */
export function getAddOptions() {
  return http.request({
    url: api.getAddOption,
    method: RequestEnum.GET,
  })
}

/**
 * 添加评价
 */
export function addJobEvaluationInfo(data: AddJobEvaluationFo){
  return http.request({
    url: api.addJobEvaluation,
    method: RequestEnum.POST,
    data
  })
}
