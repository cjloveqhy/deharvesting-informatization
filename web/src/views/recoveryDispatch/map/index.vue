<script setup lang="ts">
const router = useRouter();
const goToDispatchDetail = ()=> {
  router.push({
    path: '/recoveryDispatch/record/details'
  })
}
</script>

<template>
  <div class="mapDispatchBox">
    <div class="mapDispatch_detail_search">
      <n-input placeholder="请输入轧花厂名称"></n-input>
      <img src="../../../assets/images/recoveryDispatch/u2291.png">
    </div>
    <div class="mapDispatch_detail_text">
      <n-list hoverable clickable>
        <n-list-item @click="goToDispatchDetail">
          <n-thing title="轧花厂：XXX轧花厂" content-style="margin-top: 10px;">
            <n-h6 prefix="bar" style="--n-bar-color: #AAAAAA">
              <span style="margin-right: 50px">新农棉1号</span>
              <span>已收面积：258.33亩</span>
            </n-h6>
          </n-thing>
          <n-thing title="轧花厂：XXX轧花厂" content-style="margin-top: 10px;">
            <n-h6 prefix="bar" style="--n-bar-color: #AAAAAA">
              <span style="margin-right: 50px">新农棉1号</span>
              <span>已收面积：258.33亩</span>
            </n-h6>
          </n-thing>
        </n-list-item>
        <n-list-item>
          <n-thing title="轧花厂：XXX轧花厂" content-style="margin-top: 10px;">
            <n-h6 prefix="bar" style="--n-bar-color: #AAAAAA">
              <span style="margin-right: 50px">新农棉1号</span>
              <span>已收面积：258.33亩</span>
            </n-h6>
          </n-thing>
        </n-list-item>
      </n-list>
    </div>
  </div>
</template>
<style lang="less" scoped>
.mapDispatchBox{
  width: 100%;
  height: 90vh;
  background: url("../../../assets/images/recoveryDispatch/u2282.png") no-repeat;
  background-size: 100% 100%;
  position: relative;
  .mapDispatch_detail_search{
    display: flex;
    position: absolute;
    margin: 20px;
    .n-input{
      width: 350px;
      height: 40px;
      opacity: 0.8;
    }
    img{
      width: 30px;
      height: 30px;
      transform: translate(-40px, 6px);
    }
  }
  .mapDispatch_detail_text{
    position: absolute;
    top: 50px;
    width: 350px;
    height: 400px;
    margin: 20px;
    opacity: 0.8;
    .n-thing .n-thing-main .n-thing-main__content:not(:first-child) {
      display: flex;
    }
  }
}
</style>
