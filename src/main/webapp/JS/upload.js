/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

$(function () {
    $('#uploadfile').submit(function (e) {
        var data = new FormData(document.getElementById("form_upload"));
        var url = "./UploadServlet";
        $.ajax({
            url: url,
            data: data,
            dataType: 'json',
            type: 'POST',
            async: false,
            success: function (data) {
               if(data.message == "success"){
                   alert("Upload Thanh cong");
                   windown.location.href="/DepVL";
               }
            },
            error: function (data) {

            },
            cache: false,
            contentType: false,
            processData: false
        });

        e.preventDefault();
        return false;
    });
});

