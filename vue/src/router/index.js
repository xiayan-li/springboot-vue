import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/User.vue'
import Layout from '../layout/Layout.vue'
// import Vue from 'vue'
// import Router from'vue-router'
//  Vue.use(Router)

const routes = [
  {

    path: '/',
    name: 'Layout',
    component: Layout,
    redirect: '/book',
    children:[
        { //进行二次路由
          path: 'user',
          name: 'user',
          component: () => import("@/views/User")
        },
      { //进行二次路由
        path: 'book',
        name: 'book',
        component: () => import("@/views/Book")
      },
      {
        path: 'person',
        name: 'Person',
        component: () => import("@/views/Person"),
      },
      {
        path: 'news',
        name: 'news',
        component: () => import("@/views/News"),
      }
    ]
  },
  {
    path: '/login',
    name: 'login',
    component: () => import("@/views/Login")
  },
  {
    path: '/register',
    name: 'register',
    component: () => import("@/views/Register")
  },
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
