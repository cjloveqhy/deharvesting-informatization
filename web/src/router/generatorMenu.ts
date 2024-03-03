import {RouteRecordRaw} from "vue-router";
import {RouteRecord} from "@/router/types";
import {DeepCopyOptions, DeepCopyExclude, DeepCopyObject} from "@/utils/copyUtil";
import {useProjectSettingStore} from "@/store/modules/projectSetting";
import {routeModuleList} from "@/router/index";
import {updateMenus} from "@/api/menu";

export function updateMenu(routes: Array<RouteRecordRaw>) {

  const options = ref<DeepCopyOptions<Array<RouteRecordRaw>>>()

  function deepCopy<T>(obj: T, options: DeepCopyOptions<T> = {}): DeepCopyExclude<T, keyof T> | T {
    if (Array.isArray(obj)) {
      return obj.map((item) => deepCopy(item, options));
    } else if (typeof obj === 'object' && obj !== null) {
      return Object.keys(obj).reduce((result, key) => {
        if (options[key] === false) {
          if (key === 'component') {
            if (obj[key].name === 'component') {
              const regex = /import\("\/src\/views(.*?)\.vue"\)/;
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
          // 递归拷贝其他属性
          result[key] = deepCopy(obj[key], options[key]);
        }
        return result;
      }, {} as DeepCopyObject<T>);
    } else {
      // 基本类型直接返回
      return obj;
    }
  }

  function start(): void {
    // @ts-ignore
    const routers: RouteRecord[] = deepCopy(routes, options.value)
    updateMenus(routers)
  }

  return { options, start }
}

export function setupPushMenu() {
  const projectSettingStore = useProjectSettingStore()

  const mode = projectSettingStore.getPermissionMode

  if (mode === 'FIXED') {
    const { options, start } = updateMenu(routeModuleList)

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

    // start()

  }
}

export const backRouter = [
  {
    "path": "/homePage",
    "name": "homePage",
    "redirect": "/homePage/index",
    "component": "LAYOUT",
    "meta": {
      "title": "平台首页",
      "icon": "DashboardOutlined",
      "sort": 0
    },
    "children": [
      {
        "path": "index",
        "name": "homePage_index",
        "meta": {
          "title": "平台首页"
        },
        "component": "/homePage/index"
      }
    ]
  },
  {
    "path": "/recoveryDispatch",
    "name": "recoveryDispatch",
    "redirect": "/recoveryDispatch/map",
    "component": "LAYOUT",
    "meta": {
      "title": "采收调度",
      "sort": 1
    },
    "children": [
      {
        "path": "map",
        "name": "recoveryDispatch_map",
        "meta": {
          "title": "地图调度"
        },
        "component": "/recoveryDispatch/map/index"
      },
      {
        "path": "record",
        "name": "recoveryDispatch_record",
        "meta": {
          "title": "调度记录"
        },
        "component": "/recoveryDispatch/record/index"
      }
    ]
  },
  {
    "path": "/jobEvaluation",
    "name": "jobEvaluation",
    "redirect": "/jobEvaluation/map",
    "component": "LAYOUT",
    "meta": {
      "title": "作业评价",
      "sort": 2
    },
    "children": [
      {
        "path": "uav",
        "name": "jobEvaluation_uav",
        "meta": {
          "title": "无人机评价"
        },
        "component": "/jobEvaluation/uav/index"
      },
      {
        "path": "cottonPicker",
        "name": "jobEvaluation_cottonPicker",
        "meta": {
          "title": "采棉机评价"
        },
        "component": "/jobEvaluation/cottonPicker/index"
      },
      {
        "path": "setting",
        "name": "jobEvaluation_setting",
        "meta": {
          "title": "评价设置"
        },
        "children": [
          {
            "path": "uav",
            "name": "jobEvaluation_setting_uav",
            "meta": {
              "title": "无人机"
            },
            "component": "/jobEvaluation/setting/uav"
          },
          {
            "path": "cottonPicker",
            "name": "jobEvaluation_setting_cottonPicker",
            "meta": {
              "title": "采棉机"
            },
            "component": "/jobEvaluation/setting/cottonPicker"
          }
        ]
      }
    ]
  },
  {
    "path": "/basic",
    "name": "basic",
    "redirect": "/basic/workplace",
    "component": "LAYOUT",
    "meta": {
      "title": "基础管理",
      "sort": 3
    },
    "children": [
      {
        "path": "contractTerms",
        "name": "basic_contractTerms",
        "meta": {
          "title": "合同条款"
        },
        "component": "/basic/contractTerms/index"
      },
      {
        "path": "farm",
        "name": "basic_farm",
        "meta": {
          "title": "农机管理"
        },
        "children": [
          {
            "path": "uav",
            "name": "basic_farm_uav",
            "meta": {
              "title": "无人机"
            },
            "component": "/basic/farm/uav"
          },
          {
            "path": "cottonPicker",
            "name": "basic_farm_cottonPicker",
            "meta": {
              "title": "采棉机"
            },
            "component": "/basic/farm/cottonPicker"
          },
          {
            "path": "dataTerminal",
            "name": "basic_farm_dataTerminal",
            "meta": {
              "title": "数据终端"
            },
            "component": "/basic/farm/dataTerminal"
          }
        ]
      }
    ]
  },
]
