<template>
  <section class="content-section">
    <!-- Stats Row -->
    <div class="stats-row">
      <div class="stat-card">
        <div class="stat-icon-wrapper">
          <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
            <path d="M12 2v20M17 5H9.5a3.5 3.5 0 0 0 0 7h5a3.5 3.5 0 0 1 0 7H6"/>
          </svg>
        </div>
        <div class="stat-content">
          <span class="stat-label">Total Portfolio Value</span>
          <span class="stat-value">${{ totalValue.toLocaleString() }}</span>
        </div>
        <span class="stat-badge positive">+{{ totalReturn }}%</span>
      </div>
      <div class="stat-card">
        <div class="stat-icon-wrapper warning">
          <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
            <path d="M12 9v4M12 17h.01M10.29 3.86L1.82 18a2 2 0 0 0 1.71 3h16.94a2 2 0 0 0 1.71-3L13.71 3.86a2 2 0 0 0-3.42 0z"/>
          </svg>
        </div>
        <div class="stat-content">
          <span class="stat-label">Risk Level</span>
          <span class="stat-value moderate">{{ riskLevel }}</span>
        </div>
      </div>
      <div class="stat-card">
        <div class="stat-icon-wrapper accent">
          <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
            <circle cx="12" cy="12" r="10"/>
            <path d="M12 6v6l4 2"/>
          </svg>
        </div>
        <div class="stat-content">
          <span class="stat-label">Assets Held</span>
          <span class="stat-value">{{ holdings.length }} <span class="stat-unit">coins</span></span>
        </div>
      </div>
    </div>

    <!-- Main Content: Pie Chart + Asset List -->
    <div class="dashboard-card">
      <!-- Left: Pie Chart with Legend -->
      <div class="pie-section">
        <div class="pie-chart-wrapper">
          <svg viewBox="0 0 200 200" class="pie-chart">
            <g transform="translate(100, 100)">
              <path
                v-for="(slice, index) in pieSlices"
                :key="index"
                :d="slice.path"
                :fill="slice.color"
                stroke="rgba(255,255,255,0.5)"
                stroke-width="2.5"
                class="pie-slice"
              />
            </g>
          </svg>
        </div>
        <!-- Pie Legend -->
        <div class="pie-legend">
          <div v-for="(item, index) in holdings" :key="index" class="pie-legend-item">
            <span class="pie-legend-dot" :style="{ background: pieColors[index % pieColors.length] }"></span>
            <span class="pie-legend-label">{{ item.symbol }}</span>
            <span class="pie-legend-value">{{ item.percentage }}%</span>
          </div>
        </div>
      </div>

      <!-- Right: Asset List -->
      <div class="asset-section">
        <div class="asset-section-header">
          <h3 class="asset-section-title">Your Assets</h3>
          <span class="asset-count">{{ holdings.length }} coins</span>
        </div>
        <div class="asset-list-scroll">
          <div v-for="(item, index) in holdings" :key="index" class="asset-row">
            <div class="asset-left">
              <div class="asset-icon" :style="{ background: pieColors[index % pieColors.length] }">
                {{ item.symbol.charAt(0) }}
              </div>
              <div class="asset-info">
                <span class="asset-symbol">{{ item.symbol }}</span>
                <span class="asset-name">{{ item.fullName }}</span>
              </div>
            </div>
            <div class="asset-right">
              <span class="asset-value">${{ item.value.toLocaleString() }}</span>
              <span class="asset-percent">{{ item.percentage }}%</span>
            </div>
          </div>
        </div>
      </div>
    </div>

    <!-- Line Chart Card -->
    <div class="chart-card line-card-full">
      <div class="card-header">
        <h3 class="card-title">Portfolio Performance</h3>
        <div class="chart-stats">
          <div class="chart-stat">
            <span class="chart-stat-label">Starting</span>
            <span class="chart-stat-value">$17,500</span>
          </div>
          <div class="chart-stat">
            <span class="chart-stat-label">Current</span>
            <span class="chart-stat-value highlight">$27,000</span>
          </div>
          <div class="chart-stat">
            <span class="chart-stat-label">Change</span>
            <span class="chart-stat-value positive">+54.3%</span>
          </div>
        </div>
      </div>
      <div class="line-chart-container-full">
        <svg viewBox="0 0 600 200" class="line-chart" preserveAspectRatio="xMidYMid meet">
          <!-- Gradient -->
          <defs>
            <linearGradient id="lineGradient" x1="0%" y1="0%" x2="0%" y2="100%">
              <stop offset="0%" style="stop-color:#7c3aed;stop-opacity:0.35" />
              <stop offset="50%" style="stop-color:#a855f7;stop-opacity:0.15" />
              <stop offset="100%" style="stop-color:#e9d5ff;stop-opacity:0.02" />
            </linearGradient>
            <linearGradient id="lineStrokeGradient" x1="0%" y1="0%" x2="100%" y2="0%">
              <stop offset="0%" style="stop-color:#6d28d9" />
              <stop offset="50%" style="stop-color:#a855f7" />
              <stop offset="100%" style="stop-color:#ec4899" />
            </linearGradient>
            <filter id="glowEffect" x="-20%" y="-20%" width="140%" height="140%">
              <feGaussianBlur stdDeviation="2" result="coloredBlur"/>
              <feMerge>
                <feMergeNode in="coloredBlur"/>
                <feMergeNode in="SourceGraphic"/>
              </feMerge>
            </filter>
          </defs>

          <!-- Grid lines -->
          <line v-for="i in 5" :key="i" x1="50" :y1="20 + (i-1)*40" x2="570" :y2="20 + (i-1)*40" class="grid-line" />

          <!-- Y-axis labels -->
          <text x="40" y="24" class="axis-label">$28K</text>
          <text x="40" y="64" class="axis-label">$25K</text>
          <text x="40" y="104" class="axis-label">$22K</text>
          <text x="40" y="144" class="axis-label">$19K</text>
          <text x="40" y="184" class="axis-label">$16K</text>

          <!-- Area fill -->
          <path :d="areaPathFull" fill="url(#lineGradient)" />

          <!-- Line -->
          <polyline
            :points="lineChartPointsFull"
            fill="none"
            stroke="url(#lineStrokeGradient)"
            stroke-width="3.5"
            stroke-linecap="round"
            stroke-linejoin="round"
            filter="url(#glowEffect)"
          />

          <!-- Data points -->
          <circle v-for="(point, index) in dataPointsFull" :key="index" :cx="point.x" :cy="point.y" r="7" fill="#7c3aed" stroke="#fff" stroke-width="3" class="data-point" />

          <!-- X-axis labels -->
          <text x="100" y="198" class="axis-label x-label">Sep 1</text>
          <text x="270" y="198" class="axis-label x-label">Sep 15</text>
          <text x="440" y="198" class="axis-label x-label">Oct 1</text>
          <text x="540" y="198" class="axis-label x-label">Oct 15</text>
        </svg>
      </div>
    </div>
  </section>
</template>

<script setup>
import { ref, computed } from 'vue'
import { onMounted } from 'vue'
import { getOverall } from '@/api/user'

// Portfolio data
const totalValue = ref(0)
const totalReturn = ref(0)
const riskLevel = ref('Null')

const pieColors = ['#6366f1', '#10b981', '#f59e0b', '#ec4899', '#8b5cf6', '#06b6d4', '#f97316', '#14b8a6', '#a855f7', '#0ea5e9']

const holdings = ref([
  // { symbol: 'BTC', fullName: 'Bitcoin', amount: 0.15, value: 10000, percentage: 40 },
  // { symbol: 'Null', fullName: 'Null', amount: 0, value: 0, percentage: 0 }
])

// Calculate solid pie chart slices
const pieSlices = computed(() => {
  if (!holdings.value || holdings.value.length === 0) return []
  const slices = []
  let currentAngle = -90
  const radius = 80

  holdings.value.forEach((item, index) => {
    const angle = (item.percentage / 100) * 360
    const startAngle = currentAngle
    const endAngle = currentAngle + angle

    const startRad = (startAngle * Math.PI) / 180
    const endRad = (endAngle * Math.PI) / 180

    const x1 = Math.cos(startRad) * radius
    const y1 = Math.sin(startRad) * radius
    const x2 = Math.cos(endRad) * radius
    const y2 = Math.sin(endRad) * radius

    const largeArc = angle > 180 ? 1 : 0
    const path = `M 0 0 L ${x1} ${y1} A ${radius} ${radius} 0 ${largeArc} 1 ${x2} ${y2} Z`

    slices.push({ path, color: pieColors[index % pieColors.length] })
    currentAngle = endAngle
  })

  return slices
})

// Line chart data
const historyData = ref([
  { date: '2025-09-01', value: 17500 },
  { date: '2025-09-15', value: 19500 },
  { date: '2025-10-01', value: 24000 },
  { date: '2025-10-15', value: 27000 }
])

// Full width line chart data points
const dataPointsFull = computed(() => {
  const minValue = 16000
  const maxValue = 28000
  const range = maxValue - minValue

  return historyData.value.map((item, index) => ({
    x: 100 + index * 150,
    y: 180 - ((item.value - minValue) / range) * 160
  }))
})

const lineChartPointsFull = computed(() => {
  return dataPointsFull.value.map(p => `${p.x},${p.y}`).join(' ')
})

const areaPathFull = computed(() => {
  const points = dataPointsFull.value
  if (points.length === 0) return ''

  let path = `M ${points[0].x} 180 L ${points[0].x} ${points[0].y}`
  points.forEach(p => {
    path += ` L ${p.x} ${p.y}`
  })
  path += ` L ${points[points.length - 1].x} 180 Z`
  return path
})

// 从后端获取数据
const getOverallHoldings = async () => {
  try {
    const res = await getOverall()
    totalValue.value = res.data.data.totalValue
    totalReturn.value = res.data.data.totalReturn
    riskLevel.value=res.data.data.riskLevel
    holdings.value=res.data.data.holdings
  } catch (err) {
    console.error('Failed to load', err)
  }
}
// 页面加载自动调用
onMounted(() => {
  getOverallHoldings()
})
</script>

<style scoped>
/* Content Section */
.content-section {
  animation: fadeIn 0.3s ease;
}

@keyframes fadeIn {
  from { opacity: 0; transform: translateY(10px); }
  to { opacity: 1; transform: translateY(0); }
}

/* Stats Row */
.stats-row {
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  gap: 20px;
  margin-bottom: 28px;
}

.stat-card {
  background: linear-gradient(145deg, #ffffff 0%, #fefefe 100%);
  border-radius: 20px;
  padding: 24px;
  display: flex;
  align-items: center;
  gap: 16px;
  box-shadow: 0 8px 32px rgba(139, 92, 246, 0.12), 0 2px 8px rgba(0, 0, 0, 0.04);
  transition: all 0.4s cubic-bezier(0.4, 0, 0.2, 1);
  border: 1px solid rgba(255, 255, 255, 0.8);
  position: relative;
}

.stat-card:hover {
  transform: translateY(-4px);
  box-shadow: 0 16px 48px rgba(139, 92, 246, 0.2), 0 4px 12px rgba(0, 0, 0, 0.06);
  border-color: rgba(139, 92, 246, 0.2);
}

.stat-icon-wrapper {
  width: 52px;
  height: 52px;
  border-radius: 16px;
  background: linear-gradient(135deg, #7c3aed 0%, #8b5cf6 100%);
  display: flex;
  align-items: center;
  justify-content: center;
  flex-shrink: 0;
  box-shadow: 0 4px 12px rgba(124, 58, 237, 0.3);
}

.stat-icon-wrapper svg {
  width: 24px;
  height: 24px;
  color: #ffffff;
}

.stat-icon-wrapper.warning {
  background: linear-gradient(135deg, #f59e0b 0%, #fbbf24 100%);
  box-shadow: 0 4px 12px rgba(245, 158, 11, 0.3);
}

.stat-icon-wrapper.accent {
  background: linear-gradient(135deg, #06b6d4 0%, #22d3ee 100%);
  box-shadow: 0 4px 12px rgba(6, 182, 212, 0.3);
}

.stat-content {
  display: flex;
  flex-direction: column;
  gap: 4px;
  flex: 1;
  min-width: 0;
}

.stat-label {
  font-size: 12px;
  font-weight: 600;
  text-transform: uppercase;
  letter-spacing: 0.5px;
  color: #6d28d9;
}

.stat-value {
  font-size: 26px;
  font-weight: 800;
  color: #1e293b;
  letter-spacing: -0.5px;
  line-height: 1.2;
}

.stat-value.moderate {
  background: linear-gradient(135deg, #f59e0b 0%, #ea580c 100%);
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
  background-clip: text;
}

.stat-unit {
  font-size: 16px;
  font-weight: 600;
  color: #8b5cf6;
}

.stat-badge {
  display: inline-flex;
  align-items: center;
  padding: 6px 12px;
  border-radius: 10px;
  font-size: 13px;
  font-weight: 700;
  position: absolute;
  top: 16px;
  right: 16px;
}

.stat-badge.positive {
  background: linear-gradient(135deg, #d1fae5 0%, #a7f3d0 100%);
  color: #059669;
}

/* Dashboard Card */
.dashboard-card {
  background: linear-gradient(145deg, #ffffff 0%, #fefefe 100%);
  border-radius: 28px;
  padding: 36px;
  display: grid;
  grid-template-columns: 340px 1fr;
  gap: 40px;
  box-shadow: 0 8px 40px rgba(139, 92, 246, 0.12), 0 2px 8px rgba(0, 0, 0, 0.04);
  margin-bottom: 28px;
  position: relative;
  overflow: hidden;
  transition: all 0.4s cubic-bezier(0.4, 0, 0.2, 1);
  border: 1px solid rgba(255, 255, 255, 0.8);
}

.dashboard-card:hover {
  transform: translateY(-3px);
  box-shadow: 0 20px 60px rgba(139, 92, 246, 0.18), 0 4px 12px rgba(0, 0, 0, 0.06);
  border-color: rgba(139, 92, 246, 0.15);
}

/* Pie Section */
.pie-section {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  position: relative;
  z-index: 1;
  gap: 24px;
  padding: 24px;
  background: linear-gradient(145deg, #7c3aed 0%, #8b5cf6 50%, #a78bfa 100%);
  border-radius: 24px;
  box-shadow: 0 8px 32px rgba(139, 92, 246, 0.3);
}

.pie-chart-wrapper {
  width: 200px;
  height: 200px;
  display: flex;
  align-items: center;
  justify-content: center;
  position: relative;
}

.pie-chart {
  width: 100%;
  height: 100%;
  filter: drop-shadow(0 8px 24px rgba(0, 0, 0, 0.3));
}

.pie-slice {
  transition: all 0.3s cubic-bezier(0.4, 0, 0.2, 1);
  transform-origin: center;
  cursor: pointer;
}

.pie-slice:hover {
  filter: brightness(1.15) saturate(1.1);
  transform: scale(1.02);
}

/* Pie Legend */
.pie-legend {
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 8px;
  width: 100%;
  padding: 0 8px;
}

.pie-legend-item {
  display: flex;
  align-items: center;
  gap: 10px;
  padding: 10px 14px;
  background: rgba(255, 255, 255, 0.05);
  border-radius: 12px;
  border: 1px solid rgba(255, 255, 255, 0.08);
  transition: all 0.2s ease;
}

.pie-legend-item:hover {
  background: rgba(255, 255, 255, 0.1);
  border-color: rgba(255, 255, 255, 0.15);
}

.pie-legend-dot {
  width: 10px;
  height: 10px;
  border-radius: 3px;
  flex-shrink: 0;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.2);
}

.pie-legend-label {
  font-size: 13px;
  font-weight: 600;
  color: #ffffff;
  flex: 1;
}

.pie-legend-value {
  font-size: 12px;
  color: rgba(255, 255, 255, 0.5);
  font-weight: 600;
}

/* Asset Section */
.asset-section {
  display: flex;
  flex-direction: column;
  position: relative;
  z-index: 1;
}

.asset-section-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 16px;
  padding-bottom: 12px;
  border-bottom: 1px solid rgba(139, 92, 246, 0.15);
}

.asset-section-title {
  font-size: 16px;
  font-weight: 700;
  color: #3730a3;
  margin: 0;
}

.asset-count {
  font-size: 12px;
  color: #6d28d9;
  background: linear-gradient(135deg, #ede9fe 0%, #e9e4fd 100%);
  padding: 5px 12px;
  border-radius: 20px;
  font-weight: 600;
  border: 1px solid rgba(167, 139, 250, 0.2);
}

/* Asset List with scroll */
.asset-list-scroll {
  display: flex;
  flex-direction: column;
  gap: 10px;
  max-height: 280px;
  overflow-y: auto;
  padding-right: 8px;
}

/* Custom scrollbar for asset list */
.asset-list-scroll::-webkit-scrollbar {
  width: 5px;
}

.asset-list-scroll::-webkit-scrollbar-track {
  background: rgba(109, 40, 217, 0.05);
  border-radius: 3px;
}

.asset-list-scroll::-webkit-scrollbar-thumb {
  background: linear-gradient(180deg, #a78bfa, #c4b5fd);
  border-radius: 3px;
}

.asset-list-scroll::-webkit-scrollbar-thumb:hover {
  background: linear-gradient(180deg, #8b5cf6, #a78bfa);
}

.asset-row {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 14px 18px;
  background: linear-gradient(135deg, #f8f7ff 0%, #f5f3ff 100%);
  border-radius: 14px;
  transition: all 0.3s ease;
  border: 1px solid rgba(139, 92, 246, 0.08);
}

.asset-row:hover {
  background: linear-gradient(135deg, #f3f0ff 0%, #ede9fe 100%);
  border-color: rgba(167, 139, 250, 0.2);
  transform: translateX(4px);
  box-shadow: 0 4px 16px rgba(139, 92, 246, 0.1);
}

.asset-left {
  display: flex;
  align-items: center;
  gap: 14px;
}

.asset-icon {
  width: 40px;
  height: 40px;
  border-radius: 12px;
  display: flex;
  align-items: center;
  justify-content: center;
  font-weight: 700;
  font-size: 15px;
  color: #ffffff;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.15);
  transition: all 0.3s ease;
}

.asset-row:hover .asset-icon {
  transform: scale(1.05);
}

.asset-info {
  display: flex;
  flex-direction: column;
  gap: 2px;
}

.asset-symbol {
  font-weight: 700;
  font-size: 14px;
  color: #3730a3;
}

.asset-name {
  font-size: 12px;
  color: #6d28d9;
  opacity: 0.7;
}

.asset-right {
  text-align: right;
  display: flex;
  flex-direction: column;
  gap: 2px;
}

.asset-value {
  font-weight: 700;
  font-size: 14px;
  color: #3730a3;
}

.asset-percent {
  font-size: 12px;
  color: #8b5cf6;
  font-weight: 600;
}

/* Line Chart Full Width */
.line-card-full {
  margin-bottom: 24px;
}

.line-chart-container-full {
  width: 100%;
  height: 220px;
}

.chart-stats {
  display: flex;
  gap: 16px;
}

.chart-stat {
  display: flex;
  flex-direction: column;
  align-items: flex-end;
  padding: 12px 18px;
  background: linear-gradient(145deg, #f8f7ff 0%, #f5f3ff 100%);
  border-radius: 14px;
  border: 1px solid rgba(139, 92, 246, 0.1);
  transition: all 0.3s ease;
}

.chart-stat:hover {
  background: linear-gradient(145deg, #f3f0ff 0%, #ede9fe 100%);
  border-color: rgba(167, 139, 250, 0.25);
  transform: translateY(-2px);
}

.chart-stat-label {
  font-size: 10px;
  color: #7c3aed;
  text-transform: uppercase;
  letter-spacing: 0.8px;
  font-weight: 600;
  margin-bottom: 2px;
}

.chart-stat-value {
  font-size: 18px;
  font-weight: 800;
  color: #1e293b;
  letter-spacing: -0.3px;
}

.chart-stat-value.highlight {
  background: linear-gradient(135deg, #7c3aed 0%, #a855f7 100%);
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
  background-clip: text;
}

.chart-stat-value.positive {
  color: #10b981;
}

/* Card Styles */
.chart-card {
  background: linear-gradient(145deg, #ffffff 0%, #fefefe 100%);
  border-radius: 24px;
  padding: 28px;
  box-shadow: 0 8px 40px rgba(139, 92, 246, 0.12), 0 2px 8px rgba(0, 0, 0, 0.04);
  border: 1px solid rgba(255, 255, 255, 0.8);
  transition: all 0.4s cubic-bezier(0.4, 0, 0.2, 1);
  position: relative;
  overflow: hidden;
}

.chart-card:hover {
  box-shadow: 0 20px 60px rgba(139, 92, 246, 0.18), 0 4px 12px rgba(0, 0, 0, 0.06);
  border-color: rgba(139, 92, 246, 0.15);
  transform: translateY(-3px);
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 24px;
  padding-bottom: 16px;
  border-bottom: 1px solid rgba(139, 92, 246, 0.15);
}

.card-title {
  font-size: 18px;
  font-weight: 700;
  color: #3730a3;
  margin: 0;
  letter-spacing: -0.3px;
}

.line-chart {
  width: 100%;
  height: 100%;
}

.grid-line {
  stroke: #f1f5f9;
  stroke-width: 1;
}

.axis-label {
  font-size: 11px;
  fill: #94a3b8;
  text-anchor: end;
}

.axis-label.x-label {
  text-anchor: middle;
}

.data-point {
  transition: all 0.3s ease;
  filter: drop-shadow(0 2px 4px rgba(109, 40, 217, 0.3));
}

.data-point:hover {
  r: 10;
  filter: drop-shadow(0 4px 8px rgba(109, 40, 217, 0.5));
}

/* Responsive Design */
@media (max-width: 1024px) {
  .stats-row {
    grid-template-columns: 1fr;
    gap: 16px;
  }

  .stat-card {
    padding: 20px;
  }

  .stat-badge {
    position: static;
    margin-left: auto;
  }

  .dashboard-card {
    grid-template-columns: 1fr;
    gap: 28px;
    padding: 28px;
  }

  .pie-section {
    order: -1;
    width: 100%;
    max-width: 400px;
    margin: 0 auto;
  }
}

@media (max-width: 768px) {
  .chart-stats {
    flex-wrap: wrap;
    gap: 16px;
  }

  .card-header {
    flex-direction: column;
    align-items: flex-start;
    gap: 12px;
  }
}
</style>
