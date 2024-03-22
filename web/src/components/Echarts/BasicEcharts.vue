<script setup lang="ts">
import * as echarts from 'echarts'
import {BasicEchartsProps} from "@/components/Echarts/index";
import {deepCopy} from "@/utils/copyUtil";

const props = withDefaults(defineProps<BasicEchartsProps>(), {
  chartClass: 'w-full h-full',
  dataField: 'dataset.source',
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
    <template v-if="!!useSlots()['header-extra']" #header-extra>
      <slot name="header-extra" />
    </template>
    <div :id="echartsId" :class="props.chartClass" />
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
