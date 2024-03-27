import { http } from '@/utils/http/axios';
import {RequestEnum} from "@/enums/httpEnum";
import {
  AddHarvestScheduleFo, DeleteHarvestScheduleRecordCottonFieldFo,
  FilterPageFo,
  HarvestScheduleRecordPageFo
} from "@/store/api/harvestSchedule";

const getUri = (uri: string) => {
  return `/harvest-schedule${uri}`
}

export const api = {
  filterPage: getUri('/filterPage'),
  getRecord: getUri("/get"),
  addRecord: getUri("/add"),
  details: getUri('/details'),
  delCottonField: getUri("/delCottonField"),
  createRecord: getUri("/create"),
  checkOrder: getUri("/checkOrder")
}

/**
 * @description 获取过滤分页数据
 */
export function filterPage(params: FilterPageFo) {
  return http.request({
    url: api.filterPage,
    method: RequestEnum.GET,
    params
  },{
    joinParamsToUrl: true
  })
}

/**
 * @description 获取调度单详细信息
 */
export function getDetails(dispatchId: string) {
  return http.request({
    url: api.details,
    method: RequestEnum.GET,
    params: {dispatchId: dispatchId}
  })
}

/**
 * 获取调度单信息
 * @param params
 */
export function getRecord(params: HarvestScheduleRecordPageFo){
  return http.request({
    url: api.getRecord,
    method: RequestEnum.GET,
    params
  })
}

/**
 * 添加调度单记录
 * @param data
 */
export function addRecord(data: AddHarvestScheduleFo){
  return http.request({
    url: api.addRecord,
    method: RequestEnum.POST,
    data
  })
}

/**
 * 删除调度单内的棉地信息
 * @param data
 */
export function delCottonField(data: DeleteHarvestScheduleRecordCottonFieldFo) {
  return http.request({
    url: api.delCottonField,
    method: RequestEnum.DELETE,
    data
  })
}

/**
 * 生成调度单
 * @param dispatchId
 */
export function createRecord(dispatchId: string){
  return http.request({
    url: api.createRecord,
    method: RequestEnum.PUT,
    data: {
      dispatchId
    }
  })
}

export function checkOrderInfo(params: FilterPageFo){
  return http.request({
    url: api.checkOrder,
    method: RequestEnum.GET,
    params
  })
}
