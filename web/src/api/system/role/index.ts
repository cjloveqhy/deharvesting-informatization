import {http} from '@/utils/http/axios';
import {RequestEnum} from "@/enums/httpEnum";
import {AddRole, RolePage, UpdateRole} from "@/store/api/role/types";

const getUri = (uri: string) => {
  return `/role${uri}`
}

export const api = {
  page: getUri('/page'),
  add: getUri('/add'),
  update: getUri('/update'),
  del: getUri('/del'),
  options: getUri('/options'),
}

/**
 * @description 分页获取角色列表
 */
export function getRolePage(params: RolePage) {
  return http.request({
    url: api.page,
    method: RequestEnum.GET,
    params
  })
}

/**
 * 添加角色
 */
export function addRole(data: AddRole) {
  return http.request({
    url: api.add,
    method: RequestEnum.POST,
    data
  })
}

/**
 * 更新角色信息
 */
export function updateRole(data: UpdateRole) {
  return http.request({
    url: api.update,
    method: RequestEnum.PUT,
    data
  })
}

/**
 * 删除角色
 */
export function delRole(id: string) {
  return http.request({
    url: api.del,
    method: RequestEnum.DELETE,
    params: {
      id: id
    }
  }, {
    joinParamsToUrl: true
  })
}

/**
 * 获取角色的可操作选项
 */
export function getOptions() {
  return http.request({
    url: api.options,
    method: RequestEnum.GET,
  })
}
