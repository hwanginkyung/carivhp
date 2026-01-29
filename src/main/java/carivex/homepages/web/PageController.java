package carivex.homepages.web;

import carivex.homepages.domain.inquiry.service.InquiryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class PageController {

    private final InquiryService inquiryService;

    @GetMapping({"/", "/index.html"})
    public String index() {
        return "index";
    }

    @GetMapping({"/en", "/index_en.html"})
    public String indexEn() {
        return "index_en";
    }

    @GetMapping("/sub1-1.html")
    public String sub11() { return "sub1-1"; }

    @GetMapping("/en/sub1-1.html")
    public String sub11En() { return "sub1-1_en"; }

    @GetMapping("/sub1-2.html")
    public String sub12() { return "sub1-2"; }

    @GetMapping("/en/sub1-2.html")
    public String sub12En() { return "sub1-2_en"; }

    @GetMapping("/sub1-3.html")
    public String sub13() { return "sub1-3"; }

    @GetMapping("/en/sub1-3.html")
    public String sub13En() { return "sub1-3_en"; }

    @GetMapping("/sub2-1.html")
    public String sub21() { return "sub2-1"; }

    @GetMapping("/en/sub2-1.html")
    public String sub21En() { return "sub2-1_en"; }

    @GetMapping("/sub2-2.html")
    public String sub22() { return "sub2-2"; }

    @GetMapping("/en/sub2-2.html")
    public String sub22En() { return "sub2-2_en"; }

    @GetMapping("/sub2-3.html")
    public String sub23() { return "sub2-3"; }

    @GetMapping("/en/sub2-3.html")
    public String sub23En() { return "sub2-3_en"; }

    @GetMapping("/sub5-1.html")
    public String sub51(Model model) {
        model.addAttribute("inquiries", inquiryService.listAll());
        return "sub5-1";
    }

    @GetMapping("/en/sub5-1.html")
    public String sub51En(Model model) {
        model.addAttribute("inquiries", inquiryService.listAll());
        return "sub5-1_en";
    }

    @GetMapping("/sub5-2.html")
    public String sub52() { return "sub5-2"; }

    @GetMapping("/en/sub5-2.html")
    public String sub52En() { return "sub5-2_en"; }

    @GetMapping("/sub5-3.html")
    public String sub53() { return "sub5-3"; }

    @GetMapping("/en/sub5-3.html")
    public String sub53En() { return "sub5-3_en"; }

    @GetMapping("/inquiry_form.html")
    public String inquiryForm() { return "inquiry_form"; }

    @GetMapping("/en/inquiry_form.html")
    public String inquiryFormEn() { return "inquiry_form"; }

    @GetMapping("/inquiry_view.html")
    public String inquiryView(@RequestParam("id") Long id, Model model) {
        model.addAttribute("inquiry", inquiryService.get(id));
        return "inquiry_view";
    }

    @GetMapping("/en/inquiry_view.html")
    public String inquiryViewEn(@RequestParam("id") Long id, Model model) {
        model.addAttribute("inquiry", inquiryService.get(id));
        return "inquiry_view_en";
    }
}
