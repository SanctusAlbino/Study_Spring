select e.employee_id, e.first_name||' '||e.last_name name,e.department_id, e.department_name, j.job_title, e.hire_date 
from  employees e , departments d, jobs j
where  e.department_id = d.department_id(+)
and e.job_id = j.job_id
order by 1;

-- 사원이 소속되어 있는 부서정보 조회. 부서코드, 부서명 조회
--IT 부서에 속한 사원정보 조회
select * from employees
where 부서코드 = (IT의 부서코드)
;

select department_id, department_name
from departments;

private int department_id;

select distinct nvl(to_char(department_id),'코드없음'), nvl(department_name, '소속없음')
from employees e left outer join departments d using(department_id);

select employee_id, last_name||' '||first_name name, e.department_id, department_name
        , job_id, job_title, hire_date
from departments d right outer join employees e on e.department_id=d.department_id
inner join jobs j using(job_id)
where department_name is null
order by employee_id;

--전체사원조회
-- 사번, 성명(이름), 부서명, 업무제목, 입사일자 조회되게
select employee_id, last_name||' '||first_name name, e.department_id, department_name
        , e.job_id,job_title, hire_date
from employees e, departments d, jobs j
where e.department_id = d.department_id(+)
and e.job_id = j.job_id
order by employee_id;

select employee_id, last_name||' '||first_name name, e.department_id, department_name
        , job_id,job_title, hire_date
from departments d right outer join employees e on e.department_id=d.department_id
inner join jobs j using(job_id)
where e.department_id is null
--where ?
order by employee_id;


select employee_id, last_name, department_id from employees;


-- 사원이 소속되어 있는 부서정보 조회: 부서코드, 부서명 조회
-- IT 부서에 속한 사원정보 조회
select * from employees
where 부서코드 = (IT의 부서코드)
;

select department_id, department_name
from departments;

--private int department_id;

select distinct department_id, nvl(department_name, '소속없음') department_name
--select distinct nvl(to_char(department_id), '코드없음'), nvl(department_name, '소속없음') department_name
from employees e left outer join departments d using(department_id);




select last_name || ' ' || first_name name, job_title, department_name, e.*
from employees e inner join jobs j on e.job_id = j.job_id
left outer join departments d on d.department_id = e.department_id
where employee_id = 100;

--update_job_history 트리거 사용 중단 처리 enable/disable
alter trigger update_job_history disable;



