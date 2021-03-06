package longs.api.mycollect.controller;

import lombok.AllArgsConstructor;
import longs.api.api.NonStaticResourceHttpRequestHandler;
import longs.api.common.dao.entity.XtcsEntity;
import longs.api.common.service.XtcsService;
import longs.api.mycollect.dao.entity.FileEntity;
import longs.api.mycollect.dao.repository.FileRepo;
import longs.api.mycollect.service.FileService;
import longs.api.until.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/filectrl")
@AllArgsConstructor
public class FileController {

    @Autowired
    private XtcsService xtcsService;
    @Autowired
    private FileRepo fileRepo;
    @Autowired
    private FileService fileService;
    @Autowired
    private final NonStaticResourceHttpRequestHandler nonStaticResourceHttpRequestHandler;

    /**
     * 上传文件
     * @return R
     */
    @PostMapping("/upload")
    public R upload(@RequestParam("file") MultipartFile file) {
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

            // 存数据库
            FileEntity fileEntity = new FileEntity();
            fileEntity.setFl102(localFileName);             // 文件存储名（代码）
            fileEntity.setFl103(fileName);                  // 文件原名
            fileEntity.setFl104(fileSuffix);                // 文件拓展名
            fileEntity.setFl105(filePath);                  // 文件完整路径名
            fileEntity.setFl106(0);                         // 被下载次数

            Long timeT = System.currentTimeMillis();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String fl107 = simpleDateFormat.format(new Date(timeT));
            fileEntity.setFl107(fl107);                     // 文件上传时间
            fileRepo.save(fileEntity);

            // 反参
            HashMap fileInfo = new HashMap();
            fileInfo.put("fileName", fileName);
            fileInfo.put("filePath", filePath);
            return R.ok().put("fileInfo", fileInfo);
        } catch (Exception ex) {
            return R.error(-1, "上传文件失败：" + ex.getMessage());
        }
    }

    /**
     * 获取文件列表
     * @return R
     */
    @GetMapping("/getFileList")
    public R getFileList(
            @RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "999") int pageSize,
            @RequestParam(required = false, defaultValue = "") String fl103) {
        return fileService.getFileList(pageNo, pageSize, fl103);
    }

    /**
     * 下载文件
     * @return R
     */
    @GetMapping("/downloadFile")
    public void downloadFile(@RequestParam int fl101,HttpServletResponse response) {
        fileService.downloadFile(fl101, response);
    }

}
