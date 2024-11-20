package com.example.demo.controller;

import com.example.demo.entity.Rolepermission;
import com.example.demo.service.IRolepermissionService;
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
@RequestMapping("/Rolepermission")
public class RolepermissionController {

    @Autowired
    private IRolepermissionService RolepermissionService;

    @PostMapping
    public Boolean createRolepermission(@RequestBody Rolepermission Rolepermission) {
        return RolepermissionService.save(Rolepermission);
    }

    @GetMapping("/{id}")
    public Rolepermission getRolepermissionById(@PathVariable Integer id) {
        return RolepermissionService.getById(id);
    }

    @GetMapping
    public List<Rolepermission> getAllRolepermissions() {
        return RolepermissionService.list();
    }

    @PutMapping("/{id}")
    public Boolean updateRolepermission(@PathVariable Integer id, @RequestBody Rolepermission Rolepermission) {
        Rolepermission.setId(id);
        return RolepermissionService.updateById(Rolepermission);
    }

    @DeleteMapping("/{id}")
    public Boolean deleteRolepermission(@PathVariable Integer id) {
        return RolepermissionService.removeById(id);
    }
}