/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.businessLogic.perDiem;

import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import et.gov.eep.commonApplications.entity.ComLuCountry;
import et.gov.eep.fcms.entity.perDiem.FmsLuCountry;
import et.gov.eep.fcms.mapper.perDiem.FmsLuCountryFacade;

/**
 *
 * @author muller
 */
@Stateless
public class FmsLuCountryBean implements FmsLuCountryBeanLocal {

    @EJB
    FmsLuCountryFacade fmsLuCountryFacade;

    @Override
    public void create(FmsLuCountry fmsLuCountry) {
        fmsLuCountryFacade.create(fmsLuCountry);
    }

    @Override
    public void edit(FmsLuCountry fmsLuCountry) {
        fmsLuCountryFacade.edit(fmsLuCountry);
    }

    @Override
    public List<FmsLuCountry> SearchCountry(FmsLuCountry fmsLuCountry) {
        return fmsLuCountryFacade.SearchCountry(fmsLuCountry);
    }

    @Override
    public FmsLuCountry getAllCountry(FmsLuCountry fmsLuCountry) {
        return fmsLuCountryFacade.getAllCountry(fmsLuCountry);
    }

    @Override
    public boolean getCountryDup(FmsLuCountry fmsLuCountry) {
        return fmsLuCountryFacade.getCountryDup(fmsLuCountry);
    }

    @Override
    public List<FmsLuCountry> findAllCountry() {
        return fmsLuCountryFacade.findAll();
    }

    @Override
    public FmsLuCountry getSelectedcountry(int key) {
        return fmsLuCountryFacade.getSelectedCountry(key);
    }

    @Override
    public List<FmsLuCountry> searchCountryByName(ComLuCountry comLuCountry) {
        return fmsLuCountryFacade.searchCountryByName(comLuCountry);
    }

    @Override
    public List<FmsLuCountry> searchAllCountry() {
        return fmsLuCountryFacade.findAll();
    }

    @Override
    public FmsLuCountry getById(FmsLuCountry fmsLuCountry) {
        return fmsLuCountryFacade.getDataById(fmsLuCountry);
    }

    @Override
    public boolean searchCountryByID(ComLuCountry comLuCountry) {
        return fmsLuCountryFacade.searchCountryByID(comLuCountry);
    }

}
