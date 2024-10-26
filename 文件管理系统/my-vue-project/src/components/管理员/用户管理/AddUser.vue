<template>
    <div class="content-wrapper">
        <div class="container-fluid">
            <!-- Breadcrumb-->
            <div class="row pt-2 pb-2">
                <div class="col-sm-9">
                    <h4 class="page-title">新增用户</h4>
                    <ol class="breadcrumb">
                        <li class="breadcrumb-item">用户管理</li>
                        <li class="breadcrumb-item active" aria-current="page">新增用户</li>
                    </ol>
                </div>
            </div>
            <!-- End Breadcrumb-->
            <div class="row justify-content-center">
                <div class="card col-lg-12">
                    <div class="card-body">
                        <div class="card-title text-primary">新增用户</div>
                        <hr>
                        <form>
                            <div class="form-group">
                                <label>用户ID(作为登录账号使用)</label>
                                <input type="text" class="form-control" placeholder="请填写用户ID" v-model="user.id">
                            </div>
                            <div class="form-group">
                                <label>用户密码</label>
                                <input type="text" class="form-control" placeholder="填写用户密码" v-model="user.password">
                            </div>
                            <div class="form-group">
                                <label>用户姓名</label>
                                <input type="text" class="form-control" placeholder="填写用户姓名" v-model="user.username">
                            </div>
                            <div class="form-group">
                                <label>用户手机号</label>
                                <input type="number" class="form-control" placeholder="填写用户手机号" v-model="user.phone">
                            </div>
                            <div class="form-group">
                                <label>是否设为管理员</label>
                                <select class="form-control" v-model="user.type">
                                    <option value="true">是</option>
                                    <option value="false">否</option>
                                </select>
                            </div>
                            <div class="form-group">
                                <label>用户邮箱</label>
                                <input type="text" class="form-control" placeholder="填写用户邮箱" v-model="user.email">
                            </div>
                            <label>用户性别</label>
                            <select class="form-control" v-model="user.gender">
                                <option value="男">男</option>
                                <option value="女">女</option>
                            </select>
                            <br>
                            <div class="form-group">
                                <button type="button" @click="register"
                                    class="btn btn-primary shadow-primary px-5 col-lg-12">
                                    <i class="icon-lock"></i>提交</button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>


        </div>
        <!-- End container-fluid-->

    </div>
</template>
<script>
import axios from 'axios';
export default {
    data() {
        return {
            // data
            user: {
                id: '',
                password: '123456',
                username: '',
                phone: '',
                email: '',
                gender: '',
                creator: '管理员',
                userType: false
            },
            URL: 'http://localhost:8086/user/'
        }
    },
    methods: {
        checknullvalue() {
            if (this.user.id == '' || this.user.password == '' || this.user.username == '' || this.user.phone == '') {
                alert('请填写完整信息');
                return false;
            }
            return true;
        },
        // methods
        register() {
            var check = this.checknullvalue();
            if (!check) {
                return;
            }
            // register
            // console.log('register');
            // console.log(this.user);
            axios.post(this.URL, this.user).then(res => {
                // console.log(res);
                if (res.data != '') {
                    // console.log(res.data);
                    // this.$router.push('/user/list');
                    alert('新增用户成功');
                } else {
                    alert('新增用户失败,请检查用户ID是否已存在');
                }
            }).catch(err => {
                console.error(err);
            });

        }
    }
}
</script>