package carivex.homepages.domain.page.service;

import carivex.homepages.domain.page.PageType;
import carivex.homepages.domain.page.StaticPage;
import carivex.homepages.domain.page.repo.StaticPageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class StaticPageService {

    private final StaticPageRepository staticPageRepository;

    @Transactional(readOnly = true)
    public StaticPage get(PageType pageType) {
        return staticPageRepository.findByPageType(pageType)
                .orElseThrow(() -> new IllegalArgumentException("Page not found: " + pageType));
    }

    public StaticPage upsert(PageType pageType, String title, String content) {
        return staticPageRepository.findByPageType(pageType)
                .map(existing -> {
                    existing.update(title, content);
                    return existing;
                })
                .orElseGet(() -> staticPageRepository.save(new StaticPage(pageType, title, content)));
    }
}
