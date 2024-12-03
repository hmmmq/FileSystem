<template>
    <div id="wrapper">

        <SideNav @select-option="handleOptionSelect" />
        <NavHeader @select-option="handleOptionSelect" />
        <WelcomePanel v-show="currentComponent === 'WelcomePanel'" />
        <FileUpload v-show="currentComponent === 'FileUpload'" />
        <FileList v-show="currentComponent === 'FileList'" />
        <AddUser v-show="currentComponent === 'AddUser'" />
        <UserList v-show="currentComponent === 'UserList'" />
        <DepartmentList v-show="currentComponent === 'DepartmentList'" />
        <AddDepartment v-show="currentComponent === 'AddDepartment'" />
        <PersonInfo v-show="currentComponent === 'PersonInfo'" />
        <RecordListUploadList v-show="currentComponent === 'RecordListUploadList'" />
        <RecordDownLoadList v-show="currentComponent === 'RecordDownLoadList'" />
        <RecordList v-show="currentComponent === 'RecordList'" />
        <AppUpload v-show="currentComponent === 'AppUpload'" />
        <AppList v-show="currentComponent === 'AppList'" />

      

    </div>

</template>
<script scoped>

import SideNav from '@/components/公共/SideNav.vue';
import NavHeader from '@/components/公共/NavHeader.vue';
import WelcomePanel from '@/components/公共/WelcomePanel.vue';
import FileUpload from '@/components/公共/文档管理/FileUpload.vue';
import FileList from '@/components/公共/文档管理/FileList.vue';
import UserList from '@/components/管理员/用户管理/UserList.vue';
import AddUser from '@/components/管理员/用户管理/AddUser.vue';
import DepartmentList from '@/components/管理员/部门管理/DepartmentList.vue';
import AddDepartment from '@/components/管理员/部门管理/AddDepartment.vue';
import PersonInfo from '@/components/公共/PersonInfo.vue';
import RecordListUploadList from '@/components/公共/日志管理/RecordListUploadList.vue';
import RecordDownLoadList from '@/components/公共/日志管理/RecordDownLoadList.vue';
import RecordList from '@/components/公共/日志管理/RecordList.vue';
import AppUpload from '@/components/管理员/应用管理/AppUpload.vue';
import AppList from '@/components/管理员/应用管理/AppList.vue';
export default {
    components: {
        SideNav,
        NavHeader,
        WelcomePanel,
        FileUpload,
        FileList,
        UserList,
        AddUser,
        DepartmentList,
        AddDepartment,
        PersonInfo,
        RecordListUploadList,
        RecordDownLoadList,
        RecordList,
        AppUpload,
        AppList

    },
    data() {
        return {
            user: {},
            currentComponent: 'WelcomePanel'
        }
    },
    name: 'UserPage',
    methods: {
        handleOptionSelect(option) {
            switch (option) {
                case 'index':
                    this.currentComponent = 'WelcomePanel';
                    break;
                case 'uploadapp':
                    this.currentComponent = 'AppUpload'; 
                    break;
                case 'applist':
                    this.currentComponent = 'AppList';
                    break;
                case 'upload':
                    this.currentComponent = 'FileUpload';
                    break;
                case 'filelist':
                    this.currentComponent = 'FileList';
                    break;
                case 'viewrecordlist':
                    this.currentComponent = 'RecordList';
                    break;
                case 'uploadrecordlist':
                    this.currentComponent = 'RecordListUploadList';
                    break;
                case 'downloadrecordlist':
                    this.currentComponent = 'RecordDownLoadList';
                    break;
                case 'personcentre':
                    this.currentComponent = 'PersonInfo';
                    break;
                case 'rolelist':
                    this.currentComponent = 'RoleList';
                    break;
                case 'addrole':
                    this.currentComponent = 'AddRole';
                    break;
                case 'addroleuser':
                    this.currentComponent = 'AddRoleUser';
                    break;
                case 'permissionlist':
                    this.currentComponent = 'PermissionList';
                    break;
                case 'userlist':
                    this.currentComponent = 'UserList';
                    break;
                case 'adduser':
                    this.currentComponent = 'AddUser';
                    break;
                case 'departmentlist':
                    this.currentComponent = 'DepartmentList';
                    break;
                case 'adddepartment':
                    this.currentComponent = 'AddDepartment';
                    break;
                case 'userdepartment':
                    this.currentComponent = 'UserDepartmentList';
                    break;
                case 'deleteapp':
                    this.currentComponent = 'DeleteAppList';
                    break;
                default:

            }
        }
    },
    mounted() {
        this.user = JSON.parse(localStorage.getItem('user'));

    },

    watch: {
        currentComponent(newVal, oldVal) {
            console.log('currentComponent changed from', oldVal, 'to', newVal);
        }
    }

}
</script>
<style scoped></style>