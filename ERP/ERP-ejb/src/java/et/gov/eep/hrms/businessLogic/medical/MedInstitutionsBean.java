/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.medical;

import et.gov.eep.hrms.entity.lookup.HrLuBankBranches;
import et.gov.eep.hrms.entity.lookup.HrLuBanks;
import et.gov.eep.hrms.entity.medical.HrLocalMedInstitutions;
import et.gov.eep.hrms.mapper.medical.HrLocalMedInstitutionsFacade;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author INSA
 */
@Stateless
public class MedInstitutionsBean implements MedInstitutionsBeanLocal {

    @EJB
    HrLocalMedInstitutionsFacade medInstitutionsFacade;

    @Override
    public void save(HrLocalMedInstitutions hrLocalMedInstitutions) {
        medInstitutionsFacade.create(hrLocalMedInstitutions);
    }

    @Override
    public void edit(HrLocalMedInstitutions hrLocalMedInstitutions) {
        medInstitutionsFacade.edit(hrLocalMedInstitutions);
    }

    @Override
    public void saveUpdate(HrLocalMedInstitutions hrLocalMedInstitutions) {
        medInstitutionsFacade.saveOrUpdate(hrLocalMedInstitutions);
    }

    @Override
    public HrLuBanks findBanks(HrLuBanks hrLuBanks) {
        return medInstitutionsFacade.findBanks(hrLuBanks);
    }

    @Override
    public HrLuBankBranches findBankBranchs(HrLuBankBranches hrLuBankBranches) {
        return medInstitutionsFacade.findBankBranchs(hrLuBankBranches);
    }

    @Override
    public ArrayList<HrLuBanks> findAllBanks() {
        return medInstitutionsFacade.findAllBanks();
    }

    @Override
    public ArrayList<HrLuBankBranches> getBankBranchInfo(HrLuBanks hrLuBanks) {
        return medInstitutionsFacade.getBankBranchInfo(hrLuBanks);
    }

    @Override
    public List<HrLocalMedInstitutions> getAllInstitutionName() {
        return medInstitutionsFacade.findAll();
    }

    @Override
    public ArrayList<HrLocalMedInstitutions> getInstitutionType(HrLocalMedInstitutions hrLocalMedInstitutions) {
        return medInstitutionsFacade.getInstitutionType(hrLocalMedInstitutions);
    }

    @Override
    public ArrayList<HrLocalMedInstitutions> findByInstitutionName(HrLocalMedInstitutions hrLocalMedInstitutions) {
        return medInstitutionsFacade.findByInstitutionName(hrLocalMedInstitutions);
    }

    @Override
    public List<HrLocalMedInstitutions> findByName(String institutionName) {
        return medInstitutionsFacade.findByName(institutionName);
    }

    @Override
    public HrLocalMedInstitutions getSelectedMdcInst(int result) {
        return medInstitutionsFacade.getSelectedMdcInst(result);
    }

    @Override
    public boolean isExist(HrLocalMedInstitutions institution) {
        return medInstitutionsFacade.isExist(institution);
    }

}
