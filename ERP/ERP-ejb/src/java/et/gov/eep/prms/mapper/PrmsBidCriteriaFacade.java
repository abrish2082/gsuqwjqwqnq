/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.prms.mapper;

import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.prms.entity.PrmsBid;
import et.gov.eep.prms.entity.PrmsBidCriteria;
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
public class PrmsBidCriteriaFacade extends AbstractFacade<PrmsBidCriteria> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PrmsBidCriteriaFacade() {
        super(PrmsBidCriteria.class);
    }

    public ArrayList<PrmsBid> getBidNO() {
        Query query = em.createNamedQuery("PrmsBid.findAll");
        try {
            ArrayList<PrmsBid> prmsBidList = new ArrayList(query.getResultList());
            return prmsBidList;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;

        }
    }

    public List<PrmsBidCriteria> getCriteriaNo(PrmsBidCriteria prmsBidCriteria) {
        List<PrmsBidCriteria> bidCriterialist = new ArrayList();
        if (prmsBidCriteria.getColumnName() != null && !prmsBidCriteria.getColumnName().equals("")
                && prmsBidCriteria.getColumnValue() != null && !prmsBidCriteria.getColumnValue().equals("")) {

            Query query = em.createNativeQuery("SELECT * FROM prms_bid_criteria\n"
                    + "where " + prmsBidCriteria.getColumnName().toLowerCase() + " = '" + prmsBidCriteria.getColumnValue() + "'"
                    + "and " + prmsBidCriteria.getPreparedBy() + "='" + prmsBidCriteria.getPreparedBy() + "'", PrmsBidCriteria.class);
            try {
                if (query.getResultList().size() > 0) {
                    bidCriterialist = query.getResultList();
                }
                return bidCriterialist;
            } catch (NumberFormatException nfe) {
                nfe.getMessage();
                return null;
            }
        } else {
            Query query = em.createNamedQuery("PrmsBidCriteria.findByPreparedBy");
            query.setParameter("preparedBy", prmsBidCriteria.getPreparedBy());
            bidCriterialist = query.getResultList();
            return bidCriterialist;
        }
    }

    public PrmsBidCriteria getBidCriteriaId(String id) {
        Query query = em.createNamedQuery("PrmsBidCriteria.findById");
        query.setParameter("id", id);
        try {
            PrmsBidCriteria idlst = (PrmsBidCriteria) query.getResultList().get(0);
            return idlst;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public PrmsBidCriteria getCiteriaNo() {
        Query query = em.createNamedQuery("PrmsBidCriteria.findCriteriaNoAuto");

        try {
            PrmsBidCriteria directPurcObj = (PrmsBidCriteria) query.getResultList().get(0);

            return directPurcObj;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<PrmsBidCriteria> getParamNameList() {
        Query query = em.createNativeQuery("SELECT column_name FROM user_tab_columns \n"
                + "where table_name = UPPER('PRMS_BID_CRITERIA') \n"
                + "and column_name not in ('ID','BID_ID','STATUS','REAMARK')");
        try {
            List<PrmsBidCriteria> columnNameList = new ArrayList<>();
            columnNameList = query.getResultList();
            return columnNameList;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }
}
