/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.prms.mapper;

import et.gov.eep.prms.entity.PrmsServiceProvider;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import et.gov.eep.commonApplications.mapper.AbstractFacade;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Query;

/**
 *
 * @author user
 */
@Stateless
public class PrmsServiceProviderFacade extends AbstractFacade<PrmsServiceProvider> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PrmsServiceProviderFacade() {
        super(PrmsServiceProvider.class);
    }

    // <editor-fold defaultstate="collapsed" desc="Named Queries">
    public ArrayList<PrmsServiceProvider> serviceProviderFrom() {
        Query query = em.createNamedQuery("PrmsServiceProvider.findAll");

        try {
            ArrayList<PrmsServiceProvider> fromInsurance = new ArrayList<>(query.getResultList());
            return fromInsurance;
        } catch (Exception e) {
        }
        return null;

    }

    public List<PrmsServiceProvider> searchServiceProvideByName(PrmsServiceProvider papmsServiceProvider) {
       List<PrmsServiceProvider> serviceProviderList = new ArrayList();
        if (papmsServiceProvider.getColumnName() != null && !papmsServiceProvider.getColumnName().equals("")
                && papmsServiceProvider.getColumnValue() != null && !papmsServiceProvider.getColumnValue().equals("")) {

            Query query = em.createNativeQuery("SELECT * FROM PRMS_SERVICE_PROVIDER\n"
                    + "where " + papmsServiceProvider.getColumnName().toLowerCase() + " = '" + papmsServiceProvider.getColumnValue() + "'"
                    + "and " + papmsServiceProvider.getPreparedby()+ "='" + papmsServiceProvider.getPreparedby()+ "'", PrmsServiceProvider.class);
            try {
                if (query.getResultList().size() > 0) {
                    serviceProviderList = query.getResultList();
                }
                return serviceProviderList;
            } catch (NumberFormatException nfe) {
                nfe.getMessage();
                return null;
            }
        } else {
            Query query = em.createNamedQuery("PrmsServiceProvider.findByPreparedby");
            query.setParameter("preparedby", papmsServiceProvider.getPreparedby());
            serviceProviderList = query.getResultList();
            return serviceProviderList;
        }

    }

    public PrmsServiceProvider getServiceName(PrmsServiceProvider papmsServiceProvider) {
        Query query = em.createNamedQuery("PrmsServiceProvider.findByName");
        query.setParameter("name", papmsServiceProvider.getName());

        try {
            PrmsServiceProvider selectedobj = (PrmsServiceProvider) query.getResultList().get(0);

            return selectedobj;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public PrmsServiceProvider getLastServiceNo() {

        Query query = em.createNamedQuery("PrmsServiceProvider.findByMaxServProvideNo");

        try {
            PrmsServiceProvider ServiceProd = (PrmsServiceProvider) query.getResultList().get(0);

            return ServiceProd;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<PrmsServiceProvider> searchServiceProvider(PrmsServiceProvider prmsServiceProvider) {
         List<PrmsServiceProvider> prmsServiceProviderLst = new ArrayList();
        if (prmsServiceProvider.getColumnName() != null && !prmsServiceProvider.getColumnName().equals("")
                && prmsServiceProvider.getColumnValue() != null && !prmsServiceProvider.getColumnValue().equals("")) {

            Query query = em.createNativeQuery("SELECT * FROM PRMS_SERVICE_PROVIDER\n"
                    + "where " + prmsServiceProvider.getColumnName().toLowerCase() + " = '" + prmsServiceProvider.getColumnValue() + "'"
                    + "and " + prmsServiceProvider.getPreparedby()+ "='" + prmsServiceProvider.getPreparedby()+ "'", PrmsServiceProvider.class);
            try {
                if (query.getResultList().size() > 0) {
                    prmsServiceProviderLst = query.getResultList();
                }
                return prmsServiceProviderLst;
            } catch (NumberFormatException nfe) {
                nfe.getMessage();
                return null;
            }
        } else {
            Query query = em.createNamedQuery("PrmsServiceProvider.findByPreparedby");
            query.setParameter("preparedby", prmsServiceProvider.getPreparedby());
            prmsServiceProviderLst = query.getResultList();
            return prmsServiceProviderLst;
        }
    }

    public PrmsServiceProvider getSelectedRequest(BigDecimal id) {
        Query query = em.createNamedQuery("PrmsServiceProvider.findByServiceProId");
        query.setParameter("serviceProId", id);
        try {
            PrmsServiceProvider selectrequest = (PrmsServiceProvider) query.getResultList().get(0);
            return selectrequest;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public ArrayList<PrmsServiceProvider> getserviceproviders() {

        Query query = em.createNamedQuery("PrmsServiceProvider.findByRequestForApproval");

        try {
            ArrayList<PrmsServiceProvider> poroviderList = new ArrayList(query.getResultList());

            return poroviderList;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public ArrayList<PrmsServiceProvider> serviceProviderInsuranceList() {
        Query query = em.createNamedQuery("PrmsServiceProvider.findServTypeInsurancesOnly");
        try {
            ArrayList<PrmsServiceProvider> serviceProvider = new ArrayList(query.getResultList());
            return serviceProvider;

        } catch (Exception e) {
            return null;
        }
    }

    public PrmsServiceProvider serviceProviderInfoById(PrmsServiceProvider prmsServiceProvider) {
        Query query = em.createNamedQuery("PrmsServiceProvider.findByServiceProId");
        query.setParameter("serviceProId", prmsServiceProvider.getServiceProId());
        try {
            PrmsServiceProvider serviceProviderInfo = null;
            if (query.getResultList().size() > 0) {
                serviceProviderInfo = (PrmsServiceProvider) query.getResultList().get(0);
            }
            return serviceProviderInfo;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    // </editor-fold>

    public List<PrmsServiceProvider> getParamNameList() {
       Query query = em.createNativeQuery("SELECT column_name FROM user_tab_columns \n"
                + "where table_name = UPPER('PRMS_SERVICE_PROVIDER') \n"
                + "and column_name not in ('SERVICE_PRO_ID','REMARK','REMARK','STATUS','CURRENT_STATUS')");
        try {
            List<PrmsServiceProvider> columnNameList = new ArrayList<>();
            System.out.print("g"+columnNameList.size());
            columnNameList = query.getResultList();
            return columnNameList;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }

}
