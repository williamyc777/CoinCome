<template>
  <!-- toolbar -->
  <div class="table-toolbar">
    <button class="toolbar-btn"><span class="icon-globe">🌐</span> All assets ▼</button>
    <button class="toolbar-btn">1D ▼</button>
    <button class="toolbar-btn">USD ▼</button>
    <button class="toolbar-btn">10 rows ▼</button>
  </div>
  <!-- table -->
  <table class="crypto-table">
    <thead>
      <tr>
        <th class="star-header"></th>
        <th class="left">Asset</th>
        <th class="right">Price</th>
        <th class="center">Chart</th>
        <th class="right">Change</th>
      </tr>
    </thead>
    <tbody>
      <tr v-for="(coin, idx) in coins" :key="coin.symbol">
        <td class="star-cell">
          <span
            class="star"
            :class="{ active: starred[idx] }"
            @click="toggleStar(idx)"
            title="Favorite"
          >
            {{ starred[idx] ? '★' : '☆' }}
          </span>
        </td>
        <td class="asset-cell">
          <img :src="coin.icon" :alt="coin.name" class="icon" />
          <div class="asset-info">
            <span class="name">{{ coin.name }}</span>
            <span class="symbol">{{ coin.symbol }}</span>
          </div>
        </td>
        <td class="right price">{{ coin.price }}</td>
        <td class="center">
          <img :src="coin.chart" alt="chart" class="chart" />
        </td>
        <td class="right">
          <span :class="['change', coin.change > 0 ? 'up' : coin.change < 0 ? 'down' : '']">
            <span v-if="coin.change > 0">↑</span>
            <span v-else-if="coin.change < 0">↓</span>
            {{ Math.abs(coin.change) }}%
          </span>
        </td>
      </tr>
    </tbody>
  </table>
</template>

<script setup>
import { ref } from 'vue'

const coins = [
  {
    name: 'Bitcoin',
    symbol: 'BTC',
    icon: 'https://cryptologos.cc/logos/bitcoin-btc-logo.png',
    price: '$122,818.51',
    chart: 'https://dummyimage.com/60x20/orange/fff.png&text=chart', // 可替换为SVG或更美观的图
    change: 0.55
  },
  {
    name: 'Ethereum',
    symbol: 'ETH',
    icon: 'https://cryptologos.cc/logos/ethereum-eth-logo.png',
    price: '$4,501.25',
    chart: 'https://dummyimage.com/60x20/4f6ef7/fff.png&text=chart',
    change: 0.29
  },
  {
    name: 'Tether',
    symbol: 'USDT',
    icon: 'https://cryptologos.cc/logos/tether-usdt-logo.png',
    price: '$1.00',
    chart: 'https://dummyimage.com/60x20/2ecc71/fff.png&text=chart',
    change: -0.02
  },
  {
    name: 'XRP',
    symbol: 'XRP',
    icon: 'https://cryptologos.cc/logos/xrp-xrp-logo.png',
    price: '$2.96',
    chart: 'https://dummyimage.com/60x20/000/fff.png&text=chart',
    change: 0.27
  }
]

const starred = ref(coins.map(() => false))

function toggleStar(idx) {
  starred.value[idx] = !starred.value[idx]
}
</script>

<style scoped>

/* toolbar */
.table-toolbar {
  display: flex;
  gap: 16px;
  margin-bottom: 18px;
  margin-left: 8px;
  margin-top: 32px; /* 新增：与上方拉开距离 */
}
.toolbar-btn {
  background: #f5f6fa;
  border: none;
  border-radius: 16px;
  padding: 8px 18px;
  font-size: 15px;
  color: #222;
  font-weight: 500;
  cursor: pointer;
  display: flex;
  align-items: center;
  transition: background 0.2s;
}
.toolbar-btn:hover {
  background: #e9eaf0;
}
.icon-globe {
  margin-right: 6px;
}

/* table */
.crypto-table {
  width: 100%;
  border-collapse: separate;
  border-spacing: 0 12px;
  background: #fff;
  font-family: 'Inter', Arial, sans-serif;
}
.crypto-table th {
  color: #888;
  font-weight: 600;
  font-size: 15px;
  background: #fff;
  border: none;
  padding-bottom: 10px;
}
.crypto-table td {
  background: #fff;
  border: none;
  font-size: 16px;
  padding: 18px 8px;
  vertical-align: middle;
}
.asset-cell {
  display: flex;
  align-items: center;
  gap: 14px;
}
.icon {
  width: 36px;
  height: 36px;
  border-radius: 50%;
  background: #f5f5f5;
}
.asset-info {
  display: flex;
  flex-direction: column;
}
.name {
  font-weight: bold;
  font-size: 16px;
  color: #222;
}
.symbol {
  font-size: 13px;
  color: #aaa;
  margin-top: 2px;
}
.price {
  font-family: 'Menlo', monospace;
  font-weight: 500;
}
.chart {
  width: 60px;
  height: 20px;
  display: block;
  margin: 0 auto;
}
.change {
  font-weight: bold;
  font-size: 15px;
}
.up {
  color: #16c784;
}
.down {
  color: #ea3943;
}
.left {
  text-align: left;
}
.right {
  text-align: right;
}
.center {
  text-align: center;
}
tbody tr {
  box-shadow: 0 2px 8px 0 rgba(60,60,60,0.04);
  border-radius: 12px;
  transition: box-shadow 0.2s;
}
tbody tr:hover {
  box-shadow: 0 4px 16px 0 rgba(60,60,60,0.10);
}

/* stars */
.star-header, .star-cell {
  width: 36px;
  text-align: center;
}
.star {
  font-size: 20px;
  color: #bfc5d2;
  cursor: pointer;
  user-select: none;
  transition: color 0.2s;
}
.star.active {
  color: #f7b500;
  text-shadow: 0 1px 4px #ffe06644;
}
</style>