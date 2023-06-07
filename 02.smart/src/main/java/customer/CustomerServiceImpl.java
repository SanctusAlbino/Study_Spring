package customer;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImpl implements CustomerService {
	@Autowired private CustomerDAO dao;
	
	@Override
	public void customer_insert(CustomerVO vo) {
		// TODO Auto-generated method stub
			dao.customer_insert(vo);
	}

	@Override
	public List<CustomerVO> customer_list() {
		// TODO Auto-generated method stub
		return dao.customer_list();
	}

	@Override
	public CustomerVO customer_info(int id) {
		// TODO Auto-generated method stub
		return dao.customer_info(id);
	}

	@Override
	public void customer_update(CustomerVO vo) {
		// TODO Auto-generated method stub
		dao.customer_update(vo);
	}

	@Override
	public void customer_delete(int id) {
		// TODO Auto-generated method stub
		dao.customer_delete(id);
	}

	@Override
	public List<CustomerVO> customer_list(String name) {
		// TODO Auto-generated method stub
		return dao.customer_list(name);
	}

}
