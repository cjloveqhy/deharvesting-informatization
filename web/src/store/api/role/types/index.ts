import {PageQuery} from "@/store/common";
import {LogicalEnum} from "@/enums/LogicalEnum";

export interface RolePage extends PageQuery {
  name: string | null
  value: string | null
  status: LogicalEnum | null
  disabled: LogicalEnum | null
}

export interface AddRole {
  name: string | null
  level: number
  remark: string | null
  value: string | null
  status: LogicalEnum
  disabled: LogicalEnum
  permissions: string[]
}

export interface UpdateRole extends AddRole {
  id: string | null
}

export interface RoleVo extends UpdateRole {
}
