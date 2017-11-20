
var waterFall = {
    content:document.getElementById('content'),    //存放内容的容器
    list:document.getElementById('list'),   //将要展示的列表容器

    setOptions:function(options){
        options = options || {};
        this.colNum = options.num || 4;   //显示的列数，默认显示3列 
        this.colWidth = options.width || 250;   //每列的宽度
    },
    
    //构建列数
    setColumn:function(){
        var self = this;
        var html = '';
        for(var i=0,l=self.colNum;i<l;i++){
            html += '<li style="width:'+ self.colWidth +'px;"></li>';
        }
        self.list.innerHTML = html;
        
        self.column = self.list.getElementsByTagName('li');
    },
    
    //计算最小高度的列
    setMinHeightCol:function(){
        var self = this;
        var heiArray = [];
        var minIndex = 0,index  = 1;
        for(var i=0,l=self.colNum;i<l;i++){
            heiArray[i] = self.column[i].offsetHeight;
        }
        while(heiArray[index]){
            if(heiArray[index] < heiArray[minIndex]){
                minIndex = index;
            }
            index ++;
        }
        return self.column[minIndex];
    },
    
    //填充内容
    setCont:function(cnt){
        var self = this;
        self.setMinHeightCol().appendChild(cnt);
        if(!!self.content.children[0]){
            self.setCont(self.content.children[0]);
        }
    },
    
    init:function(options){
        var self = this;
        window.onload = function(){
            self.setOptions(options);
            self.setColumn();
            self.setCont(self.content.children[0]);
        }
    }
};

waterFall.init();//使用初始化参数 waterFall.init({num:4,width:100});
