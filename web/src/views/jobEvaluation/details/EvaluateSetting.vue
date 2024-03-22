<script lang="ts" setup>
import {LayersOutline} from '@vicons/ionicons5'
import LineType from "@/components/LineType/LineType.vue";

const props = defineProps<{settingType: string}>()

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
          <span class="text-14px text-#289CEB ml-10px">监测参数</span>
        </template>
        <n-flex vertical>
          <n-checkbox
            :checked="isSelectAll"
            label="全选"
            @update:checked="selectAll"
          />
          <n-checkbox-group :value="monitoredParameters" @update:value="handleUpdateValue">
            <n-flex align="center">
              <n-checkbox v-for="(item, index) in showCheckBoxOption" :key="index" :value="item.value" :label="item.label"/>
            </n-flex>
          </n-checkbox-group>
        </n-flex>
      </n-card>
      <n-card :segmented="{content: true,footer: 'soft'}" :bordered="false">
        <template #header>
          <n-icon color="#80D0F9" :size="20">
            <LayersOutline></LayersOutline>
          </n-icon>
          <span class="text-14px text-#289CEB ml-10px">参数设置</span>
        </template>
        <div v-for="(item, index) in showCheckBoxOption" :key="index">
          <n-h6 prefix="bar" align-text>
            {{ item.label }}
          </n-h6>
          <n-flex>
            <n-slider v-model:value="item.minToMaxValue" range :step="1" class="w-1/4 mr-20px mt-5px"
                      style="--n-fill-color-hover: #81D3F8;--n-fill-color:#81D3F8;--n-rail-height: 8px" />
            <n-input-number v-model:value="item.minToMaxValue[0]" size="small" class="w-10%" />
            <span>&emsp;-&emsp;</span>
            <n-input-number v-model:value="item.minToMaxValue[1]" size="small" class="w-10%" />
            <span>&emsp;{{ item.unit }}</span>
          </n-flex>
        </div>
      </n-card>
    </n-flex>
  </n-flex>
</template>

<style scoped lang="less">

::v-deep(.n-card > .n-card-header) {
  padding: 10px;
}
</style>
