<template>
    <div class="content-wrapper">
        <div class="container-fluid">
            <!-- Breadcrumb-->
            <div class="row pt-2 pb-2">
                <div class="col-sm-9">
                    <h4 class="page-title" v-show="!user?.type">我的文档</h4>
                    <h4 class="page-title" v-show="user?.type">用户文档</h4>
                    <ol class="breadcrumb">
                        <li class="breadcrumb-item">文档管理</li>
                        <li class="breadcrumb-item active" aria-current="page" v-show="!user?.type">我的文档</li>
                        <li class="breadcrumb-item active" aria-current="page" v-show="user?.type">用户文档</li>
                    </ol>
                </div>
            </div>
            <button type="button" class="btn btn-outline-info waves-effect waves-light m-1"
                @click="updatelist">刷新列表</button>

            <file-edit :initialDocument="initialDocument" v-if="initialDocument.id != ''" @data-back="handleDataBack" />

            <!-- End Breadcrumb-->
            <div class="row">
                <div class="col-lg-12">
                    <div class="card">
                        <div class="card-header"><i class="fa fa-table"></i> 文档列表</div>

                        <div class="card-body">

                            <div class="table-responsive">
                                <table id="example" class="table table-bordered">

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
import FileEdit from './FileEdit.vue';

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
        FileEdit
    },
    data() {
        return {
            user: null,
            documentlist: [], // 初始化为空数组
            update: false,
            initialDocument: {
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
        handleDataBack(data) {
            console.log('回传的数据:', data);
            // 处理回传的数据
            if (data) {
                this.initialDocument.id = '';
            }
        },
        editData(data) {
            // 弹出对话框，允许用户编辑数据
            const newData = prompt("编辑数据:", JSON.stringify(data));
            if (newData) {
                // 处理编辑后的数据
                this.$emit('update-data', JSON.parse(newData));
            }
        },
        downloadData(data) {
            // 创建一个隐藏的a标签，触发下载
            const blob = new Blob([JSON.stringify(data)], { type: 'application/json' });
            const url = URL.createObjectURL(blob);
            const a = document.createElement('a');
            a.href = url;
            a.download = 'data.json';
            a.click();
            URL.revokeObjectURL(url);
        },
        deleteData(data) {
            // 确认删除操作
            if (confirm("确定要删除吗?")) {
                this.$emit('delete-data', data);
            }
        },
        async updatelist() {
            await this.destoryDataTable();
            this.user = JSON.parse(localStorage.getItem('user'));
            var url = '';
            if (this.user.type) {
                url = 'http://192.168.0.104:8086/document/';
            }
            else {
                url = 'http://192.168.0.104:8086/document/user/' + this.user.id;
            }

            this.initializeDataTable(url);

        },
        fetchData() {
            try {
                const res = axios.get('http://192.168.0.104:8086/document/');
                if (res.status === 200) {
                    console.log('http://192.168.0.104:8086/document/');
                    console.log('this.documentlist = res.data;');
                }
            } catch (err) {
                console.error(err);
            }
        },
        convertTo2DArray(data) {
            return data.map((item, index) => [
                index + 1, // 添加索引列
                item.id,
                item.name,
                item.url,
                item.author,
                item.category,
                item.uploadTime,
                item.viewCount,
                item.permission ? '全部可见' : '部分可见',
                item.visibleDepartmentId,
                item.visibleUserId,
                item.id
            ]);
        },
        async initializeDataTable(url) {
            console.log("initializeDataTable");
            var documentlist2d = null;
            try {
                if (!$.fn.DataTable.isDataTable('#example')) {


                    try {
                        this.user = JSON.parse(localStorage.getItem('user'));
                        const promise = await axios.get(url);
                        if (promise.status === 200) {
                            console.log(promise.data);
                            documentlist2d = this.convertTo2DArray(promise.data);
                        } else {
                            console.log(promise);
                            return;
                        }
                    } catch (err) {
                        console.error(err);
                        return;
                    }

                    this.$nextTick(() => {
                        console.log(" var table = $('#example').DataTable({");
                        var table = $('#example').DataTable({
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
                            data: documentlist2d,
                            columns: [
                                { title: '序号' },
                                { title: '文档id' },
                                { title: '文档名称' },
                                { title: '文件名称' },
                                { title: '上传用户' },
                                { title: '文档分类' },
                                { title: '上传时间' },
                                { title: '查看次数' },
                                { title: '文档权限' },
                                { title: '可见部门id' },
                                { title: '可见用户id' },

                                {
                                    title: '操作',
                                    render: function (data) {
                                        return `
                                        <button class="view-btn btn btn-outline-primary buttons-copy buttons-html5" value="${data}" >查看</button>
                                        <button class="edit-btn btn btn-outline-primary buttons-copy buttons-html5" value="${data}" >修改</button>
                                        <button class="download-btn btn btn-outline-primary buttons-copy buttons-html5" value="${data}">下载</button>
                                        <button class="delete-btn btn btn-outline-primary buttons-copy buttons-html5" value="${data}">删除</button>
                                        `;
                                    }
                                }
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

                      
                        $('#example tbody').off('click', '.view-btn').on('click', '.view-btn', async (event) => {
                            var user = JSON.parse(localStorage.getItem('user'));
                            var documentId = $(event.currentTarget).val();
                            // 创建一个<a>link标签
                            var a = document.createElement('a');
                            // 设置下载url
                            a.href = 'http://192.168.0.104:8086/document/view/' + user.id + '/' + documentId;
                            // 点击下载
                            a.click();
                        });


                        // 绑定编辑、下载和删除按钮的事件
                        $('#example tbody').off('click', '.edit-btn').on('click', '.edit-btn', (event) => {
                            var data = $(event.currentTarget).val();
                            console.log('编辑数据:', data);
                            console.log('数据:', this.initialDocument);
                            axios.get('http://192.168.0.104:8086/document/' + data).then(res => {
                                this.initialDocument = res.data;
                            });

                        });

                        $('#example tbody').off('click', '.download-btn').on('click', '.download-btn', async (event) => {
                            var user = JSON.parse(localStorage.getItem('user'));
                            var documentId = $(event.currentTarget).val();
                            // 创建一个<a>link标签
                            var a = document.createElement('a');
                            // 设置下载url
                            a.href = 'http://192.168.0.104:8086/document/download/' + user.id + '/' + documentId;
                            // 点击下载
                            a.click();
                        });

                        $('#example tbody').off('click', '.delete-btn').on('click', '.delete-btn', (event) => {
                            var user = JSON.parse(localStorage.getItem('user'));
                            axios.delete('http://192.168.0.104:8086/document/' + user.id + '/' + $(event.currentTarget).val()).then(res => {
                                if (res.data) {
                                    console.log('删除成功');
                                    alert('删除成功');
                                } else {
                                    console.error('删除失败');
                                    alert('删除失败,你无权删除该文档或已删除未刷新');
                                }
                            });
                        });


                        console.log("table.buttons().container().appendTo(#example_wrapper.col-md-6:eq(0));");
                        table
                            .buttons()
                            .container()
                            .appendTo("#example_wrapper .col-md-6:eq(0)");

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
                if ($.fn.DataTable.isDataTable('#example')) {
                    console.log("destoryDataTable");
                    $('#example').DataTable().destroy();
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