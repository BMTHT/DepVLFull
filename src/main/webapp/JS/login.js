

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

function createAvatar(userid) {
    var user = getUser(userid);
    var loginbar = document.getElementById("loginbar");
    var avatar = document.getElementById("avatar");
    avatar.remove();
    var li1 = document.createElement("li");
    li1.setAttribute ("class","dropdown");
    var a1 = document.createElement("a");
    a1.setAttribute("style", "padding: 0;");
    a1.href = "profile.html";
    a1.setAttribute("class", "dropdown-toggle");
    a1.setAttribute("data-toggle", "dropdown");
    var img = document.createElement("img");
    img.src = user.avatarUrl;
    img.setAttribute("style" , "width: 45px; height: 45px;");
    img.alt = user.nickName;
    img.setAttribute("class", "img-circle");
    a1.appendChild(img);
    var span = document.createElement("span");
    span.setAttribute("class", "caret");
    a1.appendChild(span);
    var ul = document.createElement("ul");
    ul.setAttribute("class", "dropdown-menu");
    ul.setAttribute("style" , "left: 0;");
    var a2 = document.createElement("a");
    a2.href = "profile.html?id="+user.userId;
    a2.appendChild(document.createTextNode(user.nickName));
    var li = document.createElement("li");
    li.appendChild(a2);
    ul.appendChild(li);
    var a3 = document.createElement("a");
    a3.href = "upload.html";
    a3.appendChild(document.createTextNode("Upload"));
    var li_ = document.createElement("li");
    li_.appendChild(a3);
    ul.appendChild(li_);
    var a4 = document.createElement("a");
    a4.href="javascript:logout()";
    a4.appendChild(document.createTextNode("Đăng xuất"));
    var li__ = document.createElement("li");
    li__.appendChild(a4);
    ul.appendChild(li__);
    li1.appendChild(a1);
    li1.appendChild(ul);
    loginbar.appendChild(li1);

}