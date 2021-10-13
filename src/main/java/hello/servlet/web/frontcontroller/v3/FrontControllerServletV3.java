package hello.servlet.web.frontcontroller.v3;

import hello.servlet.web.frontcontroller.ModelView;
import hello.servlet.web.frontcontroller.MyView;
import hello.servlet.web.frontcontroller.v3.controller.MemberFormControllerV3;
import hello.servlet.web.frontcontroller.v3.controller.MemberListControllerV3;
import hello.servlet.web.frontcontroller.v3.controller.MemberSaveControllerV3;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "frontControllerServletV3", urlPatterns = "/front-controller/v3/*")
public class FrontControllerServletV3 extends HttpServlet {

    private Map<String, ControllerV3> controllerV3Map = new HashMap<>();

    public FrontControllerServletV3() {
        controllerV3Map.put("/front-controller/v3/members/new-form", new MemberFormControllerV3());
        controllerV3Map.put("/front-controller/v3/members/save", new MemberSaveControllerV3());
        controllerV3Map.put("/front-controller/v3/members", new MemberListControllerV3());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("FrontControllerServletV3.service");

        /**
         * 프론트 컨트롤러 v3
         *
         * 1. HTTP 요청
         * 2. FrontController에서 URL매핑정보(컨트롤러 조회) 확인 후 해당 컨트롤러 호출
         * 3. 해당 컨트롤러에서 ModelView 반환
         * 4. viewResolver 호출 후 MyView 반환
         * 5. FrontController에서 render(model) 호출
         * 6. HTML 응답
         */

        // 1) 사용자가 요청한 url을 확인해서 해당컨트롤러 호출
        String requestURI = request.getRequestURI();
        
            // 다형성을 사용해서 인터페이스로 받을 수 있음
        ControllerV3 controller = controllerV3Map.get(requestURI);
        if(controller == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        // 기존
//        controller.process(request, response);

        // v2
//        MyView view = controller.process(request, response);
//        view.render(request, response);

        // 변경 모든 파라미터 이름을 가져와서 paramMap에 넣음
        Map<String, String> paramMap = createParamMap(request);
        ModelView mv = controller.process(paramMap);

        // 논리이름을 얻어서 물리이름생성
        String viewname = mv.getViewname();
        MyView view = viewResolver(viewname);

            // 모델을 같이 넘겨줘야 함
        view.render(mv.getModel(), request, response);

    }

    private MyView viewResolver(String viewname) {
        return new MyView("/WEB-INF/views/" + viewname + ".jsp");
    }

    private Map<String, String> createParamMap(HttpServletRequest request) {
        Map<String, String> paramMap = new HashMap<>();
        request.getParameterNames().asIterator()
                .forEachRemaining(paramName -> paramMap.put(paramName, request.getParameter(paramName)));
        return paramMap;
    }
}
