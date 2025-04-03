<template>
  <div class="status-tabs">
    <span
      v-for="tab in tabs"
      :key="tab.value"
      :class="{ active: tab.value === currentTab }"
      @click="updateTab(tab.value)"
    >
      {{ tab.label }}
    </span>
  </div>
</template>

<script>
import { defineProps, defineEmits } from "vue";

export default {
  props: {
    tabs: { type: Array, required: true },
    modelValue: { type: String, default: "all" }
  },
  emits: ["update:modelValue"],
  setup(props, { emit }) {
    const updateTab = (value) => {
      emit("update:modelValue", value);
    };

    return { updateTab };
  }
};
</script>

<style>
.status-tabs span {
  cursor: pointer;
  margin-right: 10px;
}
.status-tabs .active {
  font-weight: bold;
}
</style>
