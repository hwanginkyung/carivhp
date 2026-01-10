package carivex.homepages.domain.page.repo;

import carivex.homepages.domain.page.PageType;
import carivex.homepages.domain.page.StaticPage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StaticPageRepository extends JpaRepository<StaticPage, Long> {
    Optional<StaticPage> findByPageType(PageType pageType);
}
