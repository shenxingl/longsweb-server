package longs.api.mycollect.dao.repository;

import longs.api.mycollect.dao.entity.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileRepo  extends JpaRepository<FileEntity, Long> {
}
