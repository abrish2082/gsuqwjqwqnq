/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.prms.mapper;

import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.fcms.entity.FmsLuCurrency;
import et.gov.eep.prms.entity.PrmsServiceProvider;
import et.gov.eep.prms.entity.PrmsInsuranceRequisition;
import et.gov.eep.prms.entity.PrmsServiceProviderDetail;
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
public class PrmsInsuranceregistrationFacade extends AbstractFacade<PrmsInsuranceRequisition> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PrmsInsuranceregistrationFacade() {
        super(PrmsInsuranceRequisition.class);
    }

    // <editor-fold defaultstate="collapsed" desc="Named(Static) Queries">
    public List<PrmsInsuranceRequisition> searchByInsuranceNo(PrmsInsuranceRequisition insuranceregistration) {
        List<PrmsInsuranceRequisition> prmsInsuranceRequisitionList = new ArrayList();
        if (insuranceregistration.getColumnName() != null && !insuranceregistration.getColumnName().equals("")
                && insuranceregistration.getColumnValue() != null && !insuranceregistration.getColumnValue().equals("")) {

            Query query = em.createNativeQuery("SELECT * FROM PRMS_INSURANCE_REQUISITION\n"
                    + "where " + insuranceregistration.getColumnName().toLowerCase() + " = '" + insuranceregistration.getColumnValue() + "'"
                    + "and " + insuranceregistration.getPreparedBy() + "='" + insuranceregistration.getPreparedBy() + "'", PrmsInsuranceRequisition.class);
            try {
                if (query.getResultList().size() > 0) {
                    prmsInsuranceRequisitionList = query.getResultList();
                }
                return prmsInsuranceRequisitionList;
            } catch (NumberFormatException nfe) {
                nfe.getMessage();
                return null;
            }
        } else {
            Query query = em.createNamedQuery("PrmsInsuranceRequisition.findByPreparedBy");
            query.setParameter("preparedBy", insuranceregistration.getPreparedBy());
            prmsInsuranceRequisitionList = query.getResultList();
            return prmsInsuranceRequisitionList;
        
        }
    }

    public PrmsInsuranceRequisition getSelectedRow(String insuranceNo) {
        Query query = em.createNamedQuery("PrmsInsuranceRequisition.findForRowSelect");
        query.setParameter("insuranceNo", insuranceNo);
        try {
            PrmsInsuranceRequisition insuranceList = (PrmsInsuranceRequisition) query.getResultList().get(0);
            return insuranceList;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public List<PrmsInsuranceRequisition> generateNextInsuranceNo(String prefix, String eYear) {
        Query query = em.createNamedQuery("PrmsInsuranceRequisition.findByInsuranceNos");
        query.setParameter("insuranceNo", prefix + "-" + '%' + "/" + eYear);
        List<PrmsInsuranceRequisition> insuNoList = new ArrayList<>();
        try {
            if (query.getResultList().size() > 0) {
                insuNoList = query.getResultList();
            }
            return insuNoList;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    public List<PrmsInsuranceRequisition> getInsuRequestLists() {
        Query query = em.createNamedQuery("PrmsInsuranceRequisition.findByInsuranceForApproval", PrmsInsuranceRequisition.class);
        ArrayList<PrmsInsuranceRequisition> insuranceRequestList = new ArrayList(query.getResultList());
        return insuranceRequestList;
    }
    // </editor-fold>

    public List<PrmsInsuranceRequisition> getParamNameList() {
         Query query = em.createNativeQuery("SELECT column_name FROM user_tab_columns \n"
                + "where table_name = UPPER('PRMS_INSURANCE_REQUISITION') \n"
                + "and column_name not in ('INSURANCE_REG_ID','SERVICE_PRO_ID','EXCHANGE_RATE','DESCRIPTION_OF_GOOD','MARKS_AND_NOS','COVER_REQUIRED','REMARK','SERVICE_DT_ID','HR_DEPT_ID','FILE_REF_NUMBER','PREMIUM_DUE','STATUS')");
        try {
            List<PrmsInsuranceRequisition> columnNameList = new ArrayList<>();
            columnNameList = query.getResultList();
            return columnNameList;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }

}
