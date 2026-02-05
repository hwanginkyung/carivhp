package carivex.homepages.domain.notice.service;

import carivex.homepages.domain.notice.Notice;
import carivex.homepages.domain.notice.NoticeCategory;

import carivex.homepages.domain.notice.repo.NoticeRepository;
import carivex.homepages.domain.translation.TranslationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NoticeService {

    private final NoticeRepository noticeRepository;
    private final TranslationService translationService;

    @Transactional(readOnly = true)
    public List<Notice> listAll() {
        return noticeRepository.findAll(Sort.by(Sort.Order.desc("pinned"), Sort.Order.desc("id")));
    }

    @Transactional(readOnly = true)
    public Page<Notice> listAll(Pageable pageable) {
        return noticeRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public List<Notice> listByCategory(NoticeCategory category) {
        return noticeRepository.findByCategory(category, Sort.by(Sort.Order.desc("pinned"), Sort.Order.desc("id")));
    }

    @Transactional(readOnly = true)
    public Page<Notice> listByCategory(NoticeCategory category, Pageable pageable) {
        return noticeRepository.findByCategory(category, pageable);
    }

    @Transactional(readOnly = true)
    public Notice get(Long id) {
        return noticeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Notice not found: " + id));
    }

    @Transactional
    public Notice create(NoticeCategory category, String title, String content,
                         String fileOriginal, String fileStored) {
        String titleEn = translationService.translateToEnglishText(title);
        String contentEn = translationService.translateToEnglishHtml(content);
        return noticeRepository.save(new Notice(category, title, content, titleEn, contentEn, fileOriginal, fileStored));
    }

    @Transactional
    public Notice update(Long id, NoticeCategory category, String title, String content,
                         String fileOriginal, String fileStored) {
        Notice n = get(id);
        String titleEn = translationService.translateToEnglishText(title);
        String contentEn = translationService.translateToEnglishHtml(content);
        n.update(category, title, content, titleEn, contentEn, fileOriginal, fileStored);
        return n;
    }

    @Transactional
    public Notice togglePinned(Long id) {
        Notice n = get(id);
        n.setPinned(!n.isPinned());
        return n;
    }

    @Transactional
    public void delete(Long id) {
        noticeRepository.deleteById(id);
    }
}
