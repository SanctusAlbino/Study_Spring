--공지글 관리
create table notice (
id          number constraint notice_id_pk primary key,
title       varchar2(300) not null /*제목*/,
content     varchar2(4000) not null /*내용*/,

writer      varchar2(50) not null /*작성자의 id*/,

writedate   date default sysdate /*작성일자*/,
readcnt     number default 0 /*조회수*/,
filename    varchar2(300) /*첨부파일명*/,
filepath    varchar2(600) /*첨부파일 경로*/ ,
constraint notice_writer_fk foreign key(writer)
                                references member(userid) on delete cascade
);

pk인 id 컬럼에 적용할 시퀀스 생성
create sequence seq_notice
start with 1 increment by 1 nocache;

notice의 pk인 id에 시퀀스를 자동 적용시킬 트리거 생성
create or replace trigger trg_notice
    before insert on notice
    for each row 
begin
    select seq_notice.nextval into :new.id from dual;
end;
/

insert into notice(id, title, content, writer)
values (seq_notice.nextval, '테스트 공지글', '이 글은 테스트 공지글입니다.', 'honghong');

insert into notice(title, content, writer)
values ('두번째 공지글', '이 글은 두번째 공지글입니다.', 'honghong');

select * from notice;
commit;

select name, userid, admin from member;
--update notice set writer = 'admin2' where id =2;
update notice
set writer = case when id=1 then 'admin' when id=2 then 'admin1' end;


작성자 id: hong 
탈퇴하면 member에는 회원이 없다 - 
1. writer에 null 넣는다. : writer 컬럼은 null을 허용해야한다.
    constraint에 옵션 지정: on delete set null
2. 탈퇴자가 쓴 글은 삭제 :writer 컬럼은 null 을 불허한다. -> not null
    constraint에 옵션 지정: on delete cascade
    
    
    
select * from notice;

insert into notice(title, content, writer)
select title, content, writer from notice;

--데이터행을 조회해온 순서에 해당하는 컬럼: rownum
select rownum, n.* from
(select n.*, name
from notice n inner join member m on n.writer = m.userid
order by id) n
;

select row_number() over(order by id) no, n.*, name
from notice n inner join member m on n.writer = m.userid
order by no desc;

delete from notice where mod(id,5) = 0;
commit;

select count(*) from notice;

select * 
from    (select row_number() over(order by id) no, n.*, name
            from notice n inner join member m on n.writer = m.userid) n
where no between 202 and 211
order by no desc
;

