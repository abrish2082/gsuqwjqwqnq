/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.prms.mapper;

import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.prms.entity.PrmsBid;
import et.gov.eep.prms.entity.PrmsBidSubmission;
import java.math.BigDecimal;
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
public class PrmsBidSubmissionFacade extends AbstractFacade<PrmsBidSubmission> {

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
    public PrmsBidSubmissionFacade() {
        super(PrmsBidSubmission.class);
    }

    /**
     *
     * @return
     */
    /**
     *
     * @param toString
     * @return
     */
    /**
     *
     * @param prmsBidSubmission
     * @return
     */
    //<editor-fold defaultstate="collapsed" desc="Named Queries">
    public List<PrmsBidSubmission> searchBidSubmission(PrmsBidSubmission prmsBidSubmission) {
        if (prmsBidSubmission.getColumnValue() != null && prmsBidSubmission.getColumnName() != null
                && !prmsBidSubmission.getColumnValue().equals("") && !prmsBidSubmission.getColumnName().equals("")) {
            Query query = em.createNativeQuery("SELECT * FROM PRMS_BID_SUBMISSION\n"
                    + "where " + prmsBidSubmission.getColumnName().toLowerCase() + " = '" + prmsBidSubmission.getColumnValue() + "' "
                    + "and " + prmsBidSubmission.getPreparedBy() + "='" + prmsBidSubmission.getPreparedBy() + "' ", PrmsBidSubmission.class);
            try {
                List<PrmsBidSubmission> prmsBidSubmissionList = new ArrayList<>();
                if (query.getResultList().size() > 0) {
                    prmsBidSubmissionList = query.getResultList();
                }
                return prmsBidSubmissionList;
            } catch (Exception ex) {
                ex.getMessage();
                return null;
            }
        } else {
            Query query = em.createNamedQuery("PrmsBidSubmission.findByPreparedBy");
            query.setParameter("preparedBy", prmsBidSubmission.getPreparedBy());
            return query.getResultList();
        }
    }

    public PrmsBidSubmission LastCheckListNo() {

        Query query = em.createNamedQuery("PrmsBidSubmission.findByMaxBidOfferNum");
        PrmsBidSubmission result = null;

        try {
            if (query.getResultList().size() > 0) {
                result = (PrmsBidSubmission) query.getResultList().get(0);
            } else {
                return result;
            }

            return result;
        } catch (Exception ex) {
            return null;
        }
    }

    public PrmsBidSubmission getSelectedRequest(BigDecimal id) {
        Query query = em.createNamedQuery("PrmsBidSubmission.findByBidSubId");
        query.setParameter("bidSubId", id);

        try {
            PrmsBidSubmission selectrequest = (PrmsBidSubmission) query.getResultList().get(0);
            return selectrequest;
        } catch (Exception ex) {
            return null;
        }
    }

    public PrmsBidSubmission selectBidSubmissionByBidNo(PrmsBid bid) {
        Query query = em.createNamedQuery("PrmsBidSubmission.findByBidId");
        query.setParameter("bidId", bid.getId());
        try {
            PrmsBidSubmission selectrequest = (PrmsBidSubmission) query.getResultList().get(0);
            return selectrequest;
        } catch (Exception ex) {
            return null;
        }
    }

    public ArrayList<PrmsBidSubmission> searchBidSubmission() {

        Query query = em.createNamedQuery("PrmsBidSubmission.findAllByStatus");

        try {
            ArrayList<PrmsBidSubmission> bissubmision = new ArrayList(query.getResultList());

            return bissubmision;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    //</editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Native Queries">
    public ArrayList<PrmsBidSubmission> BidNoListForCheckList() {
        Query query = em.createNativeQuery("SELECT PRMS_BID_SUBMISSION.*,PRMS_BID.* \n"
                + "                FROM PRMS_BID_SUBMISSION\n"
                + "                INNER JOIN PRMS_BID\n"
                + "                ON PRMS_BID.ID = PRMS_BID_SUBMISSION.BID_ID\n"
                + "                WHERE PRMS_BID.ID = PRMS_BID_SUBMISSION.BID_ID", PrmsBid.class);
        try {
            ArrayList<PrmsBidSubmission> bidList = new ArrayList<>();
            bidList = new ArrayList<>(query.getResultList());
            return bidList;

        } catch (Exception ex) {
            return null;
        }
    }

    public List<PrmsBidSubmission> getBidNoList() {
        List<PrmsBidSubmission> suppLierList = null;
        Query query = em.createNativeQuery("SELECT PRMS_BID.REF_NO,\n"
                + "  PRMS_BID.ID,\n"
                + "  PRMS_BID_OFFER_SUBMISSION.BID_OFFER_ID,\n"
                + "  PRMS_BID_OFFER_SUBMISSION.BID_ID\n"
                + "FROM PRMS_BID_OFFER_SUBMISSION\n"
                + "INNER JOIN PRMS_BID\n"
                + "ON PRMS_BID.ID = PRMS_BID_OFFER_SUBMISSION.BID_ID", PrmsBidSubmission.class
        );
        try {
            suppLierList = (List<PrmsBidSubmission>) query.getResultList();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return suppLierList;
    }

    // </editor-fold>
    public List<PrmsBidSubmission> getParamNameList() {
        Query query = em.createNativeQuery("SELECT column_name FROM user_tab_columns \n"
                + "where table_name = UPPER('PRMS_BID_SUBMISSION') \n"
                + "and column_name not in ('BID_SUB_ID','CURRENT_STATUS','STATUS','BIDDER_ID','BID_ID')");
        try {
            List<PrmsBidSubmission> columnNameList = new ArrayList<>();
            columnNameList = query.getResultList();
            return columnNameList;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }
}
