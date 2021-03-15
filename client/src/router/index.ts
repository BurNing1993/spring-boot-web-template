import { createRouter, createWebHashHistory, RouteLocationNormalized } from 'vue-router'
import routes from './routes'
import store from '../store'

const router = createRouter({
  // https://vitejs.dev/config/#base
  history: createWebHashHistory(import.meta.env.BASE_URL),
  routes,
})

export default router

const ALLOW_LIST = ['/login']


router.beforeEach(async (to, from) => {
  if (ALLOW_LIST.includes(to.path)) {
    return true
  } else {
    const token = store.getters.token
    if (token) {
      const role = store.getters.role
      if (role) {
        return true
      } else {
        try {
          await store.dispatch('getUserInfo')
          return true
        } catch (error) {
          router.replace('/login')
          return false
        }
      }
    } else {
      router.replace('/login')
      return false
    }
  }
})

router.afterEach((to, from) => {
  const role = store.getters.role
  const ROLE_ADMIN = 'ADMIN'
  console.log(to.meta.auth);
  console.log(role);
  if (to.meta.auth && role !== ROLE_ADMIN) {
    router.replace('/login')
    return false;
  }
})
