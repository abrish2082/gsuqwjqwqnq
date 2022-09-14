/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.mms.businessLogic;

import et.gov.eep.mms.entity.MmsFaTransmission;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Binyam
 */
@Local
public interface MmsFaTransmissionBeanLocal {

    public ArrayList<MmsFaTransmission> searchTransmissisionFA(MmsFaTransmission mmsFaTransmission);

    public MmsFaTransmission getByAssetName(MmsFaTransmission mmsFaTransmission);

    void create(MmsFaTransmission mmsFaTransmission);

    /**
     *
     * @param mmsFaTransmission
     */
    void edit(MmsFaTransmission mmsFaTransmission);

    /**
     *
     * @param mmsFaTransmission
     * @return
     */
    /**
     *
     * @return
     */
    public List<MmsFaTransmission> findAll1();

    public MmsFaTransmission getLastTraId();

    public ArrayList<MmsFaTransmission> searchByTransNo(MmsFaTransmission FaTransmissionEntity);

    public List<MmsFaTransmission> searchByTrans(MmsFaTransmission FaTransmissionEntity);

    public MmsFaTransmission getSelectedRequest(Integer transmissionId);

    public List<MmsFaTransmission> findNewItems();

    public List<MmsFaTransmission> searchByLocName(MmsFaTransmission FaTransmissionEntity);

    public List<MmsFaTransmission> searchByTrans2(MmsFaTransmission FaTransmissionEntity);

    public List<MmsFaTransmission> searchByTransNoAndTrPrep(MmsFaTransmission FaTransmissionEntity);

    public List<MmsFaTransmission> searchByLocNameAndTrPrep(MmsFaTransmission FaTransmissionEntity);

    public List<MmsFaTransmission> searchAllTransmissionsInfoByPreparerId(MmsFaTransmission FaTransmissionEntity);
}
