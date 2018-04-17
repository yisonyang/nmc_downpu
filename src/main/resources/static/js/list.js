/*
 * 开始获取第一页的数据
 */
 //存取后台传过来的json
var oShow;

var prossList;

//传递ID；
var sendID;



//向页面加载数据

ajaxPage();

function sameDate() {
	//list in page	

	var listLength = oShow.length;
	//如果数据传过来大于十五个，那么就只能显示十五个
	if (listLength > 15) {
		listLength == 15;
	}
	var listContent = new String("");
	for (var i = 0 ; i < listLength ; i++) {
		listContent += '<tr>' ;
		listContent += '<td>' + oShow[i].name + '</td>';
		listContent += '<td>'+ oShow[i].url + '</td>';
		listContent += '<td>' + '<a class="btn alter" href="#">' + '修改' + '</a>'
						  + '<a class="waves-effect waves-light btn modal-trigger red deleteList" href="#modal1">' + '删除' + '</a>'
						  + '<div class="modal">' 
						  	+ '<div class="modal-content">' 
						  		+ '<h4>' + '删除此条目' + '</h4>'
						  	+ '</div>'
						  	+ '<div class="modal-footer">'
						  		+ '<a href="#!" class="modal-action modal-close waves-effect waves-green btn-flat">' + '取消' + '</a>'
						  		+ '<a href="#!" class="modal-action modal-close waves-effect waves-green btn-flat removeList">' + '同意' + '</a>'
						  	+ '</div>'
						  + '</div>'
				+ '</td>';
		listContent += '</tr>';
	};
	$("tbody").html(listContent);
	about();
}
function ajaxPage() {
	$.ajax({
		type : "POST",
		url : "/getLists",
		dataType : "json",
		//传number的目的是为了让后台判断传哪些数据
		data : {
			pageNumber : $(".active a").html(),
		},
		success : function(content) {

            oShow = content.content;
            // listShow();
            //
            // //展示list
            // function listShow() {
            //     sameDate();
            // }
            sameDate();
            prossList();
				//成功后加载数据 传15个数据

		},
		error: function(jqXHR) {
			alert("发生错误：" + jqXHR.status);
		}
	});
};

function ajaxSearch(val) {
	$.ajax({
	type : "POST",
	url : "/search_a",
	dataType : "json",
	//传val的目的是为了让后台判断传哪些数据
	data : {
        search : val
	},
	success : function(content, count) {

        //成功后加载数据 传15个数据
		oShow = content.content;
        listShow();

        //展示list
        function listShow() {
            var listLengthPage = content.count;
            sameDate();
            prossList();
            //为列表添加分页
            var pageNumber = Math.ceil(listLengthPage / 15);
            if (pageNumber > 8) {
                pageNumber = 8;
            }
            var pageLi = new String("");
            pageLi += '<li class="disabled"><a href="#!"><i class="material-icons">chevron_left</i></a></li>';
            for (var i = 0; i < pageNumber; i++) {
                pageLi += '<li class="active"><a href="#!">' + (i + 1) + '</a></li>';
            }
            pageLi += '<li class="waves-effect"><a href="#!"><i class="material-icons">chevron_right</i></a></li>';
            $(".pagination").html(pageLi);
        }
    },
	error: function(jqXHR) {
		alert("发生错误：" + jqXHR.status);
	}
	});
}

// function about() {
//     $("a.alter").click(function () {
//         var number;
//         var category = "";
//
//         var kind = 0;
//         var listIndex = $(this).parent().parent().index();
//         var listId = oShow[listIndex].idloaditem;
//         //获取旧数据(ById)
//         $.ajax({
//             type: "POST",
//             url: "/findById",
//             dataType: "json",
//             data: {
//                 id: listId
//             },
//             success: function (content) {
//                 var name=content.content.name;
//                 var intro=content.content.intro;
//                 kind=parseInt(content.content.kind) - 1;
//                 $(".background_mask").show();
//                 $('#intro').html(intro);
//                 $('#name').val(name);
//                 $(".background_mask .cards-title input").focus();
//                 getCategoryList();
//                 function getCategoryList() {
//
//                     $.ajax({
//                         type: 'GET',
//                         url: '/category',
//                         dataType: 'JSON',
//                         async: false,
//                         success: function (content) {
//                             category = content.category;
//                             var list = "";
//
//                             for (var i = 1; i <= category.length; i++) {
//                                 list = list + '<li data-category="' + i + '"><a>' + category[i - 1] + '</a></li>';
//                             };
//                             $("#dropdown2").html(list);
//                             $(".dropdown-content li[data-category]").click(function () {
//                                 var data_category = $(this).attr("data-category");
//                                 var data_name = $(this).text();
//
//                                 //将类别名字显示出来，并绑定将其类别数据绑定在data-category这个属性里
//                                 $(this).parent().prev().text(data_name).attr("data-category",data_category);
//
//                                 number = data_category;
//                             });
//                         }
//                     });
//
//
//                 }//get category list
//
//                 // $('#dropdown2 li').eq(kind).click();
//                 //更新
//                 $(".background_mask .button button").click(function () {
//
//                     var titleName = $(".background_mask .cards-title input").val();
//                     var reg=new RegExp("\n","g");
//                     var regSpace=new RegExp(" ","g");
//                     var discription = $(".background_mask .input").html();
//                     discription=discription.replace(reg,"<br>");
//                     discription= discription.replace(regSpace,"&nbsp;");
//                     console.log(titleName);
//                     console.log(discription);
//                     console.log(number);
//                     if (titleName != "" && discription != "" && number > 0 && number < $("#dropdown2 li").length + 1) {
//                         //ajax 更新信息
//                         $.ajax({
//                             type: "POST",
//                             url: "/edit",
//                             dataType: "json",
//                             data: {
//                                 name: titleName,
//                                 content: discription,
//                                 sort: category[number - 1],
//                                 id: listId
//                             },
//                             success: function (content) {
//                                 if (content.data) {
//                                     $(".background_mask .cards-title input").val("");
//                                     $(".background_mask .input").html("");
//                                     $(".background_mask .cards-content .dropdown-button").text("类别");
//                                     $(".background_mask").css("display", "none");
//                                     // ajaxPage();
//                                     alert("修改成功");
//                                     console.log('有问题');
//                                 }
//                             },
//                             error: function () {
//                                 alert("修改失败，请重试");
//                             }
//                         });
//                     }
//                     else {
//                         alert("请勿留空！");
//                     }
//                 });
//             },
//             error: function () {
//                 alert("请求失败");
//             }
//         });
//         //取消之后恢复原状
//         $(".btn-flat").click(function () {
//             $(".background_mask").hide();
//             $(".background_mask .cards-title input").val("");
//             $(".background_mask .input").html("");
//             $(".cards-content .dropdown-button").text("类别");
//         });
//     });
// };

function about() {
    $(".dropdown-button btn").click(function(){
        getCategoryList();
        //console.log("click");
    })
    function getCategoryList() {
        $.ajax({
            type: 'GET',
            url: '/category',
            dataType: 'JSON',
            async: false,
            success: function (content) {
                var category = content.category;
                var list = "";

                for (var i = 1; i <= category.length; i++) {
                    list = list + '<li data-category="' + i + '"><a>' + category[i - 1] + '</a></li>';
                }
                ;
                $("#dropdown2").html(list);
                $(".dropdown-content li[data-category]").click(function () {
                    var data_category = $(this).attr("data-category");
                    var data_name = $(this).text();

                    //将类别名字显示出来，并绑定将其类别数据绑定在data-category这个属性里
                    $(this).parent().prev().text(data_name).attr("data-category", data_category);
                });
            }
        });
    };
    $('a.alter').click(function(){
        var listIndex = $(this).parent().parent().index();
        var listId = oShow[listIndex].idloaditem;
        sendID = listId;
        $.ajax({
            type: "POST",
            url: "/findById?id="+listId,
            dataType: "json",
            data: {

            },
            success: function (content) {
                //console.log(content);
                var name = content.content.name;
                var intro = content.content.intro;
                // kind = parseInt(content.content.kind) - 1;
                $(".background_mask").show();
                $('#intro').html(intro);
                $('#name').val(name);
                $(".background_mask .cards-title input").focus();
                $('.cards-content>.btn').html(content.content.kind);
                $(".dropdown-button btn").html(content.content.kind);
                // 显示类别
                $("#dropdown2").html(content.content.kind);
                getCategoryList();
            },
            error: function () {
                alert("请求失败");
            }
        });
    });
}

// 向后台发送消息
function Send() {
    var area=$('#description').val();
    var reg=new RegExp("\n","g");
    var regSpace=new RegExp(" ","g");
    area= area.replace(reg,"<br>");
    area = area.replace(regSpace,"&nbsp;");
    $('#description').val(area);
    var type = $("form .dropdown-button").text();
    var data=new FormData($('#itemData')[0]);
    data.append("type",type);
    $.ajax({
        url:"/upload",
        type:"post",
        data:data,
        dataType:"JSON",
        charset:"UTF-8",
        cache:false,
        processData:false,
        contentType:false,
    }).done(function (ret) {
        if (ret['success']){
            alert("Success");
            cancelPost();
        }
        else {
            alert(ret.result);
        }
    })
}
// 向后台发送消息

//当点击的时候向后台请求数据 页面加载后
$(document).ready(function(){
	//搜索,向后台发出请求
	$(".input_area i").click(function() {
		var searchContent = $(".input_area input").val();
		//console.log("asdfghjk");
		//搜索内容不为空
		if (searchContent != "") {
			ajaxSearch(searchContent);
	  	}
	});

    //发布文件的点击事件
    $(".addFile").click(function(){
        addPost();
    });//发布文件的点击事件

    //取消发布的点击事件
    $(".cards-action .cancel").click(function(){
        cancelPost();
    });//取消发布的点击事件

	var paginationLi = $(".pagination li");
	var paginationLiFirstChild = $(".pagination li:first-child");
	var paginationLiLastChild = $(".pagination li:last-child");

	for (var i = 1; i < paginationLi.length - 1; i++) {
		paginationLi[i].onclick = function() {
			$(this).addClass("active");
			$(this).siblings().removeClass("active");
			$(this).find("a").html() == 1 ? $(paginationLiFirstChild).addClass("disabled") : $(paginationLiFirstChild).removeClass("disabled");
			$(this).find("a").html() == (paginationLi.length - 2) ? $(paginationLiLastChild).addClass("disabled") : $(paginationLiLastChild).removeClass("disabled");
			ajaxPage();
		}
	}
	$(paginationLiFirstChild).click(function() {
		var activePrev = $(".pagination li.active");
		var activeNextA = $(".pagination li.active a");

		if ($(activeNextA).html() > 1) {
			if ($(activeNextA.html() != (paginationLi.length - 2))) {
				$(paginationLiLastChild).removeClass("disabled");
			}
			$(this).removeClass("disabled");
			$(activePrev).prev().addClass("active");
			$(activePrev).removeClass("active");
			if ($(activeNextA).html() == 2) {
				$(this).addClass("disabled");
			}
		}
		ajaxPage();

	});

	$(paginationLiLastChild).click(function() {
		var activeNext = $(".pagination li.active");
		var activeNextA = $(".pagination li.active a");

		if ($(activeNextA).html() < (paginationLi.length - 2)) {
			if ($(activeNextA.html() != 1)) {
				$(paginationLiFirstChild).removeClass("disabled");
			}
			$(this).removeClass("disabled");
			$(".pagination li.active").next().addClass("active");
			$(activeNext).removeClass("active");
			if ($(activeNextA).html() == (paginationLi.length - 3)) {
				$(this).addClass("disabled");
			}
		}
		ajaxPage();							
	});
	prossList = function() {
        //处理列表
        var removeList = document.getElementsByClassName("removeList");
        var oTbody = document.getElementsByTagName("tbody")[0];
        var oTr = oTbody.getElementsByTagName("tr");
        var deleteList = document.getElementsByClassName("deleteList");

        for (var i = 0; i < deleteList.length; i++) {
            deleteList[i].index = i;
            deleteList[i].onclick = function () {
                var _this = this;
                _this.nextElementSibling.id = "modal1";
                var model1 = document.getElementById("modal1");
                var aList = model1.getElementsByTagName("a");
                aList[1].onclick = function () {
                    //ajax 传后台要删除的id
                    //oTbody.removeChild(_this.parentNode.parentNode);
                    $.ajax({
                        type: "POST",
                        url: "/delitem",
                        dataType: "json",
                        data: {
                            id: oShow[_this.index].idloaditem
                        },
                        success: function (data) {
                        	if(data) {

                                oTbody.removeChild(_this.parentNode.parentNode);

                                //重新加载数据
                                ajaxPage();
                                prossList();
                            }
                        },
                        error: function (jqXHR) {
                            alert("发生错误：" + jqXHR.status);
                        }
                    });
                };
                aList[0].onclick = function () {
                    _this.nextElementSibling.id = "";
                };
            };
        }
        $('.modal').modal();
    }
    /*if(oShow.length > 0) {
	    about();
    }*/
    // if(oShow) {
    //     about();
    // }
    $(".background_mask .button button").click(function () {
        var titleName = $(".background_mask .cards-title input").val();
        var reg=new RegExp("\n","g");
        var regSpace=new RegExp(" ","g");
        var discription = $(".background_mask .input").html();
        var categorys = $(".cards-content>.btn").attr('data-category');
        var categoryName = $(".cards-content>.btn").html();
        //console.log(categoryName);
        discription=discription.replace(reg,"<br>");
        discription= discription.replace(regSpace,"&nbsp;");
        if (titleName != "" && discription != "") {
            var listId = sendID;
            //ajax 更新信息
            $.ajax({
                type: "POST",
                url: "/edit",
                dataType: "json",
                data: {
                    name: titleName,
                    content: discription,
                    sort: categoryName,
                    id: listId
                },
                success: function (content) {
                    if (content.data) {
                        $(".background_mask .cards-title input").val("");
                        $(".background_mask .input").html("");
                        $(".background_mask .cards-content .dropdown-button").text("类别");
                        $(".cards-content .btn").attr('data-category',0);
                        $(".background_mask").css("display", "none");
                        ajaxPage();
                        alert("修改成功");
                    }
                },
                error: function () {
                    alert("修改失败，请重试");
                }
            });
        }
        else {
            alert("请勿留空！");
        }
    });

    //取消之后恢复原状
    $(".btn-flat").click(function () {
        $(".background_mask").hide();
        $(".background_mask .cards-title input").val("");
        $(".background_mask .input").html("");
        $(".cards-content .dropdown-button").text("类别");
    });

});

