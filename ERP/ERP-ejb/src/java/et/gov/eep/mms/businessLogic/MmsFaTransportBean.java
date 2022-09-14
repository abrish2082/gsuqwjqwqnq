/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.mms.businessLogic;

import et.gov.eep.mms.entity.MmsFaTransport;
import et.gov.eep.mms.mapper.MmsFaTransportFacade;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.Query;

/**
 *
 * @author w_station
 */
@Stateless
public class MmsFaTransportBean implements MmsFaTransportBeanLocal {

    @EJB
    MmsFaTransportFacade transportFacade;

    @Override
    public void create(MmsFaTransport transportEntity) {

        transportFacade.create(transportEntity);

    }

    @Override
    public void edit(MmsFaTransport transportEntity) {
        transportFacade.edit(transportEntity);
    }

    @Override
    public List<MmsFaTransport> searchTransportByParameterPrefix(MmsFaTransport transportEntity) {
        return transportFacade.searchTransportByParameterPrefix(transportEntity);
    }

    @Override
    public MmsFaTransport getLastTrId() {
        return transportFacade.getLastTrId();
    }

    @Override
    public ArrayList<MmsFaTransport> searchByTrNo(MmsFaTransport transportEntity) {
        return transportFacade.searchByTrNo(transportEntity);
    }

    @Override
    public List<MmsFaTransport> findAll1() {
        return transportFacade.findAll();
    }

    @Override
    public List<MmsFaTransport> searchBytr(MmsFaTransport transportEntity) {
        return transportFacade.searchBytr(transportEntity);
    }

    @Override
    public MmsFaTransport getSelectedRequest(Integer transportId) {
        return transportFacade.getSelectedRequest(transportId);
    }

    @Override
    public List<MmsFaTransport> findNewItems() {
        return transportFacade.findNewItems();
    }

    @Override
    public List<MmsFaTransport> searchByTrNoAndTrPrep(MmsFaTransport transportEntity) {
        return transportFacade.searchByTrNoAndTrPrep(transportEntity);
    }

    @Override
    public List<MmsFaTransport> searchAllTransmissionsInfoByPreparerId(Integer tpPrepared) {
        return transportFacade.searchAllTransmissionsInfoByPreparerId(tpPrepared);
    }

}
