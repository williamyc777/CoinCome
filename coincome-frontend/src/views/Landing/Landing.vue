<template>
  <div class="landing-root">
    <div class="landing-inner">

      <!-- Navigation Bar -->
      <header class="header">
        <div class="header-left">
          <div @click="goToLanding" class="logo-link">
            <img :src="IconSvg" alt="logo" class="logo" />
            <span class="logo-text">Bitcome</span>
          </div>
        </div>

        <div class="header-right">
          <button class="signin-btn" @click="showSignInForm = true">Sign In</button>
          <div v-if="showSignInForm" class="signin-backdrop"></div>
          <SigninForm :visible="showSignInForm" @close="showSignInForm = false" />
        </div>
      </header>

      <!-- Content -->
      <main class="landing-main">
        <div class="main-content">
          <h1 class="main-title">
            The Crypto Tracking App<br />for Everyone
          </h1>
          <p class="main-desc">
            Join the millions of people using Bitcome to manage your assets.
          </p>
          <button class="main-signup-btn" @click="showSignUpForm = true">
            <span>Get Started</span>
          </button>
          <div v-if="showSignUpForm" class="signin-backdrop"></div>
          <SignupForm :visible="showSignUpForm" @close="showSignUpForm = false" />
        </div>
      </main>

      <!-- footer -->
      <footer class="landing-footer">
        <span class="footer-copyright">© 2025 Bitcome. All rights reserved.</span>
      </footer>

    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue';
import IconSvg from '@/assets/icon_d.svg';
import SigninForm from '@/views/Landing/SigninForm.vue';
import SignupForm from '@/views/Landing/SignupForm.vue';
import { useRouter } from 'vue-router';

const showSignInForm = ref(false);
const showSignUpForm = ref(false);

const router = useRouter();
const goToLanding = () => {
  router.push('/');
};

</script>

<style scoped>
/* Background */
.landing-root {
  min-height: 100vh;
  font-family: 'Inter', Arial, sans-serif;
  background: radial-gradient(circle at top, #f5edff 0, #f4f5ff 45%, #eef0ff 100%);
  position: relative;
  overflow: hidden;
}

.landing-root::before {
  content: "";
  position: absolute;
  inset: 0;
  /* 只负责铺图，不再叠一个很浅的渐变 */
  background: url('@/assets/Currency.jpg') top center/cover no-repeat;
  opacity: 0.25;
  /* 可以按效果再调 */
  z-index: 0;
  pointer-events: none;
  mask-image: linear-gradient(to bottom, black 10%, transparent 70%);
  -webkit-mask-image: linear-gradient(to bottom, black 10%, transparent 70%);
}

/* 中间内容宽度 + 居中 */
.landing-inner {
  position: relative;
  max-width: 100%;
  margin: 0 auto;
  padding: 0;
  z-index: 1;
}

/* 登录弹窗时的全屏暗色遮罩 */
.signin-backdrop {
  position: fixed;
  inset: 0;
  background: rgba(111, 101, 161, 0.38);
  z-index: 9998;
}

/* Effect on logo click */
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
/* 顶部结构：左右布局 */
.header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 20px 0 0 0;
}

/* 左侧：logo */
.header-left {
  display: flex;
  align-items: center;
  gap: 12px;
  margin-left: 150px;
}

/* 右侧：Sign In 按钮 */
.header-right {
  display: flex;
  justify-content: flex-end;
  margin-right: 150px;
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

.signin-btn {
  background: #e9e6fb;
  color: #6c5dd3;
  border: none;
  border-radius: 999px;
  padding: 16px 35px;
  font-size: 15px;
  font-weight: 600;
  cursor: pointer;
  transition: background 0.2s;
  box-shadow: 0 2px 12px 0 rgba(108, 93, 211, 0.1);
  white-space: nowrap;
}

.signin-btn:hover {
  background: #d6d0f7;
}

.landing-main {
  display: flex;
  align-items: center;
  justify-content: center;
  min-height: calc(100vh - 120px);
  gap: 0;
  margin-top: 0;
}

.main-content {
  max-width: 520px;
  display: flex;
  flex-direction: column;
  gap: 32px;
  align-items: center;
  text-align: center;
  margin: 0;
}

.main-title {
  font-family: 'Georgia', 'Times New Roman', Times, serif;
  /* 衬线体 */
  font-size: 75px;
  /* 可根据需要调整 */
  font-weight: 800;
  text-align: center;
  line-height: 1.1;
  margin-bottom: 0;
  margin-top: 0;
  color: #3a2a68;
  /* 保持你原有的紫色 */
  white-space: nowrap;
  background: none;
  opacity: 1;
}

.main-desc {
  color: #4f458d;
  font-size: 18px;
  margin: 0;
  background: none;
  opacity: 1;
}

.main-signup-btn {
  margin-top: 22px;
  background: #ffffff;
  color: #4e40a7;
  border: 2px solid #ffffff;
  border-radius: 999px;
  padding: 17px 40px;
  font-size: 19px;
  font-weight: 500;
  cursor: pointer;
  display: flex;
  align-items: center;
  gap: 18px;
  box-shadow: 0 2px 12px 0 rgba(235, 235, 238, 0.08);
  transition: background 0.2s, border 0.2s, box-shadow 0.2s;
  outline: none;
  opacity: 1;
}

.main-signup-btn:hover {
  background: #f3edff;
  border-color: #d6d0f7;
  box-shadow: 0 4px 16px 0 rgba(108, 93, 211, 0.18);
}


/* footer */
.landing-footer {
  width: 100%;
  background: transparent;
  padding: 32px 0 24px 0;
  display: flex;
  justify-content: center;
  align-items: center;
  margin-top: 48px;
}

.footer-inner {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 8px;
}

.footer-logo {
  font-family: 'Verdana', 'Arial', sans-serif;
  font-size: 22px;
  font-weight: 900;
  background: transparent;
  color: transparent;
  -webkit-background-clip: text;
  background-clip: text;
  -webkit-text-fill-color: transparent;
  letter-spacing: 1px;
}

.footer-copyright {
  color: #7c6e9f;
  font-size: 14px;
  font-weight: 500;
  letter-spacing: 0.2px;
}

.hero-img {
  width: 420px;
  max-width: 90vw;
  border-radius: 32px;
  background: transparent;
}

/* 响应式 */
@media (max-width: 1200px) {
  .landing-inner {
    padding: 0 24px;
  }

  .landing-main {
    margin-top: 72px;
  }
}

@media (max-width: 900px) {
  .landing-main {
    flex-direction: column;
    padding: 40px 0 0 0;
    gap: 32px;
    margin-top: 32px;
  }

  .main-graphic {
    margin-top: 24px;
  }

  .landing-header {
    padding: 24px 0 0 0;
  }

  .nav {
    display: none;
    /* 小屏可先隐藏导航，后面再做 drawer */
  }

  .logo-img {
    width: 32px;
    height: 32px;
  }

  .logo-text {
    font-size: 20px;
  }

  .main-content {
    align-items: center;
    text-align: center;
  }
}
</style>
