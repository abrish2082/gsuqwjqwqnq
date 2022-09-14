/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.prms.mapper;

import et.gov.eep.commonApplications.entity.ColumnNameResolver;
import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.fcms.entity.FmsLuCurrency;
import et.gov.eep.prms.entity.PrmsRevenueContarct;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author mora1
 */
@Stateless
public class PrmsRevenueContarctFacade extends AbstractFacade<PrmsRevenueContarct> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PrmsRevenueContarctFacade() {
        super(PrmsRevenueContarct.class);
    }

    // <editor-fold defaultstate="collapsed" desc="Named(Static) Queries">
    public List<FmsLuCurrency> currencyNameLists() {
        Query query = em.createNamedQuery("FmsLuCurrency.findAll");
        try {
            List<FmsLuCurrency> currLists = new ArrayList<>();
            if (query.getResultList().size() > 0) {
                currLists = query.getResultList();
            }
            return currLists;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<PrmsRevenueContarct> searchAllRevContract() {
        Query query = em.createNamedQuery("PrmsRevenueContarct.findAll");
        try {
            List<PrmsRevenueContarct> allRevContList = new ArrayList<>();
            if (query.getResultList().size() > 0) {
                allRevContList = query.getResultList();
                System.out.println("hhh " + allRevContList.size());
            }
            return allRevContList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<PrmsRevenueContarct> getRevContSeqNo(String prefix, String ethYear) {
        Query query = em.createNamedQuery("PrmsRevenueContarct.findByRevNoLike");
        query.setParameter("revContractNo", prefix + "-" + "%" + "/" + ethYear);
        List<PrmsRevenueContarct> seqNoLists = new ArrayList<>();
        if (query.getResultList().size() > 0) {
            seqNoLists = query.getResultList();
        }
        return seqNoLists;
    }
    // </editor-fold>

    public List<ColumnNameResolver> findColumns() {
        Query query = em.createNativeQuery("SELECT column_name  FROM user_tab_columns\n"
                + "WHERE table_name = UPPER('PRMS_REVENUE_CONTARCT')\n"
                + "and column_name NOT IN('ID','ESTIMATED_TOTAL_COST','CONTRACT_DURATION','PAYMENT_AMOUNT','STATUS','CONTRACT_DESCRRIPTION',\n"
                + "'CURRENCY_ID','GL_ID','SUBSIDIARY_ID')\n "
                + "ORDER BY column_name ASC");
        try {
            List<String> RetrivedColumns = new ArrayList<>();
            RetrivedColumns = query.getResultList();
            List<ColumnNameResolver> ResolvedCol_list = new ArrayList<>();
            if (RetrivedColumns.size() > 0) {
                for (int i = 0; i < RetrivedColumns.size(); i++) {
                    ColumnNameResolver obj = new ColumnNameResolver();
                    obj.setCol_Name_FromTable(RetrivedColumns.get(i));
                    obj.setParsed_Col_Name(ColumnParser(RetrivedColumns.get(i)).toLowerCase());
                    ResolvedCol_list.add(obj);
                }
            }
            return ResolvedCol_list;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public String ColumnParser(String col_name) {
        String col = col_name.replace("_", " ");
        return col;
    }

    public ArrayList<PrmsRevenueContarct> searchByCol_NameAndCol_Value(ColumnNameResolver columnNameResolver, String columnValue) {
        if (columnNameResolver.getCol_Name_FromTable() != null && columnValue != null) {
            Query query = em.createNativeQuery("SELECT * FROM PRMS_REVENUE_CONTARCT\n"
                    + "where " + columnNameResolver.getCol_Name_FromTable().toLowerCase() + " LIKE'" + columnValue + "%'  ", PrmsRevenueContarct.class);
            try {
                ArrayList<PrmsRevenueContarct> revenuContract = new ArrayList(query.getResultList());
                return revenuContract;
            } catch (Exception ex) {
                ex.printStackTrace();
                return null;
            }
        } else {
            return null;
        }
    }

}
