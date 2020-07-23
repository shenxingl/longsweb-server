package longs.api.mycollect.dao.repository;

import longs.api.mycollect.dao.entity.UrlCollectEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UrlCollectRepo extends JpaRepository<UrlCollectEntity, Long> {

    Page<UrlCollectEntity> findByUf203LikeAndUf205(String uf202, String uf205, Pageable pageable);
    Page<UrlCollectEntity> findByUf203LikeAndUf101AndUf205(String uf202, String uf101, String uf205, Pageable pageable);
    UrlCollectEntity findByUf201(String uf201);

}
