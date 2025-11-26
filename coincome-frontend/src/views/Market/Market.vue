<script setup>
import { ref } from 'vue'
import LandingLayout from '@/views/Landing/LandingLayout.vue'
import DisplayTable from './DisplayTable.vue'
import CashIcon from '@/assets/d.svg'

const search = ref('')
</script>

<template>
  <LandingLayout>
    <div class="explore-container">
      <!-- Hero Section -->
      <div class="hero-section">
        <h1 class="hero-title">Explore the ecosystem</h1>
        <p class="hero-subtitle">Discover tokens on Solana & beyond</p>

        <!-- Search Bar -->
        <div class="search-container">
          <div class="search-box">
            <svg class="search-icon" width="20" height="20" viewBox="0 0 20 20" fill="none">
              <path d="M9 17A8 8 0 1 0 9 1a8 8 0 0 0 0 16zM19 19l-4.35-4.35" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
            </svg>
            <input
              type="text"
              v-model="search"
              placeholder="Search"
              class="search-input"
            />
          </div>
        </div>
      </div>

      <!-- Market Overview Section -->
      <div class="market-section">
        <div class="section-header">
          <img :src="CashIcon" alt="Cash Icon" class="section-icon" />
          <h2 class="section-title">Market Overview</h2>
        </div>

        <DisplayTable :search="search" />
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

/* Search Container */
.search-container {
  display: flex;
  justify-content: center;
  margin-bottom: 48px;
  caret-color: transparent;
}

.search-box {
  position: relative;
  width: 100%;
  max-width: 480px;
  display: flex;
  align-items: center;
  caret-color: transparent;
}

.search-icon {
  position: absolute;
  left: 16px;
  color: #94a3b8;
  pointer-events: none;
}

.search-input {
  width: 100%;
  padding: 14px 16px 14px 48px;
  font-size: 16px;
  border: 1px solid #e2e8f0;
  border-radius: 12px;
  background: #ffffff;
  color: #0f172a;
  outline: none;
  transition: border-color 0.2s, box-shadow 0.2s;
  font-family: system-ui, -apple-system, BlinkMacSystemFont, "Inter", sans-serif;
}

.search-input:focus {
  border-color: #8b5cf6;
  box-shadow: 0 0 0 3px rgba(139, 92, 246, 0.1);
}

.search-input::placeholder {
  color: #94a3b8;
}

/* Market Section */
.market-section {
  margin-top: 24px;
}

/* Section Header */
.section-header {
  display: flex;
  align-items: center;
  gap: 12px;
  margin-bottom: 20px;
}

.section-icon {
  flex-shrink: 0;
  width: 32px;
  height: 32px;
}

.section-title {
  font-size: 24px;
  font-weight: 700;
  color: #0f172a;
  margin: 0;
  font-family: system-ui, -apple-system, BlinkMacSystemFont, "Inter", sans-serif;
}

/* Responsive Design */
@media (max-width: 768px) {
  .explore-container {
    padding: 0 16px;
  }

  .hero-section {
    padding: 40px 0 32px 0;
  }

  .hero-title {
    font-size: 32px;
  }

  .hero-subtitle {
    font-size: 16px;
  }

  .search-container {
    margin-bottom: 32px;
  }

  .section-title {
    font-size: 20px;
  }
}
</style>
