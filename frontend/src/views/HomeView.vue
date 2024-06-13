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
        <el-main>
          <el-table :data="cartItems" stripe style="width: 100%" @selection-change="handleSelectionChange"
            ref="cartTable">
            <el-table-column type="selection" width="60">
            </el-table-column>
            <el-table-column label="商品" width="500">
              <template #default="{ row }">
                <div style="display: flex; align-items: center;">
                  <img :src="row.picture" alt="产品图片" style="width: 50px; height: auto; margin-right: 10px;">
                  <span>{{ row.name }}</span>
                </div>
              </template>
            </el-table-column>
            <el-table-column label="单价" prop="price">
              <template #default="{ row }">
                <span class="bold-price">{{ row.price }}</span>
              </template>
            </el-table-column>
            <el-table-column label="剩余数量" prop="number">
            </el-table-column>
            <el-table-column label="操作">
              <template #default="{ row }">
                <el-button @click="evaluateItem(row.name, true)" type="text" size="small">评价</el-button>
              </template>
            </el-table-column>
          </el-table>

          <div class="foot">
            <el-button @click="clearSelection">取消所选</el-button>
            <span>商品总价: {{ totalPrice }}</span>
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
      selectedItems: []
    };
  },
  computed: {
    totalPrice() {
      return this.selectedItems.reduce((total, item) => {
        return total + (item.price * item.quantity);
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
    evaluateItem(productName, isLocked) {
      this.$router.push({ name: 'feedback', params: { productName, isLocked } });
    },
    clearSelection() {
      this.$refs.cartTable.clearSelection();
    },
    update() {
      const updatedItems = this.selectedItems.map(item => ({
        ...item,
        number: item.quantity
      }));
      axios.post('http://localhost:8000/api/update', updatedItems)
        .then(response => {
          console.log('Update response:', response);
          alert('购买成功,祝您用餐愉快!');
          this.$router.push({ name: 'feedback', params: { isLocked: false } });
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
            productName: item.name,
            image: item.picture,
            number: item.number, // 剩余数量
            quantity: 1,
            maxQuantity: item.maxQuantity
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