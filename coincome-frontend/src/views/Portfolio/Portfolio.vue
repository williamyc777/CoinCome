<template>
  <LandingLayout>
    <div class="portfolio-container">
      <!-- Page Header -->
      <section class="page-header">
        <div class="header-decoration">
          <div class="decoration-circle circle-1"></div>
          <div class="decoration-circle circle-2"></div>
          <div class="decoration-circle circle-3"></div>
        </div>
        <div class="header-icon">
          <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.5">
            <path d="M21 12c0 1.66-4 3-9 3s-9-1.34-9-3"/>
            <path d="M3 5v14c0 1.66 4 3 9 3s9-1.34 9-3V5"/>
            <ellipse cx="12" cy="5" rx="9" ry="3"/>
          </svg>
        </div>
        <h1 class="page-title">Holdings Center</h1>
        <p class="page-subtitle">Track your investments and optimize your returns</p>
      </section>

      <!-- Tab Navigation -->
      <div class="tab-nav-wrapper">
        <div class="tab-nav">
          <router-link
            v-for="tab in tabs"
            :key="tab.id"
            :to="tab.to"
            class="tab-btn"
            active-class="active"
          >
            {{ tab.label }}
          </router-link>
        </div>
      </div>

      <!-- Child Route Content -->
      <router-view />
    </div>
  </LandingLayout>
</template>

<script setup>
import LandingLayout from '@/views/Landing/LandingLayout.vue'

const tabs = [
  { id: 'overall', label: 'Overall Holdings', to: '/portfolio/overall' },
  { id: 'exchange', label: 'By Exchange', to: '/portfolio/by-exchange' },
  { id: 'recommended', label: 'Recommended Portfolio', to: '/portfolio/recommended' }
]
</script>

<style scoped>
.portfolio-container {
  width: 100%;
  max-width: 1400px;
  margin: 0 auto;
  padding: 0 48px 60px 48px;
  box-sizing: border-box;
}

/* Page Header */
.page-header {
  text-align: center;
  padding: 48px 0 32px 0;
  position: relative;
  overflow: hidden;
}

.header-decoration {
  position: absolute;
  top: 0;
  left: 50%;
  transform: translateX(-50%);
  width: 400px;
  height: 200px;
  pointer-events: none;
}

.decoration-circle {
  position: absolute;
  border-radius: 50%;
  opacity: 0.1;
}

.circle-1 {
  width: 200px;
  height: 200px;
  background: linear-gradient(135deg, #6c5dd3, #8b7ae8);
  top: -100px;
  left: 50%;
  transform: translateX(-50%);
}

.circle-2 {
  width: 120px;
  height: 120px;
  background: linear-gradient(135deg, #10b981, #34d399);
  top: -20px;
  left: 20%;
}

.circle-3 {
  width: 80px;
  height: 80px;
  background: linear-gradient(135deg, #f59e0b, #fbbf24);
  top: 0;
  right: 20%;
}

.header-icon {
  width: 64px;
  height: 64px;
  margin: 0 auto 16px;
  background: linear-gradient(135deg, #6c5dd3, #8b7ae8);
  border-radius: 20px;
  display: flex;
  align-items: center;
  justify-content: center;
  box-shadow: 0 8px 24px rgba(108, 93, 211, 0.3);
  position: relative;
  z-index: 1;
}

.header-icon svg {
  width: 32px;
  height: 32px;
  color: #ffffff;
}

.page-title {
  font-size: 48px;
  font-weight: 700;
  color: #3a2a68;
  margin: 0 0 16px 0;
  font-family: 'Georgia', 'Times New Roman', Times, serif;
  line-height: 1.2;
  position: relative;
  z-index: 1;
}

.page-subtitle {
  font-size: 18px;
  color: #4f458d;
  margin: 0;
  font-weight: 400;
  position: relative;
  z-index: 1;
}

/* Tab Navigation */
.tab-nav-wrapper {
  display: flex;
  justify-content: center;
  margin-bottom: 32px;
}

.tab-nav {
  display: inline-flex;
  gap: 8px;
  background: rgba(255, 255, 255, 0.95);
  backdrop-filter: blur(10px);
  padding: 6px;
  border-radius: 16px;
  box-shadow: 0 4px 20px rgba(139, 92, 246, 0.1), 0 2px 6px rgba(0, 0, 0, 0.03);
  border: 1px solid rgba(255, 255, 255, 0.8);
}

.tab-btn {
  display: flex;
  align-items: center;
  gap: 8px;
  background: transparent;
  border: none;
  font-size: 14px;
  font-weight: 500;
  color: #64748b;
  padding: 12px 24px;
  border-radius: 12px;
  cursor: pointer;
  transition: all 0.2s ease;
  text-decoration: none;
}

.tab-btn:hover {
  color: #3a2a68;
  background: rgba(108, 93, 211, 0.05);
}

.tab-btn.active {
  color: #ffffff;
  background: linear-gradient(135deg, #6c5dd3, #8b7ae8);
  box-shadow: 0 4px 12px rgba(108, 93, 211, 0.3);
}

/* Responsive Design */
@media (max-width: 1024px) {
  .portfolio-container {
    padding: 0 24px 60px 24px;
  }
}

@media (max-width: 768px) {
  .portfolio-container {
    padding: 0 16px 40px 16px;
  }

  .page-title {
    font-size: 36px;
  }

  .page-subtitle {
    font-size: 16px;
  }

  .tab-nav {
    flex-direction: column;
    width: 100%;
  }
}
</style>
