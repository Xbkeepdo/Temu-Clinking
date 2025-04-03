// store/shopStore.js
import { defineStore } from "pinia";

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

export const useShopStore = defineStore("shop", {
  state: () => ({
    currentShop: {
      shopId: "634418212510386",
      shopName: "CharmHop",
      followers: 99999,
      shopImage:
        "https://img.cdnfe.com/supplier-public-tag/1eac59b83b/f1d28cc8-08db-4a2c-a84c-9d8c28f88b47_300x300.png",
    },
  }),
  actions: {
    setCurrentShop(shop) {
      this.currentShop = shop;
    },
  },
});
