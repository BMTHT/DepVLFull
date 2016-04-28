

$(function () {

    $('#form').submit(function (e) {
        
        var data = $('#form2').serialize();
        var url = "./LoginServlet?act=login";
        $.ajax({
            url: url,
            data: data,
            dataType: 'json',
            type: 'POST',
            async: false,
            success: function (data) {
                if (data.id != 0) {
                    window.location.href = "/DepVL";
                } else
                    alert("Sai tài khoản hoặc mật khẩu! \n Vui lòng nhập lại  ");
            },
            error: function (data) {

            }
        });
        e.preventDefault();
        return false;
    });
});