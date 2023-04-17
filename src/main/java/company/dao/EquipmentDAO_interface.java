package company.dao;

import java.util.List;

import company.model.EquipmentVO;
//父類別
public interface EquipmentDAO_interface {
          public void insert(EquipmentVO equipmentVO);
          public void update(EquipmentVO equipmentVO);
          public void delete(Integer equipmentID);
          public EquipmentVO findByPrimaryKey(Integer equipment);
          public List<EquipmentVO> getAll();
          //萬用複合查詢(傳入參數型態Map)(回傳 List)
          
}
