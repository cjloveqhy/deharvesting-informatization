import {UserInfo} from "@/store/api/user";
import {GinneryBasicVo} from "@/store/api/ginnery";

export interface CottonFieldVo {
  id: string | null
  plotName: string | null
  ginnery: GinneryBasicVo | null
  contacts: UserInfo | null
  cultivatedArea: number | null
  cottonVariety: string | null
  addr: string | null
  createTime: Date | null
}
