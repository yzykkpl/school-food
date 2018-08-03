<nav class="navbar navbar-inverse navbar-fixed-top" id="sidebar-wrapper" role="navigation">
    <ul class="nav sidebar-nav">
        <li class="sidebar-brand">
            <a href="#">
                后台管理
            </a>
        </li>
        <li>
            <a href="/canteen/seller/school/list"><i class="fa fa-fw fa-list-alt"></i>学校管理</a>
        </li>
        <li>
            <a href="/canteen/seller/order/list"><i class="fa fa-fw fa-list-alt"></i>水果订单</a>
        </li>
        <li>
            <a href="/canteen/seller/mealOrder/list"><i class="fa fa-fw fa-list-alt"></i>套餐订单</a>
        </li>
        <li>
            <a href="/canteen/seller/category/list"><i class="fa fa-fw fa-list-alt"></i> 编辑分类</a>
        </li>
        <li class="dropdown open">
            <a href="#" class="dropdown-toggle" data-toggle="dropdown" aria-expanded="true"><i class="fa fa-fw fa-plus"></i> 水果/零食 <span class="caret"></span></a>
            <ul class="dropdown-menu" role="menu">
                <li class="dropdown-header">操作</li>
                <li><a href="/canteen/seller/product/list">列表</a></li>
                <li><a href="/canteen/seller/product/index">新增</a></li>
            </ul>
        </li>
        <#--<li class="dropdown open">-->
            <#--<a href="#" class="dropdown-toggle" data-toggle="dropdown" aria-expanded="true"><i class="fa fa-fw fa-plus"></i> 类目 <span class="caret"></span></a>-->
            <#--<ul class="dropdown-menu" role="menu">-->
                <#--<li class="dropdown-header">操作</li>-->
                <#--<li><a href="/canteen/seller/category/list">列表</a></li>-->
                <#--<li><a href="/canteen/seller/category/index">新增</a></li>-->
            <#--</ul>-->
        <#--</li>-->
        <li class="dropdown open">
            <a href="#" class="dropdown-toggle" data-toggle="dropdown" aria-expanded="true"><i class="fa fa-fw fa-plus"></i> 套餐 <span class="caret"></span></a>
            <ul class="dropdown-menu" role="menu">
                <li class="dropdown-header">操作</li>
                <li><a href="/canteen/seller/meal/list">列表</a></li>
                <li><a href="/canteen/seller/meal/index">新增</a></li>
            </ul>
        </li>
        <li>
            <a href="/canteen/seller/user/list"><i class="fa fa-fw fa-list-alt"></i>操作员管理</a>
        </li>
        <li>
            <a href="/canteen/seller/std/list"><i class="fa fa-fw fa-list-alt"></i>用户管理</a>
        </li>
        <li>
            <a href="/canteen/seller/logout"><i class=user"fa fa-fw fa-list-alt"></i> 登出</a>
        </li>
    </ul>
</nav>