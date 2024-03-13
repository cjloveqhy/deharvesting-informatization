import { http } from '@/utils/http/axios';
import {RequestEnum} from "@/enums/httpEnum";
import {CottonPickerFo, CottonPickerPageFo} from "@/store/api/farmCottonPicker";

const getUri = (uri: string) => {
  return `/farm-cotton-picker${uri}`
}

export const api = {
  brandOptions: getUri('/brandOptions'),
  filterPage: getUri('/filterPage'),
  add: getUri('/add'),
  del: getUri('/del'),
  update: getUri('/update'),
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
 * @description 分页过滤获取采棉机的信息
 */
export function getFilterPage(params: CottonPickerPageFo) {
  return http.request({
    url: api.filterPage,
    method: RequestEnum.GET,
    params
  }, {
    joinParamsToUrl: true
  })
}

/**
 * @description 新机入库（新增采棉机）
 */
export function addFarmCottonPicker(data: CottonPickerFo) {
  return http.request({
    url: api.add,
    method: RequestEnum.POST,
    data
  })
}

/**
 * @description 移除采棉机
 */
export function delFarmCottonPicker(id: string) {
  return http.request({
    url: api.del,
    method: RequestEnum.DELETE,
    params: {id: id}
  },{
    joinParamsToUrl: true
  })
}

/**
 * @description 更新采棉机设备信息
 */
export function updateFarmCottonPicker(data: CottonPickerFo) {
  return http.request({
    url: api.update,
    method: RequestEnum.PUT,
    data
  })
}

