package com.zy.web.webserver.bean;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

/**
 * 传感器类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Sessor {

    @TableId(type=IdType.AUTO)
    private Integer id;

    private Date datetime;

    private String ip;

    private String data;

}

