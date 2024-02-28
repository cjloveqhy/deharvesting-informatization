import { App } from 'vue';
import { createRouter, createWebHistory, RouteRecordRaw } from 'vue-router';
import { RedirectRoute, RootRoute, LoginRoute, RegisterRoute, ForgetPasswordRoute } from '@/router/base';
import { createRouterGuards } from './guards';
import type { IModuleType } from './types';

const details = import.meta.glob<IModuleType>('./details/**/*.ts', { eager: true });
const modules = import.meta.glob<IModuleType>('./modules/**/*.ts', { eager: true });

// 组装路由
function assembleRoute(routes: Record<string, IModuleType>): RouteRecordRaw[] {
  return Object.keys(routes).reduce((list, key) => {
    const mod = routes[key].default ?? {};
    const modList = Array.isArray(mod) ? [...mod] : [mod];
    return [...list, ...modList];
  }, [])
}

const routeDetailList = assembleRoute(details)

const routeModuleList = assembleRoute(modules)

function sortRoute(a, b) {
  return (a.meta?.sort ?? 0) - (b.meta?.sort ?? 0);
}

routeModuleList.sort(sortRoute);

//需要验证权限
export const asyncRoutes = [...routeModuleList];

//普通路由 无需验证权限
export const constantRouter: RouteRecordRaw[] = [LoginRoute, RegisterRoute, ForgetPasswordRoute, RootRoute, RedirectRoute, ...routeDetailList];

const router = createRouter({
  history: createWebHistory(),
  routes: constantRouter,
  strict: true,
  scrollBehavior: () => ({ left: 0, top: 0 }),
});

export function setupRouter(app: App) {
  app.use(router);
  // 创建路由守卫
  createRouterGuards(router);
}

export default router;
