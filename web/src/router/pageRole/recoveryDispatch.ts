import {PageRole} from "@/router/types";
import {MenuType} from "@/store/api/permission";

/**
 * key为明确的菜单名称，及 RouteRecordRaw 对象的name值，取自modules下指定路由的name,
 * 属性值为数组，标识为该菜单下有哪些page页上的权限可用
 */
const roles: PageRole = {
  recoveryDispatch_map: [
    {
      name: 'view',
      meta: {
        title: '查看',
        type: MenuType.Content,
        permissions: ['recoveryDispatch::map::view']
      }
    }
  ],
  recoveryDispatch_record: [
    {
      name: 'view',
      meta: {
        title: '查看',
        type: MenuType.Content,
        permissions: ['recoveryDispatch::record::view']
      }
    }
  ],
  recoveryDispatch_order: [
    {
      name: 'view',
      meta: {
        title: '查看',
        type: MenuType.Content,
        permissions: ['recoveryDispatch::order::view']
      }
    }
  ]
}

export default roles
