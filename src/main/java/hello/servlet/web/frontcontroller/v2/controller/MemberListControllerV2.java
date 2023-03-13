package hello.servlet.web.frontcontroller.v2.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hello.servlet.domain.member.Member;
import hello.servlet.domain.member.MemberRepository;
import hello.servlet.web.frontcontroller.MyView;
import hello.servlet.web.frontcontroller.v2.ControllerV2;

public class MemberListControllerV2 implements ControllerV2 {

    private MemberRepository memberRepository = MemberRepository.getInstance();

    @Override
    public MyView process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 비즈니스 로직 실행 - 회원 리스트를 저장
        List<Member> members = memberRepository.findAll();

        // Model에 데이터를 보관
        request.setAttribute("members", members);

        // View에 요청을 넘긴다.
        /*String viewPath = "/WEB-INF/views/members.jsp";
        RequestDispatcher dispatcher = request.getRequestDispatcher(viewPath);
        dispatcher.forward(request, response);*/

        // 위와 같은 코드를 반복할 필요 없이 경로만 넣어도 되는 간단한 코드로 수정됨
        return new MyView("/WEB-INF/views/members.jsp");
    }
}
