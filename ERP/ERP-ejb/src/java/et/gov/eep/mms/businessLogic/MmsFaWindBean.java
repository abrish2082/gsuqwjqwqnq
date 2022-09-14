/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.mms.businessLogic;



import et.gov.eep.mms.entity.MmsFaWind;
import et.gov.eep.mms.mapper.MmsFaWindFacade;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author w_station
 */
@Stateless
public class MmsFaWindBean implements MmsFaWindBeanLocal {

    @EJB
    MmsFaWindFacade windFacade;
    
    @Override
    public void create(MmsFaWind windENtity) {
        windFacade.create(windENtity);
    }

    @Override
    public void edit(MmsFaWind windENtity) {
        windFacade.edit(windENtity);
    }

    @Override
    public List<MmsFaWind> searchWindByParameterPrefix(MmsFaWind windENtity) {
        return windFacade.searchWindByParameterPrefix(windENtity);
    }

    @Override
    public MmsFaWind getLastWindId() {
        return windFacade.getLastWindId();
    }

    @Override
    public ArrayList<MmsFaWind> searchByWindNo(MmsFaWind windENtity) {
        return windFacade.searchByWindNo(windENtity);
    }

    @Override
    public List<MmsFaWind> findAll1() {
        return windFacade.findAll();
    }    

    @Override
    public List<MmsFaWind> searchByWn(MmsFaWind windENtity) {
       return windFacade.searchByWn(windENtity);
    }

    @Override
    public MmsFaWind getSelectedRequest(Integer windId) {
        return windFacade.getSelectedRequest(windId);
    }

    @Override
    public List<MmsFaWind> findNewItems() {
        return windFacade.findNewItems();
    }

    @Override
    public List<MmsFaWind> searchByWn2(MmsFaWind windENtity) {
        return windFacade.searchByWn2(windENtity);
    }

    @Override
    public List<MmsFaWind> searchByWindPlantName(MmsFaWind windENtity) {
       return windFacade.searchByWindPlantName(windENtity);
    }

    @Override
    public List<MmsFaWind> searchByWindNoAndWdPrep(MmsFaWind windENtity) {
        return windFacade.searchByWindNoAndWdPrep(windENtity);
    }

    @Override
    public List<MmsFaWind> searchByWindPlantNameAndWdPrep(MmsFaWind windENtity) {
        return windFacade.searchByWindPlantNameAndWdPrep(windENtity);
    }
    @Override
    public List<MmsFaWind> searchAllTransmissionsInfoByPreparerId(MmsFaWind windENtity) {
        return windFacade.searchAllTransmissionsInfoByPreparerId(windENtity);
    }

}
