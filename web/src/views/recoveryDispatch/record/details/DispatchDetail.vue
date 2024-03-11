<script setup lang="ts">
import {getDetails} from "@/api/harvestSchedule";
import {HarvestScheduleDetailsVo} from "@/store/api/harvestSchedule";
import {SexEnum} from "@/store/api/user";
import {NFlex} from "naive-ui";

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

const columns = ref([
  {
    key: 'index',
    title: '序号',
    align: 'center',
    titleAlign: 'center',
    render: (_, index) => {
      return index + 1
    }
  },
  {
    key: 'plotName',
    title: '地块名称',
    align: 'center',
    titleAlign: 'center',
  },
  {
    key: 'cottonVariety',
    title: '品种',
    align: 'center',
    titleAlign: 'center',
  },
  {
    key: 'cultivatedArea',
    title: '面积（亩）',
    align: 'center',
    titleAlign: 'center',
  },
  {
    key: 'addr',
    title: '地址',
    titleAlign: 'center',
    render: (row) => {
      return h(NFlex, {
        justify: 'center'
      }, {
        default: () => [
          h('span', row.addr)
        ]
      })

    }
  },
  {
    key: 'username',
    title: '联系人',
    align: 'center',
    titleAlign: 'center',
    render: (row) => {
      return row.contacts.username
    }
  },
  {
    key: 'phone',
    title: '联系方式',
    align: 'center',
    titleAlign: 'center',
    render: (row) => {
      return row.contacts.phone
    }
  },
  {
    key: 'createTime',
    title: '调度时间',
    align: 'center',
    titleAlign: 'center',
  }
])

const loading = ref<boolean>(true)

function getData() {
  loading.value = true
  getDetails(route.params.dispatchId as string).then(res => {
    dispatchDetails.value = res.data
  }).finally(() => loading.value = false)
}

getData()

const back = () => router.push({name: 'recoveryDispatch_record'})

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
            <n-skeleton class="w-1/3" height="30px" />
            <n-skeleton class="w-1/3" height="30px" />
            <n-skeleton class="w-1/3" height="30px" />
          </n-flex>
          <n-flex :wrap="false" :size="150">
            <n-skeleton class="w-1/3" height="30px" />
            <n-skeleton class="w-1/3" height="30px" />
            <n-skeleton class="w-1/3" height="30px" />
          </n-flex>
          <n-flex :wrap="false" :size="150">
            <n-skeleton class="w-1/3" height="30px" />
            <n-skeleton class="w-1/3" height="30px" />
            <div class="w-1/3" />
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
          <span>调度信息</span>
        </n-flex>
      </template>
      <n-data-table
        :loading="loading"
        :columns="columns"
        :single-line="false"
        :data="dispatchDetails.children"
      />
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
