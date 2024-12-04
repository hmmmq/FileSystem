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
            <button type="button" class="btn btn-outline-info waves-effect waves-light m-1"
                @click="cancel">取消修改</button>

            <!-- End Breadcrumb-->
            <div class="row justify-content-center">
                <div class="card col-lg-12">
                    <div class="card-body">
                        <div class="card-title text-primary">文档资料</div>
                        <hr>
                        <form @submit.prevent="submit">
                            <div class="form-group">
                                <label>文档名称</label>
                                <input type="text" class="form-control" placeholder="" v-model="document.name">
                            </div>
                            <div class="form-group">
                                <label>文档类别</label>
                                <input type="text" class="form-control" placeholder="" v-model="document.category">
                            </div>
                            <div class="form-group">
                                <label for="input-3">文档关键字</label>
                                <input type="text" class="form-control" id="input-3" placeholder=""
                                    v-model="document.keyword">
                            </div>

                            <div class="form-group">
                                <label for="input-4">修改权限(如有)</label>
                                <select class="custom-select" @change="updatePermission" ref="permissionSelect">
                                    <option selected="">选择...</option>
                                    <option value="全部可见">全部可见</option>
                                    <option value="指定人可见">指定人可见</option>
                                    <option value="指定部门可见">指定部门可见</option>
                                    <option value="仅自己可见">仅自己可见</option>
                                </select>
                                <br>
                                <label for="input-4">当前部门可见列表</label>
                                <input type="text" class="form-control" placeholder=""
                                    v-model="document.visibleDepartmentId" disabled>
                                <label for="input-4">当前用户可见列表</label>
                                <input type="text" class="form-control" placeholder="" v-model="document.visibleUserId"
                                    disabled>

                            </div>

                            <div class="form-group" v-if="uservisible">
                                <label>用户列表</label>
                                <select class="custom-select" @change="updateVisibleUserId" multiple>
                                    <option v-for="user in users" :key="user.id" :value="user.id">
                                        {{ user.username }}
                                    </option>
                                </select>
                                <div>已选择用户:</div>
                                <div>{{ document.visibleUserId }}</div>
                            </div>

                            <div class="form-group" v-if="departmentvisible">
                                <label>部门列表</label>
                                <select class="custom-select" v-model="document.visibleDepartmentId"
                                    @change="updateVisibleDepartmentId" multiple>
                                    <option v-for="department in departments" :key="department.id"
                                        :value="department.id">
                                        {{ department.name }}
                                    </option>
                                </select>
                                <div>已选择部门:</div>
                                <div>{{ document.visibleDepartmentId }}</div>
                            </div>


                            <div class="form-group">
                                <label for="input-4">已经上传的文件名</label>
                                <input type="text" class="form-control" placeholder="" v-model="document.url" disabled>
                            </div>
                            <div class="form-group">
                                <label for="input-4">上传更新文档(如有)</label>
                                <input type="file" class="form-control valid" id="input-8" name="file"
                                    aria-invalid="false" @change="onFileChange" ref="fileInput">
                            </div>
                            <br>
                            <div class="form-group">
                                <button class="btn btn-primary shadow-primary px-5 col-lg-12" type="submit"><i
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
    props: {
        initialDocument: {
            type: Object,
            required: true
        }
    },
    data() {
        return {
            file: null,
            url: 'http://192.168.0.104:8086/document/upload',
            user: {},
            document: {
                id: this.initialDocument.id || '',
                name: this.initialDocument.name || '',
                author: '',
                status: true,
                category: this.initialDocument.category || '',
                keyword: this.initialDocument.keyword || '',
                permission: false,
                url: this.initialDocument.url || '',
                visibleDepartmentId: this.initialDocument.visibleDepartmentId || '',
                visibleUserId: this.initialDocument.visibleUserId || ''
            },
            departmentvisible: false,
            uservisible: false,
            departments: [],
            users: []
        }
    },
    mounted() {
        this.user = JSON.parse(localStorage.getItem('user'));
        this.document.author = this.user.id;
        this.getDepartments();
        this.getUsers();
    },
    methods: {
        cancel() {
            this.$emit('data-back', true);
        },
        updatePermission(event) {
            const value = event.target.value;
            this.departmentvisible = value === '指定部门可见';
            this.uservisible = value === '指定人可见';
            this.document.permission = (value === '全部可见');
        },
        onFileChange(event) {
            this.file = event.target.files[0];
            if (this.file) {
                this.document.url = this.file.name;
            }
        },
        updateVisibleDepartmentId(event) {
            const selectedOptions = Array.from(event.target.selectedOptions).map(option => option.value);
            this.document.visibleDepartmentId = selectedOptions.join(',');
        },
        updateVisibleUserId(event) {
            const selectedOptions = Array.from(event.target.selectedOptions).map(option => option.value);
            this.document.visibleUserId = selectedOptions.join(',');
        },
        getUsers() {
            axios.get('http://192.168.0.104:8086/user/').then(res => {
                this.users = res.data;
            });
        },
        getDepartments() {
            axios.get('http://192.168.0.104:8086/department/').then(res => {
                this.departments = res.data;
            });
        },
        submit() {

            if (!this.validateForm()) {
                alert('请填写完整的文档信息');
                return;
            }

            if (this.file != null) {
                let formData = new FormData();
                formData.append('file', this.file);
                axios.post('http://192.168.0.104:8086/document/upload/file', formData)
                    .then(res => {
                        if (res.data) {
                            return axios.put('http://192.168.0.104:8086/document/' + this.user.id + '/' + this.document.id, this.document);
                        } else {
                            throw new Error('上传的文件名称重复,请更改文件名');
                        }
                    })
                    .then(res => {
                        alert('更新成功');
                        console.log(res.data);
                        this.resetForm();
                        this.$emit('data-back', true);

                    })
                    .catch(error => {
                        alert(error.message || '更新失败,你无权更改此文档');
                    });
            } else {
                axios.put('http://192.168.0.104:8086/document/' + this.user.id + '/' + this.document.id, this.document)
                    .then(res => {
                        if (res.data) {
                            alert('更新成功');
                        } else {
                            throw new Error('更新失败,你无权更改此文档');
                        }

                    })
                    .catch(error => {
                        alert(error.message || '更新失败,你无权更改此文档');
                    })
                    .finally(() => {
                        this.resetForm();
                        this.$emit('data-back', true);
                    });
            }
        },
        validateForm() {
            return this.document.name && this.document.category && this.document.keyword;
        },
        resetForm() {
            this.document = {
                id: '',
                name: '',
                author: this.user.id,
                status: true,
                category: '',
                keyword: '',
                permission: false,
                url: '',
                visibleDepartmentId: [],
                visibleUserId: []
            };
            this.file = null;
            this.$refs.fileInput.value = '';
            this.departmentvisible = false;
            this.uservisible = false;
        }
    }
}
</script>
