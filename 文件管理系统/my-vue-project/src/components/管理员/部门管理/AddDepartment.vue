<template>
    <div class="content-wrapper">
        <div class="container-fluid">
            <!-- Breadcrumb-->
            <div class="row pt-2 pb-2">
                <div class="col-sm-9">
                    <h4 class="page-title">添加部门</h4>
                    <ol class="breadcrumb">
                        <li class="breadcrumb-item">部门管理</li>
                        <li class="breadcrumb-item active" aria-current="page">添加部门</li>
                    </ol>
                </div>
            </div>
            <!-- End Breadcrumb-->
            <div class="row justify-content-center">
                <div class="card col-lg-12">
                    <div class="card-body">
                        <div class="card-title text-primary">添加部门</div>
                        <hr>
                        <form @submit.prevent="submit">
                            <div class="form-group">
                                <label>部门ID</label>
                                <input type="text" class="form-control" placeholder="" v-model="department.id">
                            </div>
                            <div class="form-group">
                                <label>部门名</label>
                                <input type="text" class="form-control" placeholder="" v-model="department.name">
                            </div>
                            <div class="form-group">
                                <label>部门人数</label>
                                <input type="number" class="form-control" placeholder="" v-model="department.amount">
                            </div>
                            <br>
                            <div class="form-group">
                                <button @click="add" class="btn btn-primary shadow-primary px-5 col-lg-12"><i
                                        class="icon-lock"></i>提交</button>
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
            // data
            department: {
                id: '',
                name: '',
                status: true,
                parentName: '',
                parentId: '',
                amount: '',
            },
            URL: 'http://192.168.0.101:8086/department/'
        }
    },
    methods: {

        checknullvalue() {
            if (this.department.id == '' || this.department.name == '' || this.department.amount == '') {
                alert('请填写完整信息');
                return false;
            }
            return true;
        },

        // methods
        add(event) {
            event.preventDefault(); // 阻止默认的表单提交行为
            var check = this.checknullvalue();

            if (!check) {
                return;
            }

            axios.post(this.URL, this.department).then(res => {

                if (res.data != '') {
                    alert('添加部门成功');

                } else {
                    alert('添加部门失败,请检查部门ID是否已存在');
                }
            }).catch(err => {
                console.error(err);
            });

        }
    }

}
</script>