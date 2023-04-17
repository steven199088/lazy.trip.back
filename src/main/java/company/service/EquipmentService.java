package company.service;

import java.util.List;

import company.dao.EquipmentDAO_interface;
import company.dao.EquipmentDAO;
import company.model.EquipmentVO;

public class EquipmentService {

	private EquipmentDAO_interface dao;

	public EquipmentService() {
		dao = new EquipmentDAO();
	}

	public EquipmentVO addEquipment(Integer equipmentID,String equipmentName, String equipmentDesc
			) {

		EquipmentVO equipmentVO = new EquipmentVO();

		equipmentVO.setEquipmentID(equipmentID);
		equipmentVO.setEquipmentName(equipmentName);
		equipmentVO.setEquipmentDesc(equipmentDesc);
		dao.insert(equipmentVO);

		return equipmentVO;
	}

	public EquipmentVO updateEquipment(Integer equipmentID,String equipmentName, String equipmentDesc
) {

		EquipmentVO equipmentVO = new EquipmentVO();

		equipmentVO.setEquipmentID(equipmentID);
		equipmentVO.setEquipmentName(equipmentName);
		equipmentVO.setEquipmentDesc(equipmentDesc);
		dao.update(equipmentVO);

		return equipmentVO;
	}

	public void deleteEquipment(Integer equipmentID) {
		dao.delete(equipmentID);
	}

	public EquipmentVO getOneEquipment(Integer equipmentID) {
		return dao.findByPrimaryKey(equipmentID);
	}

	public List<EquipmentVO> getAll() {
		return dao.getAll();
	}
	
	
	
}
