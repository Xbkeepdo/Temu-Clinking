<template>
  <div class="product-page">
    <div class="search-container">
      <input v-model="searchKey" placeholder="请输入SKC、title、款号搜索" />
      <button @click="handleSearch">查询</button>
    </div>

    <div class="tabs">
      <button
        v-for="tab in tabs"
        :key="tab.value"
        :class="{ active: currentTab === tab.value }"
        @click="changeTab(tab.value)"
      >
        {{ tab.label }}
      </button>
    </div>

    <table class="product-table">
      <thead>
        <tr>
          <th style="width: 150px">商品图片</th>
          <th style="width: 200px">商品标题</th>
          <th style="width: 100px">SKC</th>
          <th style="width: 150px">分类</th>
          <th>价格</th>
          <th>状态</th>
          <th style="width: 150px">尺码</th>
          <th style="width: 110px">货号</th>
          <th style="width: 145px">设计师</th>
          <th style="width: 150px">操作</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="product in filteredProducts" :key="product.id">
          <td>
            <img
              :src="product.firstImage"
              alt="商品图片"
              class="product-image"
            />
          </td>
          <td>{{ product.title }}</td>
          <td>{{ product.skcId }}</td>
          <td>{{ product.catName }}</td>
          <td>
            {{ product.price === 0 ? "待定" : `￥${product.price}` }}
          </td>
          <td>{{ mapStatus(product.state) }}</td>
          <td>{{ product.skuList.replace(/-/g, ", ").slice(1) }}</td>

          <!-- 修改货号列的模板 -->
          <td>
            <span
              v-if="product.productNumber"
              class="editable-cell"
              @click="handleEditProductNumber(product)"
            >
              {{ product.productNumber }}
            </span>
            <span
              v-else
              class="editable-cell"
              @click="handleEditProductNumber(product)"
            >
              未编写货号(点击编辑)
            </span>
          </td>

          <!-- <td>
            <div class="designer-list">
              <span
                v-for="(designer, index) in boundDesigners
                  .get(product.skcId)
                  ?.slice(0, 3)"
                :key="designer"
                class="designer-name"
              >
                {{ designer }}
              </span>

              <span
                v-if="boundDesigners.get(product.skcId)?.length > 3"
                class="more-designers"
                @click="toggleExpand(product.skcId, $event)"
              >
                +{{ boundDesigners.get(product.skcId).length - 3 }}更多
              </span>
            </div> -->

          <!-- 设计师完整列表的弹出框 -->
          <!-- <div
              v-if="expandedProductId === product.skcId"
              class="designer-popup"
              :style="popupStyle"
              @click.stop
            >
              <div class="popup-content">
                <h4>已绑定设计师</h4>
                <ul>
                  <li
                    v-for="designer in boundDesigners.get(product.skcId)"
                    :key="designer"
                  >
                    {{ designer }}
                  </li>
                </ul>
                <button class="close-btn" @click="expandedProductId = null">
                  关闭
                </button>
              </div>
            </div>
          </td> -->

          <td>
            <div class="designer-list">
              <!-- 显示前3个设计师 -->
              <span
                v-for="(designer, index) in product.designerList?.slice(0, 4)"
                :key="index"
                class="designer-name"
              >
                {{ designer }}
              </span>

              <!-- 显示更多按钮 -->
              <el-link
                v-if="product.designerList?.length > 4"
                class="more-designers"
                @click="toggleExpand(product.skcId, $event)"
              >
                +{{ product.designerList.length - 4 }}更多
              </el-link>
            </div>

            <!-- 设计师弹出框 -->
            <div
              v-if="expandedProductId === product.skcId"
              class="designer-popup"
              :style="popupStyle"
              @click.stop
            >
              <div class="popup-content">
                <h4>已绑定设计师</h4>
                <ul>
                  <li
                    v-for="(designer, index) in product.designerList"
                    :key="index"
                  >
                    {{ designer }}
                  </li>
                </ul>
                <button class="close-btn" @click="expandedProductId = null">
                  关闭
                </button>
              </div>
            </div>
          </td>

          <td class="button-container">
            <button
              @click="
                boundProducts.get(product.skcId)
                  ? unbindUserFromProduct(product)
                  : bindUserToProduct(product)
              "
              :class="{
                'bind-btn': !boundProducts.get(product.skcId),
                'unbind-btn': boundProducts.get(product.skcId),
              }"
              class="btn"
            >
              {{ boundProducts.get(product.skcId) ? "解绑" : "绑定" }}
            </button>

            <el-button
              @click="openPatternFileModal(product.skcId)"
              class="Patternbtn"
            >
              查看纸样文件
            </el-button>

            <button
              class="edit-cost-btn"
              @click="openEditDialog(product)"
              :style="{
                backgroundColor: product.totalCost < 1 ? '#ffc700' : '#000000',
              }"
            >
              编辑成本
            </button>

            <el-button
              class="Detailbtn"
              @click="handleViewDetail(product.skcId)"
            >
              详情
            </el-button>
          </td>
        </tr>
      </tbody>
    </table>

    <!-- 样纸文件弹窗弹窗 -->
    <el-dialog
      v-model="patternFileModalVisible"
      title="纸样文件"
      width="50%"
      @close="resetPatternFileModal"
    >
      <el-upload
        class="upload-demo"
        drag
        :action="uploadUrl"
        :on-success="handleUploadSuccess"
        :on-error="handleUploadError"
        :file-list="fileList"
        :before-upload="beforeUpload"
        :auto-upload="true"
        :data="{ skcId: selectSkcId }"
      >
        <i class="el-icon-upload"></i>
        <div class="el-upload__text">点击或拖动文件上传纸样</div>
      </el-upload>

      <el-table
        :data="patternFileList"
        style="width: 100%"
        @selection-change="handleSelectionChange"
      >
        <!-- 监听选择事件 -->
        <el-table-column
          type="selection"
          width="55"
          :selectable="selectable"
        ></el-table-column>
        <el-table-column label="文件名" prop="fileName"></el-table-column>
        <el-table-column label="上传时间" prop="uploadTime">
          <template #default="{ row }">
            {{ formatDate(row.uploadTime) }}
          </template>
        </el-table-column>
        <el-table-column label="操作">
          <template #default="{ row }">
            <el-button @click="downloadPatternFile(row)">下载</el-button>
            <el-button type="danger" @click="deletePatternFile(row)"
              >删除</el-button
            >
          </template>
        </el-table-column>
      </el-table>

      <div slot="footer" class="dialog-footer">
        <el-button @click="resetPatternFileModal">取消</el-button>
        <el-button type="primary" @click="handleDownloadSelectedFiles"
          >下载选中</el-button
        >
      </div>
    </el-dialog>

    <el-dialog
      v-model="dialogVisible"
      title="编辑商品成本"
      width="45%"
      @close="resetForm"
      class="compact-dialog"
    >
      <el-form :model="formData" label-width="100px">
        <!-- 面料板块 -->
        <div class="section">
          <h4 class="section-title">面 料</h4>
          <el-row :gutter="16">
            <el-col
              :span="12"
              v-for="item in availableCostItems.slice(0, 2)"
              :key="item.value"
            >
              <el-form-item :label="item.label + '：'">
                <el-input
                  v-model="formData[item.value]"
                  :disabled="!isEditable"
                  @input="handleInput(item.value)"
                  type="number"
                  step="0.01"
                  controls-position="right"
                />
              </el-form-item>
            </el-col>
          </el-row>
        </div>

        <!-- 工艺板块 -->
        <div class="section">
          <h4 class="section-title">工 艺</h4>
          <el-row :gutter="16">
            <!-- 原有工艺字段 -->
            <el-col
              :span="8"
              v-for="item in availableCostItems.slice(2, 8)"
              :key="item.value"
            >
              <el-form-item :label="item.label + '：'" class="compact-item">
                <el-input
                  v-model="formData[item.value]"
                  :disabled="!isEditable"
                  @input="handleInput(item.value)"
                  type="number"
                  step="0.01"
                />
              </el-form-item>
            </el-col>

            <!-- 其他工艺操作 -->
            <el-col :span="24">
              <!-- 原有其他工艺字段 -->
              <!-- <el-form-item label="其他工艺：" class="compact-item">
                <el-input
                  v-model="formData.otherCraft"
                  :disabled="!isEditable"
                  @input="handleInput('otherCraft')"
                  type="number"
                  step="0.01"
                />
              </el-form-item> -->

              <!-- 添加其他工艺按钮 -->
              <el-button
                @click="openAddCraftDialog"
                type="primary"
                class="add-button"
              >
                + 添加其他工艺
              </el-button>

              <!-- 已添加工艺列表 -->
              <el-table
                :data="addedCrafts"
                size="small"
                v-if="addedCrafts.length > 0"
                class="material-table"
              >
                <el-table-column prop="name" label="工艺名称" width="120" />
                <el-table-column prop="cost" label="成本" width="100">
                  <template #default="{ row }">
                    {{ Number(row.cost).toFixed(2) }}
                  </template>
                </el-table-column>
                <el-table-column label="操作" width="60">
                  <template #default="{ $index }">
                    <!-- 方案2：纯图标（无按钮样式） -->
                    <el-icon
                      @mouseenter="isHoverCraft = true"
                      @mouseleave="isHoverCraft = false"
                      @click="removeCraft($index)"
                      :style="{ color: isHoverCraft ? '#ff4d4f' : '#666' }"
                      title="删除"
                      size="15"
                      class="delete-icon"
                    >
                      <Delete v-if="!isHoverCraft" />
                      <DeleteFilled v-else />
                    </el-icon>
                  </template>
                </el-table-column>
              </el-table>
            </el-col>
          </el-row>
        </div>

        <!-- 辅料板块 -->
        <div class="section">
          <h4 class="section-title">辅 料</h4>
          <el-row :gutter="16">
            <el-col
              :span="8"
              v-for="item in availableCostItems.slice(9, 19)"
              :key="item.value"
            >
              <el-form-item :label="item.label + '：'" class="compact-item">
                <el-input
                  v-model="formData[item.value]"
                  :disabled="!isEditable"
                  @input="handleInput(item.value)"
                  type="number"
                  step="0.01"
                  controls-position="right"
                />
              </el-form-item>
            </el-col>
            <el-col :span="24">
              <el-button
                @click="openAddMaterialDialog"
                type="primary"
                class="add-button"
              >
                + 添加辅料
              </el-button>
              <el-table
                :data="addedMaterials"
                size="small"
                v-if="addedMaterials.length > 0"
                class="material-table"
              >
                <el-table-column prop="name" label="辅料名称" width="120" />
                <el-table-column prop="cost" label="成本" width="100">
                  <template #default="{ row }">
                    {{ Number(row.cost).toFixed(2) }}
                  </template>
                </el-table-column>
                <el-table-column label="操作" width="60">
                  <template #default="{ $index }">
                    <!-- 方案2：纯图标（无按钮样式） -->
                    <!-- <el-icon
                      class="delete-icon"
                      @click="removeMaterial($index)"
                      title="删除"
                    >
                      <CloseBold />
                    </el-icon> -->

                    <el-icon
                      @mouseenter="isHoverMeterial = true"
                      @mouseleave="isHoverMeterial = false"
                      @click="removeMaterial($index)"
                      :style="{ color: isHoverMeterial ? '#ff4d4f' : '#666' }"
                      title="删除"
                      size="15"
                      class="delete-icon"
                    >
                      <Delete v-if="!isHoverMeterial" />
                      <DeleteFilled v-else />
                    </el-icon>
                  </template>
                </el-table-column>
              </el-table>
            </el-col>
          </el-row>
        </div>

        <!-- 运费和其他 -->
        <div class="section">
          <h4 class="section-title">运费/其他</h4>
          <el-row :gutter="16">
            <el-col
              :span="12"
              v-for="item in availableCostItems.slice(19)"
              :key="item.value"
            >
              <el-form-item :label="item.label + '：'">
                <el-input
                  v-model="formData[item.value]"
                  :disabled="!isEditable"
                  @input="handleInput(item.value)"
                  type="number"
                  step="0.01"
                  controls-position="right"
                />
              </el-form-item>
            </el-col>
          </el-row>
        </div>

        <!-- 底部操作 -->
        <div class="footer-section">
          <el-form-item label="总成本：">
            <el-input
              :value="totalCost.toFixed(2)"
              disabled
              class="total-cost-input"
            />
          </el-form-item>
          <div class="action-buttons">
            <el-button @click="toggleEdit" type="primary" class="edit-btn">
              {{ isEditable ? "保存修改" : "进入编辑" }}
            </el-button>
            <el-button @click="submitForm" type="primary" class="submit-btn">
              提交成本
            </el-button>
          </div>
        </div>
      </el-form>
    </el-dialog>

    <!-- 添加其他工艺弹窗 -->
    <el-dialog v-model="addCraftDialogVisible" title="添加其他工艺" width="30%">
      <el-form :model="addCraftForm" label-width="80px">
        <el-form-item label="工艺名称">
          <el-input v-model="addCraftForm.name" placeholder="如：刺绣" />
        </el-form-item>
        <el-form-item label="成本">
          <el-input v-model="addCraftForm.cost" type="number" step="0.01" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="addCraftDialogVisible = false">取消</el-button>
        <el-button
          type="primary"
          @click="saveCraft"
          style="background-color: black"
        >
          保存
        </el-button>
      </template>
    </el-dialog>

    <!-- 添加辅料弹窗 -->
    <el-dialog v-model="addMaterialDialogVisible" title="添加辅料" width="30%">
      <el-form :model="addMaterialForm" label-width="80px">
        <el-form-item label="辅料名称">
          <el-input
            v-model="addMaterialForm.name"
            placeholder="请输入辅料名称"
          ></el-input>
        </el-form-item>
        <el-form-item label="成本">
          <el-input
            v-model="addMaterialForm.cost"
            placeholder="请输入成本"
            type="number"
            step="any"
          ></el-input>
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="addMaterialDialogVisible = false">取消</el-button>
        <el-button
          type="primary"
          @click="saveMaterial"
          style="background-color: black"
          >保存</el-button
        >
      </template>
    </el-dialog>

    <!-- 分页组件 -->
    <el-pagination
      v-if="totalProducts > 0"
      :current-page="currentPage"
      :page-sizes="[10, 20, 30, 40, 50, 100]"
      :total="totalProducts"
      @current-change="handlePageChange"
      @size-change="handleSizeChange"
      layout="total, sizes,prev, pager, next, jumper"
      class="custom-pagination"
    />
  </div>
</template>

<script lang="ts" setup name="ProductPage">
import { ref, computed, inject, onMounted, onUnmounted } from "vue";
import axios from "axios";
import { useShopStore } from "@/store/Shop";
import { useUserStore } from "@/store/User";
import { watch } from "vue";
import ShopDropdown from "@/components/ShopDropdown.vue";
import request from "@/utils/request";
import { storeToRefs } from "pinia";
import {
  ElUpload,
  ElDialog,
  ElTable,
  ElButton,
  ElTableColumn,
  ElMessage,
  ElMessageBox,
  ElIcon,
  ElLoading,
} from "element-plus";
import { Delete, DeleteFilled, Plus } from "@element-plus/icons-vue";

interface Product {
  id: string; // 自动递增的 ID
  shopId: string; // 店铺 ID
  spuId: string; // SPU ID
  skcId: string; // SKC ID
  sSku: string; // S码的 SKU
  title: string; // 商品标题
  productNumber: string; // 商品编号
  catName: string; // 分类名称
  firstImage: string; // 商品首图
  price: number; // 价格
  state: string; // 商品状态
  shopType: string; // 店铺类型
  fabricWeight: string; // 面料克重
  fabricSampleWeight: string; // 样布实际克重
  skuList: string; // 尺码列表
  createTime: string; // 创建时间（时间戳，毫秒级）
  sampleDeliveryTime: string; // 第一次寄样时间节点
  sampleReviewPatternTime: string; // 第一次审版时间节点
  startOfPriceCheckTime: string; // 核价开始时间
  reviewPatternCount: string; // 审版次数
  waitForTheFirstOrderTime: string; // 待下首单时间
  priceCheckTimeSpent: string; // 核价所花时间，单位小时
  reviewPatternCompletedTimeSpent: string; // 审版完成所花时间，单位小时
  averageReviewPatternCompletedTime: string; // 平均审版完成所花时间，单位小时
  totalCost: number; //成本

  designerList: string[]; //设计师列表
}

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

interface File {
  id: string;
  skcId: string;
  fileName: string;
  fileUrl: string;
  uploadTime: string;
}

// 定义响应式数据
const products = ref<Product[]>([]);
const originalProducts = ref<Product[]>([]);
const searchKey = ref("");
const currentTab = ref("all");
const shopStore = useShopStore();
const { currentShop } = storeToRefs(shopStore);
const userStore = useUserStore();
const { currentUser } = storeToRefs(userStore);
const currentDesigner = ref(userStore.currentUser.nickName);
const boundProducts = ref(new Map()); // 存储绑定状态
//const boundDesigners = ref(new Map()); // 存储每个 SKC 的绑定设计师

const totalFilteredProducts = ref(0); // 筛选后的商品总数

const totalProducts = ref(0); //商品总数
const currentPage = ref(1); // 当前页
const pageSize = ref(10); // 每页显示的数量

const expandedProductId = ref(null); // 记录展开的商品 ID

const isSearching = ref(false); // 是否处于搜索模式

const isHoverMeterial = ref(false); //辅料删除悬停效果
const isHoverCraft = ref(false); //工艺删除悬停效果

//成本弹窗功能
// 控制弹窗显示的变量
const dialogVisible = ref(false);

// 商品skcid=成本映射
//const productCostMap = ref<Record<string, number>>({});

const tabs = [
  { label: "全部", value: "all" },
  { label: "未发布", value: "unpublished" },
  { label: "待寄样", value: "pendingSample" },
  { label: "审版中", value: "reviewing" },
  { label: "价格申报中", value: "pricing" },
  { label: "待创建首单", value: "pendingOrder" },
  { label: "已发布到站点", value: "published" },
  { label: "已下架/终止", value: "terminated" },
];

interface CostData {
  skcId: string;
  spuId: string;
  shopId: string;
  cloth: number;
  cutInto: number;
  tailor: number;
  printing: number;
  pack: number;
  more: number;
  qualityRefundRate: number;
  sizeRefundRate: number;
  qualityScore: number;
  freight: number;
  button: number;
  ribbing: number;
  rubber: number;
  string: number;
  sack: number;
  airHole: number;
  zipper: number;
  chain: number;
  beltloop: number;
  paper: number;
  cloth2: number;
  lockHole: number;
  otherCraft: number;
  sewButton: number;
  otherFiliaoStr: string;
  otherCraftStr: string;

  total: number;

  // productNumber: string;
}

// 表单数据初始化为空对象，避免访问 undefined 错误
const formData = ref<CostData>({
  skcId: "",
  spuId: "",
  shopId: "",
  cloth: 0,
  cutInto: 0,
  tailor: 0,
  printing: 0,
  pack: 0,
  more: 0,
  qualityRefundRate: 0,
  sizeRefundRate: 0,
  qualityScore: 0,
  freight: 0.24,
  button: 0,
  ribbing: 0,
  rubber: 0,
  string: 0,
  sack: 0.32,
  airHole: 0,
  zipper: 0,
  chain: 0,
  beltloop: 0,
  paper: 0,
  cloth2: 0,
  lockHole: 0,
  otherCraft: 0,
  sewButton: 0,
  otherFiliaoStr: "",
  otherCraftStr: "",
  total: 0,

  // productNumber: "",
});

// 控制是否可编辑
const isEditable = ref(false);

// 所有可选择的成本项
const availableCostItems = [
  // 面料板块
  { label: "布料", value: "cloth" },
  { label: "其他面料", value: "cloth2" },

  // 工艺板块
  { label: "裁剪", value: "cutInto" },
  { label: "车间", value: "tailor" },
  { label: "印花", value: "printing" },
  { label: "打包", value: "pack" },
  { label: "钉扣子", value: "sewButton" },
  { label: "锁眼", value: "lockHole" },
  { label: "其他工艺", value: "otherCraft" },

  // 辅料板块
  { label: "拉链", value: "zipper" },
  { label: "罗纹", value: "ribbing" },
  { label: "气眼", value: "airHole" },
  { label: "耳仔", value: "beltloop" },
  { label: "绳子", value: "string" },
  { label: "扣子", value: "button" },
  { label: "橡筋", value: "rubber" },
  { label: "链条", value: "chain" },
  { label: "衬纸", value: "paper" },
  { label: "袋子", value: "sack" },

  // 运费和其他板块
  { label: "运费", value: "freight" },
  { label: "其他", value: "more" },
];
import { useRouter } from "vue-router";

const router = useRouter();
// 跳转到详情页
const handleViewDetail = async (skcId) => {
  const loading = ElLoading.service({
    lock: true,
    text: "正在加载商品详情...",
  });

  try {
    await router.push({
      name: "productInfo",
      params: { skcId: skcId },
    });
  } catch (err) {
    ElMessage.error("跳转失败");
  } finally {
    loading.close();
  }
};

// 表格内直接编辑货号
const handleEditProductNumber = async (product: Product) => {
  try {
    const { value } = await ElMessageBox.prompt("请输入新货号", "编辑货号", {
      confirmButtonText: "保存",
      cancelButtonText: "取消",
      inputPattern: /^[A-Za-z0-9]{2,20}$/,
      inputErrorMessage: "货号需为2-20位字母数字组合",
      inputValue: product.productNumber || "",
    });

    // 调用更新接口
    const response = await request.post("/api/product/updataProductNumber", {
      skcId: product.skcId,
      productNumber: value,
    });

    if (response.code === 1) {
      product.productNumber = value; // 直接更新前端数据
      ElMessage.success("货号更新成功");
    } else {
      ElMessage.error("更新失败：" + response.message);
    }
  } catch (error) {
    // 用户取消时不处理
  }
};

// 获取商品的成本数据
// 获取商品的成本数据
const fetchProductCost = async (skcId) => {
  const loading = ElLoading.service({
    lock: true,
    text: "正在加载商品成本...",
  });
  try {
    const response = await request.get("/api/productCost/getCostData", {
      params: { skcId },
    });

    console.log("获取商品成本数据成功:", response);
    if (response.code === 1) {
      // 填充 skcId, spuId, shopId 作为字符串
      // formData.value.skcId = skcId; // 直接将 skcId 作为字符串填入
      // formData.value.spuId = response.data.spuId; // 确保 spuId 也是字符串
      // formData.value.shopId = currentShop.value.shopId; // shopId 保持为字符串
      // formData.value.otherFiliaoStr = response.data.otherFiliaoStr;
      // formData.value.otherCraftStr = response.data.otherCraftStr;

      // 填充其他数值类型的字段，并确保转换为数字
      formData.value = Object.keys(formData.value).reduce((acc, key) => {
        if (
          key === "skcId" ||
          key === "spuId" ||
          key === "shopId" ||
          key === "otherFiliaoStr" ||
          key === "otherCraftStr"
        ) {
          acc[key] = response.data[key];
        } else {
          // 其他字段转换为数字
          acc[key] = parseFloat(response.data[key]) || 0;
        }
        return acc;
      }, {} as CostData);
    }

    console.log("填充后的 formData:", formData.value);
  } catch (error) {
    console.error("获取商品成本数据失败:", error);
  } finally {
    loading.close();
  }
};

// 禁止输入非数字// 输入处理函数
// 输入处理函数
// 禁止输入非数字// 输入处理函数
const handleInput = (field: keyof CostData) => {
  let value = formData.value[field];
  console.log("handleInput", field, value);

  // 允许输入数字和一个小数点
  // 移除所有非数字字符和多余的小数点
  value = value.replace(/[^0-9.]/g, ""); // 移除所有非数字和小数点的字符
  console.log("handleInput1", field, value);

  // 如果有多个小数点，移除后面的
  if ((value.match(/\./g) || []).length > 1) {
    value =
      value.substring(0, value.lastIndexOf(".") + 1) +
      value.substring(value.lastIndexOf(".") + 1).replace(/\./g, "");
  }
  console.log("handleInput2", field, value);

  // 确保输入的值是有效的数字，空值或者无效数字则设置为 0
  // 如果值为空字符串，保持为空
  if (value === "") {
    formData.value[field] = 0;
  } else if (!isNaN(Number(value))) {
    // 如果值是有效的数字，则更新为数字类型
    formData.value[field] = parseFloat(value);
  } else {
    // 否则，保留之前的输入值
    formData.value[field] = value;
  }

  console.log("handleInput3", field, formData.value[field]);
};

// 编辑/保存按钮功能
const toggleEdit = () => {
  isEditable.value = !isEditable.value;
  // 保存时检查所有输入框是否为空，若为空，则设置为 0
  // 保存时检查字段
  Object.keys(formData.value).forEach((key) => {
    if (key === "otherFiliaoStr" || key === "otherCraftStr") {
      // 特殊字段：空值设置为空字符串
      if (!formData.value[key]?.trim()) {
        formData.value[key] = "";
      }
    } else {
      // 其他数值字段：空值设置为 0
      if (!formData.value[key] && formData.value[key] !== 0) {
        formData.value[key] = 0;
      }
    }
  });

  console.log(formData.value);
};

// 提交表单
const submitForm = async () => {
  // 保存时检查所有输入框是否为空，若为空，则设置为 0
  // Object.keys(formData.value).forEach((key) => {
  //   if (!formData.value[key] && formData.value[key] !== 0) {
  //     formData.value[key] = 0;
  //   }
  // });

  try {
    console.log("提交表单:", formData.value);
    const response = await request.post(
      "/api/productCost/saveCost",
      formData.value
    );

    console.log("货号：+" + response.code);
    if (response.code) {
      // 提交成功提示
      alert("提交成功！");
      resetForm();
      fetchProducts(
        currentShop.value.shopId,
        currentPage.value,
        pageSize.value,
        searchKey.value, // 传递搜索关键字
        currentTab.value
      );
      // 关键：直接修改响应式对象的属性
      //props.product.totalCost = form.totalCost; // 假设product是响应式对象
    }
  } catch (error) {
    console.error("提交失败:", error);
  }
};

// 重置表单
const resetForm = () => {
  dialogVisible.value = false;
};

// 计算总成本
// 总成本计算优化（处理浮点精度）
// 更新总成本计算
const totalCost = computed(() => {
  let sum = 0;

  // 计算固定项
  availableCostItems.forEach((item) => {
    sum += Number(formData.value[item.value]) || 0;
  });

  // 计算动态工艺
  if (formData.value.otherCraftStr) {
    formData.value.otherCraftStr.split("\n").forEach((craft) => {
      const [_, cost] = craft.split("\t");
      sum += Number(cost) || 0;
    });
  }

  // 计算动态辅料
  if (formData.value.otherFiliaoStr) {
    formData.value.otherFiliaoStr.split("\n").forEach((material) => {
      const [_, cost] = material.split("\t");
      sum += Number(cost) || 0;
    });
  }

  formData.value.total = sum;

  //productCostMap.value[formData.value.skcId] = sum;
  return Math.round(sum * 100) / 100;
});

// 打开弹窗
const openEditDialog = (product) => {
  dialogVisible.value = true;
  fetchProductCost(product.skcId);
  isEditable.value = false;
  console.log("成本项" + availableCostItems);
  console.log("成本项" + availableCostItems.slice(2, 7));
};

// 添加辅料弹窗状态
const addMaterialDialogVisible = ref(false);
const addMaterialForm = ref({
  name: "",
  cost: 0,
});

// 已添加的辅料列表
const addedMaterials = computed(() => {
  //console.log("addedMaterials1", formData.value.otherFiliaoStr);
  if (!formData.value.otherFiliaoStr) return [];
  //console.log("addedMaterials2", formData.value.otherFiliaoStr);
  return formData.value.otherFiliaoStr.split("\n").map((item) => {
    const [name, cost] = item.split("\t");
    return { name, cost: parseFloat(cost) };
  });
});

// 打开添加辅料弹窗
const openAddMaterialDialog = () => {
  addMaterialDialogVisible.value = true;
};

// 保存辅料
const saveMaterial = () => {
  if (addMaterialForm.value.name && addMaterialForm.value.cost) {
    const newMaterial = `${addMaterialForm.value.name}\t${addMaterialForm.value.cost}`;
    if (formData.value.otherFiliaoStr) {
      formData.value.otherFiliaoStr += `\n${newMaterial}`;
    } else {
      formData.value.otherFiliaoStr = newMaterial;
    }
    addMaterialDialogVisible.value = false;
    addMaterialForm.value = { name: "", cost: 0 };
  }
};

// 删除辅料
// 删除辅料（带确认）
const removeMaterial = async (index: number) => {
  try {
    await ElMessageBox.confirm(
      "确定要删除该辅料吗？删除后不可恢复！",
      "删除确认",
      {
        confirmButtonText: "确认删除",
        cancelButtonText: "取消",
        type: "warning",
        confirmButtonClass: "confirm-delete-btn",
        cancelButtonClass: "cancel-delete-btn",
      }
    );

    const materials = formData.value.otherFiliaoStr.split("\n");
    materials.splice(index, 1);
    formData.value.otherFiliaoStr = materials.join("\n");
    ElMessage.success("辅料删除成功");
  } catch (error) {
    // 用户点击取消时不执行操作
  }
};

// 其他工艺相关状态
const addCraftDialogVisible = ref(false);
const addCraftForm = ref({
  name: "",
  cost: 0,
});

// 已添加工艺列表
const addedCrafts = computed(() => {
  if (!formData.value.otherCraftStr) return [];
  return formData.value.otherCraftStr.split("\n").map((item) => {
    const [name, cost] = item.split("\t");
    return { name, cost: parseFloat(cost) };
  });
});

// 打开添加工艺弹窗
const openAddCraftDialog = () => {
  addCraftDialogVisible.value = true;
};

// 保存工艺
const saveCraft = () => {
  if (addCraftForm.value.name && addCraftForm.value.cost) {
    const newCraft = `${addCraftForm.value.name}\t${addCraftForm.value.cost}`;
    formData.value.otherCraftStr = formData.value.otherCraftStr
      ? `${formData.value.otherCraftStr}\n${newCraft}`
      : newCraft;
    addCraftDialogVisible.value = false;
    addCraftForm.value = { name: "", cost: 0 };
  }
};

// 删除工艺（带确认）
const removeCraft = async (index: number) => {
  try {
    await ElMessageBox.confirm(
      "确定要删除该工艺吗？删除后不可恢复！",
      "删除确认",
      {
        confirmButtonText: "确认删除",
        cancelButtonText: "取消",
        type: "warning",
        confirmButtonClass: "confirm-delete-btn",
        cancelButtonClass: "cancel-delete-btn",
      }
    );

    const crafts = formData.value.otherCraftStr.split("\n");
    crafts.splice(index, 1);
    formData.value.otherCraftStr = crafts.join("\n");
    ElMessage.success("工艺删除成功");
  } catch (error) {
    // 用户点击取消时不执行操作
  }
};

//纸样文件功能
const patternFileModalVisible = ref(false);
const fileList = ref([]);
const patternFileList = ref([]); // 存储纸样文件列表
const selectedPatternFiles = ref([]); // 存储选中的文件
const selectSkcId = ref("");

const uploadUrl = "/api/patternFile/uploadPattern"; // 后端上传接口

const openPatternFileModal = async (skcId: string) => {
  patternFileModalVisible.value = true;
  selectSkcId.value = skcId;
  console.log("打开弹窗" + patternFileModalVisible.value);
  // 获取历史上传的纸样文件
  await fetchPatternFiles(skcId);
};

const fetchPatternFiles = async (skcId: string) => {
  try {
    const response = await request.get(`/api/patternFile/getPatternFiles`, {
      params: { skcId },
    });
    patternFileList.value = response; // 获取返回的文件列表
  } catch (error) {
    console.error("获取纸样文件失败:", error);
  }
};

const handleUploadSuccess = (response) => {
  console.log("上传成功:", response);
  // 更新文件列表
  fetchPatternFiles(selectSkcId.value); // 重新获取文件列表
};

const handleUploadError = (error) => {
  console.error("上传失败:", error);
};

const beforeUpload = (file) => {
  // 检查文件名是否已经存在于 fileList 中
  console.log("beforeUpload0:", file);
  //console.log("beforeUpload1:", patternFileList.value);
  const isFileExists = patternFileList.value.some(
    (existingFile) => existingFile.fileName === file.name
  );

  console.log("beforeUpload1:", isFileExists);

  if (isFileExists) {
    // 如果文件已存在，弹出提示框并返回 false 来取消上传
    ElMessage.error(`文件 "${file.name}" 已经存在，无法上传同名文件`);
    console.log("beforeUpload2:", file);
    return false;
  }
  console.log("beforeUpload3:", file);
  return true;
};
const downloadPatternFile = async (file) => {
  try {
    // 发送请求获取文件
    const response = await request.get("/api/patternFile/downloadPatternFile", {
      params: { fileId: file.id },
      responseType: "blob", // 设置为 'blob' 以接收文件
    });
    console.log("返回数据" + response);

    // 创建一个 URL 对象
    const url = window.URL.createObjectURL(response);

    // 创建一个临时的下载链接
    const a = document.createElement("a");
    a.href = url;
    a.download = file.fileName || "download"; // 设置下载的文件名，如果没有则默认为 "download"

    // 触发下载
    document.body.appendChild(a);
    a.click();
    document.body.removeChild(a);

    // 清理 URL 对象
    window.URL.revokeObjectURL(url);
  } catch (error) {
    console.error("下载失败:", error);
  }
};

const deletePatternFile = async (file: File) => {
  try {
    // 弹出确认对话框
    const result = await ElMessageBox.confirm(
      `您确定要删除文件 ${file.fileName} 吗？`,
      "警告",
      {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      }
    );

    // 如果用户点击了确定按钮
    if (result === "confirm") {
      console.log("删除文件:", file);
      console.log("删除文件:", file.id);

      // 调用 API 删除文件
      const response = await request.post(
        "/api/patternFile/deletePatternFile",
        {
          skcId: file.skcId,
          fileName: file.fileName,
        }
      );

      console.log("删除文件成功:", response);

      // 删除成功后重新加载文件列表
      fetchPatternFiles(file.skcId);

      // 提示用户删除成功
      ElMessage.success("文件删除成功");
    }
  } catch (error) {
    // 如果用户取消删除操作或发生其他错误
    console.error("删除失败:", error);
    ElMessage.error("文件删除失败");
  }
};

const resetPatternFileModal = () => {
  patternFileModalVisible.value = false;
  selectedPatternFiles.value = []; // 重置选中的文件
};

// 监听选择变化
const handleSelectionChange = (val) => {
  // val 是选中的文件数组
  selectedPatternFiles.value = val;
  console.log("选中的文件:", selectedPatternFiles.value); // 调试输出选中的文件
};

//下载选中文件
const handleDownloadSelectedFiles = async () => {
  console.log(selectedPatternFiles.value);
  // 如果没有选中任何文件
  if (selectedPatternFiles.value.length === 0) {
    console.log("没有选中文件");
    return;
  }

  // 遍历选中的文件进行下载
  for (const file of selectedPatternFiles.value) {
    try {
      const response = await request.get(
        "/api/patternFile/downloadPatternFile",
        {
          params: { fileId: file.id },
          responseType: "blob", // 设置返回数据类型为 'blob'
        }
      );

      console.log("返回数据:", response);

      // 创建一个 URL 对象
      const url = window.URL.createObjectURL(response);

      // 创建一个临时的下载链接
      const a = document.createElement("a");
      a.href = url;
      a.download = file.fileName || "download"; // 默认文件名

      // 触发下载
      document.body.appendChild(a);
      a.click();
      document.body.removeChild(a);

      // 清理 URL 对象
      window.URL.revokeObjectURL(url);
    } catch (error) {
      console.error("下载失败:", error);
    }
  }
};

const selectable = (row) => {
  // 选择逻辑：可以根据需要实现
  return true;
  //return row.isSelected;
};

// 计算属性：过滤商品
const filteredProducts = computed(() => {
  if (currentTab.value === "all") {
    return products.value;
  }
  return products.value.filter(
    (product) => mapTabToState(currentTab.value) === product.state
  );
});

// 方法定义
async function fetchProducts(
  shopId: string,
  page: number,
  size: number,
  searchKey: string,
  currentTab: string
) {
  const loading = ElLoading.service({
    lock: true,
    text: "正在加载商品信息...",
  });
  try {
    const response = await request.get("/api/product/all", {
      params: { shopId, page, size, searchKey, currentTab },
    });
    //products.value = response.data;
    products.value = response.records; // 记录
    originalProducts.value = response.records;
    totalProducts.value = response.total; // 总数
    //fetchBoundDesigners(shopId);

    console.log("products.value:", products.value);

    checkBoundProducts(); // 获取绑定状态

    // 获取每个商品的成本
    // products.value.forEach(async (product) => {
    //   const costRes = await request.get("/api/productCost/getTotalCost", {
    //     params: { skcId: product.skcId },
    //   });
    //   productCostMap.value[product.skcId] = costRes.data;
    // });
  } catch (error) {
    console.error("获取商品失败:", error);
  } finally {
    loading.close();
  }
}

const popupPosition = ref({ x: 0, y: 0 }); // 弹窗的位置

// 切换弹窗显示
const toggleExpand = (skcId, event) => {
  expandedProductId.value = expandedProductId.value === skcId ? null : skcId;
  // if (expandedProductId.value) {
  //   // 获取点击位置
  //   const rect = event.target.getBoundingClientRect();
  //   popupPosition.value = {
  //     x: rect.left + window.scrollX - 88,
  //     y: rect.top + window.scrollY + rect.height, // 在点击元素下方显示
  //   };
  // }

  expandedProductId.value = skcId;

  const updatePosition = () => {
    const clickX = event.clientX;
    const clickY = event.clientY;
    popupPosition.value = {
      x: clickX - 88,
      y: clickY + 10,
    };
  };

  // 初始定位
  updatePosition();

  // 添加滚动监听实时更新
  const handleScroll = () => updatePosition();
  window.addEventListener("scroll", handleScroll);

  // 组件卸载时移除监听
  onUnmounted(() => {
    window.removeEventListener("scroll", handleScroll);
  });
};

// 计算弹窗样式
const popupStyle = computed(() => {
  return {
    left: `${popupPosition.value.x}px`,
    top: `${popupPosition.value.y}px`,
  };
});

// 获取绑定的设计师列表
// const fetchBoundDesigners = async (shopId) => {
//   for (const product of products.value) {
//     try {
//       const response = await request.get("/api/user/getBoundDesigners", {
//         params: { skcId: product.skcId, shopId },
//       });
//       boundDesigners.value.set(product.skcId, response.data);
//     } catch (error) {
//       console.error(`获取 ${product.skcId} 绑定设计师失败:`, error);
//     }
//   }
// };

// 查询当前设计师绑定了哪些商品
const checkBoundProducts = async () => {
  try {
    const response = await request.get("/api/product/getDesignerBind", {
      params: {
        shopId: shopStore.currentShop.shopId,
        nickName: currentDesigner.value,
      },
    });

    console.log("response.data:", response);

    boundProducts.value = new Map();
    const boundSkcIds = new Set(response); // 绑定的 SKC ID 列表

    products.value.forEach((product) => {
      boundProducts.value.set(product.skcId, boundSkcIds.has(product.skcId));
    });
  } catch (error) {
    console.error("获取绑定状态失败:", error);
  }
};

// 绑定用户到商品
const bindUserToProduct = async (product) => {
  console.log(userStore.currentUser);
  try {
    const response = await request.post("/api/product/bind", {
      skcId: product.skcId,
      shopId: shopStore.currentShop.shopId,
      name: currentDesigner.value,
      shopName: shopStore.currentShop.shopName,
    });

    if (response.code === 1) {
      boundProducts.value.set(product.skcId, true);

      // if (!boundDesigners.value.has(product.skcId)) {
      //   boundDesigners.value.set(product.skcId, []);
      // }
      // boundDesigners.value.get(product.skcId)?.push(currentDesigner.value);
      // 直接操作 product.designerList
      if (!product.designerList) {
        product.designerList = []; // 初始化数组
      }

      // 添加设计师（带去重校验）
      if (!product.designerList.includes(currentDesigner.value)) {
        product.designerList.push(currentDesigner.value);
      }
    }
  } catch (error) {
    console.error("绑定失败", error);
  }
};

// 解绑用户与商品
const unbindUserFromProduct = async (product) => {
  try {
    const response = await request.post("/api/product/unbind", {
      skcId: product.skcId,
      shopId: shopStore.currentShop.shopId,
      name: currentDesigner.value,
      shopName: shopStore.currentShop.shopName,
    });

    if (response.code === 1) {
      boundProducts.value.set(product.skcId, false);

      // 更新绑定设计师的列表，移除设计师
      // const designers = boundDesigners.value.get(product.skcId);
      // if (designers) {
      //   boundDesigners.value.set(
      //     product.skcId,
      //     designers.filter((designer) => designer !== currentDesigner.value)
      //   );
      // }

      // 直接操作 product.designerList

      if (product.designerList) {
        product.designerList = product.designerList.filter(
          (designer) => designer !== currentDesigner.value
        );
      }
    }
  } catch (error) {
    console.error("解绑失败", error);
  }
};

async function handleSearch() {
  if (searchKey.value.trim() === "") {
    isSearching.value = false;
    products.value = originalProducts.value;
  } else {
    isSearching.value = true;
    await fetchProducts(
      currentShop.value.shopId,
      currentPage.value,
      pageSize.value,
      searchKey.value, // 传递搜索关键字
      currentTab.value
    );

    // products.value = originalProducts.value.filter((product) =>
    //   product.skcId.includes(searchKey.value)
    // );
  }
}

// 监听店铺切换
watch(
  () => shopStore.currentShop,
  (newShop) => {
    currentPage.value = 1;
    fetchProducts(
      newShop.shopId,
      currentPage.value,
      pageSize.value,
      isSearching.value ? searchKey.value : "",
      currentTab.value
    );
  },
  { immediate: true }
);

watch(
  () => currentTab.value,
  (newTab) => {
    currentPage.value = 1; // 重置为第一页
    fetchProducts(
      shopStore.currentShop.shopId,
      currentPage.value,
      pageSize.value,
      searchKey.value,
      currentTab.value
    );
  },
  { immediate: true }
);

watch(searchKey, (newKey) => {
  // 实时过滤逻辑

  currentPage.value = 1;
  fetchProducts(
    currentShop.value.shopId,
    currentPage.value,
    pageSize.value,
    isSearching.value ? newKey : "",
    currentTab.value
  );
  // products.value = originalProducts.value.filter((product) =>
  //   product.skcId.includes(newKey)
  // );
});

const changeTab = (tab) => {
  currentTab.value = tab;
  currentPage.value = 1;
};

const mapStatus = (state) => {
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
    "17": "已终止",
  };
  return statusMap[state] || "未知状态";
};

const mapTabToState = (tab) => {
  const tabStateMap = {
    unpublished: "1",
    pendingSample: "3",
    reviewing: "5",
    pricing: "7",
    pendingOrder: "10",
    published: "12",
    terminated: "13",
  };
  return tabStateMap[tab];
};

const formatDate = (timestamp) => {
  const date = new Date(timestamp);
  return date.toISOString().slice(0, 10);
};

// 处理每页显示数量变化
const handleSizeChange = (newSize: number) => {
  pageSize.value = newSize;
  currentPage.value = 1; // 重置到第一页
  fetchProducts(
    shopStore.currentShop.shopId,
    currentPage.value,
    pageSize.value,
    isSearching.value ? searchKey.value : "",
    currentTab.value
  );
};

// 分页变化时
const handlePageChange = (page: number) => {
  currentPage.value = page;
  fetchProducts(
    shopStore.currentShop.shopId,
    page,
    pageSize.value,
    isSearching.value ? searchKey.value : "",
    currentTab.value
  );
};
// 生命周期钩子
onMounted(() => {
  fetchProducts(
    shopStore.currentShop.shopId,
    currentPage.value,
    pageSize.value,
    isSearching.value ? searchKey.value : "",
    currentTab.value
  );

  console.log(shopStore.currentShop);
  //console.log(boundDesigners.value);

  const currentDesigner = ref(userStore.currentUser.nickName);
  console.log(userStore.currentUser);
});
</script>

<style scoped>
/* 使页面背景色变为白色 */
* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}

body {
  background-color: #ffffff;
  color: #333333;
  font-family: Arial, sans-serif;
}

.product-page {
  padding: 20px;
  background-color: #ffffff;
  flex: 1;
  box-shadow: 0px 4px 10px rgba(0, 0, 0, 0.1); /* 添加轻微阴影 */
  border-radius: 8px;
}

.product-image {
  max-width: 135px; /* 限制图片最大宽度 */
  max-height: 135px; /* 限制图片最大高度 */
  object-fit: contain; /* 保持图片比例，避免变形 */
}

/* 搜索框样式 */
.search-container {
  margin-bottom: 20px;
  display: flex;
  align-items: center;
  gap: 10px;
}

.search-container input {
  padding: 8px 12px;
  font-size: 16px;
  border-radius: 5px;
  background-color: #f8f8f8;
  color: #333;
  border: 1px solid #ddd;
  width: 250px;
}

.search-container button {
  padding: 8px 12px;
  font-size: 16px;
  background-color: #000000;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  transition: 0.3s;
}

.search-container button:hover {
  background-color: #2980b9;
}

/* 标签栏样式 */
.tabs button {
  padding: 10px 20px;
  margin-right: 10px;
  background-color: #f1f1f1;
  color: #333;
  border: 1px solid #ddd;
  border-radius: 5px;
  cursor: pointer;
}

.tabs .active {
  background-color: #3498db;
  color: white;
}

/* 表格样式 */
.product-table {
  width: 100%;
  border-collapse: collapse;
  margin-top: 20px;
}

.product-table th,
.product-table td {
  border: 1px solid #ddd;
  padding: 12px;
  text-align: left;
  background-color: #f9f9f9;
  color: #333;
}

.product-table th {
  background-color: #f1f1f1;
}

.product-table td:hover {
  background-color: #eaf3fb;
}

/* 绑定和查看样纸按钮容器 */
.button-container {
  display: flex;
  flex-direction: column; /* 设置为列方向，按钮会上下排列 */
  gap: 5px; /* 设置按钮之间的间距 */
}
/* 设计师按钮样式 */
.bind-btn {
  background-color: #000000;
  color: white;
  padding: 3px 15px;
  border-radius: 5px;
  cursor: pointer;
  border: none;
  transition: background-color 0.3s;
}

.unbind-btn {
  background-color: #e74c3c;
  color: white;
  padding: 3px 15px;
  border-radius: 5px;
  cursor: pointer;
  border: none;
  transition: background-color 0.3s;
}

.bind-btn:hover {
  background-color: #303031;
}

.unbind-btn:hover {
  background-color: #c0392b;
}

.Patternbtn {
  background-color: #000000;
  color: white;
  padding: 6px 15px;
  border-radius: 5px;
  cursor: pointer;
  border: none;
  transition: background-color 0.3s;
}

.Patternbtn:hover {
  background-color: #303031;
}

.Detailbtn {
  background-color: #000000;
  color: white;
  padding: 6px 15px;
  border-radius: 5px;
  cursor: pointer;
  border: none;
  transition: background-color 0.3s;
}

.Detailbtn:hover {
  background-color: #303031;
}

.edit-cost-btn {
  background-color: #000000;
  color: white;
  padding: 3px 15px;
  border-radius: 5px;
  cursor: pointer;
  border: none;
  transition: background-color 0.3s;

  /* &[style*="background-color: #ffc700"] {
    background-color: #ffc700 !important;
    color: #333;
    border-color: #ffc700;
  } */
}

.edit-cost-btn:hover {
  background-color: #303031;
}

/* 设计师小标签 */
.designer-list {
  display: flex;
  flex-wrap: wrap;
  gap: 5px;
}

.designer-name {
  background: linear-gradient(135deg, #000000, #e44a39);
  color: white;
  padding: 4px 10px;
  border-radius: 15px;
  font-size: 12px;
  font-weight: bold;
}
/* 设计师弹窗样式 */
.designer-popup {
  position: fixed;
  background: white;
  width: 120px;
  padding: 10px;
  border-radius: 8px;
  box-shadow: 0px 4px 10px rgba(0, 0, 0, 0.2);
  z-index: 1000;
  color: black;
}

.popup-content {
  text-align: center;
}

.close-btn {
  background: #ff7eb3;
  color: white;
  border: none;
  padding: 8px 15px;
  font-size: 14px;
  border-radius: 20px;
  cursor: pointer;
  margin-top: 15px;
}

.close-btn:hover {
  background: #ff758c;
}

.transparent-dialog .el-dialog__body {
  padding: 20px;
}

.transparent-dialog .el-row {
  margin-bottom: 20px;
}

.transparent-dialog .el-col {
  padding: 10px;
}

.transparent-dialog h4 {
  margin-bottom: 10px;
  font-size: 16px;
  font-weight: bold;
  color: #333;
}

.compact-dialog {
  .el-dialog__body {
    padding: 15px 25px;
  }

  .section {
    margin-bottom: 18px;
    padding: 12px;
    background: #f8f9fa;
    border-radius: 6px;

    &-title {
      text-align: center;
      margin: 0 0 15px 0;
      font-size: 15px;
      color: #606266;
      font-weight: 600;
      letter-spacing: 2px;
    }
  }

  .compact-item .el-form-item__content {
    line-height: 32px;
  }

  /* 统一操作按钮样式 */
  .material-table .el-button--danger {
    padding: 5px;
    min-height: auto;
  }

  /* 紧凑表格样式 */
  .material-table {
    th {
      background-color: #f5f7fa;
    }
    td {
      padding: 6px 10px !important;
    }
  }

  /* 添加按钮统一样式 */
  .add-button {
    margin: 10px 0;
    width: 100%;
    background: #000;
    border-color: #000;
    &:hover {
      opacity: 0.8;
    }
  }

  .footer-section {
    margin-top: 20px;
    border-top: 1px solid #eee;
    padding-top: 15px;

    .total-cost-input {
      width: 200px;
      .el-input__inner {
        font-weight: bold;
        color: #e6a23c;
      }
    }

    .action-buttons {
      margin-top: 15px;
      display: flex;
      justify-content: flex-end;
      gap: 12px;

      .edit-btn {
        background: #000;
        border-color: #000;
      }

      .submit-btn {
        background: #409eff;
        border-color: #409eff;
      }
    }
  }
}

/* 删除确认弹窗样式 */
.confirm-delete-btn {
  background-color: #ff4d4f !important;
  border-color: #ff4d4f !important;
  color: white !important;

  &:hover {
    opacity: 0.8;
    background-color: #ff4d4f !important;
  }
}

.delete-icon {
  cursor: pointer;
  transition: all 0.3s ease;
}

.delete-icon:hover {
  transform: scale(1.1);
}

/* 添加按钮样式 */
.add-productNumber-btn {
  background-color: #000000;
  color: white;
  padding: 6px 15px;
  border-radius: 5px;
  cursor: pointer;
  border: none;
  transition: background-color 0.3s;
}

.add-productNumber-btn:hover {
  background-color: #303031;
}

/* 添加自定义样式 */
.custom-pagination {
  margin-top: 20px;
  justify-content: flex-end;
}

/* 调整下拉菜单样式 */
.el-select .el-input {
  width: 100px;
}

.el-pagination__sizes {
  margin: 0 16px 0 0;
}
</style>
