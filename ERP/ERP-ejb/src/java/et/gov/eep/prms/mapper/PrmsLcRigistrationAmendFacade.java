/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.prms.mapper;

import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.prms.entity.PrmsBankClearance;
import et.gov.eep.prms.entity.PrmsLcRigistration;
import et.gov.eep.prms.entity.PrmsLcRigistrationAmend;
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
public class PrmsLcRigistrationAmendFacade extends AbstractFacade<PrmsLcRigistrationAmend> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PrmsLcRigistrationAmendFacade() {
        super(PrmsLcRigistrationAmend.class);
    }

    // <editor-fold defaultstate="collapsed" desc="Named Queries">
    public List<PrmsLcRigistrationAmend> getLCNoAndLCAmendNo(PrmsLcRigistrationAmend prmsLcRigistrationAmend) {
        List<PrmsLcRigistrationAmend> lcRigistrationAmendList = new ArrayList();
        if (prmsLcRigistrationAmend.getColumnName() != null && !prmsLcRigistrationAmend.getColumnName().equals("")
                && prmsLcRigistrationAmend.getColumnValue() != null && !prmsLcRigistrationAmend.getColumnValue().equals("")) {

            Query query = em.createNativeQuery("SELECT * FROM PRMS_LC_RIGISTRATION_AMEND\n"
                    + "where " + prmsLcRigistrationAmend.getColumnName().toLowerCase() + " = '" + prmsLcRigistrationAmend.getColumnValue() + "'"
                    + "and " + prmsLcRigistrationAmend.getPreparedBy() + "='" + prmsLcRigistrationAmend.getPreparedBy() + "'", PrmsLcRigistrationAmend.class);
            try {
                if (query.getResultList().size() > 0) {
                    lcRigistrationAmendList = query.getResultList();
                }
                return lcRigistrationAmendList;
            } catch (NumberFormatException nfe) {
                nfe.getMessage();
                return null;
            }
        } else {
            Query query = em.createNamedQuery("PrmsLcRigistrationAmend.findByPreparedBy");
            query.setParameter("preparedBy", prmsLcRigistrationAmend.getPreparedBy());
            lcRigistrationAmendList = query.getResultList();
            return lcRigistrationAmendList;
        }
    }

    public List<PrmsLcRigistrationAmend> getNextLcRegAmendNo(String prefix, String eYear) {
        Query query = em.createNamedQuery("PrmsLcRigistrationAmend.findByLcAmendNos");
        query.setParameter("amendedNo", prefix + "-" + '%' + "/" + eYear);
        List<PrmsLcRigistrationAmend> lcAmendNoList = new ArrayList<>();
        if (query.getResultList().size() > 0) {
            lcAmendNoList = query.getResultList();
        }
        return lcAmendNoList;
    }

    public List<PrmsLcRigistrationAmend> getLCAmendedNoListByLcId(PrmsLcRigistration prmsLcRigistration) {
        Query query = em.createNamedQuery("PrmsLcRigistrationAmend.findByLcIds");
        query.setParameter("lcId", prmsLcRigistration.getLcId());
        List<PrmsLcRigistrationAmend> amendedNoList = new ArrayList<>();
        if (query.getResultList().size() > 0) {
            amendedNoList = query.getResultList();
        }
        return amendedNoList;
    }

    public List<PrmsLcRigistrationAmend> searchAmendedLCByLCNo(PrmsLcRigistrationAmend prmsLcRigistrationAmend) {
        Query query = em.createNamedQuery("PrmsLcRigistrationAmend.findByLcId", PrmsLcRigistrationAmend.class);

        query.setParameter("lcId", prmsLcRigistrationAmend.getLcId());
        try {
            ArrayList<PrmsLcRigistrationAmend> amendList = new ArrayList(query.getResultList());

            return amendList;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public PrmsLcRigistrationAmend getAmendNo() {
        Query query = em.createNamedQuery("PrmsLcRigistrationAmend.findByGeneratedAmdNm");
        PrmsLcRigistrationAmend directPurcObj = null;
        try {
            if (query.getResultList().size() > 0) {
                directPurcObj = (PrmsLcRigistrationAmend) query.getResultList().get(0);
            }
            return directPurcObj;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public PrmsLcRigistrationAmend getSelectedLcregAmendID(Integer id) {
        Query query = em.createNamedQuery("PrmsLcRegDetailAmendment.findById", PrmsLcRigistrationAmend.class);
        query.setParameter("id", id);
        try {

            PrmsLcRigistrationAmend idlst = (PrmsLcRigistrationAmend) query.getResultList().get(0);
            return idlst;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public PrmsLcRigistrationAmend getLCNumbers(BigDecimal AmendedNo) {
        Query query = em.createNamedQuery("PrmsLcRigistrationAmend.findById");
        PrmsLcRigistrationAmend directPurcObj = null;
        query.setParameter("id", AmendedNo);

        try {
            directPurcObj = (PrmsLcRigistrationAmend) query.getResultList().get(0);

            return directPurcObj;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<PrmsLcRigistrationAmend> getLCAmendLists() {
        Query query = em.createNamedQuery("PrmsLcRigistrationAmend.findByReqForApproval");
        ArrayList<PrmsLcRigistrationAmend> LClst = new ArrayList<>(query.getResultList());
        return LClst;
    }

    public List<PrmsLcRigistrationAmend> findLCAmentNOs(PrmsLcRigistrationAmend prmsLcRigistrationAmend) {
        Query query = em.createNamedQuery("PrmsLcRigistrationAmend.findByLCAmendNos", PrmsLcRigistrationAmend.class);
        query.setParameter("amendedNo", prmsLcRigistrationAmend.getAmendedNo() + '%');

        try {
            List<PrmsLcRigistrationAmend> marketAssessmentLst = new ArrayList(query.getResultList());
            return marketAssessmentLst;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<PrmsLcRigistrationAmend> findLCAmendListByWfStatus(int status) {
        Query query = em.createNamedQuery("PrmsLcRigistrationAmend.findByReqForApprovalss", PrmsLcRigistrationAmend.class);
        query.setParameter("status", status);
        try {
            ArrayList<PrmsLcRigistrationAmend> listofAmend = new ArrayList(query.getResultList());
            return listofAmend;
        } catch (Exception e) {
            return null;
        }
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Native Queries">
    // to check whether it's amended LC or not
    public List<PrmsLcRigistrationAmend> checkAsLcIsAmendedByLcId(PrmsLcRigistration prmsLcRigistration) {
        Query q = em.createNativeQuery("SELECT * FROM prms_lc_rigistration_amend lcamd\n"
                + "inner join(select lc_id, max(id) as LcAmendMaxId\n"
                + "from prms_lc_rigistration_amend "
                + "GROUP BY lc_id) lcamd2\n"
                + "on  lcamd.id=lcamd2.LcAmendMaxId\n"
                + "inner join prms_lc_rigistration lc\n"
                + "on lc.lc_id=lcamd.lc_id\n"
                + "where lc.lc_id='" + prmsLcRigistration.getLcId() + "'", PrmsLcRigistrationAmend.class);

        try {
            List<PrmsLcRigistrationAmend> amendedLcLists = new ArrayList<>();
            if (q.getResultList().size() > 0) {
                amendedLcLists = q.getResultList();
            }
            return amendedLcLists;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //getting info from Lc Amend by LC Id
    public PrmsLcRigistrationAmend getLcAmendedInfoByLcId(PrmsLcRigistration prmsLcRigistration) {
        Query q = em.createNativeQuery("SELECT * FROM prms_lc_rigistration_amend lcamd\n"
                + "inner join(SELECT lcamde.lc_id, max(lcamde.id) as lcAmendMaximumId \n"
                + "FROM prms_lc_rigistration_amend lcamde \n"
                + "GROUP BY lcamde.lc_id) lcamd2\n"
                + "on lcamd.id=lcamd2.lcAmendMaximumId\n"
                + " where lcamd.lc_id = '" + prmsLcRigistration.getLcId() + "' ", PrmsLcRigistrationAmend.class);

        try {
            PrmsLcRigistrationAmend amendedLcInfo = new PrmsLcRigistrationAmend();
            if (q.getResultList().size() > 0) {
                amendedLcInfo = (PrmsLcRigistrationAmend) q.getResultList().get(0);
            }
            return amendedLcInfo;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

//getting info from LC Amend by Amend Number
    public PrmsLcRigistrationAmend getLcAmendInfoByAmendedNo(String amendedNo) {
        Query query = em.createNativeQuery("SELECT * FROM prms_lc_rigistration_amend \n"
                + "where lc_amend_no='" + amendedNo + "'", PrmsLcRigistrationAmend.class);
        PrmsLcRigistrationAmend amendedNoInfo = new PrmsLcRigistrationAmend();
        if (query.getResultList().size() > 0) {
            amendedNoInfo = (PrmsLcRigistrationAmend) query.getResultList().get(0);
        }
        return amendedNoInfo;
    }
     // </editor-fold >

    public List<PrmsLcRigistrationAmend> getParamNameList() {
         Query query = em.createNativeQuery("SELECT column_name FROM user_tab_columns \n"
                + "where table_name = UPPER('PRMS_LC_RIGISTRATION_AMEND') \n"
                + "and column_name not in ('LC_ID','CURRENCY_ID','DOCUMENT_ID','AMENDMENT_COST_COVERED','REMARK','SERVICE_PRO_ID','CONTRACT_ID','COUNTRY_ID','DOC_ID','DESCRIPTION_OF_GOODS','STATUS','FOREIGN_ID')");
        try {
            List<PrmsLcRigistrationAmend> columnNameList = new ArrayList<>();
            columnNameList = query.getResultList();
            return columnNameList;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }

}
