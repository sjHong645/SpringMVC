package hello.servlet.basic.response;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import hello.servlet.basic.HelloData;

@WebServlet(name = "responseJsonServlet", urlPatterns = "/response-json")
public class ResponseJsonServlet extends HttpServlet {

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // Content-type: application/json
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");

        // 저장하고자 하는 객체
        HelloData helloData = new HelloData();
        helloData.setUsername("hong");
        helloData.setAge(20);

        // 저장하고 싶은 객체를 'json' 형태로 바꿀거다
        // json 형태로 바꾸고 나서
        // 메시지 바디에 입력했다.
        String result = objectMapper.writeValueAsString(helloData);
        response.getWriter().write(result);

    }
}
