<template>
  <div>
    <h2>{{ category.id ? 'Edit' : 'Add' }} Category</h2>
    <form @submit.prevent="submitForm">
      <div>
        <label>Name:</label>
        <input v-model="category.name" required />
      </div>
      <button type="submit">Submit</button>
    </form>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  props: {
    category: {
      type: Object,
      default: () => ({
        name: ''
      })
    }
  },
  methods: {
    submitForm() {
      if (this.category.id) {
        axios.put(`http://localhost:8082/api/categories/${this.category.id}`, this.category)
          .then(() => {
            this.$emit('refresh');
          });
      } else {
        axios.post('http://localhost:8082/api/categories', this.category)
          .then(() => {
            this.$emit('refresh');
          });
      }
    }
  }
};
</script>
