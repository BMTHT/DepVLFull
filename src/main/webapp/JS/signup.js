/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


$(function () {

    $('#signup').submit(function (e) {
        var data = new FormData(document.getElementById("signup_form"));
        var url = "./RegisterServlet";
        var checkuser = checkUserName($('#username').val());
        var checkpass = checkPass($('#password').val());
        var checkrp = checkRP($('#password').val(), $('#repeatpass').val());
        var checkemail = checkEmail($('#email').val());
        if (checkuser && checkpass && checkrp && checkemail) {
            $.ajax({
                url: url,
                data: data,
                dataType: 'json',
                type: 'POST',
                async: false,
                success: function (data) {
                    if (data.userId != 0)
                    {
                        alert("Đăng ký thành công \n Xin mời đăng nhập");
                        window.location.href = "signIn.html";
                    }
                },
                error: function (data) {

                },
                cache: false,
                contentType: false,
                processData: false
            });
        }
        else
            alert("Sai vài thông tin \n Xin hãy nhập lại!");

        e.preventDefault();
        return false;
    });
});


function checkUserName(username) {
    var url = "./UserServlet?user_name=" + username;
    var result;
    $.ajax({
        url: url,
        type: "GET",
        dataType: "JSON",
        async: false,
        success: function (data) {

            if (data.userId === 0) {

                result = true;
            } else {
                result = false;
            }

        },
        error: function (a, b, c) {

        }

    });
    return result;
}

function checkPass(password) {
    var passw = /^(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{6,20}$/;
    return passw.test(password);
}
function checkRP(password, rpassword) {
    if (!checkPass(password))
        return false;
    if (password == rpassword)
        return true;
    else
        return false;
}

function checkEmail(email) {
    if (email != "")
        return true;
    else
        return false;
}
