import axios from "axios";

const request = axios.create({
  //baseURL: "/api",
  timeout: 10 * 1000,
});
// 请求前的过滤器

request.interceptors.request.use(
  (config) => {
    config.headers["Content-Type"] = "application/json;charset=UTF-8";

    // //const user = localStorage.getItem("user");
    // if (user) {
    //   config.headers["token"] = JSON.parse(user).token;
    // }

    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);
// 请求后的过滤器
request.interceptors.response.use(
  (response) => {
    let res = response.data;

    if (typeof res === "string") {
      res = res ? JSON.parse(res) : res;
    }
    return res;
  },
  (error) => {
    console.log(error);
    return Promise.reject(error);
  }
);

export default request;
