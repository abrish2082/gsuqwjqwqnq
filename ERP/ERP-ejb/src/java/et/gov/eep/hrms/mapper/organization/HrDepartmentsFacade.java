/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.mapper.organization;

import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.hrms.entity.address.HrAddresses;
import et.gov.eep.hrms.entity.organization.HrDepartments;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

/**
 *
 * @author Administrator
 */
@Stateless
public class HrDepartmentsFacade extends AbstractFacade<HrDepartments> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    /**
     *
     * @return
     */
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    /**
     *
     */
    public HrDepartmentsFacade() {
        super(HrDepartments.class);
    }

    /**
     *
     * @param id
     * @return
     */
    public HrDepartments serchDepById(int id) {
        Query query = em.createNamedQuery("HrDepartments.findByDepId");
        query.setParameter("depId", id);
        return (HrDepartments) query.getSingleResult();
    }

    /**
     *
     * @param id
     * @return
     */
    public List<HrDepartments> searchByParentId(int id) {
        Query query = em.createNamedQuery("HrDepartments.findByParentId");
        query.setParameter("parentId", id);
        return query.getResultList();
    }

    /**
     *
     * @return
     */
    public ArrayList<HrDepartments> searchAllDepSorted() {
        Query query = em.createNamedQuery("HrDepartments.findAllBySorted");

        try {
            ArrayList<HrDepartments> departmentsList = new ArrayList(query.getResultList());

            return departmentsList;

        } catch (Exception ex) {
            return null;
        }
    }

    /**
     *
     * @return
     */
    public List<HrDepartments> getDeptID() {
        List<HrDepartments> deptList = null;
        try {
            Query query = em.createNamedQuery("HrDepartments.findAll", HrDepartments.class);

            deptList = (List<HrDepartments>) query.getResultList();
            return deptList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    //FOR MMS USE
    /**
     *
     * @param hrDepartments
     * @return
     */
    public ArrayList<HrDepartments> getdepartmetInfoByName(HrDepartments hrDepartments) {
        ArrayList<HrDepartments> mmsHrInfo = null;
        try {
            Query query = em.createNamedQuery("HrDepartments.findByDepNameLike", HrDepartments.class);
            query.setParameter("depName", hrDepartments.getDepName() + "%");
            mmsHrInfo = new ArrayList(query.getResultList());
            return mmsHrInfo;
        } catch (Exception e) {
            return null;
        }

    }

    /**
     *
     * @param hrDepartments
     * @return
     */
    public HrDepartments getdepartmentInformation(HrDepartments hrDepartments) {
        Query query = em.createNamedQuery("HrDepartments.findByDepName", HrDepartments.class);
        query.setParameter("depName", hrDepartments.getDepName());
        try {
            HrDepartments depInformation = (HrDepartments) query.getResultList().get(0);
            return depInformation;
        } catch (Exception ex) {

            return null;
        }
    }

    /**
     * munir code
     *
     * @return list of departments
     */
    public List<HrDepartments> findAllDepartment() {
        Query query = em.createNativeQuery("SELECT DEP_ID, "
                + "MISSION,VISION,EST_DATE, "
                + "LEVEL,LPAD(' ', 5* LEVEL - 1)|| DEP_NAME || '    ' ,DEP_NAME,PARENT_ID "
                + "FROM HR_DEPARTMENTS "
                + "START WITH DEP_ID=1 "
                + "CONNECT BY nocycle PRIOR DEP_ID=PARENT_ID ", HrDepartments.class);
        try {
            return (List<HrDepartments>) query.getResultList();
        } catch (Exception ex) {
            return null;
        }
    }

    public List<HrDepartments> findAllDepartmentWith() {
        Query query = em.createNativeQuery("SELECT HR_DEPARTMENTS.DEP_ID, \n"
                + "                 MISSION,\n"
                + "                 VISION,\n"
                + "                 EST_DATE, \n"
                + "                 LEVEL,LPAD(' ', 5* LEVEL - 1)|| DEP_NAME || '    ' ,\n"
                + "                 DEP_NAME,\n"
                + "                 PARENT_ID,\n"
                + "                 FMS_COST_CENTER.SYSTEM_NAME COST_CENTER_NAME,\n"
                + "                 FMS_SYSTEM.SYSTEM_NAME,\n"
                + "                 HR_PAYROLL_PL_PG.PAY_LOCATION,\n"
                + "                 HR_PAYROLL_PL_PG.PAY_GROUP\n"
                + "                 FROM HR_DEPARTMENTS \n"
                + "                 lEFT JOIN FMS_COST_CENTER \n"
                + "                 ON HR_DEPARTMENTS.DEP_ID = FMS_COST_CENTER.DEP_ID \n"
                + "                 lEFT JOIN FMS_SYSTEM \n"
                + "                 ON FMS_COST_CENTER.SYSTEM_ID = FMS_SYSTEM.SYSTEM_ID \n"
                + "                 lEFT JOIN HR_PAYROLL_PL_PG \n"
                + "                 ON HR_DEPARTMENTS.DEP_ID = HR_PAYROLL_PL_PG.DEPT_ID \n"
                + "                 START WITH HR_DEPARTMENTS.DEP_ID='1' \n"
                + "                 CONNECT BY nocycle PRIOR HR_DEPARTMENTS.DEP_ID=HR_DEPARTMENTS.PARENT_ID;", HrDepartments.class);
        try {
            return (List<HrDepartments>) query.getResultList();
        } catch (Exception ex) {
            return null;
        }
    }

    /**
     *
     * @param hrDepartments
     * @return
     */
    public HrDepartments findByDepartmentId(HrDepartments hrDepartments) {
        Query query = em.createNamedQuery("HrDepartments.findByDepId");
        query.setParameter("depId", hrDepartments.getDepId());
        try {
            HrDepartments getDepartment = (HrDepartments) query.getResultList().get(0);
            return getDepartment;
        } catch (Exception ex) {
            return null;
        }
    }

    public HrDepartments findByDeptParentId(int parentId) {
        Query query = em.createNamedQuery("HrDepartments.findByDepId");
        query.setParameter("depId", parentId);
        try {
            HrDepartments getDepartment = (HrDepartments) query.getResultList().get(0);
            return getDepartment;
        } catch (Exception ex) {
            return null;
        }
    }

    public List<HrAddresses> findAllAddressUnit() {
        Query query = em.createNativeQuery("SELECT HR_ADDRESSES.*, "
                + "LEVEL, LPAD(' ', 5* LEVEL - 1)|| ADDRESS_DESCRIPTION || '    ' "
                + "FROM HR_ADDRESSES "
                + "WHERE (PARENT_ID != 0 OR ADDRESS_ID = 1) AND STATUS = 1 "
                + "START WITH ADDRESS_ID = 1 "
                + "CONNECT BY nocycle "
                + "PRIOR ADDRESS_ID = PARENT_ID", HrAddresses.class);
        try {
            return (List<HrAddresses>) query.getResultList();
        } catch (Exception ex) {
            return null;
        }
    }

    public HrAddresses findAllAddressUnitAsOne(HrAddresses hrAddresses) {
        Query query = em.createNativeQuery("SELECT HR_ADDRESSES.ADDRESS_ID, SUBSTR(sys_connect_by_path( "
                + "HR_ADDRESSES.ADDRESS_NAME, ', ' ), 2) as ADDRESS_NAME "
                + "FROM HR_ADDRESSES "
                + "WHERE ADDRESS_ID = ? "
                + "START WITH HR_ADDRESSES.PARENT_ID = 0 "
                + "CONNECT BY PRIOR ADDRESS_ID = PARENT_ID", HrAddresses.class);
        query.setParameter(1, hrAddresses.getAddressId());
        try {
            return (HrAddresses) query.getSingleResult();
        } catch (Exception ex) {
            return null;
        }
    }

    //get Department Name By Store Requisiotion Number
    public HrDepartments getDepNameBySrNo(String sRNo) {
        Query query = em.createNativeQuery("SELECT * FROM HR_DEPARTMENTS hr\n"
                + "inner join mms_storereq ms\n"
                + "on hr.dep_id=ms.department\n"
                + "where ms.sr_no='" + sRNo + "'", HrDepartments.class);
        HrDepartments depName = new HrDepartments();
        if (query.getResultList().size() > 0) {
            depName = (HrDepartments) query.getResultList().get(0);
        }
        return depName;
    }

    public HrDepartments findByDeptID(int parentId) {
        Query query = em.createNamedQuery("HrDepartments.findByDepId");
        query.setParameter("depId", parentId);
        try {
            HrDepartments getDepartment = (HrDepartments) query.getResultList().get(0);
            return getDepartment;
        } catch (Exception ex) {
            return null;
        }
    }

    public HrDepartments findDepartmentbyName(HrDepartments department) {
        Query query = em.createNamedQuery("HrDepartments.findByDepName");
        query.setParameter("depName", department.getDepName());
        try {
            System.err.println("dadfa=" + query.getResultList().size());
            HrDepartments deptname = (HrDepartments) query.getResultList().get(0);
            return deptname;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * @meles
     */
    private Throwable getLastThrowable(Exception e) {
        Throwable t = null;
        for (t = e.getCause(); t.getCause() != null; t = t.getCause());
        return t;
    }

    public int deleteDepartmentInfo(HrDepartments hrDepartments) {
        Query query = em.createNativeQuery("DELETE FROM HR_DEPARTMENTS "
                + "WHERE PARENT_ID = ? OR DEP_ID = ?");
        try {
            query.setParameter(1, hrDepartments.getDepId());
            query.setParameter(2, hrDepartments.getDepId());
            return query.executeUpdate();
        } catch (PersistenceException ex) {
            Throwable t = getLastThrowable(ex);  //fetching Internal Exception
            SQLException sqlEx = (SQLException) t;  //casting Throwable object to SQL Exception
            if (Integer.valueOf(sqlEx.getSQLState()) == 23000) // Integrity constraint violation
            {
                return -1;
            } else {
                return 0;
            }
        }
    }
      public List<String> getMmsHrDepColumnNameList() {
          Query query = em.createNativeQuery("SELECT column_name\n"
                + "   FROM USER_TAB_COLUMNS\n"
                + "   WHERE table_name = UPPER('HR_DEPARTMENTS')\n"
                + "   and COLUMN_NAME NOT IN ('DEP_ID','PARENT_ID','REGION_ID')\n"
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
    // @NamedQuery(name = "HrEmployees.findByEmpIdLike", query = "SELECT h FROM HrEmployees h WHERE UPPER(h.empId) LIKE :empId"),

    public ArrayList<HrDepartments> SearchByDeptId(HrDepartments hrDepartments) {
        try {
            Query query = em.createNamedQuery("HrDepartments.findByDepNameLike", HrDepartments.class);
            query.setParameter("depName", hrDepartments.getDepName().toUpperCase() + '%');
            ArrayList<HrDepartments> deptList = new ArrayList(query.getResultList());
            return deptList;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

  

}
