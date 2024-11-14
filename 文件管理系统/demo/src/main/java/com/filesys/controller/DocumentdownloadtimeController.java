package com.filesys.controller;

import com.filesys.entity.Documentdownloadtime;
import com.filesys.entity.Documentdownloadtime;
import com.filesys.entity.User;
import com.filesys.service.IDocumentdownloadtimeService;
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
@RequestMapping("/documentdownloadtime")
public class DocumentdownloadtimeController {
    @Autowired
    IDocumentdownloadtimeService documentdownloadtimeService;

    @PostMapping
    public Boolean createDocumentdownloadtime(@RequestBody Documentdownloadtime documentdownloadtime) {
        return documentdownloadtimeService.save(documentdownloadtime);
    }

    @GetMapping("/{id}")
    public Documentdownloadtime getDocumentdownloadtimeById(@PathVariable Integer id) {
        return documentdownloadtimeService.getById(id);
    }

    @GetMapping
    public List<Documentdownloadtime> getAllDocumentdownloadtimes() {
        return documentdownloadtimeService.list();
    }

    @PutMapping("/{id}")
    public Boolean updateDocumentdownloadtime(@PathVariable Integer id, @RequestBody Documentdownloadtime documentdownloadtime) {
        documentdownloadtime.setId(id);
        return documentdownloadtimeService.updateById(documentdownloadtime);
    }

    @DeleteMapping("/{id}")
    public Boolean deleteDocumentdownloadtime(@PathVariable Integer id) {
        return documentdownloadtimeService.removeById(id);
    }

    @GetMapping("/user/{id}")
    @ResponseBody
    public List<Documentdownloadtime> getUserAllDocuments(@PathVariable String id) {
        //获取当前用户
        List<Documentdownloadtime> documentdownloadtimes = documentdownloadtimeService.list();
        //如果document权限为公开,或者document的作者为当前用户,或者document可查看的权限部门id包含当前用户的部门id,或者document可查看的用户id包含当前用户的id, 放行,
        //否则,移除
        documentdownloadtimes.removeIf(documentdownloadtime -> !documentdownloadtime.getUserId().equals(id));

        return documentdownloadtimes;
    }
}
