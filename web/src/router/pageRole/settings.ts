import {PageRole} from "@/router/types";
import {MenuType} from "@/store/api/permission";

/**
 * key为明确的菜单名称，及 RouteRecordRaw 对象的name值，取自modules下指定路由的name,
 * 属性值为数组，标识为该菜单下有哪些page页上的权限可用
 */
const roles: PageRole = {
  settings_user: [
    {
      name: 'view',
      meta: {
        title: '查看',
        type: MenuType.Content,
        permissions: ['settings::user::view']
      }
    },
  ],
  settings_role: [
    {
      name: 'view',
      meta: {
        title: '查看',
        type: MenuType.Content,
        permissions: ['settings::role::view']
      }
    },
  ],
  settings_menu: [
    {
      name: 'view',
      meta: {
        title: '查看',
        type: MenuType.Content,
        permissions: ['settings::menu::view']
      }
    },
  ]
}

export default roles
