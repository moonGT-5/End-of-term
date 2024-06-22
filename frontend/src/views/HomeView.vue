<template>
  <el-container>
    <el-header>
      <h1>今日菜品</h1>
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

        <div style="width: 100%; max-width: 960px; margin: auto; margin-top: 20px;">
      <el-carousel :interval="4000" type="card" height="150px">
        <el-carousel-item v-for="item in carouselItems" :key="item.id" :style="{ backgroundImage: 'url(' + item.imageUrl + ')', backgroundSize: 'cover', backgroundPosition: 'center' }">
          <h3 class="medium">{{ item.title }}</h3>
        </el-carousel-item>
      </el-carousel>
    </div>

        <el-main>
          <el-table :data="cartItems" stripe style="width: 100%" @selection-change="handleSelectionChange"
            ref="cartTable">
            <el-table-column type="selection" width="60">
            </el-table-column>
            <el-table-column label="商品" width="300">
              <template #default="{ row }">
                <div style="display: flex; align-items: center;">
                  <img :src="row.image" alt="产品图片" style="width: 50px; height: auto; margin-right: 10px;">
                  <span>{{ row.name }}</span>
                </div>
              </template>
            </el-table-column>
            <el-table-column label="描述">
              <template #default="{ row }">
                <span>{{ row.info }}</span>
              </template>
            </el-table-column>
            <el-table-column label="单价" prop="price">
              <template #default="{ row }">
                <span class="bold-price">{{ row.price }}￥</span>
              </template>
            </el-table-column>
            <el-table-column label="剩余数量" prop="number">
            </el-table-column>
            <el-table-column label="操作">
              <template #default="{ row }">
                <el-button @click="evaluateItem(row.name, true)" type="primary">评价</el-button>
              </template>
            </el-table-column>
          </el-table>

          <div class="foot">
            <el-button @click="clearSelection">取消所选</el-button>
            <span>商品总价: {{ totalPrice }}￥</span>
            <el-button type="primary" @click="update">立即购买</el-button>
          </div>
        </el-main>

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
      cartItems: [],
      selectedItems: [],
      carouselItems: [
        { id: 1, imageUrl: 'http://localhost/images/1.jpg' },
        { id: 2, imageUrl: 'http://localhost/images/2.jpg' },
        { id: 3, imageUrl: 'http://localhost/images/3.jpg' },
        { id: 4, imageUrl: 'http://localhost/images/4.jpg' },
        { id: 5, imageUrl: 'http://localhost/images/5.jpg' },
        { id: 6, imageUrl: 'http://localhost/images/6.jpg' },
      ]
    };
  },
  computed: {
    totalPrice() {
      return this.selectedItems.reduce((total, item) => {
        return total + item.price;
      }, 0);
    }
  },
  methods: {
    handleSelectionChange(selectedItems) {
      this.selectedItems = selectedItems;
    },
    updateTotals() {
      this.$forceUpdate();
    },
    evaluateItem(name) {
      this.$router.push({ name: 'feedback', params: { name } });
    },
    clearSelection() {
      this.$refs.cartTable.clearSelection();
    },
    update() {
      const updatedItems = this.selectedItems.map(item => ({
        ...item,
        number: 1
      }));
      axios.post('http://localhost:8000/api/items/update', updatedItems)
        .then(response => {
          console.log('Update response:', response);
          alert('购买成功,祝您用餐愉快!');
          this.$router.push({ name: 'feedback' });
        })
        .catch(error => {
          console.error('购买失败:', error);
        });
    },


    fetchItems() {
      axios.get('http://localhost:8000/api/items')
        .then(response => {
          this.cartItems = response.data.map(item => ({
            ...item,
            name: item.name,
            image: item.image,
            number: item.number,
          }));
        })
        .catch(error => {
          console.error('获取数据失败:', error);
        });
    }

  },
  created() {
    this.fetchItems();
  },
  watch: {
    cartItems: {
      handler() {
        this.updateTotals();
      },
      deep: true
    },
    selectedItems: {
      handler() {
        this.updateTotals();
      },
      deep: true
    }
  }
};
</script>



<style>
h1 {
  text-align: center;
  color: #4CAF50;
  font-size: 2em;
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

.bold-price {
  font-weight: bolder;
}

img {
  border: 1px solid #ddd;
  border-radius: 4px;
  padding: 5px;
  box-shadow: 0 2px 2px 0 rgba(0, 0, 0, 0.2);
}

body {
  background-color: #fbf6ef;
}

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

.el-table td,
.el-table th.is-leaf,
.el-table--border,
.el-table--group {
  border-color: #fbf6ef;
  background-color: #fbf6ef;
  color: black;
}

.el-table tbody tr:hover>td {
  background-color: #ead7c3 !important
}

.el-carousel__item h3 {
  color: #475669;
  font-size: 14px;
  opacity: 0.75;
  line-height: 150px;
  margin: 0;
}

.el-carousel__item:nth-child(2n) {
  background-color: #99a9bf;
}

.el-carousel__item:nth-child(2n+1) {
  background-color: #d3dce6;
}
footer {
  position: relative;
  bottom: 0;
  width: 100%;
  text-align: center;
  padding: 1em 0;
  background-color: #fbf6ef;
}

footer a {
  text-decoration: none;
  color: #42b983;
}
</style>