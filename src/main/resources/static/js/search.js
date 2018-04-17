$(document).ready(function(){
    $('.item_download_link').click(function(){
        $('.item_links').css("height","auto");
    });

    $('.item_links > .item_download_link[type=1]').click(function(){
        $(this).parent().css("height","200px");
        $(this).parent().css("border-bottom","1px solid rgba(153,153,153,0.3)");

        $(this).children().css("float","none");
        $(this).children().css("width","25px");
        $(this).children().css("height","4px");
        $(this).attr("type","2");
    });

    $('.item_links > .item_download_link[type=2]').click(function(){
        $(this).parent().css("height","1px");
        $(this).parent().css("border-bottom","none");
        $(this).children().css("float","left");
        $(this).children().css("width","4px");
        $(this).children().css("height","4px");
        $(this).attr("type","1");
    });

    $(".search_result .item_name").on("click" , ".item" , function(){
        $(".search_list").css({
            "opacity" : "0" ,
            "transform" : "translateX(-100px)"
        })
        setTimeout(function(){
            $(".search_list").css("position","absolute");
            itemDetail();            
        },300);
    });
    $(".search_result .item_name").on("click", ".material-icons",function(e) {
        var content1 = new String("");
        var index = $(this).attr("index");
        for (var i = 0; i < result[index].links.length; i++) {
            content1 += '<li>';
            content1 += '<span>' + result[index].links[i].name + '</span><a href="'+result[index].links[i].link +'" class="waves-effect waves-green btn-flat float_right">下载</a></div>';
            content1 += '</li>';
        };
        $("#modal1 .modal-content ul").html(content1);
        $('.modal').fadeIn(500);

    })
    $(".modal").on("click",".modal-close",function() {
        $('#modal1').fadeOut(500);
        $("#modal1 .modal-content ul li").remove(); //清除dom
    })
    $("body > .item .backTolist").on("click",function(){
        $("body > .item").css({
            "opacity" : "0" ,
            "transform" : "translateY(100px)",
        })
        $(".search_list").css({
            "position" : "relative" ,
            "opacity" : "1" ,
            "transform" : "translateX(0px)"
        });

        setTimeout(function(){
            $("body > .item").css({
                "position" : "absolute" ,
                "z-index" : "-1"
            })
        },500)
    });
});

//罗列搜索结果
//@param search_result
//            |
//            |---search_result.name
//            |---search_result.postTime
//            |---search_result.discription
//            |---search_result.links
//            |---search.file
//                  |
//                  |---search.file.links[{name,fileURL},{name,fileURL}]


function listResult(search_result) {
    //example
    var search_value = window.location.search;
    search_value = search_value.substring(1, search_value.length);  
    
    /*$.ajax({
        url: '',
        type: 'POST',
        data: JSON.stringify(search_value),
        success:function(data) {
            var search_result = data;
        }

    })*/
    var result = search_result;
    //console.log(result);
    var content = new String("");
    for (var i = 0 ; i < result.length ; i++) {
        content += '<li>';
        content += '<div class="item_name"><span class="item">' + result[i].name + '</span><i class="material-icons float_right"  index = "'+ i +'">file_download</i></div>';
        content += '<div class="item_postTime">'+result[i].postTime+'</div>';
        content += '<div class="item_discription">'+result[i].discription+'</div>';
        content += '</li>';

    };

    $(".search_list > .search_result > ul").html(content).hide().slideDown();
    
    $(".search_list > .resultCount > i").text(result.length);
}//罗列搜索结果

//详情展示
//@param detail (json格式)
//          |
//          |---detail.name
//          |---detail.postTime
//          |---detail.size
//          |---detail.discription
//          |---detail.photos
//                      |
//                      |---detail.photos.links["...","...",...]
//          |---detail.file
//                  |
//                  |---detail.file.links[{name,fileURL},{name,fileURL}]
//
function itemDetail(detail) {
    $("body > .item").css({
        "opacity" : "1" ,
        "transform" : "translateY(0px)",
        "position" : "relative" ,
        "z-index" : "1"
    });

    //example
    detail = {
        name : "Microsoft word" ,
        postTime : "2017 10 30" ,
        size : "xxxxxxx Mb" ,
        discription : "Microsoft word is the best document editor in the world" ,
        photos : {
            links :["images/sample-1.jpg","images/sample-2.jpg","images/sample-3.jpg"],
        },
        files : {
            links : [
                {
                    name : "Microsoft word 2016 x86" ,
                    fileURL : "..."
                },
                {
                    name : "Microsoft word 2016 64" ,
                    fileURL : "..."
                }
            ]
        }
    }

    var itemPath = $("body > .item");
    itemPath.children(".item_base_info").children(".item_avatar").text(detail.name[0]);
    itemPath.children(".item_base_info").children(".item_name").text(detail.name);
    itemPath.children(".item_base_info").children(".item_postTime").text(detail.postTime);
    itemPath.children(".item_base_info").children(".item_size").text(detail.size);

    itemPath.children(".item_description").text(detail.discription);

    if (detail.photos) {
        var photos = new String("");
        for (var i = 0 ; i < detail.photos.links.length ; i++) {
            photos += '<img class="materialboxed" src="'+detail.photos.links[i]+'">';
        };
        itemPath.children(".item_photos").html(photos);
        $('.materialboxed').materialbox();
    };

    if (detail.files) {
        var files = "";
        for (var i = 0 ; i < detail.files.links.length ; i++) {
            files += '<div class="row"><span class="col s10">'+detail.files.links[i].name+'</span><a class="waves-effect waves-light btn col s2" files-url="'+detail.files.links[i].fileURL+'">下载</a></div>';
        };
        itemPath.children(".item_links").html(files);
    };
}//详情展示

