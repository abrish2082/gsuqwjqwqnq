/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.mms.businessLogic;



import et.gov.eep.mms.entity.MmsFaTransmission;
import et.gov.eep.mms.mapper.MmsFaTransmissionFacade;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author Binyam
 */
@Stateless
public class MmsFaTransmissionBean implements MmsFaTransmissionBeanLocal {

    @EJB
    MmsFaTransmissionFacade mmsFaTransmissionfacade;

    @Override
    public ArrayList<MmsFaTransmission> searchTransmissisionFA(MmsFaTransmission mmsFaTransmission) {
        return mmsFaTransmissionfacade.searchByFromName(mmsFaTransmission);
    }

    @Override
    public MmsFaTransmission getByAssetName(MmsFaTransmission mmsFaTransmission) {
        return mmsFaTransmissionfacade.getTRInfo(mmsFaTransmission);
    }
    
    /**
     *
     * @param mmsFaTransmission
     */
    @Override
    public void create(MmsFaTransmission mmsFaTransmission) {
       mmsFaTransmissionfacade.create(mmsFaTransmission);
    }

    /**
     *
     * @param mmsFaTransmission
     */
    @Override
    public void edit(MmsFaTransmission mmsFaTransmission) {
        mmsFaTransmissionfacade.edit(mmsFaTransmission);
    }

    /**
     *
     * @return
     */
    @Override
    public List<MmsFaTransmission> findAll1() {
        
        return mmsFaTransmissionfacade.findAll();
    }

    @Override
    public MmsFaTransmission getLastTraId() {
       return mmsFaTransmissionfacade.getLastTraId();
    }

    @Override
    public ArrayList<MmsFaTransmission> searchByTransNo(MmsFaTransmission FaTransmissionEntity) {
        return mmsFaTransmissionfacade.searchByTransNo(FaTransmissionEntity);
    }

    @Override
    public List<MmsFaTransmission> searchByTrans(MmsFaTransmission FaTransmissionEntity) {
        return mmsFaTransmissionfacade.searchByTrans(FaTransmissionEntity);
    }

    @Override
    public MmsFaTransmission getSelectedRequest(Integer transmissionId) {
        return mmsFaTransmissionfacade.getSelectedRequest(transmissionId);
    }

    @Override
    public List<MmsFaTransmission> findNewItems() {
        return mmsFaTransmissionfacade.findNewItems();
    }

    @Override
    public List<MmsFaTransmission> searchByLocName(MmsFaTransmission FaTransmissionEntity) {
      return mmsFaTransmissionfacade.searchByLocName(FaTransmissionEntity); 
    }

    @Override
    public List<MmsFaTransmission> searchByTrans2(MmsFaTransmission FaTransmissionEntity) {
        return mmsFaTransmissionfacade.searchByTrans2(FaTransmissionEntity);
    }

    @Override
    public List<MmsFaTransmission> searchByTransNoAndTrPrep(MmsFaTransmission FaTransmissionEntity) {
         return mmsFaTransmissionfacade.searchByTransNoAndTrPrep(FaTransmissionEntity);
    }

    @Override
    public List<MmsFaTransmission> searchByLocNameAndTrPrep(MmsFaTransmission FaTransmissionEntity) {
        return mmsFaTransmissionfacade.searchByLocNameAndTrPrep(FaTransmissionEntity);
    }

    @Override
    public List<MmsFaTransmission> searchAllTransmissionsInfoByPreparerId(MmsFaTransmission FaTransmissionEntity) {
     return mmsFaTransmissionfacade.searchAllTransmissionsInfoByPreparerId(FaTransmissionEntity);
    }
}