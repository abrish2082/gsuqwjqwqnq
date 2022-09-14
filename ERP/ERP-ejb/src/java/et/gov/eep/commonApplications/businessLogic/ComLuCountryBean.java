/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.commonApplications.businessLogic;

import et.gov.eep.commonApplications.entity.ComLuCountry;
import et.gov.eep.commonApplications.mapper.ComLuCountryFacade;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author mubejbl
 */
@Stateless
public class ComLuCountryBean implements ComLuCountryBeanLocal {

    @EJB
    ComLuCountryFacade comLuCountryFacade;
    private int x;

    @Override
    public List<ComLuCountry> findAll() {

        return comLuCountryFacade.findAll();
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
     @Override
    public ComLuCountry luCountry(ComLuCountry luCountry) {
        return comLuCountryFacade.findByCountryId(luCountry); //To change body of generated methods, choose Tools | Templates.
    }
}
