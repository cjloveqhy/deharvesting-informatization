import { Layout } from '@/router/constant';
import {AppRouteRecordRaw} from "@/router/types";

const mapRouteName = 'recoveryDispatchMap';

const recordRouteName = 'recoveryDispatchRecord';

const routes: Array<AppRouteRecordRaw> = [
  {
    path: '/recoveryDispatch/map',
    name: mapRouteName,
    component: Layout,
    meta: {
      title: '地图调度',
    },
    children: [
      {
        path: 'details',
        name: `${mapRouteName}_details`,
        component: () => import('@/views/recoveryDispatch/record/details/DispatchDetail.vue'),
        meta: {
          title: '详情页',
          activeMenu: 'recoveryDispatch_map'
        },
      },
    ],
  },
  {
    path: '/recoveryDispatch/record',
    name: recordRouteName,
    component: Layout,
    meta: {
      title: '调度记录',
      activeMenu: 'recoveryDispatch_record'
    },
    children: [
      {
        path: 'details',
        name: `${mapRouteName}_details`,
        component: () => import('@/views/recoveryDispatch/record/details/DispatchDetail.vue'),
        meta: {
          title: '详情页',
          activeMenu: 'recoveryDispatch_map'
        },
      },
    ],
  }
];

export default routes;
