<script setup lang="ts">
import {ginnerySearch} from "@/api/ginnery";
import {NH6, NFlex, NThing} from "naive-ui";
import { useScroll } from '@vueuse/core'

const router = useRouter();

const goToPlotCreateDispatch = (id: string)=> {
  router.push({
    name: 'recoveryDispatchMap_create',
    query: {factoryId: id},
  })
}

const loading = ref<boolean>(false)

const options = ref<any[]>([])

const searchText = ref<string>('')

function search(text: string) {
  loading.value = true
  searchText.value = text
  options.value = []
  ginnerySearch(text).then(res => {
    options.value = res.data
  }).finally(() => loading.value = false)
}

search('')

/*const scrollRef = ref<HTMLElement | null>(null)
const { arrivedState } = useScroll(scrollRef, {behavior: 'smooth'})
const { left, right, top, bottom } = toRefs(arrivedState)
console.log(arrivedState)
watch(
  () => bottom.value,
  (val) => {
    console.log(val)
  }
)*/

</script>

<template>
  <div class="box relative w-full h-87vh">
    <div class="absolute w-35% top-20px left-20px">
      <n-select
        clearable
        filterable
        :show="false"
        @search="search"
        :loading="loading"
        :show-arrow="false"
        placeholder="请输入轧花厂名"
      />
      <n-card class="mt-10px" content-style="padding: 0">
        <template v-if="options.length > 0">
          <n-virtual-list
            ref="scrollRef"
            style="max-height: 240px"
            :item-size="42"
            :items="options"
            item-resizable
            key-field="id"
          >
            <template #default="{ item, index }">
              <n-thing
                :title="`轧花厂：${item.factoryName}`"
                content-style="margin-top: 10px; padding-left: 10px"
                class="cursor-pointer clickable-button p-20px"
                @click="goToPlotCreateDispatch(item.id)"
              >
                <n-flex vertical size="large">
                  <template v-for="option in item.children">
                    <n-h6 prefix="bar" style="--n-bar-color: #AAAAAA; --n-margin: 0">
                      <n-flex :wrap="false" class="w-full text-base">
                        <span class="truncate w-1/2">{{ option.plotName }}</span>
                        <span class="w-1/2">已收面积：{{ option.cultivatedArea }}亩</span>
                      </n-flex>
                    </n-h6>
                  </template>
                </n-flex>
              </n-thing>
            </template>
          </n-virtual-list>
        </template>
        <template v-else>
          <n-empty :description="`暂无与${searchText}轧花厂相关的调度信息`" class="py-20px"/>
        </template>
      </n-card>
    </div>
  </div>
</template>
<style lang="less" scoped>
.box{
  background: url("@/assets/images/recoveryDispatch/u2282.png") no-repeat;
  background-size: 100% 100%;
}

.clickable-button {
  transition: background-color 0.3s ease;
  background-color: white;
}

.clickable-button:hover {
  background-color: #f1f5f9;
}
</style>
