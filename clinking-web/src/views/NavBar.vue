<template>
  <div class="flex h-screen">
    <!-- 侧边导航栏 -->
    <!-- <el-aside
      width="180px"
      class="bg-white text-black flex flex-col p-4 fixed left-0 top-0 bottom-0 shadow-lg"
    > -->

    <el-aside
      width="180px"
      class="bg-white text-black flex flex-col p-2 fixed left-0 top-0 bottom-0 shadow-lg"
    >
      <div class="text-2xl font-bold p-4">产品中心</div>
      <el-menu
        :default-active="activePath"
        class="bg-white text-black"
        @select="navigateTo"
        background-color="#ffffff"
        text-color="#000"
        active-text-color="#409EFF"
        style="border: none"
      >
        <el-menu-item
          v-for="item in menuItems"
          :key="item.name"
          :index="item.path"
          class="flex flex-col justify-center items-center h-[80px]"
        >
          <div class="flex flex-col items-center">
            <el-icon class="w-5 h-5 mb-1">
              <component :is="item.icon" />
            </el-icon>
            <span class="text-sm font-medium">{{ item.name }}</span>
          </div>
        </el-menu-item>
      </el-menu>
    </el-aside>

    <!-- 右侧主界面 -->
    <!-- <div class="flex-1 ml-60 flex flex-col"> -->

    <div class="flex-1 ml-[170px] flex flex-col">
      <!-- 顶部导航栏 -->
      <el-header
        class="bg-white shadow-md flex items-center justify-between px-6 h-10"
      >
        <ShopDropdown></ShopDropdown>

        <div class="flex items-center space-x-3">
          <img
            class="w-9 h-9 rounded-full border"
            src="@/assets/defalt_avater.jpg"
            alt="User Avatar"
          />
          <div>
            <div class="text-sm font-semibold">
              {{ userStore.currentUser.nickName }}
            </div>
            <div class="text-xs text-gray-500">
              {{ userStore.currentUser.shopId }}
            </div>
          </div>
          <el-button
            @click="logout"
            type="button"
            class="text-red-500 text-sm hover:underline"
          >
            登出
          </el-button>
        </div>
      </el-header>

      <!-- 主体内容：这里动态显示路由组件 -->
      <el-main class="p-2 flex-1 bg-white shadow-md rounded-lg">
        <router-view />
      </el-main>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref } from "vue";
import { useRouter } from "vue-router";
import { AlarmClock, Link } from "@element-plus/icons-vue"; // 使用 Element Plus 的图标
import { useUserStore } from "@/store/User";
import { storeToRefs } from "pinia";
import ShopDropdown from "@/components/ShopDropdown.vue";

const router = useRouter();
const userStore = useUserStore();
const { currentUser } = storeToRefs(userStore);

//const userAvatar = require("@/assets/defalt_avater.jpg"); // 设置用户头像路径

const menuItems = ref([
  { name: "产品生命周期", path: "/product", icon: AlarmClock },
  { name: "查询绑定产品", path: "/productBind", icon: Link },
  //{ name: "商品详情", path: "/productInfo" },
]);

const activePath = ref(router.currentRoute.value.path); // 当前激活的菜单项路径

const navigateTo = (path) => {
  router.push(path);
  activePath.value = path; // 更新当前激活的路径
};

const logout = () => {
  userStore.logoutUser();
  router.push("/login");
};
</script>

<style scoped>
/* 确保整个页面没有外部的默认间距 */
body,
html {
  margin: 0;
  padding: 0;
  height: 100%;
  background-color: #eff0f4; /* 背景颜色设置为淡灰色 */
}

/* 自定义高亮样式 */
.el-menu .el-menu-item.is-active {
  background-color: #f0f0f0 !important;
  color: #409eff !important;
}

.el-menu {
  border: none; /* 去掉默认的边框 */
}

/* 添加自定义样式 */
.el-menu-item {
  height: 80px !important; /* 设置固定高度 */
  padding: 0 !important; /* 移除默认内边距 */
  justify-content: center !important; /* 垂直居中 */
}
.el-menu-item:hover {
  background-color: #f5f5f5 !important;
}

.el-menu-item > div {
  display: flex;
  flex-direction: column;
  align-items: center;
  width: 100%;
}
</style>
