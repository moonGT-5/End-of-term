<template>
  <el-container>
    <el-header>
      <h1>反馈单</h1>
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
        <el-form ref="form" :model="form" label-width="80px">
          <el-form-item label="商品名称">
            <el-input v-model="form.name"></el-input>
          </el-form-item>
          <el-form-item label="购买时间">
            <el-col :span="11">
              <el-date-picker type="date" placeholder="选择日期" v-model="form.date1" style="width: 100%;"></el-date-picker>
            </el-col>
            <el-col class="line" :span="2">-</el-col>
            <el-col :span="11">
              <el-time-picker placeholder="选择时间" v-model="form.date2" style="width: 100%;"></el-time-picker>
            </el-col>
          </el-form-item>
          <el-form-item label="反馈内容">
            <el-input type="textarea" v-model="form.feedback" placeholder="请输入您的反馈内容"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="onSubmit">立即创建</el-button>
            <el-button>取消</el-button>
          </el-form-item>
        </el-form>

        <el-footer class="footer">
          <router-link to="/about">© 2024 Altscherz</router-link>
        </el-footer>
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
        date1: '',
        date2: '',
        feedback: '',
      },
    }
  },
  created() {
    if (this.$route.params.productName) {
      this.form.name = this.$route.params.productName;
    }
  },
  methods: {
    onSubmit() {
      console.log('submit button clicked!');
      const purchaseDateTime = new Date(this.form.date1.getTime());
      purchaseDateTime.setHours(this.form.date2.getHours());
      purchaseDateTime.setMinutes(this.form.date2.getMinutes());
      purchaseDateTime.setSeconds(this.form.date2.getSeconds());
      const formData = {
        name: this.form.name,
        purchaseDate: purchaseDateTime.toISOString(),
        feedback: this.form.feedback
      };
      console.log('Sending form data:', formData);
      axios.post('http://localhost:8000/feedbacks', formData)
        .then(response => {
          console.log('Feedback submitted successfully', response);
        })
        .catch(error => {
          console.error('An error occurred while submitting feedback', error);
        });
    }
  }
}
</script>

<style scoped>
h1 {
  text-align: center;
}

.home {
  width: 96%;
  padding: 20px;
}

.foot {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-top: 20px;
}

.footer {
  position: relative;
  bottom: 0;
  width: 100%;
  text-align: center;
  background-color: #f8f8f8;
  padding: 10px;
  box-shadow: 0 -2px 4px rgba(0, 0, 0, 0.1);
  z-index: 1000;
}

.bold-price {
  font-weight: bold;
}
</style>