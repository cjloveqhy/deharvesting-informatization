import {LogicalEnum} from "@/enums/LogicalEnum";
import {PageQuery} from "@/store/common";

export interface DataTerminalPageFo extends PageQuery {
  terminalId: string | null
  brand: string | null
  status: LogicalEnum | null
  cottonPickerRackId: string | null
  startTime: string | null
  endTime: string | null
}

export interface DataTerminalFo {
  id: string | null
  terminalId: string | null
  brand: string | null
  model: string | null
  simExpirationTime: Date | null
  status: LogicalEnum | null
  cottonPickerRackId: string | null
}

export interface DataTerminalVo extends DataTerminalFo {
  updateTime: Date | null
}
