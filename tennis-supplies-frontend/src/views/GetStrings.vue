<template>
  <div>
    <h2>Strings from Database</h2>
    <table>
      <caption>Strings from db</caption>
      <thead>
      <tr>
        <th>ID</th>
        <th>String</th>
      </tr>
      </thead>
      <tbody>
      <tr v-for="item in strings" :key="item.id">
        <td>{{ item.id }}</td>
        <td>{{ item.name }}</td>
      </tr>
      </tbody>
    </table>
  </div>
</template>

<script lang="ts">
import { defineComponent } from 'vue';

export default defineComponent({
  data() {
    return {
      strings: [] as { id: number; name: string }[]
    };
  },
  mounted() {
    this.getStrings();
  },
  methods: {
    async getStrings() {
      try {
        const response = await fetch(`http://${window.location.hostname}:8082/getStrings`);
        if (!response.ok) {
          console.error('Error fetching strings: Failed to fetch strings');
        }
        this.strings = await response.json();
      } catch (err) {
        if (err instanceof Error)
        console.error('Error fetching strings:', err.message);
      }
    }
  }
});
</script>

<style scoped>
/* Your scoped styles here */
</style>
