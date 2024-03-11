import {PageQuery} from "@/store/common";
import {GinneryVo} from "@/store/api/ginnery";

export interface FilterPageFo extends PageQuery {
  ginneryName?: string | null
  contacts?: string | null
  startTime?: string | null
  endTime?: string | null
}

export interface HarvestScheduleVo {
  dispatchId: string
  factoryName: string
  addr: string
  username: string
  phone: string
  dispatchArea: number
  createTime: Date
}

export interface HarvestScheduleDetailsVo extends GinneryVo {
  dispatchId: string | null
  dispatchArea: number | null
  createTime: Date | null
}
