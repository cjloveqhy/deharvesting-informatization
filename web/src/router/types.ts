import type { RouteRecordRaw, RouteMeta } from 'vue-router';
import { defineComponent } from 'vue';
import {MenuType} from "@/store/api/permission/types";

export type Component<T = any> =
  | ReturnType<typeof defineComponent>
  | (() => Promise<typeof import('*.vue')>)
  | (() => Promise<T>);

export interface AppRouteRecordRaw extends Omit<RouteRecordRaw, 'meta' | 'children'> {
  name: string;
  meta: RouteMeta;
  component?: Component | string;
  components?: Component;
  children?: AppRouteRecordRaw[];
  props?: Recordable;
  fullPath?: string;
}

export interface Meta {
  // 名称
  title: string;
  // 是否忽略权限
  ignoreAuth?: boolean;
  permissions?: string[];
  // 是否不缓存
  noKeepAlive?: boolean;
  // 是否固定在tab上
  affix?: boolean;
  // tab上的图标
  icon?: string;
  // 跳转地址
  frameSrc?: string;
  // 外链跳转地址
  externalLink?: string;
  //隐藏
  hidden?: boolean;
}

export interface Menu {
  title: string;
  label: string;
  key: string;
  meta: RouteMeta;
  name: string;
  component?: Component | string;
  components?: Component;
  children?: AppRouteRecordRaw[];
  props?: Recordable;
  fullPath?: string;
  icon?: any;
  path: string;
  permissions?: string[];
  redirect?: string;
  sort?: number;
}

export interface IModuleType {
  default: Array<RouteRecordRaw> | RouteRecordRaw;
}

export interface IPageModuleType {
  default: PageRole;
}

export interface RouterIconItem<K> {
  icon: Component
  name: K
}

export type RouterIcon = {
  [K in string]: RouterIconItem<K>
}

export interface RouteMeta {
  title: string | null
  disabled?: boolean
  icon?: string
  keepAlive?: boolean
  hidden?: boolean
  sort?: number
  alwaysShow?: boolean
  isRoot?: boolean
  frameSrc?: string
  permissions?: string[]
  affix?: boolean
}

export interface RouteRecord {
  path?: string
  name: string
  component?: string
  redirect?: string
  meta: RouteMeta
  children?: RouteRecord[]
}

export interface PageRoleAtom {
  name: string
  meta: {
    title: string
    type: MenuType
    sort?: number
    permissions?: string[]
  }
}

export interface PageRole {
  [key: string]: PageRoleAtom[]
}
