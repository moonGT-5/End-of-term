<template>
  <div class="home">
    <h1>购物车</h1>
    <el-table
        :data="cartItems"
        stripe
        style="width: 100%"
        @selection-change="handleSelectionChange">
      <el-table-column
          type="selection"
          width="60">
      </el-table-column>
      <el-table-column
          label="商品"
          width="500">
        <template #default="{ row }">
          <div style="display: flex; align-items: center;">
            <img :src="row.picture" alt="产品图片" style="width: 50px; height: auto; margin-right: 10px;">
            <span>{{ row.name }}</span>
          </div>
        </template>
      </el-table-column>
      <el-table-column
          label="获取积分"
          prop="number">
      </el-table-column>
      <el-table-column
          label="单价"
          prop="price">
      </el-table-column>
      <el-table-column
          label="数量">
        <template #default="{ row }">
          <el-input-number v-model="row.quantity" :min="1" :max="row.maxQuantity"
                           @change="updateTotals"></el-input-number>
        </template>
      </el-table-column>
      <el-table-column
          label="小计">
        <template #default="{ row }">
          {{ row.price * row.quantity }}
        </template>
      </el-table-column>
      <el-table-column
          label="操作">
        <template #default="{ $index }">
          <el-button @click="removeItem($index)" type="text" size="small">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <div class="footer">
      <el-button @click="removeSelectedItems">删除所选</el-button>
      <span>商品总价: {{ totalPrice }}</span>
      <span>可获取积分: {{ totalPoints }}</span>
      <el-button type="primary" @click="update">立即购买</el-button>
    </div>
  </div>
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
    update() {  //返回
      const selectedItemsData = this.selectedItems.map(item => ({
        name: item.name,
        id: item.id,
        number: item.number
      }));
      axios.post('http://localhost:8000/api/update', selectedItemsData)
          .then(response => {
            console.log('Checkout response:', response);
            alert('购买成功!');
          })
          .catch(error => {
            console.error('购买失败:', error);
            alert('购买失败!');
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

.footer {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-top: 20px;
}
</style>