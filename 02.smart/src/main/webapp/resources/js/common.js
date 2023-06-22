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
 
 //동적으로 만들어진 요소에 대해서는 document에 이벤트를 등록해야 한다.
 $(document).on('click','.date + .date-delete', function(){
	$(this).css('display', 'none'); //삭제버튼 안보이게
	$(this).prev('.date').val('');//날짜태그의 값을 초기화
}).on('click','#file-attach .file-delete', function(){
		$(this).addClass('d-none'); //삭제버튼 안 보이게 
		
		console.log( '1>', $('#file-single').val())
		$('input[type=file]').val('');//첨부되어 있던 이미지파일 없애기
		var _preview = $('#file-attach .file-preview');
		if(_preview.length > 0 ) _preview.empty(); //미리보기한 이미지 없애기
		console.log('2> ', $('#file-single').val())
	})
 
 //파일이 이미지파일인지 확인
 function isImage(filename){
	// abc.png, abc.jpg, abc.txt, abc.hwp
	var ext = filename.substr(filename.lastIndexOf('.')+1).toLowerCase();
	var imgs = [ "png", "jpg", "bmp", "gif", "jpeg", "webp" ];
	return imgs.indexOf(ext)== -1 ? false : true;
}
 
 
 $( function() {
	//프로필 이미지 선택처리
	$('input#file-single').change(function(){
		console.log($(this))
		console.log(this.files)
		
		var _preview = $('#file-attach .file-preview');
		var _delete = $('#file-attach .file-delete');
		
		var attached = this.files[0];
		console.log(attached)
		if(attached){
			//이미지파일인지 확인
			if(isImage(attached.name)){
				singleFile = attached; //선택한 파일정보를 관리
				_delete.removeClass('d-none'); //삭제버튼 보이게
				// 미리보기 태그가 있을때만 
				if( _preview.length > 0 ){
					_preview.html( "<img>" );
					
					var reader = new FileReader();
					reader.readAsDataURL( attached );
					reader.onload = function(e){
	//					_preview.children("img").attr("src",this.result );
						_preview.children("img").attr("src",e.target.result );
					}
					
				}
			}else{
				singleFile = ''; //이미지가 아닌 파일인 경우는 관리정보를 초기화
				//이전 선택했던 이미지파일 처리
				_preview.empty();
				$(this).val('');	//실제file 태그의 정보 초기화
				_delete.addClass('d-none'); //삭제버튼 안 보이게
			}
			
		}else{
			// 파일선택 창에서 취소를 클릭한 경우: 어떠한 처리도 하지 않는다.
			// 파일정보는 관리된 singleFile 변수에 있다.
		}
	})
	
	
	
	$('.date').change(function(){
		$(this).next('.date-delete').css('display','inline')
		
	})
	
	$('[name=phone]').keyup(function(){
	toPhone($(this));
})
	
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
  
  function toPhone(tag){
	//02-1234-1234 010-1234-1234
	var phone = tag.val().replace(/[^0-9]/g, '').replace(/[-]/g,'') ; //숫자만 입력되게 처리
	if(phone.length >1){ //2자리 이상 입력하면: 02, 062, 010
		var start = phone.substr(0,2) == "02" ? 2 : 3,
				middle = start + 4 ;
	
				// -만들어서 넣기
				if(phone.length > middle){
					phone = phone.substr(0, start) + "-" +phone.substr(start,4)+ "-" + phone.substr(middle,4);
				}else if(phone.length > start){
					phone = phone.substr(0, start) + "-" +phone.substr(start,4)+ "-";
				}
	}
	// substr(시작, 길이), substring(시작, 끝위치)
	tag.val( phone );
}
  
  