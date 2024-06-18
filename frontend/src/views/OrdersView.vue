<template>
  <el-container>
    <el-header>
      <h1>我的订单</h1>
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
          <el-table :data="items" style="width: 100%">
            <el-table-column label="商品">
              <template v-slot="{ row }">
                <div style="display: flex; align-items: center;">
                  <el-image style="width: 50px; height: 50px" :src="row.image" fit="fill"></el-image>
                  <span>{{ row.name }}</span>
                </div>
              </template>
            </el-table-column>
            <el-table-column label="描述" prop="info"></el-table-column>
            <el-table-column label="单价" prop="price"></el-table-column>
            <el-table-column label="购买数量" prop="number"></el-table-column>
          </el-table>

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
  name: 'OrderList',
  data() {
    return {
      items: [],
    };
  },
  created() {
    this.fetchItems();
  },
  methods: {
    fetchItems() {
      axios.get('http://localhost:8000/api/items/purchase')
        .then(response => {
          this.items = response.data;
        })
        .catch(error => {
          console.error('Error fetching items:', error);
        });
    },
  },
};
</script>
