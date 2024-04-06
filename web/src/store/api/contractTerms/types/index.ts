export interface ContractTerms {
  id: string | null
  type: ContractTermsType
  content: string | null
}

export enum ContractTermsType {

  /**
   * 棉农
   */
  CottonGrower = 'C',
  /**
   * 飞手
   */
  Uav = 'U',
  /**
   * 司机
   */
  Driver = 'D',
  /**
   * 轧花厂
   */
  Ginnery = 'G',
  /**
   * 检测单位
   */
  TestingUnit = 'T',

}

export type SaveContractTermsFo = Omit<ContractTerms, "id">
