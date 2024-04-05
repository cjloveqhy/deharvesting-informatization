import {useUserApiStore} from "@/store/api/user";
import {TABS_ROUTES} from "@/store/mutation-types";
import {FuncButtonItem, UserMenuItem} from "/#/config";
import {FullscreenExitOutlined, FullscreenOutlined, LockOutlined} from "@vicons/antd";
import {useScreenLockStore} from "@/store/modules/screenLock";
import UserInfo from '@/components/UserInfo/UserInfo.vue'

// 用户菜单
export const userMenu:UserMenuItem[] = [
  {
    label: '个人设置',
    key: 1,
    type: 'modal',
    exec: () => {
      return {
        show: true,
        preset: 'dialog',
        title: '个人设置',
        className: 'n-dialog n-dialog--closable n-dialog--icon-left n-modal userSetting',
        content: () => h(UserInfo),
        showIcon: false
      }
    }
  },
  {
    label: '退出登录',
    key: 2,
    type: 'dialog',
    exec: (route, router) => {
      return {
        type: 'info',
        title: '提示',
        content: '您确定要退出登录吗',
        positiveText: '确定',
        negativeText: '取消',
        onPositiveClick: () => {
          const message = window['$message'];
          const userStore = useUserApiStore();
          userStore.logout().then(data => {
            message.success(data);
            // 移除标签页
            localStorage.removeItem(TABS_ROUTES);
            // @ts-ignore
            router.replace({
              name: 'Login',
              query: {
                redirect: route.fullPath,
              },
            })
              .finally(() => location.reload());
          });
        },
        onNegativeClick: () => {
        },
      }
    }
  }
]

// 功能按钮
export const funcButtons: FuncButtonItem[] = [
  {
    beforeIcon: {
      tips: '锁屏',
      size: '18',
      component: shallowRef(LockOutlined),
    },
    eventObject: {
      click: () => {
        const useLockscreen = useScreenLockStore()
        useLockscreen.setLock(true)
      },
    }
  },
  {
    beforeIcon: {
      tips: '全屏',
      size: '18',
      component: shallowRef(FullscreenOutlined),
    },
    afterIcon: {
      tips: '缩小',
      size: '18',
      component: shallowRef(FullscreenExitOutlined),
    },
    eventObject: {
      click: (e) => {
        e.stopPropagation()
        if (!document.fullscreenElement) {
          document.documentElement.requestFullscreen();
        } else {
          if (document.exitFullscreen) {
            document.exitFullscreen();
          }
        }
      },
    },
    listener: [
      {
        type: 'fullscreenchange',
        ev: (state) => {
          state.value = !state.value
        }
      },
    ]
  },
  /*{
    beforeIcon: {
      tips: '通知',
      size: '18',
      component: shallowRef(NotificationsOutline),
    },
    badge: {
      show: true,
    },
    eventObject: {
      click: () => {

      },
    }
  },*/
]
