import { http } from '@/utils/http/axios';
import {RequestEnum} from "@/enums/httpEnum";
import {GinneryPageFo} from "@/store/api/ginnery";

const getUri = (uri: string) => {
  return `/ginnery${uri}`
}

export const api = {
  search: getUri('/search'),
}

/**
 * @description 根据用户id获取用户菜单
 */
export function ginnerySearch(params: GinneryPageFo) {
  return http.request({
    url: api.search,
    method: RequestEnum.GET,
    params
  },{
    joinParamsToUrl: true
  });
}

