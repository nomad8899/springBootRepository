package com.ohgiraffers.handlermethod;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.context.request.WebRequest;

import java.util.Map;

import static java.awt.SystemColor.menu;

@Controller
@RequestMapping("first/*")
//@SessionAttributes("id")
public class FirstController {

    /* 반환 값을 void로 설정하면 요청 주소가 view의 이름이 된다. */
    @GetMapping("regist")
    public void regist(){}

    @PostMapping("regist")
    public String registMenu(Model model, WebRequest request) {

        String name = request.getParameter("name");
        int price = Integer.parseInt(request.getParameter("price"));
        int categoryCode = Integer.parseInt(request.getParameter("categoryCode"));

        String message = name + "을(를) 신규 메뉴 목록의 " + categoryCode + "번 카테고리에"
                + price + "원으로 등록했습니다!";

        model.addAttribute("message", message);

        return "first/messagePrinter";
    }

    @GetMapping("modify")
    public void modify() {}

    @PostMapping("modify")
    public String modifyMenuPrice(Model model, @RequestParam String modifyName,
                                  @RequestParam(defaultValue = "0") int modifyPrice) {
        String message = modifyName + "메뉴의 가격을" + modifyPrice + "원으로 변경하였습니다.";

        model.addAttribute("message", message);

        return "first/messagePrinter";
    }

    /* 파라미터가 여러개 인 경우 맵으로 한번에 처리할 수 있다.
    * 이 때 맵의 키는 form의 name 속성 값이 된다.*/
    @PostMapping("modifyAll")
    public String modifyMenu(Model model, @RequestParam Map<String, String> parameters) {
        String modifyMenu = parameters.get("modifyName2");
        int modifyPrice = Integer.parseInt(parameters.get("modifyPrice2"));

        String message = "메뉴의 이름을 " + modifyMenu + "으로, 가격을 " + modifyPrice + "원 으로 변경함";

        model.addAttribute("message", message);

        return "first/messagePrinter";
    }

    @GetMapping("search")
    public void search(){}

    /* @ModelAttribute 이용하는 방법
    * DTO같은 모델을 커맨드 객체를 생성하여 매개변수로 전달 해준 뒤 해당 인스턴스를 midel에 담는다.
    * 경우에 따라 폼에서 입력한 값을 다음화면으로 바로 전달해야 하는 경우 유용하게 사용할 수 있다.
    *
    * @ModelAttribute("모델에담을 key값")을 지정할 수 있으며,
    * 지정하지 않으면 타입의 앞글자를 소문자로 한 네이밍 규칙을 따른다.
    * menuDTO로 담긴다.
    * */
    @PostMapping("search")
    public String searchMenu(@ModelAttribute("menu") MenuDTO menuDTO) {

        System.out.println(menu);

        return "first/searchResult";
    }

    @GetMapping("login")
    public void login(){}

    /* HttpSession을 매개변수로 선언하면  핸들러 메소드 호출 시 세션 객체를 넣어서 호출한다. */
    @PostMapping("login1")
    public String sessionTest1(HttpSession session, @RequestParam String id) {

        session.setAttribute("id", id);

        return "first/loginResult";
    }

    @GetMapping("logout1")
    public String logoutTest1(HttpSession session) {

        session.invalidate();

        return "first/loginResult";
    }

    @PostMapping("login2")
    public String sessionTest2(Model model, @RequestParam String id) {

        model.addAttribute("id", id);

        return "first/loginResult";
    }

    /* SessionAttribute로 등록된 값은 session의 상태를 관리*/
    @GetMapping("logout2")
    public String logoutTest2(SessionStatus sessionStatus) {

        sessionStatus.setComplete();

        return "first/loginResult";
    }
}
