<template>
  <aside
    :style="{
      width: isCollapse ? '64px' : '200px',
    }"
    class="h-screen bg-gray-900 transition-all duration-500"
  >
    <Logo />
    <el-scrollbar wrap-class="menu-scrollbar">
      <el-menu
        :default-active="active"
        :collapse="isCollapse"
        :background-color="backgroundColor"
        :text-color="textColor"
        :active-text-color="activeTextColor"
        mode="vertical"
        router
        class="border-none"
      >
        <el-menu-item index="/">
          <i class="el-icon-s-home"></i>
          <template #title>
            <span>首 页</span>
          </template>
        </el-menu-item>
        <el-menu-item index="/user">
          <i class="el-icon-user"></i>
          <template #title>
            <span>用 户</span>
          </template>
        </el-menu-item>
        <el-menu-item index="/role">
          <i class="el-icon-s-custom"></i>
          <template #title>
            <span>角 色</span>
          </template>
        </el-menu-item>
      </el-menu>
    </el-scrollbar>
  </aside>
</template>

<script lang="ts">
import { computed, defineComponent } from 'vue'
import { useStore } from 'vuex'
import Logo from './Logo.vue'
import { RootState } from '../../store'
import { useRoute } from 'vue-router'

export default defineComponent({
  name: 'Sider',
  components: {
    Logo,
  },
  setup() {
    const store = useStore<RootState>()
    const route = useRoute()
    return {
      isCollapse: computed(() => store.state.app.isCollapse),
      backgroundColor: '#111827',
      textColor: '#fff',
      activeTextColor: '#409EFF',
      active: route.path,
    }
  },
})
</script>


<style scoped>
.menu-scrollbar {
  height: calc(100vh - 64px);
  overflow-x: hidden !important;
}
</style>
