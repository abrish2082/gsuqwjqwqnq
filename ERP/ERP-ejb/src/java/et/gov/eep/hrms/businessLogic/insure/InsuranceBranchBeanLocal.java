/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.insure;

import et.gov.eep.hrms.entity.insurance.HrLuInsuranceBranches;
import et.gov.eep.hrms.entity.insurance.HrLuInsurances;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author meles
 */
@Local
public interface InsuranceBranchBeanLocal {

    public void saveorupdate(HrLuInsuranceBranches hrLuInsuranceBranches);

    public List<HrLuInsuranceBranches> findall();

    public List<HrLuInsuranceBranches> findbyID(HrLuInsurances hrLuInsurances);
    
}
