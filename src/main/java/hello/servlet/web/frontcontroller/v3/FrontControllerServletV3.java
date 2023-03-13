package hello.servlet.web.frontcontroller.v3;

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
import hello.servlet.web.frontcontroller.v3.controller.MemberFormControllerV3;
import hello.servlet.web.frontcontroller.v3.controller.MemberListControllerV3;
import hello.servlet.web.frontcontroller.v3.controller.MemberSaveControllerV3;

@WebServlet(name = "frontControllerServletV3", urlPatterns = "/front-controller/v3/*")
public class FrontControllerServletV3 extends HttpServlet {

    // 매핑 정보를 저장하는 공간
    private Map<String, ControllerV3> controllerMap = new HashMap<>();

    public FrontControllerServletV3() {

        // url이 매핑왔을 때 어떤 컨트롤러를 실행할 지 저장함
        // key - 매핑 URL, value - 호출될 컨트롤러
        controllerMap.put("/front-controller/v3/members/new-form", new MemberFormControllerV3());
        controllerMap.put("/front-controller/v3/members/save", new MemberSaveControllerV3());
        controllerMap.put("/front-controller/v3/members", new MemberListControllerV3());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("FrontControllerServletV3.Service");

        // ex) /front-controller/v2/members/new-form와 같은 uri값을 반환함
        String requestURI = request.getRequestURI();

        // uri 값과 매핑된 컨트롤러를 조회했음
        // ex) /front-controller/v3/members/new-form 와 매핑된 컨트롤러는
        // 회원등록 컨트롤러 new MemberFormControllerV3()가 된다.
        ControllerV3 controller = controllerMap.get(requestURI);

        if(controller == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        // 요청받은 parameter 값들을 paramMap에 저장할 거다.
        // ex) username=hong&age=20이 왔다면
        // username - hong, age - 20 으로 저장할 거다.
        Map<String, String> paramMap = createParamMap(request);

        // paramMap을 넘겨줘서 ModelView를 반환받는다.
        ModelView mv = controller.process(paramMap);
        // 여기까지 마치면 view의 논리 이름만 얻게 된다.
        // 이제 그 논리 이름을 가지고 view를 출력받을 수 있도록 해야 한다.

        // 반환받는 viewName을 viewResolver를 통해 view파일이 있는 주소값을 가진 MyView를 반환한다.
        String viewName = mv.getViewName();
        MyView view = viewResolver(viewName);

        // MyView 인스턴스를 가지고 렌더링을 진행한다.
        view.render(mv.getModel(), request, response);
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
