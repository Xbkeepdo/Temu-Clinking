import { defineStore } from "pinia";
import VueCookies from "vue-cookies";
import { ref } from "vue";

interface User {
  account: string;
  nickName: string;
  shopId: string;
  status: string;
}

export const useUserStore = defineStore("user", {
  state: () => ({
    currentUser: {} as User,
    islogin: false,
  }),
  actions: {
    setCurrentUser(user) {
      this.currentUser = user;
      VueCookies.set("user", user, "7d");
      this.islogin = true;
    },

    // 从 cookies 恢复用户信息
    loadUserFromCookies() {
      const savedUser = VueCookies.get("user");
      if (savedUser) {
        console.log("从 cookies 恢复用户信息:", savedUser);
        this.currentUser = savedUser; // 将 JSON 字符串转为对象
        this.islogin = true;
      }
    },

    // loginUser(user) {
    //   this.currentUser = user;
    //   VueCookies.VueCookies.set("user", JSON.stringify(user), "7d");
    // },

    logoutUser() {
      this.currentUser = null;
      this.islogin = false;
      VueCookies.remove("user");
      VueCookies.remove("token");
    },
  },

  getters: {
    // 计算属性来判断是否登录
    isLoggedIn(state) {
      return state.islogin;
    },
  },
});

// export const useUserStore = defineStore("user", () =>{

// }

// );
