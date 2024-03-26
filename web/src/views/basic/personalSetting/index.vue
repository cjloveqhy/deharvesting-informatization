<script setup lang="ts">
import ShowOrEdit from "@/components/UserInfo/ShowOrEdit.vue";
import {useUserApiStore, UserInfo, SexOptions} from "@/store/api/user";
import {getLabel} from "@/utils/optionUtil";
import {getLoginUserInfo, updateUserInfo} from "@/api/system/login";

const userApiStore = useUserApiStore()

const userInfo = ref<UserInfo>(userApiStore.getUserInfo())

const rules = {
  account: {required: true, message: '请输入账户名', trigger: 'blur'},
};

const tab = ref()

const formRef = ref();

const loading = ref<boolean>(false)

function changeUpdateUserInfo() {
  formRef.value.validate((errors) => {
    if (!errors) {
      loading.value = true
      updateUserInfo(userInfo.value).then(() => {
        getLoginUserInfo().then(res => {
          userApiStore.refreshUserInfo(res.data)
          userInfo.value = res.data
          loading.value = false
        })
      })
    }
  })
}

</script>

<template>
  <n-form
    ref="formRef"
    label-placement="left"
    require-mark-placement="left"
    :model="userInfo"
  >
    <n-flex :wrap="false" :size="100" class="mt-6">
      <n-flex vertical class="w-1/3" align="center">
        <n-form-item path="photo">
          <template v-if="loading">
            <n-skeleton height="210px" circle/>
          </template>
          <template v-else>
            <n-avatar :size="210" round
                      :src="userInfo.photo && userInfo.photo !== '' ? userInfo.photo : null">
              <template v-if="!userInfo.photo">
                {{ userInfo.account }}
              </template>
            </n-avatar>
          </template>
        </n-form-item>
        <template v-if="loading">
          <n-skeleton width="100%" height="30px"/>
        </template>
        <template v-else>
        </template>
      </n-flex>
      <n-flex vertical class="w-1/3">
        <template v-if="loading">
          <n-flex vertical :size="40">
            <n-skeleton width="100%" height="30px"/>
            <n-skeleton width="100%" height="30px"/>
            <n-skeleton width="100%" height="30px"/>
          </n-flex>
        </template>
        <template v-else>
          <n-form-item path="account" label="账户名">
            <show-or-edit
              v-model:value="userInfo.account"
              input-placeholder="请输入账户名"
              @update:value="changeUpdateUserInfo"
            />
          </n-form-item>
          <n-form-item path="username" label="姓名">
            <show-or-edit
              v-model:value="userInfo.username"
              input-placeholder="请输入姓名"
              @update:value="changeUpdateUserInfo"
            />
          </n-form-item>
          <n-form-item path="sex" label="性别">
            <show-or-edit
              v-model:value="userInfo.sex"
              :options="SexOptions"
              type="select"
              @update:value="changeUpdateUserInfo"
            >
              {{ getLabel(SexOptions, userInfo.sex) }}
            </show-or-edit>
          </n-form-item>
          <n-form-item label="注册日期">
            {{ userInfo.createTime }}
          </n-form-item>
          <n-form-item path="phone" label="手机号">
            <show-or-edit v-model:value="userInfo.phone" :edit="false"/>
          </n-form-item>
          <n-form-item path="email" label="电子邮箱">
            <show-or-edit v-model:value="userInfo.email" :edit="false"/>
          </n-form-item>
        </template>
      </n-flex>
    </n-flex>
  </n-form>
</template>
