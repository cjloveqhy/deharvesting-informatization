import { http } from '@/utils/http/axios';
import {RequestEnum} from "@/enums/httpEnum";

const getUri = (uri: string) => {
  return `/cotton-field${uri}`
}

export const api = {
  cottons: getUri('/cottons'),
}

/**
 * @description 根据轧花厂Id编号获取地块信息
 */
export function getCottons(factoryId: string) {
  return http.request({
    url: api.cottons,
    method: RequestEnum.GET,
    params: {
      factoryId: factoryId
    }
  })
}

