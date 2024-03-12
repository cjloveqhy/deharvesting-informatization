import {PageQuery} from "@/store/common";
import {UserInfo} from "@/store/api/user";

export enum HoldingAttrEnum {

  /**
   * 自有
   */
  SelfOwned = 'S',

  /**
   * 租赁
   */
  Lease = 'L'

}

export interface FarmUavPageFo extends PageQuery {
  rackNumber: string | null
  serviceProvider: string | null
  holdingAttr: HoldingAttrEnum | null
}

export interface FarmUavVo {
  id: string | null
  rackNumber: string | null
  serviceProvider: string | null
  belonger: UserInfo
  holdingAttr: HoldingAttrEnum | null
  workArea: number | null
  workTime: number | null
  sprayFlowRate: number | null
}
