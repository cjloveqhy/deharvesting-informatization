import logoImage from '@/assets/images/logo.png';
import { LogoGithub, LogoFacebook } from '@vicons/ionicons5';

export const websiteConfig = Object.freeze({
  title: '脱采收信息化管理系统',
  logo: logoImage,
  loginImage: logoImage,
  loginDesc: '脱采收信息化管理系统',
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
