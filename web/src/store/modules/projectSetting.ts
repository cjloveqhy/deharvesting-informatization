import projectSetting from '@/settings/projectSetting';
import type {AxiosConfig, ProjectSettingState} from '/#/config';

const {
  navMode,
  navTheme,
  isMobile,
  headerSetting,
  showFooter,
  menuSetting,
  multiTabsSetting,
  crumbsSetting,
  permissionMode,
  isPageAnimate,
  pageAnimateType,
  isLogin,
  whitePathList,
  axiosConfig
} = projectSetting;

export const useProjectSettingStore = defineStore({
  id: 'app-project-setting',
  state: (): ProjectSettingState => ({
    navMode: navMode,
    navTheme,
    isMobile,
    headerSetting,
    showFooter,
    menuSetting,
    multiTabsSetting,
    crumbsSetting,
    permissionMode,
    isPageAnimate,
    pageAnimateType,
    isLogin,
    whitePathList,
    axiosConfig
  }),
  getters: {
    getNavMode(): string {
      return this.navMode;
    },
    getNavTheme(): string {
      return this.navTheme;
    },
    getIsMobile(): boolean {
      return this.isMobile;
    },
    getHeaderSetting(): object {
      return this.headerSetting;
    },
    getShowFooter(): boolean {
      return this.showFooter;
    },
    getMenuSetting(): object {
      return this.menuSetting;
    },
    getMultiTabsSetting(): object {
      return this.multiTabsSetting;
    },
    getCrumbsSetting(): object {
      return this.crumbsSetting;
    },
    getPermissionMode(): string {
      return this.permissionMode;
    },
    getIsPageAnimate(): boolean {
      return this.isPageAnimate;
    },
    getPageAnimateType(): string {
      return this.pageAnimateType;
    },
    getIsLogin(): boolean {
      return this.isLogin;
    },
    getWhitePathList(): string[] {
      return this.whitePathList;
    },
    getAxiosConfig(): AxiosConfig {
      return this.axiosConfig;
    },
  },
  actions: {
    setNavTheme(value: string): void {
      this.navTheme = value;
    },
    setIsMobile(value: boolean): void {
      this.isMobile = value;
    },
  },
});
