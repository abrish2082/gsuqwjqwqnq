/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.mapper.admin;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.fcms.entity.admin.FmsGeneralLedger;

/**
 *
 * @author user
 */
@Stateless
public class FmsGeneralLedgerFacade extends AbstractFacade<FmsGeneralLedger> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    /**
     *
     */
    public FmsGeneralLedgerFacade() {
        super(FmsGeneralLedger.class);
    }

    /**
     *
     * @param fmsGeneralLedger
     * @return account list
     */
    public FmsGeneralLedger getByACCountTitle(FmsGeneralLedger fmsGeneralLedger) {
        Query query = em.createNamedQuery("FmsGeneralLedger.findByAccountTitle");
        query.setParameter("accountTitle", fmsGeneralLedger.getAccountTitle());
        try {
            FmsGeneralLedger acclist = (FmsGeneralLedger) (query.getResultList().get(0));
            return acclist;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     *
     * @param Glname
     * @return GL list
     */
    public FmsGeneralLedger getGeneralLedger(FmsGeneralLedger Glname) {
        Query query = em.createNamedQuery("FmsGeneralLedger.findByGeneralLedgerCode");
        query.setParameter("generalLedgerCode", Glname.getGeneralLedgerCode());
        try {
            FmsGeneralLedger glList = (FmsGeneralLedger) query.getResultList().get(0);
            return glList;

        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     *
     * @param generalLedger
     * @return fmsGeneralLedger by account Title
     */
    public FmsGeneralLedger getGLAccountInfo(FmsGeneralLedger generalLedger) {
        Query query = em.createNamedQuery("FmsGeneralLedger.findByAccountTitle", FmsGeneralLedger.class);
        query.setParameter("accountTitle", generalLedger.getAccountTitle());
        try {
            FmsGeneralLedger fmsGeneralLedger = (FmsGeneralLedger) query.getResultList().get(0);
            return fmsGeneralLedger;
        } catch (Exception ex) {
            return null;
        }
    }

    /**
     *
     * @param generalLedger
     * @return fmsGeneralLedger by account code
     */
    public FmsGeneralLedger getGlAccountCodeInfo(FmsGeneralLedger generalLedger) {
        Query query = em.createNamedQuery("FmsGeneralLedger.findByGlAccountCode", FmsGeneralLedger.class);
        query.setParameter("glAccountCode", generalLedger.getGeneralLedgerCode());
        try {
            FmsGeneralLedger fmsGeneralLedger = (FmsGeneralLedger) query.getResultList().get(0);
            return fmsGeneralLedger;
        } catch (Exception ex) {

            return null;
        }
    }

     /**
     *@param generalLedger
     * @return fmsGeneralLedger
     */
    public FmsGeneralLedger getGlcode(FmsGeneralLedger generalLedger) {
        Query query = em.createNamedQuery("FmsGeneralLedger.findByGeneralLedgerCode");
        query.setParameter("generalLedgerCode", generalLedger.getGeneralLedgerCode());
        try {
            FmsGeneralLedger fmsGeneralLedger = (FmsGeneralLedger) query.getResultList().get(0);
            return fmsGeneralLedger;
        } catch (Exception ex) {

            return null;
        }
    }

    /**
     *@param fmsGeneralLedger
     * @return account list
     */
    public FmsGeneralLedger getByGlId(FmsGeneralLedger fmsGeneralLedger) {
        Query query = em.createNamedQuery("FmsGeneralLedger.findByGeneralLedgerId");
        query.setParameter("generalLedgerId", fmsGeneralLedger.getGeneralLedgerId());
        try {
            FmsGeneralLedger acclist = (FmsGeneralLedger) (query.getSingleResult());
            return acclist;
        } catch (NoResultException ex) {
            return null;
        }
    }

    /**
     *
     * @return account list
     */
    public FmsGeneralLedger findById(int id) {
        Query query = em.createNamedQuery("FmsGeneralLedger.findByGeneralLedgerId");
        query.setParameter("generalLedgerId", id);
        try {
            FmsGeneralLedger acclist = (FmsGeneralLedger) (query.getSingleResult());
            return acclist;
        } catch (NoResultException ex) {
            return null;
        }
    }

    /**
     *
     * @return GlAccountCodeList
     */
    public List<FmsGeneralLedger> searchGlAccountCode() {
        List<FmsGeneralLedger> GlAccountCodeList = null;
        try {
            Query query = em.createNamedQuery("FmsGeneralLedger.findAll", FmsGeneralLedger.class);

            GlAccountCodeList = (List<FmsGeneralLedger>) query.getResultList();
            return GlAccountCodeList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     *
     * @return oprGLlist
     */
    public List<FmsGeneralLedger> getOprGLAccount() {
        Query query = em.createNamedQuery("FmsGeneralLedger.findOprGLAccount");
        try {
            List<FmsGeneralLedger> oprGLlist;
            oprGLlist = (List<FmsGeneralLedger>) query.getResultList();
            return oprGLlist;
        } catch (Exception ex) {
            return null;
        }
    }

    /**
     *
     * @param generalLedgers
     * @return ChartOfAccountGlList
     */
    public List<FmsGeneralLedger> searchGL(FmsGeneralLedger generalLedgers) {
        List<FmsGeneralLedger> ChartOfAccountGlList = null;
        try {
            Query query = em.createNamedQuery("FmsGeneralLedger.findByAccountName", FmsGeneralLedger.class);
            query.setParameter("accountTitle", generalLedgers.getAccountTitle() + "%");
            ChartOfAccountGlList = (List<FmsGeneralLedger>) query.getResultList();
            return ChartOfAccountGlList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    /**
     *
     * @return projGLlist
     */
    public List<FmsGeneralLedger> getProjGLAccount() {
        Query query = em.createNamedQuery("FmsGeneralLedger.findProjGLAccount");
        try {
            List<FmsGeneralLedger> projGLlist;
            projGLlist = (List<FmsGeneralLedger>) query.getResultList();
            return projGLlist;
        } catch (Exception ex) {
            return null;
        }
    }

    /**
     *
     * @param generalLedger
     * @return duplicate voucher id
     */
    public boolean getGeneralLedgerDup(FmsGeneralLedger generalLedger) {
        boolean duplicaton;
        Query query = em.createNamedQuery("FmsGeneralLedger.findVoucheIdDup", FmsGeneralLedger.class);
        try {
            if (query.getResultList().size() > 0) {
                duplicaton = true;
            } else {
                duplicaton = false;
            }
            return duplicaton;
        } catch (Exception ex) {
            return false;
        }
    }

    /**
     *
     * @return slList
     */
    public ArrayList<FmsGeneralLedger> getGeneralLedgerCodeList() {
        Query query = em.createNamedQuery("FmsGeneralLedger.findAll");
        try {
            ArrayList<FmsGeneralLedger> slList = new ArrayList(query.getResultList());
            return slList;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     *
     * @param fmsGeneralLedger
     * @return glCodeLikeList
     */
    public ArrayList<FmsGeneralLedger> searchGenLedger(FmsGeneralLedger fmsGeneralLedger) {
        try {
            Query query = em.createNamedQuery("FmsGeneralLedger.findByGeneralLedgerCodeLike");
            query.setParameter("generalLedgerCode", fmsGeneralLedger.getGeneralLedgerCode() + '%');
            ArrayList<FmsGeneralLedger> glCodeLikeList = new ArrayList(query.getResultList());
            return glCodeLikeList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     *
     * @param accountType
     * @return glCodeLikeList
     */
    public ArrayList<FmsGeneralLedger> getByAccountType(Integer accountType) {

        Query query = em.createNamedQuery("FmsGeneralLedger.findByAccountType");
        query.setParameter("accountType", accountType);
        try {
            ArrayList<FmsGeneralLedger> glCodeLikeList = new ArrayList(query.getResultList());
            return glCodeLikeList;
        } catch (NoResultException ex) {
            return null;
        }
    }

    /**
     *
     * @return glCodeList
     */
    public List<FmsGeneralLedger> getGLListForFixedAsset() {
        List<FmsGeneralLedger> glCodeList;
        try {
            Query query = em.createNativeQuery("SELECT * FROM IFRS_FIXED_ASSET ifa "
                    + "INNER JOIN "
                    + "FMS_SUBSIDIARY_LEDGER fmsl "
                    + "ON ifa.SUBSIDIARY_ID = fmsl.SUBSIDIARY_ID "
                    + "INNER JOIN "
                    + "FMS_GENERAL_LEDGER fmsg "
                    + "ON fmsg.GENERAL_LEDGER_ID = fmsl.GENERAL_LEDGER_ID ", FmsGeneralLedger.class);
            glCodeList = (List<FmsGeneralLedger>) query.getResultList();
            return glCodeList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     *
     * @param generalLedger
     * @return GlAccountCodeList
     */
    public List<FmsGeneralLedger> searchGlAccountCode(FmsGeneralLedger generalLedger) {

        List<FmsGeneralLedger> GlAccountCodeList = null;
        try {
            Query query = em.createNativeQuery("SELECT * FROM fms_general_ledger WHERE gl_account_code  LIKE ? ", FmsGeneralLedger.class);
            query.setParameter(1, generalLedger.getGeneralLedgerCode() + "%");
            GlAccountCodeList = (List<FmsGeneralLedger>) query.getResultList();
            return GlAccountCodeList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     *
     * @param glCode
     * @return glCodeList
     */
    public List<FmsGeneralLedger> searchGlAccountCode(String glCode) {
        List<FmsGeneralLedger> glCodeList;
        try {
            Query query = em.createNativeQuery("SELECT GENERAL_LEDGER_ID, "
                    + "  ACCOUNT_TITLE, "
                    + "  GL_ACCOUNT_CODE, "
                    + "  STATUS "
                    + "FROM FMS_GENERAL_LEDGER "
                    + "WHERE GL_ACCOUNT_CODE LIKE '" + glCode + "%'", FmsGeneralLedger.class);
            glCodeList = (List<FmsGeneralLedger>) query.getResultList();
            return glCodeList;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     *
     * @param glCode
     * @return glCodeList
     */
    public List<FmsGeneralLedger> searchByGLCode(String glCode) {
        List<FmsGeneralLedger> glCodeList;
        try {
            Query query = em.createNativeQuery("SELECT GENERAL_LEDGER_ID, "
                    + "  ACCOUNT_TITLE, "
                    + "  GL_ACCOUNT_CODE, "
                    + "  STATUS "
                    + "FROM FMS_GENERAL_LEDGER "
                    + "WHERE GL_ACCOUNT_CODE LIKE '" + glCode + "%'", FmsGeneralLedger.class);
            glCodeList = (List<FmsGeneralLedger>) query.getResultList();
            return glCodeList;
        } catch (Exception e) {
            return null;
        }
    }

    public List<FmsGeneralLedger> getFmsGeneralLedgerSearchingParameterList() {
            System.out.println("inside the query");
        Query query = em.createNativeQuery("SELECT column_name  FROM user_tab_columns\n"
                + "WHERE table_name = UPPER('FMS_GENERAL_LEDGER')\n"
                + "and column_name NOT IN('COST_CENTER_ID'  ,'ACCOUNT_CATEGORY' ) ORDER BY column_name ASC");
        try {
            List<FmsGeneralLedger> searchParamLists = new ArrayList<>();
            searchParamLists = query.getResultList();
            System.out.println("inside the query == searchParamLists " + searchParamLists);
            return searchParamLists;
        } catch (Exception ex) {
            ex.getMessage();
            return null;
        }
        
    }

    public List<FmsGeneralLedger> getGeneralLagers(FmsGeneralLedger fmsGeneralLedger) {
    System.out.println("fmsVoucher.getColumnValue()===" + fmsGeneralLedger.getColumnValue());
        System.out.println("====fmsVoucher.getColumnName()===" + fmsGeneralLedger.getColumnName());
        if (fmsGeneralLedger.getColumnValue() != null && fmsGeneralLedger.getColumnName() != null
                && !fmsGeneralLedger.getColumnValue().equals("") && !fmsGeneralLedger.getColumnName().equals("")) {
            System.out.println("passd!!!");
            Query query = em.createNativeQuery("SELECT * FROM FMS_GENERAL_LEDGER\n"
                    + "where " + fmsGeneralLedger.getColumnName().toLowerCase() + " = '" + fmsGeneralLedger.getColumnValue() + "' ", FmsGeneralLedger.class);

            try {
                List<FmsGeneralLedger> FmsGeneralLedgerSearchLists = new ArrayList<>();
                if (query.getResultList().size() > 0) {
                    FmsGeneralLedgerSearchLists = query.getResultList();
                    System.out.println("==fmsVoucherSearchLists==" + FmsGeneralLedgerSearchLists);
                }
                return FmsGeneralLedgerSearchLists;
            } catch (Exception ex) {
                ex.getMessage();
                return null;
            }
        } else {
           // Query query = em.createNamedQuery("FmsVoucher.findByPreparedBy");
            //  query.setParameter("preparedby", fmsVoucher.getPreparedBy());
            //  return query.getResultList();
            Query query = em.createNamedQuery("FmsGeneralLedger.findAll");
//            query.setParameter("type", fmsGeneralLedger.getType());
            ArrayList<FmsGeneralLedger> VoucherList = new ArrayList(query.getResultList());
            System.out.println("VoucherList===" + VoucherList);
            return VoucherList;
        }
    }

}
