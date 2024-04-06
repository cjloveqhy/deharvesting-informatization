import { http } from '@/utils/http/axios';
import {RequestEnum} from "@/enums/httpEnum";
import {ContractTermsType, SaveContractTermsFo} from "@/store/api/contractTerms";

const getUri = (uri: string) => {
  return `/contract-terms${uri}`
}

export const api = {
  content: getUri('/content'),
  save: getUri('/save'),
}

/**
 * @description 获取合同条款信息
 */
export function getContent(type: ContractTermsType) {
  return http.request({
    url: api.content,
    method: RequestEnum.GET,
    params: {
      type: type
    }
  })
}

/**
 * @description 保存合同条款
 */
export function save(data: SaveContractTermsFo) {
  return http.request({
    url: api.save,
    method: RequestEnum.POST,
    data
  })
}
