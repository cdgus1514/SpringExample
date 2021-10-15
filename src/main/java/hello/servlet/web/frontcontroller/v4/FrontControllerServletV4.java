package hello.servlet.web.frontcontroller.v4;

import hello.servlet.web.frontcontroller.MyView;
import hello.servlet.web.frontcontroller.v4.controller.MemberFormControllerV4;
import hello.servlet.web.frontcontroller.v4.controller.MemberListControllerV4;
import hello.servlet.web.frontcontroller.v4.controller.MemberSaveControllerV4;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "frontControllerServletV4", urlPatterns = "/front-controller/v4/*")
public class FrontControllerServletV4 extends HttpServlet {

    private Map<String, ControllerV4> controllermap = new HashMap<>();

    public FrontControllerServletV4() {
        controllermap.put("/front-controller/v4/members/new-form", new MemberFormControllerV4());
        controllermap.put("/front-controller/v4/members/save", new MemberSaveControllerV4());
        controllermap.put("/front-controller/v4/members", new MemberListControllerV4());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("FrontControllerServletV4.service");

        /**
         * 프론트 컨트롤러 v4
         *
         * 1. HTTP 요청
         * 2. FrontController에서 URL매핑정보(컨트롤러 조회) 확인 후 해당 컨트롤러 호출
         * 3. 해당 컨트롤러에서 뷰이름 반환(모델뷰 사용X)
         * 4. viewResolver 호출 후 MyView 반환
         * 5. FrontController에서 render(model) 호출
         * 6. HTML 응답
         */

        // 1) 사용자가 요청한 url을 확인해서 해당컨트롤러 호출
        String requestURI = request.getRequestURI();
        
            // 다형성을 사용해서 인터페이스로 받을 수 있음
        ControllerV4 controller = controllermap.get(requestURI);
        if(controller == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        // 기존
//        controller.process(request, response);

        // v2
//        MyView view = controller.process(request, response);
//        view.render(request, response);

        // v3
//        Map<String, String> paramMap = createParamMap(request);
//        ModelView mv = controller.process(paramMap);

        // 변경
        Map<String, String> paramMap = createParamMap(request);
        Map<String, Object> model = new HashMap<>();
        String viewname = controller.process(paramMap, model);

        // 논리이름을 얻어서 물리이름생성
        MyView view = viewResolver(viewname);

            // 모델을 같이 넘겨줘야 함
        //view.render(mv.getModel(), request, response);
        view.render(model, request, response);
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
