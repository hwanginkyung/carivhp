package carivex.homepages.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    @GetMapping({"/", "/index.html"})
    public String index() {
        return "index";
    }

    @GetMapping("/sub1-1.html")
    public String sub11() { return "sub1-1"; }

    @GetMapping("/sub1-2.html")
    public String sub12() { return "sub1-2"; }

    @GetMapping("/sub1-3.html")
    public String sub13() { return "sub1-3"; }

    @GetMapping("/sub2-1.html")
    public String sub21() { return "sub2-1"; }

    @GetMapping("/sub2-2.html")
    public String sub22() { return "sub2-2"; }

    @GetMapping("/sub2-3.html")
    public String sub23() { return "sub2-3"; }

    @GetMapping("/sub5-1.html")
    public String sub51() { return "sub5-1"; }

    @GetMapping("/sub5-2.html")
    public String sub52() { return "sub5-2"; }

    @GetMapping("/sub5-3.html")
    public String sub53() { return "sub5-3"; }
}
