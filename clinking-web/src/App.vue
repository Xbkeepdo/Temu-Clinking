<template>
  <div>
    <RouterView> </RouterView>
  </div>
</template>

<script setup>
import { reactive } from "vue";
import { RouterView } from "vue-router";
import { onMounted } from "vue";
import { useUserStore } from "@/store/User";

const userStore = useUserStore();

onMounted(() => {
  userStore.loadUserFromCookies();
});
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  margin: 10px;
}
</style>
