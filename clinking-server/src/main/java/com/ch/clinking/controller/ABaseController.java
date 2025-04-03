package com.ch.clinking.controller;

import com.ch.clinking.util.StringTools;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;


public class ABaseController {

	private static final Logger logger = LoggerFactory.getLogger(ABaseController.class);



	protected static final String STATUS_SUCCESS = "success";

	protected static final String STATUS_ERROR = "error";




	//从服务器读取文件，上传到response返回给用户
	protected void readFile(HttpServletResponse response, String filePath) {
		if (!StringTools.pathIsOk(filePath)) {
			return;
		}
		OutputStream out = null;
		FileInputStream in = null;
		try {
			File file = new File(filePath);
			if (!file.exists()) {
				return;
			}
			in = new FileInputStream(file);
			byte[] byteData = new byte[1024];
			out = response.getOutputStream();
			int len = 0;
			while ((len = in.read(byteData)) != -1) {
				out.write(byteData, 0, len);
			}
			out.flush();
		} catch (Exception e) {
			logger.error("读取文件异常", e);
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					logger.error("IO异常", e);
				}
			}
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					logger.error("IO异常", e);
				}
			}
		}
	}
}
