import { AxiosPromise } from 'axios'
import request from '../utils/request'
import { Role } from './role'

export interface LoginData {
  username: string
  password: string
}
// 登录
export function login(data: LoginData): AxiosPromise<{token:string}> {
  return request({
    url: '/auth/login',
    method: 'POST',
    data,
  })
}

export interface MenuItem {
  fullPath: string
  title: string
  redirect?: string
  icon?: string
  children?: MenuItem[]
}

export interface UserInfo {
  username: string
  nickname: string
  role: Role
  avatar?: string
}
// 获取用户信息
export function getUserInfo(): AxiosPromise<UserInfo> {
  return request({
    url: '/userInfo',
    method: 'GET',
  })
}
