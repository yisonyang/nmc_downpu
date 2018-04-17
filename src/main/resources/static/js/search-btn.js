window.onload = function () {
    document.querySelector('.button').addEventListener('click',function () {
        var val = document.getElementById('text').value;
        if(!val){
            return;
        }
        else{
            var url = "search.html?"+val;
            window.location.href = url;
        }
    })
    
}