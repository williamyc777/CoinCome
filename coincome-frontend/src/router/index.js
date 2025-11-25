import { createRouter, createWebHistory } from 'vue-router'
import Home from '@/compo/Home.vue'
import SignIn from '@/compo/SignIn.vue'
import GoogleCallback from '@/compo/GoogleCallback.vue'
import HomeAfter from '@/compo/HomeAfter.vue'
import Signup from '@/compo/Signup.vue'
import Upload from '@/compo/upload.vue'
import Landing from '@/components/Landing/Landing.vue'
import SigninForm from '@/components/LoginView/SigninForm.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'landing',
      component: Landing
    },
    {
      path: '/signin',
      name: 'signin',
      component: SignIn
    },
    {
      path: '/auth/google/callback',
      name: 'googlecallback',
      component: GoogleCallback
    },
    {
      path: '/homeafter',
      name: 'homeafter',
      component: HomeAfter
    },
    {
      path: '/signup',
      name: 'signup',
      component: Signup
    },
    {
      path: '/dashboard', 
      name: 'dashboard',
      component: () => import('@/components/dashboard/DashboardView.vue')
    },
    {
      path: '/upload',
      name: 'upload',
      component: Upload
    },
    // {
    //   path: '/landing',
    //   name: 'landing',
    //   component: Home
    // },
  ],
})

export default router
