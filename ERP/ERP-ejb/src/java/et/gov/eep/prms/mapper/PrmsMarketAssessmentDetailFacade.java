/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.prms.mapper;

import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.fcms.entity.budget.FmsOperatingBudgetDetail;
import et.gov.eep.prms.entity.PrmsMarketAssessmentDetail;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author user
 */
@Stateless
public class PrmsMarketAssessmentDetailFacade extends AbstractFacade<PrmsMarketAssessmentDetail> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    /**
     *
     * @return
     */
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    /**
     *
     */
    public PrmsMarketAssessmentDetailFacade() {
        super(PrmsMarketAssessmentDetail.class);
    }

    // <editor-fold defaultstate="collapsed" desc="Named(Static) Queries">
    public List<PrmsMarketAssessmentDetail> getItemNa() {
        Query query = em.createNamedQuery("PrmsMarketAssessmentDetail.findAll");
        try {
            List<PrmsMarketAssessmentDetail> mmsServiceRegLst = new ArrayList<>(query.getResultList());
            return mmsServiceRegLst;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<PrmsMarketAssessmentDetail> findByMarketAssNoLike(String string) {

        Query query = em.createNamedQuery("PrmsMarketAssessmentDetail.findByItemNam");
        query.setParameter("item", string + '%');

        try {
            List<PrmsMarketAssessmentDetail> ItemNam = null;
            ItemNam = (List<PrmsMarketAssessmentDetail>) (query.getResultList());

            return ItemNam;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<PrmsMarketAssessmentDetail> getByMarketAssNoLike() {

        Query query = em.createNamedQuery("PrmsMarketAssessmentDetail.findByItemNam");
        try {
            List<PrmsMarketAssessmentDetail> supplierName = new ArrayList<>(query.getResultList());
            return supplierName;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public PrmsMarketAssessmentDetail findByMarketAssNo(PrmsMarketAssessmentDetail toString) {

        Query query = em.createNamedQuery("PrmsMarketAssessmentDetail.findByMarketAssNo", PrmsMarketAssessmentDetail.class);
        query.setParameter("assNo", toString.getMarketAssmntId().getMarketAssNo());
        try {
            PrmsMarketAssessmentDetail supplierName = null;
            if (query.getResultList().size() > 0) {
                supplierName = (PrmsMarketAssessmentDetail) query.getResultList().get(0);
            }

            return supplierName;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public PrmsMarketAssessmentDetail getByMarketAssNo(String toString) {

        Query query = em.createNamedQuery("PrmsMarketAssessmentDetail.findByMarketAssNo", PrmsMarketAssessmentDetail.class);
        query.setParameter("assNo", toString);
        try {
            PrmsMarketAssessmentDetail supplierName = null;
            if (query.getResultList().size() > 0) {
                supplierName = (PrmsMarketAssessmentDetail) query.getResultList().get(0);
            }

            return supplierName;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Native Queries">
    //get Lists of Item Name   
    public List<PrmsMarketAssessmentDetail> getItemNameLists() {
        Query query = em.createNativeQuery("select md.*"
                + " from prms_market_assessment_detail md\n"
                + "inner join\n"
                + "(select item_id, max(id) as max_IdMAD\n"
                + "from prms_market_assessment_detail\n"
                + "where item_id is not null\n"
                + "group by item_id)md2\n"
                + "on md.id=md2.max_IdMAD", PrmsMarketAssessmentDetail.class);

        try {
            List<PrmsMarketAssessmentDetail> itemLst = null;
            itemLst = (List<PrmsMarketAssessmentDetail>) query.getResultList();
            return itemLst;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    public List<PrmsMarketAssessmentDetail> getFilteredItemByGLId(FmsOperatingBudgetDetail fmsOperatingBudgetDetail) {
        Query q = em.createNativeQuery("select mad.*\n"
                + "  from prms_market_assessment_detail mad\n"
                + "  inner join\n"
                + "  (select  item_id,max(id) as maxIs\n"
                + "  from prms_market_assessment_detail \n"
                + "  where item_id is not null\n"
                + "  group by item_id)mad2\n"
                + "  on mad.id=mad2.maxIs\n"
                + "  inner join mms_item_registration ir\n"
                + "  inner join fms_general_ledger gl\n"
                + "  on gl.general_ledger_id=ir.gl_id\n"
                + "  on ir.material_id=mad.item_id\n"
                + "  where gl.general_ledger_code='" + fmsOperatingBudgetDetail.getGeneralLedger().getGeneralLedgerCode() + "'", PrmsMarketAssessmentDetail.class);

        try {

            ArrayList<PrmsMarketAssessmentDetail> items = new ArrayList<>(q.getResultList());
            return items;
        } catch (Exception c) {
            c.printStackTrace();
            return null;
        }
    }
    // </editor-fold>

}
