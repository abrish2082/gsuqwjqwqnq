/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.prms.mapper;

import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.mms.entity.MmsItemRegistration;
import et.gov.eep.prms.entity.PrmsBid;
import et.gov.eep.prms.entity.PrmsBidDetail;
import et.gov.eep.prms.entity.PrmsFinancialEvalResult;
import et.gov.eep.prms.entity.PrmsFinancialEvlResultyDtl;
import et.gov.eep.prms.entity.PrmsPostQualification;
import et.gov.eep.prms.entity.PrmsSupplyProfile;
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
public class PrmsPostQualificationFacade extends AbstractFacade<PrmsPostQualification> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PrmsPostQualificationFacade() {
        super(PrmsPostQualification.class);
    }

    public List<PrmsPostQualification> getPQNofind(PrmsPostQualification prmsPostQualification) {
        List<PrmsPostQualification> postQualificationLst = new ArrayList();
        if (prmsPostQualification.getColumnName() != null && !prmsPostQualification.getColumnName().equals("")
                && prmsPostQualification.getColumnValue() != null && !prmsPostQualification.getColumnValue().equals("")) {

            Query query = em.createNativeQuery("SELECT * FROM PRMS_POST_QUALIFICATION\n"
                    + "where " + prmsPostQualification.getColumnName().toLowerCase() + " = '" + prmsPostQualification.getColumnValue() + "'"
                    + "and " + prmsPostQualification.getPreparedBy() + "='" + prmsPostQualification.getPreparedBy() + "'", PrmsPostQualification.class);
            try {
                if (query.getResultList().size() > 0) {
                    postQualificationLst = query.getResultList();
                }
                return postQualificationLst;
            } catch (NumberFormatException nfe) {
                nfe.getMessage();
                return null;
            }
        } else {
            Query query = em.createNamedQuery("PrmsPostQualification.findByPreparedBy");
            query.setParameter("preparedBy", prmsPostQualification.getPreparedBy());
            postQualificationLst = query.getResultList();
            return postQualificationLst;
        }
    }

    public PrmsPostQualification getPQNos() {

        Query query = em.createNamedQuery("PrmsPostQualification.findByMaxPostId");
        PrmsPostQualification result = null;
        try {
            if (query.getResultList().size() > 0) {
                result = (PrmsPostQualification) query.getResultList().get(0);
            }

            return result;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

//    public PrmsPostQualification getReqPQN(Integer postId) {
//        Query query = em.createNamedQuery("PrmsPostQualification.findById");
//        query.setParameter("postId", postId);
//        try {
//            PrmsPostQualification idlst = (PrmsPostQualification) query.getResultList().get(0);
//            return idlst;
//        } catch (Exception ex) {
//            ex.printStackTrace();
//            return null;
//        }
//    }
    public List<PrmsFinancialEvalResult> getFinancialNoForPostQualifaction() {
        System.out.println("so facade");
        List<PrmsFinancialEvalResult> itemBaseList = null;
        Query query = em.createNativeQuery("SELECT f.* FROM PRMS_FINANCIAL_EVAL_RESULT f \n"
                + "               INNER JOIN PRMS_BID b \n"
                + "                ON f.Bid_id = b.id \n"
                + "                INNER JOIN PRMS_FINANCIAL_EVL_RESULTY_DTL fd\n"
                + "                on f.ID = fd.FNANCIAL_RESULT_ID\n"
                + "               where b.post_qualification = 'Applicable' and fd.RESULT_RANK=1 and f.STATUS = 3", PrmsFinancialEvalResult.class);
        try {

            itemBaseList = (List<PrmsFinancialEvalResult>) query.getResultList();
            System.out.println("List of Result No==" + itemBaseList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return itemBaseList;

    }

    public List<PrmsSupplyProfile> getItemNameLists(PrmsBid prmsBid, PrmsSupplyProfile prmsSupplyProfile) {

        em.getEntityManagerFactory().getCache().evictAll();
        Query qu = em.createNativeQuery("Select fd.* from PRMS_FINANCIAL_EVL_RESULTY_DTL fd\n"
                + "inner join PRMS_FINANCIAL_EVAL_RESULT fe\n"
                + "on fe.ID = fd.FNANCIAL_RESULT_ID where fe.BID_ID =" + prmsBid.getId()
                + " and fd.SUPPLIER_ID=" + prmsSupplyProfile.getId() + " and fd.RESULT_RANK=1");
        try {

            //   System.out.println("2");
            ArrayList<PrmsSupplyProfile> supLists = new ArrayList<>(qu.getResultList());
            return supLists;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    public List<PrmsFinancialEvlResultyDtl> getBidNameLists(PrmsFinancialEvlResultyDtl prmsFinancialEvlResultyDtl, int nominatedRank) {
        System.out.println("We R facade--");
        Query qu = em.createNamedQuery("PrmsFinancialEvlResultyDtl.findByResultId");
        qu.setParameter("fnancialResultId", prmsFinancialEvlResultyDtl.getFnancialResultId());
        qu.setParameter("rank", nominatedRank);
        System.out.println("fia id====" + prmsFinancialEvlResultyDtl.getFnancialResultId());
        try {
            List<PrmsFinancialEvlResultyDtl> bidNames = null;
            if (qu.getResultList().size() > 0) {
                bidNames = new ArrayList<>(qu.getResultList());
            }
            return bidNames;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<PrmsBidDetail> getLotNames(PrmsBid prmsBid) {
        em.getEntityManagerFactory().getCache().evictAll();
        Query qu = em.createNativeQuery("", PrmsBid.class);
        try {

            //   System.out.println("2");
            ArrayList<PrmsBidDetail> lotsName = new ArrayList<>(qu.getResultList());
            return lotsName;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<MmsItemRegistration> getItemCodeList(PrmsFinancialEvlResultyDtl prmsFinancialEvlResultyDtl) {
        System.out.println("=====facade 1====" + prmsFinancialEvlResultyDtl.getId());
        Query qu = em.createNativeQuery("SELECT PRMS_FINANCIAL_EVL_RESULTY_DTL.ID,\n"
                + "  MMS_ITEM_REGISTRATION.MAT_NAME,\n"
                + "  MMS_ITEM_REGISTRATION.MATERIAL_ID\n"
                + "FROM MMS_ITEM_REGISTRATION\n"
                + "INNER JOIN PRMS_FINANCIAL_EVL_RESULTY_DTL\n"
                + "ON MMS_ITEM_REGISTRATION.MATERIAL_ID    = PRMS_FINANCIAL_EVL_RESULTY_DTL.ITEM_ID\n"
                + "WHERE PRMS_FINANCIAL_EVL_RESULTY_DTL.ID='" + prmsFinancialEvlResultyDtl.getId() + "'", PrmsFinancialEvlResultyDtl.class);

        try {
            List<MmsItemRegistration> itemCodeList = new ArrayList<>(qu.getResultList());
            return itemCodeList;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<PrmsFinancialEvlResultyDtl> getLotNumberList(PrmsFinancialEvlResultyDtl prmsFinancialEvlResultyDtl) {
        System.out.println("=====facade 2====");
        Query que = em.createNamedQuery("PrmsFinancialEvlResultyDtl.findById");
        que.setParameter("id", prmsFinancialEvlResultyDtl.getId());
        try {
            List<PrmsFinancialEvlResultyDtl> lotNoList = null;
            if (que.getResultList().size() > 0) {
                lotNoList = new ArrayList<>(que.getResultList());
                System.out.println("Lot lists=====" + lotNoList);
            }
            return lotNoList;
        } catch (Exception ex) {
            return null;
        }
    }

    public List<PrmsFinancialEvlResultyDtl> getListofNextSupplier(PrmsFinancialEvalResult prmsFinancialEvalResult, int nominatedRank, PrmsFinancialEvlResultyDtl prmsFinancialEvlResultyDtl) {
        System.out.println("We R facade--");
        Query qu = em.createNamedQuery("PrmsFinancialEvlResultyDtl.findByFnancialResultIdMatCodeAndRank");
        qu.setParameter("fnancialResultId", prmsFinancialEvalResult);
        System.out.println("First==" + prmsFinancialEvalResult);
        qu.setParameter("nextRank", nominatedRank);
        System.out.println("nextRank==" + nominatedRank);
        qu.setParameter("matCode", prmsFinancialEvlResultyDtl.getItemId());
        System.out.println("matCode==" + prmsFinancialEvlResultyDtl.getItemId());
        System.out.println("fia id====" + prmsFinancialEvalResult);
        try {
            System.out.println("try");
            List<PrmsFinancialEvlResultyDtl> nextBidNames = new ArrayList<>();
            if (qu.getResultList().size() > 0) {
                System.out.println("is there");
                nextBidNames = qu.getResultList();
                System.out.println("bid Names" + nextBidNames);
                System.out.println("bid Names" + nextBidNames.size());
            }
            return nextBidNames;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<PrmsFinancialEvlResultyDtl> getListOfLots(PrmsFinancialEvalResult prmsFinancialEvalResult, int nominatedRank, PrmsFinancialEvlResultyDtl prmsFinancialEvlResultyDtl) {
        System.out.println("We R facade at lotnames--");
        Query qu = em.createNamedQuery("PrmsFinancialEvlResultyDtl.findByFnancialResultIdLotNoAndRank");
        qu.setParameter("fnancialResultId", prmsFinancialEvalResult);
        qu.setParameter("nextRank", nominatedRank);
        qu.setParameter("lotNo", prmsFinancialEvlResultyDtl.getLotNo());
        System.out.println("the lot no is " + prmsFinancialEvlResultyDtl.getLotNo());
        System.out.println("fia id====" + prmsFinancialEvalResult);
        try {
            List<PrmsFinancialEvlResultyDtl> lotNumber = null;
            if (qu.getResultList().size() > 0) {
                lotNumber = new ArrayList<>(qu.getResultList());
            }
            return lotNumber;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<PrmsPostQualification> getPostRqLists() {
        Query query = em.createNamedQuery("PrmsPostQualification.findByReqForApproval");
        ArrayList<PrmsPostQualification> postlst = new ArrayList<>(query.getResultList());
        return postlst;
    }

//    public List<PrmsFinancialEvlResultyDtl> getResultFormLists(PrmsFinancialEvalResult prmsFinancialEvalResult, int nominatedRank) {
//        System.out.println("faca");
//        Query q = em.createNamedQuery("PrmsFinancialEvlResultyDtl.findByResultId");
//        q.setParameter("fnancialResultId", prmsFinancialEvalResult.getFinancialevaId());
//        q.setParameter("rank", nominatedRank);
//        System.out.println("financial id  " + prmsFinancialEvalResult.getFinancialevaId());
//        System.out.println("rank=" + nominatedRank);
//        try {
//            System.out.println("ter");
//
//            List<PrmsFinancialEvlResultyDtl> resultFormLst = null;
//            if (q.getResultList().size() > 0) {
//                System.out.println("if");
//                resultFormLst = new ArrayList<>(q.getResultList());
//                System.out.println("size===" + q.getResultList().size());
//            }
//            return resultFormLst;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
    public List<PrmsFinancialEvlResultyDtl> getResultFormLists() {
        em.getEntityManagerFactory().getCache().evictAll();
        Query qu = em.createNativeQuery("SELECT PRMS_FINANCIAL_EVAL_RESULT.FINANCIAL_RESULT_NO,\n"
                + "  PRMS_FINANCIAL_EVAL_RESULT.BID_ID,\n"
                + "  PRMS_FINANCIAL_EVL_RESULTY_DTL.RESULT_RANK,\n"
                + "  PRMS_FINANCIAL_EVL_RESULTY_DTL.LOT_NO,\n"
                + "  PRMS_FINANCIAL_EVL_RESULTY_DTL.SUPPLIER_ID,\n"
                + "  PRMS_FINANCIAL_EVL_RESULTY_DTL.ITEM_ID,\n"
                + "  PRMS_BID.POST_QUALIFICATION\n"
                + "FROM PRMS_BID\n"
                + "INNER JOIN PRMS_FINANCIAL_EVAL_RESULT\n"
                + "ON PRMS_BID.ID = PRMS_FINANCIAL_EVAL_RESULT.BID_ID\n"
                + "INNER JOIN PRMS_FINANCIAL_EVL_RESULTY_DTL\n"
                + "ON PRMS_FINANCIAL_EVAL_RESULT.ID = PRMS_FINANCIAL_EVL_RESULTY_DTL.FNANCIAL_RESULT_ID\n"
                + "WHERE PRMS_FINANCIAL_EVL_RESULTY_DTL.RESULT_RANK = 1 AND PRMS_BID.POST_QUALIFICATION = 'Applicable'");
        try {

            //   System.out.println("2");
            ArrayList<PrmsFinancialEvlResultyDtl> supLists = new ArrayList<>(qu.getResultList());
            return supLists;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<PrmsPostQualification> getParamNameList() {
        Query query = em.createNativeQuery("SELECT column_name FROM user_tab_columns \n"
                + "where table_name = UPPER('PRMS_POST_QUALIFICATION') \n"
                + "and column_name not in ('postId','BID_ID','FINANC_ID','REMARK','STATUS')");
        try {
            List<PrmsPostQualification> columnNameList = new ArrayList<>();
            columnNameList = query.getResultList();
            return columnNameList;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }
}
