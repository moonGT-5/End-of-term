<template>
  <el-container>
    <el-header>
      <h1>注册会员</h1>
    </el-header>

    <el-container>
      <el-aside width="200px">
        <el-menu router>
          <el-menu-item index="/home">今日菜品</el-menu-item>
          <el-menu-item index="/register">注册会员</el-menu-item>
          <el-menu-item index="/search">菜品查询</el-menu-item>
          <el-menu-item index="/orders">我的订单</el-menu-item>
          <el-menu-item index="/feedback">系统留言</el-menu-item>
        </el-menu>
      </el-aside>

      <el-container>
        <el-main>
          <el-form ref="registerForm" :model="registerForm" label-width="100px">
            <el-form-item label="用户名">
              <el-input v-model="registerForm.name"></el-input>
            </el-form-item>
            <el-form-item label="密码">
              <el-input type="password" v-model="registerForm.password"></el-input>
            </el-form-item>
            <el-form-item label="确认密码">
              <el-input type="password" v-model="registerForm.confirmPassword"></el-input>
            </el-form-item>
            <el-form-item label="邮箱">
              <el-input v-model="registerForm.email"></el-input>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="submitForm">注册</el-button>
              <el-button @click="resetForm">重置</el-button>
            </el-form-item>
          </el-form>

          <el-footer class="footer">
            <router-link to="/about">© 2024 Altscherz</router-link>
          </el-footer>
        </el-main>
      </el-container>
    </el-container>
  </el-container>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      registerForm: {
        name: '',
        password: '',
        confirmPassword: '',
        email: '',
      },
    };
  },
  methods: {
    submitForm() {
  if (this.registerForm.password === this.registerForm.confirmPassword) {
    const formData = {
      name: this.registerForm.name,
      password: this.registerForm.password,
      email: this.registerForm.email,
    };
    console.log('发送的数据:', formData);
    axios.post('http://localhost:8000/api/user/insert', formData)
      .then(() => {
        alert('注册成功！');
        this.resetForm(); 
        this.$router.push({ name: 'login', params: { isLocked: false } });

      })
      .catch(error => {
        console.error('注册失败：', error);
        alert('注册失败，请稍后再试');
      });
  } else {
    alert('密码和确认密码不一致，请重新输入');
  }
},

    resetForm() {
      this.registerForm = {
        name: '',
        password: '',
        confirmPassword: '',
        email: '',
      };
    },
  },
};
</script>

<style scoped>
.footer {
  text-align: center;
  margin-top: 20px;
}
</style>