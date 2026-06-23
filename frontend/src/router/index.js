import { createRouter, createWebHistory } from 'vue-router'

const routes = [
  {
    path: '/',
    name: 'Chat',
    component: () => import('../views/ChatView.vue')
  },
  {
    path: '/prompts',
    name: 'PromptManage',
    component: () => import('../views/PromptManageView.vue')
  },
  {
    path: '/documents',
    name: 'DocumentManage',
    component: () => import('../views/DocumentManageView.vue')
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router