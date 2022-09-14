/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.businessLogic.fixedasset;

import et.gov.eep.fcms.entity.fixedasset.FmsDprSubstation;
import et.gov.eep.fcms.mapper.fixedasset.FmsDprSubstationFacade;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author Binyam
 */
@Stateless
public class FmsDprSubstationBean implements FmsDprSubstationBeanLocal {

    @EJB
    FmsDprSubstationFacade fmsDprSubstationFacade;

    /**
     *
     * @return
     */
    @Override
    public List<FmsDprSubstation> findAll() {
        return fmsDprSubstationFacade.findAll();
    }

    @Override
    public void create(FmsDprSubstation fmsDprSubstation) {
        fmsDprSubstationFacade.create(fmsDprSubstation);
    }

    @Override
    public void edit(FmsDprSubstation fmsDprSubstation) {
        fmsDprSubstationFacade.edit(fmsDprSubstation);
    }

    @Override
    public List<FmsDprSubstation> fetchSubstation(FmsDprSubstation fmsDprSubstation) {
        return fmsDprSubstationFacade.fetchSubstation(fmsDprSubstation);
    }

    @Override
    public List<FmsDprSubstation> findStatus1() {
        return fmsDprSubstationFacade.findStatus1();
    }

}
