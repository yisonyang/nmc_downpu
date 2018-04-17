/*
 * 开始获取第一页的数据
 */
 //存取后台传过来的json
var oShow;
//总页数
var num;

var content_category;


ajaxPage(); // 向后台请求系统安全第一页数据数据
ajaxCategory();

var asideContent = function(content_category) {
	var content = content_category;
	var list = '';
	for(var i = 0; i < content.length; i++) {
		list += '<li><a>' + content[i] + '</a></li>';
	}
	$("#nav-mobile").html(list);
    if($("#nav-mobile li a.active").html() == undefined) {
        $('#nav-mobile li a').eq(0).addClass('active');
    }
}

function aside() {
	$('#nav-mobile li a').click(function() {
		$(this).addClass('active');
		$(this).parent().siblings().find('a').removeClass('active');
		ajaxPage();
	})
}	

function sameDate() {
	//list in page	
	var numb = num;
	numb = num > 8?8:numb;
	var listLength = oShow.length;
	//如果数据传过来大于十五个，那么就只能显示十五个
	if (listLength > 15) {
		listLength == 15;
	}
	var listContent = new String("");
	for (var i = 0 ; i < listLength ; i++) {
		oShow[i].intro = oShow[i].intro.replace(/&nbsp;/g, "").replace(/\<br\>/g,"").substring(0, 20) + '...';
		listContent += '<tr>' ;
		listContent += '<td>' + oShow[i].name + '</td>';
		listContent += '<td>'+ oShow[i].intro + '</td>';
		listContent += '<td><a class="waves-effect waves-light btn btn_behaviour">下载</a></td>';
		listContent += '</tr>';
	};
	$("tbody").html(listContent);
	var tableWidth = $(window).width() - 300;
	tableWidth = tableWidth > 992?tableWidth:992;
	$('.content .row table').css({'width': tableWidth + 'px'}, {'position': 'absolute'});
	var page = '';
	page += '<li class="disabled"><a href="#!"><i class="material-icons">chevron_left</i></a></li>';
	for(var i = 1; i <= numb ; i++) {
		page += '<li><a href="#!">' + i +'</a></li>'
	}
	page += '<li class="waves-effect"><a href="#!"><i class="material-icons">chevron_right</i></a></li>';
	$('.pagination').html(page);
	if(numb != 0) {
        $('.pagination li').eq(1).addClass('active');
	}
	if(numb == 0 || numb == 1) {
		$('.pagination li:last-child').addClass('disabled');
	}
	pageBlock(numb);
	intro();// 弹出弹框
}
function ajaxCategory() {
    $.ajax({
        type : "POST",
        url : "/category",
        dataType : "json",
        //传number的目的是为了让后台判断传哪些数据
        data : {
            type : $("#nav-mobile li a.active").html() || '',
			number: 0
        },
        success : function(category) {
            content_category = category.category;
            asideContent(content_category);
            aside();
        },
        error: function(jqXHR) {
            alert("发生错误：" + jqXHR.status);
        }
    });
}
function ajaxPage() {
	var pageNum = parseInt($(".active a").html()) || 0;
	$.ajax({
		type : "POST",
		url : "/sortByKind",
		dataType : "json",
		//传number的目的是为了让后台判断传哪些数据
		data : {
			type : $("#nav-mobile li a.active").html() || '',
			number : pageNum
		},
		success : function(result) {
						// 请求得到数据和页码
            oShow = result.result.content.content;
            // 全页码
            num = result.result.content.totalPages;
			//content_category = result.result.category;
            sameDate();
		},
		error: function(jqXHR) {
			alert("发生错误：" + jqXHR.status);
		}
	});
};
// 判断页数 直接点击时候
function judgePage(listIndex, numb) {
	function activeLi() {
			for(var j = 1; j <= numb; j++) {
					var index = $(".pagination li").eq(j).find('a').html();
					if(index == listIndex) {					
						$(".pagination li").eq(j).addClass('active');
						$(".pagination li").eq(j).siblings().removeClass('active');
					}
				}
		}
		if(num > 8) {
			if(num - listIndex <= 3) {
				for(var j = num, m = 8; j >= num - 7, m >= 1; j--, m--) {
					$(".pagination li").eq(m).find('a').html(j);
				}
				activeLi();
			}

			else if(listIndex <= 4) {
				for(var j = 1; j <= 8; j++) {
					$(".pagination li").eq(j).find('a').html(j);
				}
				activeLi();
			}
			else {
				for(var j = listIndex - 3, m = 1; j <= listIndex + 4, m <= 8; j++, m++) {
					$(".pagination li").eq(m).find('a').html(j);
				}
				activeLi();
			}
		}
}

// 判断页数 前一页
function judgePagePrev(listIndex, activePrev) {
	if(num > 8) {
		if(num - listIndex <= 3  || listIndex <= 4 && listIndex > 1) {
			$(activePrev).prev().addClass("active");
	    $(activePrev).removeClass("active");
		}
		else if(listIndex == 5) {
			for(var j = 1; j <= 8; j++) {
				$(".pagination li").eq(j).find('a').html(j);
			}
			$(".pagination li").eq(listIndex-1).addClass('active');
			$(".pagination li").eq(listIndex-1).siblings().removeClass('active');
		}

		else if(num - listIndex > 3 && listIndex > 4){
			for(var j = listIndex - 4, m = 1; j <= listIndex + 3, m <= 8; j++, m++) {
				$(".pagination li").eq(m).find('a').html(j);
			}
			for(var j = 1; j <= 8; j++) {
				if($(".pagination li").eq(j).find('a') == listIndex - 1) {
					$(".pagination li").eq(j).addClass('active');
					$(".pagination li").eq(j).siblings().removeClass('active');
				}
			}
		}
	}
}

function pageBlock(numb) {
	var paginationLi = $(".pagination li");
	var paginationLiFirstChild = $(".pagination li:first-child");
	var paginationLiLastChild = $(".pagination li:last-child");

	for (var i = 1; i <= numb ; i++) {
		paginationLi[i].onclick = function() {
			var listIndex = $(this).find('a').html();
			$(this).find("a").html() == 1 ? $(paginationLiFirstChild).addClass("disabled") : $(paginationLiFirstChild).removeClass("disabled");
			$(this).find("a").html() == num ? $(paginationLiLastChild).addClass("disabled") : $(paginationLiLastChild).removeClass("disabled");
			judgePage(listIndex, numb);
			ajaxPage();
		};
	}

	//前一页
	$(paginationLiFirstChild).click(function() {
		var activePrev = $(".pagination li.active");
		var activeNextA = $(".pagination li.active a");
		var listIndex = activeNextA.html();

		if ($(activeNextA).html() > 1) {
			if ($(activeNextA).html() != num) {
				$(paginationLiLastChild).removeClass("disabled");
			}
			$(this).removeClass("disabled");
			if ($(activeNextA).html() == 2) {
				$(this).addClass("disabled");
			}
		}
		judgePagePrev(listIndex, activePrev);
		ajaxPage();
	});

	//后一页
	$(paginationLiLastChild).click(function() {
		var activeNext = $(".pagination li.active");
		var activeNextA = $(".pagination li.active a");
		var listIndex = activeNextA.html();
		if (listIndex >= 1) {
				$(paginationLiFirstChild).removeClass("disabled");
				if(listIndex == num - 1) {
					$(this).addClass("disabled");
				}
		}
		if(num > 8) {
			if(num - listIndex <= 4 && num - listIndex >=1 || listIndex <= 3) {
				$(activeNext).next().addClass("active");
		    $(activeNext).removeClass("active");
			}
			else if(num - listIndex > 4 && listIndex > 3) {
				for(var j = listIndex - 2, m = 1; j <= listIndex + 5, m <= 8; j++, m++) {
					$(".pagination li").eq(m).find('a').html(j);
				}
				for(var j = 1; j <= 8; j++) {
					if($(".pagination li").eq(j).find('a') == listIndex + 1) {
						$(".pagination li").eq(j).addClass('active');
						$(".pagination li").eq(j).siblings().removeClass('active');
					}
				}
			}
		}
		ajaxPage();					
	});
}

//查找时向后台发出请求
function ajaxSearch(val) {
	$.ajax({
	type : "POST",
	url : "/search_a",
	dataType : "json",
	//传val的目的是为了让后台判断传哪些数据
	data : {
        search : val
	},
	success : function(content) {
		//注意传三个值
    // 请求得到数据和页码
    oShow = content.content;
    // 全页码
    num = content.number;
    for(var i = 0; i < $('#nav-mobile li').length; i++) {
    	if($('#nav-mobile li a').eq(i).html() == content.type) {
    		$('#nav-mobile li a').eq(i).addClass('active');
    	}
    }
    $('.side-nav').find('a.active').removeClass('active');
    sameDate();
  },
	error: function(jqXHR) {
		alert("发生错误：" + jqXHR.status);
	}
	});
}

//简介及下载的ajax
function ajaxIntro(idnum, judege) {
	$.ajax({
	type : "POST",
	url : "/findById",
	dataType : "json",
	//传val的目的是为了让后台判断传哪些数据
	data : {
        id : idnum,
	},
	success : function(content) {
		console.log(content);
		if(judege == 1) {
			var name = content.content.name;
	    	var intro = content.content.intro;
	    	var url = content.content.image;
	    	introduce(name, intro, url); //弹出简介
		} else if(judege == 2){
			var url = content.url;
			download(url);
		}
    
  },
	error: function(jqXHR) {
		alert("发生错误：" + jqXHR.status);
	}
	});
}

//显示简介
function intro() {
	$("tbody tr td").click(function() {
		var introList = $(this).parent().index();
		var judege = $(this).index();
		var idnum = oShow[introList].idloaditem;
		ajaxIntro(idnum, judege);
	})
}
//
// 在简介里构造函数
// introduce();
function introduce(name, intro, url) {
	$(".background_mask").show();
	var text = '<h3>' + name + '</h3>';
	var content = '<p>' + intro + '</p>';
    $(".mask_size").append(text, content);
    var links=url.split(',');
    for (var i = 0 ; i < links.length-1; i++) {
        var img = '<img src="' + links[i] + '" alt="' + name  + '">';
        $(".mask_size").append(img);
    };

	$(".mask_size img").alt = 'Matlab';
	$(".mask_size img").css({
		'width': '300px', 
		'height': '150px',
		'margin-bottom': '25px'
	});
	closeWindow();
}


function download(url) {
    $(".background_mask").show();
	var text = '<h4>选择要下载的版本</h4>';
	var load = '<ul>';
	for(var i = 0; i < url.length; i++) {
		load += '<li><span>' + url[i].name + '</span>';
		load += '<a href="' + url[i].fileURL + '">下载</a></li>';
	}
	load += '</ul>';
	$(".mask_size").append(text, load);
	closeWindow();
}

function closeWindow() {
	$(document).click(function(e) {
        window.event? window.event.cancelBubble = true : e.stopPropagation();
        $(".mask_size").children().remove();
		$(".background_mask").hide();
	});
	$(".mask_size").click(function(e) {
        window.event? window.event.cancelBubble = true : e.stopPropagation();
		$(".background-mask").show();
	});
}

$(document).ready(function(){
	var search = $(".three_input_area input").val();
	//搜索,向后台发出请求
	function searchCon() {
        var searchContent = $(".three_input_area input").val();
        //搜索内容不为空
        if (searchContent != "" && search != searchContent) {
            ajaxSearch(searchContent);
        };
        return searchContent;
	}

	$(".three_input_area i").click(function() {
		search = searchCon();

	});
	$(".three_input_area input").focus(function () {
		$(this).keypress(function (e) {
			var event = window.event ? window.event:e;
			if(event.keyCode == 13) {
                search = searchCon();
			}
        })
    })
});
