package hello.servlet.web.springmvc.v3;

import hello.servlet.domain.member.Member;
import hello.servlet.domain.member.MemberRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/springmvc/v3/members")
public class SpringMemberControllerV3 {

    private MemberRepository memberRepository = MemberRepository.getInstance();


    //@RequestMapping(value = "/new-form", method = RequestMethod.GET)
    @GetMapping("/new-form")
    //public ModelAndView newForm() {
    public String newForm() {
        //return new ModelAndView("new-form");
        return "new-form";
    }

    //@RequestMapping(value = "/save", method = RequestMethod.POST)
    @PostMapping("/save")
    //public ModelAndView save(HttpServletRequest request, HttpServletResponse response) {
    public String save(@RequestParam("username") String username,
                       @RequestParam("age") int age,
                       Model model) {

        Member member = new Member(username, age);
        memberRepository.save(member);

        //ModelAndView mv = new ModelAndView("save-result");
        //mv.addObject("member", member);
        model.addAttribute("member", member);

        return "save-result";
    }

    //@RequestMapping(method = RequestMethod.GET)
    @GetMapping
    //public ModelAndView members(Model model) {
    public String members(Model model) {
        List<Member> members = memberRepository.findAll();

        //ModelAndView mv = new ModelAndView("members");
        //mv.addObject("members", members);
        model.addAttribute("members", members);

        return "members";
    }

}
