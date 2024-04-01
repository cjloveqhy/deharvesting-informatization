import {PageQuery} from "@/store/common";
import {UserInfo} from "@/store/api/user";

export interface UserRolePage extends PageQuery {

}

export interface AddUserRoleFo extends Omit<UserInfo, 'id' | 'createTime'> {
  roleId: string | null
  attachedPermission: string[]
}

export interface UpdateUserRoleFo extends AddUserRoleFo {
  id: string | null
}
