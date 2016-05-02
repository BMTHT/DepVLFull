/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
function loadImage(page, theme) {
    
    var data = getImageData(page,theme);
    console.log(data);
    var userSession = getUserSession();
    if(data == null) return false;
   $.each(data, function (key, value) {
        var img = addImage(key + (page - 1) * 5 + 1);
        var user = getUser(value.userId);
        img[0].src = value.imgUrl;
        img[0].alt = value.theme;
        var id = $(img[6]).children()[0];
        $(id).append(value.point);
        $(img[3]).append(value.imgDescribe);
        id = $(img[1]).children()[0];
        id.src = user.avatarUrl;
        id = $(img[2]).children()[0];
        $(id).append(user.nickName);
        id = $(img[4]);
        $(id).append(value.imgDate);
        var showcomment = "showComment(" + value.imgId + ")";
        id = $(img[5]).children()[0];
        $(id).attr("onclick",showcomment);
        var list_comment = document.getElementById("list_comment");
        var listCommentId = "list_comment_"+value.imgId;
        list_comment.id = listCommentId;
    //    console.log(list_comment1);
        var comment = loadComment(value.imgId);
        $.each(comment,function(key1,value1){
           createComment(value1,list_comment); 
        });
        if(userSession.userId != 0 ){
            addComment(userSession,list_comment);
        }else{
            list_comment.appendChild(document.createElement("p").appendChild(document.createTextNode("Dang nhap de binh luan")));
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
            
           data1= data;
        },
        error: function(a,b,c){
            console.log(a+b+c);
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


function popup(id){
    $(id).webuiPopover({type:'iframe',url:'comment1.html', width:'500',height: '300',placement: 'top-left'});
}
