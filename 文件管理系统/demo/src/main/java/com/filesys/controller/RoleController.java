package com.filesys.controller;

import com.filesys.entity.Role;
import com.filesys.service.IRoleService;
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
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private IRoleService roleService;

    @PostMapping
    public Boolean createRole(@RequestBody Role role) {
        return roleService.save(role);
    }

    @GetMapping("/{id}")
    public Role getRoleById(@PathVariable Integer id) {
        return roleService.getById(id);
    }

    @GetMapping
    public List<Role> getAllRoles() {
        return roleService.list();
    }

    @PutMapping("/{id}")
    public Boolean updateRole(@PathVariable Integer id, @RequestBody Role role) {
        role.setId(id);
        return roleService.updateById(role);
    }

    @DeleteMapping("/{id}")
    public Boolean deleteRole(@PathVariable Integer id) {
        return roleService.removeById(id);
    }
}