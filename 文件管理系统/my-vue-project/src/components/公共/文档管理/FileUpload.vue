<template>
    <div class="content-wrapper">
        <div class="container-fluid">
            <!-- Breadcrumb-->
            <div class="row pt-2 pb-2">
                <div class="col-sm-9">
                    <h4 class="page-title">上传文档</h4>
                    <ol class="breadcrumb">
                        <li class="breadcrumb-item">文档管理</li>
                        <li class="breadcrumb-item active" aria-current="page">上传文档</li>
                    </ol>
                </div>
            </div>
            <!-- End Breadcrumb-->
            <div class="row justify-content-center">
                <div class="card col-lg-12">
                    <div class="card-body">
                        <div class="card-title text-primary">文档资料</div>
                        <hr>
                        <form>
                            <div class="form-group">
                                <label>文档名称</label>
                                <input type="text" class="form-control" placeholder="" v-model="document.name">
                            </div>
                            <div class="form-group">
                                <label>文档类别</label>
                                <input type="text" class="form-control" placeholder="" v-model="document.type">
                            </div>
                            <div class="form-group">
                                <label for="input-3">文档关键字</label>
                                <input type="text" class="form-control" id="input-3" placeholder=""
                                    v-model="document.keyword">
                            </div>

                            <div class="form-group">
                                <label for="input-4">权限</label>
                                <select class="custom-select" id="inputGroupSelect03" v-model="document.permission">
                                    <option selected="">选择...</option>
                                    <option value="true">全部可见</option>
                                    <option value="true" @click="uservisible = true">指定人可见</option>
                                    <option value="true" @click="departmentvisible = true">指定部门可见</option>
                                    <option value="false">仅自己可见</option>
                                </select>
                                <select v-if="uservisible" class="custom-select" v-model="document.visible_user_id"
                                    multiple @change="updateVisibleUserId">
                                    <option selected="">选择...</option>
                                    <option v-for="user in users" :key="user.id" :value="user.id">
                                        {{ user.name }}
                                    </option>
                                </select>
                                <select v-if="departmentvisible" class="custom-select"
                                    v-model="document.visible_department_id" @change="updateVisibleDepartmentId"
                                    multiple>
                                    <option selected="">选择...</option>
                                    <option v-for="department in departments" :key="department.id"
                                        :value="department.id">
                                        {{ department.name }}
                                    </option>

                                </select>
                            </div>
                            <div class="form-group">
                                <label for="input-4">文档描述</label>
                                <textarea class="form-control" v-model="document.keyword"></textarea>
                            </div>
                            <div class="form-group">
                                <label for="input-4">上传文档</label>
                                <input type="file" class="form-control valid" id="input-8" name="file" required=""
                                    aria-invalid="false" @change="onFileChange">
                            </div>
                            <br>
                            <div class="form-group">
                                <button class="btn btn-primary shadow-primary px-5 col-lg-12" @click="submit"><i
                                        class="icon-lock"></i>保存</button>
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
import axios from 'axios';
export default {
    data() {
        return {
            file: null,
            url: 'http://localhost:8080/document/upload',
            user: {},
            document: {
                name: '',
                author: '',
                status: true,
                category: '',
                keyword: '',
                permission: true,
                description: '',
                file: '',
                visible_department_id: '',
                visible_user_id: ''
            },
            departmentvisible: false,
            uservisible: false,
            departments: [],
            users: []
        }
    },
    mounted() {
        this.user = JSON.parse(localStorage.getItem('user'));
        this.getDepartments();
        this.getUsers();

    },
    methods: {
        onFileChange(event) {
            this.file = event.target.files[0];
        },
        updateVisibleDepartmentId(event) {
            const selectedOptions = Array.from(event.target.selectedOptions).map(option => option.value);
            this.document.visible_department_id = selectedOptions.join(',');
        },
        updateVisibleUserId(event) {
            const selectedOptions = Array.from(event.target.selectedOptions).map(option => option.value);
            this.document.visible_user_id = selectedOptions.join(',');
        },
        getUsers() {
            axios.get('http://localhost:8086/user/').then(res => {
                this.users = res.data;
            })
        },
        getDepartments() {
            axios.get('http://localhost:8086/department/').then(res => {
                this.departments = res.data;
            })
        },
        async submit() {
            let formData = new FormData();
            formData.append('file', this.document.file);
            formData.append('document', new Blob([JSON.stringify(this.document)], { type: 'application/json' }));
            try {
                const response = await axios.post('http://localhost:8086/document/upload', formData, {
                    headers: {
                        'Content-Type': 'multipart/form-data',
                    },
                });
                if (response.data) {
                    alert('上传成功');
                }
            } catch (error) {
                console.error('File upload failed', error);
                alert('File upload failed');
            }

        }
    }
}
</script>