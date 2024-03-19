import Paragraph from '@ckeditor/ckeditor5-paragraph/src/paragraph'
import Essentials from '@ckeditor/ckeditor5-essentials/src/essentials'
import {
  Bold,
  Code,
  Italic,
  Strikethrough,
  Subscript,
  Superscript,
  Underline
} from '@ckeditor/ckeditor5-basic-styles/src/index'
import {Autoformat} from '@ckeditor/ckeditor5-autoformat/src/index'
import {Indent, IndentBlock} from '@ckeditor/ckeditor5-indent/src/index'
import {BlockQuote} from '@ckeditor/ckeditor5-block-quote/src/index'
import {
  FontBackgroundColor,
  FontColor,
  FontFamily,
  FontSize
} from '@ckeditor/ckeditor5-font/src/index'
import {Heading} from '@ckeditor/ckeditor5-heading/src/index'
import {GeneralHtmlSupport, HtmlComment} from '@ckeditor/ckeditor5-html-support/src/index'
import {PasteFromMarkdownExperimental} from '@ckeditor/ckeditor5-markdown-gfm/src/index'
import {Highlight} from '@ckeditor/ckeditor5-highlight/src/index'
import {HorizontalLine} from '@ckeditor/ckeditor5-horizontal-line/src/index'
import {
  Image,
  ImageCaption,
  ImageInsert,
  ImageResize,
  ImageStyle,
  ImageToolbar
} from '@ckeditor/ckeditor5-image/src/index'
import {AutoLink, Link, LinkImage} from '@ckeditor/ckeditor5-link/src/index'
import {SimpleUploadAdapter} from '@ckeditor/ckeditor5-upload/src/index'
import {FindAndReplace} from '@ckeditor/ckeditor5-find-and-replace/src/index'
import {ListProperties, TodoList} from '@ckeditor/ckeditor5-list/src/index'
import {PasteFromOffice} from '@ckeditor/ckeditor5-paste-from-office/src/index'
import {Clipboard} from '@ckeditor/ckeditor5-clipboard/src/index'
import {
  SpecialCharacters,
  SpecialCharactersArrows,
  SpecialCharactersCurrency,
  SpecialCharactersEssentials,
  SpecialCharactersLatin,
  SpecialCharactersMathematical,
  SpecialCharactersText,
} from '@ckeditor/ckeditor5-special-characters/src/index'
import {
  Table,
  TableCellProperties,
  TableProperties,
  TableToolbar,
} from '@ckeditor/ckeditor5-table/src/index'
import {Alignment} from '@ckeditor/ckeditor5-alignment/src/index'
import {RemoveFormat} from '@ckeditor/ckeditor5-remove-format/src/index'
// import {SourceEditing} from '@ckeditor/ckeditor5-source-editing/src/index'
import 'ckeditor5/build/translations/zh-cn.js'
import '@ckeditor/ckeditor5-indent/build/translations/zh-cn.js'
import '@ckeditor/ckeditor5-block-quote/build/translations/zh-cn.js'
import '@ckeditor/ckeditor5-font/build/translations/zh-cn.js'
import '@ckeditor/ckeditor5-heading/build/translations/zh-cn.js'
import '@ckeditor/ckeditor5-highlight/build/translations/zh-cn.js'
import '@ckeditor/ckeditor5-horizontal-line/build/translations/zh-cn.js'
import '@ckeditor/ckeditor5-image/build/translations/zh-cn.js'
import '@ckeditor/ckeditor5-link/build/translations/zh-cn.js'
import '@ckeditor/ckeditor5-find-and-replace/build/translations/zh-cn.js'
import '@ckeditor/ckeditor5-list/build/translations/zh-cn.js'
import '@ckeditor/ckeditor5-media-embed/build/translations/zh-cn.js'
import '@ckeditor/ckeditor5-special-characters/build/translations/zh-cn.js'
import '@ckeditor/ckeditor5-table/build/translations/zh-cn.js'
import '@ckeditor/ckeditor5-alignment/build/translations/zh-cn.js'
import '@ckeditor/ckeditor5-remove-format/build/translations/zh-cn.js'
import '@ckeditor/ckeditor5-basic-styles/build/translations/zh-cn.js'
import {EditorConfig} from "ckeditor5/src/core";
import {useProjectSettingStore} from "@/store/modules/projectSetting";
import {useUserApiStore} from "@/store/api/user";

const userStore = useUserApiStore();
const token = userStore.getToken();
const projectSettingStore = useProjectSettingStore()
const { tokenName } = projectSettingStore.getAxiosConfig

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
  // 逆工程得到的key，不可商用
  licenseKey: 'dG9rZW5fMTIzNDU2ZGVoYXJ2ZXN0aW5nX2luZm9ybWF0aXphdGlvbjpkZWhhcnZlc3Rpbmct',
  plugins: [
    GeneralHtmlSupport, // 通用HTML支持
    HtmlComment, // HTML注释元素
    // Markdown, // markdown支持
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
    Image, // 图片
    ImageInsert, // 插入图片
    ImageCaption, // 图片标题
    ImageResize, // 图片大小
    ImageStyle, // 图片样式
    ImageToolbar, // 图片工具支持
    LinkImage,  // 图片链接
    Link, // 超链接
    AutoLink, // 自动链接
    SimpleUploadAdapter, // 简单上传文件适配器
    FindAndReplace, // 查找替换
    ListProperties, // 有序无序列表
    TodoList, // 代办列表
    // MediaEmbed, // 插入媒体
    // AutoMediaEmbed, // 自动插入媒体
    PasteFromOffice, // office 剪切板支持
    PasteFromMarkdownExperimental, // markdown 剪切板支持
    Clipboard, // 纯文本剪切板支持
    SpecialCharacters, // 特殊字符
    SpecialCharactersArrows, // 箭头特殊字符
    SpecialCharactersCurrency, // 货币特殊字符
    SpecialCharactersLatin, // 拉丁特殊字符
    SpecialCharactersMathematical, // 数学特殊字符
    SpecialCharactersText, // 文本特殊字符
    SpecialCharactersEssentials, // 组合上面列出的插件
    Table, // 表格
    TableToolbar, // 表格工具栏
    TableProperties,
    TableCellProperties,
    Alignment, // 文本对齐
    RemoveFormat, // 删除格式
    // SourceEditing, // 源码
  ],
  toolbar: {
    items: [
      'undo', // 撤销
      'redo', // 重做
      'selectAll', // 全选
      'removeFormat', // 删除格式
      '|',
      'findAndReplace', // 查找替换
      'heading', // 标题
      '|',
      'fontFamily', // 字体
      'fontSize', // 字体字号
      'fontColor', // 字体颜色
      'fontBackgroundColor', //字体背景颜色
      '|',
      'link', // 超链接
      'insertImage', // 插入图片
      'insertTable', // 插入表格
      '|',
      'bold', // 加粗
      'italic', // 斜体
      'strikethrough', // 删除线
      'underline', //上划线
      '|',
      'subscript', //下标
      'superscript', // 上标
      'highlight', // 强调
      'specialCharacters', // 特殊字符
      '|',
      'alignment:left', // 左对齐
      'alignment:right', // 右对齐
      'alignment:center', // 居中对齐
      'alignment:justify', // 两端对齐
      '|',
      'code', //行内代码
      'blockQuote', // 块引用
      'horizontalLine', // 水平线
      // 'mediaEmbed', // 插入媒体
      '|',
      'numberedList', // 有序列表
      'bulletedList', // 无序列表
      'todolist', // 待办事项列表
      'outdent', // 减少缩进
      'indent', // 增加缩进
      // 'sourceEditing', // 源码
    ],
    shouldNotGroupWhenFull: true
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
  fontFamily: {
    supportAllValues: true
  },
  fontSize: {
    supportAllValues: false
  },
  image: {
    upload: {
      types: ['jpeg', 'bmp', 'gif', 'png', 'tiff', 'webp']
    },
    toolbar: [
      'toggleImageCaption',
      'imageTextAlternative',
      '|',
      'imageStyle:inline',
      'imageStyle:alignCenter',
      'imageStyle:wrapText',
      '|',
      'resizeImage',
    ],
    resizeOptions: [
      {
        name: 'resizeImage:default',
        value: null,
        label: '默认'
      },
      {
        name: 'resizeImage:25',
        value: '25',
        label: '25%'
      },
      {
        name: 'resizeImage:50',
        value: '50',
        label: '50%'
      },
      {
        name: 'resizeImage:75',
        value: '75',
        label: '75%'
      },
      {
        name: 'resizeImage:100',
        value: '100',
        label: '100%'
      }
    ],
  },
  simpleUpload: {
    uploadUrl: '',
    withCredentials: false,
    headers: {
      [tokenName as string]: token
    }
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
  },
  list: {
    properties: {
      styles: true, // 是否显示列表样式，默认为 true，false 时无法创建有序和无序列表
      startIndex: true, // 列表序号开始值
      reversed: true, // 是否反转有序列表序号
    }
  },
  table: {
    defaultHeadings: {
      rows: 1,
      columns: 1
    },
    contentToolbar: [
      'tableColumn', 'tableRow', 'mergeTableCells',
      'tableProperties', 'tableCellProperties'
    ]
  }
}
