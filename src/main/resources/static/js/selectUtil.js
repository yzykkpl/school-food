function findCls(){  
        var departmentId = $("#department").attr("value");  
        $.ajax({  
            url:"${pageContext.request.contextPath}/student/findClass",  
            type:"get",  
            timeout:"1000",  
            data:{departmentId:departmentId},  
            success:function(data){  
                $("#classId option").remove();  
                $("#classId").append("<option value='0'>请选择</option>");  
                if (data != 0) {  
                    for ( var i = 0; i < data.length; i++) {  
                        var classId = data[i].classId;  
                        var className = data[i].className;  
                        $("#classId").append(  
                                "<option value="+classId+">"  
                                        + className + "</option>");  
                    }  
                }  
            },  
            error : function(XMLResponse) {  
                alert(XMLResponse.responseText);  
            }  
        });  
    }  