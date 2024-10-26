<template>
    <div id="wrapper">

        <SideNav @select-option="handleOptionSelect" />
        <NavHeader @select-option="handleOptionSelect" />
        <WelcomePanel v-show="currentComponent === 'WelcomePanel'" />
        <FileUpload v-show="currentComponent === 'FileUpload'" />

        <!-- <FileUpload v-show="currentComponent === 'FileUpload'" />
        <FileList v-show="currentComponent === 'FileList'" />
        <RecordList v-show="currentComponent === 'RecordList'" />
        <RecordListUploadList v-show="currentComponent === 'RecordListUploadList'" />
        <RecordListDownLoadList v-show="currentComponent === 'RecordListDownLoadList'" />
        <AppUpload v-show="currentComponent === 'AppUpload'" />
        <AppDownload v-show="currentComponent === 'AppDownload'" />
        <PersonInfo v-show="currentComponent === 'PersonInfo'" />
        <RoleList v-show="currentComponent === 'RoleList'" />
        <AddRole v-show="currentComponent === 'AddRole'" />
        <AddRoleUser v-show="currentComponent === 'AddRoleUser'" />
        <PermissionList v-show="currentComponent === 'PermissionList'" />
        <UserList v-show="currentComponent === 'UserList'" />
        <AddUser v-show="currentComponent === 'AddUser'" />
        <DepartmentList v-show="currentComponent === 'DepartmentList'" />
        <AddDepartment v-show="currentComponent === 'AddDepartment'" />
        <UserDepartmentList v-show="currentComponent === 'UserDepartmentList'" />
        <DeleteAppList v-show="currentComponent === 'DeleteAppList'" /> -->

    </div>

</template>
<script>

import SideNav from '@/components/公共/SideNav.vue';
import NavHeader from '@/components/公共/NavHeader.vue';
import WelcomePanel from '@/components/公共/WelcomePanel.vue';
import FileUpload from '@/components/公共/文档管理/FileUpload.vue';
export default {
    components: {
        SideNav,
        NavHeader,
        WelcomePanel,
        FileUpload,
        // FileList,
        // RecordList,
        // RecordListUploadList,
        // RecordListDownLoadList,
        // AppUpload,
        // AppDownload,
        // PersonInfo,
        // RoleList,
        // AddRole,
        // AddRoleUser,
        // PermissionList,
        // UserList,
        // AddUser,
        // DepartmentList,
        // AddDepartment,
        // UserDepartmentList,
        // DeleteAppList,

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
                    this.currentComponent = 'RecordListDownLoadList';
                    break;
                case 'uploadapp':
                    this.currentComponent = 'AppUpload';
                    break;
                case 'downloadapp':
                    this.currentComponent = 'AppDownload';
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