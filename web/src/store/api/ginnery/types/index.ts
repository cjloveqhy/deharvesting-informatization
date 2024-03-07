import {UserInfo} from "@/store/api/user";
import {CottonFieldVo} from "@/store/api/cottonField";
import {PageQuery} from "@/store/common";

export interface GinneryBasicVo {
  id: string | null
  contacts: UserInfo | null
  factoryName: string | null
  addr: string | null
  workingAbility: number | null
}

export interface GinneryVo extends GinneryBasicVo {
  children: CottonFieldVo[]
}

export interface GinneryPageFo extends PageQuery {
  factoryName: string
}
