import {PasswordLoginForm, SexEnum, UserAutoInfo} from "@/store/api/user/types";
import {storage} from "@/utils/Storage";
import {ACCESS_TOKEN, CURRENT_USER, IS_SCREENLOCKED} from "@/store/mutation-types";
import { login as userLogin, logout as userLogout } from '@/api/system/login'
import {ResultEnum} from "@/enums/httpEnum";
import {getUserPermissions as obtainUserPermissions} from "@/api/user_role";

export const useUserApiStore = defineStore(
  'user-api',
  () => {

    const userAutoInfo = ref<UserAutoInfo>({
      id: '',
      token: storage.get(ACCESS_TOKEN, ''),
      account: '',
      permissions: [],
      info: storage.get(CURRENT_USER, {}),
    })

    function getToken() {
      return userAutoInfo.value.token
    }

    function getPermissions() {
      return userAutoInfo.value.permissions
    }

    function getUserInfo() {
      return userAutoInfo.value.info
    }

    /**
     * @description: 重置用户信息
     */
    function resetUserInfo() {
      userAutoInfo.value.info = {
        id: null,
        username: null,
        photo: null,
        sex: SexEnum.Unknown,
        account: null,
        mobile: null,
        email: null,
      }
    }

    /**
     * @description: 登录
     */
    async function login(params: PasswordLoginForm) {
      const response = await userLogin(params);
      const { data, status } = response;
      if (status === ResultEnum.SUCCESS) {
        const ex = 7 * 24 * 60 * 60;
        storage.set(ACCESS_TOKEN, data.token, ex);
        storage.set(CURRENT_USER, data.info, ex);
        storage.set(IS_SCREENLOCKED, false);
        userAutoInfo.value.token = storage.get(ACCESS_TOKEN)
        userAutoInfo.value.info = data
      }
      return response;
    }

    /**
     * @description: 获取用户的权限值
     */
    async function getUserPermissions() {
      const result = await obtainUserPermissions()
      if (result && result.length) {
        userAutoInfo.value.permissions = result
      }
      return result;
    }

    /**
     * @description: 登出
     */
    async function logout() {
      const msg = await userLogout()
      userAutoInfo.value.permissions = []
      resetUserInfo()
      storage.remove(ACCESS_TOKEN)
      storage.remove(CURRENT_USER)
      return msg
    }

    return {
      userAutoInfo,
      getToken,
      getPermissions,
      getUserInfo,
      login,
      getUserPermissions,
      logout
    }
  },
  {
    persist: false,
  }
)
