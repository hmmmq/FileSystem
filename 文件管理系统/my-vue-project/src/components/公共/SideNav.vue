<template>
    <div id="sidebar-wrapper" data-simplebar="init" data-simplebar-auto-hide="true">

        <div class="brand-logo">
            <a href="#">
                <img src="http://127.0.0.1:8083/images/logo-icon.png" class="logo-icon" alt="logo icon">

                <h5 class="logo-text" v-if="user?.type">管理员</h5>

                <h5 class="logo-text" v-else>普通用户</h5>


            </a>
        </div>
        <ul class="sidebar-menu do-nicescrol">
            <li class="sidebar-header">导航栏</li>
            <li>
                <a href="#" class="waves-effect" @click="selectOption('index')">
                    <i class="zmdi zmdi-view-dashboard"></i><span>首页</span> <i class="fa fa-angle-left pull-right"></i>
                </a>
                <!-- <ul class="sidebar-submenu">
                    <li><a href="#"><i class="zmdi zmdi-star-outline"></i>仪表盘</a></li>
                </ul> -->

            </li>
            <li>
                <a href="#" class="waves-effect">
                    <i class="zmdi zmdi-layers"></i>
                    <span>文档管理</span> <i class="fa fa-angle-left pull-right"></i>
                </a>
                <ul class="sidebar-submenu">
                    <li><a href="#" @click="selectOption('upload')"> <i class=" zmdi zmdi-star-outline"></i>
                            上传文档</a></li>
                    <li><a href="#" @click="selectOption('filelist')" v-if="!user?.type"><i
                                class="zmdi zmdi-star-outline"></i>
                            我的文档</a>
                        <a href="#" @click="selectOption('filelist')" v-else><i class="zmdi zmdi-star-outline"></i>
                            用户文档</a>
                    </li>

                </ul>

            </li>
            <li>
                <a href="#" class="waves-effect">
                    <i class="zmdi zmdi-gamepad"></i><span>日志管理</span>
                    <i class="fa fa-angle-left float-right"></i>
                </a>
                <ul class="sidebar-submenu">
                    <li><a href="#" @click="selectOption('viewrecordlist')"><i class="zmdi zmdi-star-outline"></i>
                            文档查看记录</a>
                    </li>
                    <li><a href="#" @click="selectOption('uploadrecordlist')"><i class="zmdi zmdi-star-outline"></i>
                            文档上传记录</a>
                    </li>
                    <li><a href="#" @click="selectOption('downloadrecordlist')"><i class="zmdi zmdi-star-outline"></i>
                            文档下载记录</a>
                    </li>



                </ul>

            </li>
            <!-- <li v-if="user?.type">
                <a href="#" class="waves-effect">
                    <i class="zmdi zmdi-chart"></i><span>角色管理</span>
                    <i class="fa fa-angle-left float-right"></i>
                </a>
                <ul class="sidebar-submenu">
                    <li><a href="#" @click="selectOption('rolelist')"><i class="zmdi zmdi-star-outline"></i> 查看角色</a>
                    </li>
                    <li><a href="#" @click="selectOption('addrole')"><i class="zmdi zmdi-star-outline"></i> 新增角色</a>
                    </li>


                    <li><a href="#" @click="selectOption('addroleuser')"><i class="zmdi zmdi-star-outline"></i>
                            分配用户角色</a></li>

                </ul>
            </li> -->
            <!-- <li v-if="user?.type">
                <a href="#" class="waves-effect">
                    <i class="zmdi zmdi-cloud-done"></i><span>权限管理</span><i class="fa fa-angle-left float-right"></i>
                </a>
                <ul class="sidebar-submenu">
                    <li><a href="#" @click="selectOption('permissionlist')"><i
                                class="zmdi zmdi-star-outline"></i>权限列表</a>
                    </li>
                </ul>

            </li> -->


            <li v-if="user?.type">
                <a href="#" class="waves-effect">
                    <i class="zmdi zmdi-chart"></i><span>用户管理</span>
                    <i class="fa fa-angle-left float-right"></i>
                </a>
                <ul class="sidebar-submenu">
                    <li><a href="#" @click="selectOption('userlist')"><i class="zmdi zmdi-star-outline"></i> 查看用户</a>
                    </li>
                    <li><a href="#" @click="selectOption('adduser')"><i class="zmdi zmdi-star-outline"></i> 新增用户</a>
                    </li>



                </ul>
            </li>
            <li v-if="user?.type">
                <a href="#" class="waves-effect">
                    <i class="zmdi zmdi-chart"></i><span>部门管理</span>
                    <i class="fa fa-angle-left float-right"></i>
                </a>
                <ul class="sidebar-submenu">
                    <li><a href="#" @click="selectOption('departmentlist')"><i class="zmdi zmdi-star-outline"></i>
                            查看部门</a></li>
                    <li><a href="#" @click="selectOption('adddepartment')"><i class="zmdi zmdi-star-outline"></i>
                            新增部门</a></li>
                </ul>
            </li>
            <!-- <li>
                <a href="#" class="waves-effect">
                    <i class="zmdi zmdi-card-travel"></i><span>应用管理</span>
                    <i class="fa fa-angle-left pull-right"></i>
                </a>
                <ul class="sidebar-submenu">
                    <li><a href="#" @click="selectOption('uploadapp')"><i class="zmdi zmdi-star-outline"></i>
                            上传应用</a>
                    </li>
                    <li><a href="#" @click="selectOption('downloadapp')"><i class="zmdi zmdi-star-outline"></i>
                            下载应用</a>
                    </li>
                    <li v-if="user?.type" @click="selectOption('deleteapp')"><a href="#"><i
                                class="zmdi zmdi-star-outline"></i> 删除应用</a>
                    </li>

                </ul>

            </li> -->
        </ul>

    </div>

</template>

<script scoped>

export default {
    components: {

    },
    data() {
        return {
            user: null
        }
    },
    mounted() {
        // 读取 localStorage
        const user = JSON.parse(localStorage.getItem('user'));
        console.log('SideNav Component: User from localStorage:', user);
        this.user = user;
        console.log('SideNav mounted');
        console.log('调用自定义的 sidebarMenu 方法');

    },
    methods: {

        selectOption(option) {
            this.$emit('select-option', option);
        },

    },
    beforeCreate() {
        console.log('SideNav beforeCreate');
    },
    created() {
        console.log('SideNav created');
    },
    beforeMount() {
        console.log('SideNav beforeMount');
    },

    beforeUpdate() {
        console.log('SideNav beforeUpdate');
    },
    updated() {
        console.log('SideNav updated');
    },
    beforeUnmount() {
        console.log('SideNav beforeUnmount');

    },
    unmounted() {
        console.log('SideNav unmounted');
    },


}
</script>

<style scoped></style>