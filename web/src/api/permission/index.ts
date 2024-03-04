import { http } from '@/utils/http/axios';
import {RequestEnum} from "@/enums/httpEnum";

const getUri = (uri: string) => {
  return `/permission${uri}`
}

export const api = {
  tree: getUri('/tree'),
}

/**
 * @description 根据用户id获取用户菜单
 */
export function getTree() {
  return http.request({
    url: api.tree,
    method: RequestEnum.GET,
  });
}
