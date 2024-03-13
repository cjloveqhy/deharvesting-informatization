import {PageQuery} from "@/store/common";
import {HoldingAttrEnum} from "@/store/api/farmUav";
import {LogicalEnum} from "@/enums/LogicalEnum";

export interface CottonPickerPageFo extends PageQuery {
  brand: string | null
  belonger: string | null
  holdingAttr: HoldingAttrEnum | null
  status: LogicalEnum | null
  startTime: string | null
  endTime: string | null
}

export interface CottonPickerVo {
  id: string | null
  rackNumber: string | null
  brand: string | null
  belonger: string | null
  phone: string | null
  holdingAttr: HoldingAttrEnum | null
  affiliatedCompany: string | null
  createTime: Date | null
  status: LogicalEnum | null
}

export interface CottonPickerFo {
  id?: string | null
  rackNumber: string | null
  brand: string | null
  belonger: string | null
  holdingAttr: HoldingAttrEnum | null
  affiliatedCompany?: string | null
}
