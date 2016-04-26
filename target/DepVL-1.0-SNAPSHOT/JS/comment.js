/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function loadComment(imgId){
    var comment;
    var url = "./CommentServlet?imgId="+imgId;
    $.ajax({
        url: url,
        type: "GET",
        dataType: 'json',
        async: false,
        success:function(data){
            comment = data;
        },
        error: function(){
            
        }
    });
    return comment;
}