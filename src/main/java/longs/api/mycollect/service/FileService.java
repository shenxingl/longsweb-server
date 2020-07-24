package longs.api.mycollect.service;

import longs.api.mycollect.dao.entity.FileEntity;
import longs.api.mycollect.dao.repository.FileRepo;
import longs.api.until.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class FileService {

    @Autowired
    private FileRepo fileRepo;

    /**
     * 根据条件查询url列表
     * @return R
     */
    public R getUrlList(int pageNo, int pageSize, String fl103) {
        try {
            Sort sort = new Sort(Sort.Direction.DESC, "fl107");
            Pageable pageable = new PageRequest(pageNo, pageSize, sort);
            Page<FileEntity> fileList;
            fileList = fileRepo.findByFl103Like("%" + fl103 + "%", pageable);
            return R.ok().put("fileList", fileList);
        } catch (Exception ex) {
            return R.error(-1, "查询文件列表失败：" + ex.getMessage());
        }
    }
}
