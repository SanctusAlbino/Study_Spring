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
root        number /*답글관리를 위한 id*/,
step        number default 0/*글 순서*/,
indent      number default 0/*들여쓰기*/,
constraint notice_writer_fk foreign key(writer)
                                references member(userid) on delete cascade
);

alter table notice add(
root        number /*답글관리를 위한 id*/,
step        number default 0/*글 순서*/,
indent      number default 0/*들여쓰기*/
);

select id, root, step, indent from notice
order by id desc;

update notice set root = id;
commit;

pk인 id 컬럼에 적용할 시퀀스 생성
create sequence seq_notice
start with 1 increment by 1 nocache;

notice의 pk인 id에 시퀀스를 자동 적용시킬 트리거 생성
create or replace trigger trg_notice
    before insert on notice
    for each row 
begin
    select seq_notice.nextval into :new.id from dual;
    if( :new.root is null ) then
        /*원글인 경우 root에 값을 넣기 위한 처리*/
        select seq_notice.currval into :new.root from dual;
    else
        /*답글인 경우 순서를 위한 step변경처리*/
        update notice set step = step + 1 
        where root = :old.root and step > :new.step;
    end if;
end;
/

create or replace trigger trg_notice
    before insert on notice
    for each row 
begin
    select seq_notice.nextval into :new.id from dual;
    if( :new.root is null ) then
        /*원글인 경우 root에 값을 넣기 위한 처리*/
        select seq_notice.currval into :new.root from dual;
    end if;
end;
/

--원글삭제시 답글들 삭제처리
create or replace trigger trg_notice_delelte
    after delete on notice
    for each row
begin
    --삭제한 글의 root와 같은 root인 데이터행 삭제
    delete from notice where root = :old.root;
end;
/

alter trigger trg_notice_delelte disable; --disable/enable


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

select count(*) content from notice
where title like '%테스트%'
;

select * 
from    (select row_number() over(order by id) no, n.*, name
            from notice n inner join member m on n.writer = m.userid) n
where no between 12 and 21
order by no desc
;

select count(*) from notice
where writer = 'admin' or writer='admin2'
;

//

