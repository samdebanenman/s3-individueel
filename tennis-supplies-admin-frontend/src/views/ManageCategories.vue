<template>
  <div>
    <h1>Manage Categories</h1>
    <form @submit.prevent="saveCategory">
      <input v-model="category.name" placeholder="Category name" required />
      <button type="submit">Save</button>
    </form>
    <ul>
      <li v-for="category in categories" :key="category.id">
        {{ category.name }}
        <button @click="editCategory(category)">Edit</button>
        <button @click="deleteCategory(category.id)">Delete</button>
      </li>
    </ul>
  </div>
</template>

<script lang="ts">
import { defineComponent } from 'vue';
import axios from 'axios';

interface Category {
  id?: number;
  name: string;
}

export default defineComponent({
  data() {
    return {
      categories: [] as Category[],
      category: { name: '' } as Category
    };
  },
  methods: {
    fetchCategories() {
      axios.get('http://localhost:8082/api/categories')
          .then(response => {
            this.categories = response.data;
          });
    },
    saveCategory() {
      axios.post('http://localhost:8082/api/categories', this.category)
          .then(() => {
            this.fetchCategories();
            this.category = { name: '' };
          });
    },
    editCategory(category: Category) {
      this.category = { ...category };
    },
    deleteCategory(id: number | undefined) {
      axios.delete(`http://localhost:8082/api/categories/${id}`)
          .then(() => {
            this.fetchCategories();
          });
    }
  },
  mounted() {
    this.fetchCategories();
  }
});
</script>
