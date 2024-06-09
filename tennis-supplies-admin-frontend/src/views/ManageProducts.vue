<template>
  <div>
    <h1>Manage Products</h1>
    <button @click="addProduct">Add Product</button>
    <ul>
      <li v-for="product in products" :key="product.id">
        <h3>{{ product.name }}</h3>
        <p>{{ product.description }}</p>
        <p>{{ product.price }}</p>
        <button @click="editProduct(product)">Edit</button>
        <button @click="deleteProduct(product.id)">Delete</button>
      </li>
    </ul>
    <ProductForm v-if="showForm" :product="currentProduct" @refresh="fetchProducts" />
  </div>
</template>

<script>
import axios from 'axios';
import ProductForm from '../components/ProductForm.vue';

export default {
  components: {
    ProductForm
  },
  data() {
    return {
      products: [],
      showForm: false,
      currentProduct: null
    };
  },
  methods: {
    fetchProducts() {
      axios.get('http://localhost:8082/api/products')
        .then(response => {
          this.products = response.data;
          this.showForm = false;
          this.currentProduct = null;
        });
    },
    addProduct() {
      this.currentProduct = {
        name: '',
        description: '',
        price: 0,
        categories: []
      };
      this.showForm = true;
    },
    editProduct(product) {
      this.currentProduct = { ...product };
      this.showForm = true;
    },
    deleteProduct(id) {
      axios.delete(`http://localhost:8082/api/products/${id}`)
        .then(() => {
          this.fetchProducts();
        });
    }
  },
  mounted() {
    this.fetchProducts();
  }
};
</script>
