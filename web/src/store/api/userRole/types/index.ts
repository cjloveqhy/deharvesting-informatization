import {PageQuery} from "@/store/common";
import {SexEnum, UserInfo} from "@/store/api/user";
import {LogicalEnum} from "@/enums/LogicalEnum";

export interface UserRolePage extends PageQuery {
  account: string | null
  username: string | null
  sex: SexEnum | null
  phone: string | null
  email: string | null
  roleId: string | null
  status: LogicalEnum | null
}

export interface AddUserRoleFo extends Omit<UserInfo, 'id' | 'createTime'> {
  roleId: string | null
  attachedPermission: string[]
}

export interface UpdateUserRoleFo extends AddUserRoleFo {
  id: string | null
}
