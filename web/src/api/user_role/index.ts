import {http} from '@/utils/http/axios';
import {RequestEnum} from "@/enums/httpEnum";
import {AddUserRoleFo, UpdateUserRoleFo, UserRolePage} from "@/store/api/userRole";

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
  })
}

/**
 * @description 分页获取用户角色权限
 */
export function filterPage(params: UserRolePage) {
  return http.request({
    url: api.page,
    method: RequestEnum.GET,
    params
  })
}

/**
 * 修改用户角色权限
 */
export function update(data: UpdateUserRoleFo) {
  return http.request({
    url: api.update,
    method: RequestEnum.PUT,
    data
  })
}

/**
 * 添加用户角色权限
 */
export function add(data: AddUserRoleFo) {
  return http.request({
    url: api.add,
    method: RequestEnum.POST,
    data
  })
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
  })
}

