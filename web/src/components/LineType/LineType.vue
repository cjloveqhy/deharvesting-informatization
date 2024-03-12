<script lang="ts" setup>
import {List, LayersSharp, LayersOutline} from '@vicons/ionicons5'
import {object} from "vue-types";
const props = defineProps({
  mark: {
    type: String,
    default: ""
  },
  type:{
    type: String,
    default: ""
  }
})
const lineTypesInfo = ref([
  {
    title: "农机类型",
    mark: "setting",
    types: [
      {
        label: "无人机",
        value: "settingUav"
      },
      {
        label: "采棉机",
        value: "settingCottonPicker"
      }
    ]
  },
  {
    title: "农机类型",
    mark: 'farm',
    types: [
      {
        label: "无人机",
        value: "farmUav"
      },
      {
        label: "采棉机",
        value: "farmCottonPicker"
      },
      {
        label: "数据终端",
        value: "farmDataTerminal"
      }
    ]
  },
  {
    title: "条款类型",
    mark: "contractTerms",
    types: [
      {
        label: "棉农",
        value: "contractTermsCottonFarmer"
      },
      {
        label: "飞手",
        value: "contractTermsFlyer"
      },
      {
        label: "司机",
        value: "contractTermsDriver"
      },
      {
        label: "轧花厂",
        value: "contractTermsCottonGinningMill"
      },
      {
        label: "检测单位",
        value: "contractTermsTestingUnit"
      }
    ]
  }
])
let filterLineTypesInfo = lineTypesInfo.value.filter(item => item.mark == props.mark);
let lineTypeTitle = filterLineTypesInfo[0].title
let lineTypeArr = filterLineTypesInfo[0].types

</script>

<template>
  <n-card :segmented="{content: true,footer: 'soft'}">
    <template #header>
      <n-icon color="#0175A8" :size="20">
        <List></List>
      </n-icon>
      {{ lineTypeTitle }}
    </template>
    <n-timeline :icon-size="20">
      <n-timeline-item v-for="(item, index) in lineTypeArr" :title="item.label" :key="index">
        <template #icon>
          <n-icon :color="props.type == item.value ? '#80D0F9' : ''">
            <LayersSharp/>
          </n-icon>
        </template>
      </n-timeline-item>
    </n-timeline>
  </n-card>
</template>
