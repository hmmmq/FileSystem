package com.filesys.controller;

import com.filesys.entity.Document;
import com.filesys.entity.Documentuploadtime;
import com.filesys.entity.User;
import com.filesys.service.IDocumentuploadtimeService;
import com.filesys.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author meiqi huang
 * @since 2024-11-14
 */
@RestController
@RequestMapping("/documentuploadtime")
public class DocumentuploadtimeController {
    @Autowired
    IDocumentuploadtimeService documentuploadtimeService;


    @PostMapping
    public Boolean createDocumentuploadtime(@RequestBody Documentuploadtime documentuploadtime) {
        return documentuploadtimeService.save(documentuploadtime);
    }

    @GetMapping("/{id}")
    public Documentuploadtime getDocumentuploadtimeById(@PathVariable Integer id) {
        return documentuploadtimeService.getById(id);
    }

    @GetMapping
    public List<Documentuploadtime> getAllDocumentuploadtimes() {
        return documentuploadtimeService.list();
    }

    @PutMapping("/{id}")
    public Boolean updateDocumentuploadtime(@PathVariable Integer id, @RequestBody Documentuploadtime documentuploadtime) {
        documentuploadtime.setId(id);
        return documentuploadtimeService.updateById(documentuploadtime);
    }

    @DeleteMapping("/{id}")
    public Boolean deleteDocumentuploadtime(@PathVariable Integer id) {
        return documentuploadtimeService.removeById(id);
    }

    @GetMapping("/user/{id}")
    @ResponseBody
    public List<Documentuploadtime> getUserAllDocuments(@PathVariable String id) {
        //获取当前用户
        List<Documentuploadtime> documentuploadtimes = documentuploadtimeService.list();
        //如果document权限为公开,或者document的作者为当前用户,或者document可查看的权限部门id包含当前用户的部门id,或者document可查看的用户id包含当前用户的id, 放行,
        //否则,移除
        documentuploadtimes.removeIf(documentuploadtime -> !documentuploadtime.getUserId().equals(id));

        return documentuploadtimes;
    }
}
