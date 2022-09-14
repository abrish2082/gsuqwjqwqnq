/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.insure;

import et.gov.eep.hrms.entity.insurance.HrLuInsuranceBranches;
import et.gov.eep.hrms.entity.insurance.HrLuInsurances;
import et.gov.eep.hrms.mapper.insure.HrLuInsuranceBranchesFacade;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author meles
 */
@Stateless
public class InsuranceBranchBean implements InsuranceBranchBeanLocal {
@EJB
HrLuInsuranceBranchesFacade HrLuInsuranceBranchesFacade;
    @Override
    public void saveorupdate(HrLuInsuranceBranches hrLuInsuranceBranches) {
        HrLuInsuranceBranchesFacade.saveOrUpdate(hrLuInsuranceBranches);
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")

    @Override
    public List<HrLuInsuranceBranches> findall() {
   return     HrLuInsuranceBranchesFacade.findAll();
    }

    @Override
    public List<HrLuInsuranceBranches> findbyID(HrLuInsurances hrLuInsurances) {
       return     HrLuInsuranceBranchesFacade.findbyID(hrLuInsurances);
    }
}
