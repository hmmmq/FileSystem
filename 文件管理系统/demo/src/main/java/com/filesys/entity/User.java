package com.filesys.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
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
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    private String password;

    private Integer departmentId;

    private String username;

    private String email;

    private String phone;

    private String gender;

    private String avatarUrl;

    private Boolean status;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    private String lastLoginIp;

    private LocalDateTime lastLoginTime;

    private String creator;

    private String updater;

    private LocalDateTime updateTime;

    private String remark;

    private String departmentName;

    private Boolean type;
}
