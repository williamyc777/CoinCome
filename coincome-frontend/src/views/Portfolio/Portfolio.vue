<script setup>
import { ref } from 'vue'
import LandingLayout from '@/views/Landing/LandingLayout.vue'


const search = ref('')
</script>

<template>
  <LandingLayout>
    <div class="explore-container">
      <!-- Hero Section -->
      <div class="hero-section">
        <h1 class="hero-title">My portfolio</h1>
        <!-- <p class="hero-subtitle">Discover tokens on Solana & beyond</p> -->
      </div>
    </div>
  </LandingLayout>
</template>

<style scoped>
.explore-container {
  width: 100%;
  max-width: 1400px;
  margin: 0 auto;
  padding: 0 48px;
  box-sizing: border-box;
}

/* Hero Section */
.hero-section {
  text-align: center;
  padding: 60px 0 48px 0;
}

.hero-title {
  font-size: 48px;
  font-weight: 700;
  color: #0f172a;
  margin: 0 0 12px 0;
  font-family: system-ui, -apple-system, BlinkMacSystemFont, "Inter", sans-serif;
  letter-spacing: -0.02em;
}

.hero-subtitle {
  font-size: 18px;
  color: #64748b;
  margin: 0 0 32px 0;
  font-weight: 400;
}


</style>
