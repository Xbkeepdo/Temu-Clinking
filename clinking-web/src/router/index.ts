import { createRouter, createWebHistory } from "vue-router";
import VueCookies from "vue-cookies";
import request from "@/utils/request";
import { useUserStore } from "@/store/User";

const routes = [
  {
    path: "/login",
    name: "login",
    component: () => import("@/views/Login.vue"),
    meta: {
      requireAuth: false,
      title: "登录",
    },
  },

  {
    path: "/",
    name: "main",
    component: () => import("@/views/NavBar.vue"),
    meta: {
      requireAuth: true,
      title: "首页",
    },
    children: [
      // 新增重定向配置
      {
        path: "", // 空路径匹配根路由
        redirect: "/product",
      },
      {
        path: "/product",
        name: "product",
        component: () => import("@/views/ProductPage.vue"),
        meta: {
          requireAuth: true,
          title: "产品生命周期",
        },
      },
      {
        path: "/productBind",
        name: "productBind",
        component: () => import("@/views/ProductBind.vue"),
        meta: {
          requireAuth: true,
          title: "已绑定产品",
        },
      },
      {
        path: "/productInfo/:skcId",
        name: "productInfo",
        component: () => import("@/views/ProductInfo.vue"),
        props: true,
        meta: {
          requireAuth: true,
          title: "产品详情",
        },
      },
    ],
  },
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

// 路由前置守卫：检查用户是否已登录
router.beforeEach(async (to, from, next) => {
  const userStore = useUserStore();
  const isLoggedIn = userStore.isLoggedIn; // 判断用户是否已登录

  console.log("isLoggedIn:", isLoggedIn);

  if (to.meta.requireAuth) {
    try {
      const response = await request.post("/api/user/checkLogin", {
        withCredentials: true,
      });
      if (response.code === 1 && isLoggedIn) {
        next();
      } else {
        console.log("未登录，跳转到登录页");
        next({ name: "login" }); // 跳转到登录页
      }
    } catch (error) {
      console.error("未登录，跳转到登录页");
      next({ name: "login" });
    }
  } else {
    next();
  }
});

router.afterEach((to, from) => {
  // console.log("路由切换", to.path, from.path);
  document.title = to.meta.title || "产品中心";
});
export default router;
