import { fileURLToPath, URL } from "node:url";

import { defineConfig } from "vite";
import vue from "@vitejs/plugin-vue";
import vueDevTools from "vite-plugin-vue-devtools";
import vueSetUpExtend from "vite-plugin-vue-setup-extend";

// https://vite.dev/config/
export default defineConfig({
  plugins: [vue(), vueDevTools(), vueSetUpExtend()],
  resolve: {
    alias: {
      "@": fileURLToPath(new URL("./src", import.meta.url)),
    },
  },

  server: {
    port: 5173, // 前端开发服务器的端口号
    proxy: {
      "/api": {
        target: "http://localhost:8889/api", // 后端服务的地址和端口号
        changeOrigin: true, // 是否修改请求的来源（解决跨域问题）
        rewrite: (path) => path.replace(/^\/api/, ""), // 去掉请求路径中的 `/api` 前缀
      },
    },
  },
});
