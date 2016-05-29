/**
 * Created by zison on 2016/5/9.
 */

$(document).ready(function () {
    $(".chooseFile").hide();
    $(".broacastIP").hide();

});



//当前页
var curPage = 0;
//同步状态，初始值为0，代表同步关闭状态
var syncTip = 0;
var scrollTopValue = 100;
$(document).ready(function() {

    $("#broacast").mouseover(function () {
        $("#broacast").css("background-color", "yellow");
    });

    $("#choose").mouseover(function () {
        $("#choose").css("background-color", "yellow");
    });

    $("#syncControl").mouseover(function () {
        $("#syncControl").css("background-color", "yellow");
    });

    $("#syncControl").mouseleave(function () {
        $("#syncControl").css("background-color", "#adadad");
    });

    $("#broacast").mouseleave(function () {
        $("#broacast").css("background-color", "#adadad");
    });

    $("#choose").mouseleave(function () {
        $("#choose").css("background-color", "#adadad");
    });

    $("#syncControl").click(function () {
        if(syncTip == 0) {
            $("#syncControl").html("[Sync Close]");
            syncTip = 1;
        }
        else {
            $("#syncControl").html("[Sync Open]");
            syncTip = 0;
        }

    });

    $("#choose").click(function () {
        $(".chooseFile").show();
    });

    $("#broacast").click(function () {
        $(".broacastIP").show();
    });

    $("#broacast").click(function () {
        document.getElementById("ips").options.length = 0;
        $.get("/IP/getIP", function (data, status) {
            for (var i = 0; i < data.length; i++) {
                document.getElementById("ips").options.add(new Option(data[i] + "", data[i] + ""));
            }
        });
    });

    $(".confirm").click(function () {
        var obj = document.getElementById("ips"); //定位id

        var index = obj.selectedIndex; // 选中索引

        var text = obj.options[index].text; // 选中文本

        $.get("/IP/broadcastIP?ip=" + text);
        $(".broacastIP").hide();
    });

    $(".cancle").click(function () {
        $(".chooseFile").hide();
        $(".broacastIP").hide();
    });

    //全屏事件
    jQuery(document).on("click touchstart",
        ".fullScreen", function () {

            var elem = $(".pptimg")[0];
            if (!document.fullscreenElement && !document.mozFullScreenElement
                && !document.webkitFullscreenElement && !document.msFullscreenElement)
                if (elem.requestFullscreen)elem.requestFullscreen();
                else if (elem.msRequestFullscreen)elem.msRequestFullscreen();
                else if (elem.mozRequestFullScreen)elem.mozRequestFullScreen();
                else {
                    if (elem.webkitRequestFullscreen)elem.webkitRequestFullscreen()
                } else if (document.exitFullscreen)document.exitFullscreen();
            else if (document.msExitFullscreen)document.msExitFullscreen();
            else if (document.mozCancelFullScreen)document.mozCancelFullScreen();
            else if (document.webkitExitFullscreen)document.webkitExitFullscreen()
        });

    //图片列表点击事件
    $(document).on("click", ".left_ul > li > img", function() {
        var ppturl = $(this).attr("ppturl");
        var syncurl = $(this).attr("syncurl");
        var img = document.getElementById("slide");
        img.setAttribute("src", ppturl);

        curPage = $(this).attr("class");

        $(".left_ul > li >img").css("border", "none");
        $(this).css("border","2px solid  red");

        if(syncTip == 1) {
            $.get(syncurl);
        }
    });

    //下一页
    $(".arrow-r").click(function() {

        var pageNum = $(this).attr("pageNum");

        if(curPage < pageNum - 1) {
            curPage++;
            scrollTopValue += 150;
        }
        $("."+curPage).click();

        //$(".left").scrollTop(scrollTopValue);

    });

    //上一页
    $(".arrow-l").click(function() {
        if(curPage > 0) {
            curPage -- ;
            scrollTopValue -= 150;
        }
        $("."+curPage).click();
    });
    //$(".left").scrollTop(scrollTopValue);



});