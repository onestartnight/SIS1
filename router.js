import { createRouter, createWebHistory } from 'vue-router'
import Login from './views/Login.vue'
import StudentDashboard from './views/StudentDashboard.vue'
import AdminDashboard from './views/AdminDashboard.vue'

// 简化版，实际应根据后端返回的角色
const routes = [
  { path: '/', component: Login },
  { path: '/student', component: StudentDashboard },
  { path: '/admin', component: AdminDashboard }
]

export default createRouter({
  history: createWebHistory(),
  routes
})