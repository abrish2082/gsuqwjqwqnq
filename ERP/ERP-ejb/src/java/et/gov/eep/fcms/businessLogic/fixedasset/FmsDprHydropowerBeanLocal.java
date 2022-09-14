/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.businessLogic.fixedasset;

import et.gov.eep.fcms.entity.fixedasset.FmsDprHydropower;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Binyam
 */
@Local
public interface FmsDprHydropowerBeanLocal {
    public List<FmsDprHydropower> findAll();
    
    public List<FmsDprHydropower> findStatus1();

    public void edit(FmsDprHydropower fmsDprHydropower);

    public void create(FmsDprHydropower fmsDprHydropower);
    
    public List <FmsDprHydropower> fetchDprHydropowers(FmsDprHydropower fmsDprHydropower);
}
