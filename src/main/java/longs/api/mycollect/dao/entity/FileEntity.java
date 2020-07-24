package longs.api.mycollect.dao.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "fl01")                              // 文件表
public class FileEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int fl101;                             // 主键

    private String fl102;                          // 文件存储名（代码）

    private String fl103;                          // 文件原名

    private String fl104;                          // 文件拓展名

    private String fl105;                          // 文件完整路径名

    private int fl106;                             // 文件下载次数

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private String fl107;                          // 文件上传时间
}
