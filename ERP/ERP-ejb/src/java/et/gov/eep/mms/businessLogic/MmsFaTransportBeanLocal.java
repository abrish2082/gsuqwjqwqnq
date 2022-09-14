/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.mms.businessLogic;



import et.gov.eep.mms.entity.MmsFaTransport;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Local;
  


/**
 *
 * @author w_station
 */
@Local
public interface MmsFaTransportBeanLocal {

    public void create(MmsFaTransport transportEntity);

    public void edit(MmsFaTransport transportEntity);

    public List<MmsFaTransport> searchTransportByParameterPrefix(MmsFaTransport transportEntity);

    public MmsFaTransport getLastTrId();

    public ArrayList<MmsFaTransport> searchByTrNo(MmsFaTransport transportEntity);

    public List<MmsFaTransport> findAll1();

    public List<MmsFaTransport> searchBytr(MmsFaTransport transportEntity);

    public MmsFaTransport getSelectedRequest(Integer transportId);
    
    public List<MmsFaTransport> findNewItems();

    public List<MmsFaTransport> searchByTrNoAndTrPrep(MmsFaTransport transportEntity);
    public List<MmsFaTransport> searchAllTransmissionsInfoByPreparerId(Integer tpPrepared);
    
}
