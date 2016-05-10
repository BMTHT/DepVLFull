function userLikes(userid) {
    var data1;
    var url = "./PointImageServlet?userid=" + userid;
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
        }
    });
    return data1;
}

function imgLiked(imgid, likes) {
    var kq =false;
    $.each(likes, function (key, value) {
        console.log(value);
        if (value == imgid)
            kq = true;
    });
    return kq;
}

function dislike(id,imgid) {
    var url = "./PointImageServlet?act=dislike&imgid="+imgid;
    $.ajax({
        url: url,
        type: "POST",
        dataType: "JSON",
        async: false,
        success: function (data) {
              id.removeChild($(id).children()[1]);
            $(id).append('<b>' +data.point+'</b>');
        },
        error: function (a, b, c) {
            console.log(a + b + c);
        }
    });
}

function like(id,imgid) {
    var url = "./PointImageServlet?act=like&imgid="+imgid;
    $.ajax({
        url: url,
        type: "POST",
        dataType: "JSON",
        async: false,
        success: function (data) {
            id.removeChild($(id).children()[1]);
            $(id).append('<b>' +data.point+'</b>');
        },
        error: function (a, b, c) {
            console.log(a + b + c);
        }
    });
}
function likeOnClick(imgid, userid,id) {
    var likes = userLikes(userid);
    var liked = imgLiked(imgid, likes);
    var element = document.getElementById(id);
    if (liked) dislike(element,imgid);
    else like(element,imgid);
}

