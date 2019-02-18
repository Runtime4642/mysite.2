<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link rel="stylesheet"
	href="${pageContext.request.contextPath }/assets/css/guestbook-ajax.css"
	rel="stylesheet" type="text/css">
<link rel="stylesheet"
	href="https://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<script type="text/javascript"
	src="${pageContext.request.contextPath }/assets/js/jquery/jquery-1.9.0.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath }/assets/js/getWeather.js"></script>
<style type="text/css">
#dialog-delete-from p {
	padding: 10px;
	font-weight: bold;
	font-size: 1.0em;
}

#dialog-delete-from input[type="passowrd"] {
	padding: 5px;
}
</style>
<script>
	//jquery plug-in
	(function($) {
		$.fn.hello = function() {
			console.log($(this).attr("id") + "----> hello");
		}
	})(jQuery);

	var page = 0;
	var messageBox = function(title, message, select) {
		$('#dialog-message').attr("title", title);
		$('#dialog-message p').text(message);
		$("#dialog-message").dialog({
			modal : true,
			buttons : {
				"확인" : function() {
					//	console.log("누름");
					$(this).dialog("close");
					select.focus();
				}
			}

		});
	}

	var render = function(vo, mode) {

		//작성 mode ==1
		if (mode == 1) {

			// 현업에 가면 이렇게 안함. -> js template library 라이브러리 사용
			// ex) ejs, underscore, mustache

			//   -->  /\n/g 정규 표현식 \n 모든 개행(g)을
			var htmls = "<li data-no='"+vo.no+"'><strong>"
					+ vo.name
					+ "</strong><p>"
					+ vo.message.replace(/\n/g, "<br>")
					+ "</p><strong></strong><a href='' data-no='"+vo.no+"'>삭제</a></li>";

			$("#list-guestbook").prepend(htmls);
		}
		//조회 mode ==2
		else if (mode == 2) {
			// 현업에 가면 이렇게 안함. -> js template library 라이브러리 사용
			// ex) ejs, underscore, mustache

			//   -->  /\n/g 정규 표현식 \n 모든 개행(g)을
			var htmls = "<li data-no='"+vo.no+"'><strong>"
					+ vo.name
					+ "</strong><p>"
					+ vo.message.replace(/\n/g, "<br>")
					+ "</p><strong></strong><a href='' data-no='"+vo.no+"'>삭제</a></li>";

			$("#list-guestbook").append(htmls);
		}
	}
	var fetchList = function() {
		page++;
		$.ajax({
			url : "/mysite5/api/gb?a=ajax-list&p=" + page,
			type : "get",
			dataType : "json",
			data : "",
			success : function(response) {
				if (response.result == "fail") {
					console.warn(response.data);
					return;
				}
				if (response.data.length < 5) {
					$("#btn-next").prop("disabled", true);
				}
				// rendering
				$.each(response.data, function(index, vo) {
					render(vo, 2);
				});
			},
			error : function(xhr, status, e) {
				console.error(statuse + ":" + e);
			}
		})
	};

	$(function() {

		//날씨 받아오기 코드 수정중. accress cross error -> http 헤더를 수정해야 하는거라서 일단 나둠.
		realTimeWeather();

		var dialogDelete = $("#dialog-delete-form").dialog(
				{
					autoOpen : false,
					modal : true,
					buttons : {
						"삭제" : function() {

							$.ajax({
								url : "/mysite5/api/gb",
								type : "post",
								dataType : "json",
								data : "a=ajax-delete&password="
										+ $('#password-delete').val() + "&no="
										+ $('#hidden-no').val(),
								success : function(response) {

									if (!response.result) {

										$('.validateTips-error').show();
										console.warn(response.data);
										return;
									}
									console.log(response.no);

									$(
											'#list-guestbook li[data-no='
													+ response.no + ']')
											.remove();

									dialogDelete.dialog("close");

								},
								error : function(xhr, status, e) {
									console.error(statuse + ":" + e);
								}
							})
						},
						"취소" : function() {
							dialogDelete.dialog("close");
						}
					},
					close : function() {
						//value 값 비우기 등...
						$('.validateTips-error').hide();
						$('#password-delete').val("");
					}

				});
		// live event
		$(document).on("click", "#list-guestbook li a", function() {
			event.preventDefault();

			//delete창에 no값 넣기
			$('#hidden-no').val($(this).data("no"));

			//삭제창 열기
			dialogDelete.dialog("open");
		});

		//메세지 등록
		$("#add-form")
				.submit(
						function(event) {
							//submit의 기본동작(post)
							//막아야함
							// 이거 왜 막아야하는지 질문  -->submit 이  되기전에 체크해야할것들이 있기때문에 잠시막는거라고함..
							event.preventDefault();

							//validate form data
							var name = $("#input-name").val();
							if (name == "") {
								messageBox("글남기기", "이름은 필수 입력 항목입니다.",
										$("#input-name"));
								//alert("이름은 필수 입력 항목입니다.");
								return;
							}
							var password = $("#input-password").val();
							if (password == "") {
								messageBox("글남기기", "비밀번호는 필수 입력 항목입니다.",
										$("#input-password"));
								return;
							}
							var content = $("#tx-content").val();
							if (password == "") {
								messageBox("글남기기", "내용이 비었습니다.",
										$("#tx-content"));
								return;
							}

							$.ajax({
								url : "/mysite5/api/gb",
								type : "post",
								dataType : "json",
								data : "a=ajax-insert&name="
										+ $('#input-name').val() + "&message="
										+ $('#tx-content').val() + "&password="
										+ $('#input-password').val(),
								success : function(response) {
									if (response.result == "fail") {
										console.warn(response.data);
										return;
									}
									var __vo = response.data;
									render(__vo, 1);
									$('#input-name').val("");
									$('#tx-content').val("");
									$('#input-password').val("");
									console.log("입력완료");
								},
								error : function(xhr, status, e) {
									console.error(statuse + ":" + e);
								}
							})

						});

		$("#btn-next").click(function() {
			fetchList();
		});

		//최초리스트 가져오기
		fetchList();

		$(window).scroll(function() {
			var $window = $(this);
			var scrollTop = $window.scrollTop();
			var windowHeight = $window.height();
			var documentHeight = $(document).height();

			//console.log(scrollTop + ":" + windowHeight +":"+documentHeight);
			if (scrollTop + windowHeight + 20 > documentHeight)
				fetchList();
		});

	});
</script>



</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/includes/header.jsp" />
		<div id="content">
			<div id="guestbook">

				<h1>방명록</h1>
				<!-- 날씨 받아오기
				<div class="vis-weather">
				        <h2 class="vh_hide">날씨정보</h2>
				        <p class="weather-date"></p>
				        <ul>
				            <li class="weather-temp"></li>
				            <li id="RN1">시간당강수량 : ?</li>
				            <li class="weather-state-text"></li>
				        </ul>
				</div>
				-->
				<form id="add-form" action="" method="post">
					<input type="text" id="input-name" placeholder="이름"> <input
						type="password" id="input-password" placeholder="비밀번호">
					<textarea id="tx-content" placeholder="내용을 입력해 주세요."></textarea>
					<input type="submit" value="보내기" />
				</form>
				<ul id="list-guestbook">



				</ul>

				<button id="btn-next">다음</button>

			</div>
			<div id="dialog-delete-form" title="메세지 삭제" style="display: none">
				<p class="validateTips-normal">작성시 입력했던 비밀번호를 입력하세요.</p>
				<p class="validateTips-error" style="display: none; color: red">비밀번호가
					틀립니다.</p>
				<form>
					<input type="password" id="password-delete" value=""
						class="text ui-widget-content ui-corner-all"> <input
						type="hidden" id="hidden-no" value=""> <input
						type="submit" tabindex="-1"
						style="position: absolute; top: -1000px">
				</form>
			</div>
			<div id="dialog-message" title="" style="display: none">
				<p></p>
			</div>
		</div>
		<c:import url="/WEB-INF/views/includes/navigation.jsp">
			<c:param name="menu" value="guestbook-ajax" />
		</c:import>
		<c:import url="/WEB-INF/views/includes/footer.jsp" />
	</div>



</body>
</html>