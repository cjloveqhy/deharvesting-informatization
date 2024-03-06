import { App } from 'vue';
import { createRouter, createWebHistory, RouteRecordRaw } from 'vue-router';
import { RedirectRoute, RootRoute, LoginRoute, RegisterRoute, ForgetPasswordRoute } from '@/router/base';
import { createRouterGuards } from './guards';
import type { IModuleType, IPageModuleType, PageRole } from './types';

const details = import.meta.glob<IModuleType>('./details/**/*.ts', { eager: true });
const modules = import.meta.glob<IModuleType>('./modules/**/*.ts', { eager: true });
const pageRoles = import.meta.glob<IPageModuleType>('./pageRole/**/*.ts', { eager: true });

// 组装路由
export function assembleRoute(routes: Record<string, IModuleType>): RouteRecordRaw[] {
  return Object.keys(routes).reduce((list, key) => {
    const mod = routes[key].default ?? {};
    const modList = Array.isArray(mod) ? [...mod] : [mod];
    return [...list, ...modList];
  }, [])
}

export function assemblePageRole(roles: Record<string, IPageModuleType>):PageRole {
  return Object.keys(roles).reduce((obj, key) => {
    const rolObj = roles[key].default ?? {};
    for (let key in rolObj) {
      rolObj[key].forEach(item => item.name = `${key}_${item.name}`)
    }
    return {...obj, ...rolObj}
  }, {})
}

const routeDetailList = assembleRoute(details)

export const routeModuleList = assembleRoute(modules)

export const pageRoleList = assemblePageRole(pageRoles)

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
