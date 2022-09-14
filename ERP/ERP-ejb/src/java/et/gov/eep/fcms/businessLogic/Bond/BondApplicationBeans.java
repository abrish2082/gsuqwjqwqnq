/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.businessLogic.Bond;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import et.gov.eep.fcms.entity.Bond.FmsBondApplication;
import et.gov.eep.fcms.mapper.Bond.FmsBondApplicationFacade;

/**
 *
 * @author mora
 */
@Stateless
public class BondApplicationBeans implements BondApplicationBeanLocals {

    @EJB
    FmsBondApplicationFacade fmsBondApplicationFacade;

    @Override
    public void Create(FmsBondApplication fmsBondApplication) {
        fmsBondApplicationFacade.create(fmsBondApplication);
    }

    @Override
    public ArrayList<FmsBondApplication> searchBondType(FmsBondApplication bondApplication) {
        return fmsBondApplicationFacade.searchFmsBondTypeId(bondApplication);
    }

//     @Override
//    public FmsBondApplication bondApplicationInfo(FmsBondApplication bondApplication) {
//        return fmsBondApplicationFacade.fmsBondApplicationinfo(bondApplication);
//    }

    @Override
    public List<FmsBondApplication> searchBondmatured() {
        return fmsBondApplicationFacade.searchFmsBonddate();
    }
}
