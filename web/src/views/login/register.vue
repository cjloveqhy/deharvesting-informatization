<script setup lang="ts">
import BasicForm from "@/views/login/details/basicForm.vue";
import { PersonOutline, LockClosedOutline } from '@vicons/ionicons5';
import {RegisterForm, useUserApiStore} from "@/store/api/user";
import {ResultEnum} from "@/enums/httpEnum";
import {useMessage} from "naive-ui";
import {PageEnum} from "@/enums/pageEnum";

const formRef = ref();
const loading = ref(false);
const message = useMessage();
const LOGIN_NAME = PageEnum.BASE_LOGIN_NAME;

const formInline = reactive({
  account: '',
  password: '',
  rePassword: ''
});

const rules = {
  account: { required: true, message: '请输入用户名', trigger: 'blur' },
  password: { required: true, message: '请输入密码', trigger: 'blur' },
  rePassword: { required: true, message: '请再次输入密码', trigger: 'blur' },
};

const userStore = useUserApiStore();

const router = useRouter();

const handleSubmit = (e) => {
  e.preventDefault();
  formRef.value.validate(async (errors) => {
    if (!errors) {
      const { account, password, rePassword } = formInline;
      message.loading('账户创建中...');
      loading.value = true;

      const params: RegisterForm = {
        account,
        password,
        rePassword,
      };

      try {
        const { data, status } = await userStore.register(params);
        message.destroyAll();
        if (status == ResultEnum.SUCCESS) {
          message.success('注册成功，即将跳转至登录页');
          router.push({name: LOGIN_NAME})
        } else {
          message.info(data || '注册失败');
        }
      } finally {
        loading.value = false;
      }
    }
  });
};

</script>

<template>
  <basic-form>
    <n-form
      ref="formRef"
      label-placement="left"
      size="large"
      :model="formInline"
      :rules="rules"
    >
      <n-form-item path="account">
        <n-input v-model:value="formInline.account" placeholder="请输入用户名">
          <template #prefix>
            <n-icon size="18" color="#808695">
              <PersonOutline />
            </n-icon>
          </template>
        </n-input>
      </n-form-item>
      <n-form-item path="password">
        <n-input
          v-model:value="formInline.password"
          type="password"
          showPasswordOn="click"
          placeholder="请输入密码"
        >
          <template #prefix>
            <n-icon size="18" color="#808695">
              <LockClosedOutline />
            </n-icon>
          </template>
        </n-input>
      </n-form-item>
      <n-form-item path="password">
        <n-input
          v-model:value="formInline.rePassword"
          type="password"
          showPasswordOn="click"
          placeholder="请再次输入密码"
        >
          <template #prefix>
            <n-icon size="18" color="#808695">
              <LockClosedOutline />
            </n-icon>
          </template>
        </n-input>
      </n-form-item>
    </n-form>
    <n-button type="primary" @click="handleSubmit" size="large" :loading="loading" block>
      注册
    </n-button>
  </basic-form>
</template>
