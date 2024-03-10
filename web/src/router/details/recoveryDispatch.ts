import { Layout } from '@/router/constant';
import {AppRouteRecordRaw} from "@/router/types";

const mapRouteName = 'recoveryDispatchMap';

const recordRouteName = 'recoveryDispatchRecord';

const routes: Array<AppRouteRecordRaw> = [
  {
    path: '/recoveryDispatch',
    name: mapRouteName,
    component: Layout,
    meta: {
      title: '地图调度',
    },
    children: [
      {
        path: 'map/create/:factoryId',
        name: `${mapRouteName}_create`,
        component: () => import('@/views/recoveryDispatch/map/details/plotCreateDispatch.vue'),
        meta: {
          title: '棉田调度',
          activeMenu: 'recoveryDispatch_map'
        },
      },
    ],
  },
  {
    path: '/recoveryDispatch',
    name: recordRouteName,
    component: Layout,
    meta: {
      title: '调度记录',
    },
    children: [
      {
        path: 'record/details',
        name: `${mapRouteName}_details`,
        component: () => import('@/views/recoveryDispatch/record/details/DispatchDetail.vue'),
        meta: {
          title: '详情页',
          activeMenu: 'recoveryDispatch_record'
        },
      },
    ],
  }
];

export default routes;
