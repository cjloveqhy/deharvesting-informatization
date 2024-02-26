import logoImage from '@/assets/images/logo.png';
import { LogoGithub, LogoFacebook } from '@vicons/ionicons5';

export const websiteConfig = Object.freeze({
  title: 'template',
  logo: logoImage,
  loginImage: logoImage,
  loginDesc: '中后台前端模板',
  oauth2: [
    {
      color: '#2d8cf0',
      size: 24,
      icon: LogoGithub,
      path: ''
    },
    {
      color: '#2d8cf0',
      size: 24,
      icon: LogoFacebook,
      path: ''
    },
  ]
});
