/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.businessLogic.fixedasset;

import et.gov.eep.fcms.entity.fixedasset.FmsDprTransmisson;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Binyam
 */
@Local
public interface FmsDprTransmissionBeanLocal {

    public List<FmsDprTransmisson> findAll();
    
    public List<FmsDprTransmisson> findStatus1();

    public void edit(FmsDprTransmisson fmsDprTransmisson);

    public void create(FmsDprTransmisson fmsDprTransmisson);
    
    public List <FmsDprTransmisson> fetchTransmission(FmsDprTransmisson fmsDprTransmisson);

    public List<FmsDprTransmisson> searchByTrId(FmsDprTransmisson transDprEntity);
}
