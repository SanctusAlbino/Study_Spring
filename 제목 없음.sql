-- 방명록 관리
drop table board;
create table board(
id          number constraint board_id_pk primary key,
title       varchar2(300) not null /*제목*/,
content     varchar2(4000) not null /*내용*/,
writer      varchar2(50) not null /*작성자의 id*/,
writedate   date default sysdate /*작성일자*/,
readcnt     number default 0 /*조회수*/      
);

create sequence seq_board start with 1 increment by 1 nocache;

create or replace trigger trg_board
    before insert on board
    for each row
begin
    select seq_board.nextval into :new.id from dual;
end;
/

--방명록에 첨부하는 파일관리
create table board_file(
id          number constraint board_file_id_pk primary key,
board_id    number constraint board_file_fk references board(id) on delete cascade, 
filename    varchar2(300) /*첨부파일명*/,
filepath    varchar2(600) /*첨부파일 경로*/ 
);

create sequence seq_board_file start with 1 increment by 1 nocache;

create or replace trigger trg_board_file
    before insert on board_file
    for each row
begin
    select seq_board_file.nextval into :new.id from dual;
end;
/

select * from board;
select * from board_file;

insert into board(title, content, writer)
select title, content, writer from board;

commit;

select (select count(*) from board_file where b.id=board_id)filecnt, b.*
from (select row_number() over(order by id) no, b.*, name 
		from board b inner join member m on b.writer = m.userid) b
order by no desc;


