/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function profile(userid){
    var user = getUser(userid);
    var title = document.getElementById("title");
    title.appendChild(document.createTextNode(user.nickName));
    var nickname = document.getElementById("nickname");
    nickname.appendChild(document.createTextNode(user.nickName));
    var avatar = document.getElementById("avatar");
    avatar.src = user.avatarUrl;
    var fullname = document.getElementById("fullname");
    fullname.appendChild(document.createTextNode(user.fullName));
    var gender = document.getElementById("gender");
    var sex = userid.gender == 1 ? "Nam" : "Nu";
    gender.appendChild(document.createTextNode(sex));
    var birthday = document.getElementById("birthday");
    birthday.appendChild(document.createTextNode(user.birthDay));
    var email = document.getElementById("email");
    email.appendChild(document.createTextNode(user.email));
    var phonenumber = document.getElementById("phonenumber");
    phonenumber.appendChild(document.createTextNode(user.phoneNumber));
    var address = document.getElementById("address");
    address.appendChild(document.createTextNode(user.address));
    var point = document.getElementById("point");
    point.appendChild(document.createTextNode(getPointAuthor(userid)));
}

function getPointAuthor(userid){
    var point;
    var url = "./PointServlet?author="+userid;
    $.ajax({
       url: url,
       type: 'GET',
       dataType: "JSON",
       async: false,
       success:function(data){
           point = data.point;
       },
       error: function(a,b,c){
           
       }
    });
    return point;
}
