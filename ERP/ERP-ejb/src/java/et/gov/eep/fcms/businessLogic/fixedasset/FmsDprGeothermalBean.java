/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.businessLogic.fixedasset;

import et.gov.eep.fcms.entity.fixedasset.FmsDprGeothermal;
import et.gov.eep.fcms.mapper.fixedasset.FmsDprGeothermalFacade;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author Binyam
 */
@Stateless
public class FmsDprGeothermalBean implements FmsDprGeothermalBeanLocal {
    
    @EJB
    FmsDprGeothermalFacade dprGeothermalFacade;

    @Override
    public List<FmsDprGeothermal> findStatus1() {
        return dprGeothermalFacade.findStatus1();
    }

    @Override
    public List<FmsDprGeothermal> fetchGeothermal(FmsDprGeothermal fmsDprGeothermal) {
        return dprGeothermalFacade.fetchGeothermal(fmsDprGeothermal);
    }

    @Override
    public List<FmsDprGeothermal> findAll() {
        return dprGeothermalFacade.findAll();
    }

    @Override
    public void edit(FmsDprGeothermal fmsDprGeothermal) {
        dprGeothermalFacade.edit(fmsDprGeothermal);
    }

    @Override
    public void create(FmsDprGeothermal fmsDprGeothermal) {
        dprGeothermalFacade.create(fmsDprGeothermal);
    }
}
