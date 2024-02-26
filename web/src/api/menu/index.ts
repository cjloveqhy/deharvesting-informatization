import { http } from '@/utils/http/axios';
import {RequestEnum} from "@/enums/httpEnum";

const getUri = (uri: string) => {
  return `/menu${uri}`
}

export const api = {
  menus: getUri('/'),
}

/**
 * @description 根据用户id获取用户菜单
 */
export function userMenus() {
  return http.request({
    url: api.menus,
    method: RequestEnum.GET,
  });
}
