package hello.servlet.web.frontcontroller.v5;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
import hello.servlet.web.frontcontroller.v5.adater.ControllerV3HandlerAdapter;

@WebServlet(name = "frontControllerServletV5", urlPatterns = "/front-controller/v5/*")
public class FrontControllerServletV5 extends HttpServlet {

    // value의 타입을 유연하게 받도록 하기 위해 Object 타입으로 설정
    private final Map<String, Object> handlerMappingMap = new HashMap<>();

    private final List<MyHandlerAdapter> handlerAdapters = new ArrayList<>();


    public FrontControllerServletV5() {
        // url이 매핑왔을 때 어떤 컨트롤러를 실행할 지 저장함
        // key - 매핑 URL, value - 호출될 컨트롤러
        initHandlerMappingMap();
        initHandlerAdapters();


    }

    private void initHandlerAdapters() {
        handlerAdapters.add(new ControllerV3HandlerAdapter());
    }

    private void initHandlerMappingMap() {
        handlerMappingMap.put("/front-controller/v5/v3/members/new-form", new MemberFormControllerV3());
        handlerMappingMap.put("/front-controller/v5/v3/members/save", new MemberSaveControllerV3());
        handlerMappingMap.put("/front-controller/v5/v3/members", new MemberListControllerV3());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // 1. 핸들러를 조회한다.
        Object handler = getHandler(request);

        if(handler == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        // 2. 핸들러를 처리할 수 있는 핸들러 어댑터를 조회한다.
        MyHandlerAdapter adapter = getHanlderAdapter(handler);

        // 3. handle() & 4 & 5 과정을 통해 modelView를 반환
        // 그 이후의 과정은 V3, V4와 동일함
        ModelView mv = adapter.handle(request, response, handler);

        // 반환받는 viewName을 viewResolver를 통해 view파일이 있는 주소값을 가진 MyView를 반환한다.
        String viewName = mv.getViewName();
        MyView view = viewResolver(viewName);

        // MyView 인스턴스를 가지고 렌더링을 진행한다.
        view.render(mv.getModel(), request, response);
    }

    private MyHandlerAdapter getHanlderAdapter(Object handler) {


        for (MyHandlerAdapter adpater : handlerAdapters) {
            if(adpater.supports(handler)) {
                return adpater;
            }
        }

        throw new IllegalArgumentException("handler adapter를 찾을 수 없음. handler = " + handler);
    }

    private Object getHandler(HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        return handlerMappingMap.get(requestURI);
    }

    private MyView viewResolver(String viewName) {
        return new MyView("/WEB-INF/views/" + viewName + ".jsp");
    }
}
