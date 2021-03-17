import { AxiosPromise } from 'axios';
import request from '../utils/request';
import { PageParams, PageData } from '../utils/types';
import { Role, RoleType } from './role';

export interface User {
  id: number;
  username: string;
  nickname: string;
  password?: string;
  role: Role | RoleType;
}

export function getUserPage(params: PageParams): AxiosPromise<PageData<User>> {
  return request({
    url: '/user/page',
    method: 'GET',
    params,
  });
}

export function addUser(user: Partial<User>):AxiosPromise<string> {
  return request({
    url: "/user",
    method: "POST",
    data: user
  })
}

export function updateUser(user: Partial<User>):AxiosPromise<string> {
  return request({
    url: "/user",
    method: "PUT",
    data: user
  })
}

export function deleteUser(userId:number):AxiosPromise<string> {
  return request({
    url: `/user/${userId}`,
    method: "DELETE",
  })
}