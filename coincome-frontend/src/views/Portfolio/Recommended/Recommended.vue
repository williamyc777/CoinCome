<template>
  <section class="content-section">
    <!-- Optimization Summary -->
    <div class="optimization-banner">
      <div class="banner-left">
        <div class="banner-icon">
          <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.5">
            <path d="M3.75 3v11.25A2.25 2.25 0 006 16.5h2.25M3.75 3h-1.5m1.5 0h16.5m0 0h1.5m-1.5 0v11.25A2.25 2.25 0 0118 16.5h-2.25m-7.5 0h7.5m-7.5 0l-1 3m8.5-3l1 3m0 0l.5 1.5m-.5-1.5h-9.5m0 0l-.5 1.5"/>
          </svg>
        </div>
        <div class="banner-text">
          <h3>Portfolio Optimization</h3>
          <p>{{ currentRecommendation.description }}</p>
        </div>
      </div>
      <div class="banner-right">
        <div class="stat-group">
          <div class="stat-item">
            <span class="stat-value green">+{{ currentRecommendation.expectedReturn }}%</span>
            <span class="stat-label">Expected Return</span>
          </div>
          <div class="stat-divider"></div>
          <div class="stat-item">
            <span class="stat-value">{{ currentRecommendation.sharpeRatio }}</span>
            <span class="stat-label">Sharpe Ratio</span>
          </div>
        </div>
        <div class="risk-selector">
          <div class="risk-toggle">
            <button
              v-for="level in riskLevels"
              :key="level.id"
              :class="['risk-btn', { active: selectedRisk === level.id }, level.id]"
              @click.stop="selectedRisk = level.id"
            >
              {{ level.label }}
            </button>
          </div>
          <span class="risk-label">Risk Level</span>
        </div>
      </div>
    </div>

    <!-- Rebalance Actions -->
    <div class="rebalance-card">
      <div class="card-header">
        <div class="header-left">
          <h3 class="card-title">Rebalance Actions</h3>
          <span class="subtitle">Suggested changes to optimize your portfolio</span>
        </div>
        <span class="changes-badge">{{ currentRecommendation.data.length }} changes</span>
      </div>
      <div class="rebalance-list">
        <div
          v-for="(item, index) in currentRecommendation.data"
          :key="index"
          :class="['rebalance-row', { expanded: expandedCoin === item.coin }]"
          @click="toggleExpand(item.coin)"
        >
          <div class="row-main">
            <div class="coin-info">
              <div class="coin-avatar" :style="{ background: getCoinGradient(index) }">
                {{ item.coin.charAt(0) }}
              </div>
              <span class="coin-symbol">{{ item.coin }}</span>
            </div>
            <div class="allocation-change">
              <span class="from-value">{{ item.current }}%</span>
              <svg class="arrow-icon" viewBox="0 0 20 20" fill="currentColor">
                <path fill-rule="evenodd" d="M3 10a.75.75 0 01.75-.75h10.638L10.23 5.29a.75.75 0 111.04-1.08l5.5 5.25a.75.75 0 010 1.08l-5.5 5.25a.75.75 0 11-1.04-1.08l4.158-3.96H3.75A.75.75 0 013 10z" clip-rule="evenodd"/>
              </svg>
              <span class="to-value">{{ item.target }}%</span>
            </div>
            <div :class="['action-pill', getActionClass(item.action)]">
              {{ item.action }}
            </div>
            <div class="chevron">
              <svg viewBox="0 0 20 20" fill="currentColor">
                <path fill-rule="evenodd" d="M5.23 7.21a.75.75 0 011.06.02L10 11.168l3.71-3.938a.75.75 0 111.08 1.04l-4.25 4.5a.75.75 0 01-1.08 0l-4.25-4.5a.75.75 0 01.02-1.06z" clip-rule="evenodd"/>
              </svg>
            </div>
          </div>
          <!-- Expanded Details -->
          <div class="rebalance-details" v-if="expandedCoin === item.coin">
            <div class="details-grid">
              <div class="detail-item">
                <span class="detail-label">Current Holdings</span>
                <span class="detail-value">${{ item.currentValue?.toLocaleString() || '---' }}</span>
              </div>
              <div class="detail-item">
                <span class="detail-label">Target Holdings</span>
                <span class="detail-value highlight">${{ item.targetValue?.toLocaleString() || '---' }}</span>
              </div>
              <div class="detail-item">
                <span class="detail-label">Action Amount</span>
                <span :class="['detail-value', getActionClass(item.action)]">
                  {{ item.action.includes('Buy') ? '+' : item.action.includes('Sell') ? '-' : '' }}${{ item.actionAmount?.toLocaleString() || '---' }}
                </span>
              </div>
              <div class="detail-item">
                <span class="detail-label">Current Price</span>
                <span class="detail-value">${{ item.currentPrice?.toLocaleString() || '---' }}</span>
              </div>
            </div>
            <div class="detail-chart">
              <div class="mini-comparison">
                <div class="mini-bar-group">
                  <div class="mini-bar-label">Current</div>
                  <div class="mini-bar-track">
                    <div class="mini-bar-fill current" :style="{ width: (item.current / 50 * 100) + '%' }"></div>
                  </div>
                  <span class="mini-bar-value">{{ item.current }}%</span>
                </div>
                <div class="mini-bar-group">
                  <div class="mini-bar-label">Target</div>
                  <div class="mini-bar-track">
                    <div class="mini-bar-fill target" :style="{ width: (item.target / 50 * 100) + '%' }"></div>
                  </div>
                  <span class="mini-bar-value">{{ item.target }}%</span>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </section>
</template>

<script setup>
import { ref, computed } from 'vue'

const coinGradients = [
  'linear-gradient(135deg, #6366f1, #818cf8)',
  'linear-gradient(135deg, #10b981, #34d399)',
  'linear-gradient(135deg, #f59e0b, #fbbf24)',
  'linear-gradient(135deg, #ec4899, #f472b6)',
  'linear-gradient(135deg, #8b5cf6, #a78bfa)',
  'linear-gradient(135deg, #06b6d4, #22d3ee)',
  'linear-gradient(135deg, #f97316, #fb923c)',
  'linear-gradient(135deg, #14b8a6, #2dd4bf)'
]

const getCoinGradient = (index) => coinGradients[index % coinGradients.length]

// Risk level selection
const selectedRisk = ref('moderate')

const riskLevels = [
  { id: 'low', label: 'Low' },
  { id: 'moderate', label: 'Medium' },
  { id: 'high', label: 'High' }
]

// Different recommendations for each risk level
const recommendations = {
  low: {
    description: 'Lower risk approach focusing on stable assets and capital preservation',
    expectedReturn: 8.5,
    sharpeRatio: 0.85,
    data: [
      { coin: 'BTC', current: 30, target: 40, action: 'Buy +10%', currentValue: 7500, targetValue: 10000, actionAmount: 2500, currentPrice: 67500 },
      { coin: 'ETH', current: 22, target: 30, action: 'Buy +8%', currentValue: 5500, targetValue: 7500, actionAmount: 2000, currentPrice: 3650 },
      { coin: 'SOL', current: 12, target: 10, action: 'Sell -2%', currentValue: 3000, targetValue: 2500, actionAmount: 500, currentPrice: 185 },
      { coin: 'DOGE', current: 10, target: 5, action: 'Sell -5%', currentValue: 2500, targetValue: 1250, actionAmount: 1250, currentPrice: 0.38 },
      { coin: 'XRP', current: 10, target: 8, action: 'Sell -2%', currentValue: 2500, targetValue: 2000, actionAmount: 500, currentPrice: 2.15 },
      { coin: 'ADA', current: 8, target: 5, action: 'Sell -3%', currentValue: 2000, targetValue: 1250, actionAmount: 750, currentPrice: 0.95 },
      { coin: 'DOT', current: 8, target: 2, action: 'Sell -6%', currentValue: 2000, targetValue: 500, actionAmount: 1500, currentPrice: 7.80 }
    ]
  },
  moderate: {
    description: 'Based on Markowitz Modern Portfolio Theory for optimal risk-adjusted returns',
    expectedReturn: 13.0,
    sharpeRatio: 1.15,
    data: [
      { coin: 'BTC', current: 30, target: 25, action: 'Sell -5%', currentValue: 7500, targetValue: 6250, actionAmount: 1250, currentPrice: 67500 },
      { coin: 'ETH', current: 22, target: 20, action: 'Sell -2%', currentValue: 5500, targetValue: 5000, actionAmount: 500, currentPrice: 3650 },
      { coin: 'SOL', current: 12, target: 15, action: 'Buy +3%', currentValue: 3000, targetValue: 3750, actionAmount: 750, currentPrice: 185 },
      { coin: 'DOGE', current: 10, target: 10, action: 'Hold', currentValue: 2500, targetValue: 2500, actionAmount: 0, currentPrice: 0.38 },
      { coin: 'XRP', current: 10, target: 12, action: 'Buy +2%', currentValue: 2500, targetValue: 3000, actionAmount: 500, currentPrice: 2.15 },
      { coin: 'ADA', current: 8, target: 10, action: 'Buy +2%', currentValue: 2000, targetValue: 2500, actionAmount: 500, currentPrice: 0.95 },
      { coin: 'DOT', current: 8, target: 8, action: 'Hold', currentValue: 2000, targetValue: 2000, actionAmount: 0, currentPrice: 7.80 }
    ]
  },
  high: {
    description: 'High growth potential with increased volatility exposure',
    expectedReturn: 22.5,
    sharpeRatio: 1.45,
    data: [
      { coin: 'BTC', current: 30, target: 15, action: 'Sell -15%', currentValue: 7500, targetValue: 3750, actionAmount: 3750, currentPrice: 67500 },
      { coin: 'ETH', current: 22, target: 15, action: 'Sell -7%', currentValue: 5500, targetValue: 3750, actionAmount: 1750, currentPrice: 3650 },
      { coin: 'SOL', current: 12, target: 25, action: 'Buy +13%', currentValue: 3000, targetValue: 6250, actionAmount: 3250, currentPrice: 185 },
      { coin: 'DOGE', current: 10, target: 15, action: 'Buy +5%', currentValue: 2500, targetValue: 3750, actionAmount: 1250, currentPrice: 0.38 },
      { coin: 'XRP', current: 10, target: 12, action: 'Buy +2%', currentValue: 2500, targetValue: 3000, actionAmount: 500, currentPrice: 2.15 },
      { coin: 'ADA', current: 8, target: 10, action: 'Buy +2%', currentValue: 2000, targetValue: 2500, actionAmount: 500, currentPrice: 0.95 },
      { coin: 'DOT', current: 8, target: 8, action: 'Hold', currentValue: 2000, targetValue: 2000, actionAmount: 0, currentPrice: 7.80 }
    ]
  }
}

const currentRecommendation = computed(() => recommendations[selectedRisk.value])

// Expanded coin state
const expandedCoin = ref(null)

const toggleExpand = (coin) => {
  expandedCoin.value = expandedCoin.value === coin ? null : coin
}

const getActionClass = (action) => {
  if (action.includes('Sell')) return 'sell'
  if (action.includes('Buy')) return 'buy'
  return 'hold'
}
</script>

<style scoped>
/* Base */
.content-section {
  animation: fadeIn 0.3s ease;
}

@keyframes fadeIn {
  from { opacity: 0; transform: translateY(8px); }
  to { opacity: 1; transform: translateY(0); }
}

/* Banner */
.optimization-banner {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  border-radius: 16px;
  padding: 24px 28px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
  color: #fff;
  position: relative;
  overflow: hidden;
}

.optimization-banner::before {
  content: '';
  position: absolute;
  top: -50%;
  right: -20%;
  width: 400px;
  height: 400px;
  background: radial-gradient(circle, rgba(255,255,255,0.1) 0%, transparent 60%);
  pointer-events: none;
}

.banner-left {
  display: flex;
  align-items: center;
  gap: 16px;
  position: relative;
  z-index: 1;
}

.banner-icon {
  width: 48px;
  height: 48px;
  background: rgba(255, 255, 255, 0.2);
  border-radius: 12px;
  display: flex;
  align-items: center;
  justify-content: center;
  flex-shrink: 0;
}

.banner-icon svg {
  width: 24px;
  height: 24px;
}

.banner-text h3 {
  font-size: 18px;
  font-weight: 700;
  margin: 0 0 4px 0;
}

.banner-text p {
  font-size: 13px;
  opacity: 0.9;
  margin: 0;
  max-width: 300px;
  line-height: 1.4;
}

.banner-right {
  display: flex;
  align-items: center;
  gap: 16px;
  position: relative;
  z-index: 1;
}

.stat-group {
  display: flex;
  align-items: center;
  background: rgba(255, 255, 255, 0.15);
  border-radius: 12px;
  padding: 12px 20px;
  gap: 20px;
}

.stat-item {
  text-align: center;
}

.stat-value {
  display: block;
  font-size: 20px;
  font-weight: 700;
  line-height: 1.2;
}

.stat-value.green {
  color: #86efac;
}

.stat-label {
  font-size: 10px;
  text-transform: uppercase;
  letter-spacing: 0.5px;
  opacity: 0.8;
  margin-top: 2px;
}

.stat-divider {
  width: 1px;
  height: 32px;
  background: rgba(255, 255, 255, 0.2);
}

/* Risk Selector */
.risk-selector {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 6px;
}

.risk-toggle {
  display: flex;
  background: rgba(255, 255, 255, 0.15);
  padding: 3px;
  border-radius: 10px;
  gap: 2px;
}

.risk-btn {
  border: none;
  background: transparent;
  color: rgba(255, 255, 255, 0.7);
  font-size: 12px;
  font-weight: 600;
  padding: 7px 14px;
  border-radius: 8px;
  cursor: pointer;
  transition: all 0.2s ease;
}

.risk-btn:hover {
  color: #fff;
  background: rgba(255, 255, 255, 0.1);
}

.risk-btn.active {
  color: #fff;
  font-weight: 700;
}

.risk-btn.active.low {
  background: linear-gradient(135deg, #10b981, #059669);
  box-shadow: 0 2px 8px rgba(16, 185, 129, 0.4);
}

.risk-btn.active.moderate {
  background: linear-gradient(135deg, #3b82f6, #2563eb);
  box-shadow: 0 2px 8px rgba(59, 130, 246, 0.4);
}

.risk-btn.active.high {
  background: linear-gradient(135deg, #f59e0b, #d97706);
  box-shadow: 0 2px 8px rgba(245, 158, 11, 0.4);
}

.risk-label {
  font-size: 10px;
  text-transform: uppercase;
  letter-spacing: 0.5px;
  opacity: 0.8;
}

/* Rebalance Card */
.rebalance-card {
  background: #fff;
  border-radius: 16px;
  padding: 20px 24px;
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.05), 0 1px 2px rgba(0, 0, 0, 0.1);
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: flex-start;
  margin-bottom: 16px;
}

.header-left {
  display: flex;
  flex-direction: column;
  gap: 2px;
}

.card-title {
  font-size: 16px;
  font-weight: 700;
  color: #1e293b;
  margin: 0;
}

.subtitle {
  font-size: 13px;
  color: #64748b;
}

.changes-badge {
  font-size: 12px;
  font-weight: 600;
  color: #7c3aed;
  background: #f3f0ff;
  padding: 6px 12px;
  border-radius: 20px;
}

/* Rebalance List */
.rebalance-list {
  display: flex;
  flex-direction: column;
  gap: 6px;
}

.rebalance-row {
  background: #fafafa;
  border-radius: 12px;
  transition: all 0.2s ease;
  cursor: pointer;
  overflow: hidden;
}

.rebalance-row:hover {
  background: #f5f3ff;
}

.rebalance-row.expanded {
  background: #f5f3ff;
  box-shadow: 0 2px 8px rgba(124, 58, 237, 0.1);
}

.row-main {
  display: flex;
  align-items: center;
  padding: 12px 16px;
  gap: 16px;
}

.coin-info {
  display: flex;
  align-items: center;
  gap: 10px;
  min-width: 90px;
}

.coin-avatar {
  width: 32px;
  height: 32px;
  border-radius: 8px;
  display: flex;
  align-items: center;
  justify-content: center;
  font-weight: 700;
  color: #fff;
  font-size: 12px;
}

.coin-symbol {
  font-weight: 600;
  color: #1e293b;
  font-size: 14px;
}

.allocation-change {
  display: flex;
  align-items: center;
  gap: 8px;
  background: #fff;
  padding: 6px 12px;
  border-radius: 20px;
  border: 1px solid #e5e7eb;
}

.from-value {
  font-size: 13px;
  font-weight: 500;
  color: #9ca3af;
  min-width: 28px;
  text-align: center;
}

.arrow-icon {
  width: 14px;
  height: 14px;
  color: #a78bfa;
}

.to-value {
  font-size: 13px;
  font-weight: 700;
  color: #7c3aed;
  min-width: 28px;
  text-align: center;
}

.action-pill {
  padding: 5px 12px;
  border-radius: 16px;
  font-size: 11px;
  font-weight: 600;
  min-width: 64px;
  text-align: center;
}

.action-pill.buy {
  background: #d1fae5;
  color: #059669;
}

.action-pill.sell {
  background: #fee2e2;
  color: #dc2626;
}

.action-pill.hold {
  background: #f1f5f9;
  color: #64748b;
}

.chevron {
  width: 20px;
  height: 20px;
  color: #9ca3af;
  margin-left: auto;
  transition: transform 0.2s ease;
}

.chevron svg {
  width: 20px;
  height: 20px;
}

.rebalance-row.expanded .chevron {
  transform: rotate(180deg);
  color: #7c3aed;
}

/* Expanded Details */
.rebalance-details {
  padding: 0 16px 16px 16px;
  animation: slideDown 0.2s ease;
}

@keyframes slideDown {
  from { opacity: 0; transform: translateY(-8px); }
  to { opacity: 1; transform: translateY(0); }
}

.details-grid {
  display: grid;
  grid-template-columns: repeat(4, 1fr);
  gap: 12px;
  padding: 16px 0;
  border-top: 1px solid #e5e7eb;
}

.detail-item {
  display: flex;
  flex-direction: column;
  gap: 4px;
  padding: 10px 12px;
  background: #fff;
  border-radius: 10px;
}

.detail-label {
  font-size: 11px;
  color: #64748b;
  font-weight: 500;
  text-transform: uppercase;
  letter-spacing: 0.3px;
}

.detail-value {
  font-size: 14px;
  font-weight: 700;
  color: #1e293b;
}

.detail-value.highlight {
  color: #7c3aed;
}

.detail-value.buy {
  color: #059669;
}

.detail-value.sell {
  color: #dc2626;
}

.detail-value.hold {
  color: #64748b;
}

/* Mini Chart */
.detail-chart {
  padding-top: 12px;
  border-top: 1px solid #e5e7eb;
}

.mini-comparison {
  display: flex;
  flex-direction: column;
  gap: 8px;
}

.mini-bar-group {
  display: flex;
  align-items: center;
  gap: 10px;
}

.mini-bar-label {
  font-size: 12px;
  font-weight: 500;
  color: #64748b;
  min-width: 50px;
}

.mini-bar-track {
  flex: 1;
  height: 8px;
  background: #e5e7eb;
  border-radius: 4px;
  overflow: hidden;
}

.mini-bar-fill {
  height: 100%;
  border-radius: 4px;
  transition: width 0.4s ease;
}

.mini-bar-fill.current {
  background: #cbd5e1;
}

.mini-bar-fill.target {
  background: linear-gradient(90deg, #7c3aed, #a78bfa);
}

.mini-bar-value {
  font-size: 12px;
  font-weight: 600;
  color: #1e293b;
  min-width: 36px;
  text-align: right;
}

/* Responsive */
@media (max-width: 1024px) {
  .optimization-banner {
    flex-direction: column;
    gap: 20px;
    text-align: center;
  }

  .banner-left {
    flex-direction: column;
  }

  .banner-right {
    flex-direction: column;
    width: 100%;
  }

  .stat-group {
    width: 100%;
    justify-content: center;
  }

  .details-grid {
    grid-template-columns: repeat(2, 1fr);
  }
}

@media (max-width: 768px) {
  .row-main {
    flex-wrap: wrap;
    gap: 10px;
  }

  .chevron {
    margin-left: 0;
  }

  .details-grid {
    grid-template-columns: 1fr 1fr;
  }
}
</style>
