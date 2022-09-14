/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.mapper.budget;
//<editor-fold defaultstate="collapsed" desc="import">
import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.fcms.entity.budget.FmsObTransferDetail;
import et.gov.eep.fcms.entity.budget.FmsOperatingBudgetDetail;
import et.gov.eep.fcms.entity.budget.FmsOperatingBudgetTasks;
import java.util.ArrayList;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
    //</editor-fold>


@Stateless
public class FmsObTransferDetailFacade extends AbstractFacade<FmsObTransferDetail> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;
//<editor-fold defaultstate="collapsed" desc="other methods ">
     @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public FmsObTransferDetailFacade() {
        super(FmsObTransferDetail.class);
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="NamedQuery">
       public ArrayList<FmsObTransferDetail> fetchOBFromByOBDTasktlID(FmsOperatingBudgetTasks fmsOperatingBudgetTasks) {
        try {
            Query query = em.createNamedQuery("FmsObTransferDetail.findByFromObId");
            query.setParameter("fromObId", fmsOperatingBudgetTasks);
            ArrayList<FmsObTransferDetail> capitalBudgetList = new ArrayList(query.getResultList());
            return capitalBudgetList;
        } catch (NullPointerException nullex) {
            throw nullex;
        }
    }
    
    public ArrayList<FmsObTransferDetail> findOBTransReqList() {
        try {
            Query query = em.createNamedQuery("FmsObTransferDetail.findReqList");            
            ArrayList<FmsObTransferDetail> reqList = new ArrayList(query.getResultList());
            return reqList;
        } catch (NullPointerException nullex) {
            throw nullex;
        }
    }
    
    public FmsObTransferDetail findByTrasferId(FmsObTransferDetail fmsObTransferDetail) {
        try {
            Query query = em.createNamedQuery("FmsObTransferDetail.findByTransferDtlId");    
            query.setParameter("transferDtlId", fmsObTransferDetail.getTransferDtlId());
            FmsObTransferDetail obTrans = (FmsObTransferDetail) query.getResultList().get(0);
            return obTrans;
        } catch (NullPointerException nullex) {
            throw nullex;
        }
    }
    //</editor-fold>
    
    
   

 
}
