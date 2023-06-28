/**
 * 공통함수선언
 */
 //파일첨부 정보 file태그에 담기
 var singleFile = ''; //파일선택시 선택한 첨부파일정보를 담아둘 변수
 function singleFileUpload(){
	if(singleFile !=""){
		var transfer = new DataTransfer();
		transfer.items.add( singleFile);
		//화면 태그 속성: attr : 기본에 해당, 나중에 속성추가지정: prop
		$('input[type=file]').prop('files', transfer.files);
		
		//console.log($('input[type=file]').val())
	}	
}
 
 
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
		
		var _name = $('#file-attach .file-name'); //파일명 태그
		if(_name.length > 0 ) _name.empty();	//파일명 없애기
		console.log('2> ', $('#file-single').val())
		console.log('파일정보> ', $('[type=file]').val())
		
	})
	.on('click', '.file-preview img', function(){
		// 미리보기 이미지 클릭시 크게보이게
		if($('#modal-image').length == 1){
			$('.modal-body').html($(this).clone() );
			new bootstrap.Modal( $('#modal-image')).show()
		}
		
	})
 
 //파일이 이미지파일인지 확인
 function isImage(filename){
	// abc.png, abc.jpg, abc.txt, abc.hwp
	var ext = filename.substr(filename.lastIndexOf('.')+1).toLowerCase();
	var imgs = [ "png", "jpg", "bmp", "gif", "jpeg", "webp" ];
	return imgs.indexOf(ext)== -1 ? false : true;
}
 
 //첨부파일 크기 제한 함수
 function rejectedFile(fileInfo, tag){
	// 1024 byte = lkb, lMb=1024*1024 byte, ...
	if(fileInfo.size > 1024*1024*10){
		alert("10Mb 를 넘는 파일은 첨부할 수 없습니다.!!!!!");
		tag.val('');
		return true;
	}else
		return false;
}
 
 $( function() {
	//프로필 이미지 선택처리
	$('input#file-single').change(function(){
		console.log($(this))
		console.log(this.files)
		
		var _preview = $('#file-attach .file-preview'); //이미지 미리보기요소
		var _delete = $('#file-attach .file-delete'); //파일삭제요소
		var _name = $('#file-attach .file-name'); //파일명 요소
		
		
		var attached = this.files[0];
		console.log(attached)
		if(attached){
			//파일 사이즈 제한
			if(rejectedFile(attached, $(this))) return;
			//파일명 보여지게
			if( _name.length>0)  _name.text( attached.name);
				_delete.removeClass('d-none'); //삭제버튼 보이게
			
			//이미지파일인지 확인
			if(isImage(attached.name)){
				singleFile = attached; //선택한 파일정보를 관리
				// 미리보기 태그가 있을때만 
				if( _preview.length > 0 ){
					_preview.html( "<img>" );
				}else{
					//첨부파일이 이미지인데, 미리보기 요소가 없으면 동적으로 만들어 보이게 처리
					//삭제버튼 앞에 넣기
					_delete.before("<span class='file-preview'><img></span>");
					_preview = $( '#file-attach .file-preview')
				}
					
				var reader = new FileReader();
				reader.readAsDataURL( attached );
				reader.onload = function(e){
	//					_preview.children("img").attr("src",this.result );
					_preview.children("img").attr("src",e.target.result );
					}
					
				
			}else{
				//이전 선택했던 이미지파일 처리
				_preview.empty();
				
				if($(this).hasClass("image-only")){
					
					singleFile = ''; //이미지가 아닌 파일인 경우는 관리정보를 초기화
					$(this).val('');	//실제file 태그의 정보 초기화
					_delete.addClass('d-none'); //삭제버튼 안 보이게
				}
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
  
  //입력항목 입력 여부 
  function emptyCheck(){
	var ok = true;
	$('.check-empty').each(function(){
		if($(this).val()==""){
			alert($(this).attr('title')+"입력하세요");
			$(this).focus();
			ok = false;
			return ok;
		}
	})
	return ok;
}
  
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
  
  