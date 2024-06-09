<template>
  <div>
    <h2>{{ product.id ? 'Edit' : 'Add' }} Product</h2>
    <form @submit.prevent="submitForm">
      <div>
        <label>Name:</label>
        <input v-model="product.name" required />
      </div>
      <div>
        <label>Description:</label>
        <textarea v-model="product.description" required></textarea>
      </div>
      <div>
        <label>Price:</label>
        <input type="number" v-model="product.price" required />
      </div>
      <div>
        <label>Categories:</label>
        <select multiple v-model="selectedCategories">
          <option v-for="category in categories" :key="category.id" :value="category.id">{{ category.name }}</option>
        </select>
      </div>
      <button type="submit">Submit</button>
    </form>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  props: {
    product: {
      type: Object,
      default: () => ({
        name: '',
        description: '',
        price: 0,
        categories: []
      })
    }
  },
  data() {
    return {
      categories: [],
      selectedCategories: []
    };
  },
  methods: {
    fetchCategories() {
      axios.get('http://localhost:8082/api/categories')
        .then(response => {
          this.categories = response.data;
          this.selectedCategories = this.product.categories ? this.product.categories.map(c => c.id) : [];
        });
    },
    submitForm() {
      const productData = {
        ...this.product,
        categories: this.selectedCategories.map(id => ({ id }))
      };

      if (this.product.id) {
        axios.put(`http://localhost:8082/api/products/${this.product.id}`, productData)
          .then(() => {
            this.$emit('refresh');
          });
      } else {
        axios.post('http://localhost:8082/api/products', productData)
          .then(() => {
            this.$emit('refresh');
          });
      }
    }
  },
  mounted() {
    this.fetchCategories();
  }
};
</script>
