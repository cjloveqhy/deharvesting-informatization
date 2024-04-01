<script lang="ts" setup>
import {LoginEnum, PasswordLoginForm, useUserApiStore} from '@/store/api/user';
import {useMessage} from 'naive-ui';
import {ResultEnum} from '@/enums/httpEnum';
import {LockClosedOutline, PersonOutline} from '@vicons/ionicons5';
import {PageEnum} from '@/enums/pageEnum';
import {websiteConfig} from '@/config/website.config';
import {useGlobSetting} from "@/hooks/setting";
import BasicForm from "@/views/login/details/basicForm.vue";
import {api} from "@/api/system/user";

const formRef = ref();
const message = useMessage();
const loading = ref(false);
const autoLogin = ref(true);
const LOGIN_NAME = PageEnum.BASE_LOGIN_NAME;
const globSetting = useGlobSetting();
const verifyCodeUrl = ref<string>(globSetting.apiUrl + globSetting.urlPrefix + api.getLoginVerifyImg)

const formInline = reactive({
  account: 'admin',
  password: '123456',
  code: '',
  isCaptcha: true,
});

const rules = {
  account: { required: true, message: '请输入用户名', trigger: 'blur' },
  password: { required: true, message: '请输入密码', trigger: 'blur' },
  code: { required: true, message: '请输入校验码', trigger: 'blur' },
};

const userStore = useUserApiStore();

const router = useRouter();
const route = useRoute();

function changeCode(){
  verifyCodeUrl.value = verifyCodeUrl.value + '?' + Math.random()
}

function toPath(name: string) {
  router.push({name: name})
}

const handleSubmit = (e) => {
  e.preventDefault();
  formRef.value.validate(async (errors) => {
    if (!errors) {
      const { account, password, code } = formInline;
      message.loading('登录中...');
      loading.value = true;

      const params: PasswordLoginForm = {
        account,
        password,
        code,
        type: LoginEnum.PASSWORD
      };

      try {
        const { data, status } = await userStore.login(params);
        message.destroyAll();
        if (status == ResultEnum.SUCCESS) {
          const toPath = decodeURIComponent((route.query?.redirect || '/') as string);
          message.success('登录成功，即将进入系统');
          if (route.name === LOGIN_NAME) {
            router.replace('/');
          } else router.replace(toPath);
        } else {
          message.info(data || '登录失败');
        }
      } catch(err) {
        changeCode()
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
      <n-form-item path="code">
        <n-flex :wrap="false">
          <n-input
            v-model:value="formInline.code"
            type="text"
            placeholder="请输入验证码"
          />
          <img :style="{height: '45px'}" @click="changeCode" :src="verifyCodeUrl">
        </n-flex>
      </n-form-item>
      <n-button type="primary" @click="handleSubmit" size="large" :loading="loading" block>
        登录
      </n-button>
      <div class="flex w-full" style="margin-top: 20px">
        <template v-if="websiteConfig.oauth2 && websiteConfig.oauth2.length > 0">
          <div class="flex-initial">
            <span>其它登录方式</span>
          </div>
          <template v-for="item in websiteConfig.oauth2">
            <div class="flex-initial mx-2">
              <a :href="item.path">
                <n-icon v-bind="item" :component="item.icon" />
              </a>
            </div>
          </template>
        </template>
        <n-flex justify="end" style="margin-left: auto">
          <n-button text tag="a" @click="toPath(PageEnum.REGISTER)">
            忘记密码
          </n-button>
          <n-button text tag="a" @click="toPath(PageEnum.FORGET_PASSWORD)">
            注册账号
          </n-button>
        </n-flex>
      </div>
    </n-form>
  </basic-form>
</template>
