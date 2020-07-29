package longs.api.mycollect.dao.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "vd01")                              // 视频文件表
public class VideoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int vd101;                             // 主键

    private String vd102;                          // 视频名

    private String vd103;                          // 视频文件存储名（代码）

    private String vd104;                          // 视频文件原名

    private String vd105;                          // 视频文件拓展名

    private String vd106;                          // 视频文件完整路径名

    private int vd107;                             // 视频文件观看次数

    private String vd108;                          // 视频文件上传人

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private String vd109;                          // 视频文件上传时间
}
