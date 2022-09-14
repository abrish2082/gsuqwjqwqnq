/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.businessLogic.fixedasset;

import et.gov.eep.fcms.entity.fixedasset.FmsDprGeothermal;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Binyam
 */
@Local
public interface FmsDprGeothermalBeanLocal {
    public List<FmsDprGeothermal> findAll();
    
    public List<FmsDprGeothermal> findStatus1();

    public void edit(FmsDprGeothermal fmsDprGeothermal);

    public void create(FmsDprGeothermal fmsDprGeothermal);
    
    public List <FmsDprGeothermal> fetchGeothermal(FmsDprGeothermal fmsDprGeothermal);
}
