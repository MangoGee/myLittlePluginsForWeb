jQuery(document).ready(function () {
	$(this).rechargeableareaCheckbox();

	G.init.btn();

	$("#sys-rechargeablearea").click(function () {
		$("#sys-recharge").attr('disabled',"true");
		func_rechargeablearea();
		// $(this).parents("div#rechargeablearea").modal("hide");
	});

	//添加省份label自定义属性，包含选择的城市数量
	var aItembox = $(".itembox");
	for (var i = 0; i < aItembox.length; i++) {
		aItembox.eq(i).siblings("label").attr("oCount", 0);
	}

	//地区
	var aRegion = $("#region").children();
	for (var i = 0; i < aRegion.length; i++) {
		aRegion.eq(i).children().children().eq(0).click( function () {
			var arrProvince = $(this).siblings("div").find("li.province");
			var aCheckbox = $(this).siblings("div").find("input");
			if( $(this).prop("checked") ) {
				for (var j = 0; j < aCheckbox.length; j++) {
					aCheckbox.eq(j).prop("checked", true);
				}
				for (var j = 0; j < arrProvince.length; j++) {
					var oLabel = arrProvince.eq(j).children("label").html().match(/[\u4e00-\u9fa5]+/);
					var allCount = arrProvince.eq(j).children("div").find("input").length;
					arrProvince.eq(j).children("label").attr("oCount", allCount);
					arrProvince.eq(j).children("label").html( oLabel + "(" + arrProvince.eq(j).children("label").attr("oCount") + ")");
				}	
			} else {
				for (var j = 0; j < aCheckbox.length; j++) {
					aCheckbox.eq(j).prop("checked", false);
				}
				for (var j = 0; j < arrProvince.length; j++) {
					var oLabel = arrProvince.eq(j).children("label").html().match(/[\u4e00-\u9fa5]+/);
					arrProvince.eq(j).children("label").attr("oCount", 0);
					arrProvince.eq(j).children("label").html(oLabel);
				}
			}
		} );
	}

	//省份
	var aProvince = $('.province');
	for (var i = 0; i < aProvince.length; i++) {
		$('.province').eq(i).children("input").eq(0).click( function () {
			var aCity = $(this).siblings("div").find("input");
			var provinceLabel =  $(this).siblings("label");
			var oLabel = provinceLabel.html().match(/[\u4e00-\u9fa5]+/);
			if ( $(this).prop("checked") ) {
				for (var j = 0; j < aCity.length; j++) {
					aCity.eq(j).prop("checked", true);
					provinceLabel.attr("oCount", function () {
						var count = parseInt(provinceLabel.attr("oCount"));
						count += 1;
						provinceLabel.attr("oCount", count);
					})
				}
				provinceLabel.html(oLabel + "(" + provinceLabel.attr("oCount") + ")");
				if (oOldItembox && $(oOldItembox).siblings("label").html()!=$(this).siblings("label").html()) {
					oOldItembox.fadeOut("fast", function () {
						oOldItembox.addClass("hideItembox");
					});
				}
			} else {
				for (var j = 0; j < aCity.length; j++) {
					aCity.eq(j).prop("checked", false);
				}
				$(this).parents("div.provinceDiv").siblings("input").prop("checked", false);
				provinceLabel.html(oLabel);
				provinceLabel.attr("oCount", 0);
				if (oOldItembox && $(oOldItembox).siblings("label").html()!=$(this).siblings("label").html()) {
					oOldItembox.fadeOut("fast", function () {
						oOldItembox.addClass("hideItembox");
					});
				}
			}
		} );
	}

	//城市
	for (var i = 0; i < aItembox.length; i++) {
		var aCityCheckbox = aItembox.eq(i).find("input");
		for (var j = 0; j < aCityCheckbox.length; j++) {
			aCityCheckbox.eq(j).click( function () {
				var oLabel = $(this).parents("div.itembox").siblings("label").html().match(/[\u4e00-\u9fa5]+/);
				var aCitybox = $(this).parents("div.itembox").find("input");

				//计算对应省份的城市数量属性
				if ($(this).prop("checked")) {
					var oCount = parseInt($(this).parents("div.itembox").siblings("label").attr("oCount"));
					oCount += 1;
					$(this).parents("div.itembox").siblings("label").attr("oCount", oCount);
				} else {
					var oCount = parseInt($(this).parents("div.itembox").siblings("label").attr("oCount"));
					oCount -= 1;
					$(this).parents("div.itembox").siblings("label").attr("oCount", oCount);
					$(this).parents("div.provinceDiv").siblings("input").prop("checked", false);
				}

				//满选勾上对象的省份，否则不勾
				for (var k = 0; k < aCitybox.length; k++) {
					if (k==aCitybox.length-1 && aCitybox.eq(k).prop("checked")) {
						aCitybox.eq(k).parents("div.itembox").siblings("input").prop("checked", true);
					} else if (aCitybox.eq(k).prop("checked") == false){
						aCitybox.eq(k).parents("div.itembox").siblings("input").prop("checked", false);
						break;
					}
				}
				//显示选择城市的数量
				for (var l = 0; l < aCitybox.length; l++) {
					if(l == aCitybox.length-1) {
						var Count = parseInt($(this).parents("div.itembox").siblings("label").attr("oCount"));
						if (Count == 0) {
							aCitybox.eq(l).parents("div.itembox").siblings("label").html(oLabel);
						} else {
							aCitybox.eq(l).parents("div.itembox").siblings("label").html(oLabel + "(" + Count + ")");
						}
					}
				}
			} );
		}
	}
});

//显示\隐藏 城市DIV
var oOldItembox = null;		//设置旧DIV，当新DIV点击时，隐藏DIV
function showCity(obj) {
	var oDiv = $(obj).siblings("div");
	if (oOldItembox) {
		oOldItembox.fadeOut("fast", function () {
			oOldItembox.addClass("hideItembox");
		});
	}
	if (oDiv.hasClass("hideItembox")) {
		oDiv.fadeIn("fast", function () {
			oDiv.removeClass("hideItembox");
			oDiv.addClass("showItembox");
			oOldItembox = oDiv;
		});
	} else {
		oDiv.fadeOut("fast", function () {
			oDiv.addClass("hideItembox");
			oDiv.removeClass("showItembox");
		});
	}
}
