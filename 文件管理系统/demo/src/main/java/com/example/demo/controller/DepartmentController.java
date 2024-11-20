package com.example.demo.controller;

import com.example.demo.entity.Department;
import com.example.demo.service.IDepartmentService;
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
@RequestMapping("/department")
public class DepartmentController {

    @Autowired
    private IDepartmentService departmentService;

    @PostMapping
    public Boolean createDepartment(@RequestBody Department department) {
        //判断部门是否存在
        if (departmentService.getById(department.getId()) != null) {
            return false;
        }

        return departmentService.save(department);
    }

    @GetMapping("/{id}")
    public Department getDepartmentById(@PathVariable Integer id) {
        return departmentService.getById(id);
    }

    @GetMapping
    public List<Department> getAllDepartments() {
        return departmentService.list();
    }

    @PutMapping("/{id}")
    public Boolean updateDepartment(@PathVariable Integer id, @RequestBody Department department) {
        department.setId(id);
        return departmentService.updateById(department);
    }

    @DeleteMapping("/{id}")
    public Boolean deleteDepartment(@PathVariable Integer id) {
        return departmentService.removeById(id);
    }
}