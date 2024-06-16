<template>
  <div>
    <h1>Product List</h1>
    <input v-model="name" @input="fetchProducts" placeholder="Filter by name" />
    <select v-model="selectedCategory" @change="fetchProducts">
      <option value="">All Categories</option>
      <option v-for="category in categories" :key="category.id" :value="category.id">{{ category.name }}</option>
    </select>
    <ul>
      <li v-for="product in products" :key="product.id">
        <h3>{{ product.name }}</h3>
        <p>{{ product.description }}</p>
        <p>{{ product.price }}</p>
      </li>
    </ul>
  </div>
</template>

<script lang="ts">
import { defineComponent } from 'vue';
import axios from 'axios';

interface Product {
  id: number;
  name: string;
  description: string;
  price: number;
  categories: Category[];
}

interface Category {
  id: number;
  name: string;
}

export default defineComponent({
  data() {
    return {
      products: [] as Product[],
      categories: [] as Category[],
      name: '',
      selectedCategory: '',
      socket: null as WebSocket | null,
    };
  },
  methods: {
    fetchProducts() {
      axios.get('http://localhost:8082/api/products', {
        params: {
          name: this.name,
          category: this.selectedCategory,
        },
      })
        .then(response => {
          this.products = response.data.filter((product : Product) => {
            const matchesName = this.name ? product.name.toLowerCase().includes(this.name.toLowerCase()) : true;
            const matchesCategory = this.selectedCategory ? product.categories.some(cat => cat.id == parseInt(this.selectedCategory)) : true;
            return matchesName && matchesCategory;
          });
        });
    },
    fetchCategories() {
      axios.get('http://localhost:8082/api/categories')
        .then(response => {
          this.categories = response.data;
        });
    },
    connectWebSocket() {
      this.socket = new WebSocket('ws://localhost:8082/ws');

      this.socket.onmessage = (event) => {
        console.log('WebSocket message received:', event.data);
        if (event.data === 'Product changed') {
          this.fetchProducts();
        }
      }

      this.socket.onopen = () => {
        console.log('WebSocket connection established');
      };

      this.socket.onclose = () => {
        console.log('WebSocket connection closed');
      };

      this.socket.onerror = (error) => {
        console.error('WebSocket error:', error);
      };
    },
  },
  mounted() {
    this.fetchProducts();
    this.fetchCategories();
    this.connectWebSocket();
  },
});
</script>
