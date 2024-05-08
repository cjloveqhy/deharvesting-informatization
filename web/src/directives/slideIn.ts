import {Directive, DirectiveBinding} from 'vue'
import {deepMerge} from "@/utils";
import {deepCopy} from "@/utils/copyUtil";

const map = new WeakMap<Element, Animation>()

const ob = new IntersectionObserver(entries => {
  for (const entry of entries) {
    if (entry.isIntersecting) {
      const animation = map.get(entry.target)
      animation && animation.play()
      ob.unobserve(entry.target)
    }
  }
})

function isBelowViewPort(el: Element): boolean {
  const rect = el.getBoundingClientRect()
  return rect.top - window.innerHeight > 0
}

interface SlideInDirectiveBinding extends DirectiveBinding {
  keyframes?: Keyframe[]
  firstScreenAnimation?: boolean
}

/**
 * 滑入指令，用于控制组件或标签进入视口后加载的动画
 */
const slideIn: Directive = {
  mounted(el: Element, binding: DirectiveBinding<SlideInDirectiveBinding>) {
    const { firstScreenAnimation = true } = binding.value || {}
    if (firstScreenAnimation && !isBelowViewPort(el)) {
      return;
    }
    const animation = el.animate(binding.value?.keyframes || [
      {
        transform: 'translateY(100px)',
        opacity: 0.5
      },
      {
        transform: 'translateY(0)',
        opacity: 1
      }
    ], deepMerge(
      {
        duration: 500,
        easing: 'ease-out',
        fill: 'forwards'
      }, binding.value ?
        deepCopy(binding.value, {keyframes: false})
        : {}
      )
    )
    animation.pause()
    map.set(el, animation)
    ob.observe(el)
  },
  unmounted(el: Element) {
    ob.unobserve(el)
  },
}

export default slideIn;
