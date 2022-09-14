/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.prms.mapper;

import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.prms.entity.PrmsBankClearance;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class PrmsBankClearanceFacade extends AbstractFacade<PrmsBankClearance> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PrmsBankClearanceFacade() {
        super(PrmsBankClearance.class);
    }

    // <editor-fold defaultstate="collapsed" desc="Named(Static) Queries">
    public PrmsBankClearance getClearanceNo(PrmsBankClearance bankClearanceRegistration) {
        Query query = em.createNamedQuery("PrmsBankClearance.findByClearanceNo");
        query.setParameter("clearanceNo", bankClearanceRegistration.getClearanceNo());
        try {
            PrmsBankClearance selectedobj = (PrmsBankClearance) query.getResultList().get(0);

            return selectedobj;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public PrmsBankClearance getLastClearanceNo() {
        Query query = em.createNamedQuery("PrmsBankClearance.generateByMaxClearanceNo");
        PrmsBankClearance result = null;
        try {
            if (query.getResultList().size() > 0) {
                result = (PrmsBankClearance) query.getResultList().get(0);
            }

            return result;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public PrmsBankClearance getSelectedClearanceNo(String clearanceNo) {
        Query query = em.createNamedQuery("PrmsBankClearance.SearchByClearanceIdSelection");
        query.setParameter("clearanceNo", clearanceNo);
        try {
            PrmsBankClearance ClearanceNoList = (PrmsBankClearance) query.getResultList().get(0);
            return ClearanceNoList;
        } catch (Exception e) {
            e.printStackTrace();

        }
        return null;
    }

    public List<PrmsBankClearance> getBankClearanceReq() {
        Query query = em.createNamedQuery("PrmsBankClearance.findByBankReqForApproval", PrmsBankClearance.class);
        ArrayList<PrmsBankClearance> bankClearReq = new ArrayList<>(query.getResultList());
        return bankClearReq;
    }
    // </editor-fold >

    // <editor-fold defaultstate="collapsed" desc="Native Queries">
    public List<PrmsBankClearance> getBankClearanceSearchParameterLst() {
        Query query = em.createNativeQuery("SELECT column_name  FROM  user_tab_cols\n"
                + "where table_name=Upper('PRMS_BANK_CLEARANCE')\n"
                + "and column_name not in ('CLEARANCE_ID','STATUS','ATTACHMENT_REF_NUMBER',"
                + "'CONTRACT_ID','SUPPLIER_ID','REMARK','LETTER_OF_CREDIT_NO','LETTER_OF_CREDIT_AMOUNT',"
                + "'BANK_PERMIT_NO','PERMIT_AMOUNT','BILL_TYPE','BILL_NO') order by column_name ASC");
        List<PrmsBankClearance> colNameLists = new ArrayList<>();
        colNameLists = query.getResultList();
        return colNameLists;

    }

    public List<PrmsBankClearance> findClearanceNo(PrmsBankClearance bankClearanceRegistration) {
        if (bankClearanceRegistration.getColumnValue() != null && bankClearanceRegistration.getColumnName() != null
                && !bankClearanceRegistration.getColumnValue().equals("") && !bankClearanceRegistration.getColumnName().equals("")) {
            Query query = em.createNativeQuery("SELECT * FROM PRMS_BANK_CLEARANCE\n"
                    + "where " + bankClearanceRegistration.getColumnName().toLowerCase() + " = '" + bankClearanceRegistration.getColumnValue() + "' "
                    + "and " + bankClearanceRegistration.getPreparedBy() + "='" + bankClearanceRegistration.getPreparedBy() + "' ", PrmsBankClearance.class);
            try {
                List<PrmsBankClearance> bankClearanceSearchLists = new ArrayList<>();
                if (query.getResultList().size() > 0) {
                    bankClearanceSearchLists = query.getResultList();
                }
                return bankClearanceSearchLists;
            } catch (Exception ex) {
                ex.getMessage();
                return null;
            }
        } else {
            Query query = em.createNamedQuery("PrmsBankClearance.findByPreparedBy");
            query.setParameter("preparedBy", bankClearanceRegistration.getPreparedBy());
            return query.getResultList();
        }

    }
    // </editor-fold >

}
