import { http } from '@/utils/http/axios';
import {RequestEnum} from "@/enums/httpEnum";
import {FarmUavPageFo} from "@/store/api/farmUav";

const getUri = (uri: string) => {
  return `/farm-uav${uri}`
}

export const api = {
  filterPage: getUri('/filterPage'),
}

/**
 * @description 分页过滤获取无人机的信息
 */
export function getFilterPage(params: FarmUavPageFo) {
  return http.request({
    url: api.filterPage,
    method: RequestEnum.GET,
    params
  }, {
    joinParamsToUrl: true
  })
}

