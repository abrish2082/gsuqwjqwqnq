/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.businessLogic.fixedasset;

import et.gov.eep.fcms.entity.fixedasset.FmsDprTransport;
import et.gov.eep.fcms.mapper.fixedasset.FmsDprTransportFacade;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author Binyam
 */
@Stateless
public class FmsDprTransportBean implements FmsDprTransportBeanLocal {

    @EJB
    FmsDprTransportFacade fmsDprTransportFacade;
    @Override
    public List<FmsDprTransport> findAll() {
        return fmsDprTransportFacade.findAll();
    }

    @Override
    public List<FmsDprTransport> findStatus1() {
        return fmsDprTransportFacade.findStatus1();
    }

    @Override
    public void edit(FmsDprTransport fmsDprWind) {
        fmsDprTransportFacade.edit(fmsDprWind);
    }

    @Override
    public void create(FmsDprTransport fmsDprWind) {
        fmsDprTransportFacade.create(fmsDprWind);
    }

    @Override
    public List<FmsDprTransport> fetchTransport(FmsDprTransport fmsDprTransport) {
        return fmsDprTransportFacade.fetchTransport(fmsDprTransport);
    }


}
