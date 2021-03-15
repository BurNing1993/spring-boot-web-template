import { AxiosPromise } from 'axios';
import request from '../utils/request';

export interface Role {
  name: string;
  desc: string;
  code: number;
}

export function getRoleList(): AxiosPromise<Role[]> {
  return request({
    url: '/role',
    method: 'GET',
  });
}
