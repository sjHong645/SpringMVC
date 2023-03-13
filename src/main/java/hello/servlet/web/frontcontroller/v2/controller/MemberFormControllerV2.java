package hello.servlet.web.frontcontroller.v2.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hello.servlet.web.frontcontroller.MyView;
import hello.servlet.web.frontcontroller.v2.ControllerV2;

public class MemberFormControllerV2 implements ControllerV2 {

    @Override
    public MyView process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // 새로운 회원 정보를 입력할 수 있도록 view로 던진다.
        /*String viewPath = "/WEB-INF/views/new-form.jsp";
        RequestDispatcher dispatcher = request.getRequestDispatcher(viewPath);
        dispatcher.forward(request, response);*/

        // 위와 같은 코드를 반복할 필요 없이 경로만 넣어도 되는 간단한 코드로 수정됨

        return new MyView("/WEB-INF/views/new-form.jsp");

    }
}
