export enum SexEnum {

  /**
   * 男性
   */
  Man = 'M',

  /**
   * 女性
   */
  Woman = 'W',

  /**
   * 未知
   */
  Unknown = 'U'
}

export interface UserInfo {
  id: string | null
  username: string | null
  photo: string | null
  sex: SexEnum
  account: string | null
  phone: string | null
  email: string | null
  createTime: Date | null
}

export interface UserAutoInfo {
  id: string
  token: string
  account: string
  permissions: string[]
  info: UserInfo
}

export  enum LoginEnum {

  /**
   * 账号密码登录
   */
  PASSWORD= 'P',

  /**
   * 手机号登录
   */
  MOBILE = 'M',

  /**
   * 邮箱登录
   */
  EMAIL = 'E',

  /**
   * 扫码登录
   */
  CODE = 'C',

  /**
   * 认证授权登录
   */
  OAUTH2 = 'O'

}

export interface BasicLoginForm {
  type: LoginEnum
}
export interface PasswordLoginForm extends BasicLoginForm {
  account: string
  password: string
  code: string
}

export interface RegisterForm {
  account: string,
  password: string,
  rePassword: string
}

export interface UserOption {
  id: string | null
  username: string | null
  account: string | null
  phone: string | null
}
