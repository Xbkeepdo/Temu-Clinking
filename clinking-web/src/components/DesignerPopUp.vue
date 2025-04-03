<template>
  <div v-if="isPopupVisible" class="designer-popup-overlay" @click="closePopup">
    <div class="designer-popup" @click.stop>
      <h3>已绑定设计师</h3>
      <ul class="designer-list-popup">
        <li
          v-for="designer in boundDesigners.get(currentSkcId)"
          :key="designer"
        >
          {{ designer }}
        </li>
      </ul>
      <button class="close-btn" @click="closePopup">✖ 关闭</button>
    </div>
  </div>
</template>

<script setup>
import { ref } from "vue";

const isPopupVisible = ref(false);
const currentSkcId = ref(null);
const boundDesigners = ref(new Map()); // 假设你的绑定数据是这样存的

const openPopup = (skcId) => {
  currentSkcId.value = skcId;
  isPopupVisible.value = true;
};

const closePopup = () => {
  isPopupVisible.value = false;
};

// 这里可以用 provide/inject 让 `boundDesigners` 从父组件获取
</script>

<style>
/* 🔥 遮罩层：半透明毛玻璃 */
.designer-popup-overlay {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.4);
  backdrop-filter: blur(5px); /* 毛玻璃效果 */
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 1000;
  animation: fadeIn 0.3s ease-in-out;
}

/* 🔥 弹窗主体 */
.designer-popup {
  background: white;
  width: 320px;
  padding: 20px;
  border-radius: 12px;
  box-shadow: 0px 8px 20px rgba(0, 0, 0, 0.2);
  text-align: center;
  position: fixed;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  animation: scaleIn 0.3s ease-in-out;
}

/* 标题 */
.designer-popup h3 {
  margin-bottom: 15px;
  font-size: 18px;
  color: #333;
}

/* 设计师列表 */
.designer-list-popup {
  list-style: none;
  padding: 0;
  margin: 0;
  max-height: 200px; /* 限制高度，避免过长 */
  overflow-y: auto;
}

.designer-list-popup li {
  padding: 8px;
  border-bottom: 1px solid #eee;
  font-size: 14px;
}

.designer-list-popup li:last-child {
  border-bottom: none;
}

/* 🔥 关闭按钮 */
.close-btn {
  background: linear-gradient(135deg, #ff7eb3, #ff758c);
  color: white;
  border: none;
  padding: 8px 15px;
  font-size: 14px;
  font-weight: bold;
  border-radius: 20px;
  cursor: pointer;
  margin-top: 15px;
  transition: all 0.3s ease-in-out;
}

.close-btn:hover {
  background: linear-gradient(135deg, #ff758c, #ff5e62);
}

/* 🔥 动画 */
@keyframes fadeIn {
  from {
    opacity: 0;
  }
  to {
    opacity: 1;
  }
}

@keyframes scaleIn {
  from {
    transform: scale(0.8);
    opacity: 0;
  }
  to {
    transform: scale(1);
    opacity: 1;
  }
}
</style>
