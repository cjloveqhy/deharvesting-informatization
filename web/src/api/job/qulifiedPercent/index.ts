import {http} from '@/utils/http/axios';
import {RequestEnum} from "@/enums/httpEnum";
import {JobQualifiedPercentPageFo, JobType} from "@/store/api/job/evaluation";

const getUri = (uri: string) => {
  return `/job-qualified-percent${uri}`
}

export const api = {
  page: getUri('/page'),
  passRateRanking: getUri("/passRateRanking")
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

/**
 * 获取无人机合格率排行TOP3的接口
 * @param type
 */
export function getUavPassRateRanking(type: JobType){
  return http.request({
    url: api.passRateRanking,
    method: RequestEnum.GET,
    params: {
      type: type
    }
  })
}
