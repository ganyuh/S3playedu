package com.cssl.playedu.utils;

import com.alibaba.fastjson2.JSON;
import com.cssl.playedu.vo.Result;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @Author : Tang
 * @CreateDate 2023/8/29 19:28
 */
public class ResponseUtils {

    public static void printWriter(HttpServletResponse response, Result<Object> data) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        PrintWriter out = response.getWriter();
        out.println(JSON.toJSONString(data));
        out.flush();
        out.close();
    }

}
