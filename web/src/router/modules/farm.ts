import {RouteRecordRaw} from 'vue-router';
import {Layout} from '@/router/constant';
import {constantRouterIcon} from "@/router/icons";

const routeName = 'farm';

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
    path: '/farm',
    name: routeName,
    redirect: '/farm/uav',
    component: Layout,
    meta: {
      title: '农机管理',
      icon: constantRouterIcon.VirtualMachine,
      sort: 5,
      alwaysShow: true
    },
    children: [
      {
        path: 'uav',
        name: `${routeName}_uav`,
        meta: {
          title: '无人机',
          permissions: ['farm::uav::view']
        },
        component: () => import('@/views/farm/uav.vue'),
      },
      {
        path: 'cottonPicker',
        name: `${routeName}_cottonPicker`,
        meta: {
          title: '采棉机',
          permissions: ['farm::cottonPicker::view']
        },
        component: () => import('@/views/farm/cottonPicker.vue'),
      },
      {
        path: 'dataTerminal',
        name: `${routeName}_dataTerminal`,
        meta: {
          title: '数据终端',
          permissions: ['farm::dataTerminal::view']
        },
        component: () => import('@/views/farm/dataTerminal.vue'),
      },
    ]
  }
]

export default routes;
