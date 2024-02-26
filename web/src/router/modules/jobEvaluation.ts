import { RouteRecordRaw } from 'vue-router';
import { Layout } from '@/router/constant';
import { DashboardOutlined } from '@vicons/antd';
import { renderIcon } from '@/utils';

const routeName = 'jobEvaluation';

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
    path: '/jobEvaluation',
    name: routeName,
    redirect: '/jobEvaluation/map',
    component: Layout,
    meta: {
      title: '作业评价',
      icon: renderIcon(DashboardOutlined),
      sort: 2,
    },
    children: [
      {
        path: 'uav',
        name: `${routeName}_uav`,
        meta: {
          title: '无人机评价',
        },
        component: () => import('@/views/jobEvaluation/uav/index.vue'),
      },
      {
        path: 'cottonPicker',
        name: `${routeName}_cottonPicker`,
        meta: {
          title: '采棉机评价',
        },
        component: () => import('@/views/jobEvaluation/cottonPicker/index.vue')
      },
      {
        path: 'setting',
        name: `${routeName}_setting`,
        meta: {
          title: '评价设置',
        },
        children: [
          {
            path: 'uav',
            name: `${routeName}_setting_uav`,
            meta: {
              title: '无人机',
            },
            component: () => import('@/views/jobEvaluation/setting/uav.vue')
          },
          {
            path: 'cottonPicker',
            name: `${routeName}_setting_cottonPicker`,
            meta: {
              title: '采棉机',
            },
            component: () => import('@/views/jobEvaluation/setting/cottonPicker.vue')
          }
        ]
      },
    ],
  },
];

export default routes;
