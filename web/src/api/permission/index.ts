import { http } from '@/utils/http/axios';
import {RequestEnum} from "@/enums/httpEnum";
import {AddPermissionFo, UpdatePermissionFo} from "@/store/api/permission/types";

const getUri = (uri: string) => {
  return `/permission${uri}`
}

export const api = {
  tree: getUri('/tree'),
  update: getUri('/update'),
  add: getUri('/add'),
  del: getUri('/del'),
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

/**
 * @description 更新菜单节点信息
 */
export function updateMenuNode(data: UpdatePermissionFo) {
  return http.request({
    url: api.update,
    method: RequestEnum.PUT,
    data
  });
}

/**
 * @description 新增菜单节点信息
 */
export function addMenuNode(data: AddPermissionFo) {
  return http.request({
    url: api.add,
    method: RequestEnum.POST,
    data
  });
}

/**
 * @description 新增菜单节点信息
 */
export function delMenuNode(id: string) {
  return http.request({
    url: api.del,
    method: RequestEnum.DELETE,
    params: {id: id}
  },{
    joinParamsToUrl: true
  });
}

