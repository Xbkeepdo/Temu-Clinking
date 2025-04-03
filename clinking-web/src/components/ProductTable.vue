<template>
    <table>
      <thead>
        <tr>
          <th>图片</th>
          <th>商品标题</th>
          <th>价格</th>
          <th>状态</th>
          <th>尺码</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="product in products" :key="product.id">
          <td><img :src="product.firstImage" alt="商品图片" /></td>
          <td>{{ product.title }}</td>
          <td>{{ product.price }}</td>
          <td>{{ statusMap[product.state] }}</td>
          <td>{{ product.skuList }}</td>
        </tr>
      </tbody>
    </table>
  </template>
  
  <script>
  import { defineProps } from "vue";
  
  export default {
    props: {
      products: { type: Array, required: true }
    },
    setup() {
      const statusMap = {
  "0": "已弃用",
  "1": "待平台选品",
  "14": "待卖家修改",
  "15": "已修改",
  "16": "服饰可加色",
  "2": "待上传生产资料",
  "3": "待寄样",
  "4": "寄样中",
  "5": "待平台审版",
  "6": "审版不合格",
  "7": "平台核价中",
  "8": "待修改生产资料",
  "9": "核价未通过",
  "10": "待下首单",
  "11": "已下首单",
  "12": "已加入站点",
  "13": "已下架",
  "17": "已终止"
};
  
      return { statusMap };
    }
  };
  </script>
  
  <style>
  table {
    width: 100%;
    border-collapse: collapse;
  }
  thead th {
    text-align: left;
  }
  tbody td {
    padding: 5px;
  }
  </style>
  