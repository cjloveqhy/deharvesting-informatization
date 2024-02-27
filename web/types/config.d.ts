import {IconProps} from "naive-ui/es/icon/src/Icon";
import {Ref} from "vue";
import {RouteLocationRaw} from "vue-router";
import {DialogOptions} from "naive-ui/es/dialog/src/DialogProvider";

export interface ProjectSettingState {
  //导航模式
  navMode: string;
  //导航风格
  navTheme: string;
  //顶部设置
  headerSetting: IHeaderSetting;
  //页脚
  showFooter: boolean;
  //多标签
  menuSetting: IMenuSetting;
  //多标签
  multiTabsSetting: IMultiTabsSetting;
  //面包屑
  crumbsSetting: ICrumbsSetting;
  //权限模式
  permissionMode: string;
  //是否开启路由动画
  isPageAnimate: boolean;
  //路由动画类型
  pageAnimateType: string;
  // 是否处于移动端模式
  isMobile: boolean;
  // 是否需要登录
  isLogin: boolean
  // 路由白名单
  whitePathList: string[]
}

export interface IBodySetting {
  fixed: boolean;
}

export interface FuncIcon extends IconProps {
  tips?: string | ''
}

export interface FuncIconListener {
  type: string
  ev: (state: Ref<boolean>, e: Event) => any
}

export interface FuncButtonItem {
  beforeIcon: FuncIcon
  afterIcon?: FuncIcon
  eventObject?: object
  listener?: FuncIconListener[]
}

export interface UserMenuItem {
  label: string
  key: string | number
  type: 'function' | 'router' | 'dialog'
  exec: (route, router:RouteLocationRaw) => void | RouteLocationRaw | DialogOptions
}

export interface IHeaderSetting {
  bgColor: string;
  fixed: boolean;
  isReload: boolean;
  isProjectSetting: boolean
  funcButtons?: FuncButtonItem[]
  userMenu: UserMenuItem[]
}

export interface IMenuSetting {
  minMenuWidth: number;
  menuWidth: number;
  fixed: boolean;
  mixMenu: boolean;
  collapsed: boolean;
  mobileWidth: number;
}

export interface ICrumbsSetting {
  show: boolean;
  showIcon: boolean;
}

export interface IMultiTabsSetting {
  bgColor: string;
  fixed: boolean;
  show: boolean;
}
export interface GlobConfig {
  title: string;
  apiUrl: string;
  shortName: string;
  urlPrefix?: string;
  uploadUrl?: string;
  prodMock: boolean;
  imgUrl?: string;
}

export interface GlobEnvConfig {
  // 标题
  VITE_GLOB_APP_TITLE: string;
  // 接口地址
  VITE_GLOB_API_URL: string;
  // 接口前缀
  VITE_GLOB_API_URL_PREFIX?: string;
  // Project abbreviation
  VITE_GLOB_APP_SHORT_NAME: string;
  // 图片上传地址
  VITE_GLOB_UPLOAD_URL?: string;
  //图片前缀地址
  VITE_GLOB_IMG_URL?: string;
  //生产环境开启mock
  VITE_GLOB_PROD_MOCK: boolean;
}
