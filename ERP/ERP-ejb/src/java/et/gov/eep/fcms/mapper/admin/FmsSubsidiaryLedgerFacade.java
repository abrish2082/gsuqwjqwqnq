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
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.fcms.entity.admin.FmsCostcSystemJunction;
import et.gov.eep.fcms.entity.admin.FmsGeneralLedger;
import et.gov.eep.fcms.entity.admin.FmsSubsidiaryLedger;
import et.gov.eep.mms.entity.IfrsFixedAsset;
import et.gov.eep.ifrs.entity.IfrsOptionValues;

/**
 *
 * @author user
 */
@Stateless
public class FmsSubsidiaryLedgerFacade extends AbstractFacade<FmsSubsidiaryLedger> {

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
    public FmsSubsidiaryLedgerFacade() {
        super(FmsSubsidiaryLedger.class);
    }

    /**
     *
     * @param subsidary
     * @return subsidaryList
     */
    public ArrayList<FmsSubsidiaryLedger> searchSubsidaryByCode(FmsSubsidiaryLedger subsidary) {
        Query query = em.createNamedQuery("FmsSubsidiaryLedger.findBySubsidiaryCodeLike");
        query.setParameter("subsidiaryCode", '%' + subsidary.getSubsidiaryCode() + '%');
        try {
            ArrayList<FmsSubsidiaryLedger> subsidaryList = new ArrayList(query.getResultList());
            return subsidaryList;

        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     *
     * @param subsidary
     * @return subsidaryList
     */
    public ArrayList<FmsSubsidiaryLedger> searchSubsidiaryByName(String subsidary) {
        Query query = em.createNamedQuery("FmsSubsidiaryLedger.findBySubsidiaryCodeLike");
        query.setParameter("subsidiaryCode", subsidary + '%');
        try {
            ArrayList<FmsSubsidiaryLedger> subsidaryList = new ArrayList(query.getResultList());
            return subsidaryList;

        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     *
     * @param fmsGeneralLedger
     * @return sublList
     */
    public ArrayList<FmsSubsidiaryLedger> findSubsidiaryName(FmsGeneralLedger fmsGeneralLedger) {
        Query query = em.createNamedQuery("FmsSubsidiaryLedger.findByGeneralLedgerId");
        query.setParameter("generalLedgerId", fmsGeneralLedger);
        try {
            ArrayList<FmsSubsidiaryLedger> sublList = new ArrayList(query.getResultList());

            return sublList;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     *
     * @param fmsCostCenter
     * @param fmsGeneralLedger
     * @return sublList
     */
    public ArrayList<FmsSubsidiaryLedger> findSLbyGLandCCSS(FmsGeneralLedger fmsGeneralLedger, FmsCostcSystemJunction costcSystemJunction) {
        Query query = em.createNamedQuery("FmsSubsidiaryLedger.findByGeneralLedgerCCSS");
        query.setParameter("generalLedgerId", fmsGeneralLedger);
        query.setParameter("ssCcJunction", costcSystemJunction);
        try {
            ArrayList<FmsSubsidiaryLedger> sublList = new ArrayList(query.getResultList());

            return sublList;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     *
     * @param fmsSubsidiaryLedger
     * @return sublList
     */
    public ArrayList<FmsSubsidiaryLedger> searchSubsidiaries(FmsSubsidiaryLedger fmsSubsidiaryLedger) {
        Query query = em.createNamedQuery("FmsSubsidiaryLedger.findBySubsidiaryCodeLike");
        query.setParameter("subsidiaryCode", fmsSubsidiaryLedger.getSubsidiaryCode() + '%');
        try {
            ArrayList<FmsSubsidiaryLedger> sublList = new ArrayList(query.getResultList());
            return sublList;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     *
     * @param glCode
     * @return sublList
     */
    public ArrayList<FmsSubsidiaryLedger> findSubsideryCodeByGlCode(FmsGeneralLedger glCode) {
        Query query = em.createNamedQuery("FmsSubsidiaryLedger.findByGeneralLedgerId");
        query.setParameter("generalLedgerId", glCode);
        try {
            ArrayList<FmsSubsidiaryLedger> sublList = new ArrayList(query.getResultList());

            return sublList;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     *
     * @param subsidary
     * @return operatingBudgetInfo by subsidiary id
     */
    public FmsSubsidiaryLedger getSubsidaryInfo(FmsSubsidiaryLedger subsidary) {
        Query query = em.createNamedQuery("FmsSubsidiaryLedger.findBySubsidiaryId");
        query.setParameter("subsidiaryId", subsidary.getSubsidiaryId());
        try {
            FmsSubsidiaryLedger operatingBudgetInfo = null;
            if (query.getResultList().size() > 0) {
                operatingBudgetInfo = (FmsSubsidiaryLedger) query.getResultList().get(0);
            }
            return operatingBudgetInfo;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     *
     * @param subsidary
     * @return operatingBudgetInfo by subsidiary code
     */
    public FmsSubsidiaryLedger getSubsidary(FmsSubsidiaryLedger subsidary) {
        Query query = em.createNamedQuery("FmsSubsidiaryLedger.findBySubsidiaryCode");
        query.setParameter("subsidiaryCode", subsidary.getSubsidiaryCode());
        try {
            FmsSubsidiaryLedger operatingBudgetInfo = null;
            if (query.getResultList().size() > 0) {
                operatingBudgetInfo = (FmsSubsidiaryLedger) query.getResultList().get(0);
            }
            return operatingBudgetInfo;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     *
     * @param fmsSubsidiaryLedger
     * @return fmsSubsidiaryLedger by subsidiary code
     */
    public FmsSubsidiaryLedger getSlCode(FmsSubsidiaryLedger fmsSubsidiaryLedger) {
        Query query = em.createNamedQuery("FmsSubsidiaryLedger.findBySubsidiaryCode");
        query.setParameter("subsidiaryCode", fmsSubsidiaryLedger.getSubsidiaryCode());
        try {
            FmsSubsidiaryLedger fmsSubsidiaryLedger1 = new FmsSubsidiaryLedger();
            if (query.getResultList().size() > 0) {
                fmsSubsidiaryLedger1 = (FmsSubsidiaryLedger) query.getResultList().get(0);
            }
            return fmsSubsidiaryLedger1;
        } catch (Exception ex) {
            return null;
        }
    }

    /**
     *
     * @param findByCode
     * @return fixed asset group
     */
    public FmsSubsidiaryLedger getSubsidiaryCode(Integer findByCode) {
        em.getEntityManagerFactory().getCache().evictAll();
        FmsSubsidiaryLedger fixedassetgroup;
        try {
            Query query = em.createNamedQuery("FmsSubsidiaryLedger.findBySubsidiaryId", IfrsOptionValues.class);
            query.setParameter("subsidiaryId", findByCode);
            fixedassetgroup = (FmsSubsidiaryLedger) query.getSingleResult();
            return fixedassetgroup;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     *
     * @param glCode
     * @return FmsSubsidiaryLedger
     */
    public FmsSubsidiaryLedger findSubsideryCodeByGlCode1(FmsGeneralLedger glCode) {
        Query query = em.createNamedQuery("FmsSubsidiaryLedger.findByGeneralLedgerId");
        query.setParameter("generalLedgerId", glCode.getGeneralLedgerId());

        FmsSubsidiaryLedger result = new FmsSubsidiaryLedger();
        try {
            if (query.getResultList().size() > 0) {
                result = (FmsSubsidiaryLedger) query.getResultList().get(0);
            }
            return result;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     * @param fmsSubsidiaryLedger
     * @return subsidiary list
     */
    public FmsSubsidiaryLedger findById(int fmsSubsidiaryLedger) {
        Query query = em.createNamedQuery("FmsSubsidiaryLedger.findBySubsidiaryId");
        query.setParameter("subsidiaryId", fmsSubsidiaryLedger);
        try {
            FmsSubsidiaryLedger sublList = (FmsSubsidiaryLedger) (query.getResultList().get(0));
            return sublList;
        } catch (Exception ex) {
            return null;
        }
    }

    /**
     *
     * @return subsidiary
     */
    public FmsSubsidiaryLedger getLastSubsidary() {
        Query query = em.createNativeQuery("SELECT * FROM FMS_SUBSIDIARY_LEDGER SL WHERE SL.SUBSIDIARY_ID = (SELECT MAX(SL.SUBSIDIARY_ID) FROM FMS_SUBSIDIARY_LEDGER SL)", FmsSubsidiaryLedger.class);
        try {
            FmsSubsidiaryLedger subsidary = null;
            if (query.getResultList().size() > 0) {
                subsidary = (FmsSubsidiaryLedger) query.getResultList().get(0);
            }
            return subsidary;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     * @param fmsCostcSystemJunction
     * @param fmsGeneralLedger
     * @return subsidiary ledger list
     */
    public List<FmsSubsidiaryLedger> findBySsCcJuncAndGL(FmsCostcSystemJunction fmsCostcSystemJunction, FmsGeneralLedger fmsGeneralLedger) {
        try {
            Query query1 = em.createNativeQuery("SELECT * "
                    + "FROM FMS_SUBSIDIARY_LEDGER FSL "
                    + " WHERE FSL.SS_CC_JUNCTION ='" + fmsCostcSystemJunction.getId() + "'"
                    + " AND FSL.GENERAL_LEDGER_ID='" + fmsGeneralLedger.getGeneralLedgerId() + "'", FmsSubsidiaryLedger.class);
            return (List<FmsSubsidiaryLedger>) query1.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * @param glId
     * @return IfrsFixedAsset list
     */
    public List<IfrsFixedAsset> getSLListByGlId(int glId) {
        try {
            Query query1 = em.createNativeQuery("SELECT * FROM IFRS_FIXED_ASSET ifa "
                    + "INNER JOIN "
                    + "FMS_SUBSIDIARY_LEDGER fmsl "
                    + "ON ifa.SUBSIDIARY_ID=fmsl.SUBSIDIARY_ID "
                    + "WHERE fmsl.GENERAL_LEDGER_ID = '" + glId + "' ", IfrsFixedAsset.class);
            return (List<IfrsFixedAsset>) query1.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    public List<FmsSubsidiaryLedger> getFmsSubsidiaryLedgerSearchingParameterList() {
        Query query = em.createNativeQuery("SELECT column_name  FROM user_tab_columns\n"
                + "WHERE table_name = UPPER('FMS_SUBSIDIARY_LEDGER')\n"
                + "and column_name NOT IN('SUBSIDIARY_ID' ,'DEBIT','CREDIT' ,'EMP_ID','JOBID' , 'SS_CC_JUNCTION' ) ORDER BY column_name ASC");
        try {
            List<FmsSubsidiaryLedger> searchParamLists = new ArrayList<>();
            searchParamLists = query.getResultList();
            System.out.println("searchParamLists===" + searchParamLists);
            return searchParamLists;
        } catch (Exception ex) {
            ex.getMessage();
            return null;
        }
    }

    public List<FmsSubsidiaryLedger> getVoch1No(FmsSubsidiaryLedger fmsSubsidiaryLedger) {
        System.out.println("fmsVoucher.getColumnValue()===" + fmsSubsidiaryLedger.getColumnValue());
        System.out.println("====fmsVoucher.getColumnName()===" + fmsSubsidiaryLedger.getColumnName());
        if (fmsSubsidiaryLedger.getColumnValue() != null && fmsSubsidiaryLedger.getColumnName() != null
                && !fmsSubsidiaryLedger.getColumnValue().equals("") && !fmsSubsidiaryLedger.getColumnName().equals("")) {
            System.out.println("passd!!!");
            Query query = em.createNativeQuery("SELECT * FROM FMS_SUBSIDIARY_LEDGER\n"
                    + "where " + fmsSubsidiaryLedger.getColumnName().toLowerCase() + " = '" + fmsSubsidiaryLedger.getColumnValue() + "' ", FmsSubsidiaryLedger.class);

            try {
                List<FmsSubsidiaryLedger> fmsSubsidiaryLedgerSearchLists = new ArrayList<>();
                if (query.getResultList().size() > 0) {
                    fmsSubsidiaryLedgerSearchLists = query.getResultList();
                    System.out.println("==fmsCostCenterSearchLists==" + fmsSubsidiaryLedgerSearchLists);
                }
                return fmsSubsidiaryLedgerSearchLists;
            } catch (Exception ex) {
                ex.getMessage();
                return null;
            }
        } else {
            // Query query = em.createNamedQuery("FmsVoucher.findByPreparedBy");
            //  query.setParameter("preparedby", fmsVoucher.getPreparedBy());
            //  return query.getResultList();
            Query query = em.createNamedQuery("FmsSubsidiaryLedger.findAll");
//            query.setParameter("type", fmsLuSystem.getType());
            ArrayList<FmsSubsidiaryLedger> FmsSubsidiaryLedgerList = new ArrayList(query.getResultList());
            System.out.println("VoucherList===" + FmsSubsidiaryLedgerList);
            return FmsSubsidiaryLedgerList;
        }
    }

}
