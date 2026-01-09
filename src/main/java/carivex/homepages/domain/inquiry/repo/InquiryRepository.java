package carivex.homepages.domain.inquiry.repo;

import carivex.homepages.domain.inquiry.Inquiry;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InquiryRepository extends JpaRepository<Inquiry, Long> {
    default List<Inquiry> findAllLatest() {
        return findAll(Sort.by(Sort.Direction.DESC, "id"));
    }
}
