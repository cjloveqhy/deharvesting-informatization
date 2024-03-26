import { http } from '@/utils/http/axios';
import {RequestEnum} from "@/enums/httpEnum";
import {GetHsIdUavHarvestScheduleFo} from "@/store/api/uavHarvestSchedule/types";

const getUri = (uri: string) => {
  return `/uav-harvest-schedule${uri}`
}

export const api = {
  get: getUri('/get'),
}

/**
 * @description: 根据用户id和棉地id查询调度单id
 */
export function getDispatchIds(params: GetHsIdUavHarvestScheduleFo) {
  return http.request({
      url: api.get,
      method: RequestEnum.GET,
      params,
    }
  )
}

