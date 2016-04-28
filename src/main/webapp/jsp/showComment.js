/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function comment(image, name, src_info, messege) {
    this.image = image;
    this.name = name;
    this.messege = messege;
    this.src_info = src_info;
}


function showComment(id) {
    var listCommentId = '#list_comment_' + id;
    var visible = $(listCommentId).css("visibility");
    if (visible == "hidden") {
        $(listCommentId).css("visibility", "visible");
    }
    else
        $(listCommentId).css("visibility", "hidden");
}

function createComment(comment, id) {


    var user = getUser(comment.userId);
    var account = document.createElement("div");
    account.setAttribute("class", "account");

    var aAvatar = document.createElement("a");
    aAvatar.href = "";
    aAvatar.title = user.nickName;
    aAvatar.target = "_blank";

    var imgAvatar = document.createElement("img");
    imgAvatar.src = user.avatarUrl;

    var aName = document.createElement("a");
    aName.href = "";
    aAvatar.title = user.nickName;
    aName.target = "_blank";

    var bName = document.createElement("b");
    bName.appendChild(document.createTextNode(user.nickName));

    aName.appendChild(bName);
    aAvatar.appendChild(imgAvatar);

    var div_display = document.createElement("div");
    div_display.setAttribute("class", "comment_display");

    div_display.appendChild(document.createElement("p"));
    div_display.appendChild(
            document.createElement("p").appendChild(
            document.createTextNode(comment.comment)
            ));
    div_display.appendChild(document.createElement("p"));

    account.appendChild(aName);
    account.appendChild(aAvatar);
    account.appendChild(div_display);
    id.appendChild(account);
}

function addComment(user,id) {
    var account = document.createElement("div");
    account.setAttribute("class", "account");

    var aAvatar = document.createElement("a");
    aAvatar.href = "";
    aAvatar.title = user.nickName;
    aAvatar.target = "_blank";

    var imgAvatar = document.createElement("img");
    imgAvatar.src = user.avatarUrl;
     aAvatar.appendChild(imgAvatar);
     
     var text = document.createElement("textarea");
     text.rows="3";
     text.placeholder="Add a comment...";
     text.tabindex="2";
     text.name="message";
     
     var button = document.createElement("button");
     button.type = "button";
     button.appendChild(document.createTextNode("Binh luan"));
     account.appendChild(aAvatar);
     account.appendChild(text);
     account.appendChild(button);
     id.appendChild(account);     

    
}