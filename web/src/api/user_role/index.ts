import { http } from '@/utils/http/axios';
import {RequestEnum} from "@/enums/httpEnum";

const getUri = (uri: string) => {
  return `/user-role${uri}`
}

export const api = {
  rolePermissions: getUri('/rolePermissions'),
  page: getUri('/page'),
  add: getUri('/add'),
  update: getUri('/update'),
  del: getUri('/del'),
  roles: getUri("/roles")
}

/**
 * @description 根据用户id获取用户菜单
 */
export function getUserPermissions() {
  return http.request({
    url: api.rolePermissions,
    method: RequestEnum.GET,
  }, {
    isReturnNativeResponse: false,
    isTransformResponse: true
  });
}

/**
 * @description 根据用户id获取用户角色
 */
export function getUserRoles() {
  return http.request({
    url: api.roles,
    method: RequestEnum.GET,
  }, {
    isReturnNativeResponse: false,
    isTransformResponse: true
  });
}

