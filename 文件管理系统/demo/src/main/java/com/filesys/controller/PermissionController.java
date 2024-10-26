package com.filesys.controller;

import com.filesys.entity.Permission;
import com.filesys.service.IPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 */
@RestController
@RequestMapping("/permission")
public class PermissionController {
    @Autowired
    private IPermissionService permissionService;

    @PostMapping
    public Boolean createPermission(@RequestBody Permission permission) {
        return permissionService.save(permission);
    }

    @GetMapping("/{id}")
    public Permission getPermissionById(@PathVariable Integer id) {
        return permissionService.getById(id);
    }

    @GetMapping
    public List<Permission> getAllPermissions() {
        return permissionService.list();
    }

    @PutMapping("/{id}")
    public Boolean updatePermission(@PathVariable Integer id, @RequestBody Permission permission) {
        permission.setId(id);
        return permissionService.updateById(permission);
    }

    @DeleteMapping("/{id}")
    public Boolean deletePermission(@PathVariable Integer id) {
        return permissionService.removeById(id);
    }
}