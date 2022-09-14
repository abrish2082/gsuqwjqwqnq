/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.mapper.insure;

import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.hrms.entity.insurance.HrInsuranceDiagnosisResult;
import et.gov.eep.hrms.entity.insurance.HrInsuranceInjuredEmployee;
import et.gov.eep.hrms.entity.insurance.HrLuInsuranceBranches;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author insa
 */
@Stateless
public class HrInsuranceDiagnosisResultFacade extends AbstractFacade<HrInsuranceDiagnosisResult> {
    
    
    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public HrInsuranceDiagnosisResultFacade() {
        super(HrInsuranceDiagnosisResult.class);
    }

    //<editor-fold defaultstate="collapsed" desc="Bussiness IMmplementatin">
    public HrInsuranceInjuredEmployee getSelectedRequest(int request) {
        Query query = em.createNamedQuery("HrInsuranceDiagnosisResult.findById");
        query.setParameter("id", request);
        try {
            HrInsuranceInjuredEmployee selectrequest = (HrInsuranceInjuredEmployee) query.getResultList().get(0);
            return selectrequest;
        } catch (Exception ex) {
            return null;
        }
    }
    
    public List<HrInsuranceDiagnosisResult> findByInsuranceId(HrInsuranceDiagnosisResult hrLuInsurances) {
        try {
            Query query = em.createNamedQuery("HrInsuranceDiagnosisResult.findByInsuranceName", HrLuInsuranceBranches.class);
            query.setParameter("insuranceName", hrLuInsurances.getHrInsuranceInjuredEmployee().getInsuranceId().getInsuranceId().getInsuranceName());
            return (List<HrInsuranceDiagnosisResult>) query.getResultList();
        } catch (Exception e) {
            return null;
        }
    }
    
    public HrInsuranceInjuredEmployee findById(HrInsuranceInjuredEmployee hrInsuranceInjuredEmployee) {
        Query query = em.createNamedQuery("HrInsuranceInjuredEmployee.findById");
        query.setParameter("id", hrInsuranceInjuredEmployee.getId());
        try {
            return (HrInsuranceInjuredEmployee) query.getResultList().get(0);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    public List<HrInsuranceInjuredEmployee> findInjuredEmp(HrInsuranceInjuredEmployee hrInsuranceInjuredEmployee, String statusinjury) {
        Query query = em.createNamedQuery("HrInsuranceDiagnosisResult.findBystatus",HrInsuranceInjuredEmployee.class);
        query.setParameter("status", statusinjury);
        try {
            List<HrInsuranceInjuredEmployee> compList = new ArrayList(query.getResultList());
            return compList;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    public HrInsuranceDiagnosisResult getSelectedPlanRequest(Integer request) {
        Query query = em.createNamedQuery("HrInsuranceDiagnosisResult.findById");
        query.setParameter("id", request);
        try {
            HrInsuranceDiagnosisResult selectrequest = (HrInsuranceDiagnosisResult) query.getResultList().get(0);
            return selectrequest;
        } catch (Exception ex) {
            return null;
        }
    }
    
//</editor-fold>
    }
