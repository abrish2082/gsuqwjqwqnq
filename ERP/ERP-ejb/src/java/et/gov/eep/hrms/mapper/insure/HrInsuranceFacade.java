/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.mapper.insure;

import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.hrms.entity.insurance.HrInsurance;
import et.gov.eep.hrms.entity.insurance.HrLuInsurances;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author meles
 */
@Stateless
public class HrInsuranceFacade extends AbstractFacade<HrInsurance> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public HrInsuranceFacade() {
        super(HrInsurance.class);
    }

    //<editor-fold defaultstate="collapsed" desc="Bussiness IMmplementatin">
    public ArrayList<HrInsurance> searchByTerminationName(HrInsurance HrInsurance) {
        Query query = em.createNamedQuery("HrInsurance.findByTerminationNameLike");
        query.setParameter("ttelephone", HrInsurance.getTelephone().toUpperCase() + '%');
        try {
            ArrayList<HrInsurance> terminationNameList = new ArrayList(query.getResultList());
            return terminationNameList;
        } catch (Exception ex) {
            return null;
        }
    }
    
    public ArrayList<HrInsurance> searchByTerminationName(ArrayList<HrInsurance> insurancepro) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public HrInsurance getByTerminationName(HrInsurance HrInsurance) {
        Query query = em.createNamedQuery("HrInsurance.findByTelephone");
        query.setParameter("telephone", HrInsurance.getTelephone());
        try {
            HrInsurance terminationName = (HrInsurance) query.getResultList().get(0);
            return terminationName;
        } catch (Exception ex) {
            return null;
        }
    }
    
    public boolean searchduplicate(HrInsurance HrInsurance) {
        boolean duplicaton;
        Query query = em.createNamedQuery("HrInsurance.searchbyduplicate", HrInsurance.class);
        query.setParameter("InsuranceName", HrInsurance.getInsuranceId().getInsuranceName());
        query.setParameter("BranchName", HrInsurance.getBranchId().getBranchName());
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
    
    public ArrayList<HrInsurance> hrLuInsurances(HrInsurance HrInsurance) {
        Query query = em.createNamedQuery("HrInsurance.findByTerminationNameLike");
        query.setParameter("ttelephone", HrInsurance.getTelephone().toUpperCase() + '%');
        try {
            ArrayList<HrInsurance> terminationNameList = new ArrayList(query.getResultList());
            return terminationNameList;
        } catch (Exception ex) {
            return null;
        }
    }
    
    public ArrayList<HrInsurance> hrLuInsurances(HrLuInsurances hrLuInsurances) {
        Query query = em.createNamedQuery("HrInsurance.findByInsuranceNameLike");
        query.setParameter("getInsuranceName", hrLuInsurances.getInsuranceName().toUpperCase() + '%');
        try {
            ArrayList<HrInsurance> terminationNameList = new ArrayList(query.getResultList());
            return terminationNameList;
        } catch (Exception ex) {
            return null;
        }
    }
    
    public HrInsurance getByInsuranceName(HrLuInsurances hrLuInsurances) {
        Query query = em.createNamedQuery("HrInsurance.findById");
        query.setParameter("id", hrLuInsurances.getId());
        try {
            HrInsurance terminationName = (HrInsurance) query.getResultList().get(0);
            return terminationName;
        } catch (Exception ex) {
            return null;
        }
    }
    
    public List<HrInsurance> loadFiltereddata(int status) {
        String queryStatus = " WHERE STATUS='" + status + "' ";
        if (status == 2) {
            queryStatus = " WHERE(STATUS='0' OR STATUS='1')";
        }
        Query query = em.createNativeQuery("SELECT * FROM HR_INSURANCE "
                + queryStatus
                + "ORDER BY POLICY_NO DESC", HrInsurance.class);
        try {
            return (List<HrInsurance>) query.getResultList();
        } catch (Exception ex) {
            return null;
        }
    }
    
    public List<HrInsurance> ActiveLists(Integer Status) {
        Query query = em.createNamedQuery("HrInsurance.findByStatus", HrInsurance.class);
        query.setParameter("status", Status);
        try {
            
            List<HrInsurance> compList = new ArrayList(query.getResultList());
            return compList;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    public HrInsurance getSelectedPayment(Integer idfind) {
        Query query = em.createNamedQuery("HrInsurance.findById");
        query.setParameter("id", idfind);
        try {
            HrInsurance SelectedPayment = (HrInsurance) query.getResultList().get(0);
            return SelectedPayment;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
//</editor-fold>

}
