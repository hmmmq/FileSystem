package com.filesys.controller;

import com.filesys.entity.Document;
import com.filesys.entity.Documentdownloadtime;
import com.filesys.entity.Documentuploadtime;
import com.filesys.entity.User;
import com.filesys.service.IDocumentService;
import com.filesys.service.IDocumentdownloadtimeService;
import com.filesys.service.IDocumentuploadtimeService;
import com.filesys.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Value;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Controller
@RequestMapping("/document")
public class DocumentController {

    @Value("${upload.path}")
    private String uploadPath;

    @Autowired
    private IDocumentService documentService;

    @Autowired
    private IDocumentdownloadtimeService documentdownloadtimeService;

    @Autowired
    private IDocumentuploadtimeService documentuploadtimeService;


    @Autowired
    private IUserService userService;

    private void recorduploadtime(Document document){
        Documentuploadtime documentuploadtime = new Documentuploadtime();
        documentuploadtime.setDocumentId(document.getId());
        documentuploadtime.setDocumentName(document.getName());
        documentuploadtime.setUserId(document.getAuthor());
        documentuploadtime.setUserName(userService.getById(document.getAuthor()).getUsername());
        documentuploadtimeService.save(documentuploadtime);
    }

    private void recorddownloadtime(Document document,String userId){
        Documentdownloadtime documentdownloadtime = new Documentdownloadtime();
        documentdownloadtime.setDocumentId(document.getId());
        documentdownloadtime.setDocumentName(document.getName());
        documentdownloadtime.setUserId(userId);
        documentdownloadtime.setUserName(userService.getById(userId).getUsername());
        documentdownloadtimeService.save(documentdownloadtime);
    }

    @PostMapping("/upload/file")
    @ResponseBody
    public Boolean uploadFile(@RequestParam("file") MultipartFile file) {

        if (file.isEmpty()) {
            return false;
        }

        String fileName = file.getOriginalFilename();
        Path filePath = Paths.get( uploadPath, fileName).toAbsolutePath();

        //判断文件是否存在
        if (filePath.toFile().exists()) {
            return false;
        }


        try {
            //写入文件
            file.transferTo(filePath);


            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @PostMapping
    @ResponseBody
    public Boolean uploadFile(@RequestBody Document document) {

        recorduploadtime(document);

        return documentService.save(document);
    }




    @GetMapping("/download/{userid}/{documentid}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String userid, @PathVariable Integer documentid) {
        //判空
        if (userid == null || documentid == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        //先判断用户是否有权限下载文件
        Document document = documentService.getById(documentid);

        if(document == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        User user = userService.getById(userid);
        if (user == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if (!document.getPermission()){
            //判空
            if (document.getVisibleUserId() == null || document.getVisibleDepartmentId() == null){
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
            if (user.getDepartmentId()!=null){
                if (!document.getVisibleUserId().contains(user.getId()) && !document.getVisibleDepartmentId().contains(user.getDepartmentId().toString())){
                    return new ResponseEntity<>(HttpStatus.FORBIDDEN);
                }
            }else{
                if (!document.getVisibleUserId().contains(user.getId())){
                    return new ResponseEntity<>(HttpStatus.FORBIDDEN);
                }
            }

        }
        Path filePath = Paths.get( uploadPath, document.getUrl()).toAbsolutePath();
        File file = filePath.toFile();
        if (!file.exists()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        recorddownloadtime(document,userid);

        Resource resource = new FileSystemResource(file);
        HttpHeaders headers = new HttpHeaders();

        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getName() + "\"");
        return new ResponseEntity<>(resource, headers, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @ResponseBody
    public Document getDocumentById(@PathVariable Integer id) {
        return documentService.getById(id);
    }

    @GetMapping
    @ResponseBody
    public List<Document> getAllDocuments() {
        return documentService.list();
    }

    @GetMapping("/user/{id}")
    @ResponseBody
    public List<Document> getUserAllDocuments(@PathVariable String id) {
        //获取当前用户
        User user = userService.getById(id);
        List<Document> documents = documentService.list();
        //如果document权限为公开,或者document的作者为当前用户,或者document可查看的权限部门id包含当前用户的部门id,或者document可查看的用户id包含当前用户的id, 放行,
        //否则,移除
        documents.removeIf(document -> !document.getPermission() && !document.getAuthor().equals(id) && (user.getDepartmentId() == null || !document.getVisibleDepartmentId().contains(user.getDepartmentId().toString())) && !document.getVisibleUserId().contains(id));

        return documents;
    }

    @PutMapping("/{userid}/{documentid}")
    @ResponseBody
    public Boolean updateDocument(@PathVariable String userid,@PathVariable Integer documentid, @RequestBody Document document) {
        //判空
        if (document == null || userid == null || documentid == null){
            return false;
        }

        //权限检查
        User user = userService.getById(userid);
        if (user == null){
            return false;
        }
        Document oldDocument = documentService.getById(documentid);
        if (oldDocument == null){
            return false;
        }
        if (!oldDocument.getAuthor().equals(userid)){
            return false;
        }

        document.setId(documentid);
        return documentService.updateById(document);
    }

    @DeleteMapping("/{userid}/{documentid}")
    @ResponseBody
    public Boolean deleteDocument(@PathVariable String userid, @PathVariable Integer documentid) {
        //获取document
        Document document = documentService.getById(documentid);
        //判空
        if (document == null){
            return false;
        }
        //获取当前用户
        User user = userService.getById(userid);
        //判空
        if (user == null){
            return false;
        }
        //如果document的作者不是当前用户,返回false
        if (!document.getAuthor().equals(userid)){
            return false;
        }
        //获取文件路径
        Path filePath = Paths.get( uploadPath, document.getUrl()).toAbsolutePath();
        //删除文件
        File file = filePath.toFile();
        if (file.exists()) {
            file.delete();
        }
        return documentService.removeById(documentid);
    }
    
}

