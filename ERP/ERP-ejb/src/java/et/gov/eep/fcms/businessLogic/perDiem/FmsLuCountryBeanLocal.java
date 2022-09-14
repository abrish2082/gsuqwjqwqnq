/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.businessLogic.perDiem;

import java.util.List;
import javax.ejb.Local;
import et.gov.eep.commonApplications.entity.ComLuCountry;
import et.gov.eep.fcms.entity.perDiem.FmsLuCountry;

/**
 *
 * @author muller
 */
@Local
public interface FmsLuCountryBeanLocal {

    public void create(FmsLuCountry fmsLuCountry);

    public void edit(FmsLuCountry fmsLuCountry);

    public FmsLuCountry getById(FmsLuCountry fmsLuCountry);

    public FmsLuCountry getSelectedcountry(int key);

    public FmsLuCountry getAllCountry(FmsLuCountry fmsLuCountry);

    public boolean getCountryDup(FmsLuCountry fmsLuCountry);

    public boolean searchCountryByID(ComLuCountry comLuCountry);

    public List<FmsLuCountry> findAllCountry();

    public List<FmsLuCountry> searchCountryByName(ComLuCountry comLuCountry);

    public List<FmsLuCountry> SearchCountry(FmsLuCountry fmsLuCountry);

    public List<FmsLuCountry> searchAllCountry();

}
