package poly.persistance.mapper;



import config.Mapper;
import poly.dto.AdminDTO;
import poly.dto.ApplicantDTO;


@Mapper("MainMapper")
public interface MainMapper {
	public int insertApplicant(ApplicantDTO aDTO) throws Exception;
}
