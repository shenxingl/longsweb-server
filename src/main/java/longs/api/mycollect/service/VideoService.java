package longs.api.mycollect.service;

import lombok.AllArgsConstructor;
import longs.api.api.NonStaticResourceHttpRequestHandler;
import longs.api.mycollect.dao.entity.VideoEntity;
import longs.api.mycollect.dao.repository.VideoRepo;
import longs.api.until.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@AllArgsConstructor
public class VideoService {

    @Autowired
    private VideoRepo videoRepo;
    @Autowired
    private final NonStaticResourceHttpRequestHandler nonStaticResourceHttpRequestHandler;

    /**
     * 根据条件查询视频列表
     * @return R
     */
    public R getVideoList(int pageNo, int pageSize, String vd103) {
        try {
            Sort sort = new Sort(Sort.Direction.DESC, "vd108");
            Pageable pageable = new PageRequest(pageNo, pageSize, sort);
            Page<VideoEntity> videoList;
            videoList = videoRepo.findByVd103Like("%" + vd103 + "%", pageable);
            return R.ok().put("videoList", videoList);
        } catch (Exception ex) {
            return R.error(-1, "查询视频列表失败：" + ex.getMessage());
        }
    }

    /**
     * 播放视频
     * @return null
     */
    public void videoPlayer(HttpServletRequest request, HttpServletResponse response, int vd101) throws Exception {

        VideoEntity videoEntity = videoRepo.findByVd101(vd101);
        if (videoEntity == null) {
            System.out.println("视频信息不存在");
        } else {
            Path filePath = Paths.get(videoEntity.getVd106());

            if (Files.exists(filePath)) {
                String mimeType = Files.probeContentType(filePath);
                if (!StringUtils.isEmpty(mimeType)) {
                    response.setContentType(mimeType);
                }
                request.setAttribute(NonStaticResourceHttpRequestHandler.ATTR_FILE, filePath);
                nonStaticResourceHttpRequestHandler.handleRequest(request, response);
                videoEntity.setVd107(videoEntity.getVd107() + 1);
                videoRepo.save(videoEntity);
            } else {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                response.setCharacterEncoding(StandardCharsets.UTF_8.toString());
            }
        }
    }


    /**
     * @author wb
     * @create_time 2020-03-10
     * @description 视频断点续传播放测试接口
     * @param request
     * @param response
     */
//    @ApiOperation(httpMethod = "GET",value = "视频断点续传播放测试接口" ,notes = "视频断点续传播放测试接口")
//    @RequestMapping(value = "/player", method = RequestMethod.GET)
//    public void player2(HttpServletRequest request, HttpServletResponse response) {
//        String path = ClassUtils.getDefaultClassLoader().getResource("static/video").getPath()+"/three.wmv" ;
//
//        BufferedInputStream bis = null;
//        try {
//            File file = new File(path);
//            if (file.exists()) {
//                long p = 0L;
//                long toLength = 0L;
//                long contentLength = 0L;
//                int rangeSwitch = 0; // 0,从头开始的全文下载；1,从某字节开始的（bytes=27000-）；2,从某字节开始到某字节结束的（bytes=27000-39000）
//                long fileLength;
//                String rangBytes = "";
//                fileLength = file.length();
//
//                // get file content
//                InputStream ins = new FileInputStream(file);
//                bis = new BufferedInputStream(ins);
//
//                // tell the client to allow accept-ranges
//                response.reset();
//                //支持断点续传
//                response.setHeader("Accept-Ranges", "bytes");
//
//                // client requests a file block download start byte
//                String range = request.getHeader("Range");
//                if (range != null && range.trim().length() > 0 && !"null".equals(range)) {
////                    200是OK（一切正常），206是Partial Content（服务器已经成功处理了部分内容），
////                    416 Requested Range Not Satisfiable（对方（客户端）发来的Range 请求头不合理）。
//                    response.setStatus(javax.servlet.http.HttpServletResponse.SC_PARTIAL_CONTENT);
//                    rangBytes = range.replaceAll("bytes=", "");
//                    if (rangBytes.endsWith("-")) { // bytes=270000-
//                        rangeSwitch = 1;
//                        p = Long.parseLong(rangBytes.substring(0, rangBytes.indexOf("-")));
//                        contentLength = fileLength - p; // 客户端请求的是270000之后的字节（包括bytes下标索引为270000的字节）
//                    } else { // bytes=270000-320000
//                        rangeSwitch = 2;
//                        String temp1 = rangBytes.substring(0, rangBytes.indexOf("-"));
//                        String temp2 = rangBytes.substring(rangBytes.indexOf("-") + 1, rangBytes.length());
//                        p = Long.parseLong(temp1);
//                        toLength = Long.parseLong(temp2);
//                        contentLength = toLength - p + 1; // 客户端请求的是 270000-320000 之间的字节
//                    }
//                } else {
//                    contentLength = fileLength;
//                }
//
//                // 如果设设置了Content-Length，则客户端会自动进行多线程下载。如果不希望支持多线程，则不要设置这个参数。
//                // Content-Length: [文件的总大小] - [客户端请求的下载的文件块的开始字节]
//                response.setHeader("Content-Length", new Long(contentLength).toString());
//
//                // 断点开始
//                // 响应的格式是:
//                // Content-Range: bytes [文件块的开始字节]-[文件的总大小 - 1]/[文件的总大小]
//                if (rangeSwitch == 1) {
//                    String contentRange = new StringBuffer("bytes ").append(new Long(p).toString()).append("-")
//                            .append(new Long(fileLength - 1).toString()).append("/")
//                            .append(new Long(fileLength).toString()).toString();
//                    response.setHeader("Content-Range", contentRange);
//                    bis.skip(p);
//                } else if (rangeSwitch == 2) {
//                    String contentRange = range.replace("=", " ") + "/" + new Long(fileLength).toString();
//                    response.setHeader("Content-Range", contentRange);
//                    bis.skip(p);
//                } else {
//                    String contentRange = new StringBuffer("bytes ").append("0-").append(fileLength - 1).append("/")
//                            .append(fileLength).toString();
//                    response.setHeader("Content-Range", contentRange);
//                }
//                response.setContentType("video/mp4");
////                String fileName = file.getName();
////                response.setContentType("application/octet-stream");
////                response.addHeader("Content-Disposition", "attachment;filename=" + fileName);
//                OutputStream out = response.getOutputStream();
//                int n = 0;
//                long readLength = 0;
//                int bsize = 1024;
//                byte[] bytes = new byte[bsize];
//                if (rangeSwitch == 2) {
//                    // 针对 bytes=27000-39000 的请求，从27000开始写数据
//                    while (readLength <= contentLength - bsize) {
//                        n = bis.read(bytes);
//                        readLength += n;
//                        out.write(bytes, 0, n);
//                    }
//                    if (readLength <= contentLength) {
//                        n = bis.read(bytes, 0, (int) (contentLength - readLength));
//                        out.write(bytes, 0, n);
//                    }
//                } else {
//                    while ((n = bis.read(bytes)) != -1) {
//                        out.write(bytes, 0, n);
//                    }
//                }
//                out.flush();
//                out.close();
//                bis.close();
//            }
//        } catch (IOException ie) {
//            // 忽略 ClientAbortException 之类的异常
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
}
