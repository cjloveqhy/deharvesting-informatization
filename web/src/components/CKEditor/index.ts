import Paragraph from '@ckeditor/ckeditor5-paragraph/src/paragraph'
import Essentials from '@ckeditor/ckeditor5-essentials/src/essentials'
import {
  Bold,
  Italic,
  Strikethrough,
  Underline,
  Subscript,
  Superscript,
  Code
} from '@ckeditor/ckeditor5-basic-styles/src/index'
import {Autosave} from '@ckeditor/ckeditor5-autosave/src/index'
import {Autoformat} from '@ckeditor/ckeditor5-autoformat/src/index'
import {Indent, IndentBlock} from '@ckeditor/ckeditor5-indent/src/index'
import {BlockQuote} from '@ckeditor/ckeditor5-block-quote/src/index'
import {FontFamily, FontColor, FontSize, FontBackgroundColor} from '@ckeditor/ckeditor5-font/src/index'
import {Heading} from '@ckeditor/ckeditor5-heading/src/index'
import {GeneralHtmlSupport, HtmlComment} from '@ckeditor/ckeditor5-html-support/src/index'
import {Markdown} from '@ckeditor/ckeditor5-markdown-gfm/src/index'
import {Highlight} from '@ckeditor/ckeditor5-highlight/src/index'
import {HorizontalLine} from '@ckeditor/ckeditor5-horizontal-line/src/index'
import {Image, ImageInsert, ImageCaption, ImageResize, ImageStyle, ImageToolbar} from '@ckeditor/ckeditor5-image/src/index'
import {LinkImage} from '@ckeditor/ckeditor5-link/src/index'
import 'ckeditor5/build/translations/zh-cn.js'
import '@ckeditor/ckeditor5-indent/build/translations/zh-cn.js'
import '@ckeditor/ckeditor5-block-quote/build/translations/zh-cn.js'
import '@ckeditor/ckeditor5-font/build/translations/zh-cn.js'
import '@ckeditor/ckeditor5-heading/build/translations/zh-cn.js'
import '@ckeditor/ckeditor5-highlight/build/translations/zh-cn.js'
import '@ckeditor/ckeditor5-horizontal-line/build/translations/zh-cn.js'
import '@ckeditor/ckeditor5-image/build/translations/zh-cn.js'
import '@ckeditor/ckeditor5-link/build/translations/zh-cn.js'
import '@ckeditor/ckeditor5-basic-styles/build/translations/zh-cn.js'
import {EditorConfig} from "ckeditor5/src/core";

export const CKEditorConfig: EditorConfig = {
  language: 'zh-cn',
  ui: { // 此配置与licenseKey配合使用，仅去除徽标的作用，无其他效果
    poweredBy: {
      position: 'border',
      side: 'left',
      verticalOffset: 0,
      horizontalOffset: 0,
      label: null,
      forceVisible: false
    },
  },
  autosave: {
    save: (_) => {
      return new Promise((_, reject) => {
        reject('callback method is not implemented, please implement it')
      })
    },
    waitingTime: 5000
  },
  htmlSupport: {
    allow: [
      {
        name: /.*/,
        attributes: true,
        classes: true,
        styles: true
      }
    ]
  },
  image: {
    toolbar: [
      'toggleImageCaption',
      '|',
      'imageStyle:inline',
      'imageStyle:side',
      '|',
      'imageStyle:alignBlockLeft',
      'imageStyle:alignCenter',
      'imageStyle:alignBlockRight',
      '|',
      'imageTextAlternative'
    ]
  },
  // 逆工程得到的key，不可商用
  licenseKey: 'dG9rZW5fMTIzNDU2ZGVoYXJ2ZXN0aW5nX2luZm9ybWF0aXphdGlvbjpkZWhhcnZlc3Rpbmct',
  plugins: [
    GeneralHtmlSupport, // 通用HTML支持
    HtmlComment, // HTML注释元素
    Markdown, // markdown支持
    Paragraph, // 段落
    Essentials, // 基础
    Bold, // 加粗
    Italic, // 斜体
    Strikethrough, // 删除线
    Underline, //上划线
    Subscript, //  下标
    Superscript, // 上标
    Code, // 行内代码
    // Autosave, // 自动保存
    Autoformat, // 自动格式化
    Indent, // 增加缩进
    IndentBlock, // 减少缩进
    BlockQuote, // 块引用
    FontFamily, // 字体
    FontSize, // 字体字号
    FontColor, // 字体颜色
    FontBackgroundColor, //字体背景颜色
    Heading, // 标题
    Highlight, // 强调
    HorizontalLine, // 水平线
    Image,
    ImageInsert,
    ImageCaption,
    ImageResize,
    ImageStyle,
    ImageToolbar,
    LinkImage,
  ],
  toolbar: {
    items: [
      'undo', // 撤销
      'redo', // 重做
      'selectAll', // 全选
      '|',
      'heading', // 标题
      '|',
      'fontFamily', // 字体
      'fontSize', // 字体字号
      'fontColor', // 字体颜色
      'fontBackgroundColor', //字体背景颜色
      '|',
      'bold', // 加粗
      'italic', // 斜体
      'strikethrough', // 删除线
      'underline', //上划线
      'subscript', //下标
      'superscript', // 上标
      'highlight', // 强调
      'outdent', // 减少缩进
      'indent', // 增加缩进
      '|',
      'code', //行内代码
      'blockQuote', // 块引用
      'horizontalLine', // 水平线
      '|',
      'insertImage'
    ],
    shouldNotGroupWhenFull: true
  },
  heading: {
    options: [
      { model: 'paragraph', title: 'Paragraph', class: 'ck-heading_paragraph' },
      { model: 'heading1', view: 'h1', title: 'Heading 1', class: 'ck-heading_heading1' },
      { model: 'heading2', view: 'h2', title: 'Heading 2', class: 'ck-heading_heading2' },
      { model: 'heading3', view: 'h3', title: 'Heading 3', class: 'ck-heading_heading3' },
      { model: 'heading4', view: 'h4', title: 'Heading 4', class: 'ck-heading_heading4' },
      { model: 'heading5', view: 'h5', title: 'Heading 5', class: 'ck-heading_heading5' },
      { model: 'heading6', view: 'h6', title: 'Heading 6', class: 'ck-heading_heading6' }
    ]
  }
}
