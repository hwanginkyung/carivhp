package carivex.homepages.domain.inquiry.service;

import carivex.homepages.domain.inquiry.Inquiry;
import carivex.homepages.domain.inquiry.InquiryStatus;
import carivex.homepages.domain.inquiry.InquiryType;

import carivex.homepages.domain.inquiry.repo.InquiryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InquiryService {

    private final InquiryRepository inquiryRepository;

    @Transactional(readOnly = true)
    public List<Inquiry> listAll() {
        return inquiryRepository.findAllLatest();
    }

    @Transactional(readOnly = true)
    public Inquiry get(Long id) {
        return inquiryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Inquiry not found: " + id));
    }

    @Transactional
    public Inquiry create(String name, String contact, String email, InquiryType type, String content) {
        return inquiryRepository.save(new Inquiry(name, contact, email, type, content));
    }

    @Transactional
    public void updateStatus(Long id, InquiryStatus status) {
        Inquiry i = inquiryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Inquiry not found: " + id));
        i.updateStatus(status);
    }

    @Transactional
    public void delete(Long id) {
        inquiryRepository.deleteById(id);
    }
}
