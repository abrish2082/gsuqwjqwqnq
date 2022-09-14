/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.mms.businessLogic;





import et.gov.eep.mms.entity.MmsFaHydropower;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Local;



/**
 *
 * @author Sadik ????
 */
@Local
public interface MmsFaHydroPowerBeanLocal {
    void create(MmsFaHydropower mmsFahydropower);

    void edit(MmsFaHydropower mmsFahydropower);

    /**
     *
     * @return
     */
    public MmsFaHydropower getLastHpId();

    public ArrayList<MmsFaHydropower> searchByHpNo(MmsFaHydropower hydroPowerEntity);

    public List<MmsFaHydropower> findAll1();

    public List<MmsFaHydropower> searchByHp(MmsFaHydropower hydroPowerEntity);

    public MmsFaHydropower getSelectedRequest(Integer hydroPowerId);
    
    public List<MmsFaHydropower> findNewItems();

    public List<MmsFaHydropower> searchByHpLocation(MmsFaHydropower hydroPowerEntity);

    public List<MmsFaHydropower> searchByHp2(MmsFaHydropower hydroPowerEntity);

    public ArrayList<MmsFaHydropower> searchByHpNoANdProcessedby(MmsFaHydropower hydroPowerEntity);
     public List<MmsFaHydropower> searchAllTransmissionsInfoByPreparerId(MmsFaHydropower hydroPowerEntity);
}
