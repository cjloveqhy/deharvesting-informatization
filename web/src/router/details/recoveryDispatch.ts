import { Layout } from '@/router/constant';
import {AppRouteRecordRaw} from "@/router/types";

const mapRouteName = 'recoveryDispatchMap';

const recordRouteName = 'recoveryDispatchRecord';

const orderRouteName = 'recoveryDispatchOrder'

const routes: Array<AppRouteRecordRaw> = [
  {
    path: '/recoveryDispatch',
    name: mapRouteName,
    redirect: '/recoveryDispatch/map',
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
    redirect: '/recoveryDispatch/map',
    component: Layout,
    meta: {
      title: '调度记录',
    },
    children: [
      {
        path: 'record/details/:dispatchId',
        name: `${recordRouteName}_details`,
        component: () => import('@/views/recoveryDispatch/record/details/DispatchDetail.vue'),
        meta: {
          title: '调度详情',
          activeMenu: 'recoveryDispatch_record'
        },
      },
    ],
  },
  {
    path: '/recoveryDispatch',
    name: orderRouteName,
    redirect: '/recoveryDispatch/order',
    component: Layout,
    meta: {
      title: '查看订单',
    },
    children: [
      {
        path: 'order/details/:dispatchId',
        name: `${orderRouteName}_details`,
        component: () => import('@/views/recoveryDispatch/order/details/OrderDetail.vue'),
        meta: {
          title: '订单详情',
          activeMenu: 'recoveryDispatch_order'
        },
      },
    ],
  }
];

export default routes;
