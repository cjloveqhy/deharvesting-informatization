type Atom = string | null

export function getLabel(options: any[], value: Atom) {
  return getFieldValue(options, 'value', value, 'label')
}

export function getOption(options: any[], keyField: Atom, keyValue: Atom, valueField: Atom) {
  const valid = [keyField, keyValue, valueField]
  if (options.length <= 0) return null
  for (let item of valid) {
    if (item === null || item === '') return null
  }
  return options.find(item => item[`${keyField}`] === keyValue)!
}

export function getFieldValue(options: any[], keyField: Atom, keyValue: Atom, valueField: Atom) {
  return getOption(options, keyField, keyValue, valueField)[`${valueField}`]
}
