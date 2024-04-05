import {PageRole} from "@/router/types";
import {MenuType} from "@/store/api/permission";

/**
 * key为明确的菜单名称，及 RouteRecordRaw 对象的name值，取自modules下指定路由的name,
 * 属性值为数组，标识为该菜单下有哪些page页上的权限可用
 */
const roles: PageRole = {
  jobEvaluation_uav: [
    {
      name: 'view',
      meta: {
        title: '查看',
        type: MenuType.Content,
        permissions: ['jobEvaluation::uav::view']
      }
    },
  ],
  jobEvaluation_cottonPicker: [
    {
      name: 'view',
      meta: {
        title: '查看',
        type: MenuType.Content,
        permissions: ['jobEvaluation::cottonPicker::view']
      }
    },
  ],
  jobEvaluation_setting_uav: [
    {
      name: 'view',
      meta: {
        title: '查看',
        type: MenuType.Content,
        permissions: ['jobEvaluation::setting::uav::view']
      }
    },
  ],
  jobEvaluation_setting_cottonPicker: [
    {
      name: 'view',
      meta: {
        title: '查看',
        type: MenuType.Content,
        permissions: ['jobEvaluation::setting::cottonPicker::view']
      }
    },
  ],
  jobEvaluation_check: [
    {
      name: 'view',
      meta: {
        title: '查看',
        type: MenuType.Content,
        permissions: ['jobEvaluation::check::view']
      }
    },
  ]
}

export default roles
