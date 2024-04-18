import {Plugin} from "vite";
// @ts-ignore
import chalk from 'chalk';
import { readdirSync, readFileSync } from 'fs';
import {resolve} from 'path';
import vm from 'vm';

function formatTime(timestamp) {
  const date = new Date(timestamp)
  const hours = date.getHours().toString().padStart(2, '0')
  const minutes = date.getMinutes().toString().padStart(2, '0')
  const seconds = date.getSeconds().toString().padStart(2, '0')
  return `${hours}:${minutes}:${seconds}`
}

function pathResolve(dir: string) {
  return resolve(process.cwd(), '.', dir)
}

interface Options {
  folder: string[]
}

export function asyncMenus(opt?: Options) {

  let menuPlugin: Plugin[] = []

  let menuFiles:string[] = []

  let fileUpdateCount = {}

  menuPlugin.push(
    {
      name: 'asyncMenu',
      apply: 'serve',
      configureServer() {
        if (opt?.folder) {
          for (let path of opt.folder) {
            const basePath = pathResolve(path)
            const files = readdirSync(basePath)
            for (let fileName of files) {
              menuFiles.push(`${basePath}\\${fileName}`)
              // const fileContent = readFileSync(menuFiles[menuFiles.length - 1], 'utf-8')
              // // 创建一个新的 VM 上下文
              // const context = vm.createContext({});
              // // 执行文件内容
              // vm.runInContext(fileContent, context);

            }
          }
        }
      },
      async handleHotUpdate(ctx) {
        const index = ctx.file.indexOf('src')
        const filePath = ctx.file.substring(index)
        const fileNameIndex = filePath.lastIndexOf('/')
        const fileName = filePath.substring(fileNameIndex)
        if (!Reflect.has(fileUpdateCount, fileName)) {
          fileUpdateCount = {}
          fileUpdateCount[fileName] = 1
        } else {
          fileUpdateCount[fileName] += 1
        }
        const reloadCount = fileUpdateCount[fileName]

        /*if (opt?.folder) {
          for (let path of opt.folder) {
            if (filePath.includes(path)) {
              break;
            }
          }
        }*/
        const time = chalk.hex('#6b6c6c')(formatTime(ctx.timestamp))
        const tag = chalk.hex('#07dcdc')('[vite]')
        const reloadFile = filePath + (reloadCount > 1 ? ` (x${reloadCount})` : '')
        const reload = chalk.green(`page reload ${reloadFile}`)
        const log = `${time} ${tag} ${reload}`
        console.log(log)
      }
    }
  )
  return menuPlugin
}
