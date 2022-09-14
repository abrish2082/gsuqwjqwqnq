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
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Sadik
 */
@Local
public interface MmsStoreToDepartmentMapperBeanLocal {

    public void create(MmsStoreToHrDepMapper storeToDepartmentMapper);

    public void edit(MmsStoreToHrDepMapper storeToDepartmentMapper);

    public boolean checkForDuplication(MmsStoreToHrDepMapper storeToDepartmentMapper);

    public List<MmsStoreToHrDepMapper> searchByWarehouseName(MmsLuWareHouse wareHouse);

    public List<MmsStoreToHrDepMapper> searchByDepartmentName(HrDepartments departments);

    public List<MmsStoreToHrDepMapper> findAll();

    public List<MmsStoreToHrDepMapper> getHrDepByParameter(ColumnNameResolver columnNameResolver, MmsStoreToHrDepMapper storeToHrDepMapperEntity, String columnValue);

    public List<String> getMmsHrDepColumnNameList();

  
    
}
