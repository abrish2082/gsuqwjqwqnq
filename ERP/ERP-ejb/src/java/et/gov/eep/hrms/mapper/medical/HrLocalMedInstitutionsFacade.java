/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.mapper.medical;

import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.hrms.entity.lookup.HrLuBankBranches;
import et.gov.eep.hrms.entity.lookup.HrLuBanks;
import et.gov.eep.hrms.entity.medical.HrLocalMedInstitutions;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author INSA
 */
@Stateless
public class HrLocalMedInstitutionsFacade extends AbstractFacade<HrLocalMedInstitutions> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public HrLocalMedInstitutionsFacade() {
        super(HrLocalMedInstitutions.class);
    }

    //<editor-fold defaultstate="collapsed" desc="Bank named query">
    public HrLuBanks findBanks(HrLuBanks hrLuBanks) {
        Query query = em.createNamedQuery("HrLuBanks.findByBankName");
        query.setParameter("bankName", hrLuBanks.getBankName());
        try {
            HrLuBanks bankname = (HrLuBanks) query.getResultList().get(0);
            return bankname;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public HrLuBankBranches findBankBranchs(HrLuBankBranches hrLuBankBranches) {
        Query query = em.createNamedQuery("HrLuBankBranches.findByBranchName");
        query.setParameter("branchName", hrLuBankBranches.getBranchName());
        try {
            HrLuBankBranches branch = (HrLuBankBranches) query.getResultList().get(0);
            return branch;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public ArrayList<HrLuBanks> findAllBanks() {
        Query query = em.createNamedQuery("HrLuBanks.findAll");
        try {
            ArrayList<HrLuBanks> banks = new ArrayList(query.getResultList());
            return banks;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public ArrayList<HrLuBankBranches> getBankBranchInfo(HrLuBanks hrLuBanks) {
        Query query = em.createNamedQuery("HrLuBankBranches.findByBankId", HrLuBanks.class);
        query.setParameter("bankId", hrLuBanks.getId());
        try {
            ArrayList<HrLuBankBranches> hrBranchInfo = new ArrayList(query.getResultList());
            return hrBranchInfo;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Named query">
    public ArrayList<HrLocalMedInstitutions> getInstitutionType(HrLocalMedInstitutions hrLocalMedInstitutions) {
        Query query = em.createNamedQuery("HrLocalMedInstitutions.findByType");
        query.setParameter("type", hrLocalMedInstitutions.getType());
        try {
            ArrayList<HrLocalMedInstitutions> TypeList = new ArrayList(query.getResultList());
            return TypeList;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public ArrayList<HrLocalMedInstitutions> findByInstitutionName(HrLocalMedInstitutions hrLocalMedInstitutions) {
        Query query = em.createNamedQuery("HrLocalMedInstitutions.findByName");
        query.setParameter("name", hrLocalMedInstitutions.getName());
        try {
            ArrayList<HrLocalMedInstitutions> nameList = new ArrayList<>(query.getResultList());
            return nameList;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<HrLocalMedInstitutions> findByName(String institutionName) {
        Query query = em.createNamedQuery("HrLocalMedInstitutions.findByName", HrLocalMedInstitutions.class);
        query.setParameter("name", institutionName);
        try {
            return (List<HrLocalMedInstitutions>) query.getResultList();
        } catch (Exception ex) {
            return null;
        }
    }

    public HrLocalMedInstitutions getSelectedMdcInst(int result) {
        Query query = em.createNamedQuery("HrLocalMedInstitutions.findById");
        query.setParameter("id", result);
        try {
            HrLocalMedInstitutions selected = (HrLocalMedInstitutions) query.getResultList().get(0);
            return selected;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public boolean isExist(HrLocalMedInstitutions institution) {
        boolean isExist;
        Query query = em.createNamedQuery("HrLocalMedInstitutions.checkDuplicate");
        query.setParameter("name", institution.getName());
        query.setParameter("type", institution.getType());
        try {
            if (query.getResultList().size() > 0) {
                isExist = true;
            } else {
                isExist = false;
            }
            return isExist;
        } catch (Exception ex) {
            return false;
        }
    }
//</editor-fold>
}
