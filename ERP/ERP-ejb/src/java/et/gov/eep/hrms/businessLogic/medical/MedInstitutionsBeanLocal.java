/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.medical;

import et.gov.eep.hrms.entity.lookup.HrLuBankBranches;
import et.gov.eep.hrms.entity.lookup.HrLuBanks;
import et.gov.eep.hrms.entity.medical.HrLocalMedInstitutions;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author INSA
 */
@Local
public interface MedInstitutionsBeanLocal {

    public void save(HrLocalMedInstitutions hrLocalMedInstitutions);

    public void edit(HrLocalMedInstitutions hrLocalMedInstitutions);

    public void saveUpdate(HrLocalMedInstitutions hrLocalMedInstitutions);

    public HrLuBanks findBanks(HrLuBanks hrLuBanks);

    public HrLuBankBranches findBankBranchs(HrLuBankBranches hrLuBankBranches);

    public ArrayList<HrLuBanks> findAllBanks();

    public ArrayList<HrLuBankBranches> getBankBranchInfo(HrLuBanks hrLuBanks);

    public List<HrLocalMedInstitutions> getAllInstitutionName();

    public ArrayList<HrLocalMedInstitutions> getInstitutionType(HrLocalMedInstitutions hrLocalMedInstitutions);

    public ArrayList<HrLocalMedInstitutions> findByInstitutionName(HrLocalMedInstitutions hrLocalMedInstitutions);

    public List<HrLocalMedInstitutions> findByName(String institutionName);

    public HrLocalMedInstitutions getSelectedMdcInst(int result);

    public boolean isExist(HrLocalMedInstitutions institution);

}
