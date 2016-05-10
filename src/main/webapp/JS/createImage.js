/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function image(imageName, src, user_avatar, user_name) {
        this.imageName = imageName;
        this.src = src;
        this.user_avatar = user_avatar;
        this.user_name = user_name;
    }

function c10Item (){
    var image1 = new image("Ảnh", "img/nature/3.jpg", "img/avatar/images.jpg", "Quang Thắng");
    for(i = 0;i<10;i++){
        cImage(image1);
    }
}

function createImage() {

    var array_image = new Array();
    var image1 = new image("thắng đẹp trai", "img/nature/3.jpg", "img/avatar/images.jpg", "Quang Thắng");
    array_image.push(image1);
    for(i = 0; i < array_image.length; i ++)
    {
        cImage(array_image[i]);
    }
}

function cImage(image){
     var divItem = document.createElement("div");
    divItem.setAttribute("class", "item_image");
    
// div title

    var divTitle = document.createElement("div");
    divTitle.setAttribute("class", "image_title");

    var aTitle = document.createElement("a");
    aTitle.href = "#";
    var bTitle = document.createElement("b");
    bTitle.appendChild(document.createTextNode(image.imageName));

    aTitle.appendChild(bTitle);
    divTitle.appendChild(aTitle);
    divItem.appendChild(divTitle);

    //div image

    var divImage = document.createElement("div");
    divImage.setAttribute("class", "image");

    var aImage = document.createElement("a");
    aImage.href = "#";

    var imgImage = document.createElement("img");
    imgImage.src = image.src;

    aImage.appendChild(imgImage);
    divImage.appendChild(aImage);
    divItem.appendChild(divImage);
    
    //create user
    
    var divUser = document.createElement("div");
    divUser.setAttribute("class", "user_status");
    
    //create avatar and name user
    var itemUser = document.createElement("div");
    itemUser.setAttribute("class", "item_user");
    
    var aAvatar = document.createElement("a");
    aAvatar.href = "https://www.facebook.com/quang.thang.0502";
    aAvatar.title = "Thang";
    aAvatar.target = "_blank";

    var imgAvatar = document.createElement("img");
    imgAvatar.src = image.user_avatar;
    
    var aName = document.createElement("a");
    aName.href = "https://www.facebook.com/quang.thang.0502";
    aName.title = "Thang";
    aName.target = "_blank";
    
    var pName = document.createElement("p");
    pName.appendChild(document.createTextNode(image.user_name));
    
    aAvatar.appendChild(imgAvatar);
    aName.appendChild(pName);
    itemUser.appendChild(aAvatar);
    itemUser.appendChild(aName);
    
    // comment and vote
    var cm_vote = document.createElement("div");
    cm_vote.setAttribute("class", "vote_comment");
    
    // comment
    var divCm = document.createElement("div");
    divCm.setAttribute("class", "comment");
    
    var aCm = document.createElement("a");
    aCm.href = "#";
    aCm.setAttribute("onclick","showComment()");
    var cm_icon = document.createElement("img");
    cm_icon.src = "img/icon/comment.png";
    cm_icon.alt = "comment";
    
    aCm.appendChild(cm_icon);
    divCm.appendChild(aCm);
    cm_vote.appendChild(divCm);
    
    //vote
    var divVote = document.createElement("div");
    divVote.setAttribute("class", "vote");
    
    var aVote = document.createElement("a");
    aVote.href = "#";
    
    var vote_icon = document.createElement("img");
    vote_icon.src = "img/logo/vote.png";
    vote_icon.alt = "vote";
    
    aVote.appendChild(vote_icon);
    divVote.appendChild(aVote);
    cm_vote.appendChild(divVote);
    
    divUser.appendChild(itemUser);
    divUser.appendChild(cm_vote);
    divItem.appendChild(divUser);
        
    document.getElementById("listImage").appendChild(divItem);
}