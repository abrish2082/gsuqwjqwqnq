package et.gov.eep.prms.mapper;

import et.gov.eep.prms.entity.PrmsBidOpeningCheckList;
import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.prms.entity.PrmsBidOpeningChecklstDt;
import et.gov.eep.prms.entity.PrmsCheckListDetailforlot;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author pc
 */
@Stateless
public class PrmsBidOpeningCheckListFacade extends AbstractFacade<PrmsBidOpeningCheckList> {

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
    public PrmsBidOpeningCheckListFacade() {
        super(PrmsBidOpeningCheckList.class);
    }

    // <editor-fold defaultstate="collapsed" desc="Named(Static) Queries">
    /**
     *
     * @param prmsBidOpeningCheckList
     * @return
     */
    public PrmsBidOpeningCheckList getRequestNo(PrmsBidOpeningCheckList prmsBidOpeningCheckList) {
        Query query = em.createNamedQuery("PrmsBidOpeningCheckList.findByBidCheckListNo");
        query.setParameter("bidCheckListNo", prmsBidOpeningCheckList.getBidCheckListNo());
        try {
            PrmsBidOpeningCheckList selectedobj = (PrmsBidOpeningCheckList) query.getResultList().get(0);
            return selectedobj;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public ArrayList<PrmsBidOpeningCheckList> searchRequestForPayment(PrmsBidOpeningCheckList prmsBidOpeningCheckList) {
        Query query = em.createNamedQuery("PrmsBidOpeningCheckList.SearchByBidCheckListNo");
        query.setParameter("bidCheckListNo", prmsBidOpeningCheckList.getBidCheckListNo().toLowerCase() + '%');
        try {
            ArrayList<PrmsBidOpeningCheckList> ServiceProvideList = new ArrayList(query.getResultList());
            return ServiceProvideList;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }

    }

    /**
     *
     * @return
     */
    public PrmsBidOpeningCheckList getLastCheckListNo() {
        Query query = em.createNamedQuery("PrmsBidOpeningCheckList.findByMaxSIVNo");
        try {
            PrmsBidOpeningCheckList directPurcObj = (PrmsBidOpeningCheckList) query.getResultList().get(0);

            return directPurcObj;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     *
     * @return
     */
    public String getLastSIVNo() {
        String result;
        Query query = em.createNamedQuery("PapmsSiv.findByMaxSIVNo");
        try {
            result = (String) query.getResultList().get(0);
            if (result == null) {
                result = "0";
            }
            return result;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     *
     * @return
     */
    /**
     *
     * @return
     */
    public PrmsBidOpeningCheckList LastCheckListNo() {
        Query query = em.createNamedQuery("PrmsBidOpeningCheckList.findByMaxCheckListNum");
        PrmsBidOpeningCheckList CheckListNo = null;
        try {
            if (query.getResultList().size() > 0) {
                CheckListNo = (PrmsBidOpeningCheckList) query.getResultList().get(0);
            } else {
                return CheckListNo;
            }
            return CheckListNo;
        } catch (Exception ex) {
            return null;
        }

    }

    public PrmsBidOpeningCheckList getSelectedRequest(BigDecimal id) {
        Query query = em.createNamedQuery("PrmsBidOpeningCheckList.findByBidOpenCheckListId");
        query.setParameter("bidOpenCheckListId", id);
        try {
            PrmsBidOpeningCheckList selectrequest = (PrmsBidOpeningCheckList) query.getResultList().get(0);
            return selectrequest;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public ArrayList<PrmsBidOpeningCheckList> searchBidderRegistration() {
        Query query = em.createNamedQuery("PrmsBidOpeningCheckList.findAllByStatus");
        try {
            ArrayList<PrmsBidOpeningCheckList> bissubmision = new ArrayList(query.getResultList());
            return bissubmision;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public PrmsCheckListDetailforlot LastCheckListlotid() {

        PrmsCheckListDetailforlot lastCheckListotId = new PrmsCheckListDetailforlot();
        Query query = em.createNamedQuery("PrmsCheckListDetailforlot.findByMaxCheckListNum");
        try {
            if (query.getResultList().size() > 0) {
                lastCheckListotId = (PrmsCheckListDetailforlot) query.getResultList().get(0);
            } else {
                lastCheckListotId = null;
            }
            return lastCheckListotId;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Native Queries">
    //Lists of Parameters from PRMS_BID_OPENING_CHECK_LIST table
    public List<PrmsBidOpeningCheckList> getBidOpeningCheckListSearchParameterLst() {
        Query query = em.createNativeQuery("SELECT column_name  FROM  user_tab_cols\n"
                + "where table_name=Upper('PRMS_BID_OPENING_CHECK_LIST')\n"
                + "and column_name not in ('BID_OPEN_CHECK_LIST_ID','STATUS','BID_ID','COMMITEE_ID',"
                + "'COMMITTEE_MEMBERS','DOCUMENT_ID','CURRENT_STATUS') order by column_name ASC");
        List<PrmsBidOpeningCheckList> columnNameLists = new ArrayList<>();
        columnNameLists = query.getResultList();
        return columnNameLists;
    }

    /**
     *
     * @param prmsBidOpeningCheckList
     * @return
     */
    public ArrayList<PrmsBidOpeningCheckList> searchCheckList(PrmsBidOpeningCheckList prmsBidOpeningCheckList) {

        ArrayList<PrmsBidOpeningCheckList> bidOpeningCheckListSearchLists = new ArrayList<>();
        if (prmsBidOpeningCheckList.getColumnValue() != null && prmsBidOpeningCheckList.getColumnName() != null
                && !prmsBidOpeningCheckList.getColumnValue().equals("") && !prmsBidOpeningCheckList.getColumnName().equals("")) {
            Query query = em.createNativeQuery("SELECT * FROM PRMS_BID_OPENING_CHECK_LIST\n"
                    + "where " + prmsBidOpeningCheckList.getColumnName().toLowerCase() + " = '" + prmsBidOpeningCheckList.getColumnValue() + "' "
                    + "and " + prmsBidOpeningCheckList.getPreparedBy() + "='" + prmsBidOpeningCheckList.getPreparedBy() + "' ", PrmsBidOpeningCheckList.class);
            try {

                if (query.getResultList().size() > 0) {
                    bidOpeningCheckListSearchLists = new ArrayList(query.getResultList());
                }
                return bidOpeningCheckListSearchLists;
            } catch (Exception ex) {
                ex.getMessage();
                return null;
            }
        } else {
            Query query = em.createNamedQuery("PrmsBidOpeningCheckList.findByPreparedBy");
            query.setParameter("preparedBy", prmsBidOpeningCheckList.getPreparedBy());
            bidOpeningCheckListSearchLists = new ArrayList(query.getResultList());
            return bidOpeningCheckListSearchLists;
        }
    }
    // </editor-fold>
}
