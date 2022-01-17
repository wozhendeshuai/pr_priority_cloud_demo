/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package com.jjyu.utils;

import org.apache.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;

/**
 * 返回数据
 *
 */
public class ResultForFront extends HashMap<String, Object> {
	private static final long serialVersionUID = 1L;

	public ResultForFront() {
		put("code", 0);
		put("msg", "success");
	}
	
	public static ResultForFront error() {
		return error(HttpStatus.SC_INTERNAL_SERVER_ERROR, "未知异常，请联系管理员");
	}
	
	public static ResultForFront error(String msg) {
		return error(HttpStatus.SC_INTERNAL_SERVER_ERROR, msg);
	}
	
	public static ResultForFront error(int code, String msg) {
		ResultForFront r = new ResultForFront();
		r.put("code", code);
		r.put("msg", msg);
		return r;
	}

	public static ResultForFront ok(String msg) {
		ResultForFront r = new ResultForFront();
		r.put("msg", msg);
		return r;
	}
	
	public static ResultForFront ok(Map<String, Object> map) {
		ResultForFront r = new ResultForFront();
		r.putAll(map);
		return r;
	}
	
	public static ResultForFront ok() {
		return new ResultForFront();
	}

	public ResultForFront put(String key, Object value) {
		super.put(key, value);
		return this;
	}
}
