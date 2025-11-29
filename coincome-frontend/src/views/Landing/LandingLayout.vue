<template>
    <div class="landing-root">
        <div class="landing-inner">
            <!-- Top Navigation Bar -->
            <header class="header">

                <div class="header-left">
                    <div @click="goToDashboard" class="logo-link">
                        <img :src="IconSvg" alt="logo" class="logo" />
                        <span class="logo-text">Bitcome</span>
                    </div>
                </div>
                
                <div class="header-center">
                    <nav class="nav">
                        <a @click = "goToDashboard">Dashboard</a>
                        <a @click = "goToUpload">Upload CSV</a>
                        <a @click = "goToPortfolio">My portfolio</a>
                        <a @click = "goToRecommended">Recommendations</a>
                        <a @click = "goToMarket">Market</a>
                        <a @click="goToTransactions">Transactions</a>
                    </nav>
                </div>
                
                <div class="header-right">
                <div class="header-right">
                <!-- 已登录状态 -->
                <div v-if="isLoggedIn" class="user-box">
                    <span class="user-name">Hi, {{ displayName }}</span>
                    <button class="logout-btn" @click="handleLogout">Log out</button>
                </div>

                <!-- 未登录状态 -->
                <button v-else class="login-btn" @click="goToSignin">
                    Sign in
                </button>
                </div>
                </div>

            </header>
            <main class="main-content">
                <slot />
            </main>
            <footer class="landing-footer">
                <span class="footer-copyright">© 2025 Bitcome. All rights reserved.</span>
            </footer>
        </div>
    </div>
</template>

<script setup>
import { computed } from 'vue'
import { useRouter } from 'vue-router'
import IconSvg from '@/assets/icon_d.svg'
import { useUserStore } from '@/stores/user'

const router = useRouter()
const userStore = useUserStore()


const isLoggedIn = computed(() => !!userStore.token || !!userStore.user)
const displayName = computed(() => userStore.user?.username || 'User')

const goToDashboard = () => {
  router.push('/dashboard');
};

const goToUpload = () => {
  router.push('/upload');
};

const goToPortfolio = () => {
  router.push('/portfolio');
};

const goToMarket = () => {
    router.push('/market');
}

const goToRecommended = () => {
    router.push('/portfolio/recommended');
}

const goToTransactions = () => {
  router.push('/transactions'); 
};

const goToSignin = () => {
  router.push('/landing');
};

const handleLogout = () => {
    userStore.logout();

    if (router.currentRoute.value.name !== 'landing') {
        router.push({ name: 'landing' })
    }  

}

</script>

<style scoped>
.landing-root {
    min-height: 100vh;
    font-family: 'Inter', Arial, sans-serif;
    background: radial-gradient(ellipse at top, #e8d5ff 0%, #f0f1ff 30%, #e0e3f7 65%, #d0d4ed 100%);
    position: relative;
    overflow-x: hidden;
}


.landing-inner {
    position: relative;
    width: 100%;
    margin: 0 auto;
    padding: 0 40px;
    z-index: 1;
    min-height: 100vh;
    display: flex;
    flex-direction: column;
}

.header {
    display: grid;
    grid-template-columns: 1fr auto 1fr;
    align-items: center;
    padding: 30px 0;
    min-height: 60px;
}

.header-left {
    justify-self: start;
    margin-left: 240px;
}

.logo-link {
    display: flex;
    align-items: center;
    gap: 12px;
    cursor: pointer;
    user-select: none;
    text-decoration: none;
    transition: opacity 0.2s ease, transform 0.1s ease;
}

.logo-link:hover {
    opacity: 0.8;
}

.logo-link:active {
    transform: scale(0.98);
}

.header-center {
    justify-self: center;
    margin-top: 10px;
}

.header-right {
    display: flex;
    align-items: center;
    justify-self: end;
}



.nav {
    display: flex;
    gap: 10px;
    background: white;
    border-radius: 60px;
    padding: 10px 16px;
}

.nav a {
    color: #6152c7;
    font-size: 15px;
    text-decoration: none;
    font-weight: 600;
    cursor: pointer;
    transition: opacity 0.2s, color 0.2s, background 0.2s;
    border-radius: 35px;
    padding: 12px 24px;
    white-space: nowrap;
}

.nav a:hover,
.nav a.active {
    background: #f3edff;
    color: #4e40a7;
    transition: background 0.2s, color 0.2s;
}




.logo {
    width: 40px;
    height: 40px;
}

.logo-text {
    font-size: 28px;
    font-weight: 900;
    font-family: 'Verdana', 'Arial', sans-serif;
    background: linear-gradient(90deg, #3f3070, #6c59e6 90%);
    color: transparent;
    -webkit-background-clip: text;
    background-clip: text;
    -webkit-text-fill-color: transparent;
    letter-spacing: 1px;
}


.main-content {
    flex: 1;
    max-width: 1200px;
    width: 100%;
    margin: 0 auto;
}

.landing-footer {
    width: 100%;
    background: transparent;
    padding: 32px 0 24px 0;
    display: flex;
    justify-content: center;
    align-items: center;
    margin-top: auto;
}

.footer-copyright {
    color: #7c6e9f;
    font-size: 14px;
    font-weight: 500;
    letter-spacing: 0.2px;
}

.header-right {
  display: flex;
  align-items: center;
  justify-self: end;
}

.user-box {
  display: flex;
  align-items: center;
  gap: 12px;
  background: #ffffff;
  border-radius: 999px;
  padding: 8px 16px;
  box-shadow: 0 4px 12px rgba(108, 93, 211, 0.15);
}

.user-name {
  font-size: 14px;
  font-weight: 600;
  color: #3a2a68;
}

.logout-btn,
.login-btn {
  border: none;
  border-radius: 999px;
  padding: 8px 16px;
  font-size: 13px;
  font-weight: 600;
  cursor: pointer;
  background: #6c5dd3;
  color: #ffffff;
  transition: background 0.15s ease, transform 0.1s ease, box-shadow 0.15s ease;
}

.logout-btn:hover,
.login-btn:hover {
  background: #5a4bc2;
  transform: translateY(-1px);
  box-shadow: 0 4px 12px rgba(108, 93, 211, 0.3);
}

</style>