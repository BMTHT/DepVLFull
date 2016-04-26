/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
function loadImage(page, theme) {
    
    var data = getImageData(page,theme);
    
    if(data == null) return false;
    $.each(data, function (key, value) {
        var img = addImage(key + (page - 1) * 5 + 1);
       // console.log(img.parent());
        var user = getUser(value.userId);
        $(img[0]).append(value.imgDescribe);
        img[1].src = value.imgUrl;
        img[1].alt = value.theme;
        var id = $(img[5]).children()[0];
        $(id).append(value.point);
        id = $(img[2]).children()[0];
        id.src = user.avatarUrl;
        id = $(img[3]).children()[0];
        $(id).append(user.nickName);
        id = $(img[3]).children()[1];
        $(id).append(value.imgDate);
        var list_comment = document.getElementById("list_comment");
        var listCommentId = "list_comment_"+value.imgId;
        list_comment.id = listCommentId;
        
        
        var comment = loadComment(value.imgId);
        
        $.each(comment,function(key1,value1){
           createComment(value1,list_comment); 
        });



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
        error: function(){
            
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
        url: 'image.html',
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
