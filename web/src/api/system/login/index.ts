import { http } from '@/utils/http/axios';
import {RequestEnum} from "@/enums/httpEnum";
import {PasswordLoginForm} from "@/store/api/user";

const getUri = (uri: string) => {
  return `/login${uri}`
}

export const api = {
  pwd: getUri('/pwd'),
  logout: getUri('/logout'),
}

/**
 * @description: 用户登录
 */
export function login(params: PasswordLoginForm) {
  return http.request({
      url: api.pwd,
      method: RequestEnum.POST,
      params,
    },
    {
      isReturnNativeResponse: true,
      isTransformResponse: false,
    }
  );
}

/**
 * @description: 用户登出
 */
export function logout() {
  return http.request({
    url: api.logout,
    method: RequestEnum.GET,
  }, {
    isTransformResponse: false,
  });
}
