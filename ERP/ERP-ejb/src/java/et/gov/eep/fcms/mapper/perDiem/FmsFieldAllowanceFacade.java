package et.gov.eep.fcms.mapper.perDiem;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import et.gov.eep.fcms.entity.perDiem.FmsFieldAllowance;
import et.gov.eep.hrms.entity.employee.HrEmployees;
import et.gov.eep.commonApplications.mapper.AbstractFacade;

/**
 *
 * @author muller
 */
@Stateless
public class FmsFieldAllowanceFacade extends AbstractFacade<FmsFieldAllowance> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public FmsFieldAllowanceFacade() {
        super(FmsFieldAllowance.class);
    }

       /*named query to select fieldAllowance info from FmsFieldAllowance table using preparedDate
     returen fieldAllowance info*/
    public FmsFieldAllowance gethFieldAllowance(FmsFieldAllowance fmsFieldAllowanceEnty) {
        Query query = em.createNamedQuery("FmsFieldAllowance.findByPreparedDate", FmsFieldAllowance.class);
        query.setParameter("preparedDate", fmsFieldAllowanceEnty.getPreparedDate());
        try {
            FmsFieldAllowance fieldAllowance = (FmsFieldAllowance) query.getResultList().get(0);
            return fieldAllowance;
        } catch (Exception ex) {

            return null;
        }
    }
    
     /*named query to select fieldAllowance info from FmsFieldAllowance table using EmployeeId
     returen fieldAllowance info*/
    public FmsFieldAllowance getEmployeId(FmsFieldAllowance fmsFieldAllowanceEnty) {
        Query query = em.createNamedQuery("FmsFieldAllowance.findByempId", FmsFieldAllowance.class);
        query.setParameter("empId", fmsFieldAllowanceEnty.getEmpId());
        try {
            FmsFieldAllowance fieldAllowance = (FmsFieldAllowance) query.getResultList().get(0);
            return fieldAllowance;
        } catch (Exception ex) {
            return null;
        }
    }

     /*named query to select fieldAllowance info from FmsFieldAllowance table using Id
     returen fieldAllowance info*/
    public FmsFieldAllowance getById(FmsFieldAllowance fmsFieldAllowanceEnty) {
        Query query = em.createNamedQuery("FmsFieldAllowance.findById");
        query.setParameter("id", fmsFieldAllowanceEnty.getId());
        try {
            FmsFieldAllowance selectEmp = (FmsFieldAllowance) query.getResultList().get(0);
            return selectEmp;
        } catch (Exception ex) {

            return null;
        }
    }

     /*named query to select fieldAllowance info from FmsFieldAllowance table using RequestNumber
     returen fieldAllowance info*/
    public FmsFieldAllowance getByRequstno(FmsFieldAllowance fmsFieldAllowanceEnty) {
        Query query = em.createNamedQuery("FmsFieldAllowance.findByRequestNo");
        query.setParameter("requestNo", fmsFieldAllowanceEnty.getRequestNo());
        try {

            FmsFieldAllowance selectEmp = (FmsFieldAllowance) query.getResultList().get(0);
            return selectEmp;
        } catch (Exception ex) {

            return null;
        }
    }

    /*named query to select Employee list from HrEmployees table using Employee's name first letter match
     returen employee List*/
    public ArrayList<HrEmployees> SearchName(HrEmployees employeeEnty) {
        Query query = em.createNamedQuery("HrEmployees.findByEmployeeName");
        query.setParameter("employeeName", employeeEnty.getFirstName() + '%');
        try {
            ArrayList<HrEmployees> empList = new ArrayList(query.getResultList());
            return empList;
        } catch (Exception ex) {

            return null;
        } //To change body of generated methods, choose Tools | Templates.
    }

    /*named query to select fieldAllowance list from FmsFieldAllowance table using preparedDate
     returen fieldAllowance list*/
    public List<FmsFieldAllowance> searchFieldAllowance(FmsFieldAllowance fmsFieldAllowanceEnty) {

        List<FmsFieldAllowance> allowancesList = null;
        try {
            Query query = em.createNamedQuery("FmsFieldAllowance.findByPreparedDate", FmsFieldAllowance.class);
            query.setParameter("preparedDate", fmsFieldAllowanceEnty.getPreparedDate() + "%");
            allowancesList = (List<FmsFieldAllowance>) query.getResultList();
            return allowancesList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

      /*named query to select fieldAllowance list from FmsFieldAllowance table using workflow status
     returen fieldAllowancePrepared list*/
    public List<FmsFieldAllowance> findFaByWfStatus(int status) {
        Query query = em.createNamedQuery("FmsFieldAllowance.findFaByWfStatus");
        query.setParameter("wfStatus", status);
        try {
            List<FmsFieldAllowance> faPreparedList = query.getResultList();
            return faPreparedList;
        } catch (Exception ex) {
            return null;
        }
    }

        /*native query to select all list
     from fms_field_allowance, hr_employees table 
     using Hr first letter of name of employee
     returen selected List info*/
    public List<FmsFieldAllowance> searchEmpByEmpName(HrEmployees empEnty) {
        try {
            Query query1 = em.createNativeQuery("SELECT * "
                    + "FROM fms_field_allowance fa "
                    + "INNER JOIN hr_employees hr "
                    + "ON fa.emp_id= hr.id "
                    + "WHERE hr.FIRST_NAME LIKE  '" + empEnty.getFirstName().toUpperCase() + "%'", FmsFieldAllowance.class);
            return (List<FmsFieldAllowance>) query1.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

       /*native query to select all list
     from fms_field_allowance, hr_employees table 
     using Hr first letter of name of employee
     returen selected List info*/
    public List<FmsFieldAllowance> searchEmployeeByEmpId(HrEmployees empEnty) {
        try {
            Query query1 = em.createNativeQuery("SELECT * "
                    + "FROM fms_field_allowance fa "
                    + "INNER JOIN hr_employees hr "
                    + "ON fa.emp_id= hr.id "
                    + "WHERE hr.emp_id LIKE  '" + empEnty.getEmpId().toUpperCase() + "%'", FmsFieldAllowance.class);
            return (List<FmsFieldAllowance>) query1.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

       /*native query to select all list
     from fms_field_allowance table whose cpo is not exist  in FMS_CASH_PAYMENT_ORDER table
     using request number
     returen selected List info*/
    public List<FmsFieldAllowance> searchnotexitvocher() {
        Query query = em.createNativeQuery("SELECT * FROM FMS_FIELD_ALLOWANCE FA "
                + "WHERE NOT EXISTS(SELECT CPO.REFERENCE_NO FROM FMS_CASH_PAYMENT_ORDER CPO"
                + " WHERE fa.request_no=cpo.reference_no)", FmsFieldAllowance.class);
        try {
            List<FmsFieldAllowance> paymentRequeList = new ArrayList<>();
            if (query.getResultList().size() > 0) {
                paymentRequeList = query.getResultList();
            }
            return paymentRequeList;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /*native query to select all list
     from fms_field_allowance, FMS_FIELD_ALLOWANCE_SETTLEMENT table whose reference number is not exist  in FMS_CASH_RECEIPT_VOUCHER table
     using request number
     returen selected List info*/
    public List<FmsFieldAllowance> searchSetlementchekeVocher() {
        try {
            Query query1 = em.createNativeQuery("SELECT FA.* FROM FMS_FIELD_ALLOWANCE_SETTLEMENT FAS ,"
                    + "  FMS_FIELD_ALLOWANCE FA WHERE FA.ID =  FAS.ALLOWANCE_REQ_ID AND"
                    + " NOT EXISTS(SELECT CRV.REFERENCE_NUMBER FROM FMS_CASH_RECEIPT_VOUCHER CRV "
                    + "WHERE FA.request_no=CRV.REFERENCE_NUMBER)");
            List<Object[]> results = query1.getResultList();
            System.err.println("--size-2-" + results.size());
            FmsFieldAllowance fmsFieldAllowance = null;
            List<FmsFieldAllowance> fieldAllowances = new ArrayList<>();
            for (Object[] result : results) {
                fmsFieldAllowance = new FmsFieldAllowance();
                fmsFieldAllowance.setId(Integer.parseInt(result[0].toString()));
                fmsFieldAllowance.setRequestNo(result[13].toString());
                fieldAllowances.add(fmsFieldAllowance);
            }

            return fieldAllowances;
        } catch (Exception e) {
            return null;
        }
    }

    /*native query to select all list from FMS_FIELD_ALLOWANCE */
    public Integer countRow() {
        Query query = em.createNativeQuery("SELECT COUNT (*) FROM FMS_FIELD_ALLOWANCE");
        BigDecimal rows = (BigDecimal) query.getSingleResult();
        return rows.intValue();
    }
}
