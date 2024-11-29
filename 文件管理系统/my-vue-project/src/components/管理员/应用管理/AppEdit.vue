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
            <button type="button" class="btn btn-outline-info waves-effect waves-light m-1"
                @click="cancel">取消修改</button>

            <!-- End Breadcrumb-->
            <div class="row justify-content-center">
                <div class="card col-lg-12">
                    <div class="card-body">
                        <div class="card-title text-primary">应用资料</div>
                        <hr>
                        <form @submit.prevent="submit">
                            <div class="form-group">
                                <label>应用名称</label>
                                <input type="text" class="form-control" placeholder="" v-model="application.name">
                            </div>
                            <div class="form-group">
                                <label>应用类别</label>
                                <input type="text" class="form-control" placeholder="" v-model="application.category">
                            </div>
                            <div class="form-group">
                                <label for="input-3">应用关键字</label>
                                <input type="text" class="form-control" id="input-3" placeholder=""
                                    v-model="application.keyword">
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
                                    v-model="application.visibleDepartmentId" disabled>
                                <label for="input-4">当前用户可见列表</label>
                                <input type="text" class="form-control" placeholder="" v-model="application.visibleUserId"
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
                                <div>{{ application.visibleUserId }}</div>
                            </div>

                            <div class="form-group" v-if="departmentvisible">
                                <label>部门列表</label>
                                <select class="custom-select" v-model="application.visibleDepartmentId"
                                    @change="updateVisibleDepartmentId" multiple>
                                    <option v-for="department in departments" :key="department.id"
                                        :value="department.id">
                                        {{ department.name }}
                                    </option>
                                </select>
                                <div>已选择部门:</div>
                                <div>{{ application.visibleDepartmentId }}</div>
                            </div>


                            <div class="form-group">
                                <label for="input-4">已经上传的应用名</label>
                                <input type="text" class="form-control" placeholder="" v-model="application.url" disabled>
                            </div>
                            <div class="form-group">
                                <label for="input-4">上传更新应用(如有)</label>
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
        initialapplication: {
            type: Object,
            required: true
        }
    },
    data() {
        return {
            file: null,
            url: 'http://127.0.0.1:8086/application/upload',
            user: {},
            application: {
                id: this.initialapplication.id || '',
                name: this.initialapplication.name || '',
                author: '',
                status: true,
                category: this.initialapplication.category || '',
                keyword: this.initialapplication.keyword || '',
                permission: false,
                url: this.initialapplication.url || '',
                visibleDepartmentId: this.initialapplication.visibleDepartmentId || '',
                visibleUserId: this.initialapplication.visibleUserId || ''
            },
            departmentvisible: false,
            uservisible: false,
            departments: [],
            users: []
        }
    },
    mounted() {
        this.user = JSON.parse(localStorage.getItem('user'));
        this.application.author = this.user.id;
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
            this.application.permission = (value === '全部可见');
        },
        onFileChange(event) {
            this.file = event.target.files[0];
            if (this.file) {
                this.application.url = this.file.name;
            }
        },
        updateVisibleDepartmentId(event) {
            const selectedOptions = Array.from(event.target.selectedOptions).map(option => option.value);
            this.application.visibleDepartmentId = selectedOptions.join(',');
        },
        updateVisibleUserId(event) {
            const selectedOptions = Array.from(event.target.selectedOptions).map(option => option.value);
            this.application.visibleUserId = selectedOptions.join(',');
        },
        getUsers() {
            axios.get('http://127.0.0.1:8086/user/').then(res => {
                this.users = res.data;
            });
        },
        getDepartments() {
            axios.get('http://127.0.0.1:8086/department/').then(res => {
                this.departments = res.data;
            });
        },
        submit() {

            if (!this.validateForm()) {
                alert('请填写完整的应用信息');
                return;
            }

            if (this.file != null) {
                let formData = new FormData();
                formData.append('file', this.file);
                axios.post('http://127.0.0.1:8086/application/upload/file', formData)
                    .then(res => {
                        if (res.data) {
                            return axios.put('http://127.0.0.1:8086/application/' + this.user.id + '/' + this.application.id, this.application);
                        } else {
                            throw new Error('上传的应用名称重复,请更改应用名');
                        }
                    })
                    .then(res => {
                        alert('更新成功');
                        console.log(res.data);
                        this.resetForm();
                        this.$emit('data-back', true);

                    })
                    .catch(error => {
                        alert(error.message || '更新失败,你无权更改此应用');
                    });
            } else {
                axios.put('http://127.0.0.1:8086/application/' + this.user.id + '/' + this.application.id, this.application)
                    .then(res => {
                        if (res.data) {
                            alert('更新成功');
                        } else {
                            throw new Error('更新失败,你无权更改此应用');
                        }

                    })
                    .catch(error => {
                        alert(error.message || '更新失败,你无权更改此应用');
                    })
                    .finally(() => {
                        this.resetForm();
                        this.$emit('data-back', true);
                    });
            }
        },
        validateForm() {
            return this.application.name && this.application.category && this.application.keyword;
        },
        resetForm() {
            this.application = {
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
