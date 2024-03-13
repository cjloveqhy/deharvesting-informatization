import { http } from '@/utils/http/axios';
import {RequestEnum} from "@/enums/httpEnum";
import {PasswordLoginForm, RegisterForm, UserInfo} from "@/store/api/user";

const getUri = (uri: string) => {
  return `/user${uri}`
}

export const api = {
  login: getUri('/login'),
  register: getUri('/register'),
  logout: getUri('/logout'),
  validAccount: getUri('/validAccount'),
  getUserInfo: getUri('/getUserInfo'),
  updateUserInfo: getUri('/updateUserInfo'),
  getLoginVerifyImg: getUri('/getLoginVerifyImg'),
  userOptions: getUri('/userOptions'),
}

/**
 * @description: 用户登录
 */
export function login(params: PasswordLoginForm) {
  return http.request({
      url: api.login,
      method: RequestEnum.POST,
      params,
    }
  )
}

/**
 * @description: 用户注册
 */
export function register(params: RegisterForm) {
  return http.request({
      url: api.register,
      method: RequestEnum.POST,
      params,
    }
  )
}

/**
 * @description: 用户登出
 */
export function logout() {
  return http.request({
    url: api.logout,
    method: RequestEnum.GET,
  }, {
    isReturnNativeResponse: false,
    isTransformResponse: true
  });
}

/**
 * @description: 账户名校验
 */
export function validAccount(account: string) {
  return http.request({
    url: api.validAccount,
    method: RequestEnum.GET,
    params: {
      account: account
    }
  });
}

/**
 * @description: 获取用户信息
 */
export function getLoginUserInfo() {
  return http.request({
    url: api.getUserInfo,
    method: RequestEnum.GET,
  });
}

/**
 * @description: 更新用户信息
 */
export function updateUserInfo(data: UserInfo) {
  return http.request({
    url: api.updateUserInfo,
    method: RequestEnum.PUT,
    data: data
  });
}

/**
 * @description: 获取所有用户的信息作为可选项，以方便选择用户
 */
export function getUserOptions() {
  return http.request({
    url: api.userOptions,
    method: RequestEnum.GET,
  }, {
    isReturnNativeResponse: false,
    isTransformResponse: true
  })
}
