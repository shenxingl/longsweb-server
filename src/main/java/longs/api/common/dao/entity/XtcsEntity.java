package longs.api.common.dao.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "xtcs")                              // 系统参数表
public class XtcsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int css001;                            // 主键

    private String css002;                         // 参数关键字

    private String css003;                         // 参数内容

    private String css004;                         // 参数说明

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private String css005;                         // 添加时间
}
