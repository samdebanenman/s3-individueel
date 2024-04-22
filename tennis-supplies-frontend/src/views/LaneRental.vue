<template>
  <div>
    <h2>Rent Lanes</h2>
    <p>{{ availableLanesCount }} lane(s) available</p>
    <div v-for="lane in lanes" :key="lane.id" class="lane-item" :class="{ 'available': lane.available, 'unavailable': !lane.available }">
      {{ lane.name }}
    </div>
  </div>
  <div>
    <Datepicker v-model="date" @change="handleDateChange" />
  </div>
</template>

<script setup lang="ts">
import { computed, onMounted, ref, watch } from 'vue';
import { getAllLanes } from '@/services/ApiService';
import Datepicker from '@vuepic/vue-datepicker';
import '@vuepic/vue-datepicker/dist/main.css';

interface Lane {
  id: number;
  name: string;
  available: boolean;
}

const isLoading = ref(true);
const lanes = ref<Lane[]>([]);
const date = ref<Date | null>(null); // Updated to hold a Date object

async function fetchLanes(timestamp: bigint) {
  try {
    isLoading.value = true;
    lanes.value = await getAllLanes(timestamp).then(response => response.json());
  } catch (error) {
    console.error('Error fetching lanes:', error);
  } finally {
    isLoading.value = false;
  }
}

// Use onMounted hook to fetch lanes when the component is mounted
onMounted(() => fetchLanes(BigInt(Math.floor(Date.now() / 1000)))); // Initial fetch with current Unix timestamp

const availableLanesCount = computed(() => lanes.value.filter(lane => lane.available).length);

watch(date, (newDate, oldDate) => {
  if (newDate && newDate !== oldDate) {
    const timestamp = BigInt(Math.floor(newDate.getTime() / 1000)); // Convert Date to Unix timestamp
    fetchLanes(timestamp);
  }
});

function handleDateChange(newDate: Date) {
  date.value = newDate;
}
</script>

<style scoped>
/* Your existing styles */
</style>
