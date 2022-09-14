/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


package et.gov.eep.mms.businessLogic;


import et.gov.eep.mms.entity.MmsFaSubstation;
import java.util.ArrayList;
import java.util.List;


import javax.ejb.Local;

@Local
public interface MmsFaSubstationBeanLocal {
    
    
   
    public List<MmsFaSubstation> findAll() ;   
    
    void create(MmsFaSubstation mmsFaSubstation);

    void edit(MmsFaSubstation mmsFaSubstation);
   
    /**
     *
     * @return
     */
    public MmsFaSubstation getLastSubId();

    public ArrayList<MmsFaSubstation> searchBySubsNo(MmsFaSubstation subStationEntity);

    public List<MmsFaSubstation> findAll1();

    public List<MmsFaSubstation> searchBySub(MmsFaSubstation subStationEntity);

    public MmsFaSubstation getSelectedRequest(Integer substationId);
 
    public List<MmsFaSubstation> findNewItems();

    public List<MmsFaSubstation> searchBySubLoc(MmsFaSubstation subStationEntity);

    public List<MmsFaSubstation> searchBySub2(MmsFaSubstation subStationEntity);

    public List<MmsFaSubstation> searchBySubsNoAndSubPrep(MmsFaSubstation subStationEntity);

    public List<MmsFaSubstation> searchBySubAndSubPrep(MmsFaSubstation subStationEntity);

    public List<MmsFaSubstation> searchBySubLocAndSubPrep(MmsFaSubstation subStationEntity);
    //public List<MmsFaSubstation> searchAllSubInfoByPreparerId(MmsFaSubstation subStationEntity);

    public List<MmsFaSubstation> searchAllSubInfoByPreparerId(Integer ssPrepared);
}
