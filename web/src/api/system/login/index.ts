import { http } from '@/utils/http/axios';
import {RequestEnum} from "@/enums/httpEnum";
import {PasswordLoginForm, RegisterForm} from "@/store/api/user";

const getUri = (uri: string) => {
  return `/user${uri}`
}

export const api = {
  login: getUri('/login'),
  register: getUri('/register'),
  logout: getUri('/logout'),
}

/**
 * @description: 用户登录
 */
export function login(params: PasswordLoginForm) {
  return http.request({
      url: api.login,
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
 * @description: 用户登录
 */
export function register(params: RegisterForm) {
  return http.request({
      url: api.register,
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
