package carivex.homepages.domain.banner.repo;

import carivex.homepages.domain.banner.Banner;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BannerRepository extends JpaRepository<Banner, Long> {
    default List<Banner> findAllSorted() {
        return findAll(Sort.by(Sort.Order.desc("active"), Sort.Order.asc("sortOrder"), Sort.Order.desc("id")));
    }
}
