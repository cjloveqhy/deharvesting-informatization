import { Layout } from '@/router/constant';
import {AppRouteRecordRaw} from "@/router/types";

const jobEvaluationRouteName = 'jobEvaluationMap';

const routes: Array<AppRouteRecordRaw> = [
  {
    path: '/jobEvaluation',
    name: jobEvaluationRouteName,
    redirect: '/jobEvaluation/map',
    component: Layout,
    meta: {
      title: '作业评价',
    },
    children: [
      {
        path: 'check/details/:cottonFieldId/:belonger',
        name: `${jobEvaluationRouteName}_details`,
        component: () => import('@/views/jobEvaluation/details/CheckJobEvaluation.vue'),
        meta: {
          title: '评价详情',
          activeMenu: 'jobEvaluation_check'
        },
      },
    ],
  },
];

export default routes;
