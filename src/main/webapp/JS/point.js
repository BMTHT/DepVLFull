function userLikes(userid){
    var data1;
    var url = "./PointImageServlet?userid="+userid;
    $.ajax({
       url: url,
       type: "GET",
       dataType: "JSON",
       async: false,
       success: function(data){
           data1 = data;
       },
       error: function(a,b,c){
           console.log(a + b + c);
       }
    });
    return data1;
}

function imgLiked(imgid,likes){
    $.each(likes,function(key,value){
       if(value == imgid)return true; 
    });
    return false;
}

function like(){
    
}