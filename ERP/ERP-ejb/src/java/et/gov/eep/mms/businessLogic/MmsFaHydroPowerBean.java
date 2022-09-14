/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.mms.businessLogic;

import et.gov.eep.mms.entity.MmsFaHydropower;
import et.gov.eep.mms.mapper.MmsFaHydropowerFacade;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author Sadik
 */
@Stateless
public class MmsFaHydroPowerBean implements MmsFaHydroPowerBeanLocal {

    @EJB
    MmsFaHydropowerFacade hydropowerFacade;

    @Override
    public void create(MmsFaHydropower mmsFahydropower) {
        hydropowerFacade.create(mmsFahydropower);
    }

    @Override
    public void edit(MmsFaHydropower mmsFahydropower) {
        hydropowerFacade.edit(mmsFahydropower);
    }

    @Override
    public MmsFaHydropower getLastHpId() {
        return hydropowerFacade.getLastHpId();
    }

    @Override
    public ArrayList<MmsFaHydropower> searchByHpNo(MmsFaHydropower hydroPowerEntity) {
        return hydropowerFacade.searchByHpNo(hydroPowerEntity);
    }

    @Override
    public ArrayList<MmsFaHydropower> searchByHpNoANdProcessedby(MmsFaHydropower hydroPowerEntity) {
        return hydropowerFacade.searchByHpNoANdProcessedby(hydroPowerEntity);
    }

    @Override
    public List<MmsFaHydropower> findAll1() {
        return hydropowerFacade.findAll();
    }

    @Override
    public List<MmsFaHydropower> searchByHp(MmsFaHydropower hydroPowerEntity) {
        return hydropowerFacade.searchByHp(hydroPowerEntity);
    }

    @Override
    public MmsFaHydropower getSelectedRequest(Integer hydroPowerId) {
        return hydropowerFacade.getSelectedRequest(hydroPowerId);
    }

    @Override
    public List<MmsFaHydropower> findNewItems() {
        return hydropowerFacade.findNewItems();
    }

    @Override
    public List<MmsFaHydropower> searchByHpLocation(MmsFaHydropower hydroPowerEntity) {
        return hydropowerFacade.searchByHpLocation(hydroPowerEntity);
    }

    @Override
    public List<MmsFaHydropower> searchByHp2(MmsFaHydropower hydroPowerEntity) {
        return hydropowerFacade.searchByHp2(hydroPowerEntity);
    }

    @Override
    public List<MmsFaHydropower> searchAllTransmissionsInfoByPreparerId(MmsFaHydropower hydroPowerEntity) {
      return hydropowerFacade.searchAllTransmissionsInfoByPreparerId(hydroPowerEntity); 
    }

}
