
/*
    关于用户进行关注，取消关注等操作的js都封装在这个js中了
 */

// 获取关注信息，判断是否已经进行了关注
function checkFocus(typeName) {
    $.post("/admin/focus/info/checkFocus", {"typeName": typeName},
        function (result) {
            if(result.res == true){
                // 表示当前类型已被关注，则显示“已关注按钮”
                $('#cancelFocus').show().attr("focusId", focusId);
            }else{
                // 表示未被关注，则显示“关注”按钮
                $('#focusIt').show();
            }
        }, "json");
}

// 关注该类型的优惠信息。即关注操作
function focusIt(typeName) {
    $.post("/admin/focus/info/addFocus", {"typeName": typeName}, function (result) {
        if(result.success){
            layer.msg('关注成功');
            $('#focusIt').hide();		// 隐藏关注按钮
            $('#cancelFocus').show().attr("focusId", result.focusId);		// 显示取消关注按钮, 设置focusId属性，存放关注信息的id
        }else{
            layer.closeAll();
            layer.msg('关注失败，请稍后再试！')
        }
    }, "json");
}

// 取消关注
function cancelFocus(elem) {
    layer.confirm('确认要取消关注？',
        {icon: 3, title: '提示'},
        function (index) {
            // 获取关注信息的id
            let focusId = $(elem).attr("focusId");
            $.post("/admin/focus/info/cancelFocus", {"focusId": focusId}, function (result) {
                if (result.success) {
                    layer.msg('取消关注成功！');
                    $(elem).hide();
                    $('#focusIt').show();
                } else {
                    layer.closeAll();
                    layer.msg('取消关注失败，请稍后再试！')
                }
            }, 'json');
            // 关闭弹窗
            layer.close(index);
        });
}