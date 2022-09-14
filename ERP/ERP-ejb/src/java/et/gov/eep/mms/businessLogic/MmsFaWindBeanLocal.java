/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.mms.businessLogic;

import et.gov.eep.mms.entity.MmsFaWind;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author w_station
 */
@Local
public interface MmsFaWindBeanLocal {

    public void create(MmsFaWind windENtity);

    public void edit(MmsFaWind windENtity);

    public List<MmsFaWind> searchWindByParameterPrefix(MmsFaWind windENtity);

    public MmsFaWind getLastWindId();

    public ArrayList<MmsFaWind> searchByWindNo(MmsFaWind windENtity);

    public List<MmsFaWind> findAll1();

    public List<MmsFaWind> searchByWn(MmsFaWind windENtity);

    public MmsFaWind getSelectedRequest(Integer windId);

    public List<MmsFaWind> findNewItems();

    public List<MmsFaWind> searchByWn2(MmsFaWind windENtity);

    public List<MmsFaWind> searchByWindPlantName(MmsFaWind windENtity);

    public List<MmsFaWind> searchByWindNoAndWdPrep(MmsFaWind windENtity);

    public List<MmsFaWind> searchByWindPlantNameAndWdPrep(MmsFaWind windENtity);

    public List<MmsFaWind> searchAllTransmissionsInfoByPreparerId(MmsFaWind windENtity);
}
