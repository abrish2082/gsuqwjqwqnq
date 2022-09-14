/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.mapper.insure;

import et.gov.eep.commonApplications.entity.Status;
import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.hrms.entity.insurance.HrInsuranceDiagnosisResult;
import et.gov.eep.hrms.entity.insurance.HrInsurancePayment;
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
public class HrInsurancePaymentFacade extends AbstractFacade<HrInsurancePayment> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    //<editor-fold defaultstate="collapsed" desc="Bussiness IMmplementatin">
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    public HrInsurancePaymentFacade() {
        super(HrInsurancePayment.class);
    }
    
    public List<HrInsurancePayment> populateTableApprove(Status status, int userId) {
        try {
            Query query = em.createNativeQuery("SELECT *\n"
                    + " FROM HR_INSURANCE_PAYMENT rq\n"
                    + " WHERE (rq.status='" + status.getStatus1() + "' OR"
                    + " rq.status='" + status.getStatus2() + "')", HrInsurancePayment.class);
            System.out.println("-----result----" + query.getResultList());
            return (List<HrInsurancePayment>) query.getResultList();
        } catch (Exception ex) {
            return null;
        }
    }
    
    public List<HrInsurancePayment> loadFiltereddata(Status status, int userId) {
        try {
            Query query = em.createNativeQuery("SELECT *\n"
                    + " FROM HR_INSURANCE_PAYMENT rq\n"
                    + " WHERE (rq.status='" + status.getStatus1() + "')", HrInsurancePayment.class);
            System.out.println("-----result----" + query.getResultList());
            return (List<HrInsurancePayment>) query.getResultList();
        } catch (Exception ex) {
            return null;
        }
    }
    
    public List<HrInsurancePayment> populateTableReject(Status status, int userId) {
        try {
            Query query = em.createNativeQuery("SELECT *\n"
                    + " FROM HR_INSURANCE_PAYMENT rq\n"
                    + " WHERE (rq.status='" + status.getStatus2() + "' OR"
                    + " rq.status='" + status.getStatus3() + "')", HrInsurancePayment.class);
            System.out.println("-----result----" + query.getResultList());
            return (List<HrInsurancePayment>) query.getResultList();
        } catch (Exception ex) {
            return null;
        }
    }
    
    public HrInsurancePayment getSelectedRequest(Integer id) {
        Query query = em.createNamedQuery("HrInsurancePayment.findById");
        query.setParameter("id", id);
        try {
            HrInsurancePayment selectrequest = (HrInsurancePayment) query.getResultList().get(0);
            return selectrequest;
        } catch (Exception ex) {
            return null;
        }
    }
    
    public HrInsurancePayment getSelectedPayment(Integer idfill) {
        Query query = em.createNamedQuery("HrInsurancePayment.findById");
        query.setParameter("id", idfill);
        try {
            HrInsurancePayment SelectedPayment = (HrInsurancePayment) query.getResultList().get(0);
            return SelectedPayment;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    public List loadpaymentrequest(int status) {
        String queryStatus = " WHERE STATUS='" + status + "' ";
        if (status == 2) {
            queryStatus = " WHERE(STATUS='0' OR STATUS='1')";
        }
        Query query = em.createNativeQuery("SELECT * FROM HR_INSURANCE_PAYMENT "
                + queryStatus
                + "ORDER BY RECIVED_DATE ", HrInsurancePayment.class);
        try {
            return (List<HrInsurancePayment>) query.getResultList();
        } catch (Exception ex) {
            return null;
        }
    }
    
    public List<HrInsuranceDiagnosisResult> empnamed(HrInsuranceDiagnosisResult hrInsuranceDiagnosisResult, String daily, Integer status2) {
        Query query = em.createNamedQuery("HrInsuranceDiagnosisResult.findbydaily", HrInsuranceDiagnosisResult.class);
        query.setParameter("dname", daily);
        query.setParameter("status", status2);
        try {
            
            List<HrInsuranceDiagnosisResult> compList = new ArrayList(query.getResultList());
            return compList;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    public List<HrInsuranceDiagnosisResult> empnamedd(HrInsuranceDiagnosisResult hrInsuranceDiagnosisResult, String employe, Integer status1) {
        Query query = em.createNamedQuery("HrInsuranceDiagnosisResult.findbyfullname", HrInsuranceDiagnosisResult.class);
        query.setParameter("fname", employe);
        query.setParameter("status", status1);
        try {
            
            List<HrInsuranceDiagnosisResult> compList = new ArrayList(query.getResultList());
            return compList;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    public List<HrInsuranceDiagnosisResult> findbynamepermanentupdate(HrInsuranceDiagnosisResult hrInsuranceDiagnosisResult, String skill, Integer status2) {
        Query query = em.createNamedQuery("HrInsuranceDiagnosisResult.findbystatus2", HrInsuranceDiagnosisResult.class);
        query.setParameter("fname", skill);
        query.setParameter("status2", status2);
        try {
            
            List<HrInsuranceDiagnosisResult> compList = new ArrayList(query.getResultList());
            return compList;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    public List<HrInsuranceDiagnosisResult> findbynamedaily1(HrInsuranceDiagnosisResult hrInsuranceDiagnosisResult, String daily, Integer status2) {
        Query query = em.createNamedQuery("HrInsurancePayment.findbystatus2", HrInsuranceDiagnosisResult.class);
        query.setParameter("fname", daily);
        query.setParameter("status3", status2);
        try {
            
            List<HrInsuranceDiagnosisResult> compList = new ArrayList(query.getResultList());
            return compList;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    public List<HrInsuranceDiagnosisResult> empnamedd(HrInsuranceDiagnosisResult hrInsuranceDiagnosisResult, String employe, String daily, Integer status1) {
        Query query = em.createNamedQuery("HrInsuranceDiagnosisResult.findbyfullname", HrInsuranceDiagnosisResult.class);
        query.setParameter("fname", employe);
        query.setParameter("status", status1);
        query.setParameter("dname", daily);
        try {
            
            List<HrInsuranceDiagnosisResult> compList = new ArrayList(query.getResultList());
            return compList;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    public List<HrInsurancePayment> loadFiltereddata(int status) {
        String queryStatus = " WHERE STATUS='" + status + "' ";
        if (status == 2) {
            queryStatus = " WHERE(STATUS='0' OR STATUS='3' OR STATUS='4' OR STATUS='2')";
        } else if (status == 4) {
            queryStatus = " WHERE(STATUS='4' OR STATUS='2')";
        } else if (status == 3) {
            queryStatus = " WHERE(STATUS='1' OR STATUS='3')";
        }
        Query query = em.createNativeQuery("SELECT * FROM HR_INSURANCE_PAYMENT "
                + queryStatus
                + "ORDER BY RECIVED_DATE ", HrInsurancePayment.class);
        try {
            return (List<HrInsurancePayment>) query.getResultList();
        } catch (Exception ex) {
            return null;
        }
    }
    
    public List<HrInsurancePayment> findrequestlist() {
        Query query = em.createNamedQuery("HrInsurancePayment.findByStatus");
        query.setParameter("status", 0);
        try {
            return (List<HrInsurancePayment>) query.getResultList();
        } catch (Exception ex) {
            return null;
        }
    }
//</editor-fold>
}
