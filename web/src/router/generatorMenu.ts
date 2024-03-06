import {RouteRecordRaw} from "vue-router";
import {RouteRecord, PageRole} from "@/router/types";
import {DeepCopyOptions, DeepCopyExclude, DeepCopyObject} from "@/utils/copyUtil";
import {useProjectSettingStore} from "@/store/modules/projectSetting";
import {pageRoleList, routeModuleList} from "@/router/index";
import {updateMenus} from "@/api/menu";

export function updateMenu(routes: Array<RouteRecordRaw>, roles: PageRole) {

  const options = ref<DeepCopyOptions<Array<RouteRecordRaw>>>()

  /**
   * 将路由对象转换成可生成的动态路由对象，提交给后端进行保存
   */
  function deepCopy<T>(obj: T, roles: PageRole, options: DeepCopyOptions<T> = {}): DeepCopyExclude<T, keyof T> | T {
    if (Array.isArray(obj)) {
      return obj.map((item) => deepCopy(item, roles, options));
    } else if (typeof obj === 'object' && obj !== null) {
      return Object.keys(obj).reduce((result, key) => {
        if (options[key] === false) {
          if (key === 'component') {
            if (obj[key].name === 'component') {
              const regex = /import\("\/src\/views(.*?)(\.vue)(\?.*)?"\)/;
              const match = obj[key].toString().match(regex);
              if (match && match[1]) {
                result[key] = match[1]
              }
            } else {
              result[key] = obj[key].name.toLocaleUpperCase();
            }
          } else if (key === 'icon') {
            result[key] = obj[key].name
          }
        } else {
          if (key === 'name') {
            result['children'] = roles[obj['name']]
          }
          // 递归拷贝其他属性
          result[key] = deepCopy(obj[key], roles, options[key]);
        }
        return result;
      }, {} as DeepCopyObject<T>);
    } else {
      // 基本类型直接返回
      return obj;
    }
  }

  /**
   * 将所有菜单数据及权限全部提交给后端进行存储
   */
  function start(): void {
    // @ts-ignore
    const routers: RouteRecord[] = deepCopy(routes, roles, options.value)
    updateMenus(routers)
  }

  return { options, start }
}

export function setupPushMenu() {
  const projectSettingStore = useProjectSettingStore()

  const mode = projectSettingStore.getPermissionMode

  if (mode === 'FIXED') {
    const { options, start } = updateMenu(routeModuleList, pageRoleList)

    options.value = {
      // @ts-ignore
      component: false,
      meta: {
        icon: false
      },
      children: {
        component: false,
        meta: {icon: false
        },
        children: {
          component: false,
          meta: {
            icon: false
          }
        }
      }
    }

    start()

  }
}
