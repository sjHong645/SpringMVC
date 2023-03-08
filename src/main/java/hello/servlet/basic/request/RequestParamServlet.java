package hello.servlet.basic.request;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/*
* 1. 파라미터 전송 기능
* http://localhost:8080/request-param?username=hello&age=20
* */
@WebServlet(name = "requestParamServlet", urlPatterns = "/request-param")
public class RequestParamServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // 모든 요청 파라미터들을 조회하는 방법

        /*
        http://localhost:8080/request-param?username=hello&age=20를 요청했다면

        username=hello
        age=20
        라는 결과가 나온다.

        ==> 형식: 파라미터 이름 = 파라미터 값
        */

        System.out.println("[전체 파라미터 조회] - start");
        request.getParameterNames().asIterator()
                .forEachRemaining(paramName -> System.out.println(paramName + "=" + request.getParameter(paramName)));

        System.out.println("[전체 파라미터 조회] - end");
        System.out.println();

        System.out.println("[단일 파라미터 조회] - start");

        // username 파라미터의 값, age 파라미터의 값을 조회한다.
        String username = request.getParameter("username");
        String age = request.getParameter("age");

        System.out.println("username = " + username);
        System.out.println("age = " + age);

        System.out.println("[단일 파라미터 조회] - end");
        System.out.println();

        System.out.println("이름이 동일한 복수 파라미터 조회");

        // username이라는 이름의 파라미터 값에 여러 값이 저장될 수 있다.
        // http://localhost:8080/request-param?username=hello&age=20&username=hello333

        // 여기서는 username이라는 파라미터에 hello, hello333이 저장된 상황
        String[] usernames = request.getParameterValues("username");
        for(String name : usernames) {
            System.out.println("username = " + name);
        }


    }
}
