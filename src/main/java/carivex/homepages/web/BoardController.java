package carivex.homepages.web;


import carivex.homepages.domain.notice.Notice;
import carivex.homepages.domain.notice.NoticeCategory;
import carivex.homepages.domain.notice.service.NoticeService;
import carivex.homepages.domain.resource.ResourceCategory;
import carivex.homepages.domain.resource.ResourcePost;
import carivex.homepages.domain.resource.service.ResourceService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class BoardController {

    private static final int PAGE_SIZE = 10;
    private static final int PAGE_BUTTONS = 5;

    private final NoticeService noticeService;
    private final ResourceService resourceService;

    // ===== Notices (public)
    @GetMapping("/sub4.html")
    public String noticeAll(@RequestParam(name = "page", defaultValue = "0") int page, Model model) {
        Page<Notice> notices = noticeService.listAll(pageable(page));
        model.addAttribute("notices", notices.getContent());
        addPagination(model, notices);
        model.addAttribute("activeTab", "all");
        return "sub4";
    }

    @GetMapping("/en/sub4.html")
    public String noticeAllEn(@RequestParam(name = "page", defaultValue = "0") int page, Model model) {
        Page<Notice> notices = noticeService.listAll(pageable(page));
        model.addAttribute("notices", notices.getContent());
        addPagination(model, notices);
        model.addAttribute("activeTab", "all");
        return "sub4_en";
    }

    @GetMapping("/sub4-1.html")
    public String noticePress(@RequestParam(name = "page", defaultValue = "0") int page, Model model) {
        Page<Notice> notices = noticeService.listByCategory(NoticeCategory.PRESS, pageable(page));
        model.addAttribute("notices", notices.getContent());
        addPagination(model, notices);
        model.addAttribute("activeTab", "press");
        return "sub4-1";
    }

    @GetMapping("/en/sub4-1.html")
    public String noticePressEn(@RequestParam(name = "page", defaultValue = "0") int page, Model model) {
        Page<Notice> notices = noticeService.listByCategory(NoticeCategory.PRESS, pageable(page));
        model.addAttribute("notices", notices.getContent());
        addPagination(model, notices);
        model.addAttribute("activeTab", "press");
        return "sub4-1_en";
    }

    @GetMapping("/sub4-2.html")
    public String noticeUnion(@RequestParam(name = "page", defaultValue = "0") int page, Model model) {
        Page<Notice> notices = noticeService.listByCategory(NoticeCategory.UNION, pageable(page));
        model.addAttribute("notices", notices.getContent());
        addPagination(model, notices);
        model.addAttribute("activeTab", "union");
        return "sub4-2";
    }

    @GetMapping("/en/sub4-2.html")
    public String noticeUnionEn(@RequestParam(name = "page", defaultValue = "0") int page, Model model) {
        Page<Notice> notices = noticeService.listByCategory(NoticeCategory.UNION, pageable(page));
        model.addAttribute("notices", notices.getContent());
        addPagination(model, notices);
        model.addAttribute("activeTab", "union");
        return "sub4-2_en";
    }

    @GetMapping("/notice_view.html")
    public String noticeView(@RequestParam("id") Long id, Model model) {
        Notice n = noticeService.get(id);
        model.addAttribute("notice", n);
        return "notice_view";
    }

    @GetMapping("/en/notice_view.html")
    public String noticeViewEn(@RequestParam("id") Long id, Model model) {
        Notice n = noticeService.get(id);
        model.addAttribute("notice", n);
        return "notice_view_en";
    }

    // ===== Resources (public)
    @GetMapping("/sub3.html")
    public String resourceAll(@RequestParam(name = "page", defaultValue = "0") int page, Model model) {
        Page<ResourcePost> resources = resourceService.listAll(resourcePageable(page));
        model.addAttribute("resources", resources.getContent());
        addPagination(model, resources);
        model.addAttribute("activeTab", "all");
        return "sub3";
    }

    @GetMapping("/en/sub3.html")
    public String resourceAllEn(@RequestParam(name = "page", defaultValue = "0") int page, Model model) {
        Page<ResourcePost> resources = resourceService.listAll(resourcePageable(page));
        model.addAttribute("resources", resources.getContent());
        addPagination(model, resources);
        model.addAttribute("activeTab", "all");
        return "sub3_en";
    }

    @GetMapping("/sub3-1.html")
    public String resourceStats(@RequestParam(name = "page", defaultValue = "0") int page, Model model) {
        Page<ResourcePost> resources = resourceService.listByCategory(ResourceCategory.EXPORT_STATS, resourcePageable(page));
        model.addAttribute("resources", resources.getContent());
        addPagination(model, resources);
        model.addAttribute("activeTab", "export_stats");
        return "sub3-1";
    }

    @GetMapping("/en/sub3-1.html")
    public String resourceStatsEn(@RequestParam(name = "page", defaultValue = "0") int page, Model model) {
        Page<ResourcePost> resources = resourceService.listByCategory(ResourceCategory.EXPORT_STATS, resourcePageable(page));
        model.addAttribute("resources", resources.getContent());
        addPagination(model, resources);
        model.addAttribute("activeTab", "export_stats");
        return "sub3-1_en";
    }

    @GetMapping("/sub3-2.html")
    public String resourceCustoms(@RequestParam(name = "page", defaultValue = "0") int page, Model model) {
        Page<ResourcePost> resources = resourceService.listByCategory(ResourceCategory.CUSTOMS_STANDARD, resourcePageable(page));
        model.addAttribute("resources", resources.getContent());
        addPagination(model, resources);
        model.addAttribute("activeTab", "customs");
        return "sub3-2";
    }

    @GetMapping("/en/sub3-2.html")
    public String resourceCustomsEn(@RequestParam(name = "page", defaultValue = "0") int page, Model model) {
        Page<ResourcePost> resources = resourceService.listByCategory(ResourceCategory.CUSTOMS_STANDARD, resourcePageable(page));
        model.addAttribute("resources", resources.getContent());
        addPagination(model, resources);
        model.addAttribute("activeTab", "customs");
        return "sub3-2_en";
    }

    @GetMapping("/sub3-3.html")
    public String resourceLaw(@RequestParam(name = "page", defaultValue = "0") int page, Model model) {
        Page<ResourcePost> resources = resourceService.listByCategory(ResourceCategory.LAW_OFFICIAL, resourcePageable(page));
        model.addAttribute("resources", resources.getContent());
        addPagination(model, resources);
        model.addAttribute("activeTab", "law");
        return "sub3-3";
    }

    @GetMapping("/en/sub3-3.html")
    public String resourceLawEn(@RequestParam(name = "page", defaultValue = "0") int page, Model model) {
        Page<ResourcePost> resources = resourceService.listByCategory(ResourceCategory.LAW_OFFICIAL, resourcePageable(page));
        model.addAttribute("resources", resources.getContent());
        addPagination(model, resources);
        model.addAttribute("activeTab", "law");
        return "sub3-3_en";
    }

    @GetMapping("/sub3-4.html")
    public String resourceForms(@RequestParam(name = "page", defaultValue = "0") int page, Model model) {
        Page<ResourcePost> resources = resourceService.listByCategory(ResourceCategory.FORMS, resourcePageable(page));
        model.addAttribute("resources", resources.getContent());
        addPagination(model, resources);
        model.addAttribute("activeTab", "forms");
        return "sub3-4";
    }

    @GetMapping("/en/sub3-4.html")
    public String resourceFormsEn(@RequestParam(name = "page", defaultValue = "0") int page, Model model) {
        Page<ResourcePost> resources = resourceService.listByCategory(ResourceCategory.FORMS, resourcePageable(page));
        model.addAttribute("resources", resources.getContent());
        addPagination(model, resources);
        model.addAttribute("activeTab", "forms");
        return "sub3-4_en";
    }

    @GetMapping("/view_resource_public.html")
    public String resourceView(@RequestParam("id") Long id, Model model) {
        return resourceService.find(id)
                .map(resource -> {
                    model.addAttribute("resource", resource);
                    return "view_resource_public";
                })
                .orElse("redirect:/sub3.html");
    }

    @GetMapping("/en/view_resource_public.html")
    public String resourceViewEn(@RequestParam("id") Long id, Model model) {
        return resourceService.find(id)
                .map(resource -> {
                    model.addAttribute("resource", resource);
                    return "view_resource_public_en";
                })
                .orElse("redirect:/en/sub3.html");
    }

    private Pageable pageable(int page) {
        return PageRequest.of(page, PAGE_SIZE, Sort.by(Sort.Direction.DESC, "id"));
    }

    private Pageable resourcePageable(int page) {
        return PageRequest.of(page, PAGE_SIZE, Sort.by(Sort.Order.desc("pinned"), Sort.Order.desc("id")));
    }

    private void addPagination(Model model, Page<?> page) {
        int totalPages = page.getTotalPages();
        int currentPage = page.getNumber();
        if (totalPages == 0) {
            model.addAttribute("totalPages", 0);
            model.addAttribute("currentPage", 0);
            model.addAttribute("startPage", 0);
            model.addAttribute("endPage", 0);
            model.addAttribute("hasPrevBlock", false);
            model.addAttribute("hasNextBlock", false);
            model.addAttribute("prevBlockPage", 0);
            model.addAttribute("nextBlockPage", 0);
            return;
        }

        int startPage = (currentPage / PAGE_BUTTONS) * PAGE_BUTTONS;
        int endPage = Math.min(startPage + PAGE_BUTTONS - 1, totalPages - 1);
        boolean hasPrevBlock = startPage > 0;
        boolean hasNextBlock = endPage < totalPages - 1;

        model.addAttribute("totalPages", totalPages);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("hasPrevBlock", hasPrevBlock);
        model.addAttribute("hasNextBlock", hasNextBlock);
        model.addAttribute("prevBlockPage", Math.max(0, startPage - 1));
        model.addAttribute("nextBlockPage", Math.min(totalPages - 1, endPage + 1));
    }
}
