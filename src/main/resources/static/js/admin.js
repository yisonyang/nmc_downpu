$(document).ready(function(){

	//瀑布式布局
	$('.scrollspy').scrollSpy();

	//上传图片前，图片预览
	$(".background-mask > .card > .upload_image").on("change" , function(){
		var files = !!this.files ? this.files : [];
		if (!files.length || !window.FileReader) return;
		if (/^image/.test( files[0].type)){
			var reader = new FileReader();
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
	getCategoryList();


	//软件类别选择
	$(".card #dropdown1 > li").click(function(){
		var category = $(this).text();
		var data_category = $(this).attr("data-category");
		$(this).parent().prev().text(category).attr("data-category",data_category);
	});//软件类别选择

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
	$(".background-mask .card .card-action").on("click",".select_fileURL",function(){
		fileAdded("demo","...");
	});//添加文件按钮的点击事件

});


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


//添加文件链接时，生成文件列表
//@param file_name (string格式)
//@param file_url (string格式)
//
function fileAdded(file_name , file_url) {
	var item_path = $(".background-mask > .card > .card-action > .fileList");
	var item = 
		'<div class="item collection-item" data-name="'+file_name+'" data-url="'+file_url+'">'+
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
			"opacity" : "1" ,
			"z-index" : "1000"
	});

	$(".background-mask > .card").fadeIn(100);
	keepCardMiddle();	

	$(".background-mask > .card").find(".card-title input").focus();

	$("body").css("overflow","hidden");
	//结束

	//Example 传入数据option例子
	option = {
		title : "Microsoft Word" ,
		category : "2",
		discription : "Microsoft word is the best document editor",
		photos : {
			link : ["images/sample-1.jpg","images/sample-2.jpg","images/sample-3.jpg"]
		},
		files : {
			link : [{name:"Microsoft word 2016 x86",fileURL:"..."},{name:"Microsoft word 2016 64",fileURL:"..."}]
		}
	}
	//End here

	//往编辑框里面填内容
	//若option为空，则填空，若不为空，则填option的内容
	option = option || {};
	var title = option.title || "" ;
	var category = option.category || "0" ;
	var discription = option.discription || "" ;

	$(".card-title input").val(title);
	$(".card-content .dropdown-button").text(matchCategory(category));
	$(".card").find(".input").html(discription);

	if (option.photos) {
		for (var i = 0 ; i < option.photos.link.length ; i++) {
			thumbnails(option.photos.link[i]);
			isAbleUploadImage();
			keepCardMiddle();
		};
	};

	if (option.files) {
		for (var i = 0 ; i < option.files.link.length ; i++) {
			fileAdded(option.files.link[i].name,option.files.link[i].fileURL);
			keepCardMiddle();
		};
	};
}//初始化文件编辑界面


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

	isAbleUploadImage();


	//关闭编辑框的动画
	$(".background-mask > .card").fadeOut(500);
	$(".background-mask > .card").css("transform","translateY(100px)");

	$(".background-mask").css({
		"opacity" : "0" 
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


//使编辑框保持在屏幕中间
function keepCardMiddle(option) {
	option = option || 0;
	var translateY = ($(".background-mask > .card").height()- option) / 2;
	$(".background-mask > .card").css("transform","translateY(-"+translateY+"px)");
}//使编辑框保持在屏幕中间


//get category list
//
//@param category (arry格式)
//
//return json
function getCategoryList() {
	//example
	var category = ['系统安全','网络','数学','影音音频'];
	var list = "";

	for (var i = 1 ; i <= category.length ; i++) {
		list = list + '<li data-category="'+i+'"><a>'+category[i-1]+'</a></li>';
	};

	$("#dropdown1").html(list);
}//get category list


//匹配类别
//@param category_num 类别编号
function matchCategory(category_num) {
	return $("#dropdown1 > li[data-category="+category_num+"]").text() || "类 别";
}//匹配类别


