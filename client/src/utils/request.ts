import axios, { AxiosResponse } from 'axios'
import store from '../store'
import { ElMessage } from 'element-plus';
import router from '../router';

const request = axios.create({
  baseURL: '/api',
  // withCredentials: true,
  timeout: 5000,
})

const HEADER_KEY = 'Authorization'

request.interceptors.request.use(
  (config) => {
    const token = store.getters.token
    if (token) {
      config.headers[HEADER_KEY] = token
    }
    return config
  },
  (error) => {
    console.error(error)
    return Promise.reject(error)
  }
)

request.interceptors.response.use(
  (response) => {
    return response
  },
  (error) => {
    console.error('request error!')
    handleRequestError(error.response)
    return Promise.reject(error)
  }
)

export default request

/**
 * 错误处理
 * @param response 
 */
function handleRequestError(response: AxiosResponse<any>) {
  console.dir(response)
  const { status, statusText, data } = response
  const errorMsg = typeof data === 'string' ? data : data.message
  let msg = `${statusText}:${errorMsg || 'oops,出错了!'}`
  switch (status) {
    case 401:
    case 403:
      router.replace("/login")
      break;
    default:
      break;
  }
  ElMessage.error(msg)
}