package longs.api.mycollect.dao.repository;

import longs.api.mycollect.dao.entity.VideoEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VideoRepo  extends JpaRepository<VideoEntity, Long> {

    Page<VideoEntity> findByVd103Like(String vd103, Pageable pageable);
    VideoEntity findByVd101(int vd101);
}
