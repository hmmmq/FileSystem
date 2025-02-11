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
 * @author meiqi
 * @since 2024-10-25
 */
@Getter
@Setter
public class Document implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String name;

    private String author;

    private LocalDateTime uploadTime;

    private Boolean status;

    private String category;

    private String keyword;

    private Integer viewCount;

    private String url;

    private Boolean permission;

    private String visibleDepartmentId;

    private String visibleUserId;
}
