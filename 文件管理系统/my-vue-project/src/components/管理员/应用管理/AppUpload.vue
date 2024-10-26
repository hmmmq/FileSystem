<template>
    <div class="content-wrapper">
        <div class="container-fluid">
            <!-- Breadcrumb-->
            <div class="row pt-2 pb-2">
                <div class="col-sm-9">
                    <h4 class="page-title">上传应用</h4>
                    <ol class="breadcrumb">
                        <li class="breadcrumb-item">应用管理</li>
                        <li class="breadcrumb-item active" aria-current="page">上传应用</li>
                    </ol>
                </div>

            </div>
            <!-- End Breadcrumb-->
            <div class="row" style="height: 450px;">
                <div class="col-lg-12">
                    <div class="card">
                        <div class="card-header text-uppercase">支持多文件上传,点击中间区域上传</div>
                        <div class="card-body">
                            <form action="http://127.0.0.1:8083/app/upload" class="dropzone" id="dropzone2">
                                <div class="fallback">
                                    <input name="file" type="file" multiple>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div><!--End Row-->

        </div>
        <!-- End container-fluid-->

    </div>
</template>