package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author meiqihuang
 * @since 2024-11-29
 */
@Getter
@Setter
public class Applicationdownloadtime implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer applicationId;

    private String applicationName;

    private String userId;

    private String userName;

    private LocalDateTime downloadTime;
}
