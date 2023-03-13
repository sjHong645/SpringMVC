package hello.servlet.web.frontcontroller.v4;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hello.servlet.web.frontcontroller.ModelView;
import hello.servlet.web.frontcontroller.MyView;
import hello.servlet.web.frontcontroller.v3.ControllerV3;
import hello.servlet.web.frontcontroller.v3.controller.MemberFormControllerV3;
import hello.servlet.web.frontcontroller.v3.controller.MemberListControllerV3;
import hello.servlet.web.frontcontroller.v3.controller.MemberSaveControllerV3;
import hello.servlet.web.frontcontroller.v4.controller.MemberFormControllerV4;
import hello.servlet.web.frontcontroller.v4.controller.MemberListControllerV4;
import hello.servlet.web.frontcontroller.v4.controller.MemberSaveControllerV4;

@WebServlet(name = "frontControllerServletV4", urlPatterns = "/front-controller/v4/*")
public class FrontControllerServletV4 extends HttpServlet {

    // 매핑 정보를 저장하는 공간
    private Map<String, ControllerV4> controllerMap = new HashMap<>();

    public FrontControllerServletV4() {

        // url이 매핑왔을 때 어떤 컨트롤러를 실행할 지 저장함
        // key - 매핑 URL, value - 호출될 컨트롤러
        controllerMap.put("/front-controller/v4/members/new-form", new MemberFormControllerV4());
        controllerMap.put("/front-controller/v4/members/save", new MemberSaveControllerV4());
        controllerMap.put("/front-controller/v4/members", new MemberListControllerV4());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("FrontControllerServletV4.Service");

        // ex) /front-controller/v2/members/new-form와 같은 uri값을 반환함
        String requestURI = request.getRequestURI();

        // uri 값과 매핑된 컨트롤러를 조회했음
        // ex) /front-controller/v3/members/new-form 와 매핑된 컨트롤러는
        // 회원등록 컨트롤러 new MemberFormControllerV3()가 된다.
        ControllerV4 controller = controllerMap.get(requestURI);

        if(controller == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        // 요청받은 parameter 값들을 paramMap에 저장할 거다.
        // ex) username=hong&age=20이 왔다면
        // username - hong, age - 20 으로 저장할 거다.
        Map<String, String> paramMap = createParamMap(request);

        // model 역할을 할 map을 생성 & 뷰 이름(viewName)을 반환받는다.
        Map<String, Object> model = new HashMap<>();
        String viewName = controller.process(paramMap, model);

        // 반환받는 viewName을 viewResolver를 통해 view파일이 있는 주소값을 가진 MyView를 반환한다.
        MyView view = viewResolver(viewName);
        view.render(model, request, response);
    }

    private MyView viewResolver(String viewName) {
        return new MyView("/WEB-INF/views/" + viewName + ".jsp");
    }

    private Map<String, String> createParamMap(HttpServletRequest request) {
        Map<String, String> paramMap = new HashMap<>();

        request.getParameterNames().asIterator()
               .forEachRemaining(paramName -> paramMap.put(paramName, request.getParameter(paramName)));
        return paramMap;
    }


}
