<template>
  <div>
    <h1>Manage Products</h1>
    <form @submit.prevent="saveProduct">
      <input v-model="product.name" placeholder="Product name" required />
      <input v-model="product.description" placeholder="Product description" required />
      <input type="number" v-model="product.price" placeholder="Product price" required />
      <select v-model="selectedCategories" multiple>
        <option v-for="category in categories" :key="category.id" :value="category.id">{{ category.name }}</option>
      </select>
      <button type="submit">Save</button>
    </form>
    <ul>
      <li v-for="product in products" :key="product.id">
        <h3>{{ product.name }}</h3>
        <p>{{ product.description }}</p>
        <p>{{ product.price }}</p>
        <button @click="editProduct(product)">Edit</button>
        <button @click="deleteProduct(product.id)">Delete</button>
      </li>
    </ul>
  </div>
</template>

<script lang="ts">
import { defineComponent } from 'vue';
import axios from 'axios';

interface Category {
  id: number;
  name: string;
}

interface Product {
  id?: number;
  name: string;
  description: string;
  price: number;
  categories: Category[];
}

export default defineComponent({
  data() {
    return {
      products: [] as Product[],
      categories: [] as Category[],
      product: { name: '', description: '', price: 0, categories: [] } as Product,
      selectedCategories: [] as number[]
    };
  },
  methods: {
    fetchProducts() {
      axios.get('http://localhost:8082/api/products')
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
    saveProduct() {
      this.product.categories = this.categories.filter(c => this.selectedCategories.includes(c.id));
      axios.post('http://localhost:8082/api/products', this.product)
          .then(() => {
            this.fetchProducts();
            this.product = { name: '', description: '', price: 0, categories: [] };
            this.selectedCategories = [];
          });
    },
    editProduct(product: Product) {
      this.product = { ...product };
      this.selectedCategories = product.categories.map(c => c.id);
    },
    deleteProduct(id: number | undefined) {
      axios.delete(`http://localhost:8082/api/products/${id}`)
          .then(() => {
            this.fetchProducts();
          });
    }
  },
  mounted() {
    this.fetchProducts();
    this.fetchCategories();
  }
});
</script>
