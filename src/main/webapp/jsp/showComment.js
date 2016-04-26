/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function comment(image, name, src_info,messege){
    this.image = image;
    this.name = name;
    this.messege = messege;
    this.src_info = src_info;
}


function showComment(id) {
    var visible = $("#list_comment").css("visibility");
    if(visible == "hidden"){
        $("#list_comment").css("visibility","visible");
    }
    else $("#list_comment").css("visibility","hidden");
}

function createComment (comment, id){
    var account = document.createElement("div");
    account.setAttribute("class","account");
    
    var aAvatar = document.createElement("a");
    aAvatar.href = comment.src_info;
    aAvatar.title = comment.name;
    aAvatar.target = "_blank";

    var imgAvatar = document.createElement("img");
    imgAvatar.src = comment.image;
    
    var aName = document.createElement("a");
    aName.href = comment.src_info;
    aAvatar.title = comment.name;
    aName.target = "_blank";
    
    var bName = document.createElement("b");
    bName.appendChild(document.createTextNode(comment.name));
    
    aName.appendChild(bName);
    aAvatar.appendChild(imgAvatar);
    
    var div_display = document.createElement("div");
    div_display.setAttribute("class","comment_display");
    
    div_display.appendChild(document.createElement("p"));
    div_display.appendChild(
            document.createElement("p").appendChild(
            document.createTextNode(comment.messege)
            ));
    div_display.appendChild(document.createElement("p"));
    
    account.appendChild(aName);
    account.appendChild(aAvatar);
    account.appendChild(div_display);
    id.appemdChild(account);
}