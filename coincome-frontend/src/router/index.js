import { createRouter, createWebHistory } from 'vue-router'
import Dashboard from '@/views/Dashboard/Dashboard.vue'
import GoogleCallback from '@/views/GoogleCallBack/GoogleCallBack.vue'
import Upload from '@/views/Upload/Upload.vue'
import Landing from '@/views/Landing/Landing.vue'
import Market from '@/views/Market/Market.vue'
import Portfolio from '@/views/Portfolio/Portfolio.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'landing',
      component: Landing
    },
    {
      path: '/dashboard',
      name: 'dashboard',
      component: Dashboard
    },
    {
      path: '/auth/google/callback',
      name: 'googlecallback',
      component: GoogleCallback
    },
    {
      path: '/market',
      name: 'market',
      component: Market
    },
    {
      path: '/upload',
      name: 'upload',
      component: Upload
    },
    {
      path: '/portfolio',
      name: 'portfolio',
      component: Portfolio
    },
  ],
})

export default router
