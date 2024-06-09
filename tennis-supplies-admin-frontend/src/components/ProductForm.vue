<template>
  <div>
    <h2>Add/Edit Product</h2>
    <form @submit.prevent="saveProduct">
      <label for="name">Name:</label>
      <input v-model="product.name" id="name" required />

      <label for="description">Description:</label>
      <input v-model="product.description" id="description" required />

      <label for="price">Price:</label>
      <input type="number" v-model="product.price" id="price" required />

      <label for="categories">Categories:</label>
      <select v-model="selectedCategories" multiple>
        <option v-for="category in categories" :key="category.id" :value="category.id">
          {{ category.name }}
        </option>
      </select>

      <button type="submit">Save</button>
    </form>
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
  props: {
    product: {
      type: Object as () => Product,
      required: true
    }
  },
  data() {
    return {
      categories: [] as Category[],
      selectedCategories: this.product.categories.map((c: Category) => c.id)
    };
  },
  methods: {
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
            this.$emit('saved');
          });
    }
  },
  mounted() {
    this.fetchCategories();
  }
});
</script>
