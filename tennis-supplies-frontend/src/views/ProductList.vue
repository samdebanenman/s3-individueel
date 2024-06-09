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
            this.products = response.data;
          });
    },
    fetchCategories() {
      axios.get('http://localhost:8082/api/categories')
          .then(response => {
            this.categories = response.data;
          });
    },
  },
  mounted() {
    this.fetchProducts();
    this.fetchCategories();
  },
});
</script>
