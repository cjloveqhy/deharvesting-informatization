import {App, Directive} from 'vue';

export interface IDirectivesType {
  default: Directive
}

const directives = import.meta.glob<IDirectivesType>('@/directives/*.ts', { eager: true });

/**
 * 注册全局自定义指令
 * @param app
 */
export function setupDirectives(app: App) {
  for (let key in directives) {
    const fileName = key.split('/').pop()!!.split('.')[0]
    app.directive(fileName, directives[key].default)
  }
}
