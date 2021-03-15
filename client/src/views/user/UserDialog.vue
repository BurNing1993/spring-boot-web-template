<template>
  <el-dialog :title="title" v-model="dialogVisible">
    <el-form ref="formEl" :model="form" label-width="100px">
      <el-form-item
        label="username"
        prop="username"
        :rules="[
          { required: true, message: '请输入username', trigger: 'blur' },
          {
            min: 4,
            max: 16,
            message: '长度在 4 到 16 个字符',
            trigger: 'blur',
          },
        ]"
      >
        <el-input v-model="form.username"></el-input>
      </el-form-item>
      <el-form-item
        label="nickname"
        prop="nickname"
        :rules="[
          { required: true, message: '请输入nickname', trigger: 'blur' },
          {
            min: 4,
            max: 16,
            message: '长度在 4 到 16 个字符',
            trigger: 'blur',
          },
        ]"
      >
        <el-input v-model="form.nickname"></el-input>
      </el-form-item>
      <el-form-item
        label="role"
        prop="role"
        :rules="[{ required: true, message: '请输入role', trigger: 'blur' }]"
      >
        <el-select v-model="form.role" placeholder="请选择" class="w-full">
          <el-option
            v-for="role in roleList"
            :key="role.code"
            :value="role.code"
            :label="role.name"
          ></el-option>
        </el-select>
      </el-form-item>
    </el-form>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button :loading="loading" type="primary" @click="onSubmit">
          确 定
        </el-button>
      </span>
    </template>
  </el-dialog>
</template>

<script lang="ts">
import {
  computed,
  defineComponent,
  onMounted,
  reactive,
  ref,
  toRaw,
  toRefs,
} from "vue";
import { useStore } from "vuex";
import { User, addUser } from "../../api/user";
import { RootState } from "../../store";
import { OpType } from "../../utils/types";

interface State {
  dialogVisible: boolean;
  loading: boolean;
  op: OpType;
  form: Partial<User>;
}

const initForm = (): Partial<User> => ({
  username: "",
  nickname: "",
  role: 1,
});

export default defineComponent({
  name: "UserDialog",
  setup() {
    const store = useStore<RootState>();
    const formEl = ref<any>(null);
    const state = reactive<State>({
      dialogVisible: false,
      loading: false,
      op: "detail",
      form: initForm(),
    });
    const title = computed(() => {
      const text = "用户";
      if (state.op === "add") {
        return "添加" + text;
      }
      if (state.op === "update") {
        return "编辑" + text;
      }
      return text + "详情";
    });
    const show = (op: OpType = "detail", form?: any) => {
      state.dialogVisible = true;
      formEl.value?.resetFields();
      state.op = op;
      if (form) {
      }
    };
    onMounted(() => {
      store.dispatch("getRoleList");
    });
    const onSubmit = async () => {
      try {
        state.loading = true;
        const valid = await formEl.value?.validate();
        if (valid) {
          const { data } = await addUser(toRaw(state.form));
          if (data) {
            console.log(data);
          }
        }
      } catch (error) {
        console.error(error);
      } finally {
        state.loading = false;
      }
    };
    return {
      ...toRefs(state),
      show,
      title,
      formEl,
      onSubmit,
      roleList: computed(() => store.state.user.roleList),
    };
  },
});
</script>

<style scoped></style>
