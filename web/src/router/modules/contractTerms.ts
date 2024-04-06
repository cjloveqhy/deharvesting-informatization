import {RouteRecordRaw} from 'vue-router';
import {Layout} from '@/router/constant';
import {constantRouterIcon} from "@/router/icons";

const routeName = 'contractTerms';

/**
 * @param name 路由名称, 必须设置,且不能重名
 * @param meta 路由元信息（路由附带扩展信息）
 * @param redirect 重定向地址, 访问这个路由时,自定进行重定向
 * @param meta.disabled 禁用整个菜单
 * @param meta.title 菜单名称
 * @param meta.icon 菜单图标
 * @param meta.keepAlive 缓存该路由
 * @param meta.sort 排序越小越排前
 * */
const routes: Array<RouteRecordRaw> = [
  {
    path: '/contractTerms',
    name: routeName,
    redirect: '/contractTerms/cottonGrower',
    component: Layout,
    meta: {
      title: '合同条款',
      icon: constantRouterIcon.Contract,
      sort: 4,
      alwaysShow: true
    },
    children: [
      {
        path: 'cottonGrower',
        name: `${routeName}_cottonGrower`,
        meta: {
          title: '棉农',
          permissions: ['contractTerms::cottonGrower::view']
        },
        component: () => import('@/views/contractTerms/CottonFarmer.vue'),
      },
      {
        path: 'uav',
        name: `${routeName}_uav`,
        meta: {
          title: '飞手',
          permissions: ['contractTerms::uav::view']
        },
        component: () => import('@/views/contractTerms/Uav.vue'),
      },
      {
        path: 'driver',
        name: `${routeName}_driver`,
        meta: {
          title: '司机',
          permissions: ['contractTerms::driver::view']
        },
        component: () => import('@/views/contractTerms/Driver.vue'),
      },
      {
        path: 'ginnery',
        name: `${routeName}_ginnery`,
        meta: {
          title: '轧花厂',
          permissions: ['contractTerms::ginnery::view']
        },
        component: () => import('@/views/contractTerms/Ginnery.vue'),
      },
      {
        path: 'testingUnit',
        name: `${routeName}_testingUnit`,
        meta: {
          title: '检测单位',
          permissions: ['contractTerms::testingUnit::view']
        },
        component: () => import('@/views/contractTerms/TestingUnit.vue'),
      },
    ]
  }
]

export default routes;
