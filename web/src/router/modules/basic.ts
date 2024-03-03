import { RouteRecordRaw } from 'vue-router';
import { Layout } from '@/router/constant';
import {constantRouterIcon} from "@/router/icons";

const routeName = 'basic';

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
    path: '/basic',
    name: routeName,
    redirect: '/basic/workplace',
    component: Layout,
    meta: {
      title: '基础管理',
      icon: constantRouterIcon.DashboardOutlined,
      sort: 3,
    },
    children: [
      {
        path: 'contractTerms',
        name: `${routeName}_contractTerms`,
        meta: {
          title: '合同条款',
        },
        component: () => import('@/views/basic/contractTerms/index.vue'),
      },
      {
        path: 'farm',
        name: `${ routeName }_farm`,
        meta: {
          title: '农机管理',
        },
        children: [
          {
            path: 'uav',
            name: `${routeName}_farm_uav`,
            meta: {
              title: '无人机',
            },
            component: () => import('@/views/basic/farm/uav.vue'),
          },
          {
            path: 'cottonPicker',
            name: `${routeName}_farm_cottonPicker`,
            meta: {
              title: '采棉机',
            },
            component: () => import('@/views/basic/farm/cottonPicker.vue'),
          },
          {
            path: 'dataTerminal',
            name: `${routeName}_farm_dataTerminal`,
            meta: {
              title: '数据终端',
            },
            component: () => import('@/views/basic/farm/dataTerminal.vue'),
          },
        ]
      },
    ],
  },
];

export default routes;
