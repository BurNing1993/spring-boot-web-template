<template>
  <div class="query">
    <div>
      <el-button type="primary" @click="onAdd">Add</el-button>
    </div>
    <el-form :inline="true" ref="queryForm" :model="query" class="query-form">
      <el-form-item prop="username">
        <el-input v-model="query.username" placeholder="Username"></el-input>
      </el-form-item>
      <el-form-item prop="nickname">
        <el-input v-model="query.nickname" placeholder="Nickname"></el-input>
      </el-form-item>
      <el-form-item prop="role">
        <el-select v-model="query.role" placeholder="Role">
          <el-option
            v-for="role in roleList"
            :key="role.code"
            :label="role.desc"
            :value="role.name"
          >
          </el-option>
        </el-select>
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
    <el-table-column
      prop="role"
      label="role"
      :formatter="(r, c, val) => getRoleName(val)"
      show-overflow-tooltip
    />
    <el-table-column label="action">
      <template v-slot="{ row }">
        <el-button type="text" @click="onUpdate(row)">Update</el-button>
        <el-button type="text" @click="onDelete(row)">Delete</el-button>
      </template>
    </el-table-column>
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
  <UserDialog ref="userDialog" @get-list="onSearch" />
</template>

<script lang="ts">
import {
  computed,
  defineComponent,
  onMounted,
  reactive,
  ref,
  toRaw,
} from "vue";
import { deleteUser, getUserPage, User } from "../../api/user";
import usePage, { Page } from "../..//hooks/usePage";
import { ElForm, ElMessage, ElMessageBox } from "element-plus";
import UserDialog from "./UserDialog.vue";
import App from "../../App.vue";
import { useStore } from "vuex";
import { RootState } from "../../store";

export default defineComponent({
  name: "User",
  components: {
    UserDialog,
    App,
  },
  setup() {
    const store = useStore<RootState>();
    const roleList = computed(() => store.state.user.roleList);
    const getRoleName = (name: string) =>
      roleList.value.find((r) => r.name === name)?.desc;
    onMounted(() => {
      store.dispatch("getRoleList");
    });
    const queryForm = ref<typeof ElForm>();
    const query = reactive<Partial<User>>({
      username: undefined,
      nickname: undefined,
      role: undefined,
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
      getData,
      onSearch,
      onSizeChange,
      onCurrentChange,
    } = usePage(getPage);

    const userDialog = ref(null);
    const onAdd = () => {
      userDialog.value?.show("add");
    };
    const onUpdate = (row: unknown) => {
      userDialog.value?.show("update", row);
    };
    const onDelete = async ({ id, nickname }: User) => {
      await ElMessageBox.confirm(`确认删除${nickname}用户?`, "删除", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      });
      const { data } = await deleteUser(id);
      ElMessage.success(data);
      getData();
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
      onUpdate,
      onDelete,
      roleList,
      getRoleName,
    };
  },
});
</script>
