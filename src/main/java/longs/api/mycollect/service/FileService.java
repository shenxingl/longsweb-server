package longs.api.mycollect.service;

import longs.api.mycollect.dao.entity.FileEntity;
import longs.api.mycollect.dao.repository.FileRepo;
import longs.api.until.R;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;

@Service
public class FileService {

    @Autowired
    private FileRepo fileRepo;

    /**
     * 根据条件查询文件列表
     *
     * @return R
     */
    public R getFileList(int pageNo, int pageSize, String fl103) {
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

    /**
     * 下载文件
     *
     * @return R
     */
    public void downloadFile(int fl101, HttpServletResponse res) {
        try {
            FileEntity fileEntity = fileRepo.findByFl101(fl101);

            if (fileEntity == null) {
                System.out.println("下载失败，请检查文件信息是否存在");
            } else {
                String fileName = fileEntity.getFl103();
                String downlaodFilename = URLEncoder.encode(fileName, "utf-8");

                String filePath = fileEntity.getFl105();
                File file = new File(filePath);                                        // 获取文件
                InputStream is = new FileInputStream(file);                            //读取流

                res.setHeader("Content-Disposition", "attachment; fileName=" + downlaodFilename);
                res.setHeader("content-type", "application/octet-stream");
                res.addHeader("Content-Length", String.valueOf(file.length()));

                try {
                    if (is == null) {
                        System.out.println("下载失败，请检查文件“" + filePath + "”是否存在");
                    }
                    //复制
                    IOUtils.copy(is, res.getOutputStream());
                    res.getOutputStream().flush();
                    fileEntity.setFl106(fileEntity.getFl106() + 1);
                    fileRepo.save(fileEntity);
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        if (is != null) {
                            is.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
