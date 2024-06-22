<template>
  <el-container>
    <el-header>
      <h1>菜品查询</h1>
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
          <div>
            <el-input placeholder="请输入名称或ID" v-model="searchQuery"></el-input>
            <el-button type="primary" icon="el-icon-search" @click="searchItem">搜索</el-button>
          </div>

          <div v-if="item">
            <h2>{{ item.name }}</h2>
            <p>编号：{{ item.id }}</p>
            <p>菜品介绍：{{ item.info }}</p>
            <p>剩余数量：{{ item.number }}</p>
            <p>单价：{{ item.price }}￥</p>
            <img :src="item.image" alt="菜品图片" />
          </div>

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
      searchQuery: '',
      item: null,
    };
  },
  methods: {
    searchItem() {
      const isIdSearch = /^\d+$/.test(this.searchQuery);
      const baseUrl = 'http://localhost:8000/api/items/';
      const searchUrl = isIdSearch ? `${baseUrl}${this.searchQuery}` : `${baseUrl}${encodeURIComponent(this.searchQuery)}`;

      axios.get(searchUrl)
        .then(response => {
          this.item = response.data;
        })
        .catch(error => {
          console.error("搜索菜品信息失败：", error);
          this.item = null;
        });
    }
  }
};
</script>

<style scoped>
/* 全局设置 */
body {
  font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
}

.el-input {
  margin: 20px 0;
  width: 70%;
}

.el-button {
  margin: 20px 0;
}

div[v-if="item"] {
  background-color: #f9f9f9;
  padding: 20px;
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0,0,0,0.1);
  margin-top: 20px;
}

h2 {
  color: #333;
}

p {
  color: #666;
  margin: 10px 0;
}

img {
  width: 100%;
  max-width: 400px;
  margin-top: 20px;
  border-radius: 4px;
}

.footer {
  text-align: center;
  padding: 20px 0;
  font-size: 14px;
  color: #999;
}

</style>
