package carivex.homepages.web;


import carivex.homepages.domain.notice.Notice;
import carivex.homepages.domain.notice.NoticeCategory;
import carivex.homepages.domain.notice.service.NoticeService;
import carivex.homepages.domain.resource.ResourceCategory;
import carivex.homepages.domain.resource.ResourcePost;
import carivex.homepages.domain.resource.service.ResourceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class BoardController {

    private final NoticeService noticeService;
    private final ResourceService resourceService;

    // ===== Notices (public)
    @GetMapping("/sub4.html")
    public String noticeAll(Model model) {
        model.addAttribute("notices", noticeService.listAll());
        model.addAttribute("activeTab", "all");
        return "sub4";
    }

    @GetMapping("/sub4-1.html")
    public String noticePress(Model model) {
        model.addAttribute("notices", noticeService.listByCategory(NoticeCategory.PRESS));
        model.addAttribute("activeTab", "press");
        return "sub4-1";
    }

    @GetMapping("/sub4-2.html")
    public String noticeUnion(Model model) {
        model.addAttribute("notices", noticeService.listByCategory(NoticeCategory.UNION));
        model.addAttribute("activeTab", "union");
        return "sub4-2";
    }

    @GetMapping("/notice_view.html")
    public String noticeView(@RequestParam("id") Long id, Model model) {
        Notice n = noticeService.get(id);
        model.addAttribute("notice", n);
        return "notice_view";
    }

    // ===== Resources (public)
    @GetMapping("/sub3.html")
    public String resourceAll(Model model) {
        model.addAttribute("resources", resourceService.listAll());
        model.addAttribute("activeTab", "all");
        return "sub3";
    }

    @GetMapping("/sub3-1.html")
    public String resourceStats(Model model) {
        model.addAttribute("resources", resourceService.listByCategory(ResourceCategory.EXPORT_STATS));
        model.addAttribute("activeTab", "export_stats");
        return "sub3-1";
    }

    @GetMapping("/sub3-2.html")
    public String resourceCustoms(Model model) {
        model.addAttribute("resources", resourceService.listByCategory(ResourceCategory.CUSTOMS_STANDARD));
        model.addAttribute("activeTab", "customs");
        return "sub3-2";
    }

    @GetMapping("/sub3-3.html")
    public String resourceLaw(Model model) {
        model.addAttribute("resources", resourceService.listByCategory(ResourceCategory.LAW_OFFICIAL));
        model.addAttribute("activeTab", "law");
        return "sub3-3";
    }

    @GetMapping("/sub3-4.html")
    public String resourceForms(Model model) {
        model.addAttribute("resources", resourceService.listByCategory(ResourceCategory.FORMS));
        model.addAttribute("activeTab", "forms");
        return "sub3-4";
    }

    @GetMapping("/view_resource_public.html")
    public String resourceView(@RequestParam("id") Long id, Model model) {
        ResourcePost p = resourceService.get(id);
        model.addAttribute("resource", p);
        return "view_resource_public";
    }
}
