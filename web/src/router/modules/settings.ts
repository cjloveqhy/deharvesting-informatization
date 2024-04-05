import {RouteRecordRaw} from 'vue-router';
import {Layout} from '@/router/constant';
import {constantRouterIcon} from "@/router/icons";

const routeName = 'settings';

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
    path: '/settings',
    name: routeName,
    redirect: '/settings/user',
    component: Layout,
    meta: {
      title: '系统管理',
      icon: constantRouterIcon.Settings,
      sort: 5,
    },
    children: [
      {
        path: 'user',
        name: `${routeName}_user`,
        meta: {
          title: '用户管理',
          icon: constantRouterIcon.UserOutlined,
          permissions: ['settings::user::view']
        },
        component: () => import('@/views/settings/user/index.vue'),
      },
      {
        path: 'role',
        name: `${routeName}_role`,
        meta: {
          title: '角色管理',
          icon: constantRouterIcon.UserRole,
          permissions: ['settings::role::view']
        },
        component: () => import('@/views/settings/role/index.vue'),
      },
      {
        path: 'menu',
        name: `${routeName}_menu`,
        meta: {
          title: '菜单管理',
          icon: constantRouterIcon.Menu,
          permissions: ['settings::menu::view']
        },
        component: () => import('@/views/settings/menu/index.vue'),
      },
    ],
  },
];

export default routes;
