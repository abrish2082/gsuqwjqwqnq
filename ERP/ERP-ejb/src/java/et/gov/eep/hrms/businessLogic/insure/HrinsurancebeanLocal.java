/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.insure;

import et.gov.eep.hrms.entity.insurance.HrInsurance;
import et.gov.eep.hrms.entity.insurance.HrInsuranceInjuredEmployee;
import et.gov.eep.hrms.entity.insurance.HrLuInsurances;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Local;
import javax.faces.model.SelectItem;

/**
 *
 * @author meles
 */
@Local
public interface HrinsurancebeanLocal {

    public List<HrInsurance> findall();

    public void saveorupdate(HrInsurance HrInsurance);

    public ArrayList<HrInsurance> telephone(ArrayList<HrInsurance> insurancepro);

    public ArrayList<HrInsurance> telephone(HrInsurance HrInsurance);

    public HrInsurance getByTerminationName(HrInsurance HrInsurance);

    public List<HrInsuranceInjuredEmployee> findbyname(HrInsurance HrInsurance);

    public List<HrInsuranceInjuredEmployee> findbyname(HrInsuranceInjuredEmployee hrInsuranceInjuredEmployee);

    public boolean searchdup(HrInsurance HrInsurance);

    public ArrayList<HrInsurance> hrLuInsurances(HrInsurance HrInsurance);

    public ArrayList<HrInsurance> hrLuInsurances(HrLuInsurances hrLuInsurances);

    public HrInsurance getByInsuranceName(HrLuInsurances hrLuInsurances);

    public List<SelectItem> filterByStatus();

    public List<HrInsurance> loadFiltereddata(int status);

    public List<HrInsurance> ActiveLists(Integer Status);

    public HrInsurance getSelectedPayment(Integer id);

}
