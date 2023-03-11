package hello.servlet.web.frontcontroller.v1;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hello.servlet.web.frontcontroller.v1.controller.MemberFormControllerV1;
import hello.servlet.web.frontcontroller.v1.controller.MemberListControllerV1;
import hello.servlet.web.frontcontroller.v1.controller.MemberSaveControllerV1;

@WebServlet(name = "frontControllerServletV1", urlPatterns = "/front-controller/v1/*")
public class FrontControllerServletV1 extends HttpServlet {

    // 매핑 정보를 저장하는 공간
    private Map<String, ControllerV1> controllerMap = new HashMap<>();

    public FrontControllerServletV1() {

        // url이 매핑왔을 때 어떤 컨트롤러를 실행할 지 저장함
        // key - 매핑 URL, value - 호출될 컨트롤러
        controllerMap.put("/front-controller/v1/members/new-form", new MemberFormControllerV1());
        controllerMap.put("/front-controller/v1/members/save", new MemberSaveControllerV1());
        controllerMap.put("/front-controller/v1/members", new MemberListControllerV1());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("FrontControllerServletV1.Service");

        // ex) /front-controller/v1/members/new-form와 같은 uri값을 반환함
        String requestURI = request.getRequestURI();

        // uri 값과 매핑된 컨트롤러를 조회했음
        // ex) /front-controller/v1/members/new-form 와 매핑된 컨트롤러는 회원등록 컨트롤러
        ControllerV1 controller = controllerMap.get(requestURI);

        if(controller == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        // 조회한 컨트롤러에다가 매개변수 request, response를 전달하면서
        // 회원 등록, 저장, 조회 작업을 실행하도록 한다.
        // 그 이후의 작업은 이전에 봐왔던 작업과 동일하다.
        controller.process(request, response);
    }
}
