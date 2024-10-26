package com.filesys.controller;

import com.filesys.entity.Document;
import com.filesys.entity.User;
import com.filesys.service.IDocumentService;
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


    /*
* vue代码
*
* <template>
  <div>
    <input type="file" @change="onFileChange" />
    <input type="text" v-model="document.title" placeholder="Document Title" />
    <button @click="uploadFile">Upload</button>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      file: null,
      document: {
        title: '',
        // Add other document fields as needed
      },
    };
  },
  methods: {
    onFileChange(event) {
      this.file = event.target.files[0];
    },
    async uploadFile() {
      if (!this.file) {
        alert('Please select a file');
        return;
      }

      const formData = new FormData();
      formData.append('file', this.file);
      formData.append('document', new Blob([JSON.stringify(this.document)], { type: 'application/json' }));

      try {
        const response = await axios.post('/document/upload', formData, {
          headers: {
            'Content-Type': 'multipart/form-data',
          },
        });
        alert('File uploaded successfully');
      } catch (error) {
        console.error('File upload failed', error);
        alert('File upload failed');
      }
    },
  },
};
</script>

 * */
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
        return documentService.save(document);
    }



    /*
    *
  <template>
  <div>
    <input type="text" v-model="fileName" placeholder="Enter file name" />
    <button @click="downloadFile">Download</button>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      fileName: '',
    };
  },
  methods: {
    async downloadFile() {
      if (!this.fileName) {
        alert('Please enter a file name');
        return;
      }

      try {
        const response = await axios.get(`/document/download/${this.fileName}`, {
          responseType: 'blob',
        });

        const url = window.URL.createObjectURL(new Blob([response.data]));
        const link = document.createElement('a');
        link.href = url;
        link.setAttribute('download', this.fileName);
        document.body.appendChild(link);
        link.click();
        link.remove();
      } catch (error) {
        console.error('File download failed', error);
        alert('File download failed');
      }
    },
  },
};
*
* // main.js or a similar entry point
import axios from 'axios';

axios.defaults.baseURL = 'http://localhost:8086'; // Adjust the base URL as needed
</script>
    * */
    @GetMapping("/download/")
    @ResponseBody
    public ResponseEntity<Resource> downloadFile(@RequestBody User user, @RequestBody Document document) {
        //先判断用户是否有权限下载文件
        document = documentService.getById(document.getId());
        if(document == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if (!document.getPermission()){
            if (!document.getVisibleUserId().contains(user.getId()) && !document.getVisibleDepartmentId().contains(user.getDepartmentId().toString())){
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
        }



        Path filePath = Paths.get( uploadPath, document.getUrl()).toAbsolutePath();
        File file = filePath.toFile();
        if (!file.exists()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Resource resource = new FileSystemResource(file);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + document.getUrl() + "\"");
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

    @PutMapping("/{id}")
    @ResponseBody
    public Boolean updateDocument(@PathVariable Integer id, @RequestBody Document document) {
        document.setId(id);
        return documentService.updateById(document);
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public Boolean deleteDocument(@PathVariable Integer id) {
        return documentService.removeById(id);
    }
    
}

