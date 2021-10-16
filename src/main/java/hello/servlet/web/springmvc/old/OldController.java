package hello.servlet.web.springmvc.old;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// spring Bean 이름설정
@Component("/springmvc/old-controller")
public class OldController implements Controller {

    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        System.out.println("OldController.handleRequest");

        // 컨트롤러는 호출되었지만 뷰를 못찾음 > 뷰 리졸버 필요

        // 스프링 부트에서 InternalResourceViewResolver에서 application.properties의
        // spring.mvc.view.prefix(suffix) 설정정보로 등록
        return new ModelAndView("new-form");
    }
}
