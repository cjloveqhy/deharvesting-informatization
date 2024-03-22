<script setup lang="ts">
import * as echarts from 'echarts'
import {BasicEchartsProps} from "@/components/Echarts/index";
import {deepCopy} from "@/utils/copyUtil";

const props = withDefaults(defineProps<BasicEchartsProps>(), {
  dataField: 'series[0].data'
})

const echartsId = "echarts_" + new Date().getDate().toString() + Math.random() * 100000000000000000

const chart = ref()

function init() {
  const dom = document.getElementById(echartsId)
  chart.value = echarts.init(dom, props.theme || '', props.opts || {renderer: 'svg'})
  props.option && initOption()

  chart.value.resize()
  window.addEventListener('resize', () => {
    chart.value.resize()
  })
}

const currentOption = ref<echarts.EChartsOption>()

function initOption() {
  currentOption.value = deepCopy(props.option)
  chart.value.setOption(currentOption.value)
}

function setData() {
  const expr = `(refVal, data) => { refVal.${props.dataField} = data }`
  eval(expr)(currentOption.value, props.data || [])
  chart.value.setOption(currentOption.value)
  chart.value.resize()
}

onMounted(() => {
  nextTick(() => {
    init()
    setData()
  })
})

watch(
  () => props.data,
  () => setData(),
  {
    deep: true
  }
)

</script>

<template>
  <n-card>
    <template v-if="!!useSlots().header" #header>
      <slot name="header" />
    </template>
    <template v-if="!!useSlots().headerExtra" #header-extra>
      <slot name="headerExtra" />
    </template>
      <div :id="echartsId" class="w-full h-full" />
    <slot name="default" :id="echartsId">
    </slot>
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
