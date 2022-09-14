package et.gov.eep.fcms.mapper.perDiem;

import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.fcms.entity.perDiem.FmsFieldAllowanceForeign;
import et.gov.eep.fcms.entity.perDiem.FmsGoodWillingPayment;
import et.gov.eep.fcms.entity.perDiem.FmsLuAdditionalAmount;
import et.gov.eep.fcms.entity.perDiem.FmsLuPerdimeRate;
import et.gov.eep.hrms.entity.employee.HrEmployees;

/**
 *
 * @author muller
 */
@Stateless
public class FmsFieldAllowanceForeignFacade extends AbstractFacade<FmsFieldAllowanceForeign> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;
    List<FmsFieldAllowanceForeign> listempName = null;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public FmsFieldAllowanceForeignFacade() {
        super(FmsFieldAllowanceForeign.class);
    }

    /*named query to select level info from FmsLuAdditionalAmount table using LevelID
     returen Level info*/
    public FmsLuAdditionalAmount searchAdd(String levelId) {
        Query query = em.createNamedQuery("FmsLuAdditionalAmount.findByLevelId", FmsLuAdditionalAmount.class);
        query.setParameter("levelId", levelId);
        try {
            FmsLuAdditionalAmount levelList = (FmsLuAdditionalAmount) query.getResultList().get(0);
            return levelList;
        } catch (Exception ex) {
            return null;
        }
    }

    /*named query to select all info from FmsGoodWillingPayment table
     returen all goodwill info*/
    public FmsGoodWillingPayment searchgoood(FmsGoodWillingPayment fmsGoodWillingPayment) {
        Query query = em.createNamedQuery("FmsGoodWillingPayment.findAll", FmsGoodWillingPayment.class);
        try {
            FmsGoodWillingPayment Goodwill = (FmsGoodWillingPayment) query.getResultList().get(0);
            return Goodwill;
        } catch (Exception ex) {
            return null;
        }
    }

    /*named query to select job level id info from FmsLuPerdimeRate table using jobLevelID
     returen all job level info*/
    public FmsLuPerdimeRate search(Integer levId) {
        Query query = em.createNamedQuery("FmsLuPerdimeRate.findByLevel", FmsLuPerdimeRate.class);
        query.setParameter("jobLevelId", levId);
        try {
            FmsLuPerdimeRate localPer = (FmsLuPerdimeRate) query.getResultList().get(0);
            return localPer;
        } catch (Exception ex) {
            return null;
        }
    }

    /*named query to select allowance foreign info from FmsFieldAllowanceForeign table
     returen  allowance foreign info*/
    public FmsFieldAllowanceForeign findEmployee(FmsFieldAllowanceForeign allowanceForeign) {
        Query query = em.createNamedQuery("FmsFieldAllowanceForeign.findByEmpId", FmsFieldAllowanceForeign.class);
        query.setParameter("empId", allowanceForeign.getEmpId());
        try {
            FmsFieldAllowanceForeign allowanceForeign1 = (FmsFieldAllowanceForeign) query.getResultList().get(0);
            return allowanceForeign1;
        } catch (Exception ex) {
            return null;
        }
    }
    
    /*named query to select allowance foreign info from FmsFieldAllowanceForeign table by employee name
     returen  allowance foreign info*/
    public FmsFieldAllowanceForeign getAllEmployees(FmsFieldAllowanceForeign allowanceForeign) {
        Query query = em.createNamedQuery("FmsFieldAllowanceForeign.findByEmployeeName", FmsFieldAllowanceForeign.class);
        query.setParameter("employeeName", allowanceForeign.getEmpId().getFirstName().toUpperCase());
        try {
            FmsFieldAllowanceForeign allowanceForeign1 = (FmsFieldAllowanceForeign) query.getResultList().get(0);
            return allowanceForeign1;
        } catch (Exception ex) {
            return null;
        }
    }
 
    /*named query to select employee info from FmsFieldAllowanceForeign table by Id
     returen  allowance foreign info*/
    public FmsFieldAllowanceForeign getByID(FmsFieldAllowanceForeign allowanceForeign) {
        Query query = em.createNamedQuery("FmsFieldAllowanceForeign.findById");
        query.setParameter("id", allowanceForeign.getId());
        try {
            FmsFieldAllowanceForeign selectEmp = (FmsFieldAllowanceForeign) query.getResultList().get(0);
            return selectEmp;
        } catch (Exception ex) {
            return null;
        }
    }
    
    /*named query to select allowance foreign list from FmsFieldAllowanceForeign table by employee name
     returen  employee name list*/
    public List<FmsFieldAllowanceForeign> SearchEmpName(FmsFieldAllowanceForeign allowanceForeign) {
        try {
            Query query = em.createNamedQuery("FmsFieldAllowanceForeign.findByEmployeeName");
            listempName = (List<FmsFieldAllowanceForeign>) query.getResultList();
            return listempName;
        } catch (Exception e) {
            return null;
        }
    }

    /*named query to select allowance foreign list from FmsFieldAllowanceForeign table by workflow status
     returen  allowance foreign prepared list*/
    public List<FmsFieldAllowanceForeign> findFaByWfStatus(int status) {
        Query query = em.createNamedQuery("FmsFieldAllowanceForeign.findFaByWfStatus");
        query.setParameter("wfStatus", status);
        try {
            List<FmsFieldAllowanceForeign> faPreparedList = query.getResultList();
            return faPreparedList;
        } catch (Exception ex) {
            return null;
        }
    }

     /*native query to select all list
     from fms_field_allowance_foreign, hr_employees table 
     using Hr first letter of name of employee first letter match
     returen selected List info*/
    public List<FmsFieldAllowanceForeign> searchEmpByEmpName(HrEmployees empEnty) {
        try {
            Query query1 = em.createNativeQuery("SELECT * "
                    + "FROM fms_field_allowance_foreign fa "
                    + "INNER JOIN hr_employees hr "
                    + "ON fa.emp_id= hr.id "
                    + "WHERE hr.FIRST_NAME LIKE  '" + empEnty.getFirstName().toUpperCase() + "%'", FmsFieldAllowanceForeign.class);
            return (List<FmsFieldAllowanceForeign>) query1.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    
     /*native query to select all list
     from fms_field_allowance_foreign, hr_employees table 
     using Employee Id's first letter match
     returen selected List info*/
    public List<FmsFieldAllowanceForeign> searchEmployeeByEmpId(HrEmployees empEnty) {
        try {
            Query query1 = em.createNativeQuery("SELECT * "
                    + "FROM fms_field_allowance_foreign fa "
                    + "INNER JOIN hr_employees hr "
                    + "ON fa.emp_id= hr.id "
                    + "WHERE hr.emp_id LIKE  '" + empEnty.getEmpId().toUpperCase() + "%'", FmsFieldAllowanceForeign.class);
            return (List<FmsFieldAllowanceForeign>) query1.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    /*native query to select count of all value from FMS_FIELD_ALLOWANCE_FOREIGN*/
    public Integer countRow() {
        Query query = em.createNativeQuery("SELECT COUNT (*) FROM FMS_FIELD_ALLOWANCE_FOREIGN");
        BigDecimal rows = (BigDecimal) query.getSingleResult();
        return rows.intValue();
    }
}
