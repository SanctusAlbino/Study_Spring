/**
 * 공통함수선언
 */
 
 function modalAlert(type, title, message){
	$('#modal-alert .modal-title').html(title);
	$('#modal-alert .modal-body').html(message);
	
	$('.modal-icon').removeClass('text-info text-warning text-danger text-primary fa-circle-exclamation fa-circle-question');
	// 아니오/확인 으로 사용되는 버튼의 색상 초기화
	$('.modal-footer .btn-ok').removeClass('btn-info btn-warning btn-danger btn-primary btn-success');
	if(type=='danger'){//confirm에 해당
		$('.modal-footer .btn-ok').addClass('btn-secondary').text('아니오')
		$('.modal-footer .btn-danger').removeClass('d-none')
		
		$('.modal-icon').addClass('fa-circle-question')
		
	}else{
		$('.modal-footer .btn-ok').addClass('btn-'+ type).text('확인')
		$('.modal-footer .btn-danger').addClass('d-none')
		$('.modal-icon').addClass('fa-circle-exclamation') //아이콘 느낌표
	}
	$('.modal-icon').addClass('text-'+type)
}
 
 
 $( function() {
	var today = new Date();
    var	range = today.getFullYear()-100 + ':' + today.getFullYear();
	$.datepicker.setDefaults({
		dateFormat: "yy-mm-dd", 
		changeYear: true,
		changeMonth: true,
		yearRange: range,
		showMonthAfterYear: true,
		monthNamesShort: [ "1월", "2월", "3월", "4월", "5월", "6월", "7월", "8월", "9월", "10월", "11월", "12월" ],
		dayNamesMin: ["일","월","화","수","목","금","토"],
		maxDate: today, 
	})
	
    $( ".date" ).datepicker();
    $( ".date" ).attr('readonly',true);
    
  } );
  
  