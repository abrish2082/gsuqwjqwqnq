/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.prms.mapper;

import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.prms.entity.PrmsBid;
import et.gov.eep.prms.entity.PrmsBidAmend;
import et.gov.eep.prms.entity.PrmsBidDetail;
import et.gov.eep.prms.entity.PrmsBidDetailAmend;
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
public class PrmsBidAmendFacade extends AbstractFacade<PrmsBidAmend> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PrmsBidAmendFacade() {
        super(PrmsBidAmend.class);
    }

    public List<PrmsBidDetail> getBidNo(String bidNo) {

        List<PrmsBidDetail> bidNoLst = null;
        Query query = em.createNamedQuery("PrmsBidDetail.findByBidNo", PrmsBidDetail.class);
        query.setParameter("bidNo", bidNo);
        try {
            bidNoLst = (List<PrmsBidDetail>) query.getResultList();

            return bidNoLst;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bidNoLst;
    }

    public List<PrmsBid> getBidNoLst() {
        List<PrmsBid> bidNoLst = null;
        try {
            Query query = em.createNamedQuery("PrmsBid.findAll", PrmsBid.class);
            bidNoLst = (List<PrmsBid>) query.getResultList();
            return bidNoLst;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bidNoLst;
    }

    public PrmsBid getBidNums(String string) {

        PrmsBid bidNoLst = null;
        Query query = em.createNamedQuery("PrmsBid.findByRefNos", PrmsBid.class);
        query.setParameter("refNo", string);
        try {
            bidNoLst = (PrmsBid) query.getResultList().get(0);

            return bidNoLst;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bidNoLst;
    }

    public List<PrmsBidDetailAmend> getBidAmendNo() {
        Query query = em.createNamedQuery("PrmsBidDetailAmend.findBidAmendNo");
        List<PrmsBidDetailAmend> directPurcObj = null;
        try {
            directPurcObj = (List<PrmsBidDetailAmend>) query.getResultList();
            return directPurcObj;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<PrmsBidDetailAmend> getBidNumb(String bidNum) {

        Query query = em.createNamedQuery("PrmsBidDetailAmend.findByBidAmendId");
        List<PrmsBidDetailAmend> directPurcObj = null;
        query.setParameter("amendId", bidNum);

        try {
            directPurcObj = (List<PrmsBidDetailAmend>) query.getResultList();

            return directPurcObj;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<PrmsBidAmend> getBidamendNo(String bidNum) {

        Query query = em.createNamedQuery("PrmsBidAmend.findById");
        List<PrmsBidAmend> directPurcObj = null;
        query.setParameter("id", bidNum);

        try {
            directPurcObj = (List<PrmsBidAmend>) query.getResultList();

            return directPurcObj;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public PrmsBidAmend getBidNumber(String bidNumer) {
        System.out.println("=========from db ====" + bidNumer);
        Query query = em.createNamedQuery("PrmsBidAmend.findByAmend");
        PrmsBidAmend directPurcObj = null;
        query.setParameter("amend", bidNumer);

        try {
            directPurcObj = (PrmsBidAmend) query.getResultList().get(0);
            System.out.println("=========from db t ====" + directPurcObj);
            return directPurcObj;

        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<PrmsBidAmend> getBidAmend(String toString) {

        Query query = em.createNamedQuery("PrmsBidAmend.findByRefNo");
        List<PrmsBidAmend> directPurcObj = null;
        query.setParameter("refNo", toString);

        try {
            directPurcObj = (List<PrmsBidAmend>) query.getResultList();

            return directPurcObj;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<PrmsBidAmend> getBidNoAndAmendNo(PrmsBidAmend prmsBidAmend) {

         List<PrmsBidAmend> prmsBidAmendLst = new ArrayList();
        if (prmsBidAmend.getColumnName() != null && !prmsBidAmend.getColumnName().equals("")
                && prmsBidAmend.getColumnValue() != null && !prmsBidAmend.getColumnValue().equals("")) {

            Query query = em.createNativeQuery("SELECT * FROM PRMS_BID_AMEND\n"
                    + "where " + prmsBidAmend.getColumnName().toLowerCase() + " = '" + prmsBidAmend.getColumnValue() + "'"
                    + "and " + prmsBidAmend.getPreparedBy() + "='" + prmsBidAmend.getPreparedBy() + "'", PrmsBidAmend.class);
            try {
                if (query.getResultList().size() > 0) {
                    prmsBidAmendLst = query.getResultList();
                }
                return prmsBidAmendLst;
            } catch (NumberFormatException nfe) {
                nfe.getMessage();
                return null;
            }
        } else {
            Query query = em.createNamedQuery("PrmsBidAmend.findByPreparedBy");
            query.setParameter("preparedBy", prmsBidAmend.getPreparedBy());
            prmsBidAmendLst = query.getResultList();
            return prmsBidAmendLst;
        }
    }

    public PrmsBidAmend getBidAmnedId(String id) {

        Query query = em.createNamedQuery("PrmsBidAmend.findById");
        PrmsBidAmend amnedId = null;
        query.setParameter("id", id);

        try {
            amnedId = (PrmsBidAmend) query.getResultList().get(0);

            return amnedId;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<PrmsBidAmend> getBidAmendmentOnList() {
        Query query = em.createNamedQuery("PrmsBidAmend.findByReq", PrmsBidAmend.class);
        ArrayList<PrmsBidAmend> marketAssessmentList = new ArrayList(query.getResultList());
        return marketAssessmentList;
    }

//    public List<PrmsBidAmend> getAmendmentSeq(String prefix, String eYear) {
//        Query query = em.createNamedQuery("PrmsBidAmend.findBySeqAmend");
//        query.setParameter("amendNo", prefix + "-" + '%' + "/" + eYear);
//        List<PrmsBidAmend> prmsBidAmendList = new ArrayList<>();
//        if (query.getResultList().size() > 0) {
//            prmsBidAmendList = query.getResultList();
//        }
//        return prmsBidAmendList;
//    }
    public List<PrmsBidAmend> getNextAmendRegNo(String prefix, String eYear) {
        Query query = em.createNamedQuery("PrmsBidAmend.findBySeqAmend");
        query.setParameter("amendNo", prefix + "-" + '%' + "/" + eYear);
        List<PrmsBidAmend> amendRegistrationsLists = new ArrayList<>();
        if (query.getResultList().size() > 0) {
            amendRegistrationsLists = query.getResultList();
        }
        return amendRegistrationsLists;
    }

    public List<PrmsBidAmend> getNextClarifNo(String prefix, String eYear) {
        Query query = em.createNamedQuery("PrmsBidAmend.findBySeqClar");
        query.setParameter("clarificationNo", prefix + "-" + '%' + "/" + eYear);
        List<PrmsBidAmend> amendRegistrationsLists = new ArrayList<>();
        if (query.getResultList().size() > 0) {
            amendRegistrationsLists = query.getResultList();
        }
        return amendRegistrationsLists;
    }

    public List<PrmsBidAmend> getBidAmendNoList(String amendnu) {
        List<PrmsBidAmend> prmsBidAmendList = null;
        Query query = em.createNativeQuery("SELECT\n"
                + "    eep_erp.prms_bid_amend.amend_no,\n"
                + "    eep_erp.prms_bid_amend.id,\n"
                + "    eep_erp.prms_bid_amend.ref_no\n"
                + "FROM\n"
                + "    eep_erp.prms_bid_amend\n"
                + "    where eep_erp.prms_bid_amend.ref_no='"+amendnu+"'", PrmsBidAmend.class);
        try {

            prmsBidAmendList = (List<PrmsBidAmend>) query.getResultList();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return prmsBidAmendList;
    }

    public List<PrmsBidAmend> getBidAmended(String toString) {
        System.out.print("second" + toString);
        Query query = em.createNamedQuery("PrmsBidAmend.findByAmend");
        List<PrmsBidAmend> amendedNo = null;
        query.setParameter("amend", toString);

        try {
            amendedNo = (List<PrmsBidAmend>) query.getResultList();
            System.out.print("third" + amendedNo.size());
            return amendedNo;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<PrmsBidAmend> getParamNameList() {
        Query query = em.createNativeQuery("SELECT column_name FROM user_tab_columns \n"
                + "where table_name = UPPER('PRMS_BID_AMEND') \n"
                + "and column_name not in ('ID','DOCUMENT_ID', 'DESCRIPTION', 'STATUS', 'FILE_UPLOAD_REFNO', 'AMENDMENT_REASON', 'CLARIFICATION_REASON', 'PROJECT_ID', 'CURRENCY_ID', 'BID_ID BID_CRITERIA_ID' )");
        try {
            List<PrmsBidAmend> columnNameList = new ArrayList<>();
            columnNameList = query.getResultList();
            return columnNameList;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }
}
