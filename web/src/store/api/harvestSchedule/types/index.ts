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

export interface HarvestScheduleRecordPageFo extends PageQuery{
  ginneryId: string
}

export interface AddHarvestScheduleFo {
  ginneryId:string,
  cottonFieldId: string
}

export interface HarvestScheduleRecordPageVo extends GinneryVo{
   dispatchId: string;
   ginneryList: Array<GinneryVo>;
  createTime: Date | null

}

export interface HarvestScheduleDetailsVo extends GinneryVo {
  dispatchId: string | null
  dispatchArea: number | null
  createTime: Date | null
}

export interface DeleteHarvestScheduleRecordCottonFieldFo {
  dispatchId:string,
  cottonFieldId: string
}
