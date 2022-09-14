/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.businessLogic.fixedasset;

import et.gov.eep.fcms.entity.fixedasset.FmsDprBuilding;
import et.gov.eep.fcms.mapper.fixedasset.FmsDprBuildingFacade;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author Binyam
 */
@Stateless
public class FmsDprBuildingBean implements FmsDprBuildingBeanLocal {

    @EJB
    FmsDprBuildingFacade fmsDprBuildingFacade;
    
    @Override
    public List<FmsDprBuilding> findAll() {
        return fmsDprBuildingFacade.findAll();
    }

    @Override
    public List<FmsDprBuilding> findStatus1() {
        return fmsDprBuildingFacade.findStatus1();
    }

    @Override
    public void edit(FmsDprBuilding fmsDprBuilding) {
        fmsDprBuildingFacade.edit(fmsDprBuilding);
    }

    @Override
    public void create(FmsDprBuilding fmsDprBuilding) {
        fmsDprBuildingFacade.create(fmsDprBuilding);
    }

    @Override
    public List<FmsDprBuilding> fetchBuilding(FmsDprBuilding fmsDprBuilding) {
        return fmsDprBuildingFacade.fetchBuilding(fmsDprBuilding);
    }

}
