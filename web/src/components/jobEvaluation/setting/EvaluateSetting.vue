<script lang="ts" setup>
import {defineComponent} from 'vue'
import {List, LayersSharp, LayersOutline} from '@vicons/ionicons5'
import TypeLine from "@/components/LineType/LineType.vue";
import LineType from "@/components/LineType/LineType.vue";

const props = defineProps({
  settingType: {
    type: String,
    default: ""
  }
})

// 无人机的评价设置
let uavCheckBoxOptions = reactive([
  {
    label: "亩用量",
    value: 'muDose',
    unit: "升/亩",
    minToMaxValue: [0, 100],
  },
  {
    label: "最大飞行速度",
    value: "maxFlySpeed",
    unit: "米/秒",
    minToMaxValue: [0, 100],
  },
  {
    label: "相对作物高度",
    value: "cropHeight",
    unit: "米",
    minToMaxValue: [0, 100],
  },
  {
    label: "行间距",
    value: "lineSpace",
    unit: "米",
    minToMaxValue: [0, 100],
  }
])

// 采棉机的评价设置
let cottonPickerCheckBoxOptions = reactive([
  {
    label: "最大行驶速度",
    value: 'maxDriverSpeed',
    unit: "米/秒",
    minToMaxValue: [0, 100],
  },
])

// 判断是从哪个页面跳转过来的
let showCheckBoxOption = props.settingType == 'settingUav' ? uavCheckBoxOptions : cottonPickerCheckBoxOptions;

let isSelectAll = ref<boolean>(false)

let monitoredParameters = ref<Array<string>>([])

// 全选功能
const selectAll = (value) => {
  isSelectAll.value = !isSelectAll.value;
  if (value){
    monitoredParameters.value = showCheckBoxOption.map(item => item.value)
  } else {
    monitoredParameters = ref<Array<string>>([])
  }
}

// 复选功能
const handleUpdateValue = (value)=> {
  monitoredParameters.value = value
  isSelectAll.value = monitoredParameters.value.length === showCheckBoxOption.length
}

</script>

<template>
  <n-flex :wrap="false" class="w-full h-87vh">
    <LineType mark="setting" :type="settingType" class="w-15% h-full"></LineType>
    <n-flex vertical class="w-85%" :size="0">
      <n-card :segmented="{content: true,footer: 'soft'}" :bordered="false">
        <template #header>
          <n-icon color="#80D0F9" :size="20">
            <LayersOutline></LayersOutline>
          </n-icon>
          <span style="color: #289CEB;margin-left: 10px">监测参数</span>
        </template>
        <n-space item-style="display: flex;" vertical>
          <n-checkbox
            :checked="isSelectAll"
            label="全选"
            @update:checked="selectAll"
          />
          <n-checkbox-group :value="monitoredParameters" @update:value="handleUpdateValue">
            <n-space item-style="display: flex;" align="center">
              <n-checkbox v-for="(item, index) in showCheckBoxOption" :key="index" :value="item.value" :label="item.label"/>
            </n-space>
          </n-checkbox-group>
        </n-space>
      </n-card>
      <n-card :segmented="{content: true,footer: 'soft'}" :bordered="false">
        <template #header>
          <n-icon color="#80D0F9" :size="20">
            <LayersOutline></LayersOutline>
          </n-icon>
          <span style="color: #289CEB;margin-left: 10px">参数设置</span>
        </template>
        <div v-for="(item, index) in showCheckBoxOption" :key="index">
          <n-h6 prefix="bar" align-text>
            {{ item.label }}
          </n-h6>
          <div style="display: flex">
            <n-slider v-model:value="item.minToMaxValue" range :step="1" style="width: 25%; margin-right: 20px;margin-top: 5px;
                --n-fill-color-hover: #81D3F8;
                --n-fill-color:#81D3F8;--n-rail-height: 8px" />
            <n-input-number v-model:value="item.minToMaxValue[0]" size="small" style="width: 10%" />
            <span>&emsp;-&emsp;</span>
            <n-input-number v-model:value="item.minToMaxValue[1]" size="small" style="width: 10%" />
            <span>&emsp;{{ item.unit }}</span>
          </div>
        </div>
      </n-card>
    </n-flex>
  </n-flex>
</template>

<style scoped lang="less">
::v-deep .n-card.n-card--bordered {
  height: 87vh;
  margin: 0;
}

::v-deep .n-card > .n-card-header {
  padding: 10px;
  font-size: 14px;
}
</style>
