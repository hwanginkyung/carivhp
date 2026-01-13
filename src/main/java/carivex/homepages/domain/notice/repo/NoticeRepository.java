package carivex.homepages.domain.notice.repo;

import carivex.homepages.domain.notice.Notice;
import carivex.homepages.domain.notice.NoticeCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NoticeRepository extends JpaRepository<Notice, Long> {
    List<Notice> findByCategory(NoticeCategory category, Sort sort);

    Page<Notice> findByCategory(NoticeCategory category, Pageable pageable);
}
