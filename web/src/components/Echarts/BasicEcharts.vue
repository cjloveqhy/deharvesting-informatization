<script setup lang="ts">
import * as echarts from 'echarts'
import {BasicEchartsProps} from "@/components/Echarts/index";
import {deepCopy} from "@/utils/copyUtil";

const props = withDefaults(defineProps<BasicEchartsProps>(), {
  chartClass: 'w-full h-full',
  dataField: 'dataset.source',
  loading: false,
  data: null,
  ignoreData: false,
  theme: ''
})

const emits = defineEmits(['addEvent'])

const echartsId = "echarts_" + new Date().getDate().toString() + Math.random() * 100000000000000000

let chart

function init() {
  const dom = document.getElementById(echartsId)
  chart = echarts.init(dom, props.theme, props.opts || {renderer: 'svg'})
  props.option && initOption()

  chart.resize()
  window.addEventListener('resize', () => {
    chart.resize()
  })
  emits('addEvent', chart)
}

const currentOption = ref<echarts.EChartsOption>()

function initOption() {
  currentOption.value = deepCopy(props.option)
  chart.setOption(currentOption.value)
}

function setData() {
  const expr = `(refVal, data) => { refVal.${props.dataField} = data }`
  eval(expr)(currentOption.value, props.data || [])
  chart.setOption(currentOption.value)
  chart.resize()
}

onMounted(() => {
  nextTick(() => {
    if (!props.loading && (props.ignoreData || (props.data && props.data.length >= 0))) {
      init()
      !props.ignoreData && setData()
    }
  })
})

watch(
  () => props.loading,
  (val) => {
    if (!val) {
      nextTick(() => {
        if (props.ignoreData || (props.data && props.data.length >= 0)) {
          init()
          !props.ignoreData && setData()
        }
      })
    }
  }
)

watch(
  () => props.data,
  () => !props.loading && !props.ignoreData && setData(),
  {
    deep: true
  }
)

onUnmounted(() => {
  chart && chart.dispose()
})

</script>

<template>
  <n-card>
    <template v-if="!!useSlots().header" #header>
      <slot name="header" />
    </template>
    <template v-if="!!useSlots()['header-extra']" #header-extra>
      <slot name="header-extra" />
    </template>
    <template v-if="props.loading">
      <slot name="loading" :loading="props.loading">
        <n-spin :show="true" v-bind="props.spinProps" class="w-full h-full" />
      </slot>
    </template>
    <template v-else>
      <template v-if="props.ignoreData || (props.data && props.data.length >= 0)">
        <div :id="echartsId" :class="props.chartClass" />
      </template>
      <template v-else>
        <slot name="empty">
          <n-empty
            :size="props.emptyProps?.size || 'large'"
            v-bind="props.emptyProps"
            class="w-full h-full justify-center" />
        </slot>
      </template>
    </template>
    <template v-if="!!useSlots().footer" #footer>
      <slot name="footer" />
    </template>
    <template v-if="!!useSlots().action" #action>
      <slot name="action" />
    </template>
    <template v-if="!!useSlots().cover" #cover>
      <slot name="cover" />
    </template>
  </n-card>
</template>
