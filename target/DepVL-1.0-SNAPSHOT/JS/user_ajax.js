/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function getUserSession() {
    var url = "./LoginServlet?get=session";
    var user;
    $.ajax({
        url: url,
        type: 'GET',
        dataType: "JSON",
        async: false,
        success: function (data) {
            user = data;
        },
        error: function (a, b, c) {
            console.log(a + b + c);
        }
    });
    return user;
}

function getUser(userid) {
    var url = "./UserServlet?userid=" + userid;
    var user;
    $.ajax({
        url: url,
        type: "GET",
        dataType: "JSON",
        async: false,
        success: function (data) {
            
            user = data;
            
        },
        error: function (a,b,c) {

        }

    });
    return user;
}
