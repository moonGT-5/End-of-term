import { createRouter, createWebHashHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'
import RegisterView from '@/views/RegisterView.vue'
import LogInPageView from '@/views/LogInPageView.vue'

const routes = [
  {
    path: '/home',
    name: 'home',
    component: HomeView
  },
  {
    path: '/about',
    name: 'about',
    component: () => import(/* webpackChunkName: "about" */ '../views/AboutView.vue')
  },
  {
    path: '/feedback/:productName?',
    name: 'feedback',
    component: () => import(/* webpackChunkName: "about" */ '../views/FeedBackView.vue')
  },
  {
    path: '/register',
    name: 'register',
    component: RegisterView
  },
  {
    path: '/',
    name: 'login',
    component: LogInPageView
  },
]

const router = createRouter({
  history: createWebHashHistory(),
  routes
})

export default router