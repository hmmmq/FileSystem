<template>
    <div class="content-wrapper">
        <div class="container-fluid">
            <!-- Breadcrumb-->
            <div class="row pt-2 pb-2">
                <div class="col-sm-12">
                    <h4 class="page-title">用户个人中心</h4>
                    <ol class="breadcrumb">
                        <li class="breadcrumb-item">用户</li>

                        <li class="breadcrumb-item active" aria-current="page">个人中心</li>
                    </ol>

                    <div class="card-body">
                        <div class="card-content p-2">
                            <div class="text-center">
                                <img src="http://192.168.0.104:8083/images/logo-icon.png" alt="logo icon">
                            </div>
                            <div class="card-title text-uppercase text-center py-3">用户个人中心</div>
                            <form>
                                <div class="form-group">
                                    <label for="exampleInputName" class="">姓名</label>
                                    <div class="position-relative has-icon-right">
                                        <label class="" v-show="!change">{{ user?.username }}</label>
                                        <input type="text" id="exampleInputName" class="form-control input-shadow"
                                            placeholder="输入你的姓名" v-show="change" v-if="user" v-model="user.username">
                                        <div class="form-control-position">
                                            <i class="icon-user"></i>
                                        </div>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label>部门</label>
                                    <select class="custom-select" v-if="user" v-model="user.departmentId"
                                        @change="updateDepartmentId" v-show="change">
                                        <option v-for="department in departments" :key="department.id"
                                            :value="department.id">
                                            {{ department.name }}
                                        </option>
                                    </select>
                                    <div v-show="change">已选择部门:</div>
                                    <div v-if="user">{{ user.departmentId }}{{ user.departmentName }}</div>
                                </div>
                                <div class="form-group">
                                    <label for="exampleInputemailId" class="">邮箱</label>
                                    <div class="position-relative has-icon-right">
                                        <label class="" v-show="!change">{{ user?.email }}</label>
                                        <input type="email" id="exampleInputemailId" class="form-control input-shadow"
                                            placeholder="输入你的邮箱账号" v-show="change" v-if="user" v-model="user.email">
                                        <div class="form-control-position">
                                            <i class="icon-envelope-open"></i>
                                        </div>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="exampleInputPhoneId" class="">手机号</label>
                                    <div class="position-relative has-icon-right">
                                        <label class="" v-show="!change">{{ user?.phone }}</label>
                                        <input type="number" id="exampleInputPhoneId" class="form-control input-shadow"
                                            placeholder="输入你的手机号" v-show="change" v-if="user" v-model="user.phone">
                                        <div class="form-control-position">
                                            <i class="fa fa-phone"></i>
                                        </div>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="exampleInputSex" class="">性别</label>
                                    <div class="position-relative has-icon-right">
                                        <label class="" v-show="!change">{{ user?.gender }}</label>
                                        <input type="text" id="exampleInputSex" class="form-control input-shadow"
                                            placeholder="输入你的性别(男/女)" v-show="change" v-if="user" v-model="user.gender">
                                        <div class="form-control-position">
                                            <i class="icon-user"></i>
                                        </div>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="exampleInputpassword" class="">密码</label>
                                    <div class="position-relative has-icon-right">
                                        <label class="" v-show="!change">{{ user?.password }}</label>
                                        <input type="password" id="exampleInputpassword"
                                            class="form-control input-shadow" placeholder="输入密码" v-show="change"
                                            v-if="user" v-model="user.password">
                                        <div class="form-control-position">
                                            <i class="icon-lock"></i>
                                        </div>
                                    </div>
                                </div>


                                <button type="button"
                                    class="btn btn-primary shadow-primary btn-block waves-effect waves-light"
                                    v-show="!change" @click="change = true">修改个人信息</button>
                                <button type="button"
                                    class="btn btn-primary shadow-primary btn-block waves-effect waves-light"
                                    v-show="change" @click="submit">保存</button>
                            </form>
                        </div>
                    </div>
                    <div class="card-footer text-center py-3">
                        <p class="text-muted mb-0"> <a @click="Logout"> 退出登录 </a></p>
                    </div>
                </div>



            </div>
        </div>
    </div>




</template>
<script scoped>
import axios from 'axios';
export default {
    name: 'PersonInfo',
    methods: {
        updateDepartmentId() {
            this.user.departmentName = this.departments.find(department => department.id == this.user.departmentId).name;
        },
        getDepartments() {
            axios.get('http://192.168.0.104:8086/department/').then(res => {
                this.departments = res.data;
            });
        },
        switchToLogin() {
            this.$emit('switch-form', true);
        },
        Logout() {
            localStorage.removeItem('user');
            this.$router.push('/login');
        },
        submit() {
            //判空
            if (!this.user.username || !this.user.email || !this.user.phone || !this.user.gender || !this.user.password) {
                alert('请填写完整信息');
                return;
            }
            axios.put('http://192.168.0.104:8086/user/' + this.user.id, this.user).then(res => {
                if (res.data) {
                    alert('修改成功');
                    localStorage.setItem('user', JSON.stringify(this.user));
                } else {
                    alert('修改失败');
                }
            });
            this.change = false;

        }
    },
    data() {
        return {
            user: null,
            change: false,
            departments: []
        }
    },
    mounted() {
        this.user = JSON.parse(localStorage.getItem('user'));
        this.getDepartments();

    }

}
</script>
<style scoped></style>