/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
function loadImage(page, theme) {

    var data = getImageData(page, theme);
    var userSession = getUserSession();
    if (data == null)
        return false;
    $.each(data, function (key, value) {
        if (value.imgId == 0) {
            $('#loadmorebutton').hide();
            return;
        }
        var img = addImage(key + (page - 1) * 5 + 1);
        var user = getUser(value.userId);
        img[0].src = value.imgUrl;
        img[0].alt = value.theme;
        var a = $(img[0]).parent()[0];
        a.href = "oneImage.html?imgid=" + value.imgId;
        var click = $(img[6]);
        var id = $(img[6]).children()[0];
        $($(id).children()[1]).append(value.point);
        var voteid = 'vote' + value.imgId;
        id.id = voteid;
        if (userSession.userId != 0) {
            var onclick = 'likeOnClick(' + value.imgId + ',' + userSession.userId + ',"' + voteid + '")';
            $(id).attr("onclick", onclick);
        }

        $(img[3]).append(value.imgDescribe);
        id = $(img[1]).children()[0];
        id.src = user.avatarUrl;
        id = $(img[2]).children()[0];
        var url = "profile.html?id=" + user.userId;
        img[2].href = url;
        $(id).append(user.nickName);
        id = $(img[4]);
        $(id).append(value.imgDate);
        var showcomment = "showComment(" + value.imgId + ")";
        id = $(img[5]).children()[0];
        $(id).attr("onclick", showcomment);
        var list_comment = document.getElementById("list_comment");
        var listCommentId = "list_comment_" + value.imgId;
        list_comment.id = listCommentId;
        var comment = loadComment(value.imgId);
        $.each(comment, function (key1, value1) {
            if (key1 == comment.length - 3 || key1 == comment.length - 2 || key1 == comment.length - 1)
                createComment(value1, list_comment);
        });
        if (userSession.userId != 0) {
            addComment(userSession, list_comment, value.imgId);
        }
    });
    return true;

}

function getImageData(page, theme) {
    var url = "./ImageServlet?page=" + page + "&theme=" + theme;
    var data1;
    $.ajax({
        url: url,
        type: "GET",
        dataType: "JSON",
        async: false,
        success: function (data) {

            data1 = data;
        },
        error: function (a, b, c) {
            console.log(a + b + c);
            return null;
        }

    });
    return data1;
}

function addImage(key) {
    var id = "image" + key;
    var div_pre = "#image" + (key - 1);
    var newElement = document.createElement("div");
    newElement.id = id;
    $(div_pre).after(newElement);
    var test = $("#" + id);
    $.ajax({
        url: 'testimage.html',
        type: 'get',
        async: false,
        success: function (html) {

            test.append(html);
        }
    });

    var img = test.children().children().children().children();
    return img;

}


function getImageById(imgid) {
    var url = "./ImageById?imgid=" + imgid;
    var data1;
    $.ajax({
        url: url,
        type: "GET",
        dataType: "JSON",
        async: false,
        success: function (data) {

            data1 = data;
        },
        error: function (a, b, c) {
            console.log(a + b + c);
            return null;
        }

    });
    return data1;

}
