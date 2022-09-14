/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.businessLogic.fixedasset;

import et.gov.eep.fcms.entity.fixedasset.FmsDprWind;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Binyam
 */
@Local
public interface FmsDprWindBeanLocal {
    public List<FmsDprWind> findAll();
    
    public List<FmsDprWind> findStatus1();

    public void edit(FmsDprWind fmsDprWind);

    public void create(FmsDprWind fmsDprWind);
    
    public List <FmsDprWind> fetchWind(FmsDprWind fmsDprWind);

}
