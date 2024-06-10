<template>
  <el-container>
    <div class="home">
      <el-header>
        <h1>购物车</h1>
      </el-header>

      <el-main>
        <el-table :data="cartItems" stripe style="width: 100%" @selection-change="handleSelectionChange">
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
          <el-table-column label="获取积分" prop="number">
          </el-table-column>
          <el-table-column label="单价" prop="price">
          </el-table-column>
          <el-table-column label="数量">
            <template #default="{ row }">
              <el-input-number v-model="row.quantity" :min="1" :max="row.maxQuantity"
                @change="updateTotals"></el-input-number>
            </template>
          </el-table-column>
          <el-table-column label="小计">
            <template #default="{ row }">
              {{ row.price * row.quantity }}
            </template>
          </el-table-column>
          <el-table-column label="操作">
            <template #default="{ $index }">
              <el-button @click="removeItem($index)" type="text" size="small">删除</el-button>
            </template>
          </el-table-column>
        </el-table>

        <div class="foot">
          <el-button @click="removeSelectedItems">删除所选</el-button>
          <span>商品总价: {{ totalPrice }}</span>
          <span>可获取积分: {{ totalPoints }}</span>
          <el-button type="primary" @click="update">立即购买</el-button>
        </div>
      </el-main>
    </div>


    <el-footer class="footer">
      <router-link to="/about">© 2024 Altscherz</router-link>
    </el-footer>
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
    },
    totalPoints() {
      return this.selectedItems.reduce((total, item) => {
        return total + (item.number * item.quantity);
      }, 0);
    }
  },
  methods: {
    handleSelectionChange(selectedItems) {
      this.selectedItems = selectedItems;
    },
    updateTotals() {
      this.$forceUpdate(); // 强制更新视图
    },
    removeItem(index) {
      this.cartItems.splice(index, 1);
      this.updateTotals();
    },
    removeSelectedItems() {
      this.cartItems = this.cartItems.filter(item => !this.selectedItems.includes(item));
      this.updateTotals();
    },
    update() {
      const updatedItems = this.selectedItems.map(item => ({
        ...item,
        number: item.quantity
      }));
      axios.post('http://localhost:8000/api/update', updatedItems)
        .then(response => {
          console.log('Update response:', response);
          alert('购买成功!');
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
            productName: item.name,
            image: item.picture,
            points: item.number,
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

<style scoped>
h1 {
  text-align: left;
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
</style>