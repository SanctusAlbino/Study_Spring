--select 절에 group함수를 사용한 표현이 있는 경우
--group함수가 사용되지 않은 모든 표현에 대해서는 group by 의 기준으로 명시되어야 한다.

select e.department_id, count(*) count, nvl(d.department_name, '소속없음') department_name
from employees e left outer join departments d on e.department_id = d.department_id
group by e.department_id, d.department_name
order by e.department_id
;

-- 년도별 사원수 조회
-- 2001년 10 
-- 2002년 2 
-- 2003년 5 
-- 쿼리문 만들기
-- 1. 조회한 결과데이터 표현에 해당하는 것을 select 절 작성
-- 2. select 절에 표현한 컬럼이 어떤 테이블에 있는지 파악하여 from 절 작성
-- 3. from 절에 여러개의 테이블이 사용된다면 테이블조인을 한다.
--    ansi join시 on절, using 절을 사용해 조인조건절을 작성
--    oracle join시 where절을 사용해 조인조건절을 작성
-- 4. select 절에 그룹함수를 사용한 표현이 있다면
--    그룹함수를 사용하지 않은 표현에 대해 group by 절 작성
-- 5. 데이터행을 제한해야 하는 경우가 있다면
--    where 절에 데이터행에 대한 제한조건 작성
-- 6. 그룹함수를 사용한 표현에 대한 데이터행을 제한해야 하는 경우가 있다면
--    having 절에 데이터행에 대한 제한조건 작성
-- 7. 데이터행의 결과를 정렬해야 한다면 order by 절(오름차순:asc, 내림차순:desc) 작성



select to_char(hire_date, 'YYYY')||'년' YEAR, count(employee_id) count
from employees
group by to_char(hire_date, 'YYYY')
order by 1;

select to_char(hire_date, 'MM')||'월' Month, count(employee_id) count
from employees
group by to_char(hire_date, 'MM')
order by 1;

--사원수가 많은 상위 3위까지의 부서 조회
-- 순위, 부서코드, 사원수
select *
from(select dense_rank() over(order by count(*) desc)rank, department_id, count(*) count
from employees
group by department_id) e
where rank <= 3
;

--순위, 부서코드, 부서명
select rank, department_id, '(TOP'||rank||')'||department_name department_name
from(select dense_rank() over(order by count(*) desc) rank, department_id, count(*) count
from employees
group by department_id) e left outer join departments d using(department_id)
where rank <= 3
;

--위 상위 부서에 대해서만 월별 채용인원수 조회
--부서명, 입사월
select department_name, to_char(hire_date, 'mm') unit
from employees e inner join
        (select rank, department_id, '(TOP'||rank||')'||department_name department_name
         from(select dense_rank() over(order by count(*) desc) rank, department_id, count(*) count
         from employees
         group by department_id) e left outer join departments d using(department_id)
         where rank <= 3) r using (department_id);
         
-- 세로 데이터행 --> 가로데이터행으로 변경 : pivot
-- 가로 데이터행 --> 세로데이터행으로 변경 : unpivot

--pivot/unpivot 함수
--select 컬럼
--from (테이블, 인라인뷰서브쿼리로 부터 데이터를 조회하는 select)
--pivot(집계함수(표현식) for pivot대상 컬럼 in(행으로 올릴 열))

--열로 이루어진 데이터행
select 5 "01월", 10 "02월", 5 "03월", 10 "04월", 5 "05월", 10 "06월",
        5 "07월", 10 "08월", 5 "09월", 10 "10월", 5 "11월", 10 "12월"
from dual;

-- 열로 이루어진 데이터행을 행으로 변환
select *
from (select 5 "01월", 10 "02월", 20 "03월", 15 "04월", 3 "05월", 0 "06월",
            3 "07월", 2 "08월", 10 "09월", 3 "10월", 5 "11월", 2 "12월"
        from dual)
unpivot(cnt for mm in ("01월", "02월", "03월", "04월", "05월", "06월",
                        "07월","08월", "09월","10월","11월", "12월") )
                        ;
                        
-- 행으로 이루어진 데이터행을 열로 변환
select *
from (select *
from (select 5 "01월", 10 "02월", 20 "03월", 15 "04월", 3 "05월", 0 "06월",
            3 "07월", 2 "08월", 10 "09월", 3 "10월", 5 "11월", 2 "12월"
        from dual)
unpivot(cnt for mm in ("01월", "02월", "03월", "04월", "05월", "06월",
                        "07월","08월", "09월","10월","11월", "12월") ))
pivot( sum(cnt) for mm in ( '01월', '02월', '03월', '04월', '05월', '06월',
                                '07월', '08월', '09월', '10월', '11월', '12월') )
;

--입사월별 사원수
select *
from ( select to_char(hire_date,'mm')mm from employees)
pivot(count(*) for mm in('01', '02', '03', '04', '05', '06',
                                '07', '08', '09', '10', '11', '12'))
;


select *
from (select department_name, to_char(hire_date, 'mm') unit
from employees e inner join
        (select rank, department_id, '(TOP'||rank||')'||department_name department_name
         from(select dense_rank() over(order by count(*) desc) rank, department_id, count(*) count
         from employees
         group by department_id) e left outer join departments d using(department_id)
         where rank <= 3) r using (department_id))
pivot(count(*) for unit in ('01' "01월", '02'"02월", '03'"03월", '04'"04월", '05'"05월", '06'"06월",
                                '07'"07월", '08'"08월", '09'"09월", '10'"10월", '11'"11월", '12'"12월") )
order by department_name
;

/*
2001년
2002년
2003년
2004년
2005년
2006년
2007년
2008년
2023년
*/
-- 상위3위부서의 년도별 채용인원수
select *
from (select department_name, to_char(hire_date, 'yyyy') unit
from employees e inner join
        (select rank, department_id, '(TOP'||rank||')'||department_name department_name
         from(select dense_rank() over(order by count(*) desc) rank, department_id, count(*) count
         from employees
         group by department_id) e left outer join departments d using(department_id)
         where rank <= 3) r using (department_id))
pivot(count(*) for unit in ('2001' "2001년", '2002'"2002년", '2003'"2003년", '2004'"2004년"
                                , '2005'"2005년", '2006'"2006년",
                                '2007'"2007년", '2008'"2008년", '2023'"2023년") )
order by department_name
;

