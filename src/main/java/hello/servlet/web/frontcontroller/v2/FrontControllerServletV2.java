package hello.servlet.web.frontcontroller.v2;

import hello.servlet.web.frontcontroller.MyView;
import hello.servlet.web.frontcontroller.v1.ControllerV1;
import hello.servlet.web.frontcontroller.v2.controller.MemberFormControllerV2;
import hello.servlet.web.frontcontroller.v2.controller.MemberListControllerV2;
import hello.servlet.web.frontcontroller.v2.controller.MemberSaveControllerV2;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "frontControllerServletV2", urlPatterns = "/front-controller/v2/*")
public class FrontControllerServletV2 extends HttpServlet {

    private Map<String, ControllerV2> controllerV1Map = new HashMap<>();

    public FrontControllerServletV2() {
        controllerV1Map.put("/front-controller/v2/members/new-form", new MemberFormControllerV2());
        controllerV1Map.put("/front-controller/v2/members/save", new MemberSaveControllerV2());
        controllerV1Map.put("/front-controller/v2/members", new MemberListControllerV2());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("FrontControllerServletV2.service");

        /**
         * 프론트 컨트롤러 v2
         *
         * 1. HTTP 요청
         * 2. FrontController에서 URL매핑정보(컨트롤러 조회) 확인 후 해당 컨트롤러 호출
         * 3. 해당 컨트롤러에서 view 객체 생성해서 반환
         * 4. 반화받은 객체를 FrontController에서 render() 호출
         * 5. view 객체에서 JSP forward
         * 6. HTML 응답
         */

        // 1) 사용자가 요청한 url을 확인해서 해당컨트롤러 호출
        String requestURI = request.getRequestURI();
        
            // 다형성을 사용해서 인터페이스로 받을 수 있음
        ControllerV2 controller = controllerV1Map.get(requestURI);
        if(controller == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        // 기존
//        controller.process(request, response);

        // 변경
        MyView view = controller.process(request, response);
        view.render(request, response);
    }
}
