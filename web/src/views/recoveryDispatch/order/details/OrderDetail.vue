<script setup lang="ts">
import {getCheckOrderDetails, getDetails} from "@/api/harvestSchedule";
import {getLabel} from "@/utils/optionUtil";
import {HarvestScheduleCheckDetailsVo, HarvestScheduleDetailsVo} from "@/store/api/harvestSchedule";
import {SexEnum} from "@/store/api/user";
import {
  EvaluationResultOptions,
} from "@/store/api/job/evaluation";
const route = useRoute()

const router = useRouter()

const dispatchDetails = ref<HarvestScheduleDetailsVo>({
  id: null,
  contacts: {
    id: null,
    username: null,
    photo: null,
    sex: SexEnum.Unknown,
    account: null,
    phone: null,
    email: null,
    createTime: null,
  },
  factoryName: null,
  addr: null,
  workingAbility: null,
  children: [],
  dispatchId: null,
  dispatchArea: null,
  createTime: null,
})

const checkOrderDetails = ref<HarvestScheduleCheckDetailsVo>({
  id: null,
  plotName: null,
  ginnery: null,
  contacts: {
    id: null,
    username: null,
    photo: null,
    sex: SexEnum.Unknown,
    account: null,
    phone: null,
    email: null,
    createTime: null
  },
  cultivatedArea: null,
  cottonVariety: null,
  addr: null,
  createTime: null,
  jobEvaluation: {
    id: "",
    orderId: "",
    cottonField: "",
    workTime: "",
    evaluationResult: "",
    info: {
      id: null,
      username: null,
      photo: null,
      sex: SexEnum.Unknown,
      account: null,
      phone: null,
      email: null,
      createTime: null
    }
  }
})

const loading = ref<boolean>(true)

function getData() {
  loading.value = true
  getDetails(route.params.dispatchId as string).then(res => {
    dispatchDetails.value = res.data
  }).finally(() => loading.value = false)
  getCheckOrderDetails(route.params.dispatchId as string).then(res => {
    console.log(res);
    checkOrderDetails.value = res.data
  })
}

getData()

const back = () => router.push({name: 'recoveryDispatch_order'})

</script>

<template>
  <n-flex vertical :size="0">
    <n-card
      :bordered="false"
      :segmented="{content: true}"
    >
      <template #header>
        <n-flex :wrap="false">
          <img src="@/assets/images/homePage/u516.png" style="width: 30px">
          <span>基本信息</span>
        </n-flex>
      </template>
      <template v-if="loading">
        <n-flex vertical size="large">
          <n-flex :wrap="false" :size="150">
            <n-skeleton class="w-1/3" height="30px"/>
            <n-skeleton class="w-1/3" height="30px"/>
            <n-skeleton class="w-1/3" height="30px"/>
          </n-flex>
          <n-flex :wrap="false" :size="150">
            <n-skeleton class="w-1/3" height="30px"/>
            <n-skeleton class="w-1/3" height="30px"/>
            <n-skeleton class="w-1/3" height="30px"/>
          </n-flex>
          <n-flex :wrap="false" :size="150">
            <n-skeleton class="w-1/3" height="30px"/>
            <n-skeleton class="w-1/3" height="30px"/>
            <div class="w-1/3"/>
          </n-flex>
        </n-flex>
      </template>
      <template v-else>
        <n-form
          label-placement="left"
          :show-feedback="false"
          :label-width="160"
        >
          <n-flex vertical size="large">
            <n-flex :wrap="false">
              <n-form-item class="w-1/3" label="轧花厂名称：">
                {{ dispatchDetails.factoryName }}
              </n-form-item>
              <n-form-item class="w-1/3" label="地址：">
                {{ dispatchDetails.addr }}
              </n-form-item>
              <n-form-item class="w-1/3" label="联系人：">
                {{ dispatchDetails.contacts.username }}
              </n-form-item>
            </n-flex>
            <n-flex :wrap="false">
              <n-form-item class="w-1/3" label="联系方式：">
                {{ dispatchDetails.contacts.phone }}
              </n-form-item>
              <n-form-item class="w-1/3" label="加工能力（亩）：">
                {{ dispatchDetails.workingAbility }}
              </n-form-item>
              <n-form-item class="w-1/3" label="调度面积（亩）：">
                {{ dispatchDetails.dispatchArea }}
              </n-form-item>
            </n-flex>
            <n-flex :wrap="false">
              <n-form-item class="w-1/3" label="调度单编号：">
                {{ dispatchDetails.dispatchId }}
              </n-form-item>
              <n-form-item class="w-1/3" label="调度单时间：">
                {{ dispatchDetails.createTime }}
              </n-form-item>
              <n-form-item class="w-1/3" label="">
              </n-form-item>
            </n-flex>
          </n-flex>
        </n-form>
      </template>
    </n-card>
    <n-card
      :bordered="false"
      :segmented="{content: true}"
    >
      <template #header>
        <n-flex :wrap="false">
          <img src="@/assets/images/homePage/u516.png" style="width: 30px">
          <span>订单信息</span>
        </n-flex>
      </template>
      <n-form
        label-placement="left"
        :show-feedback="false"
        :label-width="160"
      >
        <n-flex vertical size="large">
          <n-flex :wrap="false">
            <n-form-item class="w-1/3" label="地块名称：">
              {{ checkOrderDetails.plotName }}
            </n-form-item>
            <n-form-item class="w-1/3" label="地址：">
              {{ checkOrderDetails.addr }}
            </n-form-item>
            <n-form-item class="w-1/3" label="联系人：">
              {{ checkOrderDetails.contacts.username }}
            </n-form-item>
          </n-flex>
          <n-flex :wrap="false">
            <n-form-item class="w-1/3" label="联系方式：">
              {{ checkOrderDetails.contacts.phone }}
            </n-form-item>
            <n-form-item class="w-1/3" label="品种：">
              {{ checkOrderDetails.cottonVariety }}
            </n-form-item>
            <n-form-item class="w-1/3" label="面积：">
              {{ checkOrderDetails.cultivatedArea }}
            </n-form-item>
          </n-flex>
        </n-flex>
      </n-form>
    </n-card>
    <n-card
      :bordered="false"
      :segmented="{content: true}"
    >
      <template #header>
        <n-flex :wrap="false">
          <img src="@/assets/images/homePage/u516.png" style="width: 30px">
          <span>评价信息</span>
        </n-flex>
      </template>
      <n-form
        label-placement="left"
        :show-feedback="false"
        :label-width="160"
      >
        <n-flex vertical size="large">
          <n-flex :wrap="false">
            <n-form-item class="w-1/3" label="订单编号：">
              {{ checkOrderDetails.jobEvaluation.orderId }}
            </n-form-item>
            <n-form-item class="w-1/3" label="飞手：">
              {{ checkOrderDetails.jobEvaluation.info.username }}
            </n-form-item>
            <n-form-item class="w-1/3" label="联系方式：">
              {{ checkOrderDetails.jobEvaluation.info.phone }}
            </n-form-item>
          </n-flex>
          <n-flex :wrap="false">
            <n-form-item class="w-1/3" label="评价：">
              {{ checkOrderDetails.jobEvaluation.evaluationResult == "PQ" ? "合格" : "不合格"}}
            </n-form-item>
            <n-form-item class="w-1/3" label="工作时间：">
              {{ checkOrderDetails.jobEvaluation.workTime }}
            </n-form-item>
          </n-flex>
          <n-flex :wrap="false">
          </n-flex>
        </n-flex>
      </n-form>
      <template #footer>
        <n-flex justify="center">
          <n-button
            ghost
            type="info"
            size="large"
            @click="back"
            class="w-100px"
          >
            返回
          </n-button>
        </n-flex>
      </template>
    </n-card>
  </n-flex>
</template>
