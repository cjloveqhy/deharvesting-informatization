<script setup lang="ts">
import BasicCard from "@/views/contractTerms/BasicCard.vue";
import {ContractTermsType} from "@/store/api/contractTerms";

</script>

<template>
  <basic-card mark="contractTermsTestingUnit" :type="ContractTermsType.TestingUnit" />
</template>

<style scoped>

::v-deep(.ck-content) {
  height: 650px
}

</style>
