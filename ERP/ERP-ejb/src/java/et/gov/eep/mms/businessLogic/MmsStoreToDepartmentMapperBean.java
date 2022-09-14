/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.mms.businessLogic;

import et.gov.eep.commonApplications.entity.ColumnNameResolver;
import et.gov.eep.hrms.entity.organization.HrDepartments;
import et.gov.eep.mms.entity.MmsLuWareHouse;
import et.gov.eep.mms.entity.MmsStoreToHrDepMapper;
import et.gov.eep.mms.mapper.MmsStoreToHrDepMapperFacade;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author Sadik
 */
@Stateless
public class MmsStoreToDepartmentMapperBean implements MmsStoreToDepartmentMapperBeanLocal {
  @EJB
  MmsStoreToHrDepMapperFacade storeToHrDepMapper;
   @Override
    public void create(MmsStoreToHrDepMapper storeToDepartmentMapper) {
        storeToHrDepMapper.create(storeToDepartmentMapper);
    }

  
    @Override
    public void edit(MmsStoreToHrDepMapper storeToDepartmentMapper) {
        storeToHrDepMapper.edit(storeToDepartmentMapper);
    }
    @Override
    public boolean checkForDuplication(MmsStoreToHrDepMapper storeToDepartmentMapper) {
        return storeToHrDepMapper.checkForDuplications(storeToDepartmentMapper);
    }
    @Override
    public List<MmsStoreToHrDepMapper> searchByWarehouseName(MmsLuWareHouse wareHouse){
        return storeToHrDepMapper.searchByWarehouseName(wareHouse);
    }
    
    @Override
    public List<MmsStoreToHrDepMapper> searchByDepartmentName(HrDepartments departments){
        return storeToHrDepMapper.searchByDepartmentName(departments);
    }

    @Override
    public List<MmsStoreToHrDepMapper> findAll() {
       return storeToHrDepMapper.findAll();
    }

   

    @Override
    public List<MmsStoreToHrDepMapper> getHrDepByParameter(ColumnNameResolver columnNameResolver, MmsStoreToHrDepMapper storeToDepartmentMapper, String columnValue) {
        return storeToHrDepMapper.getHrDepByParameter(columnNameResolver, storeToDepartmentMapper, columnValue);
    }

    @Override
    public List<String> getMmsHrDepColumnNameList() {
       return storeToHrDepMapper.getMmsHrDepColumnNameList();
    }
}
