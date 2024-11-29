package com.example.demo.controller;

import com.example.demo.entity.*;
import com.example.demo.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author meiqi huang
 * @since 2024-11-29
 */

@Controller
@RequestMapping("/application")
public class ApplicationController {

    @Value("${upload.path}")
    private String uploadPath;

    @Autowired
    private IApplicationService applicationService;

    @Autowired
    private IApplicationdownloadtimeService applicationdownloadtimeService;

    @Autowired
    private IApplicationuploadtimeService applicationuploadtimeService;

//    @Autowired
//    private IApplicationviewtimeService applicationviewtimeService;


    @Autowired
    private IUserService userService;

    private void recorduploadtime(Application application){
        Applicationuploadtime applicationuploadtime = new Applicationuploadtime();
        applicationuploadtime.setApplicationId(application.getId());
        applicationuploadtime.setApplicationName(application.getName());
        applicationuploadtime.setUserId(application.getAuthor());
        applicationuploadtime.setUserName(userService.getById(application.getAuthor()).getUsername());
        applicationuploadtimeService.save(applicationuploadtime);
    }

    private void recorddownloadtime(Application application,String userId){
        Applicationdownloadtime applicationdownloadtime = new Applicationdownloadtime();
        applicationdownloadtime.setApplicationId(application.getId());
        applicationdownloadtime.setApplicationName(application.getName());
        applicationdownloadtime.setUserId(userId);
        applicationdownloadtime.setUserName(userService.getById(userId).getUsername());
        applicationdownloadtimeService.save(applicationdownloadtime);
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
    public Boolean uploadFile(@RequestBody Application application) {
        boolean save = applicationService.save(application);
        if(save){
            recorduploadtime(application);
            return  true;
        }


        return false;
    }

    @GetMapping("/view/{userid}/{applicationid}")
    public ResponseEntity<Resource> viewApplication(@PathVariable String userid, @PathVariable Integer applicationid) {
        // Validate input
        if (userid == null || applicationid == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        // Fetch the application
        Application application = applicationService.getById(applicationid);
        if (application == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // Validate user permission
        User user = userService.getById(userid);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }


        if(!application.getAuthor().equals(userid)){

            if (!application.getPermission()) {

                if (application.getVisibleUserId() == null || application.getVisibleDepartmentId() == null) {
                    return new ResponseEntity<>(HttpStatus.FORBIDDEN);
                }

                // Check if the user is allowed to view the application based on their department and user ID
                if (user.getDepartmentId() != null) {
                    if (!application.getVisibleUserId().contains(user.getId()) && !application.getVisibleDepartmentId().contains(user.getDepartmentId().toString())) {
                        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
                    }
                } else {
                    if (!application.getVisibleUserId().contains(user.getId())) {
                        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
                    }
                }
            }

        }

        // Get the file path
        Path filePath = Paths.get(uploadPath, application.getUrl()).toAbsolutePath();
        File file = filePath.toFile();
        if (!file.exists()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // Record the view time (you can implement a similar record-view-time method as you did with download)
        // recordViewTime(application, userid);  // Optional if you want to track viewing time

        // Serve the application as a resource
        Resource resource = new FileSystemResource(file);
        HttpHeaders headers = new HttpHeaders();

        // Set the content type and disposition for inline display (browser will try to display it instead of downloading)
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + file.getName() + "\"");

        // Check the file type and set the appropriate content type
        if (application.getUrl().endsWith(".pdf")) {
            headers.add(HttpHeaders.CONTENT_TYPE, "application/pdf");
        } else if (application.getUrl().endsWith(".jpg") || application.getUrl().endsWith(".jpeg") || application.getUrl().endsWith(".png")) {
            headers.add(HttpHeaders.CONTENT_TYPE, "image/jpeg");  // or "image/png" based on the file
        } else {
            headers.add(HttpHeaders.CONTENT_TYPE, "application/octet-stream");  // Default to binary stream if unknown file type
        }

        Integer viewCount = application.getViewCount();
        viewCount++;
        application.setViewCount(viewCount);
        applicationService.updateById(application);
        return new ResponseEntity<>(resource, headers, HttpStatus.OK);
    }



    @GetMapping("/download/{userid}/{applicationid}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String userid, @PathVariable Integer applicationid) {
        //判空
        if (userid == null || applicationid == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        //先判断用户是否有权限下载文件
        Application application = applicationService.getById(applicationid);

        if(application == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        User user = userService.getById(userid);
        if (user == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        if(!application.getAuthor().equals(userid)){
            if (!application.getPermission()){
                //判空
                if (application.getVisibleUserId() == null || application.getVisibleDepartmentId() == null){
                    return new ResponseEntity<>(HttpStatus.FORBIDDEN);
                }
                if (user.getDepartmentId()!=null){
                    if (!application.getVisibleUserId().contains(user.getId()) && !application.getVisibleDepartmentId().contains(user.getDepartmentId().toString())){
                        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
                    }
                }else{
                    if (!application.getVisibleUserId().contains(user.getId())){
                        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
                    }
                }

            }}
        Path filePath = Paths.get( uploadPath, application.getUrl()).toAbsolutePath();
        File file = filePath.toFile();
        if (!file.exists()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        recorddownloadtime(application,userid);

        Resource resource = new FileSystemResource(file);
        HttpHeaders headers = new HttpHeaders();
        String fileName = null;
        try {
            fileName = URLEncoder.encode(file.getName(), StandardCharsets.UTF_8.toString());
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"; filename*=UTF-8''" + fileName);
        return new ResponseEntity<>(resource, headers, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @ResponseBody
    public Application getApplicationById(@PathVariable Integer id) {
        return applicationService.getById(id);
    }

    @GetMapping
    @ResponseBody
    public List<Application> getAllApplications() {
        return applicationService.list();
    }

    @GetMapping("/user/{id}")
    @ResponseBody
    public List<Application> getUserAllApplications(@PathVariable String id) {
        //获取当前用户
        User user = userService.getById(id);
        List<Application> applications = applicationService.list();
        //如果application权限为公开,或者application的作者为当前用户,或者application可查看的权限部门id包含当前用户的部门id,或者application可查看的用户id包含当前用户的id, 放行,
        //否则,移除
        applications.removeIf(application -> !application.getPermission() && !application.getAuthor().equals(id) && (user.getDepartmentId() == null || !application.getVisibleDepartmentId().contains(user.getDepartmentId().toString())) && !application.getVisibleUserId().contains(id));

        return applications;
    }

    @PutMapping("/{userid}/{applicationid}")
    @ResponseBody
    public Boolean updateApplication(@PathVariable String userid,@PathVariable Integer applicationid, @RequestBody Application application) {
        //判空
        if (application == null || userid == null || applicationid == null){
            return false;
        }

        //权限检查
        User user = userService.getById(userid);
        if (user == null){
            return false;
        }
        Application oldApplication = applicationService.getById(applicationid);
        if (oldApplication == null){
            return false;
        }
        if (!oldApplication.getAuthor().equals(userid)){
            return false;
        }

        application.setId(applicationid);
        return applicationService.updateById(application);
    }

    @DeleteMapping("/{userid}/{applicationid}")
    @ResponseBody
    public Boolean deleteApplication(@PathVariable String userid, @PathVariable Integer applicationid) {
        //获取application
        Application application = applicationService.getById(applicationid);
        //判空
        if (application == null){
            return false;
        }
        //获取当前用户
        User user = userService.getById(userid);
        //判空
        if (user == null){
            return false;
        }
        //如果application的作者不是当前用户,返回false
        if (!application.getAuthor().equals(userid)){
            return false;
        }
        //获取文件路径
        Path filePath = Paths.get( uploadPath, application.getUrl()).toAbsolutePath();
        //删除文件
        File file = filePath.toFile();
        if (file.exists()) {
            file.delete();
        }
        return applicationService.removeById(applicationid);
    }

}

