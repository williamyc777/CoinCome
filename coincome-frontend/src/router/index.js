import { createRouter, createWebHistory } from 'vue-router'
import Dashboard from '@/views/Dashboard/Dashboard.vue'
import GoogleCallback from '@/views/GoogleCallBack/GoogleCallBack.vue'
import Upload from '@/views/Upload/Upload.vue'
import Landing from '@/views/Landing/Landing.vue'
import Market from '@/views/Market/Market.vue'
import Portfolio from '@/views/Portfolio/Portfolio.vue'
import OverallHoldings from '@/views/Portfolio/OverallHoldings/OverallHoldings.vue'
import ByExchange from '@/views/Portfolio/ByExchange/ByExchange.vue'
import Recommended from '@/views/Portfolio/Recommended/Recommended.vue'

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
      component: Portfolio,
      redirect: '/portfolio/overall',  
      children: [
        {
          path: 'overall',
          name: 'overallHoldings',
          component: OverallHoldings,
        },
        {
          path: 'by-exchange',
          name: 'byExchange',
          component: ByExchange,
        },
        {
          path: 'recommended',
          name: 'recommended',
          component: Recommended,
        },
      ],
    },
  ],
})

export default router
