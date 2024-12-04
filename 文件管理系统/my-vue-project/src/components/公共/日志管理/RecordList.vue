<template>
    <div class="content-wrapper">
        <div class="container-fluid">
            <!-- Breadcrumb-->
            <div class="row pt-2 pb-2">
                <div class="col-sm-9">
                    <h4 class="page-title" v-show="!user?.type">我的文档查看记录</h4>
                    <h4 class="page-title" v-show="user?.type">用户文档查看记录</h4>
                    <ol class="breadcrumb">
                        <li class="breadcrumb-item">文档查看记录管理</li>
                        <li class="breadcrumb-item active" aria-current="page" v-show="!user?.type">我的文档查看记录</li>
                        <li class="breadcrumb-item active" aria-current="page" v-show="user?.type">用户文档查看记录</li>
                    </ol>
                </div>
            </div>
            <button type="button" class="btn btn-outline-info waves-effect waves-light m-1"
                @click="updatelist">刷新列表</button>


            <!-- End Breadcrumb-->
            <div class="row">
                <div class="col-lg-12">
                    <div class="card">
                        <div class="card-header"><i class="fa fa-table"></i> 文档查看记录列表</div>

                        <div class="card-body">

                            <div class="table-responsive">
                                <table id="example-documentviewtime" class="table table-bordered">

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
import axios from 'axios';
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
    components: {
    },
    data() {
        return {
            user: null,
            documentviewtimelist: [], // 初始化为空数组
            update: false,
            initialdocumentviewtime: {
                id: '',
                name: '',
                category: '',
                keyword: '',
                url: '',
                permission: false,
                visibleDepartmentId: [],
                visibleUserId: []
            }
        }
    },

    watch: {
    },
    methods: {
     
     
        async updatelist() {
            await this.destoryDataTable();
            this.user = JSON.parse(localStorage.getItem('user'));
            var url = '';
            if (this.user.type) {
                url = 'http://192.168.0.104:8086/documentviewtime/';
            }
            else {
                url = 'http://192.168.0.104:8086/documentviewtime/user/' + this.user.id;
            }

            this.initializeDataTable(url);

        },
   
        convertTo2DArray(data) {
            return data.map((item, index) => [
                index + 1, // 添加索引列
                item.id,
                item.documentId,
                item.documentName,
                item.userId,
                item.userName,
                item.viewTime,
              
            ]);
        },
        async initializeDataTable(url) {
            console.log("initializeDataTable");
            var documentviewtimelist2d = null;
            try {
                if (!$.fn.DataTable.isDataTable('#example-documentviewtime')) {


                    try {
                        this.user = JSON.parse(localStorage.getItem('user'));
                        const promise = await axios.get(url);
                        if (promise.status === 200) {
                            console.log(promise.data);
                            documentviewtimelist2d = this.convertTo2DArray(promise.data);
                        } else {
                            console.log(promise);
                            return;
                        }
                    } catch (err) {
                        console.error(err);
                        return;
                    }

                    this.$nextTick(() => {
                        console.log(" var table = $('#example-documentviewtime').DataTable({");
                        var table = $('#example-documentviewtime').DataTable({
                            dom: '<"top"l<"row"<"col-sm-6 text-left"f><"col-sm-6 text-right"B>>rt<"bottom"<"row"<"col-sm-12 dt-info-container"i>><"row"<"col-sm-12 dt-paging-container"p>>><"clear">',
                            buttons: [
                                {
                                    extend: 'copy',
                                    text: '复制'
                                },
                                {
                                    extend: 'csv',
                                    text: '导出 CSV'
                                },
                                {
                                    extend: 'excel',
                                    text: '导出 Excel'
                                },
                                {
                                    extend: 'pdfHtml5',
                                    text: '导出 PDF',
                                    customize: function (doc) {
                                        doc.defaultStyle = {
                                            font: 'fangzhen' // 使用支持中文的字体
                                        };
                                    }
                                },
                                {
                                    extend: 'print',
                                    text: '打印'
                                },
                                {
                                    extend: 'colvis',
                                    text: '列可见性'
                                }
                            ],
                            data: documentviewtimelist2d,
                            columns: [
                                { title: '序号' },
                                { title: '记录ID' },
                                { title: '文件ID' },
                                { title: '文件名称' },
                                { title: '用户ID' },
                                { title: '用户名称' },
                                { title: '查看时间' },
                               
                            ],
                            language: {
                                search: "搜索:",
                                lengthMenu: "每页显示 _MENU_ 条记录",
                                info: "显示第 _START_ 至 _END_ 项结果，共 _TOTAL_ 项",
                                paginate: {
                                    first: "首页",
                                    last: "末页",
                                    next: "下一页",
                                    previous: "上一页"
                                }
                            }
                        });

                     

                        console.log("table.buttons().container().appendTo(#example-documentviewtime_wrapper.col-md-6:eq(0));");
                        table
                            .buttons()
                            .container()
                            .appendTo("#example-documentviewtime_wrapper .col-md-6:eq(0)");

                        // 为所有按钮元素添加样式
                        console.log("const buttons = this.$el.querySelectorAll('.table-responsive button');");
                        const buttons = this.$el.querySelectorAll('.table-responsive button');
                        buttons.forEach(button => {
                            button.classList.add('btn', 'btn-outline-primary', 'buttons-copy', 'buttons-html5');
                        });
                        console.log("$('.dt-search').css('text-align', 'left'); this.$el.querySelector('.dt-length').classList.add('col-md-1');$('.dt-length').css('padding-left', '0');$('.text-right').css('padding-top', '20px');");
                        // 使用jQuery动态调整底部元素的样式
                        $('.dt-info-container').css({
                            display: 'flex',
                            justifyContent: 'flex-end',
                            alignItems: 'center',
                            paddingRight: '20px'
                        });
                        $('.dt-paging-container').css({
                            display: 'flex',
                            justifyContent: 'flex-end',
                            alignItems: 'center',
                            paddingRight: '20px'
                        });
                        console.log("-----------initializeDataTable END-----------");
                    });
                }
            } catch (error) {
                console.error('回调函数内部发生错误:', error);
            }

        },
        async destoryDataTable() {

            this.$nextTick(() => {
                if ($.fn.DataTable.isDataTable('#example-documentviewtime')) {
                    console.log("destoryDataTable");
                    $('#example-documentviewtime').DataTable().destroy();
                }
            });
        }


    }
}
</script>
<style scoped>
/* 样式 */
.dt-search {
    text-align: left;
}

.bottom {
    display: flex;
    justify-content: flex-end;
    align-items: center;
}

.dt-info {
    margin-right: 20px;
    /* 可根据需要调整间距 */
}
</style>