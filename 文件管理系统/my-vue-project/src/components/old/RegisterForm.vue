<template>
    <div class="card-body">
        <div class="card-content p-2">
            <div class="text-center">
                <img src="http://127.0.0.1:8083/images/logo-icon.png" alt="logo icon">
            </div>
            <div class="card-title text-uppercase text-center py-3">文件协同共享管理系统</div>
            <form>
                <div class="form-group">
                    <label for="exampleInputName2" class="">姓名</label>
                    <div class="position-relative has-icon-right">
                        <input type="text" id="exampleInputName2" class="form-control input-shadow"
                            placeholder="输入你的姓名">
                        <div class="form-control-position">
                            <i class="icon-user"></i>
                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <label for="exampleInputEmailId2" class="">邮箱</label>
                    <div class="position-relative has-icon-right">
                        <input type="email" id="exampleInputEmailId2" class="form-control input-shadow"
                            placeholder="输入你的邮箱账号">
                        <div class="form-control-position">
                            <i class="icon-envelope-open"></i>
                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <label for="exampleInputPhoneId2" class="">手机号</label>
                    <div class="position-relative has-icon-right">
                        <input type="number" id="exampleInputPhoneId2" class="form-control input-shadow"
                            placeholder="输入你的手机号">
                        <div class="form-control-position">
                            <i class="fa fa-phone"></i>
                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <label for="exampleInputSex2" class="">性别</label>
                    <div class="position-relative has-icon-right">
                        <input type="text" id="exampleInputSex2" class="form-control input-shadow"
                            placeholder="输入你的性别(男/女)">
                        <div class="form-control-position">
                            <i class="icon-user"></i>
                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <label for="exampleInputPassword2" class="">密码</label>
                    <div class="position-relative has-icon-right">
                        <input type="password" id="exampleInputPassword2" class="form-control input-shadow"
                            placeholder="输入密码">
                        <div class="form-control-position">
                            <i class="icon-lock"></i>
                        </div>
                    </div>
                </div>

                <div class="form-group">
                    <div class="icheck-material-primary">
                        <input type="checkbox" id="user-checkbox2" checked="">
                        <label for="user-checkbox">我同意用户条款 &amp; 注册条款</label>
                    </div>
                </div>

                <button type="button"
                    class="btn btn-primary shadow-primary btn-block waves-effect waves-light">注册</button>



            </form>
        </div>
    </div>
    <div class="card-footer text-center py-3">
        <p class="text-muted mb-0">已有账户? <a @click="switchToLogin"> 在这里登录 </a></p>
    </div>
</template>
<script>
export default {
    name: 'RegisterForm',
    emits: ['switch-form'],
    methods: {
        switchToLogin() {
            this.$emit('switch-form');
        }
    },

}
</script>
<style scoped></style>