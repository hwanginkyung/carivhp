package carivex.homepages;

import carivex.homepages.domain.notice.NoticeCategory;
import carivex.homepages.domain.notice.repo.NoticeRepository;
import carivex.homepages.domain.notice.service.NoticeService;
import carivex.homepages.domain.page.PageType;
import carivex.homepages.domain.page.repo.StaticPageRepository;
import carivex.homepages.domain.page.service.StaticPageService;
import carivex.homepages.domain.resource.ResourceCategory;
import carivex.homepages.domain.resource.repo.ResourceRepository;
import carivex.homepages.domain.resource.service.ResourceService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements ApplicationRunner {

    private final NoticeRepository noticeRepository;
    private final ResourceRepository resourceRepository;
    private final NoticeService noticeService;
    private final ResourceService resourceService;
    private final StaticPageRepository staticPageRepository;
    private final StaticPageService staticPageService;

    @Override
    public void run(ApplicationArguments args) {
        if (noticeRepository.count() == 0) {
            noticeService.create(NoticeCategory.PRESS, "샘플 공지: 언론 보도", "여기에 내용이 들어갑니다.", null, null);
            noticeService.create(NoticeCategory.UNION, "샘플 공지: 조합 공지", "여기에 내용이 들어갑니다.", null, null);
        }
        if (resourceRepository.count() == 0) {
            resourceService.create(ResourceCategory.EXPORT_STATS, "샘플 자료: 수출 통계", "여기에 내용이 들어갑니다.", null, null);
            resourceService.create(ResourceCategory.FORMS, "샘플 자료: 서식", "여기에 내용이 들어갑니다.", null, null);
        }
        if (staticPageRepository.count() == 0) {
            staticPageService.upsert(PageType.INTRO, "조합 소개", "조합 소개 내용을 입력하세요.");
            staticPageService.upsert(PageType.BUSINESS, "사업 안내", "사업 안내 내용을 입력하세요.");
        }
    }
}
