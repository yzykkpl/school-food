function select() {

    var schoolId = $('#schSelect').val()
    var cls = $('#clsSelect').val()
    var name=$('#name').val()
    var status=$('#status').val()

    getData(schoolId,name,cls,status)




}

//学校+班级
function getData(schoolId, name,cls,status) {
    $('#page2').remove()
    $('#page1').after("<div id='page2'></div>")
    $('#page2').bPage({
        url: '/canteen/seller/std/select',
        //开启异步处理模式
        asyncLoad: true,
        //关闭服务端页面模式
        serverSidePage: false,
        //数据自定义填充
        render: function (data) {
            var tb = $('#table2 tbody');
            $(tb).empty();
            if (data && data.list && data.list.length > 0) {
                var no=(data.pageNumber-1)*data.pageSize+1
                $.each(data.list, function (i, row) {
                    var date=new Date(row.createTime*1000).format("yyyy-MM-dd hh:mm:ss")
                    var tr = $('<tr>');
                    $(tr).append('<td><input type=\"checkbox\" value=\"'+row.id+'\"/></td>');
                    $(tr).append('<td>' + (i+no) + '</td>');
                    $(tr).append('<td>' + row.name + '</td>');
                    $(tr).append('<td>' + row.stdNum + '</td>');
                    $(tr).append('<td>' + row.school + '</td>');
                    $(tr).append('<td>' + row.cls + '</td>');
                    $(tr).append('<td>' + (row.status==0?'学生':'老师') + '</td>');

                    $(tr).append('<td>' + row.phone + '</td>');
                    $(tb).append(tr);
                });
            }
        },
        params: function () {
            return {
                schoolId: schoolId?schoolId:null,
                cls: cls?cls:null,
                name:name?name:null,
                status:status
            };
        }
    });
}

function del(){
        var arr = new Array();
        var checked=$("#tbody :checkbox:checked")
        $.each(checked,function(i){
            arr[i] = $(this).val();
        });
        console.log(arr)
    $.ajax({
        type: "POST",
        url: "/canteen/seller/std/delete",
        data: {ids:arr},
        success: function(data){
            if(data.code==0) alert("删除成功");
            else alert("删除失败");
            select()
        },
        error:function (err) {
            alert(err.responseText)
        }
    });
}





// //学校+班级+时间段
// function getPageWithSchoolAndClsAndDate(school, cls, start, end) {
//     $('#myPage').hide()
//     $('#page2').remove()
//     $('#page1').after("<div id='page2'></div>")
//     $('#page2').bPage({
//         url: '/canteen/seller/mealOrder/schoolDateList',
//         //开启异步处理模式
//         asyncLoad: true,
//         //关闭服务端页面模式
//         serverSidePage: false,
//         //数据自定义填充
//         render: function (data) {
//             console.log(data)
//             var tb = $('#table tbody');
//             $(tb).empty();
//             if (data && data.list && data.list.length > 0) {
//                 var no=(data.pageNumber-1)*data.pageSize+1
//                 $.each(data.list, function (i, row) {
//                     var tr = $('<tr>');
//                     $(tr).append('<td>' + (i+no) + '</td>');
//                     $(tr).append('<td>' + row.orderId + '</td>');
//                     $(tr).append('<td>' + row.snapName + '</td>');
//                     $(tr).append('<td>' + row.orderAmount + '</td>');
//                     $(tr).append('<td>' + row.comment + '</td>');
//                     $(tr).append('<td>' + row.buyerName + '</td>');
//                     $(tr).append('<td>' + row.buyerSchool + '</td>');
//                     $(tr).append('<td>' + row.buyerCls + '</td>');
//                     $(tr).append('<td>' + row.stdNum + '</td>');
//                     $(tr).append('<td>' + row.buyerPhone + '</td>');
//                     $(tr).append('<td>' + date + '</td>');
//                     if(row.orderStatus=='3'){
//
//                         var html='<a href='+'/canteen/seller/mealOrder/detail?orderId='+row.orderId+'>是</a>'
//                         $(tr).append('<td>' + html + '</td>');
//                     }
//                     $(tb).append(tr);
//                 });
//             }
//         },
//         params: function () {
//             return {
//                 school: school,
//                 cls: cls,
//                 start: start,
//                 end: end
//             };
//         }
//     });
// }

// //时间段
// function getPageWithDate(start, end) {
//     $('#myPage').hide()
//     $('#page2').remove()
//     $('#page1').after("<div id='page2'></div>")
//     $('#page2').bPage({
//         url: '/canteen/seller/mealOrder/dateList',
//         //开启异步处理模式
//         asyncLoad: true,
//         //关闭服务端页面模式
//         serverSidePage: false,
//         //数据自定义填充
//         render: function (data) {
//             console.log(data)
//             var tb = $('#table tbody');
//             $(tb).empty();
//             if (data && data.list && data.list.length > 0) {
//                 var no=(data.pageNumber-1)*data.pageSize+1
//                 $.each(data.list, function (i, row) {
//                     var date=new Date(row.createTime*1000).format("yyyy-MM-dd hh:mm:ss")
//                     var tr = $('<tr>');
//                     $(tr).append('<td>' + (i+no)+ '</td>');
//                     $(tr).append('<td>' + row.orderId + '</td>');
//                     $(tr).append('<td>' + row.snapName + '</td>');
//                     $(tr).append('<td>' + row.orderAmount + '</td>');
//                     $(tr).append('<td>' + row.comment + '</td>');
//                     $(tr).append('<td>' + row.buyerName + '</td>');
//                     $(tr).append('<td>' + row.buyerSchool + '</td>');
//                     $(tr).append('<td>' + row.buyerCls + '</td>');
//                     $(tr).append('<td>' + row.stdNum + '</td>');
//                     $(tr).append('<td>' + row.buyerPhone + '</td>');
//                     $(tr).append('<td>' + date + '</td>');
//                     if(row.orderStatus=='3'){
//
//                         var html='<a href='+'/canteen/seller/mealOrder/detail?orderId='+row.orderId+'>是</a>'
//                         $(tr).append('<td>' + html + '</td>');
//                     }
//                     $(tb).append(tr);
//                 });
//             }
//         },
//         params: function () {
//             return {
//                 start:start,
//                 end:end
//             };
//         }
//     });
//     console.log($('#page'))
//
// }
//
// //学校+班级+mealid
// function getPageWithSchoolAndMealId(school, cls, mealId) {
//     $('#myPage').hide()
//     $('#page2').remove()
//     $('#page1').after("<div id='page2'></div>")
//     $('#page2').bPage({
//         url: '/canteen/seller/mealOrder/schoolMealList',
//         //开启异步处理模式
//         asyncLoad: true,
//         //关闭服务端页面模式
//         serverSidePage: false,
//         //数据自定义填充
//         render: function (data) {
//             console.log(data)
//             var tb = $('#table tbody');
//             $(tb).empty();
//             if (data && data.list && data.list.length > 0) {
//                 var no = (data.pageNumber - 1) * data.pageSize + 1
//                 $.each(data.list, function (i, row) {
//                     var date=new Date(row.createTime*1000).format("yyyy-MM-dd hh:mm:ss")
//                     var tr = $('<tr>');
//                     $(tr).append('<td>' + (i + no) + '</td>');
//                     $(tr).append('<td>' + row.orderId + '</td>');
//                     $(tr).append('<td>' + row.snapName + '</td>');
//                     $(tr).append('<td>' + row.orderAmount + '</td>');
//                     $(tr).append('<td>' + row.comment + '</td>');
//                     $(tr).append('<td>' + row.buyerName + '</td>');
//                     $(tr).append('<td>' + row.buyerSchool + '</td>');
//                     $(tr).append('<td>' + row.buyerCls + '</td>');
//                     $(tr).append('<td>' + row.stdNum + '</td>');
//                     $(tr).append('<td>' + row.buyerPhone + '</td>');
//                     $(tr).append('<td>' + date + '</td>');
//                     if (row.orderStatus == '3') {
//
//                         var html = '<a href=' + '/canteen/seller/mealOrder/detail?orderId=' + row.orderId + '>是</a>'
//                         $(tr).append('<td>' + html + '</td>');
//                     }
//                     $(tb).append(tr);
//                 });
//             }
//         },
//         params: function () {
//             return {
//                 school: school,
//                 cls: cls,
//                 mealId: mealId
//             };
//         }
//     });
// }
//
// //退款订单
// function getRefundPage() {
//         $('#myPage').hide()
//         $('#page2').remove()
//         $('#page1').after("<div id='page2'></div>")
//         $('#page2').bPage({
//             url: '/canteen/seller/mealOrder/refundList',
//             //开启异步处理模式
//             asyncLoad: true,
//             //关闭服务端页面模式
//             serverSidePage: false,
//             //数据自定义填充
//             render: function (data) {
//                 console.log(data)
//                 var tb = $('#table tbody');
//                 $(tb).empty();
//                 if (data && data.list && data.list.length > 0) {
//                     var no=(data.pageNumber-1)*data.pageSize+1
//                     $.each(data.list, function (i, row) {
//                         var date=new Date(row.createTime*1000).format("yyyy-MM-dd hh:mm:ss")
//                         var tr = $('<tr>');
//                         $(tr).append('<td>' + (i+no) + '</td>');
//                         $(tr).append('<td>' + row.orderId + '</td>');
//                         $(tr).append('<td>' + row.snapName + '</td>');
//                         $(tr).append('<td>' + row.orderAmount + '</td>');
//                         $(tr).append('<td>' + row.comment + '</td>');
//                         $(tr).append('<td>' + row.buyerName + '</td>');
//                         $(tr).append('<td>' + row.buyerSchool + '</td>');
//                         $(tr).append('<td>' + row.buyerCls + '</td>');
//                         $(tr).append('<td>' + row.stdNum + '</td>');
//                         $(tr).append('<td>' + row.buyerPhone + '</td>');
//                         $(tr).append('<td>' + date + '</td>');
//                         if(row.orderStatus=='3'){
//
//                             var html='<a href='+'/canteen/seller/mealOrder/detail?orderId='+row.orderId+'>是</a>'
//                             $(tr).append('<td>' + html + '</td>');
//                         }
//                         $(tb).append(tr);
//                     });
//                 }
//             },
//             params: function () {
//                 return {
//                 };
//             }
//         });
// }
//
// //所有未支付学生列表
// function getNotPaidPage(mealId,school) {
//     $('#myPage').hide()
//     $('#page2').remove()
//     $('#page1').after("<div id='page2'></div>")
//     $('#page2').bPage({
//         url: '/canteen/seller/mealOrder/notPaidList',
//         //开启异步处理模式
//         asyncLoad: true,
//         //关闭服务端页面模式
//         serverSidePage: false,
//         //数据自定义填充
//         render: function (data) {
//             console.log(data)
//             var tb = $('#table2 tbody');
//             $(tb).empty();
//             if (data && data.list && data.list.length > 0) {
//                 var no=(data.pageNumber-1)*data.pageSize+1
//                 $.each(data.list, function (i, row) {
//                     var tr = $('<tr>');
//                     $(tr).append('<td>' + (i+no) + '</td>');
//                     $(tr).append('<td>' + row.school + '</td>');
//                     $(tr).append('<td>' + row.cls + '</td>');
//                     $(tr).append('<td>' + row.name + '</td>');
//                     $(tr).append('<td>' + row.stdNum + '</td>');
//                     $(tr).append('<td>' + row.phone + '</td>');
//                     $(tr).append('<td>' + data.mealId + '</td>');
//                     $(tb).append(tr);
//                 });
//             }
//         },
//         params: function () {
//             return {
//                 mealId:mealId,
//                 school:school
//
//             };
//         }
//     });
// }
//
// function getExcel() {
//     var school = $('#schSelect').val()
//     var mealId = $('#mealId').val()
//
//
//     // console.log('school',school)
//     // console.log('cls',cls)
//     // console.log('start',start)
//     // console.log('end',end)
//     // console.log('mealId',mealId)
//
//     if(!mealId){
//         alert("请输入套餐ID")
//         return;
//     }
//
//     if(!school){
//         alert("请选择学校")
//         return;
//     }
//     window.open("/canteen/seller/mealOrder/getExcel?school="+school+"&mealId="+mealId);
//
// }


