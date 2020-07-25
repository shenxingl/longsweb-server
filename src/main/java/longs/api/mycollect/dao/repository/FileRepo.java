package longs.api.mycollect.dao.repository;

import longs.api.mycollect.dao.entity.FileEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileRepo  extends JpaRepository<FileEntity, Long> {

    Page<FileEntity> findByFl103Like(String fl103, Pageable pageable);
    FileEntity findByFl101(int fl101);
}
