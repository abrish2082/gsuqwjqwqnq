package et.gov.eep.fcms.mapper.perDiem;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Query;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.fcms.entity.perDiem.FmsFieldAllowance;
import et.gov.eep.fcms.entity.perDiem.FmsLuPerdimeRate;
import et.gov.eep.hrms.entity.employee.HrEmployees;
import et.gov.eep.fcms.entity.perDiem.FmsFieldAllowanceSettlement;

/**
 *
 * @author muller
 */
@Stateless
public class FmsFieldAllowanceSettlementFacade extends AbstractFacade<FmsFieldAllowanceSettlement> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;
    List<FmsFieldAllowance> empList;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public FmsFieldAllowanceSettlementFacade() {
        super(FmsFieldAllowanceSettlement.class);
    }

     /*named query to select allowance info from FmsFieldAllowance table by Id and status(requested)
     returen employee info*/
    public FmsFieldAllowance findByempIdAndStatus(FmsFieldAllowance fmsFieldAllowanceEnty) {
        Query query = em.createNamedQuery("FmsFieldAllowance.findByempIdAndStatus", FmsFieldAllowanceSettlement.class);
        query.setParameter("id", fmsFieldAllowanceEnty.getId());
        try {
            FmsFieldAllowance Emp = (FmsFieldAllowance) query.getResultList().get(0);
            return Emp;
        } catch (Exception ex) {
            return null;
        }
    }

    /*named query to select perdiem rate info from FmsLuPerdimeRate table by JobLevelId 
     returen perdiemRate info*/
    public FmsLuPerdimeRate search(FmsLuPerdimeRate fmsLuPerdimeRate) {
        Query query = em.createNamedQuery("FmsLuPerdimeRate.findByLevel");
        query.setParameter("jobLevelId", fmsLuPerdimeRate.getJobLevelId());
        try {
            FmsLuPerdimeRate localPer = (FmsLuPerdimeRate) query.getResultList().get(0);
            return localPer;
        } catch (Exception ex) {
            return null;
        }
    }

     /*named query to select allowance settelement info from FmsFieldAllowanceSettlement table by Id
     returen settelment info*/
    public FmsFieldAllowanceSettlement getDataById(FmsFieldAllowanceSettlement allowanceSettlement) {
        Query query = em.createNamedQuery("FmsFieldAllowanceSettlement.findById");
        query.setParameter("id", allowanceSettlement.getId());
        try {
            FmsFieldAllowanceSettlement selectSettlement = (FmsFieldAllowanceSettlement) query.getResultList().get(0);
            return selectSettlement;
        } catch (Exception ex) {

            return null;
        }
    }

    
     /*named query to select employee list from FmsFieldAllowanceSettlement table by EmployeeId
     returen employee list*/
    public List<FmsFieldAllowanceSettlement> getEmplloyeId(FmsFieldAllowanceSettlement allowanceSettlement) {

        Query query = em.createNamedQuery("FmsFieldAllowanceSettlement.findByEmpId");
        query.setParameter("empId", allowanceSettlement.getEmpId());

        try {
            List<FmsFieldAllowanceSettlement> empList = (List<FmsFieldAllowanceSettlement>) query.getResultList();
            return empList;

        } catch (Exception ex) {

            return null;
        }
    }

      /*named query to select all employee list from FmsFieldAllowanceSettlement table 
     returen all employee list*/
    public List<FmsFieldAllowanceSettlement> listOfEmp(FmsFieldAllowanceSettlement allowanceSettlement) {
        List<FmsFieldAllowanceSettlement> empLis;
        Query query = em.createNamedQuery("FmsFieldAllowanceSettlement.findAll");
        try {
            empLis = (List<FmsFieldAllowanceSettlement>) query.getResultList();
            return empLis;

        } catch (Exception ex) {
            return null;
        }
    }

     /*named query to select employee list from FmsFieldAllowance table by workflowStatus(approved) and status(requested)
     returen employee list*/
    public List<FmsFieldAllowance> findByWfStatusApproved(FmsFieldAllowance fmsFieldAllowanceEnty, int wfStatusApproved) {
        Query query = em.createNamedQuery("FmsFieldAllowance.findByWfStatusApproved");
        query.setParameter("status", "Requested");
        query.setParameter("wfStatus", wfStatusApproved);

        try {
            empList = (List<FmsFieldAllowance>) query.getResultList();
            return empList;

        } catch (Exception ex) {

            return null;
        }
    }

      /*named query to select all allowance list from FmsFieldAllowanceSettlement table by employee id
     returen allowance list*/
    public List<FmsFieldAllowanceSettlement> searchSettlementByParameter(FmsFieldAllowanceSettlement allowanceSettlement) {
        Query query = em.createNamedQuery("FmsFieldAllowanceSettlement.findByAllParameters");
        query.setParameter("empId", allowanceSettlement.getEmpId());
        try {
            ArrayList<FmsFieldAllowanceSettlement> allowanceList = new ArrayList(query.getResultList());
            return allowanceList;
        } catch (Exception ex) {

            return null;
        }
    }

      /*named query to select all prepared list from FmsFieldAllowanceSettlement table by workflow status
     returen prepared list*/
    public List<FmsFieldAllowanceSettlement> findFaByWfStatus(int status) {
        Query query = em.createNamedQuery("FmsFieldAllowanceSettlement.findFaByWfStatus");
        query.setParameter("wfStatus", status);
        try {
            List<FmsFieldAllowanceSettlement> faPreparedList = query.getResultList();
            return faPreparedList;
        } catch (Exception ex) {
            return null;
        }
    }

      /*native query to select all list
     from FMS_FIELD_ALLOWANCE_SETTLEMENT,FMS_FIELD_ALLOWANCE, hr_employees table 
     using Hr first letter of first name of employee match its first letter 
     returen selected List info*/
    public List<FmsFieldAllowanceSettlement> searchEmpByEmpName(HrEmployees empEnty) {
        try {
            Query query1 = em.createNativeQuery("SELECT * "
                    + "FROM FMS_FIELD_ALLOWANCE_SETTLEMENT fas "
                    + "INNER JOIN FMS_FIELD_ALLOWANCE fa "
                    + "ON fas.ALLOWANCE_REQ_ID = fa.id "
                    + "INNER JOIN hr_employees hr "
                    + "ON fa.emp_id= hr.id "
                    + "WHERE hr.FIRST_NAME LIKE  '" + empEnty.getFirstName().toUpperCase() + "%'", FmsFieldAllowanceSettlement.class
            );
            return (List<FmsFieldAllowanceSettlement>) query1.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

         /*native query to select all list
     from FMS_FIELD_ALLOWANCE_SETTLEMENT,FMS_FIELD_ALLOWANCE, hr_employees table 
     using Hr employee id match its first letter employeeId's
     returen selected List info*/
    public List<FmsFieldAllowanceSettlement> searchEmployeeByEmpId(HrEmployees empEnty) {
        try {
            Query query1 = em.createNativeQuery("SELECT * "
                    + "FROM FMS_FIELD_ALLOWANCE_SETTLEMENT fas "
                    + "INNER JOIN FMS_FIELD_ALLOWANCE fa "
                    + "ON fas.ALLOWANCE_REQ_ID = fa.id "
                    + "INNER JOIN hr_employees hr "
                    + "ON fa.emp_id= hr.id "
                    + "WHERE hr.emp_id LIKE  '" + empEnty.getEmpId().toUpperCase() + "%'", FmsFieldAllowanceSettlement.class);
            return (List<FmsFieldAllowanceSettlement>) query1.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

 /*native query to select all list
     from FMS_FIELD_ALLOWANCE_SETTLEMENT table whose cashrecieptvoucher reference number does not exist  in FMS_CASH_RECEIPT_VOUCHER table
     using reference number
     returen selected List info*/
    public List<FmsFieldAllowanceSettlement> searchSettlementtVocher() {
        try {
            List<FmsFieldAllowanceSettlement> allowanceSettlements = new ArrayList<>();
            Query query1 = em.createNativeQuery("SELECT * FROM FMS_FIELD_ALLOWANCE_SETTLEMENT  FAS"
                    + " WHERE NOT EXISTS(SELECT CPV.REFERENCE_NUMBER FROM FMS_CASH_RECEIPT_VOUCHER CPV \n"
                    + "WHERE FAS.ID=CPV.REFERENCE_NUMBER)", FmsFieldAllowanceSettlement.class);
            List<Object[]> results = query1.getResultList();
            allowanceSettlements = (List<FmsFieldAllowanceSettlement>) query1.getResultList();

            return allowanceSettlements;
        } catch (Exception e) {
            return null;
        }
    }

   /*native query to select all list
     from FMS_FIELD_ALLOWANCE_SETTLEMENT table 
     using Allowance Request Id
     returen selected List info*/
    public FmsFieldAllowanceSettlement searchSetlementchekeVocher(int id) {
        try {
            Query query1 = em.createNativeQuery("SELECT *FROM FMS_FIELD_ALLOWANCE_SETTLEMENT where FMS_FIELD_ALLOWANCE_SETTLEMENT.ALLOWANCE_REQ_ID=" + id, FmsFieldAllowanceSettlement.class);
            FmsFieldAllowanceSettlement fmsFieldAllowance = null;
            FmsFieldAllowanceSettlement fieldAllowances = new FmsFieldAllowanceSettlement();

            fieldAllowances = (FmsFieldAllowanceSettlement) query1.getResultList().get(0);
            return fieldAllowances;
        } catch (Exception e) {
            return null;
        }
    }

}
