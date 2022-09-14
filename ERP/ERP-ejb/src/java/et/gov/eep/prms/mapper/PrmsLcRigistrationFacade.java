/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.prms.mapper;

import et.gov.eep.prms.entity.PrmsLcRigistration;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import et.gov.eep.commonApplications.mapper.AbstractFacade;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Query;

/**
 *
 * @author user
 */
@Stateless
public class PrmsLcRigistrationFacade extends AbstractFacade<PrmsLcRigistration> {

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
    public PrmsLcRigistrationFacade() {
        super(PrmsLcRigistration.class);
    }

    // <editor-fold defaultstate="collapsed" desc="Named(Static) Queries">
    public List<PrmsLcRigistration> searchByLCregNo(PrmsLcRigistration prmsLcRigistration) {
       List<PrmsLcRigistration> prmsLcRigistrationList = new ArrayList();
        if (prmsLcRigistration.getColumnName() != null && !prmsLcRigistration.getColumnName().equals("")
                && prmsLcRigistration.getColumnValue() != null && !prmsLcRigistration.getColumnValue().equals("")) {

            Query query = em.createNativeQuery("SELECT * FROM PRMS_LC_RIGISTRATION\n"
                    + "where " + prmsLcRigistration.getColumnName().toLowerCase() + " = '" + prmsLcRigistration.getColumnValue() + "'"
                    + "and " + prmsLcRigistration.getPreparedBy() + "='" + prmsLcRigistration.getPreparedBy() + "'", PrmsLcRigistration.class);
            try {
                if (query.getResultList().size() > 0) {
                    prmsLcRigistrationList = query.getResultList();
                }
                return prmsLcRigistrationList;
            } catch (NumberFormatException nfe) {
                nfe.getMessage();
                return null;
            }
        } else {
            Query query = em.createNamedQuery("PrmsLcRigistration.findByPreparedBy");
            query.setParameter("preparedBy", prmsLcRigistration.getPreparedBy());
            prmsLcRigistrationList = query.getResultList();
            return prmsLcRigistrationList;
        }
    }

    public List<PrmsLcRigistration> getNextLcRegNo(String prefix, String eYear) {
        Query query = em.createNamedQuery("PrmsLcRigistration.findByLcNoLike");
        query.setParameter("lcNo", prefix + "-" + '%' + "/" + eYear);
        List<PrmsLcRigistration> lcRegistrationsLists = new ArrayList<>();
        if (query.getResultList().size() > 0) {
            lcRegistrationsLists = query.getResultList();
        }
        return lcRegistrationsLists;
    }

    public ArrayList<PrmsLcRigistration> lcNosItemList() {
        Query queries = em.createNamedQuery("PrmsLcRigistration.findAll");

        try {
            ArrayList<PrmsLcRigistration> lcNoLists = new ArrayList<>(queries.getResultList());
            return lcNoLists;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    public PrmsLcRigistration getSelectedLCreg(Integer lcId) {
        Query query = em.createNamedQuery("PrmsLcRigistration.findByLcId", PrmsLcRigistration.class);
        query.setParameter("lcId", lcId);
        try {
            PrmsLcRigistration idlst = (PrmsLcRigistration) query.getResultList().get(0);
            return idlst;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public PrmsLcRigistration getlastLCRegNo() {
        Query query = em.createNamedQuery("PrmsLcRigistration.findByMaxLcId", PrmsLcRigistration.class);
        PrmsLcRigistration result = null;
        try {
            if (query.getResultList().size() > 0) {
                result = (PrmsLcRigistration) query.getResultList().get(0);
            }
            return result;

        } catch (Exception ex) {
            ex.printStackTrace();

            return null;

        }
    }

    public List<PrmsLcRigistration> getLCLists() {
        Query query = em.createNamedQuery("PrmsLcRigistration.findByReqForApproval");
        ArrayList<PrmsLcRigistration> LClst = new ArrayList<>(query.getResultList());
        return LClst;
    }

    public List<PrmsLcRigistration> getLCNoLst() {
        Query query = em.createNamedQuery("PrmsLcRigistration.findAlls");
        try {
            List<PrmsLcRigistration> lc = new ArrayList<>();
            if (query.getResultList().size() > 0) {
                lc = query.getResultList();
            }
            return lc;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public PrmsLcRigistration getLcNums(String lcNo) {
        PrmsLcRigistration lcNoLst = null;
        Query query = em.createNamedQuery("PrmsLcRigistration.findByLcNo", PrmsLcRigistration.class);
        query.setParameter("lcNo", lcNo);
        try {
            if (query.getResultList().size() > 0) {
                lcNoLst = (PrmsLcRigistration) query.getResultList().get(0);
                return lcNoLst;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lcNoLst;
    }

    //Lc No by List where status is approved Status
    public List<PrmsLcRigistration> getApprovedLcNoLists(int approvedStatus) {
        Query query = em.createNamedQuery("PrmsLcRigistration.findByApprovedStatus");
        query.setParameter("status", approvedStatus);
        try {
            List<PrmsLcRigistration> LcNos = new ArrayList<>();
            if (query.getResultList().size() > 0) {
                LcNos = (query.getResultList());
            }
            return LcNos;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public PrmsLcRigistration getLcAmountByLcNumber(PrmsLcRigistration prmsLcRigistration) {
        Query query = em.createNamedQuery("PrmsLcRigistration.searchByLcNoForAmount");
        query.setParameter("lcNo", prmsLcRigistration.getLcNo());
        try {
            PrmsLcRigistration getLcAmount = new PrmsLcRigistration();
            if (query.getResultList().size() > 0) {
                getLcAmount = (PrmsLcRigistration) (query.getResultList().get(0));
            }
            return getLcAmount;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
// </editor-fold>

    public List<PrmsLcRigistration> getParamNameList() {
         Query query = em.createNativeQuery("SELECT column_name FROM user_tab_columns \n"
                + "where table_name = UPPER('PRMS_LC_RIGISTRATION') \n"
                + "and column_name not in ('LC_ID','LC_EXT_CODITION','REMARK','SERVICE_PRO_ID','COUNTRY_ID','CURRENCY_ID','DESCRIPTION_OF_GOODS','STATUS','DOCUMENT_ID','FOREIGN_ID')");
        try {
            List<PrmsLcRigistration> columnNameList = new ArrayList<>();
            columnNameList = query.getResultList();
            return columnNameList;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }

}
