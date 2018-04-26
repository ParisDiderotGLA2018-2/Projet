
function getServerData(url, success){
    $.ajax({
    	contentType: "application/json; charset=utf-8",
        dataType: "json",
        url: url
    }).done(success);
}

