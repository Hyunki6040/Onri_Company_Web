package poly.persistance.mapper;



import config.Mapper;
import poly.dto.AdminDTO;


@Mapper("AdminMapper")
public interface AdminMapper {
	public AdminDTO getAdmin(String campus) throws Exception;
	public int updateAdmin(AdminDTO aDTO) throws Exception;
	public int clickCount(String ad_no) throws Exception;
	
	public String getAdminLogin(AdminDTO aDTO) throws Exception;
}
