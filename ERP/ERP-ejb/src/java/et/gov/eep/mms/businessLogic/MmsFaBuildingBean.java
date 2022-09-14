/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.mms.businessLogic;

import et.gov.eep.mms.entity.MmsFaBuilding;
import et.gov.eep.mms.mapper.MmsFaBuildingFacade;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author Binyam
 */
@Stateless
public class MmsFaBuildingBean implements MmsFaBuildingBeanLocal {

    @EJB
    private MmsFaBuildingFacade mmsFaBuildingFacade;

    /**
     *
     * @param mmsFaTransmission
     * @return
     */
    @Override
    public ArrayList<MmsFaBuilding> searchBuildingFA(MmsFaBuilding mmsFaTransmission) {
        return mmsFaBuildingFacade.searchByBuildingName(mmsFaTransmission);
    }

    @Override
    public MmsFaBuilding getByAssetName(MmsFaBuilding mmsFaTransmission) {
        return mmsFaBuildingFacade.getBUInfo(mmsFaTransmission);
    }

    @Override
    public void create(MmsFaBuilding buildingEntity) {
        mmsFaBuildingFacade.create(buildingEntity);
    }

    @Override
    public void edit(MmsFaBuilding buildingEntity) {
        mmsFaBuildingFacade.edit(buildingEntity);
    }

    @Override
    public List<MmsFaBuilding> searchBuildingByParameterPrefix(MmsFaBuilding buildingEntity) {
        return mmsFaBuildingFacade.searchBuildingByParameterPrefix(buildingEntity);
    }

    @Override
    public MmsFaBuilding getLastBuId() {
        return mmsFaBuildingFacade.getLastBuId();
    }

    @Override
    public ArrayList<MmsFaBuilding> searchByBuNo(MmsFaBuilding buildingEntity) {
        return mmsFaBuildingFacade.searchByBuNo(buildingEntity);
    }

    @Override
    public List<MmsFaBuilding> findAll1() {
        return mmsFaBuildingFacade.findAll();
    }

    @Override
    public List<MmsFaBuilding> searchByBldg(MmsFaBuilding buildingEntity) {
        return mmsFaBuildingFacade.searchByBldg(buildingEntity);
    }

    @Override
    public MmsFaBuilding getSelectedRequest(Integer buildingId) {
        return mmsFaBuildingFacade.getSelectedRequest(buildingId);
    }

    @Override
    public List<MmsFaBuilding> findNewItems() {
        return mmsFaBuildingFacade.findNewItems();
    }

    @Override
    public List<MmsFaBuilding> searchByBldg2(MmsFaBuilding buildingEntity) {
        return mmsFaBuildingFacade.searchByBldg2(buildingEntity);
    }

    @Override
    public List<MmsFaBuilding> searchByBuName(MmsFaBuilding buildingEntity) {
        return mmsFaBuildingFacade.searchByBuName(buildingEntity);
    }

    @Override
    public List<MmsFaBuilding> searchByBuNoAndProcessedBy(MmsFaBuilding buildingEntity) {
        return mmsFaBuildingFacade.searchByBuNoAndProcessedBy(buildingEntity);
    }

    @Override
    public List<MmsFaBuilding> searchByBuNameAndBuPrep(MmsFaBuilding buildingEntity) {
        return mmsFaBuildingFacade.searchByBuNameAndBuPrep(buildingEntity);
    }

    @Override
    public List<MmsFaBuilding> searchByBldg2AndBuPrep(MmsFaBuilding buildingEntity) {
        return mmsFaBuildingFacade.searchByBldg2AndBuPrep(buildingEntity);
    }

    @Override
    public List<MmsFaBuilding> searchAllTransmissionsInfoByPreparerId(Integer buPrepared) {
         return mmsFaBuildingFacade.searchAllTransmissionsInfoByPreparerId(buPrepared);
    }

}
