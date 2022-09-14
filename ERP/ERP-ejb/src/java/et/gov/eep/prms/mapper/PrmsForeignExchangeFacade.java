/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.prms.mapper;

import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.fcms.entity.FmsLuCurrency;
import et.gov.eep.hrms.entity.address.HrAddresses;
import et.gov.eep.mms.entity.MmsItemRegistration;
import et.gov.eep.prms.entity.PrmsForeignExchange;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author user
 */
@Stateless
public class PrmsForeignExchangeFacade extends AbstractFacade<PrmsForeignExchange> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PrmsForeignExchangeFacade() {
        super(PrmsForeignExchange.class);
    }

    //<editor-fold defaultstate="collapsed" desc="Named(Static) Queries">
    public PrmsForeignExchange findByfenumberObj(PrmsForeignExchange prmsForeignExchange) {
        Query query = em.createNamedQuery("PrmsForeignExchange.findByFeNumber");
        query.setParameter("feNumber", prmsForeignExchange.getFeNumber());
        try {
            PrmsForeignExchange fea = (PrmsForeignExchange) query.getResultList().get(0);
            return fea;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public List<PrmsForeignExchange> findByfenumber(PrmsForeignExchange prmsForeignExchange) {
       List<PrmsForeignExchange> lcRigistrationAmendList = new ArrayList();
        if (prmsForeignExchange.getColumnName() != null && !prmsForeignExchange.getColumnName().equals("")
                && prmsForeignExchange.getColumnValue() != null && !prmsForeignExchange.getColumnValue().equals("")) {

            Query query = em.createNativeQuery("SELECT * FROM PRMS_FOREIGN_EXCHANGE\n"
                    + "where " + prmsForeignExchange.getColumnName().toLowerCase() + " = '" + prmsForeignExchange.getColumnValue() + "'"
                    + "and " + prmsForeignExchange.getPreparedBy() + "='" + prmsForeignExchange.getPreparedBy() + "'", PrmsForeignExchange.class);
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
            Query query = em.createNamedQuery("PrmsForeignExchange.findByPreparedBy");
            query.setParameter("preparedBy", prmsForeignExchange.getPreparedBy());
            lcRigistrationAmendList = query.getResultList();
            return lcRigistrationAmendList;
        }
    }

//generating next FE Number method
    public PrmsForeignExchange generateNextForeignExNo() {
        Query query = em.createNamedQuery("PrmsForeignExchange.InsertNextFE_No");
        PrmsForeignExchange exchange = null;
        try {
            if (query.getResultList().size() > 0) {
                exchange = (PrmsForeignExchange) query.getResultList().get(0);
            }
            return exchange;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    //get Foreign Exchange Request Lists available For Approval
    public List<PrmsForeignExchange> getForeignExchReqlist() {
        Query query = em.createNamedQuery("PrmsForeignExchange.findByForeignExchReqForApproval", PrmsForeignExchange.class);
        ArrayList<PrmsForeignExchange> foreignExchlist = new ArrayList<>(query.getResultList());
        return foreignExchlist;
    }
    //</editor-fold>

    public List<PrmsForeignExchange> getParamNameList() {
         Query query = em.createNativeQuery("SELECT column_name FROM user_tab_columns \n"
                + "where table_name = UPPER('PRMS_FOREIGN_EXCHANGE') \n"
                + "and column_name not in ('ID','REMARK','MANUFACTURER_INFO','COMMISSION','BANK_ID','SUPP_ID','PORT_ID','STATUS','DOCUMENT_ID')");
        try {
            List<PrmsForeignExchange> columnNameList = new ArrayList<>();
            columnNameList = query.getResultList();
            return columnNameList;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }
}
