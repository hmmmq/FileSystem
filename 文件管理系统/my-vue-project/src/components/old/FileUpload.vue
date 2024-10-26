<template>
    <div class="content-wrapper">
        <div class="container-fluid">
            <!-- Breadcrumb-->
            <div class="row pt-2 pb-2">
                <div class="col-sm-9">
                    <h4 class="page-title">上传文档</h4>
                    <ol class="breadcrumb">
                        <li class="breadcrumb-item">文档管理</li>
                        <li class="breadcrumb-item active" aria-current="page">上传文档</li>
                    </ol>
                </div>
            </div>
            <!-- End Breadcrumb-->
            <div class="row justify-content-center">
                <div class="card col-lg-12">
                    <div class="card-body">
                        <div class="card-title text-primary">文档资料</div>
                        <hr>
                        <form>
                            <div class="form-group">
                                <label>文档名称</label>
                                <input type="text" class="form-control" placeholder="">
                            </div>
                            <div class="form-group">
                                <label>文档类别</label>
                                <input type="text" class="form-control" placeholder="">
                            </div>
                            <div class="form-group">
                                <label for="input-3">文档关键字</label>
                                <input type="text" class="form-control" id="input-3" placeholder="">
                            </div>

                            <div class="form-group">
                                <label for="input-4">权限</label>
                                <select class="custom-select" id="inputGroupSelect03">
                                    <option selected="">选择...</option>
                                    <option value="1">全部可见</option>
                                    <option value="2">指定人可见</option>
                                    <option value="3">仅自己可见</option>
                                </select>
                            </div>
                            <div class="form-group">
                                <label for="input-4">文档描述</label>
                                <textarea class="form-control"></textarea>
                            </div>
                            <div class="form-group">
                                <label for="input-4">上传文档</label>
                                <input type="file" class="form-control valid" id="input-8" name="file" required=""
                                    aria-invalid="false">
                            </div>
                            <br>
                            <div class="form-group">
                                <button type="submit" class="btn btn-primary shadow-primary px-5 col-lg-12"><i
                                        class="icon-lock"></i>保存</button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>


        </div>
        <!-- End container-fluid-->

    </div>
</template>