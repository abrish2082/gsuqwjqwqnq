/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.prms.mapper;

import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.prms.entity.PrmsBid;
import et.gov.eep.prms.entity.PrmsPreminilaryEvaluation;
import et.gov.eep.prms.entity.PrmsPreminilaryEvalutionDt;
import et.gov.eep.prms.entity.PrmsSupplyProfile;
import java.util.ArrayList;
import java.util.Arrays;
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
public class PrmsPreminilaryEvaluationFacade extends AbstractFacade<PrmsPreminilaryEvaluation> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PrmsPreminilaryEvaluationFacade() {
        super(PrmsPreminilaryEvaluation.class);
    }

    public List<PrmsBid> getBidCreteriaList() {

        Query query = em.createNamedQuery("PrmsBid.findByBidCreteria");
        try {
            ArrayList<PrmsBid> prmsBidList = new ArrayList<>(query.getResultList());

            return prmsBidList;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<PrmsBid> getBidNoLst() {
        Query query = em.createNativeQuery("SELECT DISTINCT PRMS_BID.REF_NO,\n"
                + "  PRMS_BID.ID\n"
                + "FROM PRMS_BID\n"
                + "INNER JOIN PRMS_BID_OPENING_CHECK_LIST\n"
                + "ON PRMS_BID.ID = PRMS_BID_OPENING_CHECK_LIST.BID_ID\n"
                + "INNER JOIN PRMS_BID_OPENING_CHECKLST_DT\n"
                + "ON PRMS_BID_OPENING_CHECK_LIST.BID_OPEN_CHECK_LIST_ID = PRMS_BID_OPENING_CHECKLST_DT.BID_CHECK_LIST_ID\n", PrmsBid.class);
        try {
            ArrayList<PrmsBid> prmsBidList = new ArrayList(query.getResultList());
            return prmsBidList;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<PrmsBid> getBidCriteriList(String bidCriteria) {

        Query query = em.createNamedQuery("PrmsBid.findByBidCriteria", PrmsBid.class);
        List<PrmsBid> criteriaList = new ArrayList<PrmsBid>();
        query.setParameter("bidCriteria", bidCriteria);

        try {
            PrmsBid pr = (PrmsBid) query.getSingleResult();
            String[] b = pr.getBidCriteriaId().split(",");

            for (int i = 0; i < b.length; i++) {
                PrmsBid ps = new PrmsBid();
                ps.setBidCriteriaId(b[i]);
                criteriaList.add(ps);

            }
            return criteriaList;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<PrmsBid> getSplitCrtreia() {
        PrmsBid prmsBid = new PrmsBid();
        String[] b = prmsBid.getBidCriteriaId().split(",");
        List<PrmsBid> bidNoLst = null;
        try {
            Query query = em.createNamedQuery("PrmsBid.findByBidCriteria", PrmsBid.class);
            for (int i = 0; i < b.length; i++) {

                prmsBid.setBidCriteriaId(b[i]);
                bidNoLst.add(prmsBid);
                bidNoLst = new ArrayList<>(Arrays.asList(bidNoLst.get(i)));
                return bidNoLst;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bidNoLst;
    }

    public List<PrmsSupplyProfile> getSupplierName(String supplierName) {
        System.out.println("in suppl " + supplierName);
        Query query = em.createNativeQuery("SELECT DISTINCT PRMS_BID.REF_NO,\n"
                + "  PRMS_SUPPLY_PROFILE.VENDOR_NAME,\n"
                + " PRMS_SUPPLY_PROFILE.ID\n"
                + "FROM PRMS_BID\n"
                + "INNER JOIN PRMS_BID_OPENING_CHECK_LIST\n"
                + "ON PRMS_BID.ID = PRMS_BID_OPENING_CHECK_LIST.BID_ID\n"
                + "INNER JOIN PRMS_BID_OPENING_CHECKLST_DT\n"
                + "ON PRMS_BID_OPENING_CHECK_LIST.BID_OPEN_CHECK_LIST_ID = PRMS_BID_OPENING_CHECKLST_DT.BID_CHECK_LIST_ID\n"
                + "INNER JOIN PRMS_SUPPLY_PROFILE\n"
                + "ON PRMS_SUPPLY_PROFILE.ID = PRMS_BID_OPENING_CHECKLST_DT.SUPP_ID\n"
                + "where PRMS_BID.REF_NO='" + supplierName + "'", PrmsSupplyProfile.class);
        try {
            ArrayList<PrmsSupplyProfile> prmsSupplyProfiList = new ArrayList(query.getResultList());
            System.out.println("out of suppl" + prmsSupplyProfiList.size());
            return prmsSupplyProfiList;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<PrmsPreminilaryEvaluation> getBidPrelmnryEvNo(PrmsPreminilaryEvaluation prmsPreminirayEval) {
       List<PrmsPreminilaryEvaluation> preminilaryEvaluationLst = new ArrayList();
        if (prmsPreminirayEval.getColumnName() != null && !prmsPreminirayEval.getColumnName().equals("")
                && prmsPreminirayEval.getColumnValue() != null && !prmsPreminirayEval.getColumnValue().equals("")) {

            Query query = em.createNativeQuery("SELECT * FROM PRMS_PREMINILARY_EVALUATION\n"
                    + "where " + prmsPreminirayEval.getColumnName().toLowerCase() + " = '" + prmsPreminirayEval.getColumnValue() + "'"
                    + "and " + prmsPreminirayEval.getPreparedBy() + "='" + prmsPreminirayEval.getPreparedBy() + "'", PrmsPreminilaryEvaluation.class);
            try {
                if (query.getResultList().size() > 0) {
                    preminilaryEvaluationLst = query.getResultList();
                }
                return preminilaryEvaluationLst;
            } catch (NumberFormatException nfe) {
                nfe.getMessage();
                return null;
            }
        } else {
            Query query = em.createNamedQuery("PrmsPreminilaryEvaluation.findByPreparedBy");
            query.setParameter("preparedBy", prmsPreminirayEval.getPreparedBy());
            preminilaryEvaluationLst = query.getResultList();
            return preminilaryEvaluationLst;
        }
    }

    public PrmsPreminilaryEvaluation getSelectedId(String id) {
        Query query = em.createNamedQuery("PrmsPreminilaryEvaluation.findById");
        query.setParameter("id", id);
        try {
            PrmsPreminilaryEvaluation idlst = (PrmsPreminilaryEvaluation) query.getResultList().get(0);
            return idlst;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<PrmsPreminilaryEvaluation> getBidNo() {
        List<PrmsPreminilaryEvaluation> itemLst = null;
        try {
            Query query = em.createNamedQuery("PrmsPreminilaryEvaluation.findAll", PrmsPreminilaryEvaluation.class);
            itemLst = (List<PrmsPreminilaryEvaluation>) query.getResultList();

        } catch (Exception e) {
            e.printStackTrace();//THIS IS PURCHASE REQUEST 
            return null;
        }
        return itemLst;
    }

    public PrmsPreminilaryEvaluation getPreliminarNo() {

        Query query = em.createNamedQuery("PrmsPreminilaryEvaluation.findByLastId");

        try {
            PrmsPreminilaryEvaluation preminilaryEvaluationObj = (PrmsPreminilaryEvaluation) query.getResultList().get(0);

            return preminilaryEvaluationObj;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public boolean findDup(PrmsPreminilaryEvalutionDt preminilaryEvalutionDt) {
        boolean dup;
        try {
            Query query = em.createNativeQuery("SELECT PRMS_PREMINILARY_EVALUTION_DT.SUPPLIER_CODE,\n"
                    + "  PRMS_PREMINILARY_EVALUTION_DT.SUPPLIER_ID,\n"
                    + "  PRMS_SUPPLY_PROFILE.VENDOR_NAME,\n"
                    + "  PRMS_PREMINILARY_EVALUTION_DT.ID\n"
                    + "FROM PRMS_PREMINILARY_EVALUTION_DT\n"
                    + "INNER JOIN PRMS_SUPPLY_PROFILE\n"
                    + "ON PRMS_SUPPLY_PROFILE.ID = PRMS_PREMINILARY_EVALUTION_DT.SUPPLIER_ID\n"
                    + "WHERE PRMS_PREMINILARY_EVALUTION_DT.SUPPLIER_CODE = '" + preminilaryEvalutionDt.getSupplierCode() + "'\n"
                    + "OR PRMS_PREMINILARY_EVALUTION_DT.SUPPLIER_ID      = '" + preminilaryEvalutionDt.getSupplierId().getVendorName() + "'", PrmsPreminilaryEvalutionDt.class);
            if (query.getResultList().size() > 0) {
                dup = true;
            } else {
                dup = false;
            }
            return dup;
        } catch (Exception ex) {
            return false;
        }
    }

    public List<PrmsPreminilaryEvaluation> getPrelmnryOnList() {
        Query query = em.createNamedQuery("PrmsPreminilaryEvaluation.findByPremlntyReq", PrmsPreminilaryEvaluation.class);
        ArrayList<PrmsPreminilaryEvaluation> preminilaryEvaluationList = new ArrayList(query.getResultList());
        return preminilaryEvaluationList;
    }

    public List<PrmsPreminilaryEvaluation> getParamNameList() {
         Query query = em.createNativeQuery("SELECT column_name FROM user_tab_columns \n"
                + "where table_name = UPPER('PRMS_PREMINILARY_EVALUATION') \n"
                + "and column_name not in ('ID','BID_ID','STATUS','REAMARK')");
        try {
            List<PrmsPreminilaryEvaluation> columnNameList = new ArrayList<>();
            columnNameList = query.getResultList();
            return columnNameList;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }
}
