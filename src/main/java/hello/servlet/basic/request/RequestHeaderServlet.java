package hello.servlet.basic.request;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "requestHeaderServlet", urlPatterns = "/request-header")
public class RequestHeaderServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        printStartLine(request);
        printHeaders(request);
        printHeaderUtils(request);
        printEtc(request);
    }

    private void printStartLine(HttpServletRequest request) {

        // start line 정보
        System.out.println("--- REQUEST-LINE - start ---");
        System.out.println("request.getMethod() = " + request.getMethod()); //GET
        System.out.println("request.getProtocol() = " + request.getProtocol()); // HTTP/1.1
        System.out.println("request.getScheme() = " + request.getScheme()); //http

        // http://localhost:8080/request-header
        System.out.println("request.getRequestURL() = " + request.getRequestURL());

        // /request-header
        System.out.println("request.getRequestURI() = " + request.getRequestURI());

        // username=hi
        System.out.println("request.getQueryString() = " + request.getQueryString());
        System.out.println("request.isSecure() = " + request.isSecure()); //https 사용 유무
        System.out.println("--- REQUEST-LINE - end ---");
        System.out.println();

        // ex) http://localhost:8080/request-header를 호출했다면
        // 아래와 같은 결과가 나온다

        // --- REQUEST-LINE - start ---
        // request.getMethod() = GET - 요청 메시지의 메서드
        // request.getProtocol() = HTTP/1.1 - 요청 메시지의 프로토콜
        // request.getScheme() = http
        // request.getRequestURL() = http://localhost:8080/request-header - 요청 URL
        // request.getRequestURI() = /request-header - 요청 URI
        // request.getQueryString() = null - 쿼리 파라미터
        // request.isSecure() = false - 보안 여부(https인지 아닌지)
        // --- REQUEST-LINE - end ---
    }

    // HTTP 요청 메시지의 모든 Header 정보를 가져오는 방법
    private void printHeaders(HttpServletRequest request) {
        System.out.println("--- Headers - start ---");
        /*
        // 옛날 방식
         Enumeration<String> headerNames = request.getHeaderNames();
         while (headerNames.hasMoreElements()) {
             String headerName = headerNames.nextElement();
             System.out.println(headerName + ": " + request.getHeader(headerName));
         }
        */

        // request.getHeaderNames()에 있는 값을
        // 각각 headerName으로 가리키고 출력하도록 함
        request.getHeaderNames().asIterator()
               .forEachRemaining(headerName
                       -> System.out.println(headerName + ": " + request.getHeader(headerName)));

        // request.getHeader("원하는 헤더이름") => 이렇게 하면 해당 헤더를 조회 가능

        System.out.println("--- Headers - end ---");
        System.out.println();
    }

    // HTTP 요청 메시지의 모든 Header들을 더욱 편리하게 조회
    private void printHeaderUtils(HttpServletRequest request) {
        System.out.println("--- Header 편의 조회 start ---");

        System.out.println("[Host 헤더의 값을 편리하게 조회]");
        System.out.println("request.getServerName() = " + request.getServerName()); //Host 헤더
        System.out.println("request.getServerPort() = " + request.getServerPort()); //Host 헤더
        System.out.println();


        System.out.println("[Accept-Language 헤더의 값을 편리하게 조회]");

        // 요청 메시지의 header를 보면 아래와 같이 구성되어 있다.
        // Accept-Language: ko-KR,ko;q=0.9,en-US;q=0.8,en;q=0.7

        // request.getLocales()를 통해 ko, ko-KR, en-US, en을 모두 조회한 거다
        request.getLocales().asIterator()
               .forEachRemaining(locale -> System.out.println("locale = " +
                       locale));
        // 가장 우선순위가 높은 Locale 값을 조회함
        System.out.println("request.getLocale() = " + request.getLocale());
        System.out.println();


        System.out.println("[cookie 정보를 편하게 조회]");
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                System.out.println(cookie.getName() + ": " + cookie.getValue());
            }
        }
        System.out.println();

        // Postman을 통해서 body에 hello라는 내용을 넣어놓고 HTTP 요청을 한다면
        // request.getContentType() = text/plain
        // request.getContentLength() = 5
        // request.getCharacterEncoding() = UTF-8
        // 위와 같은 정보가 콘솔에 출력된다.
        System.out.println("[Content 헤더들의 정보를 편하게 조회]");

        System.out.println("request.getContentType() = " + request.getContentType());
        System.out.println("request.getContentLength() = " + request.getContentLength());
        System.out.println("request.getCharacterEncoding() = " + request.getCharacterEncoding());


        System.out.println("--- Header 편의 조회 end ---");

        System.out.println();
    }

    // 기타 정보
    private void printEtc(HttpServletRequest request) {
        System.out.println("--- 기타 조회 start ---");

        // 요청에 대한 정보
        System.out.println("[Remote 정보]");
        System.out.println("request.getRemoteHost() = " + request.getRemoteHost()); //
        System.out.println("request.getRemoteAddr() = " + request.getRemoteAddr()); //
        System.out.println("request.getRemotePort() = " + request.getRemotePort()); //
        System.out.println();

        // 나의 서버 정보
        System.out.println("[Local 정보]");
        System.out.println("request.getLocalName() = " + request.getLocalName()); //
        System.out.println("request.getLocalAddr() = " + request.getLocalAddr()); //
        System.out.println("request.getLocalPort() = " + request.getLocalPort()); //
        System.out.println("--- 기타 조회 end ---");

        System.out.println();
    }
}
