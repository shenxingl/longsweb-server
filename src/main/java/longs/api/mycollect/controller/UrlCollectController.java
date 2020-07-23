package longs.api.mycollect.controller;

import longs.api.mycollect.service.UrlCollectService;
import longs.api.until.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/urlctrl")
public class UrlCollectController {

    @Autowired
    private UrlCollectService urlCollectService;

    /**
     * 根据条件查询url列表
     *
     * @return R
     */
    @GetMapping("/getUrlList")
    public R getUrlList(
            @RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "999") int pageSize,
            @RequestParam(required = false, defaultValue = "") String uf203,
            @RequestParam(required = false, defaultValue = "") String uf101
    ) {
        return urlCollectService.getUrlList(pageNo, pageSize, uf203, uf101);
    }

    /**
     * 新增url
     *
     * @return R
     */
    @PostMapping("/addUrl")
    public R addUrl(
            @RequestParam String uf101,
            @RequestParam String uf202,
            @RequestParam(required = false, defaultValue = "") String uf203
    ) {
        return urlCollectService.addUrl(uf101, uf202, uf203);
    }

    /**
     * 作废url
     *
     * @return R
     */
    @PostMapping("/delUrl")
    public R delUrl(@RequestParam String uf201) {
        return urlCollectService.delUrl(uf201);
    }
}
