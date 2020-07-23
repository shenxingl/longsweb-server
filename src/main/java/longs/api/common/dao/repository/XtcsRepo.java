package longs.api.common.dao.repository;

import longs.api.common.dao.entity.XtcsEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface XtcsRepo extends JpaRepository<XtcsEntity, Long> {

    List<XtcsEntity> findByCss002(String css002);
}
