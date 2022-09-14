/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.prms.mapper;

import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.prms.entity.PrmsInsuranceNotToBank;
import et.gov.eep.prms.entity.PrmsInsuranceRequisition;
import et.gov.eep.prms.entity.PrmsPortEntry;
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
public class PrmsInsuranceNotToBankFacade extends AbstractFacade<PrmsInsuranceNotToBank> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PrmsInsuranceNotToBankFacade() {
        super(PrmsInsuranceNotToBank.class);
    }

    public PrmsInsuranceNotToBank generateNotificationNoSequence() {
        System.out.println("calling Bank Notification No Sequentially");
        Query query = em.createNamedQuery("PrmsInsuranceNotToBank.seqBankNotificationNo");
        PrmsInsuranceNotToBank NotificationNo = null;
        try {
            if (query.getResultList().size() > 0) {
                NotificationNo = (PrmsInsuranceNotToBank) query.getResultList().get(0);
            }
            return NotificationNo;

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public ArrayList<PrmsInsuranceNotToBank> toBank() {
        System.out.println("inside of to bank");
        Query query = em.createNamedQuery("PrmsServiceProvider.findAllBanksOnly");

        try {
            ArrayList<PrmsInsuranceNotToBank> serviceProvider = new ArrayList(query.getResultList());
            System.out.println("Banks in Number==" + serviceProvider.size());
            return serviceProvider;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public ArrayList<PrmsInsuranceNotToBank> bankBranch() {
        System.out.println("inside of bank branch");
        Query query = em.createNamedQuery("PrmsServiceProviderDetail.findAll");

        try {
            ArrayList<PrmsInsuranceNotToBank> branch = new ArrayList(query.getResultList());
            System.out.println("hhhh" + branch.size());
            return branch;

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

        public ArrayList<PrmsPortEntry> portVoyages() {
        System.out.println("inside of voyage from,via & to");
        Query query = em.createNamedQuery("PrmsPortEntry.findAllByOrder");

        try {
            ArrayList<PrmsPortEntry> voyages = new ArrayList<>(query.getResultList());
            System.out.println("from size==" + voyages.size());
            return voyages;
        } catch (Exception e) {
        }
        return null;
    }

    public ArrayList<PrmsInsuranceNotToBank> forInsuranceNo() {
        System.out.println("inside of for Insurance No method");
        Query query = em.createNamedQuery("PrmsInsuranceRequisition.findAll");

        try {
            ArrayList<PrmsInsuranceNotToBank> insuranceNo = new ArrayList<>(query.getResultList());
            System.out.println("nnnn==" + insuranceNo.size());
            return insuranceNo;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<PrmsInsuranceNotToBank> searchByNoificationNo(PrmsInsuranceNotToBank prmsInsuranceNotToBank) {
         List<PrmsInsuranceNotToBank> insuranceNotToBankList = new ArrayList();
        if (prmsInsuranceNotToBank.getColumnName() != null && !prmsInsuranceNotToBank.getColumnName().equals("")
                && prmsInsuranceNotToBank.getColumnValue() != null && !prmsInsuranceNotToBank.getColumnValue().equals("")) {

            Query query = em.createNativeQuery("SELECT * FROM PRMS_INSURANCE_NOT_TO_BANK\n"
                    + "where " + prmsInsuranceNotToBank.getColumnName().toLowerCase() + " = '" + prmsInsuranceNotToBank.getColumnValue() + "'"
                    + "and " + prmsInsuranceNotToBank.getPrepearedBy()+ "='" + prmsInsuranceNotToBank.getPrepearedBy()+ "'", PrmsInsuranceNotToBank.class);
            try {
                if (query.getResultList().size() > 0) {
                    insuranceNotToBankList = query.getResultList();
                }
                return insuranceNotToBankList;
            } catch (NumberFormatException nfe) {
                nfe.getMessage();
                return null;
            }
        } else {
            Query query = em.createNamedQuery("PrmsInsuranceNotToBank.findByPrepearedBy");
            query.setParameter("prepearedBy", prmsInsuranceNotToBank.getPrepearedBy());
            insuranceNotToBankList = query.getResultList();
            return insuranceNotToBankList;
        }
    }

    public PrmsInsuranceRequisition getOtherAttributes(PrmsInsuranceRequisition insuranceregistration) {
        System.out.println("getting other attributes values");
        Query query = em.createNamedQuery("PrmsInsuranceRequisition.findByInsuranceNos");
        query.setParameter("insuranceNo", insuranceregistration.getInsuranceNo());
        try {
            PrmsInsuranceRequisition policyNoAndOthers = (PrmsInsuranceRequisition) (query.getResultList().get(0));
            return policyNoAndOthers;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public PrmsInsuranceNotToBank getSelectedRow(String insuranceNotificationNo) {
        System.out.println("inside row Selection");
        Query query = em.createNamedQuery("PrmsInsuranceNotToBank.findForRowSelect");
        query.setParameter("insuranceNotificationNo", insuranceNotificationNo);
        try {
            PrmsInsuranceNotToBank notificationList = (PrmsInsuranceNotToBank) query.getResultList().get(0);
            return notificationList;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public List<PrmsInsuranceNotToBank> getParamNameList() {
        Query query = em.createNativeQuery("SELECT column_name FROM user_tab_columns \n"
                + "where table_name = UPPER('PRMS_INSURANCE_NOT_TO_BANK') \n"
                + "and column_name not in ('INSURANCE_NOTIFICATION_ID','RISKS','INSURANCE_REG_ID','SERVICE_PRO_ID','SERVICE_DT_ID','PORT_ID','REMARK')");
        try {
            List<PrmsInsuranceNotToBank> columnNameList = new ArrayList<>();
            columnNameList = query.getResultList();
            return columnNameList;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }
}
