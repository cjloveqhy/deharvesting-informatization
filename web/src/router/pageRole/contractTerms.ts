import {PageRole} from "@/router/types";
import {MenuType} from "@/store/api/permission";

/**
 * key为明确的菜单名称，及 RouteRecordRaw 对象的name值，取自modules下指定路由的name,
 * 属性值为数组，标识为该菜单下有哪些page页上的权限可用
 */
const roles: PageRole = {
  contractTerms_cottonGrower: [
    {
      name: 'view',
      meta: {
        title: '查看',
        type: MenuType.Content,
        permissions: ['contractTerms::cottonGrower::view']
      }
    },
  ],
  contractTerms_uav: [
    {
      name: 'view',
      meta: {
        title: '查看',
        type: MenuType.Content,
        permissions: ['contractTerms::uav::view']
      }
    },
  ],
  contractTerms_driver: [
    {
      name: 'view',
      meta: {
        title: '查看',
        type: MenuType.Content,
        permissions: ['contractTerms::driver::view']
      }
    },
  ],
  contractTerms_ginnery: [
    {
      name: 'view',
      meta: {
        title: '查看',
        type: MenuType.Content,
        permissions: ['contractTerms::ginnery::view']
      }
    },
  ],
  contractTerms_testingUnit: [
    {
      name: 'view',
      meta: {
        title: '查看',
        type: MenuType.Content,
        permissions: ['contractTerms::testingUnit::view']
      }
    },
  ],
}

export default roles
