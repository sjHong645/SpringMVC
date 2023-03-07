package hello.servlet.basic;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "helloServlet", urlPatterns = "/hello")
public class HelloServlet extends HttpServlet { // 서블릿은 HttpServlet을 상속 받음

    // /hello가 호출되면
    // service 메소드가 호출되도록 했음
    // 그걸 확인하기 위해서 System.out.println("HelloServlet.service"); 넣었음
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // localhost:8080/hello를 호출할 때 HelloServlet.service가 콘솔에 출력됨
        System.out.println("HelloServlet.service");

        // localhost:8080/hello를 호출할 때
        // 요청, 응답 객체가 만들어지는데 어떤 객체인지 확인해보자.
        
        // request = org.apache.catalina.connector.RequestFacade@1cc32688
        // response = org.apache.catalina.connector.ResponseFacade@81e7af0
        // 와 같이 나옴
        System.out.println("request = " + request);
        System.out.println("response = " + response);

        // ex) localhost:8080/hello?username=hong 이라고 입력하면
        // 쿼리 파라미터로 받은 username의 값을 콘솔에 출력하도록 했다.
        String username = request.getParameter("username");
        System.out.println("username = " + username);

        // 응답메시지의 Content Type, CharacterEncoding 방식을 정했다.
        // 실제 응답 메시지를 보면...
        // Content-Type: text/plain;charset=utf-8가 나온다는 걸 알 수 있다.

        // 그리고
        // ex) localhost:8080/hello?username=hong 이라고 입력하면
        // 브라우저 화면에 hello hong 이라고 나타난다.
        response.setContentType("text/plain");
        response.setCharacterEncoding("utf-8");
        response.getWriter().write("hello " + username);

    }
}

