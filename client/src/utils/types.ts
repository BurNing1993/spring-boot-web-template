export interface PageData<T> {
  current: number;
  size: number;
  total: number;
  records: T[];
}

export interface PageParams {
  current: number;
  size: number;
}

export type OpType = 'add' | 'update' | 'detail';