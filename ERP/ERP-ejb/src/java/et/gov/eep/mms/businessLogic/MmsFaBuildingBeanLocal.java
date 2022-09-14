/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.mms.businessLogic;



import et.gov.eep.mms.entity.MmsFaBuilding;
import java.util.ArrayList;
import java.util.List;


import javax.ejb.Local;

/**
 *
 * @author Binyam
 */
@Local
public interface MmsFaBuildingBeanLocal {

    /**
     *
     * @param mmsFaBuilding
     * @return
     */
    public ArrayList<MmsFaBuilding> searchBuildingFA(MmsFaBuilding mmsFaBuilding);

    public MmsFaBuilding getByAssetName(MmsFaBuilding mmsFaBuilding);

    public void create(MmsFaBuilding buildingEntity);

    public void edit(MmsFaBuilding buildingEntity);

    public List<MmsFaBuilding> searchBuildingByParameterPrefix(MmsFaBuilding buildingEntity);

    public MmsFaBuilding getLastBuId();

    public ArrayList<MmsFaBuilding> searchByBuNo(MmsFaBuilding buildingEntity);

    public List<MmsFaBuilding> findAll1();

    public List<MmsFaBuilding> searchByBldg(MmsFaBuilding buildingEntity);

    public MmsFaBuilding getSelectedRequest(Integer buildingId);
    
    public List<MmsFaBuilding> findNewItems();

    public List<MmsFaBuilding> searchByBldg2(MmsFaBuilding buildingEntity);

    public List<MmsFaBuilding> searchByBuName(MmsFaBuilding buildingEntity);

    public List<MmsFaBuilding> searchByBuNoAndProcessedBy(MmsFaBuilding buildingEntity);

    public List<MmsFaBuilding> searchByBuNameAndBuPrep(MmsFaBuilding buildingEntity);

    public List<MmsFaBuilding> searchByBldg2AndBuPrep(MmsFaBuilding buildingEntity);
     public List<MmsFaBuilding> searchAllTransmissionsInfoByPreparerId(Integer buPrepared);
}
