package carivex.homepages.web;


import carivex.homepages.domain.inquiry.service.InquiryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class InquiryController {

    private final InquiryService inquiryService;

    @PostMapping("/inquiry")
    public String submit(@Valid @ModelAttribute("form") InquiryForm form,
                         BindingResult bindingResult,
                         @RequestParam(name = "lang", defaultValue = "ko") String lang,
                         RedirectAttributes ra) {
        if (bindingResult.hasErrors()) {
            if ("en".equalsIgnoreCase(lang)) {
                ra.addFlashAttribute("error", "Please review the required fields.");
                return "redirect:/en/sub5-1.html";
            }
            ra.addFlashAttribute("error", "필수 항목을 확인해 주세요.");
            return "redirect:/sub5-1.html";
        }

        inquiryService.create(
                form.getName(),
                form.getContact(),
                form.getEmail(),
                form.getType(),
                form.getContent()
        );

        if ("en".equalsIgnoreCase(lang)) {
            ra.addFlashAttribute("success", "Your inquiry has been submitted. Thank you!");
            return "redirect:/en/sub5-1.html";
        }
        ra.addFlashAttribute("success", "문의가 접수되었습니다. 감사합니다!");
        return "redirect:/sub5-1.html";
    }
}
