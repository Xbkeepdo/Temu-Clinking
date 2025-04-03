<template>
  <div class="flex h-screen items-center justify-center bg-gray-50">
    <div class="w-full max-w-md rounded-lg bg-white p-8 shadow-lg">
      <h2 class="mb-6 text-center text-2xl font-semibold text-gray-800">
        登录
      </h2>

      <form @submit.prevent="handleLogin">
        <!-- 用户名 -->
        <div class="mb-4">
          <label class="mb-2 block text-gray-700">用户名</label>
          <input
            v-model="account"
            type="text"
            placeholder="请输入用户名"
            class="w-full rounded-md border p-3 text-gray-800 bg-gray-100 border-gray-300 focus:outline-none focus:ring-2 focus:ring-blue-500"
            required
          />
        </div>

        <!-- 密码 -->
        <div class="mb-4">
          <label class="mb-2 block text-gray-700">密码</label>
          <input
            v-model="password"
            type="password"
            placeholder="请输入密码"
            class="w-full rounded-md border p-3 text-gray-800 bg-gray-100 border-gray-300 focus:outline-none focus:ring-2 focus:ring-blue-500"
            required
          />
        </div>

        <!-- 记住我 & 忘记密码 -->
        <div class="mb-4 flex items-center justify-between">
          <label class="flex items-center text-gray-700">
            <input type="checkbox" v-model="rememberMe" class="mr-2" />
            记住我
          </label>
          <a href="#" class="text-blue-500 hover:underline cursor-pointer"
            >忘记密码？</a
          >
        </div>

        <!-- 登录按钮 -->
        <button
          type="submit"
          class="w-full rounded-lg bg-blue-500 px-4 py-3 text-white transition duration-300 ease-in-out hover:bg-blue-600 active:scale-95 cursor-pointer"
        >
          登录
        </button>
      </form>

      <!-- 注册提示 -->
      <p class="mt-4 text-center text-gray-600">
        还没有账户？
        <a href="#" class="text-blue-500 hover:underline cursor-pointer"
          >立即注册</a
        >
      </p>
    </div>
  </div>
</template>

<script setup lang="ts" name="Login">
import { ref, getCurrentInstance } from "vue";
import axios from "axios";
import { useRoute, useRouter } from "vue-router";
import { useUserStore } from "@/store/User";
import request from "@/utils/request";
import VueCookies from "vue-cookies";

const account = ref("");
const password = ref("");
const errorMessage = ref("");
const rememberMe = ref(false);
const route = useRoute();
const router = useRouter();
const userStore = useUserStore();

async function handleLogin() {
  if (account.value && password.value) {
    try {
      // 向后端发送请求进行登录验证
      const response = await request.post(
        "/api/user/login",
        null,
        {
          params: {
            account: account.value,
            password: password.value,
          },
        },
        {
          withCredential: true,
        }
      );
      console.log("后端返回的数据：", response);

      if (response.code == 1) {
        userStore.setCurrentUser(response.data);
        // localStorage.setItem("user", JSON.stringify(response.data));
        // VueCookies.set("userInfo", JSON.stringify(response.data), { expires: "7d" });

        const redirectUrl = "/";
        router.push(redirectUrl);
        // 登录成功后处理
        alert(`登录成功！欢迎，${account.value}`);
      } else {
        // 后端返回错误，显示错误信息
        errorMessage.value = response.message || "登录失败，请检查用户名和密码";
        alert(errorMessage.value);
      }
    } catch (error) {
      // 请求错误，显示错误信息
      errorMessage.value = "请求失败，请稍后再试！";
      alert(errorMessage.value);
    }
  } else {
    errorMessage.value = "请输入用户名和密码！";
    alert(errorMessage.value);
  }
}
</script>

<style scoped>
/* 风格明亮，清新的背景与配色 */

/* 背景色 */
body {
  background-color: #f9fafb;
  color: #333;
}

/* 输入框 */
input {
  background-color: #f3f4f6;
  border: 1px solid #e5e7eb;
  color: #333;
  border-radius: 8px;
}

/* 表单标题 */
h2 {
  font-size: 1.5rem;
  color: #1f2937;
}

/* 按钮 */
button {
  background-color: #3498db;
  border-radius: 8px;
  color: white;
  font-size: 1.125rem;
  transition: background-color 0.3s ease, transform 0.2s;
}

button:hover {
  background-color: #2980b9;
}

button:active {
  transform: scale(0.98);
}

/* 文字 */
p {
  color: #4b5563;
}

/* 激活的输入框 */
input:focus {
  border-color: #3498db;
  box-shadow: 0 0 0 3px rgba(52, 152, 219, 0.3);
}

/* 表单和输入框 */
label {
  font-size: 1rem;
  color: #4b5563;
}

/* 记住我和忘记密码 */
a {
  color: #3498db;
}

a:hover {
  text-decoration: underline;
}

input[type="checkbox"]:checked {
  background-color: #3498db;
}

/* 圆角效果 */
div {
  border-radius: 8px;
}

/* 鼠标悬浮时指针变为黑色 */
a:hover,
button:hover,
input:hover {
  cursor: pointer;
}

input {
  cursor: text;
  color: #000;
}
</style>
