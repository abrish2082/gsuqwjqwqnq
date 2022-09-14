/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.mapper.workFlow;

import et.gov.eep.commonApplications.entity.WfFcmsProcessed;
import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.fcms.entity.perDiem.FmsFieldAllowance;
import java.util.List;
//import et.gov.eep.fcms.entity.workFlow.WfFcmsProcessed;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author memube
 */
@Stateless
public class WfFcmsProcessedFacade extends AbstractFacade<WfFcmsProcessed> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public WfFcmsProcessedFacade() {
        super(WfFcmsProcessed.class);
    }

    public List<WfFcmsProcessed> findWfListByUserIdAndFaLocalId(WfFcmsProcessed wfFcmsProcessed, FmsFieldAllowance fmsFieldAllowanceEnty) {
        System.out.println("-------------fmsFieldAllowanceEnty.getId()-----------" + fmsFieldAllowanceEnty.getId());
        System.out.println("-------------wfFcmsProcessed.getProcessedBy()-------" + wfFcmsProcessed.getProcessedBy());
        try {
            Query query1 = em.createNativeQuery("SELECT * from WF_FCMS_PROCESSED wf WHERE  "
                    + " wf.PERDIEM_REQUEST_LOCAL_ID = '" + fmsFieldAllowanceEnty.getId() + "' "
                    + " AND wf.PROCESSED_BY = '" + wfFcmsProcessed.getProcessedBy() + "'", WfFcmsProcessed.class);
            List<WfFcmsProcessed> wfList = query1.getResultList();
            System.out.println("-------list Wf FCD---------" + wfList);
            System.out.println("-------list Wf size--------" + wfList.size());
            return wfList;
        } catch (Exception ex) {
            return null;
        }
    }

}
