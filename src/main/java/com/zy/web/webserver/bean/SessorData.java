package com.zy.web.webserver.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SessorData {

    @TableId(type = IdType.AUTO)
    private Integer Id;

    private Date Datetime;

    private String Type;

    private String Addr;

    private Double Value;

    private String Ip;
}
