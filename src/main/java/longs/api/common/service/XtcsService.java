package longs.api.common.service;

import longs.api.common.dao.entity.XtcsEntity;
import longs.api.common.dao.repository.XtcsRepo;
import longs.api.until.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class XtcsService {

    @Autowired
    private XtcsRepo xtcsRepo;

    /**
     * 根据参数名查询参数列表
     *
     * @return R
     */
    public List<XtcsEntity> getXtcsByName(String css002) {
        List<XtcsEntity> xtcsList = xtcsRepo.findByCss002(css002);
        return xtcsList;
    }
}
