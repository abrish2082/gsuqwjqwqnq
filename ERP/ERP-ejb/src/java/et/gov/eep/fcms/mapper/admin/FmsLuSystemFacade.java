/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.mapper.admin;

import java.util.ArrayList;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.fcms.entity.Vocher.FmsVoucher;
import et.gov.eep.fcms.entity.admin.FmsCostCenter;
import et.gov.eep.fcms.entity.admin.FmsLuSystem;
import java.util.List;

/**
 *
 * @author pc
 */
@Stateless
public class FmsLuSystemFacade extends AbstractFacade<FmsLuSystem> {

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
    public FmsLuSystemFacade() {
        super(FmsLuSystem.class);
    }

    /**
     *
     * @param sysname
     * @return sysNameListList
     */
    
      public List<FmsLuSystem> getFmsLuSystemSearchingParameterList() {
        Query query = em.createNativeQuery("SELECT column_name  FROM user_tab_columns\n"
                + "WHERE table_name = UPPER('FMS_SYSTEM')\n"
                + "and column_name NOT IN('FMS_SYSTEM'   ) ORDER BY column_name ASC");
        try {
            List<FmsLuSystem> searchParamLists = new ArrayList<>();
            searchParamLists = query.getResultList();
            return searchParamLists;
        } catch (Exception ex) {
            ex.getMessage();
            return null;
        }
    }
      
        public List<FmsLuSystem> getVoch1No(FmsLuSystem fmsLuSystem) {
        System.out.println("fmsVoucher.getColumnValue()===" + fmsLuSystem.getColumnValue());
        System.out.println("====fmsVoucher.getColumnName()===" + fmsLuSystem.getColumnName());
        if (fmsLuSystem.getColumnValue() != null && fmsLuSystem.getColumnName() != null
                && !fmsLuSystem.getColumnValue().equals("") && !fmsLuSystem.getColumnName().equals("")) {
            System.out.println("passd!!!");
            Query query = em.createNativeQuery("SELECT * FROM FMS_SYSTEM\n"
                    + "where " + fmsLuSystem.getColumnName().toLowerCase() + " = '" + fmsLuSystem.getColumnValue() + "' ", FmsLuSystem.class);

            try {
                List<FmsLuSystem> fmsVoucherSearchLists = new ArrayList<>();
                if (query.getResultList().size() > 0) {
                    fmsVoucherSearchLists = query.getResultList();
                    System.out.println("==fmsVoucherSearchLists==" + fmsVoucherSearchLists);
                }
                return fmsVoucherSearchLists;
            } catch (Exception ex) {
                ex.getMessage();
                return null;
            }
        } else {
           // Query query = em.createNamedQuery("FmsVoucher.findByPreparedBy");
            //  query.setParameter("preparedby", fmsVoucher.getPreparedBy());
            //  return query.getResultList();
            Query query = em.createNamedQuery("FmsLuSystem.findAll");
//            query.setParameter("type", fmsLuSystem.getType());
            ArrayList<FmsLuSystem> VoucherList = new ArrayList(query.getResultList());
            System.out.println("VoucherList===" + VoucherList);
            return VoucherList;
        }

    }
      
    public FmsLuSystem getSystem(FmsLuSystem sysname) {
        Query query = em.createNamedQuery("FmsLuSystem.findBySystemCode");
        query.setParameter("systemCode", sysname.getSystemCode());
        try {
            FmsLuSystem sysNameListList = (FmsLuSystem) query.getResultList().get(0);
            return sysNameListList;

        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     *
     * @param sysname
     * @return sysNameListList
     */
    public FmsLuSystem getActiveSystem(FmsLuSystem sysname) {
        int status = 1;
        Query query = em.createNamedQuery("FmsLuSystem.findBySystemCode");
        query.setParameter("systemCode", sysname.getSystemCode());
        query.setParameter("status", status);
        try {
            FmsLuSystem sysNameListList = (FmsLuSystem) query.getResultList().get(0);
            return sysNameListList;

        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     *
     * @param luSystemEntity
     * @return getSysName
     */
    public FmsLuSystem findBySytemCode2(FmsLuSystem luSystemEntity) {
        int sysId = Integer.parseInt(luSystemEntity.getSystemCode());
        Query query = em.createNamedQuery("FmsLuSystem.findBySystemCode");
        query.setParameter("systemCode", luSystemEntity.getSystemCode());
        try {
            FmsLuSystem getSysName = new FmsLuSystem();
            if (query.getResultList().size() > 0) {
                getSysName = (FmsLuSystem) query.getResultList().get(0);
            }
            return getSysName;

        } catch (Exception ex) {
            return null;
        }
    }

    /**
     *
     * @param fmsLuSystem
     * @return fmsLuSystem by system id
     */
    public FmsLuSystem findBySytemId(int sysId) {
        FmsLuSystem fmsLuSystem;
        try {
            Query query = em.createNamedQuery("FmsLuSystem.findBySystemId", FmsLuSystem.class);
            query.setParameter("systemId", sysId);
            fmsLuSystem = (FmsLuSystem) query.getSingleResult();
            return fmsLuSystem;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     *
     * @param fmsLuSystem
     * @return sysNameListList
     */
    public FmsLuSystem getSystembyId(FmsLuSystem fmsLuSystem) {
        Query query = em.createNamedQuery("FmsLuSystem.findBySystemCode");
        query.setParameter("systemCode", fmsLuSystem.getSystemCode());
        try {
            if (query.getResultList().size() > 0) {

                FmsLuSystem sysNameListList = (FmsLuSystem) query.getResultList().get(0);
                return sysNameListList;
            } else {
                FmsLuSystem sysNameListList = new FmsLuSystem();
                return sysNameListList;
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     *
     * @param fmsCostCenter
     * @return bankAccList
     */
    public ArrayList<FmsCostCenter> getCosTCenter(FmsCostCenter fmsCostCenter) {
        Query query = em.createNamedQuery("FmsCostCenter.findBySystemId");
        query.setParameter("systemId", fmsCostCenter.getSystemId());
        try {
            ArrayList<FmsCostCenter> bankAccList = new ArrayList(query.getResultList());
            return bankAccList;

        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }

    }

    /**
     * @return projSysList
     */
    public ArrayList<FmsLuSystem> findProjSystem() {
        Query query = em.createNamedQuery("FmsLuSystem.findByProjSystem");
        try {
            ArrayList<FmsLuSystem> projSysList = new ArrayList(query.getResultList());
            return projSysList;
        } catch (Exception ex) {
            return null;
        }

    }

    /**
     * @return oprSysList
     */
    public ArrayList<FmsLuSystem> findOprSystem() {
        Query query = em.createNamedQuery("FmsLuSystem.findByOprSystem");
        try {
            ArrayList<FmsLuSystem> oprSysList = new ArrayList(query.getResultList());
            return oprSysList;

        } catch (Exception ex) {
            return null;
        }

    }

    /**
     * @return account List
     */
    public ArrayList<FmsLuSystem> getActiveSystem() {
        Query query = em.createNamedQuery("FmsLuSystem.findAll");
        try {
            ArrayList<FmsLuSystem> AccList = new ArrayList(query.getResultList());
            return AccList;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }

    }

    /**
     *
     * @return account list
     */
    public ArrayList<FmsLuSystem> getActiveSystemForCapitalBgt() {
        Query query = em.createNamedQuery("FmsLuSystem.findAll");
        try {
            ArrayList<FmsLuSystem> AccList = new ArrayList(query.getResultList());
            return AccList;

        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }

    }

    /**
     * @param luSystemEntity
     * @return sysNameList
     */
    public ArrayList<FmsLuSystem> findBySytemCode(FmsLuSystem luSystemEntity) {
        Query query = em.createNamedQuery("FmsLuSystem.findBySystemCode");
        query.setParameter("systemCode", luSystemEntity.getSystemCode() + '%');
        try {
            ArrayList<FmsLuSystem> sysNameList = new ArrayList(query.getResultList());
            return sysNameList;
        } catch (Exception ex) {
            throw ex;
        }
    }

    /**
     * @param luSystemEntity
     * @return sysNameList
     */
    public ArrayList<FmsLuSystem> findBySytemCodeLike(FmsLuSystem luSystemEntity) {
        Query query = em.createNamedQuery("FmsLuSystem.findBySystemCodeLike");
        query.setParameter("systemCode", luSystemEntity.getSystemCode() + '%');
        try {
            ArrayList<FmsLuSystem> sysNameList = new ArrayList(query.getResultList());
            return sysNameList;
        } catch (Exception ex) {
            throw ex;
        }
    }

}
