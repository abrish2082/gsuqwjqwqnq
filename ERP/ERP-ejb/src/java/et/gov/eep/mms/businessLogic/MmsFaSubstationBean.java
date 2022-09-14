/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.mms.businessLogic;

import et.gov.eep.mms.entity.MmsFaSubstation;
import et.gov.eep.mms.mapper.MmsFaSubstationFacade;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author Sadik
 */
@Stateless
public class MmsFaSubstationBean implements MmsFaSubstationBeanLocal {

    @EJB
    MmsFaSubstationFacade substationFacade;

    @Override
    public void create(MmsFaSubstation mmsFaSubstation) {
        substationFacade.create(mmsFaSubstation);
    }

    @Override
    public void edit(MmsFaSubstation mmsFaSubstation) {
        substationFacade.edit(mmsFaSubstation);
    }

    @Override
    public MmsFaSubstation getLastSubId() {
        return substationFacade.getLastSubId();
    }

    @Override
    public List<MmsFaSubstation> findAll() {
        return substationFacade.findAll();
    }

    @Override
    public ArrayList<MmsFaSubstation> searchBySubsNo(MmsFaSubstation subStationEntity) {
        return substationFacade.searchBySubsNo(subStationEntity);
    }

    @Override
    public List<MmsFaSubstation> findAll1() {
       return substationFacade.findAll();
    }

    @Override
    public List<MmsFaSubstation> searchBySub(MmsFaSubstation subStationEntity) {
         return substationFacade.searchBySub(subStationEntity);
    }

    @Override
    public MmsFaSubstation getSelectedRequest(Integer substationId) {
       return substationFacade.getSelectedRequest(substationId);
    }

    @Override
    public List<MmsFaSubstation> findNewItems() {
        return substationFacade.findNewItems();
    }

    @Override
    public List<MmsFaSubstation> searchBySubLoc(MmsFaSubstation subStationEntity) {
        return substationFacade.searchBySubLoc(subStationEntity);
    }

    @Override
    public List<MmsFaSubstation> searchBySub2(MmsFaSubstation subStationEntity) {
        return substationFacade.searchBySub2(subStationEntity);
    }

    @Override
    public List<MmsFaSubstation> searchBySubsNoAndSubPrep(MmsFaSubstation subStationEntity) {
        return substationFacade.searchBySubsNoAndSubPrep(subStationEntity);
    }

    @Override
    public List<MmsFaSubstation> searchBySubAndSubPrep(MmsFaSubstation subStationEntity) {
        return substationFacade.searchBySubAndSubPrep(subStationEntity);
    }

    @Override
    public List<MmsFaSubstation> searchBySubLocAndSubPrep(MmsFaSubstation subStationEntity) {
       return substationFacade.searchBySubLocAndSubPrep(subStationEntity);
    }

//    @Override
//    public List<MmsFaSubstation> searchAllSubInfoByPreparerId(MmsFaSubstation subStationEntity) {
//       return substationFacade.searchAllSubInfoByPreparerId(subStationEntity);
//    }

    @Override
    public List<MmsFaSubstation> searchAllSubInfoByPreparerId(Integer ssPrepared) {
         return substationFacade.searchAllSubInfoByPreparerId(ssPrepared);
    }

    
}
