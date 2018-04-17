$(document).ready(function(){

	//瀑布式布局
	$('.scrollspy').scrollSpy();

	//上传图片前，图片预览
	$(".background-mask > .card > .upload_image").on("change" , function(){
		var files = !!this.files ? this.files : [];
		if (!files.length || !window.FileReader) return;
		if (/^image/.test( files[0].type)){
			var reader = new FileReader();
			console.log(files[0]);
			reader.readAsDataURL(files[0]);
			reader.onloadend = function(){
				thumbnails(this.result);
				isAbleUploadImage();
				keepCardMiddle();
			}
		}
	});//上传图片前，图片预览

	//发布文件的点击事件
	$(".addFile").click(function(){
		addPost();
	});//发布文件的点击事件

	//取消发布的点击事件
	$(".card-action .cancel").click(function(){
		cancelPost();
	});//取消发布的点击事件

	//形成软件类别列表
	getCategoryList_dropdown1();


	//删除已添加的文件的点击事件
	$(".background-mask .card .card-action .fileList").on("click",".item-delete",function(){
		$(this).css({
			"width" : "100%" 
		});
		$(this).parent().css({
			"opacity" : "0",
			"height" : "0" 
		}).attr("data-url","remove");
		keepCardMiddle(30);
	});//删除已添加的文件的点击事件

	//上传图片按钮的点击事件
	$(".background-mask .card .card-action").on("click",".select_image",function(){
		$(".upload_image").click();
	});//上传图片按钮的点击事件

	//添加文件按钮的点击事件
	$(".background-mask .card .card-action").on("click",".con",function(){
		var subName = $(".card-title input").val();
		fileAdded(subName);
	});//添加文件按钮的点击事件
});

//检查是否为空
function check() {
	var key = true;
/*	var input_val = $('.card input');
	for(var i=0;i<input_val.length;i++){
		console.log(input_val[i].val());
		if(!input_val[i].val()){
			key = false;
		}
	}*/
	return key;
}
//判断是否达到上传图片的上限
function isAbleUploadImage() {
	var photos_limit = 3;
	var img_path = $(".background-mask > .card > .card-content > .card-photos");
	var img_thumbnail = img_path.children(".img_thumbnail");
	if (img_thumbnail.length >= photos_limit) {
		img_path.find(".photos_count").css("color","#e66e65");
		$(".background-mask").find(".upload_image").attr("disabled","true");
		$(".background-mask").find(".select_image").css("color","#747474");

	}
	else {
		img_path.find(".photos_count").css("color","#747474");
		$(".background-mask").find(".upload_image").removeAttr("disabled");
		$(".background-mask").find(".select_image").css("color","");
	}

	if (img_thumbnail.length == 0) {
		img_path.children("span").hide();
	};

	img_path.find(".photos_count").text(img_thumbnail.length);
}//判断是否达到上传图片的上限


//添加图片时生成预览图
//@param url (string格式)
//
//
function thumbnails(url) {
	var img_path = $(".background-mask > .card > .card-content > .card-photos");
	var img_box = 
		'<div class="photoDisplay_defualt img_thumbnail" style="background-image:url('+url+')">'+
			'<a class="remove_img" onclick="remove_img(this)">'+
				'<i class="material-icons">close</i>'+
			'</a>'+
		'</div>';
	img_path.append(img_box);

	img_path.children("span").show();
}//判断是否达到上传图片的上限

//使编辑框保持在屏幕中间
function keepCardMiddle(option) {
    option = option || 0;
    var translateY = ($(".background-mask > .card").height()- option) / 2;
    $(".background-mask > .card").css("transform","translateY(-"+translateY+"px)");
}//使编辑框保持在屏幕中间

//添加文件链接时，生成文件列表
//@param file_name (string格式)
//@param file_url (string格式)
//
function fileAdded(file_name) {
	var item_path = $(".background-mask > .card > .card-action > .fileList");
		item = '<div class="item collection-item" data-name="'+file_name+'">'+
			'<div class="item-name">'+file_name+'</div>'+
			'<div class="item-edit"><i class="material-icons">edit</i></div>'+
			'<div class="item-delete"><i class="material-icons">delete</i></div>'+
		'</div>';
	item_path.append(item);
	keepCardMiddle();
}//判断是否达到上传图片的上限


//初始化文件编辑界面
// @param option (json格式)
//          |
//          |---option.title
//          |---option.category
//          |---option.discription
//          |---option.photos
//          |     |
//          |     |---option.photos.link["link1","link2","link3"]
//          |
//          |---option.file
//                |
//                |---option.file.link[{name,fileURL},{name,fileURL},...]

function addPost(option) {

    //渲染表格
    $(".background-mask").css({
        "opacity" : 1,
        "z-index" : 1000,
        "display": "block"
    });

    $(".background-mask > .card").fadeIn(100);
    keepCardMiddle();

    $("body").css("overflow","hidden");
    dropDown();
}


//取消发布执行函数
function cancelPost() {

    //初始化编辑框控件的内容
    if ($(".card-title input").val()) {
        $(".card-title input").val("")
    };
    if ($(".card").find(".input").html()) {
        $(".card").find(".input").html("")
    };
    if ($(".card-photos > div")) {
        $(".card-photos > div").remove();
    };
    if ($(".fileList > .item")) {
        $(".fileList > .item").remove();
    };

    //关闭编辑框的动画
    $(".background-mask > .card").fadeOut(500);
    $(".background-mask > .card").css("transform","translateY(100px)");

    $(".background-mask").css({
        "opacity" : 1,
        "z-index" : 1000,
        "display": "none"
    });

    setTimeout('$(".background-mask").css("z-index","0")',500);

    $("body").css("overflow","visible");
}//取消发布执行函数

//删除已添加的图片
//@param obj (点击事件的对象this)
//传入参数为点击的对象
function remove_img(obj) {
	var img_path = $(obj).parent().parent();
	img_path.find(".photos_count").text(parseInt(img_path.find(".photos_count").text())-1);
	$(obj).parent().remove();
	isAbleUploadImage();
	keepCardMiddle();
}//取消发布


//get category list
//
//@param category (arry格式)
//
//return json
function getCategoryList_dropdown1() {
	var category = [];
    $.ajax({
        type: 'GET',
        url: '/category',
        dataType: 'JSON',
        async: false,
        success: function (content) {
            category = content.category;
            var list = "";

            for (var i = 1 ; i <= category.length ; i++) {
                list = list + '<li data-category="'+i+'"><a>'+category[i-1]+'</a></li>';
            };

            $("#dropdown1").html(list);

        }
    });

}//get category list


//匹配类别
//@param category_num 类别编号
function matchCategory(category_num) {
	return $("#dropdown1 > li[data-category="+category_num+"]").text() || "类 别";
}//匹配类别

var type="";
function dropDown(){
    //console.log("l:"+$("#dropdown1 li").length);
    for(var i = 0; i < $("#dropdown1 li").length; i++) {
        $("#dropdown1 li")[i].onclick = function () {
            var _this = this.getElementsByTagName("a")[0];
            type = _this.innerHTML;
            //console.log("type:" + type);
            $(".dropdown-button").html(type);
            // console.log("first:" + type);
            //this.val();
        }
    }
}
function Send() {
    var area=$('#description').val();
    var reg=new RegExp("\n","g");
    var regSpace=new RegExp(" ","g");

    area= area.replace(reg,"<br>");
    area = area.replace(regSpace,"&nbsp;");
    $('#description').val(area);
    //console.log($("#description").val());
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
        success:function (ret) {
            console.log("ret:"+ret.result);
            if (ret.result.equals("success")) {
                cancelPost();

            }else {
                alert(ret.result);
            }

        }
    })
}


