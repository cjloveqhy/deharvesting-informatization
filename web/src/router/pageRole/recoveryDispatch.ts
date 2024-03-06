import {PageRole} from "@/router/types";
import {MenuType} from "@/store/api/permission/types";

/**
 * key为明确的菜单名称，及 RouteRecordRaw 对象的name值，取自modules下指定路由的name,
 * 属性值为数组，标识为该菜单下有哪些page页上的权限可用
 */
const roles: PageRole = {
  recoveryDispatch_map: [
    {
      name: 'add',
      meta: {
        title: '新增调度',
        type: MenuType.Button,
        permissions: ['recoveryDispatch::map::add']
      }
    },
    {
      name: 'del',
      meta: {
        title: '删除调度',
        type: MenuType.Button,
        permissions: ['recoveryDispatch::map::del']
      }
    }
  ]
}

export default roles
