package poly.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import poly.dto.AdminDTO;
import poly.dto.ApplicantDTO;
import poly.persistance.mapper.MainMapper;
import poly.service.IMainService;

@Service("MainService")
public class MainService implements IMainService {

	@Resource(name = "MainMapper")
	private MainMapper mainMapper;
	
	@Override
	 public int insertApplicant(ApplicantDTO aDTO) throws Exception {
		// TODO Auto-generated method stub
		return  mainMapper.insertApplicant(aDTO);
	}

}