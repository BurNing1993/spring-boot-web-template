import { AxiosPromise } from 'axios';
import request from '../utils/request';

export type RoleType =  "ADMIN"|"USER"
export interface Role {
  name: RoleType;
  desc: string;
  code: number;
}

export function getRoleList(): AxiosPromise<Role[]> {
  return request({
    url: '/role',
    method: 'GET',
  });
}
