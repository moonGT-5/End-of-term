<template>
  <el-container>
    <el-header>
      <h1>系统留言</h1>
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
          <el-form ref="form" :model="form" label-width="80px">
            <el-form-item label="商品名称">
              <el-input v-model="form.name"></el-input>
            </el-form-item>
            <el-form-item label="商品评分">
              <el-rate v-model="form.rating"></el-rate>
            </el-form-item>

            <el-form-item label="留言内容">
              <el-input type="textarea" v-model="form.feedback" placeholder="请输入您的留言内容"></el-input>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="onSubmit">立即创建</el-button>
              <el-button @click="onCancel">取消</el-button>
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
      form: {
        name: '',
        feedback: '',
        rating: 1,
      },
    }
  },
  created() {
    if (this.$route.params.name) {
      this.form.name = this.$route.params.name;
    }
  },
  methods: {
    onCancel() {
      this.$router.go(-1);
    },
    onSubmit() {
      let currentUtcTime = new Date();
      let offset = 8;
      let localTime = new Date(currentUtcTime.getTime() + offset * 3600 * 1000);
      const formData = {
        name: this.form.name,
        purchaseDate: localTime.toISOString(),
        feedback: this.form.feedback,
        rating: this.form.rating,
      };

      axios.post('http://localhost:8000/feedbacks', formData)
        .then(response => {
          console.log('成功', response);
        })
        .catch(error => {
          console.error('失败', error);
        });
      this.$router.go(-1);
    }
  }
}
</script>

<style scoped>
.el-aside {
  background-color: #dce0d9; 
  color: black;
}

.el-menu {
  background-color: transparent;
}

.el-menu-item:focus, .el-menu-item:hover {
  background-color: #409EFF;
}

.el-main {
  padding: 20px;
}

/* 表单样式 */
.el-form {
  margin: 20px auto;
  max-width: 600px;
}

.el-form-item {
  margin-bottom: 24px;
}

.el-input {
  border-radius: 4px;
}

.el-button {
  margin-right: 10px;
}

/* 页脚样式 */
.footer {
  text-align: center;
  padding: 15px 0;
  margin-top: 30px;
  font-size: 14px;
  color: #999;
}

</style>