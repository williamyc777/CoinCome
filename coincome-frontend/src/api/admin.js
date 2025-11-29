// src/api/admin.js
import http from './http'

// 获取交易所列表
export function fetchExchanges() {
  return http.get('/api/admin/exchanges')
}

// 新增交易所
export function addExchange(payload) {
  // payload: { exchangeName, exchangeLink }
  return http.post('/api/admin/exchanges', payload)
}

// 获取币种列表
export function fetchCoins() {
  return http.get('/api/admin/coins')
}

// 新增币种
export function addCoin(payload) {
  // payload: { coinName, coinType }
  return http.post('/api/admin/coins', payload)
}

// 从 JSON 文件导入币种
export function importCoinsFromJson(formData) {
  return http.post('/api/admin/coin/import-json-file', formData, {
    headers: {
      'Content-Type': 'multipart/form-data',
    },
  })
}

// 获取用户列表
export function fetchAdminUsers() {
  return http.get('/api/admin/users')
}

// 更新用户
export function updateAdminUser(userId, payload) {
  return http.put(`/api/admin/users/${userId}`, payload)
}
