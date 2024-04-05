import {RouteRecordRaw} from 'vue-router';
import {Layout} from '@/router/constant';
import {constantRouterIcon} from "@/router/icons";

const routeName = 'recoveryDispatch';

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
    path: '/recoveryDispatch',
    name: routeName,
    redirect: '/recoveryDispatch/map',
    component: Layout,
    meta: {
      title: '棉花脱采收',
      icon: constantRouterIcon.StatsChartOutline,
      sort: 2,
    },
    children: [
      {
        path: 'map',
        name: `${routeName}_map`,
        meta: {
          title: '新建脱采收',
          permissions: ['recoveryDispatch::map::view']
        },
        component: () => import('@/views/recoveryDispatch/map/index.vue'),
      },
      {
        path: 'record',
        name: `${routeName}_record`,
        meta: {
          title: '脱采收记录',
          permissions: ['recoveryDispatch::record::view']
        },
        component: () => import('@/views/recoveryDispatch/record/index.vue'),
      },
      {
        path: 'order',
        name: `${routeName}_order`,
        meta: {
          title: '查看订单',
          permissions: ['recoveryDispatch::order::view']
        },
        component: () => import('@/views/recoveryDispatch/order/index.vue'),
      },
    ],
  },
];

export default routes;
