package poly.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import poly.dto.AdminDTO;
import poly.persistance.mapper.AdminMapper;
import poly.service.IAdminService;

@Service("AdminService")
public class AdminService implements IAdminService {

	@Resource(name = "AdminMapper")
	private AdminMapper adminMapper;
	
	@Override
	public AdminDTO getAdmin(String campus) throws Exception {
		AdminDTO admin = adminMapper.getAdmin(campus);
		return admin;
	}
	
	@Override
	public int updateAdmin(AdminDTO aDTO) throws Exception {
		return adminMapper.updateAdmin(aDTO);
	}
	
	@Override
	public int clickCount(String ad_no) throws Exception {
		return adminMapper.clickCount(ad_no);
	}

	
	
	@Override
	public String getAdminLogin(AdminDTO aDTO) throws Exception {
		// TODO Auto-generated method stub
		return  adminMapper.getAdminLogin(aDTO);
	}

}
