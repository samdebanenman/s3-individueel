<template>
  <div>
    <h1>Manage Categories</h1>
    <button @click="addCategory">Add Category</button>
    <ul>
      <li v-for="category in categories" :key="category.id">
        <h3>{{ category.name }}</h3>
        <button @click="editCategory(category)">Edit</button>
        <button @click="deleteCategory(category.id)">Delete</button>
      </li>
    </ul>
    <CategoryForm v-if="showForm" :category="currentCategory" @refresh="fetchCategories" />
  </div>
</template>

<script>
import axios from 'axios';
import CategoryForm from '../components/CategoryForm.vue';

export default {
  components: {
    CategoryForm
  },
  data() {
    return {
      categories: [],
      showForm: false,
      currentCategory: null
    };
  },
  methods: {
    fetchCategories() {
      axios.get('http://localhost:8082/api/categories')
        .then(response => {
          this.categories = response.data;
          this.showForm = false;
          this.currentCategory = null;
        });
    },
    addCategory() {
      this.currentCategory = {
        name: ''
      };
      this.showForm = true;
    },
    editCategory(category) {
      this.currentCategory = { ...category };
      this.showForm = true;
    },
    deleteCategory(id) {
      axios.delete(`http://localhost:8082/api/categories/${id}`)
        .then(() => {
          this.fetchCategories();
        });
    }
  },
  mounted() {
    this.fetchCategories();
  }
};
</script>
