/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.insure;

import et.gov.eep.hrms.entity.insurance.HrInsurance;
import et.gov.eep.hrms.entity.insurance.HrInsuranceInjuredEmployee;
import et.gov.eep.hrms.entity.insurance.HrLuInsurances;
import et.gov.eep.hrms.mapper.insure.HrInsuranceFacade;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.faces.model.SelectItem;

/**
 *
 * @author meles
 */
@Stateless
public class Hrinsurancebean implements HrinsurancebeanLocal {
@EJB
    HrInsuranceFacade hrInsuranceFacade;
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")

    @Override
    public List<HrInsurance> findall() {
   return     hrInsuranceFacade.findAll();
    }

    @Override
    public void saveorupdate(HrInsurance HrInsurance) {
        hrInsuranceFacade.saveOrUpdate(HrInsurance);
    }

    @Override
    public ArrayList<HrInsurance> telephone(ArrayList<HrInsurance> insurancepro) {
        return hrInsuranceFacade.searchByTerminationName(insurancepro);
    }

    @Override
    public ArrayList<HrInsurance> telephone(HrInsurance HrInsurance) {
       return hrInsuranceFacade.searchByTerminationName(HrInsurance);
    }

    @Override
    public HrInsurance getByTerminationName(HrInsurance HrInsurance) {
        return hrInsuranceFacade.getByTerminationName(HrInsurance);
    }

    @Override
    public List<HrInsuranceInjuredEmployee> findbyname(HrInsurance HrInsurance) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<HrInsuranceInjuredEmployee> findbyname(HrInsuranceInjuredEmployee hrInsuranceInjuredEmployee) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean searchdup(HrInsurance HrInsurance) {
        return hrInsuranceFacade.searchduplicate(HrInsurance);
    }

    @Override
    public ArrayList<HrInsurance> hrLuInsurances(HrInsurance HrInsurance) {
        return hrInsuranceFacade.hrLuInsurances(HrInsurance);
    }

    @Override
    public ArrayList<HrInsurance> hrLuInsurances(HrLuInsurances hrLuInsurances) {
        return hrInsuranceFacade.hrLuInsurances(hrLuInsurances);
    }

    @Override
    public HrInsurance getByInsuranceName(HrLuInsurances hrLuInsurances) {
        return hrInsuranceFacade.getByInsuranceName(hrLuInsurances);
    }

    @Override
    public List<SelectItem> filterByStatus() {
         List<SelectItem> selectItems = new ArrayList<>();
        selectItems.add(new SelectItem("null", "--- Select ---"));
        selectItems.add(new SelectItem(String.valueOf("0"), "Load Inactive List"));
        selectItems.add(new SelectItem(String.valueOf("1"), "Load Active List"));
        selectItems.add(new SelectItem(String.valueOf("2"), "Load all List"));
        return selectItems;
    }

    @Override
    public List<HrInsurance> loadFiltereddata(int status) {
       return hrInsuranceFacade.loadFiltereddata(status);
    }

    @Override
    public List<HrInsurance> ActiveLists(Integer Status) {
       return hrInsuranceFacade.ActiveLists(Status);
    }

    @Override
    public HrInsurance getSelectedPayment(Integer idfind) {
    return hrInsuranceFacade.getSelectedPayment(idfind);
    }
}
