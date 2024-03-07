import {MenuType} from "@/store/api/permission/types";

export const MenuOptions = [
  {label: '菜单', value: MenuType.Menu, type: 'success'},
  {label: '按钮', value: MenuType.Button, type: 'info'},
  {label: '内容', value: MenuType.Content, type: 'warning'},
]

export const CompOptions = [
  {label: 'LAYOUT', value: 'LAYOUT'},
  {label: 'IFRAME', value: 'IFRAME'}
]
