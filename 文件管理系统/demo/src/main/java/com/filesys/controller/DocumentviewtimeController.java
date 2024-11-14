package com.filesys.controller;

import com.filesys.entity.Documentviewtime;
import com.filesys.entity.Documentviewtime;
import com.filesys.service.IDocumentviewtimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author meiqi
 * @since 2024-10-25
 */
@RestController
@RequestMapping("/documentviewtime")
public class DocumentviewtimeController {
    @Autowired
    private IDocumentviewtimeService documentviewtimeService;

    @PostMapping
    public Boolean createDocumentviewtime(@RequestBody Documentviewtime documentviewtime) {
        return documentviewtimeService.save(documentviewtime);
    }

    @GetMapping("/{id}")
    public Documentviewtime getDocumentviewtimeById(@PathVariable Integer id) {
        return documentviewtimeService.getById(id);
    }

    @GetMapping
    public List<Documentviewtime> getAllDocumentviewtimes() {
        return documentviewtimeService.list();
    }

    @PutMapping("/{id}")
    public Boolean updateDocumentviewtime(@PathVariable Integer id, @RequestBody Documentviewtime documentviewtime) {
        documentviewtime.setId(id);
        return documentviewtimeService.updateById(documentviewtime);
    }

    @DeleteMapping("/{id}")
    public Boolean deleteDocumentviewtime(@PathVariable Integer id) {
        return documentviewtimeService.removeById(id);
    }

    @GetMapping("/user/{id}")
    @ResponseBody
    public List<Documentviewtime> getUserAllDocuments(@PathVariable String id) {
        //获取当前用户
        List<Documentviewtime> documentviewtimes = documentviewtimeService.list();
        //如果document权限为公开,或者document的作者为当前用户,或者document可查看的权限部门id包含当前用户的部门id,或者document可查看的用户id包含当前用户的id, 放行,
        //否则,移除
        documentviewtimes.removeIf(documentviewtime -> !documentviewtime.getUserId().equals(id));

        return documentviewtimes;
    }
}