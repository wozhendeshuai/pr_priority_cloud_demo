/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package com.jjyu.utils;

import lombok.Data;
import org.apache.http.HttpStatus;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 返回数据
 *
 */
@Data
public class ResultForFront implements Serializable {
	private int code;
	private String msg;
	private Object data;

	public static ResultForFront succ(Object data) {

		return succ(200, "操作成功", data);
	}

	public static ResultForFront succ(int code, String msg, Object data) {
		ResultForFront r = new ResultForFront();
		r.setCode(code);
		r.setMsg(msg);
		r.setData(data);
		return r;
	}

	public static ResultForFront fail(String msg) {

		return fail(400, msg, null);
	}

	public static ResultForFront fail(int code, String msg, Object data) {
		ResultForFront r = new ResultForFront();
		r.setCode(code);
		r.setMsg(msg);
		r.setData(data);
		return r;
	}

}
