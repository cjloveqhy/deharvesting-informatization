import {RouteMeta} from "@/router/types";

export enum MenuType {
  Root = 'R',
  Service = 'S',
  Menu = 'M',
  Button = 'B',
  Content = 'C'
}

export interface PermissionTree {
  id: string | null
  parentId: string | null
  name: string | null
  type: MenuType | null
  path: string | null
  component: string | null
  meta: RouteMeta
  children: PermissionTree[]
}

export interface UpdatePermissionFo extends Omit<PermissionTree, 'children'> {
}

export interface AddPermissionFo extends Omit<UpdatePermissionFo, 'id'> {
}
