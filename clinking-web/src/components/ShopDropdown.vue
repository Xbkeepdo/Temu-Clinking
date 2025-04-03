<template>
  <div class="shop-dropdown" id="shop-dropdown">
    <div class="shop-dropdown-info" @click="toggleDropdown">
      <img
        class="shop-dropdown-avatar"
        :src="shopStore.currentShop.shopImage"
        alt="Avatar"
      />
      <span class="shop-dropdown-name">{{
        shopStore.currentShop.shopName
      }}</span>
      <i :class="dropdownOpen ? 'arrow-up' : 'arrow-down'"></i>
    </div>
    <div v-if="dropdownOpen" class="dropdown-menu">
      <div class="shop-dropdown-details">
        <p><strong>ID:</strong> {{ shopStore.currentShop.shopId }}</p>
      </div>
      <div class="shop-dropdown-actions">
        <button class="open-btn" @click="toggleOverlay(true, $event)">
          切换店铺
        </button>
      </div>
    </div>
  </div>

  <!-- 悬浮页面 -->
  <div v-if="isOverlayVisible" class="overlay" ref="overlay">
    <div class="popup">
      <div class="popup-header">
        <h2>切换店铺</h2>
        <button class="close-btn" @click="toggleOverlay(false)">×</button>
      </div>
      <p class="info">您的账号存在以下{{ shopSum }}个店铺，可切换至任意店铺</p>
      <div class="shop-list">
        <div
          v-for="shop in shopList"
          :key="shop.shopId"
          class="shop-item"
          :class="{ current: shop.shopId === shopStore.currentShop.shopId }"
        >
          <div class="shop-info">
            <img class="shop-avatar" :src="shop.shopImage" alt="店铺头像" />
            <div>
              <p class="shop-name">{{ shop.shopName }}</p>
              <p v-if="shop.status" class="shop-status">{{ shop.status }}</p>
            </div>
          </div>
          <button
            class="switch-btn"
            @click="switchShop(shop)"
            :disabled="shop.shopId === shopStore.currentShop.shopId"
          >
            {{
              shop.shopId === shopStore.currentShop.shopId ? "当前店铺" : "切换"
            }}
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts" name="ShopDropdown">
import { ref, provide, onMounted, onBeforeUnmount } from "vue";
import axios from "axios";
import { useShopStore } from "@/store/Shop";
import request from "@/utils/request";

interface Shop {
  id: string; // 店铺 ID
  shopId: string; // 店铺的唯一 ID
  shopType: string; // 店铺类型
  shopName: string; // 店铺名称
  shopImage: string; // 店铺图片
  superAdminNickName: string; // 超级管理员昵称
  superAdminAccount: string; // 超级管理员账号
  accessToken: string; // 授权码
  appKey: string; // appKey
  appSecret: string; // appSecret
  totalSale: string; // 总销售额
  status: string; // 状态
  remark: string; // 备注
}

const shopStore = useShopStore();

const dropdownOpen = ref(false);
const isOverlayVisible = ref(false);
//const shopStore.currentShop = ref(shopStore.shopStore.currentShop);
const shopList = ref<Shop[]>([]);
const shopSum = ref(0);

//provide("shopStore.currentShop", shopStore.currentShop);

const toggleDropdown = () => {
  dropdownOpen.value = !dropdownOpen.value;
};

const switchShop = (shop) => {
  if (shop.shopId !== shopStore.currentShop.shopId) {
    shopStore.setCurrentShop(shop);
    isOverlayVisible.value = false; // 关闭悬浮窗
    dropdownOpen.value = false;
    console.log("已切换到店铺:", shopStore.currentShop.shopName);
  }
};

const toggleOverlay = (visible: boolean, event?: MouseEvent) => {
  if (event) {
    event.stopPropagation(); // 阻止点击事件传播，避免触发 clickOutside
  }

  isOverlayVisible.value = visible;
};

const fetchShopList = async () => {
  try {
    const response = await request.get("/api/shop/getShops");
    shopList.value = response;
    shopSum.value = shopList.value.length;
    console.log(shopList.value);
  } catch (error) {
    console.error("获取店铺列表失败:", error);
  }
};

// 监听点击页面其他地方关闭 dropdown 和 商店列表弹窗
const clickOutside = (event: MouseEvent) => {
  const dropdown = document.getElementById("shop-dropdown");
  const dropdownMenu = document.querySelector(".dropdown-menu");
  const popup = document.querySelector(".popup");

  // 关闭下拉菜单和悬浮弹窗
  if (
    dropdown &&
    dropdownMenu &&
    !dropdown.contains(event.target as Node) &&
    !dropdownMenu.contains(event.target as Node)
  ) {
    dropdownOpen.value = false;
  }

  // 关闭悬浮弹窗
  if (popup && !popup.contains(event.target as Node)) {
    isOverlayVisible.value = false;
  }
};

onMounted(() => {
  fetchShopList();
  // 在页面加载时监听点击事件
  document.addEventListener("click", clickOutside);
});

onBeforeUnmount(() => {
  // 在组件卸载时移除事件监听
  document.removeEventListener("click", clickOutside);
});
</script>

<style scoped>
.shop-dropdown {
  display: flex;
  align-items: center;
  gap: 10px;
  cursor: pointer;
  background-color: #ffffff;
  padding: 8px 12px;
  border-radius: 5px;
  position: relative; /* 添加相对定位，以便后续子元素定位 */
}

.shop-dropdown-info {
  display: flex;
  align-items: center;
  gap: 10px;
}

.shop-dropdown-avatar {
  width: 30px;
  height: 30px;
  border-radius: 50%;
}

.shop-dropdown-name {
  font-size: 14px;
}

.arrow-up,
.arrow-down {
  width: 10px;
  height: 10px;
  border: solid black;
  border-width: 0 2px 2px 0;
  display: inline-block;
  margin-left: 5px;
  transform: rotate(45deg);
}

.arrow-up {
  transform: rotate(-135deg);
}

.dropdown-menu {
  position: absolute;
  top: 100%;
  left: 0;
  background: white;
  border: 1px solid #ddd;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  padding: 10px;
  width: 200px;
  z-index: 100;
}

.shop-dropdown-details p {
  margin: 5px 0;
  font-size: 14px;
}

.shop-dropdown-details a {
  color: blue;
  text-decoration: underline;
}

.shop-dropdown-actions {
  margin-top: 10px;
}

.switch-shop,
.logout {
  display: block;
  width: 100%;
  padding: 5px;
  margin: 5px 0;
  border: none;
  cursor: pointer;
  font-size: 14px;
}

.switch-shop {
  background: #f0f0f0;
  color: #000;
}

.logout {
  background: #ff4d4f;
  color: #fff;
}

/* 按钮样式 */
.open-btn {
  padding: 5px;
  background-color: #000000;
  color: white;
  border: none;
  width: 100%;
  border-radius: 5px;
  cursor: pointer;
  font-size: 14px;
}

/* 悬浮页面的背景 */
.overlay {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 1000;
}

/* 弹窗样式 */
.popup {
  background: white;
  width: 400px;
  max-height: 500px; /* 设置最大高度 */
  padding: 20px;
  border-radius: 10px;
  text-align: left;
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.3);
  position: relative;
  display: flex;
  flex-direction: column;
}

.popup-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.popup-header h2 {
  margin: 0;
  font-size: 18px;
}

.close-btn {
  background: none;
  border: none;
  font-size: 24px;
  cursor: pointer;
}

.info {
  font-size: 14px;
  color: #555;
  margin-bottom: 10px;
}

.shop-list {
  flex: 1;
  overflow-y: auto; /* 超出内容滚动 */
  margin-top: 10px;
  padding-right: 10px; /* 避免滚动条遮挡内容 */
}

.shop-item {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 10px;
  border: 1px solid #ddd;
  border-radius: 5px;
}

.shop-item.current {
  border: 1px solid orange;
}

.shop-info {
  display: flex;
  align-items: center;
  gap: 10px;
}

.shop-avatar {
  width: 40px;
  height: 40px;
  border-radius: 50%;
}

.shop-name {
  font-size: 16px;
  font-weight: bold;
}

.shop-status {
  font-size: 12px;
  color: orange;
}

.switch-btn {
  padding: 5px 10px;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  font-size: 14px;
  background: #4caf50;
  color: white;
}

.switch-btn:disabled {
  background: orange;
  cursor: default;
}
</style>
@/pinia/Shop @/Store/Shop
