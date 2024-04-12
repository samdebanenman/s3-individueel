<script setup lang="ts">

</script>

<template>
    <div>
      <h2>Add New String</h2>
      <form @submit.prevent="addString">
        <label for="newString">New String:</label>
        <input type="text" id="newString" v-model="newString">
        <button type="submit">Add</button>
      </form>
      <p v-if="message">{{ message }}</p>
    </div>
</template>

<script lang="ts">
import { AxiosError } from 'axios';
  export default {
    data() {
      return {
        newString: '',
        message: ''
      };
    },
    methods: {
      async addString() {
        try {
          const response = await fetch(`http://${window.location.hostname}:8082/addString`, {
            method: 'POST',
            headers: {
              'Content-type': 'application/json'
            },
            body: this.newString
          });
          if (!response.ok) {
            console.error('Failed to add string');
          }
          this.message = 'String added successfully';
          this.newString = ''; // Clear input field
        } catch (error) {
          const err = error as AxiosError;
          console.error('Error adding string:', err.message);
          this.message = 'Failed to add string';
        }
      }
    }
  };
</script>
