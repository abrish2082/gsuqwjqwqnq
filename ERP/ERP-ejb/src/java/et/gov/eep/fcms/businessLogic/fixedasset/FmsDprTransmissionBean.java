/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.businessLogic.fixedasset;

import et.gov.eep.fcms.entity.fixedasset.FmsDprTransmisson;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import et.gov.eep.fcms.mapper.fixedasset.FmsDprTransmissonFacade;



/**
 *
 * @author Binyam
 */
@Stateless
public class FmsDprTransmissionBean implements FmsDprTransmissionBeanLocal {

    @EJB
    FmsDprTransmissonFacade fmsDprTransmissonFacade;

    @Override
    public List<FmsDprTransmisson> findAll() {
        return fmsDprTransmissonFacade.findAll();
    }

    @Override
    public void create(FmsDprTransmisson fmsDprTransmisson) {
        fmsDprTransmissonFacade.create(fmsDprTransmisson);
    }

    @Override
    public void edit(FmsDprTransmisson fmsDprTransmisson) {
        fmsDprTransmissonFacade.edit(fmsDprTransmisson);
    }

    @Override
    public List<FmsDprTransmisson> fetchTransmission(FmsDprTransmisson fmsDprTransmisson) {
        return fmsDprTransmissonFacade.fetchTransmission(fmsDprTransmisson);
    }

    @Override
    public List<FmsDprTransmisson> searchByTrId(FmsDprTransmisson transDprEntity) {
         return fmsDprTransmissonFacade.searchByTrId(transDprEntity);
    }
    
     @Override
    public List<FmsDprTransmisson> findStatus1() {
        return fmsDprTransmissonFacade.findStatus1();
    }

}
