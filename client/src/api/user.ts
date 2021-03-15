import { AxiosPromise } from 'axios';
import request from '../utils/request';
import { PageParams, PageData } from '../utils/types';
import { Role } from './role';

export interface User {
  id: number;
  username: string;
  nickname: string;
  password?: string;
  role: Role | number;
}

export function getUserPage(params: PageParams): AxiosPromise<PageData<User>> {
  return request({
    url: '/user/page',
    method: 'GET',
    params,
  });
}

export function addUser(user: Partial<User>):AxiosPromise<boolean> {
  return request({
    url: "/user",
    method: "POST",
    data: user
  })
}