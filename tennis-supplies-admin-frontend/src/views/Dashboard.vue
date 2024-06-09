<template>
  <div>
    <h1>Admin Dashboard</h1>
    <nav>
      <router-link to="/manage-products">Manage Products</router-link>
      <router-link to="/manage-categories">Manage Categories</router-link>
    </nav>
    <router-view></router-view>
  </div>
</template>

<script lang="ts">
export default {
};
</script>
