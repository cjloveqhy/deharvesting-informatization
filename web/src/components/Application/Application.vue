<template>
  <n-dialog-provider>
    <n-notification-provider>
      <n-message-provider>
        <n-modal-provider>
          <slot name="default"></slot>
        </n-modal-provider>
      </n-message-provider>
    </n-notification-provider>
  </n-dialog-provider>
</template>

<script lang="ts">
  import { defineComponent } from 'vue';
  import { NDialogProvider, NNotificationProvider, NMessageProvider, NModalProvider } from 'naive-ui';

  export default defineComponent({
    name: 'Application',
    components: {
      NDialogProvider,
      NNotificationProvider,
      NMessageProvider,
      NModalProvider
    },
    setup() {
      return {};
    },
  });
</script>

<style lang="less" scoped>

.n-modal-body-wrapper {
  ::v-deep(.n-scrollbar-content .n-modal-scroll-content) {
    .n-card .n-modal {
      width: 100px;
    }
  }
}

</style>
