import { http } from '@/utils/http/axios';
import {RequestEnum} from "@/enums/httpEnum";
import {RouteRecord} from "@/router/types";

const getUri = (uri: string) => {
  return `/menu${uri}`
}

export const api = {
  menus: getUri('/'),
  update: getUri('/update'),
}

/**
 * @description 根据用户id获取用户菜单
 */
export function userMenus() {
  return http.request({
    url: api.menus,
    method: RequestEnum.GET,
  }, {
    isReturnNativeResponse: false,
    isTransformResponse: true
  });
}

/**
 * @description 更新菜单
 */
export function updateMenus(data: RouteRecord[]) {
  return http.request({
    url: api.update,
    method: RequestEnum.PUT,
    data: data
  });
}
