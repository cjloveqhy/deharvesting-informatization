import { http } from '@/utils/http/axios';
import {RequestEnum} from "@/enums/httpEnum";
import {DataTerminalPageFo, DataTerminalFo} from "@/store/api/farmDataTerminal";

const getUri = (uri: string) => {
  return `/farm-data-terminal${uri}`
}

export const api = {
  brandOptions: getUri('/brandOptions'),
  filterPage: getUri('/filterPage'),
  add: getUri('/add'),
  update: getUri('/update'),
  del: getUri('/del'),
}

/**
 * @description 获取所有品牌选项
 */
export function getBrandOptions() {
  return http.request({
    url: api.brandOptions,
    method: RequestEnum.GET,
  })
}

/**
 * @description 获取过滤分页的数据
 */
export function getFilterPage(params: DataTerminalPageFo) {
  return http.request({
    url: api.filterPage,
    method: RequestEnum.GET,
    params
  }, {
    joinParamsToUrl: true
  })
}

/**
 * @description 新机入库（添加数据终端）
 */
export function addFarmDataTerminal(data: DataTerminalFo) {
  return http.request({
    url: api.add,
    method: RequestEnum.POST,
    data
  })
}

/**
 * @description 修改数据终端信息
 */
export function updateFarmDataTerminal(data: DataTerminalFo) {
  return http.request({
    url: api.update,
    method: RequestEnum.PUT,
    data
  })
}

/**
 * @description 删除数据终端信息
 */
export function delFarmDataTerminal(id: string) {
  return http.request({
    url: api.del,
    method: RequestEnum.DELETE,
    params: {id: id}
  },{
    joinParamsToUrl: true
  })
}
