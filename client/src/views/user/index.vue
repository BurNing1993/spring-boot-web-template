<template>
  <div class="query">
    <div>
      <el-button type="primary" @click="onAdd">Add</el-button>
    </div>
    <el-form :inline="true" ref="queryForm" :model="query" class="query-form">
      <el-form-item prop="username">
        <el-input v-model="query.username" placeholder="Username"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="onSearch">Search</el-button>
        <el-button @click="reset">Reset</el-button>
      </el-form-item>
    </el-form>
  </div>
  <el-table v-loading="loading" :data="list" style="width: 100%">
    <el-table-column prop="nickname" label="nickname" show-overflow-tooltip />
    <el-table-column prop="username" label="username" show-overflow-tooltip />
    <el-table-column prop="role" label="role" show-overflow-tooltip />
  </el-table>
  <div class="page">
    <el-pagination
      @size-change="onSizeChange"
      @current-change="onCurrentChange"
      :current-page="current"
      :page-size="size"
      :page-sizes="[10, 20, 50, 100]"
      layout="total, sizes, prev, pager, next, jumper"
      :total="total"
    >
    </el-pagination>
  </div>
  <UserDialog ref="userDialog" />
</template>

<script lang="ts">
import { defineComponent, reactive, ref, toRaw } from "vue";
import { getUserPage } from "../../api/user";
import usePage, { Page } from "../..//hooks/usePage";
import { ElForm } from "element-plus";
import UserDialog from "./UserDialog.vue";
import App from "../../App.vue";

export default defineComponent({
  name: "User",
  components: {
    UserDialog,
    App,
  },
  setup() {
    const queryForm = ref<typeof ElForm>();
    const query = reactive({
      username: undefined,
      email: undefined,
    });
    const reset = () => {
      queryForm.value?.resetFields();
      onSearch();
    };
    const getPage = async (page: Page) => {
      const params = {
        ...page,
        ...toRaw(query),
      };
      const {
        data: { total, records },
      } = await getUserPage(params);
      return {
        total,
        list: records,
      };
    };
    const {
      loading,
      total,
      list,
      current,
      size,
      onSearch,
      onSizeChange,
      onCurrentChange,
    } = usePage(getPage);

    const userDialog = ref(null);
    const onAdd = (): void => {
      userDialog.value?.show();
    };
    return {
      loading,
      total,
      list,
      current,
      size,
      onSearch,
      onSizeChange,
      onCurrentChange,
      tagTypes: ["success", "info"],
      query,
      queryForm,
      reset,
      userDialog,
      onAdd,
    };
  },
});
</script>
