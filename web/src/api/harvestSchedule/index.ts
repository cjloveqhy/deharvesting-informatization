import { http } from '@/utils/http/axios';
import {RequestEnum} from "@/enums/httpEnum";
import {FilterPageFo} from "@/store/api/harvestSchedule";

const getUri = (uri: string) => {
  return `/harvest-schedule${uri}`
}

export const api = {
  filterPage: getUri('/filterPage'),
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
  });
}

