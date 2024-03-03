export type DeepCopyOptions<T> = {
  [K in keyof T]?: DeepCopyOptions<T[K]> | boolean;
};

export type DeepCopyExclude<T, ExcludeKeys extends keyof T = never> = {
  [K in Exclude<keyof T, ExcludeKeys>]: DeepCopy<T[K]>;
};

export type DeepCopy<T> = T extends Array<infer U>
  ? Array<DeepCopy<U>>
  : T extends ReadonlyArray<infer U>
    ? ReadonlyArray<DeepCopy<U>>
    : T extends object
      ? DeepCopyObject<T>
      : T;

export type DeepCopyObject<T> = {
  [K in keyof T]: DeepCopy<T[K]>;
};

export function deepCopy<T>(obj: T, options: DeepCopyOptions<T> = {}): DeepCopyExclude<T, keyof T> | T {
  if (Array.isArray(obj)) {
    return obj.map((item) => deepCopy(item, options));
  } else if (typeof obj === 'object' && obj !== null) {
    return Object.keys(obj).reduce((result, key) => {
      if (options[key] === false) {
        // 跳过指定的属性
      } else {
        // 递归拷贝其他属性
        result[key] = deepCopy(obj[key], options[key]);
      }
      return result;
    }, {} as DeepCopyObject<T>);
  } else {
    // 基本类型直接返回
    return obj;
  }
}
