import { Module } from 'vuex'
import { RootState } from '../'
import {
  login,
  getUserInfo,
  LoginData,
  MenuItem,
  UserInfo,
} from '../../api/login'
import { Role, getRoleList } from '../../api/role';

const LOCAL_TOKEN = '_tk'
const TOKEN_PREFIX = "Bearer ";

export interface State {
  token: string
  username: string
  nickname: string
  role: string
  menus: MenuItem[]
  avatar: string,
  roleList: Role[]
}

const initState: () => State = () => ({
  token: localStorage[LOCAL_TOKEN],
  username: '',
  nickname: '',
  role: '',
  menus: [],
  avatar: '',
  roleList: []
})

const user: Module<State, RootState> = {
  state: initState(),
  mutations: {
    SET_TOKEN: (state, payload: string) => {
      localStorage[LOCAL_TOKEN] = payload
      state.token = payload
    },
    SET_USER_INFO: (state, payload: UserInfo) => {
      const { username, role, nickname } = payload
      state.username = username
      state.nickname = nickname
      state.role = role
    },
    RESET: (state) => {
      localStorage.removeItem(LOCAL_TOKEN)
      state.token = ''
      state.username = ''
      state.nickname = ''
      state.role = ''
    },
    SET_ROLE_LIST: (state, roleList) => {
      state.roleList = roleList
    }
  },
  actions: {
    async login({ commit }, loginData: LoginData) {
      const { data: { token } } = await login(loginData)
      commit('SET_TOKEN', TOKEN_PREFIX + token)
    },
    logout({ commit }) {
      commit('RESET')
    },
    async getUserInfo({ commit }) {
      const { data } = await getUserInfo()
      commit('SET_USER_INFO', data)
    },
    async getRoleList({ commit,state }) {
      if (state.roleList.length>0) {
        return;
      }
      const { data } = await getRoleList()
      commit('SET_ROLE_LIST', data)
    }
  },
  getters: {
    token: (state) => state.token,
    role: (state) => state.role,
  },
}

export default user
