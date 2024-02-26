<template>
  <div class="layout-header">
    <!--顶部菜单-->
    <div
      class="layout-header-left"
      v-if="navMode === 'horizontal' || (navMode === 'horizontal-mix' && mixMenu)"
    >
      <div class="logo" v-if="navMode === 'horizontal'">
        <img :src="websiteConfig.logo" alt="" />
        <h2 v-show="!collapsed" class="title">{{ websiteConfig.title }}</h2>
      </div>
      <AsideMenu
        v-model:collapsed="collapsed"
        v-model:location="getMenuLocation"
        :inverted="getInverted"
        mode="horizontal"
      />
    </div>
    <!--左侧菜单-->
    <div class="layout-header-left" v-else>
      <!-- 菜单收起 -->
      <div
        class="ml-1 layout-header-trigger layout-header-trigger-min"
        @click="() => $emit('update:collapsed', !collapsed)"
      >
        <n-icon size="18" v-if="collapsed">
          <MenuUnfoldOutlined />
        </n-icon>
        <n-icon size="18" v-else>
          <MenuFoldOutlined />
        </n-icon>
      </div>
      <!-- 刷新 -->
      <div
        class="mr-1 layout-header-trigger layout-header-trigger-min"
        v-if="headerSetting.isReload"
        @click="reloadPage"
      >
        <n-icon size="18">
          <ReloadOutlined />
        </n-icon>
      </div>
      <!-- 面包屑 -->
      <n-breadcrumb v-if="crumbsSetting.show">
        <template
          v-for="routeItem in breadcrumbList"
          :key="routeItem.name === 'Redirect' ? void 0 : routeItem.name"
        >
          <n-breadcrumb-item v-if="routeItem.meta.title">
            <n-dropdown
              v-if="routeItem.children.length"
              :options="routeItem.children"
              @select="dropdownSelect"
            >
              <span class="link-text">
                <component
                  v-if="crumbsSetting.showIcon && routeItem.meta.icon"
                  :is="routeItem.meta.icon"
                />
                {{ routeItem.meta.title }}
              </span>
            </n-dropdown>
            <span class="link-text" v-else>
              <component
                v-if="crumbsSetting.showIcon && routeItem.meta.icon"
                :is="routeItem.meta.icon"
              />
              {{ routeItem.meta.title }}
            </span>
          </n-breadcrumb-item>
        </template>
      </n-breadcrumb>
    </div>
    <div class="layout-header-right">
      <div
        class="layout-header-trigger layout-header-trigger-min"
        v-for="(item, index) in headerSetting.funcButtons"
        :key="index"
      >
        <n-tooltip placement="bottom">
          <template #trigger>
            <div @click="() => funcButtonState[index].value = !funcButtonState[index].value">
              <n-icon v-if="funcButtonState[index].value" v-bind="item.beforeIcon" v-on="item.eventObject || {}" />
              <n-icon v-else v-bind="item.afterIcon ? item.afterIcon : item.beforeIcon" v-on="item.eventObject || {}" />
            </div>
          </template>
          <template v-if="funcButtonState[index].value">
            <span>{{ item.beforeIcon.tips }}</span>
          </template>
          <template v-else>
            <template v-if="item.afterIcon">
              <span>{{ item.afterIcon.tips }}</span>
            </template>
            <template v-else>
              <span>{{ item.beforeIcon.tips }}</span>
            </template>
          </template>
        </n-tooltip>
      </div>
      <!-- 个人中心 -->
      <div class="layout-header-trigger layout-header-trigger-min">
        <n-dropdown trigger="hover" @select="avatarSelect" :options="headerSetting.userMenu">
          <div class="avatar">
            <n-avatar round>
              {{ username }}
              <template #icon>
                <UserOutlined />
              </template>
            </n-avatar>
          </div>
        </n-dropdown>
      </div>
      <!--设置-->
      <template v-if="headerSetting.isProjectSetting">
        <div class="layout-header-trigger layout-header-trigger-min" @click="openSetting">
          <n-tooltip placement="bottom-end">
            <template #trigger>
              <n-icon size="18" style="font-weight: bold">
                <SettingOutlined />
              </n-icon>
            </template>
            <span>项目配置</span>
          </n-tooltip>
        </div>
      </template>
    </div>
  </div>
  <!--项目配置-->
  <ProjectSetting v-if="headerSetting.isProjectSetting" ref="drawerSetting" />
</template>

<script lang="ts">
import {defineComponent, reactive, toRefs, ref, computed, unref, Ref} from 'vue';
import {useRouter, useRoute, RouteLocationRaw} from 'vue-router';
import components from './components';
import { NDialogProvider, useDialog } from 'naive-ui';
import { useUserApiStore } from '@/store/api/user';
import ProjectSetting from './ProjectSetting.vue';
import { AsideMenu } from '@/layout/components/Menu';
import { useProjectSetting } from '@/hooks/setting/useProjectSetting';
import { websiteConfig } from '@/config/website.config';

export default defineComponent({
  name: 'PageHeader',
  components: { ...components, NDialogProvider, ProjectSetting, AsideMenu },
  props: {
    collapsed: {
      type: Boolean,
    },
    inverted: {
      type: Boolean,
    },
  },
  setup(props) {
    const userStore = useUserApiStore();
    const dialog = useDialog();
    const { navMode, navTheme, headerSetting, menuSetting, crumbsSetting } = useProjectSetting();

    const { account } = userStore?.getUserInfo() || {};

    const drawerSetting = ref();

    const funcButtonState = ref<Ref<boolean>[]>([])

    const state = reactive({
      username: account ?? '',
      navMode,
      navTheme,
      headerSetting,
      crumbsSetting,
    });

    function init() {
      const { funcButtons } = headerSetting.value
      if (funcButtons) {
        funcButtons.forEach((item, index) => {
          funcButtonState.value[index] = ref(true)
          if (item.listener && item.listener.length > 0) {
            item.listener.forEach(e => {
              // @ts-ignore
              document.addEventListener(e.type, (_, event) => {
                e.ev(funcButtonState.value[index], event)
              });
            })
          }
        });
      }
    }

    init()

    const getInverted = computed(() => {
      return ['light', 'header-dark'].includes(unref(navTheme))
        ? props.inverted
        : !props.inverted;
    });

    const mixMenu = computed(() => {
      return unref(menuSetting).mixMenu;
    });

    const getChangeStyle = computed(() => {
      const { collapsed } = props;
      const { minMenuWidth, menuWidth } = unref(menuSetting);
      return {
        left: collapsed ? `${minMenuWidth}px` : `${menuWidth}px`,
        width: `calc(100% - ${collapsed ? `${minMenuWidth}px` : `${menuWidth}px`})`,
      };
    });

    const getMenuLocation = computed(() => {
      return 'header';
    });

    const router = useRouter();
    const route = useRoute();

    const generator: any = (routerMap) => {
      return routerMap.map((item) => {
        const currentMenu = {
          ...item,
          label: item.meta.title,
          key: item.name,
          disabled: item.path === '/',
        };
        // 是否有子菜单，并递归处理
        if (item.children && item.children.length > 0) {
          // Recursion
          currentMenu.children = generator(item.children, currentMenu);
        }
        return currentMenu;
      });
    };

    const breadcrumbList = computed(() => {
      return generator(route.matched);
    });

    const dropdownSelect = (key) => {
      router.push({ name: key });
    };

    // 刷新页面
    const reloadPage = () => {
      router.push({
        path: '/redirect' + unref(route).fullPath,
      });
    };

    //头像下拉菜单
    const avatarSelect = (_, option) => {
      if (option.type === 'function') {
        option.exec(route, router)
      } else if (option.type === 'router') {
        router.push(option.exec(route, router) as RouteLocationRaw)
      } else if (option.type === 'dialog') {
        dialog.create({...option.exec(route, router)})
      }
    };

    function openSetting() {
      const { openDrawer } = drawerSetting.value;
      openDrawer();
    }

    return {
      ...toRefs(state),
      funcButtonState,
      route,
      dropdownSelect,
      getChangeStyle,
      avatarSelect,
      breadcrumbList,
      reloadPage,
      drawerSetting,
      openSetting,
      getInverted,
      getMenuLocation,
      mixMenu,
      websiteConfig,
    };
  },
});
</script>

<style lang="less" scoped>
  .layout-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding: 0;
    height: 64px;
    box-shadow: 0 1px 4px rgb(0 21 41 / 8%);
    transition: all 0.2s ease-in-out;
    width: 100%;
    z-index: 11;

    &-left {
      display: flex;
      align-items: center;

      .logo {
        display: flex;
        align-items: center;
        justify-content: center;
        height: 64px;
        line-height: 64px;
        overflow: hidden;
        white-space: nowrap;
        padding-left: 10px;

        img {
          width: auto;
          height: 32px;
          margin-right: 10px;
        }

        .title {
          margin-bottom: 0;
        }
      }

      ::v-deep(.ant-breadcrumb span:last-child .link-text) {
        color: #515a6e;
      }

      .n-breadcrumb {
        display: inline-block;
      }

      &-menu {
        color: var(--text-color);
      }
    }

    &-right {
      display: flex;
      align-items: center;
      margin-right: 20px;

      .avatar {
        display: flex;
        align-items: center;
        height: 64px;
      }

      > * {
        cursor: pointer;
      }
    }

    &-trigger {
      display: inline-block;
      width: 64px;
      height: 64px;
      text-align: center;
      cursor: pointer;
      transition: all 0.2s ease-in-out;

      .n-icon {
        display: flex;
        align-items: center;
        height: 64px;
        line-height: 64px;
      }

      &:hover {
        background: hsla(0, 0%, 100%, 0.08);
      }

      .anticon {
        font-size: 16px;
        color: #515a6e;
      }
    }

    &-trigger-min {
      width: auto;
      padding: 0 12px;
    }
  }

  .layout-header-light {
    background: #fff;
    color: #515a6e;

    .n-icon {
      color: #515a6e;
    }

    .layout-header-left {
      ::v-deep(.n-breadcrumb .n-breadcrumb-item:last-child .n-breadcrumb-item__link) {
        color: #515a6e;
      }
    }

    .layout-header-trigger {
      &:hover {
        background: #f8f8f9;
      }
    }
  }

  .layout-header-fix {
    position: fixed;
    top: 0;
    right: 0;
    left: 200px;
    z-index: 11;
  }

  //::v-deep(.menu-router-link) {
  //  color: #515a6e;
  //
  //  &:hover {
  //    color: #1890ff;
  //  }
  //}
</style>
