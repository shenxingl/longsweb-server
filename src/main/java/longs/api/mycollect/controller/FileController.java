package longs.api.mycollect.controller;

import longs.api.common.dao.entity.XtcsEntity;
import longs.api.common.service.XtcsService;
import longs.api.until.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/filectrl")
public class FileController {

    @Autowired
    private XtcsService xtcsService;

    /**
     * 上传文件
     * @return R
     */
    @PostMapping("/upload")
    public R upload(@RequestParam("file") MultipartFile file) throws IOException {
        try {
            List<XtcsEntity> basePathList = xtcsService.getXtcsByName("filebasepath");
            String basePath = basePathList.get(0).getCss003();
            File imagePath = new File(basePath);                                    // 目录（E:\）
            if (!imagePath.exists()) {                                             // 判断目录是否存在
                imagePath.mkdirs();                                                // 不存在则创建
            }

            String fileName = file.getOriginalFilename();                          // 上传的图片的名字（微信图片_20200702133026.jpg）
            String fileSuffix = fileName.substring(fileName.lastIndexOf("."), fileName.length());// 获取图片拓展名
            String localFileName = System.currentTimeMillis() + fileSuffix;        // 存储的图片名（1595493859895.jpg）
            String filePath = basePath + File.separator + localFileName;           // 完整文件目录及文件名（E:\\1595493859895.jpg）
            File localFile = new File(filePath);                                   // 文件（E:\1595493859895.jpg）
            file.transferTo(localFile);

            HashMap fileInfo = new HashMap();
            fileInfo.put("fileName", fileName);
            fileInfo.put("filePath", filePath);
            return R.ok().put("fileInfo", fileInfo);
        } catch (Exception ex) {
            return R.error(-1, "上传文件失败：" + ex.getMessage());
        }
    }
}
