--회원관리
create table member(
name        varchar2(50) not null, /*회워명*/
userid      varchar2(50) constraint member_userid_pk primary key, /*아이디*/
userpw      varchar2(300) not null, /*비밀번호*/
gender      varchar2(3) default '남' not null, /*성별*/
email       varchar2(50) not null, /*이메일*/
profile     varchar2(300), /*프로필 이미지*/
birth       date, /*생년월일*/
phone       varchar2(13), /*전화번호*/
post        varchar2(5), /*우편번호*/
address     varchar2(300), /*주소*/
admin       varchar2(1) default 'N' /*관리자여부:Y/N*/
);
--소셜구분 컬럼 추가
alter table member add (social varchar2(1));
--소셜로그인 경우 userpw정보가 없고 email가 없을수도 있다
alter table member modify(userpw null, email null);

select * from member;
delete from member;