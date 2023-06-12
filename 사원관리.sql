--관리자로 있는 사원정보
select distinct manager_id from employees order by 1;
select * from employees;
select e.employee_id, e.last_name||' '||e.first_name name, e.manager_id, 
m.last_name||' '||m.first_name manager_name
from employees e left outer join employees m on e.manager_id = m.employee_id
order by 1;

-- manager_id 컬럼은 employees의 employee_id를 reference하고 있다.
-- FK인 constraint명이 EMP_MANAGER_FK
-- reference 되는 부모행이 삭제될때 컬럼의 정보에 null로 변경
-- 원래있던 constraint를 삭제
alter table employees drop constraint emp_manager_fk;
-- 조건지정한 constraint 를 지정
alter table employees add constraint emp_manager_fk 
foreign key(manager_id) references employees(employee_id) on delete set null;

--원래있던 constraint를 삭제
alter table job_history drop constraint JHIST_EMP_FK;
--조건지정한 constraint를 지정
alter table job_history add constraint jhist_emp_fk
    foreign key(employee_id) references employees(employee_id)on delete cascade;
    --원래있던 constraint를 삭제
alter table departments drop constraint DEPT_MGR_FK;
--조건지정한 constraint를 지정
alter table departments add constraint dept_mgr_fk
    foreign key(manager_id) references employees(employee_id)on delete set null;
    
select employees_seq.currval from dual;
    