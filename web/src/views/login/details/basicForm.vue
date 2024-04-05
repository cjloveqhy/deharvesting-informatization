<script setup lang="ts">
import {websiteConfig} from '@/config/website.config';</script>

<template>
  <div class="view-account">
    <div class="view-account-header"></div>
    <div class="view-account-container">
      <div class="view-account-top">
        <div class="view-account-top-logo">
          <img :src="websiteConfig.loginImage" alt="" />
        </div>
        <div class="view-account-top-desc">{{ websiteConfig.loginDesc }}</div>
      </div>
      <div class="view-account-form">
        <slot />
      </div>
    </div>
  </div>
</template>

<style lang="less" scoped>
.view-account {
  display: flex;
  flex-direction: column;
  height: 100vh;
  overflow: auto;

  &-container {
    flex: 1;
    padding: 32px 12px;
    max-width: 384px;
    min-width: 320px;
    margin: 0 auto;
  }

  &-top {
    padding: 32px 0;
    text-align: center;

    &-logo {
      display: flex;
      justify-content: center;

      img {
        width: auto;
        height: 100px;
      }

    }

    &-desc {
      padding-top: 20px;
      font-size: 20px;
      color: #808695;
    }
  }

}

@media (min-width: 768px) {
  .view-account {
    background-image: url('@/assets/images/login.svg');
    background-repeat: no-repeat;
    background-position: 50%;
    background-size: 100%;
  }

  .page-account-container {
    padding: 32px 0 24px 0;
  }
}
</style>
