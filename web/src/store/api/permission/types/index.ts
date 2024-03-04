import {LogicalEnum} from "@/enums/LogicalEnum";
import {RouteMeta} from "@/router/types";

export enum MenuType {
  Root = 'R',
  Service = 'S',
  Menu = 'M',
  Button = 'B',
  Content = 'C'
}

export interface PermissionTree {
  id: string
  parentId: string
  name: string
  type: MenuType
  path: string
  component: string
  status: LogicalEnum
  meta: RouteMeta
  children: PermissionTree[]
}
