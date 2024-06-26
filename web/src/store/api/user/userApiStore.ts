import {
  PasswordLoginForm,
  RegisterForm,
  SexEnum,
  UserAutoInfo,
  UserInfo,
  UserOption
} from "@/store/api/user/types";
import {storage} from "@/utils/Storage";
import {ACCESS_TOKEN, CURRENT_USER, IS_SCREENLOCKED, USER_OPTIONS} from "@/store/mutation-types";
import {
  getUserOptions,
  login as userLogin,
  logout as userLogout,
  register as userRegister
} from '@/api/system/user'
import {ResultEnum} from "@/enums/httpEnum";
import {getUserPermissions as obtainUserPermissions} from "@/api/user_role";
import {LogicalEnum} from "@/enums/LogicalEnum";

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

    const userOptions = ref<UserOption[]>(storage.get(USER_OPTIONS, []))

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
        phone: null,
        email: null,
        status: LogicalEnum.NO,
        createTime: null,
      }
    }

    /**
     * 刷新用户信息
     */
    function refreshUserInfo(info: UserInfo) {
      storage.set(CURRENT_USER, info, 7 * 24 * 60 * 60)
      userAutoInfo.value.info = info
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
        userAutoInfo.value = data
        const result = await obtainUserPermissions()
        const options = await getUserOptions()
        storage.set(USER_OPTIONS, options)
        userOptions.value = options
        if (result && result.length) {
          userAutoInfo.value.permissions = result
        }
      }
      return response;
    }

    /**
     * @description: 注册
     */
    async function register(params: RegisterForm) {
      const response = await userRegister(params)
      return response
    }

    /**
     * @description: 获取用户的权限值
     */
    function getUserPermissions() {
      return userAutoInfo.value.permissions;
    }

    /**
     * @description: 登出
     */
    async function logout() {
      const msg = await userLogout()
      userAutoInfo.value.permissions = []
      resetUserInfo()
      storage.remove(USER_OPTIONS)
      storage.remove(ACCESS_TOKEN)
      storage.remove(CURRENT_USER)
      return msg
    }

    return {
      userAutoInfo,
      userOptions,
      refreshUserInfo,
      getToken,
      getPermissions,
      getUserInfo,
      login,
      register,
      getUserPermissions,
      logout
    }
  },
  {
    persist: false,
  }
)
