import {EvaluationResult} from "@/store/api/job/evaluation/types";

export const EvaluationResultOptions = [
  {label: '全部', value: ''},
  {label: '合格', value: EvaluationResult.Pass_Qualified},
  {label: '不合格', value: EvaluationResult.NOT_Qualified},
]
