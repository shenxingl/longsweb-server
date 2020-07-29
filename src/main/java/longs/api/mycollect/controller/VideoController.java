package longs.api.mycollect.controller;

import longs.api.mycollect.service.VideoService;
import longs.api.until.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/videoctrl")
public class VideoController {

    @Autowired
    private VideoService videoService;

    /**
     * 根据条件查询视频列表
     * @return R
     */
    @GetMapping("/getVideoList")
    public R getFileList(
            @RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "999") int pageSize,
            @RequestParam(required = false, defaultValue = "") String vd103) {
        return videoService.getVideoList(pageNo, pageSize, vd103);
    }

    /**
     * 播放视频
     * @return R
     */
    @GetMapping("/videoplayer")
    public void videoPlayer(HttpServletRequest request, HttpServletResponse response, @RequestParam int vd101) throws Exception {
        videoService.videoPlayer(request, response, vd101);
    }
}
