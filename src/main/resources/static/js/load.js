function select() {
    $('#table2').hide()
    $('#table').show()

    var school = $('#schSelect').val()
    var cls = $('#clsSelect').val()
    var start = $('#start').val()
    var end = $('#end').val()
    var mealId = $('#mealId').val()


    // console.log('school',school)
    // console.log('cls',cls)
    // console.log('start',start)
    // console.log('end',end)
    // console.log('mealId',mealId)


    if (start && !end) {
        alert("请输入结束日期")
        return;
    }
    if (!start && end) {
        alert("请输入开始日期")
        return;
    }

    if(mealId&&!school){
        alert("请输入学校")
        return;
    }

    if (mealId) {
        if (school) getPageWithSchoolAndMealId(school,cls,mealId)
    } else if (start) {
        console.log(start)
        if (school) {
            getPageWithSchoolAndClsAndDate(school, cls, start, end)
        } else {
            console.log('start2',start)
            getPageWithDate(start, end)
        }
    } else if (school) {
        getPageWithSchoolAndCls(school, cls)
    }


}

function notPaid() {
    var mealId = $('#mealId').val()
    var school = $('#schSelect').val()
    if(!mealId){
        alert("请输入套餐Id")
        return
    }
    if(!school){
        alert("请选择学校")
        return
    }

    $('#table').hide()
    $('#table2').show()

    getNotPaidPage(mealId,school)




}

//学校+班级
function getPageWithSchoolAndCls(school, cls) {
    console.log(school)
    $('#myPage').hide()
    $('#page2').remove()
    $('#page1').after("<div id='page2'></div>")
    $('#page2').bPage({
        url: '/canteen/seller/mealOrder/schoolList',
        //开启异步处理模式
        asyncLoad: true,
        //关闭服务端页面模式
        serverSidePage: false,
        //数据自定义填充
        render: function (data) {
            console.log(data)
            var tb = $('#table tbody');
            $(tb).empty();
            if (data && data.list && data.list.length > 0) {
                var no=(data.pageNumber-1)*data.pageSize+1
                $.each(data.list, function (i, row) {
                    var date=new Date(row.createTime*1000).format("yyyy-MM-dd hh:mm:ss")
                    var tr = $('<tr>');
                    $(tr).append('<td>' + (i+no) + '</td>');
                    $(tr).append('<td>' + row.orderId + '</td>');
                    $(tr).append('<td>' + row.snapName + '</td>');
                    $(tr).append('<td>' + row.orderAmount + '</td>');
                    $(tr).append('<td>' + row.comment + '</td>');
                    $(tr).append('<td>' + row.buyerName + '</td>');
                    $(tr).append('<td>' + row.buyerSchool + '</td>');
                    $(tr).append('<td>' + row.buyerCls + '</td>');
                    $(tr).append('<td>' + row.stdNum + '</td>');
                    $(tr).append('<td>' + row.buyerPhone + '</td>');

                    $(tr).append('<td>' + date + '</td>');
                    if(row.orderStatus=='3'){

                        var html='<a href='+'/canteen/seller/mealOrder/detail?orderId='+row.orderId+'>是</a>'
                        $(tr).append('<td>' + html + '</td>');
                    }
                    $(tb).append(tr);
                });
            }
        },
        params: function () {
            return {
                school: school,
                cls: cls
            };
        }
    });
}


//学校+班级+时间段
function getPageWithSchoolAndClsAndDate(school, cls, start, end) {
    $('#myPage').hide()
    $('#page2').remove()
    $('#page1').after("<div id='page2'></div>")
    $('#page2').bPage({
        url: '/canteen/seller/mealOrder/schoolDateList',
        //开启异步处理模式
        asyncLoad: true,
        //关闭服务端页面模式
        serverSidePage: false,
        //数据自定义填充
        render: function (data) {
            console.log(data)
            var tb = $('#table tbody');
            $(tb).empty();
            if (data && data.list && data.list.length > 0) {
                var no=(data.pageNumber-1)*data.pageSize+1
                $.each(data.list, function (i, row) {
                    var date=new Date(row.createTime*1000).format("yyyy-MM-dd hh:mm:ss")
                    var tr = $('<tr>');
                    $(tr).append('<td>' + (i+no) + '</td>');
                    $(tr).append('<td>' + row.orderId + '</td>');
                    $(tr).append('<td>' + row.snapName + '</td>');
                    $(tr).append('<td>' + row.orderAmount + '</td>');
                    $(tr).append('<td>' + row.comment + '</td>');
                    $(tr).append('<td>' + row.buyerName + '</td>');
                    $(tr).append('<td>' + row.buyerSchool + '</td>');
                    $(tr).append('<td>' + row.buyerCls + '</td>');
                    $(tr).append('<td>' + row.stdNum + '</td>');
                    $(tr).append('<td>' + row.buyerPhone + '</td>');
                    $(tr).append('<td>' + date + '</td>');
                    if(row.orderStatus=='3'){

                        var html='<a href='+'/canteen/seller/mealOrder/detail?orderId='+row.orderId+'>是</a>'
                        $(tr).append('<td>' + html + '</td>');
                    }
                    $(tb).append(tr);
                });
            }
        },
        params: function () {
            return {
                school: school,
                cls: cls,
                start: start,
                end: end
            };
        }
    });
}

//时间段
function getPageWithDate(start, end) {
    $('#myPage').hide()
    $('#page2').remove()
    $('#page1').after("<div id='page2'></div>")
    $('#page2').bPage({
        url: '/canteen/seller/mealOrder/dateList',
        //开启异步处理模式
        asyncLoad: true,
        //关闭服务端页面模式
        serverSidePage: false,
        //数据自定义填充
        render: function (data) {
            console.log(data)
            var tb = $('#table tbody');
            $(tb).empty();
            if (data && data.list && data.list.length > 0) {
                var no=(data.pageNumber-1)*data.pageSize+1
                $.each(data.list, function (i, row) {
                    var date=new Date(row.createTime*1000).format("yyyy-MM-dd hh:mm:ss")
                    var tr = $('<tr>');
                    $(tr).append('<td>' + (i+no)+ '</td>');
                    $(tr).append('<td>' + row.orderId + '</td>');
                    $(tr).append('<td>' + row.snapName + '</td>');
                    $(tr).append('<td>' + row.orderAmount + '</td>');
                    $(tr).append('<td>' + row.comment + '</td>');
                    $(tr).append('<td>' + row.buyerName + '</td>');
                    $(tr).append('<td>' + row.buyerSchool + '</td>');
                    $(tr).append('<td>' + row.buyerCls + '</td>');
                    $(tr).append('<td>' + row.stdNum + '</td>');
                    $(tr).append('<td>' + row.buyerPhone + '</td>');
                    $(tr).append('<td>' + date + '</td>');
                    if(row.orderStatus=='3'){

                        var html='<a href='+'/canteen/seller/mealOrder/detail?orderId='+row.orderId+'>是</a>'
                        $(tr).append('<td>' + html + '</td>');
                    }
                    $(tb).append(tr);
                });
            }
        },
        params: function () {
            return {
                start:start,
                end:end
            };
        }
    });
    console.log($('#page'))

}

//学校+班级+mealid
function getPageWithSchoolAndMealId(school, cls, mealId) {
    $('#myPage').hide()
    $('#page2').remove()
    $('#page1').after("<div id='page2'></div>")
    $('#page2').bPage({
        url: '/canteen/seller/mealOrder/schoolMealList',
        //开启异步处理模式
        asyncLoad: true,
        //关闭服务端页面模式
        serverSidePage: false,
        //数据自定义填充
        render: function (data) {
            console.log(data)
            var tb = $('#table tbody');
            $(tb).empty();
            if (data && data.list && data.list.length > 0) {
                var no = (data.pageNumber - 1) * data.pageSize + 1
                $.each(data.list, function (i, row) {
                    var date=new Date(row.createTime*1000).format("yyyy-MM-dd hh:mm:ss")
                    var tr = $('<tr>');
                    $(tr).append('<td>' + (i + no) + '</td>');
                    $(tr).append('<td>' + row.orderId + '</td>');
                    $(tr).append('<td>' + row.snapName + '</td>');
                    $(tr).append('<td>' + row.orderAmount + '</td>');
                    $(tr).append('<td>' + row.comment + '</td>');
                    $(tr).append('<td>' + row.buyerName + '</td>');
                    $(tr).append('<td>' + row.buyerSchool + '</td>');
                    $(tr).append('<td>' + row.buyerCls + '</td>');
                    $(tr).append('<td>' + row.stdNum + '</td>');
                    $(tr).append('<td>' + row.buyerPhone + '</td>');
                    $(tr).append('<td>' + date + '</td>');
                    if (row.orderStatus == '3') {

                        var html = '<a href=' + '/canteen/seller/mealOrder/detail?orderId=' + row.orderId + '>是</a>'
                        $(tr).append('<td>' + html + '</td>');
                    }
                    $(tb).append(tr);
                });
            }
        },
        params: function () {
            return {
                school: school,
                cls: cls,
                mealId: mealId
            };
        }
    });
}

//退款订单
function getRefundPage() {
        $('#myPage').hide()
        $('#page2').remove()
        $('#page1').after("<div id='page2'></div>")
        $('#page2').bPage({
            url: '/canteen/seller/mealOrder/refundList',
            //开启异步处理模式
            asyncLoad: true,
            //关闭服务端页面模式
            serverSidePage: false,
            //数据自定义填充
            render: function (data) {
                console.log(data)
                var tb = $('#table tbody');
                $(tb).empty();
                if (data && data.list && data.list.length > 0) {
                    var no=(data.pageNumber-1)*data.pageSize+1
                    $.each(data.list, function (i, row) {
                        var date=new Date(row.createTime*1000).format("yyyy-MM-dd hh:mm:ss")
                        var tr = $('<tr>');
                        $(tr).append('<td>' + (i+no) + '</td>');
                        $(tr).append('<td>' + row.orderId + '</td>');
                        $(tr).append('<td>' + row.snapName + '</td>');
                        $(tr).append('<td>' + row.orderAmount + '</td>');
                        $(tr).append('<td>' + row.comment + '</td>');
                        $(tr).append('<td>' + row.buyerName + '</td>');
                        $(tr).append('<td>' + row.buyerSchool + '</td>');
                        $(tr).append('<td>' + row.buyerCls + '</td>');
                        $(tr).append('<td>' + row.stdNum + '</td>');
                        $(tr).append('<td>' + row.buyerPhone + '</td>');
                        $(tr).append('<td>' + date + '</td>');
                        if(row.orderStatus=='3'){

                            var html='<a href='+'/canteen/seller/mealOrder/detail?orderId='+row.orderId+'>是</a>'
                            $(tr).append('<td>' + html + '</td>');
                        }
                        $(tb).append(tr);
                    });
                }
            },
            params: function () {
                return {
                };
            }
        });
}

//所有未支付学生列表
function getNotPaidPage(mealId,school) {
    $('#myPage').hide()
    $('#page2').remove()
    $('#page1').after("<div id='page2'></div>")
    $('#page2').bPage({
        url: '/canteen/seller/mealOrder/notPaidList',
        //开启异步处理模式
        asyncLoad: true,
        //关闭服务端页面模式
        serverSidePage: false,
        //数据自定义填充
        render: function (data) {
            console.log(data)
            var tb = $('#table2 tbody');
            $(tb).empty();
            if (data && data.list && data.list.length > 0) {
                var no=(data.pageNumber-1)*data.pageSize+1
                $.each(data.list, function (i, row) {
                    var tr = $('<tr>');
                    $(tr).append('<td>' + (i+no) + '</td>');
                    $(tr).append('<td>' + row.school + '</td>');
                    $(tr).append('<td>' + row.cls + '</td>');
                    $(tr).append('<td>' + row.name + '</td>');
                    $(tr).append('<td>' + row.stdNum + '</td>');
                    $(tr).append('<td>' + row.phone + '</td>');
                    $(tr).append('<td>' + data.mealId + '</td>');
                    $(tb).append(tr);
                });
            }
        },
        params: function () {
            return {
                mealId:mealId,
                school:school

            };
        }
    });
}

function getExcel() {
    var school = $('#schSelect').val()
    var mealId = $('#mealId').val()


    // console.log('school',school)
    // console.log('cls',cls)
    // console.log('start',start)
    // console.log('end',end)
    // console.log('mealId',mealId)

    if(!mealId){
        alert("请输入套餐ID")
        return;
    }

    if(!school){
        alert("请选择学校")
        return;
    }
    window.open("/canteen/seller/mealOrder/getExcel?school="+school+"&mealId="+mealId);

}


