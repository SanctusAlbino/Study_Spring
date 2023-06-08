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


