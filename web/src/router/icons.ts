import { renderIcon } from '@/utils';
import { DashboardOutlined } from '@vicons/antd';
import {RouterIcon} from "@/router/types";

//前端路由图标映射表
export const constantRouterIcon:RouterIcon = {
  DashboardOutlined: {icon: renderIcon(DashboardOutlined), name: 'DashboardOutlined'},
}
