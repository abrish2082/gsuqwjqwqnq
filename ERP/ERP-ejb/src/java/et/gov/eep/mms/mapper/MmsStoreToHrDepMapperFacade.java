/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.mms.mapper;

import et.gov.eep.commonApplications.entity.ColumnNameResolver;
import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.hrms.entity.organization.HrDepartments;
import et.gov.eep.mms.entity.MmsLuWareHouse;
import et.gov.eep.mms.entity.MmsStoreToHrDepMapper;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Sadik
 */
@Stateless
public class MmsStoreToHrDepMapperFacade extends AbstractFacade<MmsStoreToHrDepMapper> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public MmsStoreToHrDepMapperFacade() {
        super(MmsStoreToHrDepMapper.class);
    }

    // <editor-fold defaultstate="collapsed" desc=" NamedQuery ">

    public boolean checkForDuplications(MmsStoreToHrDepMapper storeToDepartmentMapper) {
        boolean duplicaton;
        Query query = em.createNamedQuery("MmsStoreToHrDepMapper.findByDepartmentIdAndWarehouseId", MmsStoreToHrDepMapper.class);
        query.setParameter("warehouseId", storeToDepartmentMapper.getWarehouseId());
        query.setParameter("depId", storeToDepartmentMapper.getDepartmentId());
        try {
            if (query.getResultList().size() > 0) {
                duplicaton = true;
            } else {
                duplicaton = false;
            }
            return duplicaton;
        } catch (Exception ex) {
            return false;
        }

    }
// </editor-fold> 
    // <editor-fold defaultstate="collapsed" desc="NativeQuery">

    public List<MmsStoreToHrDepMapper> searchByWarehouseName(MmsLuWareHouse wareHouse) {
        try {
            Query query1 = em.createNativeQuery("SELECT sm.*  "
                    + "FROM mms_store_to_hr_dep_mapper sm          "
                    + "INNER JOIN mms_lu_ware_house wh "
                    + "ON sm.warehouse_id= wh.id "
                    + "WHERE wh.name Like'" + wareHouse.getName() + "%' ",
                    MmsStoreToHrDepMapper.class);
            return (List<MmsStoreToHrDepMapper>) query1.getResultList();
        } catch (Exception ex) {
            return null;
        }
    }

    public List<MmsStoreToHrDepMapper> searchByDepartmentName(HrDepartments departments) {
        try {
            Query query1 = em.createNativeQuery("SELECT sm.*  "
                    + "FROM mms_store_to_hr_dep_mapper sm          "
                    + "INNER JOIN hr_departments dep "
                    + "ON sm.department_id= dep.dep_id "
                    + "WHERE dep.dep_name Like'" + departments.getDepName() + "%' ",
                    MmsStoreToHrDepMapper.class);
            return (List<MmsStoreToHrDepMapper>) query1.getResultList();
        } catch (Exception ex) {
            return null;
        }
    }

    public List<MmsStoreToHrDepMapper> getHrDepByParameter(ColumnNameResolver columnNameResolver, MmsStoreToHrDepMapper storeToDepartmentMapper, String columnValue) {
        System.out.println("columnNameResolver.getCol_Name_FromTable()==" + columnNameResolver.getCol_Name_FromTable());
        System.out.println("Col_Value==" + columnValue);
        List<MmsStoreToHrDepMapper> colValueLists = new ArrayList<>();
        if (storeToDepartmentMapper.getColumnName() != null && !storeToDepartmentMapper.getColumnName().equals("")
                && storeToDepartmentMapper.getColumnValue() != null && !storeToDepartmentMapper.getColumnValue().equals("")) {
            System.out.println("when if");
            Query query = em.createNativeQuery("SELECT * FROM MMS_STORE_TO_HR_DEP_MAPPER\n"
                    + "   WHERE " + storeToDepartmentMapper.getColumnName().toLowerCase() + "='" + storeToDepartmentMapper.getColumnValue() + "'", MmsStoreToHrDepMapper.class);
            try {
                if (query.getResultList().size() > 0) {
                    colValueLists = query.getResultList();
                    System.out.println("list of HrDep size " + colValueLists.size());
                }
                return colValueLists;
            } catch (Exception e) {
                e.getMessage();
                return null;
            }
        } else {
            System.out.println("In else " + colValueLists.size());
            Query query = em.createNamedQuery("MmsStoreToHrDepMapper.findAll");
            colValueLists = query.getResultList();
            System.out.println("list of size " + colValueLists.size());
            return colValueLists;
        }
    }
       public List<String> getMmsHrDepColumnNameList() {
        Query query = em.createNativeQuery("SELECT column_name\n"
                + "   FROM USER_TAB_COLUMNS\n"
                + "   WHERE table_name = UPPER('MMS_STORE_TO_HR_DEP_MAPPER')\n"
                + "   and COLUMN_NAME NOT IN ('ID','DEPARTMENT_ID')\n"
                + "   ORDER BY column_name ASC");
        try {
            List<String> colNameLists = new ArrayList<>();
            colNameLists = query.getResultList();
            return colNameLists;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }
// </editor-fold> 

  
}
