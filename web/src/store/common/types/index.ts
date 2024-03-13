export interface PageQuery {
  page: number
  size: number
  isMax?: boolean
}

export interface BasicOption {
  label: string
  value: string | number
  [key: string]: any
}
