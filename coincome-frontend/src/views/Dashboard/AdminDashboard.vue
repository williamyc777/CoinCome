<template>
  <AdminLayout>
    <div class="admin-container">
      <!-- 顶部 Banner -->
      <section class="banner-section">
        <h1 class="banner-title">Admin Console</h1>
        <p class="banner-subtitle">
          Manage supported exchanges, currencies and users for all BitCome users.
        </p>
      </section>

      <!-- 主体：三张卡片（单列向下排） -->
      <section class="cards-section">
        <!-- ========= Card 1: Manage Exchanges ========= -->
        <div class="card">
          <div class="card-header">
            <div class="card-title-area">
              <h2 class="card-title">Manage Exchanges</h2>
              <p class="card-subtitle">Add new exchanges for users to select in CSV uploads.</p>
            </div>
          </div>

          <!-- 新增交易所表单 -->
          <form class="form" @submit.prevent="handleAddExchange">
            <div class="form-row">
              <div class="form-item">
                <label class="form-label">Exchange Name</label>
                <input
                  v-model="exchangeForm.name"
                  class="form-input"
                  type="text"
                  placeholder="e.g. Binance, Coinbase, OKX"
                  required
                />
              </div>
            </div>

            <div class="form-row">
              <div class="form-item">
                <label class="form-label">Link (Optional)</label>
                <input
                  v-model="exchangeForm.link"
                  class="form-input"
                  type="url"
                  placeholder="https://www.binance.com"
                />
              </div>
            </div>

            <div class="form-actions">
              <button class="primary-btn" type="submit" :disabled="exLoading">
                <span v-if="!exLoading">Add Exchange</span>
                <span v-else>Adding...</span>
              </button>
              <span v-if="exError" class="error-text">{{ exError }}</span>
              <span v-if="exSuccess" class="success-text">{{ exSuccess }}</span>
            </div>
          </form>

          <!-- 交易所列表 -->
          <div class="list-header">
            <h3 class="list-title">Existing Exchanges</h3>
          </div>
          <div class="table-wrapper">
            <table class="admin-table">
              <thead>
                <tr>
                  <th class="left">ID</th>
                  <th class="left">Name</th>
                  <th class="left">Link</th>
                  <th class="left">Created At</th>
                </tr>
              </thead>
              <tbody>
                <tr v-if="exchangeLoading">
                  <td colspan="4">
                    <div class="skeleton-row"></div>
                    <div class="skeleton-row"></div>
                    <div class="skeleton-row"></div>
                  </td>
                </tr>
                <tr v-for="ex in exchanges" :key="ex.exchangeId" class="clickable-row">
                  <td class="left">{{ ex.exchangeId }}</td>
                  <td class="left">{{ ex.exchangeName }}</td>
                  <td class="left">
                    <a
                      v-if="ex.exchangeLink"
                      class="table-link"
                      :href="ex.exchangeLink"
                      target="_blank"
                      rel="noopener noreferrer"
                    >
                      {{ ex.exchangeLink }}
                    </a>
                    <span v-else>-</span>
                  </td>
                  <td class="left">{{ formatDateTime(ex.createdAt) }}</td>
                </tr>
                <tr v-if="!exchangeLoading && !exchanges.length">
                  <td colspan="4" class="empty-text">No exchanges yet. Add one above.</td>
                </tr>
              </tbody>
            </table>
          </div>
        </div>

        <!-- ========= Card 2: Manage Coins ========= -->
        <div class="card">
          <div class="card-header">
            <div class="card-title-area">
              <h2 class="card-title">Manage Currencies</h2>
              <p class="card-subtitle">Add new coins/tokens that can appear in transactions.</p>
            </div>
          </div>

          <!-- 新增币种表单 -->
          <form class="form" @submit.prevent="handleAddCoin">
            <div class="form-row">
              <div class="form-item">
                <label class="form-label">Coin Symbol</label>
                <input
                  v-model="coinForm.name"
                  class="form-input"
                  type="text"
                  placeholder="e.g. BTC, ETH, SOL"
                  required
                />
              </div>
            </div>

            <div class="form-row">
              <div class="form-item">
                <label class="form-label">Coin Type (Optional)</label>
                <input
                  v-model="coinForm.type"
                  class="form-input"
                  type="text"
                  placeholder="Spot / Futures / Stable / etc."
                />
              </div>
            </div>
            <!-- ⭐ 批量导入 JSON 按钮 -->
              <div class="import-section">
                <button
                  class="secondary-btn"
                  type="button"
                  @click="triggerJsonSelect"
                  :disabled="importingJson"
                >
                  <span v-if="!importingJson">Import Coins from JSON</span>
                  <span v-else>Importing...</span>
                </button>

                <input
                  ref="jsonInput"
                  type="file"
                  accept=".json,application/json"
                  style="display: none"
                  @change="handleJsonSelected"
                />

                <span v-if="importError" class="error-text">{{ importError }}</span>
                <span v-if="importSuccess" class="success-text">{{ importSuccess }}</span>
              </div>

            <div class="form-actions">
              <button class="primary-btn" type="submit" :disabled="coinLoading">
                <span v-if="!coinLoading">Add Coin</span>
                <span v-else>Adding...</span>
              </button>
              <span v-if="coinError" class="error-text">{{ coinError }}</span>
              <span v-if="coinSuccess" class="success-text">{{ coinSuccess }}</span>
            </div>
          </form>

          <!-- 币种列表 -->
          <div class="list-header">
            <h3 class="list-title">Existing Currencies</h3>
          </div>
          <div class="table-wrapper">
            <table class="admin-table">
              <thead>
                <tr>
                  <th class="left">ID</th>
                  <th class="left">Symbol</th>
                  <th class="left">Type</th>
                  <th class="left">Updated At</th>
                </tr>
              </thead>
              <tbody>
                <tr v-if="coinListLoading">
                  <td colspan="4">
                    <div class="skeleton-row"></div>
                    <div class="skeleton-row"></div>
                    <div class="skeleton-row"></div>
                  </td>
                </tr>
                <tr v-for="c in coins" :key="c.coinId" class="clickable-row">
                  <td class="left">{{ c.coinId }}</td>
                  <td class="left">{{ c.coinName }}</td>
                  <td class="left">{{ c.coinType || '-' }}</td>
                  <td class="left">{{ formatDateTime(c.updatedAt) }}</td>
                </tr>
                <tr v-if="!coinListLoading && !coins.length">
                  <td colspan="4" class="empty-text">No coins yet. Add one above.</td>
                </tr>
              </tbody>
            </table>
          </div>
        </div>

        <!-- ========= Card 3: Manage Users ========= -->
        <div class="card">
          <div class="card-header">
            <div class="card-title-area">
              <h2 class="card-title">Manage Users</h2>
              <p class="card-subtitle">
                View and edit user accounts. Username and email must be unique.
              </p>
            </div>
          </div>

          <div class="list-header">
            <h3 class="list-title">All Users</h3>
          </div>

          <div class="table-wrapper">
            <table class="admin-table">
              <thead>
                <tr>
                  <th class="left">ID</th>
                  <th class="left">Username</th>
                  <th class="left">Email</th>
                  <th class="left">Role</th>
                  <th class="left">Created At</th>
                  <th class="left">Updated At</th>
                  <th class="left">Action</th>
                </tr>
              </thead>
              <tbody>
                <tr v-if="userListLoading">
                  <td colspan="7">
                    <div class="skeleton-row"></div>
                    <div class="skeleton-row"></div>
                    <div class="skeleton-row"></div>
                  </td>
                </tr>

                <tr
                  v-for="u in users"
                  :key="u.userId"
                  class="clickable-row"
                >
                  <td class="left">{{ u.userId }}</td>
                  <td class="left">{{ u.username }}</td>
                  <td class="left">{{ u.email }}</td>
                  <td class="left">
                    <span
                      class="role-tag"
                      :class="u.isAdmin ? 'role-admin' : 'role-normal'"
                    >
                      {{ u.isAdmin ? 'Admin' : 'User' }}
                    </span>
                  </td>
                  <td class="left">{{ formatDateTime(u.createdAt) }}</td>
                  <td class="left">{{ formatDateTime(u.updatedAt) }}</td>
                  <td class="left">
                    <button class="link-btn" @click="openEditUser(u)">Edit</button>
                  </td>
                </tr>

                <tr v-if="!userListLoading && !users.length">
                  <td colspan="7" class="empty-text">No users.</td>
                </tr>
              </tbody>
            </table>
          </div>
        </div>
      </section>

      <!-- ===== 编辑用户弹窗 ===== -->
      <el-dialog
        v-model="editDialogVisible"
        title="Edit User"
        width="420px"
      >
        <div v-if="editingUser">
          <div class="dialog-row">
            <label class="dialog-label">User ID</label>
            <div class="dialog-text">{{ editingUser.userId }}</div>
          </div>

          <div class="dialog-row">
            <label class="dialog-label">Username</label>
            <el-input
              v-model="editForm.username"
              placeholder="Username"
              size="small"
            />
          </div>

          <div class="dialog-row">
            <label class="dialog-label">Email</label>
            <el-input
              v-model="editForm.email"
              placeholder="Email"
              size="small"
            />
          </div>

          <!-- 如果你不想在这里改密码，可以把下面这块删掉 -->
          <div class="dialog-row">
            <label class="dialog-label">Password (hash)</label>
            <el-input
              v-model="editForm.newPassword"
              placeholder="Leave empty to keep current"
              size="small"
            />
          </div>

          <p v-if="editError" class="error-text" style="margin-top: 8px;">
            {{ editError }}
          </p>
          <p v-if="editSuccess" class="success-text" style="margin-top: 8px;">
            {{ editSuccess }}
          </p>
        </div>

        <template #footer>
          <span class="dialog-footer">
            <el-button @click="editDialogVisible = false">Cancel</el-button>
            <el-button
              type="primary"
              :loading="savingEdit"
              @click="handleSaveUser"
            >
              Save
            </el-button>
          </span>
        </template>
      </el-dialog>
    </div>
  </AdminLayout>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import AdminLayout from '@/views/Dashboard/AdminLayout.vue'
import {
  fetchExchanges,
  addExchange,
  fetchCoins,
  addCoin,
  fetchAdminUsers,
  updateAdminUser,
  importCoinsFromJson
} from '@/api/admin'
import { ElMessage } from 'element-plus'

/** ========= 通用工具 ========= */
const formatDateTime = (val) => {
  if (!val) return '-'
  try {
    return new Date(val).toLocaleString()
  } catch {
    return val
  }
}

/** ========= 1. 交易所相关 ========= */
const exchanges = ref([])
const exchangeLoading = ref(false)
const exLoading = ref(false)
const exError = ref(null)
const exSuccess = ref(null)
const exchangeForm = ref({
  name: '',
  link: '',
})

const loadExchanges = async () => {
  exchangeLoading.value = true
  try {
    const res = await fetchExchanges()
    exchanges.value = Array.isArray(res.data) ? res.data : res.data.data || []
  } catch (err) {
    console.error('Failed to load exchanges', err)
  } finally {
    exchangeLoading.value = false
  }
}

const handleAddExchange = async () => {
  exError.value = null
  exSuccess.value = null
  if (!exchangeForm.value.name.trim()) {
    exError.value = 'Exchange name is required'
    return
  }

  try {
    exLoading.value = true
    await addExchange({
      exchangeName: exchangeForm.value.name.trim(),
      exchangeLink: exchangeForm.value.link.trim() || null,
    })
    exSuccess.value = 'Exchange added successfully'
    exchangeForm.value.name = ''
    exchangeForm.value.link = ''
    await loadExchanges()
  } catch (err) {
    console.error('Failed to add exchange', err)
    exError.value =
      err?.response?.data?.message ||
      err?.response?.data?.msg ||
      'Failed to add exchange'
  } finally {
    exLoading.value = false
    setTimeout(() => {
      exSuccess.value = null
      exError.value = null
    }, 3000)
  }
}

/** ========= 2. 币种相关 ========= */
const coins = ref([])
const coinListLoading = ref(false)
const coinLoading = ref(false)
const coinError = ref(null)
const coinSuccess = ref(null)
const coinForm = ref({
  name: '',
  type: '',
})

const loadCoins = async () => {
  coinListLoading.value = true
  try {
    const res = await fetchCoins()
    coins.value = Array.isArray(res.data) ? res.data : res.data.data || []
  } catch (err) {
    console.error('Failed to load coins', err)
  } finally {
    coinListLoading.value = false
  }
}

const handleAddCoin = async () => {
  coinError.value = null
  coinSuccess.value = null
  if (!coinForm.value.name.trim()) {
    coinError.value = 'Coin symbol is required'
    return
  }

  try {
    coinLoading.value = true
    await addCoin({
      coinName: coinForm.value.name.trim(),
      coinType: coinForm.value.type.trim() || null,
    })
    coinSuccess.value = 'Coin added successfully'
    coinForm.value.name = ''
    coinForm.value.type = ''
    await loadCoins()
  } catch (err) {
    console.error('Failed to add coin', err)
    coinError.value =
      err?.response?.data?.message ||
      err?.response?.data?.msg ||
      'Failed to add coin'
  } finally {
    coinLoading.value = false
    setTimeout(() => {
      coinSuccess.value = null
      coinError.value = null
    }, 3000)
  }
}

const jsonInput = ref(null)
const importingJson = ref(false)
const importError = ref(null)
const importSuccess = ref(null)

// 点击按钮，打开文件选择框
const triggerJsonSelect = () => {
  importError.value = null
  importSuccess.value = null
  if (jsonInput.value) {
    jsonInput.value.value = ''   // 清空之前的选择
    jsonInput.value.click()
  }
}

// 选择完文件后的处理
const handleJsonSelected = async (e) => {
  const file = e.target.files && e.target.files[0]
  if (!file) return

  importingJson.value = true
  importError.value = null
  importSuccess.value = null

  try {
    const formData = new FormData()
    formData.append('file', file)

    await importCoinsFromJson(formData)   // 调后端导入接口
    importSuccess.value = 'Coins imported successfully'

    // 重新拉一遍币种列表
    await loadCoins()
  } catch (err) {
    console.error('Import coins from JSON failed', err)
    importError.value =
      err?.response?.data?.msg ||
      err?.response?.data?.message ||
      'Import failed'
  } finally {
    importingJson.value = false
    setTimeout(() => {
      importError.value = null
      importSuccess.value = null
    }, 3000)
  }
}

/** ========= 3. 用户管理相关 ========= */
const users = ref([])
const userListLoading = ref(false)

const editDialogVisible = ref(false)
const editingUser = ref(null)
const editForm = ref({
  username: '',
  email: '',
  newPassword: '',
})
const savingEdit = ref(false)
const editError = ref('')
const editSuccess = ref('')

const loadUsers = async () => {
  userListLoading.value = true
  try {
    const res = await fetchAdminUsers()
    users.value = Array.isArray(res.data) ? res.data : res.data.data || []
  } catch (err) {
    console.error('Failed to load users', err)
  } finally {
    userListLoading.value = false
  }
}



const openEditUser = (u) => {
  editingUser.value = { ...u }
  editForm.value = {
    username: u.username || '',
    email: u.email || '',
    newPassword: '', 
  }
  editError.value = ''
  editSuccess.value = ''
  editDialogVisible.value = true
}

const handleSaveUser = async () => {
  if (!editingUser.value) return

  if (!editForm.value.username.trim()) {
    editError.value = 'Username is required'
    return
  }
  if (!editForm.value.email.trim()) {
    editError.value = 'Email is required'
    return
  }

  savingEdit.value = true
  editError.value = ''
  editSuccess.value = ''

  try {
    const payload = {
      username: editForm.value.username.trim(),
      email: editForm.value.email.trim(),
    }

   const newPwd = editForm.value.newPassword
    if (newPwd && newPwd.trim().length > 0) {
      payload.newPassword = newPwd.trim()
    }


    const res = await updateAdminUser(editingUser.value.userId, payload)

    // 🌟 后端用 Result 封装：code === 1 表示成功
    if (!res || !res.data || res.data.code !== 1) {
      const msg =
        res?.data?.msg ||
        res?.data?.message ||
        'Update failed (username or email may already exist)'
      editError.value = msg
      ElMessage.error(msg)      // 弹窗提示
      return                    // 直接返回，不走后面的成功逻辑
    }

    editSuccess.value = 'User updated'
    ElMessage.success('User updated successfully')

    // 刷新列表
    await loadUsers()

    // 关闭 dialog
    editDialogVisible.value = false
  } catch (err) {
    console.error('Failed to update user', err)
    const msg =
      err?.response?.data?.message ||
      err?.response?.data?.msg ||
      err?.message ||
      'Update failed'
    editError.value = msg
  } finally {
    savingEdit.value = false
  }
}

/** ========= 页面加载 ========= */
onMounted(() => {
  loadExchanges()
  loadCoins()
  loadUsers()
})
</script>

<style scoped>
.admin-container {
  width: 100%;
  max-width: 1400px;
  margin: 0 auto;
  padding: 0 48px 60px 48px;
  box-sizing: border-box;
}

/* Banner 风格复用 Dashboard */
.banner-section {
  text-align: center;
  padding: 40px 0 40px 0;
}

.banner-title {
  font-size: 40px;
  font-weight: 700;
  color: #3a2a68;
  margin: 0 0 12px 0;
}

.banner-subtitle {
  font-size: 16px;
  color: #4f458d;
  margin: 0;
}

/* 单列卡片布局 */
.cards-section {
  display: grid;
  grid-template-columns: 1fr;
  gap: 24px;
}

/* 通用卡片 */
.card {
  background: #ffffff;
  border-radius: 24px;
  padding: 32px 32px 28px;
  box-shadow: 0 4px 16px rgba(108, 93, 211, 0.12);
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: flex-start;
  margin-bottom: 16px;
}

.card-title-area {
  display: flex;
  flex-direction: column;
  gap: 4px;
}

.card-title {
  font-size: 22px;
  font-weight: 700;
  color: #3a2a68;
  margin: 0;
}

.card-subtitle {
  font-size: 14px;
  color: #7c6e9f;
  margin: 0;
}

/* 表单区域 */
.form {
  margin-bottom: 20px;
}

.form-row {
  display: flex;
  gap: 16px;
  margin-bottom: 12px;
}

.form-item {
  flex: 1;
  display: flex;
  flex-direction: column;
  gap: 6px;
}

.form-label {
  font-size: 13px;
  color: #7c6e9f;
  font-weight: 600;
}

.form-input {
  border-radius: 12px;
  border: 1px solid #e2e8f0;
  padding: 10px 12px;
  font-size: 14px;
  outline: none;
  transition: border-color 0.15s ease, box-shadow 0.15s ease;
}

.form-input:focus {
  border-color: #6c5dd3;
  box-shadow: 0 0 0 1px rgba(108, 93, 211, 0.15);
}

/* 按钮 & 提示 */
.form-actions {
  display: flex;
  align-items: center;
  gap: 12px;
  margin-top: 4px;
}

.primary-btn {
  border: none;
  border-radius: 999px;
  background: #6c5dd3;
  color: #fff;
  font-size: 14px;
  font-weight: 600;
  padding: 10px 20px;
  cursor: pointer;
  transition: background 0.15s ease, transform 0.1s ease;
}

.primary-btn:hover:not(:disabled) {
  background: #5a4bc2;
  transform: translateY(-1px);
}

.primary-btn:disabled {
  opacity: 0.7;
  cursor: default;
}

.error-text {
  font-size: 13px;
  color: #ef4444;
}

.success-text {
  font-size: 13px;
  color: #10b981;
}

/* 表格 */
.list-header {
  margin-top: 12px;
  margin-bottom: 8px;
}

.list-title {
  font-size: 15px;
  font-weight: 600;
  color: #3a2a68;
  margin: 0;
}

.table-wrapper {
  border-radius: 18px;
  background: #f9f8fe;
  padding: 8px 12px;
  max-height: 360px;
  overflow: auto;
}

.admin-table {
  width: 100%;
  border-collapse: separate;
  border-spacing: 0 4px;
  font-size: 13px;
}

.admin-table thead th {
  text-align: left;
  padding: 8px 10px;
  color: #7c6e9f;
  font-weight: 600;
}

.admin-table tbody tr {
  background: #ffffff;
  transition: background 0.15s ease;
}

.admin-table tbody tr.clickable-row:hover {
  background: #f3edff;
}

.admin-table td {
  padding: 8px 10px;
  color: #3a2a68;
}

.left {
  text-align: left;
}

.empty-text {
  text-align: center;
  color: #94a3b8;
  padding: 16px 0;
}

.table-link {
  color: #6c5dd3;
  text-decoration: none;
}

.table-link:hover {
  text-decoration: underline;
}

/* 小按钮 / 标签 */
.link-btn {
  border: none;
  background: transparent;
  color: #6c5dd3;
  font-size: 13px;
  font-weight: 600;
  cursor: pointer;
  padding: 0;
}

.link-btn:hover {
  text-decoration: underline;
}

.role-tag {
  display: inline-block;
  padding: 2px 8px;
  border-radius: 999px;
  font-size: 11px;
  font-weight: 600;
}

.role-admin {
  background: #ede9fe;
  color: #5b21b6;
}

.role-normal {
  background: #e5e7eb;
  color: #374151;
}

/* Skeleton 加载 */
.skeleton-row {
  height: 18px;
  border-radius: 8px;
  background: #e5e7eb;
  animation: pulse 1.4s infinite ease-in-out;
  margin-bottom: 6px;
}

@keyframes pulse {
  0% { opacity: 0.6; }
  50% { opacity: 1; }
  100% { opacity: 0.6; }
}

/* 编辑弹窗 */
.dialog-row {
  margin-bottom: 12px;
}

.dialog-label {
  display: block;
  font-size: 13px;
  font-weight: 600;
  color: #4b5563;
  margin-bottom: 4px;
}

.dialog-text {
  font-size: 13px;
  color: #111827;
}

.dialog-footer {
  display: flex;
  justify-content: flex-end;
  gap: 8px;
}

/* 响应式 */
@media (max-width: 1024px) {
  .admin-container {
    padding: 0 24px 40px 24px;
  }
  .cards-section {
    grid-template-columns: 1fr;
  }
}

@media (max-width: 768px) {
  .admin-container {
    padding: 0 16px 40px 16px;
  }
}

.import-section {
  margin: 8px 0 12px;
  display: flex;
  align-items: center;
  gap: 12px;
}

/* 次级按钮 */
.secondary-btn {
  border-radius: 999px;
  border: 1px solid #cbd5e1;
  background: #f8fafc;
  color: #475569;
  font-size: 13px;
  font-weight: 500;
  padding: 8px 16px;
  cursor: pointer;
  transition: background 0.15s ease, border-color 0.15s ease,
    transform 0.1s ease;
}

.secondary-btn:hover:not(:disabled) {
  background: #e2e8f0;
  border-color: #94a3b8;
  transform: translateY(-1px);
}

.secondary-btn:disabled {
  opacity: 0.7;
  cursor: default;
}

</style>
