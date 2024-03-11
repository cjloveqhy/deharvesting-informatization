import { http } from '@/utils/http/axios';
import {RequestEnum} from "@/enums/httpEnum";
import {FilterPageFo} from "@/store/api/harvestSchedule";

const getUri = (uri: string) => {
  return `/harvest-schedule${uri}`
}

export const api = {
  filterPage: getUri('/filterPage'),
  details: getUri('/details'),
}

/**
 * @description 获取过滤分页数据
 */
export function filterPage(params: FilterPageFo) {
  return http.request({
    url: api.filterPage,
    method: RequestEnum.GET,
    params
  },{
    joinParamsToUrl: true
  })
}

/**
 * @description 获取调度单详细信息
 */
export function getDetails(dispatchId: string) {
  return http.request({
    url: api.details,
    method: RequestEnum.GET,
    params: {dispatchId: dispatchId}
  })
}

