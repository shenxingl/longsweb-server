package longs.api.mycollect.service;

import longs.api.mycollect.dao.entity.UrlCollectEntity;
import longs.api.mycollect.dao.repository.UrlCollectRepo;
import longs.api.until.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

@Service
public class UrlCollectService {

    @Autowired
    private UrlCollectRepo urlCollectRepo;

    /**
     * 根据条件查询url列表
     * @return R
     */
    public R getUrlList(int pageNo, int pageSize, String uf203, String uf101) {
        try {
            Sort sort = new Sort(Sort.Direction.DESC, "uf204");
            Pageable pageable = new PageRequest(pageNo, pageSize, sort);
            Page<UrlCollectEntity> urlList;
            if (uf101.equals("")) {
                urlList = urlCollectRepo.findByUf203LikeAndUf205("%" + uf203 + "%", "1", pageable);
            } else {
                urlList = urlCollectRepo.findByUf203LikeAndUf101AndUf205("%" + uf203 + "%", uf101, "1", pageable);
            }
            return R.ok().put("urlList", urlList);
        } catch (Exception ex) {
            return R.error(-1, "查询url列表失败：" + ex.getMessage());
        }
    }

    /**
     * 删除url
     * @return R
     */
    public R delUrl(String uf201) {
        try {
            UrlCollectEntity newUrlEntity = urlCollectRepo.findByUf201(uf201);
            if (newUrlEntity == null) {
                return R.error(-1, "URL信息不存在！");
            } else {
                newUrlEntity.setUf205("0");
                urlCollectRepo.save(newUrlEntity);
                return R.ok();
            }
        } catch (Exception ex) {
            return R.error(-1, "删除url失败：" + ex.getMessage());
        }
    }

    /**
     * 新增url
     * @return R
     */
    public R addUrl(String uf101, String uf202, String uf203) {
        try {
            Long timeT = System.currentTimeMillis();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String uf204 = simpleDateFormat.format(new Date(timeT));
            Random random = new Random();
            int sjs1 = random.nextInt(10);
            int sjs2 = random.nextInt(10);
            String uf201 = "" + timeT + sjs1 + sjs2;

            UrlCollectEntity newUrlEntity = new UrlCollectEntity();
            newUrlEntity.setUf101(uf101);
            newUrlEntity.setUf201(uf201);
            newUrlEntity.setUf202(uf202);
            newUrlEntity.setUf203(uf203);
            newUrlEntity.setUf204(uf204);
            newUrlEntity.setUf205("1");
            urlCollectRepo.save(newUrlEntity);
            return R.ok();
        } catch (Exception ex) {
            return R.error(-1, "新增url失败：" + ex.getMessage());
        }
    }
}
