/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.businessLogic.fixedasset;

import et.gov.eep.fcms.entity.fixedasset.FmsDprHydropower;
import et.gov.eep.fcms.mapper.fixedasset.FmsDprHydropowerFacade;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author Binyam
 */
@Stateless
public class FmsDprHydropowerBean implements FmsDprHydropowerBeanLocal {

    @EJB
    FmsDprHydropowerFacade fmsDprHydropowerFacade;
    
    @Override
    public List<FmsDprHydropower> findAll() {
        return fmsDprHydropowerFacade.findAll();
    }

    @Override
    public List<FmsDprHydropower> findStatus1() {
        return fmsDprHydropowerFacade.findStatus1();
    }

    @Override
    public void edit(FmsDprHydropower fmsDprHydropower) {
        fmsDprHydropowerFacade.edit(fmsDprHydropower);
    }

    @Override
    public void create(FmsDprHydropower fmsDprHydropower) {
        fmsDprHydropowerFacade.create(fmsDprHydropower);
    }

    @Override
    public List<FmsDprHydropower> fetchDprHydropowers(FmsDprHydropower fmsDprHydropower) {
        return fmsDprHydropowerFacade.fetchDprHydropowers(fmsDprHydropower);
    }

    
}
