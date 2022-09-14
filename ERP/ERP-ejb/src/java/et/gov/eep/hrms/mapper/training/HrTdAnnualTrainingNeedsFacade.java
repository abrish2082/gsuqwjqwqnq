/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.mapper.training;

import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.hrms.entity.training.HrTdAnnualNeedRequests;
import et.gov.eep.hrms.entity.training.HrTdAnnualTraParticipants;
import et.gov.eep.hrms.entity.training.HrTdAnnualTrainingNeeds;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Benin
 */
@Stateless
public class HrTdAnnualTrainingNeedsFacade extends AbstractFacade<HrTdAnnualTrainingNeeds> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    public HrTdAnnualTrainingNeedsFacade() {
        super(HrTdAnnualTrainingNeeds.class);
    }

    public ArrayList<HrTdAnnualTrainingNeeds> SrchAnnTraNeedToBeApproved(HrTdAnnualTrainingNeeds hrTdAnnualTrainingNeeds) {
        Query queryReq = em.createNamedQuery("HrTdAnnualTrainingNeeds.findByTrainingRequestStatus", HrTdAnnualTrainingNeeds.class);
        try {
            queryReq.setParameter("annualNeedRequestId", hrTdAnnualTrainingNeeds.getAnnualNeedRequestId().getId());
            ArrayList<HrTdAnnualTrainingNeeds> trainList = new ArrayList(queryReq.getResultList());
            return trainList;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
        }
