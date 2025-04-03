<template>
  <div class="container">
    <!-- 编辑控制 -->
    <div class="control-bar">
      <div class="search-container">
        <el-input
          v-model="searchSkcid"
          placeholder="输入SKCID搜索"
          style="width: 300px"
          @keyup.enter="handleSearch"
        >
          <template #append>
            <el-button :icon="Search" @click="handleSearch" />
          </template>
        </el-input>
      </div>
      <el-button type="primary" @click="toggleEditMode">
        {{ isEditing ? "保存信息" : "进入编辑" }}
      </el-button>
      <el-button type="success" @click="exportExcel">导出Excel</el-button>
    </div>

    <!-- 基本信息 -->
    <el-card class="section">
      <h3 class="title">基本信息</h3>
      <el-row :gutter="20">
        <el-col :span="8">
          <el-form-item label="样衣克重(g)：">
            <el-input v-model="formData.sampleWeight" :disabled="!isEditing" />
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="样布克重(g)：">
            <el-input
              v-model="formData.fabricSampleWeight"
              :disabled="!isEditing"
            />
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="成分：">
            <el-input v-model="formData.material" :disabled="!isEditing" />
          </el-form-item>
        </el-col>
      </el-row>
    </el-card>

    <!-- 尺码信息 -->
    <el-card class="section">
      <h3 class="title">尺码信息</h3>
      <div class="size-controls">
        <el-button @click="addSizeColumn" :disabled="!isEditing"
          >+ 列</el-button
        >
        <el-button @click="addSizeRow" :disabled="!isEditing">+ 行</el-button>
        <el-button @click="removeSizeColumn" :disabled="!isEditing"
          >- 列</el-button
        >
        <el-button @click="removeSizeRow" :disabled="!isEditing"
          >- 行</el-button
        >
      </div>
      <el-table :data="sizeTable.data" border>
        <el-table-column label="尺码" min-width="120" key="fixed-size-column">
          <template #default="{ $index }">
            <el-input
              v-model="sizeTable.data[$index][0]"
              :disabled="!isEditing"
            />
          </template>
        </el-table-column>

        <el-table-column
          v-for="(col, colIndex) in sizeTable.columns.slice(1)"
          :key="colIndex"
          :label="col"
          min-width="120"
        >
          <!-- 添加表头编辑功能 -->
          <template #header>
            <el-input
              v-model="sizeTable.columns[colIndex + 1]"
              :disabled="!isEditing"
              placeholder="请输入尺寸名称"
              size="small"
            />
          </template>
          <template #default="{ $index }">
            <el-input
              v-model="sizeTable.data[$index][colIndex + 1]"
              :disabled="!isEditing"
            />
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <!-- 布料详情 -->
    <el-card class="section">
      <h3 class="title">布料详情</h3>
      <div class="fabric-controls">
        <el-button @click="addFabricItem" :disabled="!isEditing"
          >+ 添加布料</el-button
        >
      </div>
      <el-table :data="fabricDetails" border>
        <el-table-column prop="name" label="名称" width="180">
          <template #default="{ row }">
            <el-input v-model="row.name" :disabled="!isEditing" />
          </template>
        </el-table-column>
        <el-table-column prop="color" label="颜色/色号">
          <template #default="{ row }">
            <el-input v-model="row.color" :disabled="!isEditing" />
          </template>
        </el-table-column>
        <el-table-column prop="spec" label="单位/规格">
          <template #default="{ row }">
            <el-input v-model="row.spec" :disabled="!isEditing" />
          </template>
        </el-table-column>
        <el-table-column prop="supplier" label="供应商">
          <template #default="{ row }">
            <el-input v-model="row.supplier" :disabled="!isEditing" />
          </template>
        </el-table-column>
        <el-table-column label="操作" width="80">
          <template #default="{ $index }">
            <el-button
              type="danger"
              icon="Delete"
              @click="removeFabricItem($index)"
              :disabled="!isEditing"
            />
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <!-- 工艺要求 -->
    <el-card class="section">
      <h3 class="title">工艺要求</h3>
      <el-input
        v-model="formData.technologicalRequirements"
        type="textarea"
        :rows="4"
        :disabled="!isEditing"
      />
    </el-card>

    <!-- 辅料信息 -->
    <el-card class="section">
      <h3 class="title">辅料信息</h3>
      <div class="accessory-controls">
        <el-button @click="addAccessoryItem" :disabled="!isEditing"
          >+ 添加辅料</el-button
        >
      </div>
      <el-table :data="accessories" border>
        <el-table-column prop="name" label="名称" width="200">
          <template #default="{ row }">
            <el-input v-model="row.name" :disabled="!isEditing" />
          </template>
        </el-table-column>
        <el-table-column prop="supplier" label="供应商">
          <template #default="{ row }">
            <el-input v-model="row.supplier" :disabled="!isEditing" />
          </template>
        </el-table-column>
        <el-table-column label="操作" width="80">
          <template #default="{ $index }">
            <el-button
              type="danger"
              icon="Delete"
              @click="removeAccessoryItem($index)"
              :disabled="!isEditing"
            />
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <!-- 备注 -->
    <el-card class="section">
      <h3 class="title">备注</h3>
      <el-input
        v-model="formData.remark"
        type="textarea"
        :rows="3"
        :disabled="!isEditing"
      />
    </el-card>

    <!-- 细节图片 -->
    <el-card class="section">
      <h3 class="title">细节图片</h3>
      <el-upload
        multiple
        :limit="20"
        :on-exceed="handleExceed"
        action="/api/productInfo/upload"
        list-type="picture-card"
        :file-list="fileList"
        :before-upload="(file) => beforeUpload(file, formData.skcId)"
        :on-success="handleUploadSuccess"
        :on-preview="handlePictureCardPreview"
        :on-remove="handleRemove"
        :data="{ skcId: formData.skcId }"
        :disabled="!isEditing"
      >
        <el-icon><Plus /></el-icon>
        <template #tip>
          <div class="upload-tip">
            最多上传20张图片（已上传 {{ fileList.length }} 张）
          </div>
        </template>
      </el-upload>
    </el-card>
  </div>

  <el-dialog
    v-model="dialogVisible"
    title="图片预览"
    width="80%"
    append-to-body
  >
    <img
      :src="dialogImageUrl"
      alt="预览图片"
      style="width: 100%; max-height: 70vh; object-fit: contain"
    />
  </el-dialog>
</template>

<script setup lang="ts">
import { ref, reactive, computed, onMounted } from "vue";
import { ElMessage, ElMessageBox } from "element-plus";
import type { UploadProps, UploadUserFile } from "element-plus";
import { Plus, Delete } from "@element-plus/icons-vue";
import * as XLSX from "xlsx";
import { Search } from "@element-plus/icons-vue";
// 添加路由相关引用
import { useRoute } from "vue-router";

const route = useRoute();

interface FabricDetail {
  name: string;
  color: string;
  spec: string;
  supplier: string;
}

interface Accessory {
  name: string;
  supplier: string;
}

// 表单数据
const formData = reactive({
  skcId: "",
  spuId: "",
  productionPhase: 0,
  material: "", //成分
  clothInfo: "",
  technologicalRequirements: "",
  fuLiaoInfo: "",
  remark: "",
  fabricSampleWeight: "",
  sampleWeight: "", //样衣克重
  detailImg: "default.png",
  sizeInfo: "",
});

// 尺码表格
const sizeTable = reactive({
  columns: ["尺码", "S", "M", "L"],
  data: [["", "", "", ""]],
});

// 布料详情
const fabricDetails = ref<FabricDetail[]>([]);

// 辅料信息
const accessories = ref<Accessory[]>([]);
// 增加搜索相关状态
const searchSkcid = ref("");
// 在script部分添加以下内容
// 图片预览相关状态
const dialogVisible = ref(false);
const dialogImageUrl = ref("");

// 加载时获取图片列表
const loadImageList = async () => {
  const res = await fetch(`/api/productInfo/images/${formData.skcId}`);
  const data = await res.json();
  console.log("获取图片列表成功:", data);
  console.log("获取图片列表后获取表单成功:", formData);

  fileList.value = data.data.map((url) => ({
    name: url.split("/").pop(),
    url: url,
  }));

  console.log("文件列表：", fileList.value);
};

// 处理图片预览
const handlePictureCardPreview: UploadProps["onPreview"] = (file) => {
  dialogImageUrl.value = file.url || URL.createObjectURL(file.raw!);
  dialogVisible.value = true;

  // 自动释放临时URL
  if (!file.url) {
    setTimeout(() => {
      URL.revokeObjectURL(dialogImageUrl.value);
    }, 500);
  }
};

// 增强上传成功处理
const handleUploadSuccess: UploadProps["onSuccess"] = (
  response,
  uploadFile
) => {
  // 检查重复（防止绕过前端校验）
  const existing = fileList.value.some((f) => f.name === uploadFile.name);
  if (existing) {
    ElMessage.error("检测到重复文件，已拒绝上传");
    return;
  }

  // 更新列表
  fileList.value = [
    ...fileList.value,
    {
      ...uploadFile,
      url: response.data, // 假设返回结构为 { data: "文件路径" }
    },
  ];

  ElMessage.success("上传成功");
};

// 修复删除后列表同步问题
const handleRemove: UploadProps["onRemove"] = async (file) => {
  try {
    const response = await fetch(
      `/api/productInfo/deleteImage?url=${encodeURIComponent(
        file.url!
      )}&skcId=${formData.skcId}`,
      { method: "DELETE" }
    );

    if (!response.ok) {
      const errorData = await response.json();
      throw new Error(errorData.message || "删除失败");
    }

    // 立即更新本地列表（无需等待重新加载）
    fileList.value = fileList.value.filter((f) => f.uid !== file.uid);
    ElMessage.success("删除成功");

    // 可选：刷新完整数据
    await loadData();
  } catch (err) {
    console.error("删除失败:", err);
    ElMessage.error(err.message || "删除失败");
  }
};
// 新增搜索方法
const handleSearch = async () => {
  if (!searchSkcid.value.trim()) {
    ElMessage.warning("请输入SKCID");
    return;
  }
  try {
    await loadData();
    ElMessage.success("数据加载成功");
  } catch (error) {
    ElMessage.error("数据加载失败");
  }
};

// 提取数据加载为独立方法
const loadData = async () => {
  try {
    const response = await fetch(`/api/productInfo/${searchSkcid.value}`);
    if (!response.ok) throw new Error("数据加载失败");
    const data = await response.json();

    console.log("加载的数据:", data);

    // // 处理图片数据
    // fileList.value =
    //   data.detailImg?.map((url) => ({
    //     name: url.split("/").pop() || "image",
    //     url: `/api/productInfo/images/${url}`, // 根据实际路径调整
    //   })) || [];

    // 反序列化复杂字段
    if (data.sizeInfo) {
      const sizeInfo = JSON.parse(data.sizeInfo);
      sizeTable.columns = sizeInfo.columns;
      sizeTable.data = sizeInfo.data;
    }

    fabricDetails.value = data.clothInfo ? JSON.parse(data.clothInfo) : [];
    accessories.value = data.fuLiaoInfo ? JSON.parse(data.fuLiaoInfo) : [];

    Object.assign(formData, data);
  } catch (error) {
    throw error;
  }
};

// 文件上传
const fileList = ref<UploadUserFile[]>([]);
// 在beforeUpload方法中添加校验
const beforeUpload: UploadProps["beforeUpload"] = (file) => {
  // 基础校验
  if (!["image/jpeg", "image/png"].includes(file.type)) {
    ElMessage.error("只能上传JPG/PNG格式图片!");
    return false;
  }

  if (file.size / 1024 / 1024 > 5) {
    ElMessage.error("图片大小不能超过5MB!");
    return false;
  }

  // 文件名查重
  const existingNames = fileList.value.map((f) => f.name.toLowerCase().trim());
  const newName = file.name.toLowerCase().trim();

  if (existingNames.includes(newName)) {
    ElMessage.error(`文件 ${file.name} 已存在!`);
    return false;
  }

  // 数量限制（二次校验）
  if (fileList.value.length >= 20) {
    ElMessage.error("最多上传20张图片");
    return false;
  }

  return true;
};

// 处理超出数量限制
const handleExceed: UploadProps["onExceed"] = (files) => {
  ElMessage.warning(`最多上传20张图片，当前已选择 ${files.length} 张`);
};

// 编辑状态
const isEditing = ref(false);
const toggleEditMode = async () => {
  if (isEditing.value) {
    try {
      await saveData();
      ElMessage.success("保存成功");
    } catch (error) {
      ElMessage.error("保存失败");
    }
  }
  isEditing.value = !isEditing.value;
};

// 尺码表格操作
const addSizeColumn = () => {
  // 生成默认列名：尺寸+当前列数
  const newColName = `尺寸${sizeTable.columns.length}`;
  sizeTable.columns.push(newColName);
  sizeTable.data.forEach((row) => row.push(""));
};

const removeSizeColumn = () => {
  if (sizeTable.columns.length > 1) {
    sizeTable.columns.pop();
    sizeTable.data.forEach((row) => row.pop());
  }
};

const addSizeRow = () => {
  sizeTable.data.push(new Array(sizeTable.columns.length).fill(""));
};

const removeSizeRow = () => {
  if (sizeTable.data.length > 1) {
    sizeTable.data.pop();
  }
};

// 布料操作
const addFabricItem = () => {
  fabricDetails.value.push({
    name: "",
    color: "",
    spec: "",
    supplier: "",
  });
};

const removeFabricItem = (index: number) => {
  fabricDetails.value.splice(index, 1);
};

// 辅料操作
const addAccessoryItem = () => {
  accessories.value.push({
    name: "",
    supplier: "",
  });
};

const removeAccessoryItem = (index: number) => {
  accessories.value.splice(index, 1);
};

// 修改保存方法，合并图片数据
const saveData = async () => {
  try {
    const response = await fetch("/api/productInfo/save", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(formData),
    });

    if (!response.ok) throw new Error("保存失败");

    // 重新加载数据时保留图片信息
    const data = await response.json();
    Object.assign(formData, data);
    await loadImageList(); // 重新加载确保同步

    ElMessage.success("保存成功");
  } catch (error) {
    ElMessage.error("保存失败");
    throw error;
  }
};
// 导出Excel
const exportExcel = () => {
  const workbook = XLSX.utils.book_new();
  const worksheetData = [];

  // ========== 基本信息 ==========
  worksheetData.push(["产品信息明细"], []); // 主标题和空行

  // 基本信息标题
  worksheetData.push(["基本信息"]);
  worksheetData.push(
    ["样衣克重(g)", "样布克重(g)", "成分", "工艺要求"],
    [
      formData.fabricSampleWeight,
      formData.material,
      formData.remark,
      formData.technologicalRequirements,
    ]
  );
  worksheetData.push([], []); // 添加两个空行分隔

  // ========== 尺码信息 ==========
  worksheetData.push(["尺码信息"]);
  // 构建尺码表头（第一列固定为"尺码"）
  const sizeHeader = ["尺码", ...sizeTable.columns.slice(1)];
  worksheetData.push(sizeHeader);
  // 添加尺码数据
  worksheetData.push(...sizeTable.data);
  worksheetData.push([], []);

  // ========== 布料详情 ==========
  worksheetData.push(["布料详情"]);
  const fabricHeader = ["名称", "颜色/色号", "单位/规格", "供应商"];
  worksheetData.push(fabricHeader);
  fabricDetails.value.forEach((item) => {
    worksheetData.push([item.name, item.color, item.spec, item.supplier]);
  });
  worksheetData.push([], []);

  // ========== 辅料信息 ==========
  worksheetData.push(["辅料信息"]);
  const accessoryHeader = ["名称", "供应商"];
  worksheetData.push(accessoryHeader);
  accessories.value.forEach((item) => {
    worksheetData.push([item.name, item.supplier]);
  });
  worksheetData.push([], []);

  // ========== 备注信息 ==========
  worksheetData.push(["备注"]);
  worksheetData.push([formData.remark]);
  worksheetData.push([], []);

  // ========== 图片信息 ==========
  if (fileList.value.length > 0) {
    worksheetData.push(["细节图片"]);
    fileList.value.forEach((file) => {
      worksheetData.push([file.name]); // 显示文件名
      // 如果要显示URL可以改为：[file.url || file.raw?.name || '未命名']
    });
  }

  // 创建工作表
  const worksheet = XLSX.utils.aoa_to_sheet(worksheetData);

  // ========== 样式和格式设置 ==========
  // 合并单元格
  worksheet["!merges"] = [
    // 主标题合并
    { s: { r: 0, c: 0 }, e: { r: 0, c: 3 } },

    // 各区块标题合并
    { s: { r: 2, c: 0 }, e: { r: 2, c: 3 } }, // 基本信息
    { s: { r: 6, c: 0 }, e: { r: 6, c: sizeHeader.length - 1 } }, // 尺码信息
    {
      s: { r: 6 + sizeTable.data.length + 3, c: 0 },
      e: { r: 6 + sizeTable.data.length + 3, c: 3 },
    }, // 布料详情
    // 其他区块合并位置需要根据实际数据行数计算...
  ];

  // 设置列宽（示例）
  worksheet["!cols"] = [
    { wch: 15 }, // 第一列宽度
    { wch: 15 },
    { wch: 20 },
    { wch: 25 }, // 最后一列较宽用于备注
  ];

  // 添加通用样式（通过单元格类型）
  Object.keys(worksheet).forEach((cell) => {
    if (cell.startsWith("A")) {
      // 标题行
      worksheet[cell].t = "s"; // 字符串类型
      worksheet[cell].s = {
        font: { bold: true, sz: 14 },
        alignment: { horizontal: "center" },
      };
    }
    if (cell.match(/^A\d+/)) {
      // 所有区块标题
      worksheet[cell].s = {
        font: { bold: true, color: { rgb: "FFFFFF" } },
        fill: { fgColor: { rgb: "4472C4" } },
        alignment: { horizontal: "center" },
      };
    }
  });

  // 添加备注样式
  const remarkRow = worksheetData.findIndex((row) => row[0] === "备注") + 1;
  if (remarkRow > 0) {
    const cell = XLSX.utils.encode_cell({ r: remarkRow, c: 0 });
    worksheet[cell].s = {
      alignment: { wrapText: true, vertical: "top" },
      font: { sz: 12 },
    };
  }

  // 添加到工作簿
  XLSX.utils.book_append_sheet(workbook, worksheet, "产品明细");

  // 生成文件
  XLSX.writeFile(workbook, "产品信息明细.xlsx");
};

// 修改初始化加载逻辑
onMounted(async () => {
  // 如果有初始SKCID参数可以在这里处理
  // 例如从路由参数获取
  //const route = useRoute();
  if (route.params.skcId) {
    searchSkcid.value = route.params.skcId as string;
    await loadData();
    await loadImageList();
  }
});
</script>

<style scoped>
.container {
  max-width: 1200px;
  margin: 20px auto;
  padding: 20px;
}

.control-bar {
  margin-bottom: 20px;
  text-align: right;

  display: flex;
  justify-content: space-between;
  align-items: center;
}

.section {
  margin-bottom: 20px;
}

.title {
  color: #333;
  font-size: 18px;
  margin-bottom: 15px;
  border-bottom: 2px solid #eee;
  padding-bottom: 10px;
}

.size-controls,
.fabric-controls,
.accessory-controls {
  margin-bottom: 15px;
}

.search-container {
  flex: 1;
  margin-right: 20px;
}

.operation-buttons {
  flex-shrink: 0;
}

/* 确保对话框层级 */
.el-dialog {
  z-index: 9999 !important;
}

/* 图片预览优化 */
.el-dialog__body {
  padding: 10px !important;
  display: flex;
  justify-content: center;
  align-items: center;
  min-height: 300px;
}
</style>
