/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.insure;

import et.gov.eep.hrms.entity.insurance.HrLuInsuranceBranches;
import et.gov.eep.hrms.entity.insurance.HrLuInsurances;
import et.gov.eep.hrms.mapper.insure.HrLuInsuranceBranchesFacade;
import et.gov.eep.hrms.mapper.insure.HrLuInsurancesFacade;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author meles
 */
@Stateless
public class Insurancebean implements InsurancebeanLocal {
@EJB
HrLuInsuranceBranchesFacade hrLuInsuranceBranchesFacade;
@EJB
HrLuInsurancesFacade hrLuInsurancesFacade;
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")

    @Override
    public void saveorupdate(HrLuInsurances hrLuInsurances) {
        hrLuInsurancesFacade.saveOrUpdate(hrLuInsurances);
    }

    @Override
    public List<HrLuInsurances> findAll(HrLuInsurances hrLuInsurances) {
   return     hrLuInsurancesFacade.findAll();
    }

    @Override
    public List<HrLuInsurances> findAll() {
         return hrLuInsurancesFacade.findAll();
    }

    @Override
    public List<HrLuInsuranceBranches> findbyID(HrLuInsurances hrLuInsurances) {
         return hrLuInsurancesFacade.findbyID(hrLuInsurances);
    }

    @Override
    public List<HrLuInsurances> findbyID(HrLuInsuranceBranches hrLuInsuranceBranches) {
       return hrLuInsurancesFacade.findbyID(hrLuInsuranceBranches);
    }

    @Override
    public ArrayList<HrLuInsurances> insurancename(HrLuInsurances hrLuInsurances) {
        return hrLuInsurancesFacade.insurancename(hrLuInsurances);
    }
}
