package carivex.homepages.domain.notice.service;

import carivex.homepages.domain.notice.Notice;
import carivex.homepages.domain.notice.NoticeCategory;

import carivex.homepages.domain.notice.repo.NoticeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NoticeService {

    private final NoticeRepository noticeRepository;

    @Transactional(readOnly = true)
    public List<Notice> listAll() {
        return noticeRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
    }

    @Transactional(readOnly = true)
    public List<Notice> listByCategory(NoticeCategory category) {
        return noticeRepository.findByCategory(category, Sort.by(Sort.Direction.DESC, "id"));
    }

    @Transactional(readOnly = true)
    public Notice get(Long id) {
        return noticeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Notice not found: " + id));
    }

    @Transactional
    public Notice create(NoticeCategory category, String title, String content,
                         String fileOriginal, String fileStored) {
        return noticeRepository.save(new Notice(category, title, content, fileOriginal, fileStored));
    }

    @Transactional
    public Notice update(Long id, NoticeCategory category, String title, String content,
                         String fileOriginal, String fileStored) {
        Notice n = get(id);
        n.update(category, title, content, fileOriginal, fileStored);
        return n;
    }

    @Transactional
    public void delete(Long id) {
        noticeRepository.deleteById(id);
    }
}
