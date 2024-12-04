<template>
  <div class="content-wrapper">
    <div class="container-fluid">
      <!-- Breadcrumb-->
      <div class="row pt-2 pb-2">
        <div class="col-sm-9">
          <h4 class="page-title">上传应用</h4>
          <ol class="breadcrumb">
            <li class="breadcrumb-item">应用管理</li>
            <li class="breadcrumb-item active" aria-current="page">上传应用</li>
          </ol>
        </div>
      </div>
      <!-- End Breadcrumb-->
      <div class="row justify-content-center">
        <div class="card col-lg-12">
          <div class="card-body">
            <div class="card-title text-primary">应用资料</div>
            <hr />
            <form>
              <div class="form-group">
                <label>应用名称</label>
                <input
                  type="text"
                  class="form-control"
                  placeholder=""
                  v-model="application.name"
                />
              </div>
              <div class="form-group">
                <label>应用类别</label>
                <input
                  type="text"
                  class="form-control"
                  placeholder=""
                  v-model="application.category"
                />
              </div>
              <div class="form-group">
                <label for="input-3">应用关键字</label>
                <input
                  type="text"
                  class="form-control"
                  id="input-3"
                  placeholder=""
                  v-model="application.keyword"
                />
              </div>

              <div class="form-group">
                <label for="input-4">权限</label>
                <select
                  class="custom-select"
                  id="inputGroupSelect03"
                  @change="updatepermission"
                  ref="permissionSelect"
                >
                  <option selected="">选择...</option>
                  <option value="全部可见">全部可见</option>
                  <option value="指定人可见" @click="uservisible = true">
                    指定人可见
                  </option>
                  <option
                    value="指定部门可见"
                    @click="departmentvisible = true"
                  >
                    指定部门可见
                  </option>
                  <option value="仅自己可见">仅自己可见</option>
                </select>
              </div>

              <div class="form-group" v-show="uservisible">
                <label>用户列表</label>
                <select
                  class="custom-select"
                  @change="updateVisibleUserId"
                  multiple
                >
                  <option v-for="user in users" :key="user.id" :value="user.id">
                    {{ user.id }}{{ user.username }}
                  </option>
                </select>
                <div>已选择用户:</div>
                <div>{{ application.visibleUserId }}</div>
              </div>
              <div class="form-group" v-show="departmentvisible">
                <label>部门列表</label>
                <select
                  v-show="departmentvisible"
                  class="custom-select"
                  v-model="application.visibleDepartmentId"
                  @change="updateVisibleDepartmentId"
                  multiple
                >
                  <option
                    v-for="department in departments"
                    :key="department.id"
                    :value="department.id"
                  >
                    {{ department.name }}
                  </option>
                </select>
                <div>已选择部门:</div>
                <div>{{ application.visibleDepartmentId }}</div>
              </div>

              <div class="form-group">
                <label for="input-4">上传应用</label>
                <input
                  type="file"
                  class="form-control valid"
                  id="input-8"
                  name="file"
                  required=""
                  aria-invalid="false"
                  @change="onFileChange"
                  ref="fileInput"
                />
              </div>
              <br />
              <div class="form-group">
                <button
                  class="btn btn-primary shadow-primary px-5 col-lg-12"
                  @click="submit"
                >
                  <i class="icon-lock"></i>保存
                </button>
              </div>
            </form>
          </div>
        </div>
      </div>
    </div>
    <!-- End container-fluid-->
  </div>
</template>
<script scoped>
import axios from "axios";
export default {
  data() {
    return {
      file: null,
      url: "http://192.168.0.104:8086/application/upload",
      user: {},
      application: {
        name: "",
        author: "",
        status: true,
        category: "",
        keyword: "",
        permission: false,
        url: "",
        visibleDepartmentId: "",
        visibleUserId: "",
      },
      departmentvisible: false,
      uservisible: false,
      departments: [],
      users: [],
      maxFileSize: 10000 * 1024 * 1024, // 最大应用大小（10000 MB）
    };
  },
  mounted() {
    this.user = JSON.parse(localStorage.getItem("user"));
    this.application.author = this.user.id;
    this.getDepartments();
    this.getUsers();
  },
  methods: {
    checkFileSize(){
      if(this.file.size > this.maxFileSize){
        alert("单次上传应用大小不得超过10000MB.");
        return false;
      }else{
        return true;
      }
    },
    checkFileFormat() {
      // 定义允许的应用格式
      const allowedFormats = [
        "txt",
        "doc",
        "docx",
        "xls",
        "xlsx",
        "ppt",
        "pptx",
        "dwg",
        "dws",
        "dxf",
        "dwt",
        "dwf",
        "dwfx",
        "jpg",
        "jpeg",
        "jpe",
        "bmp",
        "gif",
        "png",
        "tif",
        "tiff",
        "pdf",
        "csv",
        "et",
        "ett"
      ];

      // 获取应用名并提取后缀名
      const fileName = this.file.name;
      const fileExtension = fileName
        .substring(fileName.lastIndexOf(".") + 1)
        .toLowerCase();

      // 检查后缀名是否在允许的格式列表中
      if (allowedFormats.includes(fileExtension)) {
        // 应用格式合法
        console.log("应用格式合法");
        return true;
      } else {
        // 应用格式不合法
        console.error("应用格式不符合要求");
        return false;
      }
    },
    updatepermission(event) {
      switch (event.target.value) {
        case "全部可见":
          this.departmentvisible = false;
          this.uservisible = false;
          this.application.permission = true;
          break;
        case "指定人可见":
          this.departmentvisible = false;
          this.uservisible = true;
          break;
        case "指定部门可见":
          this.departmentvisible = true;
          this.uservisible = false;
          break;
        case "仅自己可见":
          this.departmentvisible = false;
          this.uservisible = false;
          break;
      }
    },
    onFileChange(event) {
      this.file = event.target.files[0];
      if (this.file == null) {
        return;
      }
      //获取filename
      this.application.url = this.file.name;
    },
    updateVisibleDepartmentId(event) {
      const selectedOptions = Array.from(event.target.selectedOptions).map(
        (option) => option.value
      );
      this.application.visibleDepartmentId = selectedOptions.join(",");
    },
    updateVisibleUserId(event) {
      const selectedOptions = Array.from(event.target.selectedOptions).map(
        (option) => option.value
      );
      this.application.visibleUserId = selectedOptions.join(",");
    },
    getUsers() {
      axios.get("http://192.168.0.104:8086/user/").then((res) => {
        this.users = res.data;
      });
    },
    getDepartments() {
      axios.get("http://192.168.0.104:8086/department/").then((res) => {
        this.departments = res.data;
      });
    },
    submit(event) {
      event.preventDefault(); // 阻止默认的表单提交行为
      //判断表格是否填写完整
      if (
        this.application.name == "" ||
        this.application.category == "" ||
        this.application.keyword == "" ||
        this.file == null
      ) {
        alert("请填写完整的应用信息");
        return;
      }
      //判断应用是否符合规定格式
      //*.txt;*.doc;*.docx;*.xls;*.xlsx;*.ppt;*.pptx;*.dwg;*.dws;*.dxf;*.dwt;*.dwf;*.dwfx;*.jpg;*jpeg;*jpe;*.bmp;*.gif;*.png;*.tif;*.tiff;*.txt;*.pdf;*.csv
      if (this.checkFileFormat()) {
        alert("应用格式不符合要求,文档类请去文档模块上传");
        return;
      }
      if(!this.checkFileSize()){
        return;
      }

      let formData = new FormData();
      formData.append("file", this.file);
      try {
        axios
          .post("http://192.168.0.104:8086/application/upload/file", formData, {
            headers: {
              "Content-Type": "multipart/form-data", // axios 会自动补充 boundary
            },
          })
          .then((res) => {
            if (res.data) {
              axios
                .post("http://192.168.0.104:8086/application/", this.application)
                .then((res) => {
                  if (res.data) {
                    alert("上传成功");
                    (this.application = {
                      name: "",
                      author: this.user.id,
                      status: true,
                      category: "",
                      keyword: "",
                      permission: false,
                      url: "",
                      visibleDepartmentId: "",
                      visibleUserId: "",
                    }),
                      (this.departmentvisible = false);
                    this.uservisible = false;
                    this.file = null;
                    this.$refs.fileInput.value = ""; // 清空应用输入控件
                    this.$refs.permissionSelect.value = ""; //清空权限选择控件
                  } else {
                    alert("上传失败");
                  }
                });
            } else {
              alert("上传失败,上传的应用名称重复,请更改应用名");
            }
          });
      } catch (error) {
        console.error("File upload failed", error);
        alert("File upload failed");
      }
    },
  },
};
</script>
