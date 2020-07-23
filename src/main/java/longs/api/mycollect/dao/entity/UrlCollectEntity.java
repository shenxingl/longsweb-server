package longs.api.mycollect.dao.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "uf02")                              // url列表
public class UrlCollectEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;                                // 主键

    private String uf101;                          // url分类代码

    private String uf201;                          // url代码

    private String uf202;                          // url地址

    private String uf203;                          // 备注

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private String uf204;                          // url添加时间

    private String uf205;                          // 是否有效
}
