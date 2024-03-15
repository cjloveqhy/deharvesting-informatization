<script setup lang="ts">
import {ginnerySearch} from "@/api/ginnery";
import {NH6, NFlex, NThing} from "naive-ui";
import { useScroll } from '@vueuse/core'
import {GinneryPageFo, GinneryVo} from "@/store/api/ginnery";

const router = useRouter();

const goToPlotCreateDispatch = (id: string)=> {
  router.push({
    name: 'recoveryDispatchMap_create',
    params: {
      factoryId: id,
    },
  })
}

const loading = ref<boolean>(false)

const options = ref<GinneryVo[]>([])

const pageQuery = ref<GinneryPageFo>({
  page: 1,
  size: 10,
  factoryName: ''
})

const isLast = ref<boolean>(false)

function search(text: string) {
  loading.value = true
  pageQuery.value.factoryName = text
  options.value = []
  ginnerySearch(pageQuery.value).then(res => {
    options.value = res.data.records
    pageQuery.value.page++
    if (pageQuery.value.page > res.data.pages) {
      isLast.value = true
    }
  }).finally(() => loading.value = false)
}

search('')

const el = ref<HTMLElement | null>(null)
const { arrivedState } = useScroll(el, {behavior: 'smooth'})
const { bottom } = toRefs(arrivedState)

</script>

<template>
  <div class="box relative w-full h-87vh">
    <div class="absolute w-25% top-20px left-20px">
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
          <n-flex ref="el" vertical class="w-full h-200px max-h-250px overflow-y-scroll">
            <n-list clickable hoverable>
              <template v-for="item in options">
                <n-list-item>
                  <n-thing
                    :title="`轧花厂：${item.factoryName}`"
                    content-style="margin-top: 10px; padding-left: 10px"
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
                </n-list-item>
              </template>
            </n-list>
            <template v-if="loading && !isLast">
              <div class="flex justify-center"><span>正在加载中...</span></div>
            </template>
            <template v-if="isLast">
              <div class="flex justify-center"><span>到底啦，没有数据了</span></div>
            </template>
          </n-flex>
        </template>
        <template v-else>
          <n-empty :description="`暂无与${pageQuery.factoryName}轧花厂相关的调度信息`" class="py-20px"/>
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

</style>
