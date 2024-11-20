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
 * @author meiqi huang
 * @since 2024-11-14
 */
@Getter
@Setter
public class Documentuploadtime implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer documentId;

    private String documentName;

    private String userId;

    private String userName;

    private LocalDateTime uploadTime;
}
