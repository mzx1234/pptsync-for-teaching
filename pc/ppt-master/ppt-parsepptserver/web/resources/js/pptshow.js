/**
 * Created by zison on 2016/5/9.
 */

$(document).ready(function () {
    $(".chooseFile").hide();
    $(".broacastIP").hide();

});



//��ǰҳ
var curPage = 0;
//ͬ��״̬����ʼֵΪ0������ͬ���ر�״̬
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
        var obj = document.getElementById("ips"); //��λid

        var index = obj.selectedIndex; // ѡ������

        var text = obj.options[index].text; // ѡ���ı�

        $.get("/IP/broadcastIP?ip=" + text);
        $(".broacastIP").hide();
    });

    $(".cancle").click(function () {
        $(".chooseFile").hide();
        $(".broacastIP").hide();
    });

    //ȫ���¼�
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

    //ͼƬ�б����¼�
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

    //��һҳ
    $(".arrow-r").click(function() {

        var pageNum = $(this).attr("pageNum");

        if(curPage < pageNum - 1) {
            curPage++;
            scrollTopValue += 150;
        }
        $("."+curPage).click();

        //$(".left").scrollTop(scrollTopValue);

    });

    //��һҳ
    $(".arrow-l").click(function() {
        if(curPage > 0) {
            curPage -- ;
            scrollTopValue -= 150;
        }
        $("."+curPage).click();
    });
    //$(".left").scrollTop(scrollTopValue);



});