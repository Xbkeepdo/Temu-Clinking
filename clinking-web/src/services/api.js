import axios from "axios";

const API_BASE_URL = "/api/product";

export const getAllProducts = async () => {
  const response = await axios.get(`${API_BASE_URL}/all`);
  return response.data;
};

export const searchProducts = async (skcId) => {
  const response = await axios.get(`${API_BASE_URL}/search`, { params: { skcId } });
  return response.data;
};
