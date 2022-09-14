/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.mms.businessLogic;

import et.gov.eep.mms.entity.MmsFaGeothermal;
import et.gov.eep.mms.mapper.MmsFaGeothermalFacade;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author Binyam
 */
@Stateless
public class MmsFaGeothermalBean implements MmsFaGeothermalBeanLocal {

    @EJB
    MmsFaGeothermalFacade faGeothermalFacade;

    /**
     *
     * @param mmsFaTransmission
     * @return
     */
    @Override
    public ArrayList<MmsFaGeothermal> searchGeothermalFA(MmsFaGeothermal mmsFaTransmission) {
        return faGeothermalFacade.searchByStationName(mmsFaTransmission);
    }

    /**
     *
     * @param mmsFaTransmission
     * @return
     */
    @Override
    public MmsFaGeothermal getByAssetName(MmsFaGeothermal mmsFaTransmission) {
        return faGeothermalFacade.getGTInfo(mmsFaTransmission);
    }

    @Override
    public void create(MmsFaGeothermal geothermalEntity) {
        faGeothermalFacade.create(geothermalEntity);
    }

    @Override
    public void edit(MmsFaGeothermal geothermalEntity) {
        faGeothermalFacade.edit(geothermalEntity);
    }

    @Override
    public List<MmsFaGeothermal> searchGeoByParameterPrefix(MmsFaGeothermal geothermalEntity) {
        return faGeothermalFacade.searchGeoByParameterPrefix(geothermalEntity);
    }

    @Override
    public MmsFaGeothermal getLastGeoId() {
        return faGeothermalFacade.getLastGeoId();
    }

    @Override
    public ArrayList<MmsFaGeothermal> searchByGeoNo(MmsFaGeothermal geothermalEntity) {
        return faGeothermalFacade.searchByGeoNo(geothermalEntity);
    }

    @Override
    public List<MmsFaGeothermal> findAll1() {
        return faGeothermalFacade.findAll();
    }

    @Override
    public List<MmsFaGeothermal> searchByGeo(MmsFaGeothermal geothermalEntity) {
        return faGeothermalFacade.searchByGeo(geothermalEntity);
    }

    @Override
    public MmsFaGeothermal getSelectedRequest(Integer geothermalId) {
        return faGeothermalFacade.getSelectedRequest(geothermalId);
    }

    @Override
    public List<MmsFaGeothermal> findNewItems() {
        return faGeothermalFacade.findNewItems();
    }

    @Override
    public List<MmsFaGeothermal> searchByGeoNoAndGtPrep(MmsFaGeothermal geothermalEntity) {
        return faGeothermalFacade.searchByGeoNoAndGtPrep(geothermalEntity);
    }
//     @Override
//    public List<MmsFaGeothermal> searchAllSubInfoByPreparerId(Integer ssPrepared) {
//         return faGeothermalFacade.searchAllSubInfoByPreparerId(ssPrepared);
//    }
}
