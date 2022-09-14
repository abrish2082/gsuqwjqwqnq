/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.commonApplications.mapper;

import et.gov.eep.commonApplications.entity.WfHrProcessed;
import et.gov.eep.hrms.entity.leave.HrLeaveRequest;
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
public class WfHrProcessedFacade extends AbstractFacade<WfHrProcessed> {
    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public WfHrProcessedFacade() {
        super(WfHrProcessed.class);
    }
     public List<WfHrProcessed> MassSalWFList(Integer id) {
        System.out.println("mass sall Id=="+ id);
        Query query = em.createNamedQuery("WfHrProcessed.findByByMassSalId", WfHrProcessed.class);
        query.setParameter("salIncId", id);
        try {
             List<WfHrProcessed> wfList = (List<WfHrProcessed>) query.getResultList();
            System.out.println("this is from mapper-----------------------" + wfList);
            return wfList;

        } catch (Exception ex) {

            return null;
        }
    }
      public WfHrProcessed findByLeaveId(HrLeaveRequest leaveRequest) {

        try {
            Query query = em.createNamedQuery("WfHrProcessed.findByLeaveId");
            query.setParameter("leaveId", leaveRequest);
            return (WfHrProcessed) query.getSingleResult();
        } catch (Exception e) {
          //  e.printStackTrace();
            return null;
        }
    }
    
}
