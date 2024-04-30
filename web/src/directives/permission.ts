import { Directive, DirectiveBinding } from 'vue';
import { usePermission } from '@/hooks/web/usePermission';

/**
 * 权限指令，用于控制不满足权限的组件不可用
 */
const permission: Directive = {
  mounted(el: HTMLButtonElement, binding: DirectiveBinding) {
    if (binding.value == undefined) return;
    const { action, effect } = binding.value;
    const { hasPermission } = usePermission();
    if (!hasPermission(action)) {
      if (effect == 'disabled') {
        el.disabled = true;
        el.style['disabled'] = 'disabled';
        el.classList.add('n-button--disabled');
      } else {
        el.remove();
      }
    }
  },
};

export default permission;
