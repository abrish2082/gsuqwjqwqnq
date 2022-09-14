/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.businessLogic.fixedasset;

import et.gov.eep.fcms.entity.fixedasset.FmsDprBuilding;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Binyam
 */
@Local
public interface FmsDprBuildingBeanLocal {
    public List<FmsDprBuilding> findAll();
    
    public List<FmsDprBuilding> findStatus1();

    public void edit(FmsDprBuilding fmsDprBuilding);

    public void create(FmsDprBuilding fmsDprBuilding);
    
    public List <FmsDprBuilding> fetchBuilding(FmsDprBuilding fmsDprBuilding);
}
