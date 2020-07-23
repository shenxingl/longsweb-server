package longs.api.common.controller;

import longs.api.common.service.XtcsService;
import longs.api.until.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/urlctrl")
public class XtcsController {

    @Autowired
    private XtcsService xtcsService;

    public R getXtcsByName(
            @RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "999") int pageSize,
            @RequestParam String css002) {
        try {
            return R.ok().put("xtcsList", xtcsService.getXtcsByName(css002));
        } catch (Exception ex) {
            return R.error(-1, "查询参数列表失败：" + ex.getMessage());
        }
    }
}
