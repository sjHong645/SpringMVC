package hello.servlet.web.frontcontroller.v3;

import java.util.Map;

import hello.servlet.web.frontcontroller.ModelView;

public interface ControllerV3 {

    // V2에서와 달리 httpservletrequest, httpservletresponse가 없다.
    // 즉, 서블릿에 종속되지 않도록 했다.
    ModelView process(Map<String, String> paramMap);
}
