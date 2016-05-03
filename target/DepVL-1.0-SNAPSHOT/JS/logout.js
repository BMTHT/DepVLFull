/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function logout(){
    $.ajax({
       url: "./LogoutServlet?act=logout",
       type: "POST",
       dataType: "json",
       success: function(data){
           window.location.href="/DepVL";
       },
       erorr: function(a,b,c){
           
       }
        
    });
}
