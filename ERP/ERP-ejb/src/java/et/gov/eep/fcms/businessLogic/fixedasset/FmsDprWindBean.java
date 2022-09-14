/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.businessLogic.fixedasset;

import et.gov.eep.fcms.entity.fixedasset.FmsDprWind;
import et.gov.eep.fcms.mapper.fixedasset.FmsDprWindFacade;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author Binyam
 */
@Stateless
public class FmsDprWindBean implements FmsDprWindBeanLocal {

    @EJB
    FmsDprWindFacade fmsDprWindFacade;
    
    @Override
    public List<FmsDprWind> findAll() {
        return fmsDprWindFacade.findAll();
    }

    @Override
    public void edit(FmsDprWind fmsDprWind) {
        fmsDprWindFacade.edit(fmsDprWind);
    }

    @Override
    public void create(FmsDprWind fmsDprWind) {
        fmsDprWindFacade.create(fmsDprWind);
    }
        
    @Override
    public List<FmsDprWind> findStatus1() {
        return fmsDprWindFacade.findStatus1();
    }

    @Override
    public List<FmsDprWind> fetchWind(FmsDprWind fmsDprWind) {
        return fmsDprWindFacade.fetchWind(fmsDprWind);
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}
