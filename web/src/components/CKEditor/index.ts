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
import 'ckeditor5/build/translations/zh-cn.js'
import '@ckeditor/ckeditor5-basic-styles/build/translations/zh-cn.js'
import {EditorConfig} from "ckeditor5/src/core";

export const CKEditorConfig: EditorConfig = {
  language: 'zh-cn',
  plugins: [
    Paragraph, // 段落插件
    Essentials, // 基础插件
    Bold, // 加粗
    Italic, // 斜体
    Strikethrough, // 删除线
    Underline, //上划线
    Subscript, //下标
    Superscript, // 上标
    Code //行内代码
  ],
  toolbar: [
    'undo', // 撤销
    'redo', // 重做
    'selectAll', // 全选
    '|',
    'bold', // 加粗
    'italic', // 斜体
    'strikethrough', // 删除线
    'underline', //上划线
    'subscript', //下标
    'superscript', // 上标
    'code', //行内代码
    '|',
  ],
}
