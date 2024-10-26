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
                                <img src="http://127.0.0.1:8083/images/logo-icon.png" alt="logo icon">
                            </div>
                            <div class="card-title text-uppercase text-center py-3">用户个人中心</div>
                            <form>
                                <div class="form-group">
                                    <label for="exampleInputName" class="">姓名</label>
                                    <div class="position-relative has-icon-right">
                                        <label class="" v-show="!change">{{ user?.UserName }}</label>
                                        <input type="text" id="exampleInputName" class="form-control input-shadow"
                                            placeholder="输入你的姓名" v-show="change" v-if="user" v-model="user.UserName">
                                        <div class="form-control-position">
                                            <i class="icon-user"></i>
                                        </div>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="exampleInputName" class="">部门</label>
                                    <div class="position-relative has-icon-right">
                                        <label class="" v-show="!change"> {{ user?.DepartmentName }} </label>
                                        <input type="text" id="exampleInputName" class="form-control input-shadow"
                                            placeholder="输入你的部门" v-show="change" v-if="user"
                                            v-model="user.DepartmentName">
                                        <div class="form-control-position">
                                            <i class="icon-user"></i>
                                        </div>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="exampleInputEmailId" class="">邮箱</label>
                                    <div class="position-relative has-icon-right">
                                        <label class="" v-show="!change">{{ user?.Email }}</label>
                                        <input type="email" id="exampleInputEmailId" class="form-control input-shadow"
                                            placeholder="输入你的邮箱账号" v-show="change" v-if="user" v-model="user.Email">
                                        <div class="form-control-position">
                                            <i class="icon-envelope-open"></i>
                                        </div>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="exampleInputPhoneId" class="">手机号</label>
                                    <div class="position-relative has-icon-right">
                                        <label class="" v-show="!change">{{ user?.PhoneNumber }}</label>
                                        <input type="number" id="exampleInputPhoneId" class="form-control input-shadow"
                                            placeholder="输入你的手机号" v-show="change" v-if="user"
                                            v-model="user.PhoneNumber">
                                        <div class="form-control-position">
                                            <i class="fa fa-phone"></i>
                                        </div>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="exampleInputSex" class="">性别</label>
                                    <div class="position-relative has-icon-right">
                                        <label class="" v-show="!change">{{ user?.Gender }}</label>
                                        <input type="text" id="exampleInputSex" class="form-control input-shadow"
                                            placeholder="输入你的性别(男/女)" v-show="change" v-if="user" v-model="user.Gender">
                                        <div class="form-control-position">
                                            <i class="icon-user"></i>
                                        </div>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="exampleInputPassword" class="">密码</label>
                                    <div class="position-relative has-icon-right">
                                        <label class="" v-show="!change">{{ user?.Password }}</label>
                                        <input type="password" id="exampleInputPassword"
                                            class="form-control input-shadow" placeholder="输入密码" v-show="change"
                                            v-if="user" v-model="user.Password">
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
                                    v-show="change" @click="change = false">保存</button>
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
<script>
export default {
    name: 'PersonInfo',
    methods: {
        switchToLogin() {
            this.$emit('switch-form', true);
        },
        Logout() {
            localStorage.removeItem('user');
            this.$router.push('/login');
        }
    },
    data() {
        return {
            user: null,
            change: false
        }
    },
    mounted() {
        this.user = JSON.parse(localStorage.getItem('user'));
        console.log('User get from localStorage in PersonInfo:', this.user);
    }

}
</script>
<style scoped></style>