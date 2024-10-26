<template>
    <div class="content-wrapper">
        <div class="container-fluid">
            <!-- Breadcrumb-->
            <div class="row pt-2 pb-2">
                <div class="col-sm-9">
                    <h4 class="page-title">新增部门</h4>
                    <ol class="breadcrumb">
                        <li class="breadcrumb-item">部门管理</li>
                        <li class="breadcrumb-item active" aria-current="page">新增部门</li>
                    </ol>
                </div>
            </div>
            <!-- End Breadcrumb-->
            <div class="row justify-content-center">
                <div class="card col-lg-12">
                    <div class="card-body">
                        <div class="card-title text-primary">新增部门</div>
                        <hr>
                        <form>
                            <div class="form-group">
                                <label>部门ID</label>
                                <input type="text" class="form-control" placeholder="">
                            </div>
                            <div class="form-group">
                                <label>部门名</label>
                                <input type="text" class="form-control" placeholder="">
                            </div>


                            <br>
                            <div class="form-group">
                                <button type="submit" class="btn btn-primary shadow-primary px-5 col-lg-12"><i
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