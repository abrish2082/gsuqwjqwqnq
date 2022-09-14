/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.businessLogic.fixedasset;

import et.gov.eep.fcms.entity.fixedasset.FmsDprTransport;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Binyam
 */
@Local
public interface FmsDprTransportBeanLocal {
    public List<FmsDprTransport> findAll();
    
    public List<FmsDprTransport> findStatus1();

    public void edit(FmsDprTransport fmsDprWind);

    public void create(FmsDprTransport fmsDprWind);
    
    public List <FmsDprTransport> fetchTransport(FmsDprTransport fmsDprTransport);
}
