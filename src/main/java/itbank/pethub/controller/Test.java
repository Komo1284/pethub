package itbank.pethub.controller;

import itbank.pethub.model.TestDAO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@AllArgsConstructor
public class Test {

    private final TestDAO dao;

    @GetMapping("test")
    public ModelAndView test() {
        ModelAndView mav= new ModelAndView();


        mav.addObject("list",dao.selectAll());
        mav.setViewName("test");

        return mav;
    }
}
