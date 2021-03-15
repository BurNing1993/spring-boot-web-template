<template>
  <el-table v-loading="loading" :data="list" style="width: 100%">
    <el-table-column prop="code" label="code" show-overflow-tooltip />
    <el-table-column prop="name" label="name" show-overflow-tooltip />
    <el-table-column prop="desc" label="desc" show-overflow-tooltip />
  </el-table>
</template>

<script lang="ts">
import { defineComponent, onMounted, reactive, toRefs } from "vue";
import { getRoleList, Role } from "../../api/role";

export default defineComponent({
  name: "Role",
  setup() {
    const state = reactive({
      list: [] as Role[],
      loading: false,
    });
    const getList = async () => {
      const { data } = await getRoleList();
      state.list = reactive(data);
    };
    onMounted(getList);
    return {
      ...toRefs(state),
    };
  },
});
</script>

<style scoped></style>
