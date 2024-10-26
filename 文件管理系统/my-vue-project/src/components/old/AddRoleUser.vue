<template>
    <div class="content-wrapper">
        <div class="container-fluid">
            <!-- Breadcrumb-->
            <div class="row pt-2 pb-2">
                <div class="col-sm-9">
                    <h4 class="page-title" v-show="!user?.UserType">角色列表</h4>

                    <ol class="breadcrumb">
                        <li class="breadcrumb-item">角色管理</li>
                        <li class="breadcrumb-item active" aria-current="page">角色列表</li>
                    </ol>
                </div>

            </div>
            <!-- End Breadcrumb-->


            <div class="row">
                <div class="col-lg-12">
                    <div class="card">
                        <div class="card-header"><i class="fa fa-table"></i> 角色列表</div>

                        <div class="card-body">

                            <div class="table-responsive">
                                <table id="example7" class="table table-bordered">
                                    <thead>
                                        <tr>
                                            <th>用户ID</th>
                                            <th>用户名</th>
                                            <th>用户手机号</th>
                                            <th>现有角色</th>
                                            <th>操作</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <tr>
                                            <td>1</td>
                                            <td>小明</td>
                                            <td>128312312</td>
                                            <td>普通职工,文员</td>
                                            <td>分配角色/取消分配</td>
                                        </tr>
                                        <tr>
                                            <td>2</td>
                                            <td>小红</td>
                                            <td>128311222312</td>
                                            <td>部门主管</td>
                                            <td>分配角色/取消分配</td>
                                        </tr>


                                    </tbody>
                                    <tfoot>
                                        <tr>
                                            <th>用户ID</th>
                                            <th>用户名</th>
                                            <th>用户手机号</th>
                                            <th>现有角色</th>
                                            <th>操作</th>
                                        </tr>
                                    </tfoot>
                                </table>
                            </div>
                        </div>
                    </div>
                </div>
            </div><!-- End Row-->

        </div>
        <!-- End container-fluid-->

    </div>
</template>
<script scoped>
import $ from 'jquery';
import 'datatables.net-bs4';
import 'datatables.net-buttons-bs4';
import 'datatables.net-buttons/js/buttons.html5';
import 'datatables.net-buttons/js/buttons.print';
import 'datatables.net-buttons/js/buttons.colVis';
import 'jszip';
import pdfMake from 'pdfmake-support-chinese-fonts/pdfmake.min';

import pdfFonts from 'pdfmake-support-chinese-fonts/vfs_fonts';


pdfMake.vfs = pdfFonts.pdfMake.vfs;
pdfMake.fonts = {
    Roboto: {
        normal: 'Roboto-Regular.ttf',
        bold: 'Roboto-Regular.ttf',
        italics: 'Roboto-Regular.ttf',
        bolditalics: 'Roboto-Regular.ttf'
    },
    fangzhen: {
        normal: 'fzhei-jt.TTF',
        bold: 'fzhei-jt.TTF',
        italics: 'fzhei-jt.TTF',
        bolditalics: 'fzhei-jt.TTF'
    }
};

export default {
    data() {
        return {
            once: true,
            user: null
        }
    },
    mounted() {
        this.user = JSON.parse(localStorage.getItem('user'));
        console.log('User get from localStorage in PersonInfo:', this.user);
        if (this.once) {
            var table = $('#example7').DataTable({
                dom: '<"top"l<"row"<"col-sm-6 text-left"f><"col-sm-6 text-right"B>>rt<"bottom"ip><"clear">',
                buttons: [
                    'copy', 'csv', 'excel', {
                        extend: 'pdfHtml5',
                        text: 'PDF',
                        customize: function (doc) {
                            doc.defaultStyle = {
                                font: 'fangzhen' // 使用支持中文的字体
                            };
                        }
                    }, 'print', 'colvis'
                ]
            });
            table
                .buttons()
                .container()
                .appendTo("#example_wrapper .col-md-6:eq(0)");
            this.once = false;
            // 为所有按钮元素添加样式
            const buttons = this.$el.querySelectorAll('.table-responsive button');
            buttons.forEach(button => {
                button.classList.add('btn', 'btn-outline-primary', 'buttons-copy', 'buttons-html5');
            });

            // 使用 jQuery 选择所有具有 .dt-search 类的元素
            $('.dt-search').css('text-align', 'left');
            this.$el.querySelector('.dt-length').classList.add('col-md-1');
            $('.dt-length').css('padding-left', '0');
            $('.text-right').css('padding-top', '20px');


        }

    },
    beforeUnmount() {

        // 可以在这里执行清理操作
    },
    unmounted() {

    }

}
</script>
<style scoped>
/* 样式 */
.dt-search {
    text-align: left;
}
</style>
