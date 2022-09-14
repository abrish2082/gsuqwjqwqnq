package et.gov.eep.fcms.mapper.pettyCash;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.fcms.entity.pettyCash.FmsCasherAccount;
import et.gov.eep.hrms.entity.employee.HrEmployees;

/**
 *
 * @author AB
 */
@Stateless
public class FmsCasherAccountFacade extends AbstractFacade<FmsCasherAccount> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public FmsCasherAccountFacade() {
        super(FmsCasherAccount.class);
    }

    /*named query to select employee code info from FmsCasherAccount table by employee code
     returen casher accounte info*/
    public FmsCasherAccount getCasherAccountInfo(FmsCasherAccount casherAccountEnty) {

        Query query = em.createNamedQuery("FmsCasherAccount.findByEmpCodeOBJ", FmsCasherAccount.class);
        query.setParameter("empCode", casherAccountEnty.getEmpCode());
        try {
            FmsCasherAccount CasherAccount = (FmsCasherAccount) query.getResultList().get(0);
            return CasherAccount;
        } catch (Exception ex) {
            return null;
        }

    }

    /*named query to select casher account info from FmsCasherAccount table by id
     returen casher accounte info*/
    public FmsCasherAccount searchCasher(FmsCasherAccount casherAccountEnty) {
        Query query = em.createNamedQuery("FmsCasherAccount.findById", FmsCasherAccount.class);
        query.setParameter("id", casherAccountEnty.getId());
        try {
            FmsCasherAccount CasherAccount = (FmsCasherAccount) query.getResultList().get(0);
            return CasherAccount;
        } catch (Exception ex) {

            return null;
        }

    }

    /*named query to select casher code info from FmsCasherAccount table by subsideryid
     returen casher code info*/
    public FmsCasherAccount getCashierCodeBySLId(FmsCasherAccount subsidiaryId) {
        Query query = em.createNamedQuery("FmsCasherAccount.findBySubsidiaryID");
        query.setParameter("subsidiaryId", subsidiaryId.getSubsidiaryId());
        try {
            FmsCasherAccount cashierCode = (FmsCasherAccount) query.getResultList().get(0);
            return cashierCode;
        } catch (Exception ex) {
            return null;
        }
    }

    /*named query to select casher code info from FmsCasherAccount table by id
     returen casher code info*/
    public FmsCasherAccount findSlCode(FmsCasherAccount casherAccountEnty) {
        Query query = em.createNamedQuery("FmsCasherAccount.findById");
        query.setParameter("id", casherAccountEnty.getId());
        try {
            FmsCasherAccount cashierCode = (FmsCasherAccount) query.getResultList().get(0);
            return cashierCode;
        } catch (Exception ex) {
            return null;
        }
    }

    /*named query to select casher info from FmsCasherAccount table by id
     returen casher info*/
    public FmsCasherAccount getById(FmsCasherAccount casherAccountEnty) {
        Query query = em.createNamedQuery("FmsCasherAccount.findById");
        query.setParameter("id", casherAccountEnty.getId());
        try {
            FmsCasherAccount selectCasher = (FmsCasherAccount) query.getResultList().get(0);
            return selectCasher;
        } catch (Exception ex) {
            return null;
        }
    }

    /*named query to select casher list from FmsCasherAccount table by bank accountid
     returen casher list*/
    public List<FmsCasherAccount> getAllCashierName(FmsCasherAccount id) {
        ArrayList<FmsCasherAccount> cashiers = null;
        Query query = em.createNamedQuery("FmsCasherAccount.findByBankAccountId");
        query.setParameter("id", id.getId());
        try {
            cashiers = new ArrayList(query.getResultList());
            return cashiers;
        } catch (Exception ex) {
            return null;
        }
    }

    /*named query to select all list from FmsCasherAccount table 
     returen casher info*/
    public List<FmsCasherAccount> findAllCash() {
        List<FmsCasherAccount> CasherAccount;

        Query query = em.createNamedQuery("FmsCasherAccount.findAll", FmsCasherAccount.class);

        try {
            CasherAccount = (List<FmsCasherAccount>) query.getResultList();
            return CasherAccount;
        } catch (Exception ex) {
            return null;
        }
    }

    /*named query to select casher account list from FmsCasherAccount table by employee code
     returen casher account list*/
    public List<FmsCasherAccount> searchCasherName(FmsCasherAccount casherAccountEnty) {
        List<FmsCasherAccount> CasherAccountList = null;
        try {
            Query query = em.createNamedQuery("FmsCasherAccount.findByEmpCode", FmsCasherAccount.class);
            query.setParameter("empCode", casherAccountEnty.getEmpCode() + "%");
            CasherAccountList = (List<FmsCasherAccount>) query.getResultList();
            CasherAccountList.add(casherAccountEnty);
            return CasherAccountList;
        } catch (Exception e) {
            return null;
        }
    }

    /*native query to select all list
     from FMS_CASHER_ACCOUNT,hr_employees table 
     using hr first name
     returen selected list value*/
    public List<FmsCasherAccount> searchCasherByParameter(HrEmployees empEnty) {
        try {
            Query query1 = em.createNativeQuery("SELECT * FROM FMS_CASHER_ACCOUNT fc "
                    + "                     INNER JOIN hr_employees hr "
                    + "                     ON fc.EMP_CODE= hr.id "
                    + "                     WHERE hr.FIRST_NAME LIKE  '" + empEnty.getFirstName().toUpperCase() + "%'", FmsCasherAccount.class);
            return (List<FmsCasherAccount>) query1.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    /*native query to select all list
     from FMS_CASHER_ACCOUNT,hr_employees table 
     using hr id
     returen selected list value*/
    public FmsCasherAccount getCashierAccByEmpId(HrEmployees empEnty) {
        try {
            Query query = em.createNativeQuery("SELECT * FROM FMS_CASHER_ACCOUNT fca "
                    + "INNER JOIN hr_employees hre "
                    + "ON fca.emp_code=hre.ID "
                    + "WHERE hre.emp_id='" + empEnty.getEmpId() + "'", FmsCasherAccount.class);
            FmsCasherAccount fmsCasherAccount = (FmsCasherAccount) query.getResultList().get(0);
            return fmsCasherAccount;
        } catch (Exception e) {
            return null;
        }
    }
    
    /*native query to select all list
     from FMS_CASHER_ACCOUNT,hr_employees table 
     using hr id
     returen selected list value*/
    public boolean findDup(HrEmployees empEnty) {
        boolean dup;
        try {
            Query query = em.createNativeQuery("SELECT * FROM FMS_CASHER_ACCOUNT fc "
                    + "                     INNER JOIN hr_employees hr "
                    + "                     ON fc.EMP_CODE = hr.id "
                    + "                    WHERE hr.id = '" + empEnty.getId() + "'", FmsCasherAccount.class);
            if (query.getResultList().size() > 0) {
                dup = true;
            } else {
                dup = false;
            }
            return dup;
        } catch (Exception ex) {
            return false;
        }
    }
}
