package carivex.homepages.web.admin;


import carivex.homepages.domain.banner.Banner;
import carivex.homepages.domain.banner.service.BannerService;
import carivex.homepages.domain.inquiry.service.InquiryService;
import carivex.homepages.domain.notice.Notice;
import carivex.homepages.domain.notice.NoticeCategory;
import carivex.homepages.domain.notice.service.NoticeService;
import carivex.homepages.domain.page.PageType;
import carivex.homepages.domain.page.StaticPage;
import carivex.homepages.domain.page.service.StaticPageService;
import carivex.homepages.domain.resource.ResourceCategory;
import carivex.homepages.domain.resource.ResourcePost;
import carivex.homepages.domain.resource.service.ResourceService;
import carivex.homepages.domain.storage.FileStorageService;
import carivex.homepages.domain.storage.StoredFile;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    private final NoticeService noticeService;
    private final ResourceService resourceService;
    private final InquiryService inquiryService;
    private final BannerService bannerService;
    private final FileStorageService fileStorageService;
    private final StaticPageService staticPageService;

    @GetMapping("/login.html")
    public String login() {
        return "admin/login";
    }

    @GetMapping("/notice.html")
    public String noticeList(Model model) {
        model.addAttribute("notices", noticeService.listAll());
        return "admin/notice";
    }

    @GetMapping("/resource.html")
    public String resourceList(Model model) {
        model.addAttribute("resources", resourceService.listAll());
        return "admin/resource";
    }

    @GetMapping("/inquiry.html")
    public String inquiryList(Model model) {
        model.addAttribute("inquiries", inquiryService.listAll());
        return "admin/inquiry";
    }

    @GetMapping("/banner.html")
    public String bannerList(Model model) {
        model.addAttribute("banners", bannerService.listAll());
        model.addAttribute("form", new BannerForm());
        return "admin/banner";
    }

    @GetMapping("/page.html")
    public String staticPage(@RequestParam(name = "type", defaultValue = "intro") String type,
                             Model model) {
        PageType pageType = parsePageType(type);
        StaticPage page = staticPageService.get(pageType);

        AdminStaticPageForm form = new AdminStaticPageForm();
        form.setType(type);
        form.setTitle(page.getTitle());
        form.setContent(page.getContent());

        model.addAttribute("form", form);
        model.addAttribute("pageLabel", pageType.getLabel());
        return "admin/page";
    }

    @GetMapping("/view_notice.html")
    public String noticeView(@RequestParam("id") Long id, Model model) {
        model.addAttribute("notice", noticeService.get(id));
        return "admin/view_notice";
    }

    @GetMapping("/view_resource.html")
    public String resourceView(@RequestParam("id") Long id, Model model) {
        model.addAttribute("resource", resourceService.get(id));
        return "admin/view_resource";
    }

    @GetMapping("/write.html")
    public String write(@RequestParam(name = "type", defaultValue = "notice") String type,
                        @RequestParam(name = "id", required = false) Long id,
                        Model model) {
        AdminPostForm form = new AdminPostForm();
        form.setType(type);

        if ("notice".equalsIgnoreCase(type) && id != null) {
            Notice n = noticeService.get(id);
            form.setId(n.getId());
            form.setTitle(n.getTitle());
            form.setContent(n.getContent());
            form.setCategory(n.getCategory().name());
            model.addAttribute("existingFileOriginal", n.getFileOriginalName());
            model.addAttribute("existingFileStored", n.getFileStoredName());
        }

        if ("resource".equalsIgnoreCase(type) && id != null) {
            ResourcePost p = resourceService.get(id);
            form.setId(p.getId());
            form.setTitle(p.getTitle());
            form.setContent(p.getContent());
            form.setCategory(p.getCategory().name());
            model.addAttribute("existingFileOriginal", p.getFileOriginalName());
            model.addAttribute("existingFileStored", p.getFileStoredName());
        }

        model.addAttribute("form", form);
        model.addAttribute("noticeCategories", NoticeCategory.values());
        model.addAttribute("resourceCategories", ResourceCategory.values());
        model.addAttribute("mode", (id == null) ? "create" : "edit");
        return "admin/write";
    }

    @PostMapping("/save")
    public String save(@Valid @ModelAttribute("form") AdminPostForm form,
                       BindingResult bindingResult,
                       @RequestParam(name = "file", required = false) MultipartFile file,
                       RedirectAttributes ra) throws IOException {
        if (bindingResult.hasErrors()) {
            ra.addFlashAttribute("error", "필수 입력값을 확인해 주세요.");
            return "redirect:/admin/write.html?type=" + form.getType() + (form.getId() != null ? "&id=" + form.getId() : "");
        }

        StoredFile stored = fileStorageService.storeSingle(file);

        if ("notice".equalsIgnoreCase(form.getType())) {
            if (form.getId() == null) {
                noticeService.create(
                        NoticeCategory.valueOf(form.getCategory()),
                        form.getTitle(),
                        form.getContent(),
                        stored == null ? null : stored.originalName(),
                        stored == null ? null : stored.storedName()
                );
            } else {
                Notice existing = noticeService.get(form.getId());
                String org = stored == null ? existing.getFileOriginalName() : stored.originalName();
                String st = stored == null ? existing.getFileStoredName() : stored.storedName();

                noticeService.update(
                        form.getId(),
                        NoticeCategory.valueOf(form.getCategory()),
                        form.getTitle(),
                        form.getContent(),
                        org, st
                );
            }
            return "redirect:/admin/notice.html";
        }

        if ("resource".equalsIgnoreCase(form.getType())) {
            if (form.getId() == null) {
                resourceService.create(
                        ResourceCategory.valueOf(form.getCategory()),
                        form.getTitle(),
                        form.getContent(),
                        stored == null ? null : stored.originalName(),
                        stored == null ? null : stored.storedName()
                );
            } else {
                ResourcePost existing = resourceService.get(form.getId());
                String org = stored == null ? existing.getFileOriginalName() : stored.originalName();
                String st = stored == null ? existing.getFileStoredName() : stored.storedName();

                resourceService.update(
                        form.getId(),
                        ResourceCategory.valueOf(form.getCategory()),
                        form.getTitle(),
                        form.getContent(),
                        org, st
                );
            }
            return "redirect:/admin/resource.html";
        }

        ra.addFlashAttribute("error", "type 값이 올바르지 않습니다.");
        return "redirect:/admin/notice.html";
    }

    @PostMapping("/delete")
    public String delete(@RequestParam("type") String type,
                         @RequestParam("id") Long id) {
        if ("notice".equalsIgnoreCase(type)) {
            noticeService.delete(id);
            return "redirect:/admin/notice.html";
        }
        if ("resource".equalsIgnoreCase(type)) {
            resourceService.delete(id);
            return "redirect:/admin/resource.html";
        }
        if ("inquiry".equalsIgnoreCase(type)) {
            inquiryService.delete(id);
            return "redirect:/admin/inquiry.html";
        }
        if ("banner".equalsIgnoreCase(type)) {
            bannerService.delete(id);
            return "redirect:/admin/banner.html";
        }
        return "redirect:/admin/notice.html";
    }

    @PostMapping("/banner/save")
    public String saveBanner(@Valid @ModelAttribute("form") BannerForm form,
                             BindingResult bindingResult,
                             @RequestParam(name = "image", required = false) MultipartFile image,
                             RedirectAttributes ra) throws IOException {

        if (bindingResult.hasErrors()) {
            ra.addFlashAttribute("error", "배너 정보를 확인해 주세요.");
            return "redirect:/admin/banner.html";
        }

        StoredFile stored = fileStorageService.storeSingle(image);

        if (form.getId() == null) {
            bannerService.create(
                    form.getTitle(),
                    form.getLinkUrl(),
                    stored == null ? null : stored.originalName(),
                    stored == null ? null : stored.storedName(),
                    form.isActive(),
                    form.getSortOrder()
            );
        } else {
            Banner existing = bannerService.get(form.getId());
            String org = stored == null ? existing.getImageOriginalName() : stored.originalName();
            String st = stored == null ? existing.getImageStoredName() : stored.storedName();

            bannerService.update(
                    form.getId(),
                    form.getTitle(),
                    form.getLinkUrl(),
                    org, st,
                    form.isActive(),
                    form.getSortOrder()
            );
        }
        return "redirect:/admin/banner.html";
    }

    @PostMapping("/page/save")
    public String savePage(@Valid @ModelAttribute("form") AdminStaticPageForm form,
                           BindingResult bindingResult,
                           RedirectAttributes ra) {
        if (bindingResult.hasErrors()) {
            ra.addFlashAttribute("error", "필수 입력값을 확인해 주세요.");
            return "redirect:/admin/page.html?type=" + form.getType();
        }

        PageType pageType = parsePageType(form.getType());
        staticPageService.upsert(pageType, form.getTitle(), form.getContent());
        ra.addFlashAttribute("success", "저장되었습니다.");
        return "redirect:/admin/page.html?type=" + form.getType();
    }

    private PageType parsePageType(String type) {
        if ("business".equalsIgnoreCase(type)) {
            return PageType.BUSINESS;
        }
        return PageType.INTRO;
    }
}
