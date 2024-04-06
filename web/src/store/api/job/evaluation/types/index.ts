import {CottonFieldVo} from "@/store/api/cottonField";
import {UserInfo} from "@/store/api/user";
import {BasicOption, PageQuery} from "@/store/common";

export enum EvaluationResult {

  /**
   * 合格
   */
  Pass_Qualified = 'PQ',

  /**
   * 不合格
   */
  NOT_Qualified = 'NQ'

}

export enum JobType {

  /**
   * 无人机
   */
  Uav = 'U',

  /**
   * 采棉机
   */
  Cotton_Picker = 'C'

}

export interface JobEvaluationPageFo extends PageQuery {
  orderId: string | null
  jobType: JobType
  jobId: string | null
  customerId: string | null
  evaluationResult: EvaluationResult | string
  startTime: string | null
  endTime: string | null
}

export interface JobEvaluationPageVo {
  id: string
  orderId: string
  cottonField: CottonFieldVo
  workTime: Date
  evaluationResult: EvaluationResult
  info: UserInfo | string
}

export interface JobQualifiedPercentPageFo extends PageQuery {
  jobType: JobType
}

export interface JobQualifiedPercentPageVo {
  id: string
  area: number
  cumulativeDuration: number
  percentPass: number
  info: UserInfo | string
}

export interface AddJobEvaluationFo{
  orderId: string;
  cottonFieldId: string;
  workTime: Date;
  evaluationResult: EvaluationResult;
  jobType: JobType;
  jobId: string;
}

export interface AddOptionJobEvaluationVo{
  plotNameOption: BasicOption[];
  userNameOption: BasicOption[];
  uavNameOption: BasicOption[];
  cottonFieldNameOption: BasicOption[];
}
