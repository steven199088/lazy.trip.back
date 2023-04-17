package common;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(urlPatterns = {"/*"}, asyncSupported = true)
public class CorsFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        // 將ServletResponse轉換為HttpServletResponse
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        // 設置"Access-Control-Allow-Origin"頭，允許任意來源訪問
        httpResponse.setHeader("Access-Control-Allow-Origin", "*");
        // 設置"Access-Control-Allow-Methods"頭，允許GET，POST，PUT和DELETE方法
        httpResponse.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, PATCH, DELETE");
        // 設定 response 的 charset 為 UTF-8
        httpResponse.setCharacterEncoding("UTF-8");

        // 繼續過濾鏈
        chain.doFilter(request, response);
    }
}
