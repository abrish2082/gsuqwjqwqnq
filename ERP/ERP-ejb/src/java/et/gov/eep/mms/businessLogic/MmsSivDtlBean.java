/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.mms.businessLogic;

import et.gov.eep.mms.entity.MmsItemRegistration;
import et.gov.eep.mms.entity.MmsSiv;
import javax.ejb.Stateless;
import et.gov.eep.mms.entity.MmsSivDetail;
import et.gov.eep.mms.mapper.MmsSivDetailFacade;
import java.util.List;
import javax.ejb.EJB;

/**
 *
 * @author Sadik
 */
@Stateless
public class MmsSivDtlBean implements MmsSivDtlBeanLocal {

    @EJB
    MmsSivDetailFacade sivDetailFacade;

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    /**
     *
     * @param selectedSivNo
     * @return
     */
    @Override
    public List<MmsSivDetail> getSivInfoBySivNo(String selectedSivNo) {
        return sivDetailFacade.getSIVinfoBySivNo(selectedSivNo);
    }

    /**
     *
     * @param mmsSivDtlEntity
     * @return
     */
    @Override
    public MmsSivDetail getSivDetailInfoByMatCode(MmsSivDetail mmsSivDtlEntity) {
        return sivDetailFacade.getSivDetailInfosByMatcode(mmsSivDtlEntity);
    }

    @Override
    public List<MmsSivDetail> findallItemcodeInfo() {
        return sivDetailFacade.findAll();

    }

    @Override
    public List<MmsSivDetail> findallUnitpriceInfo() {
        return sivDetailFacade.findAll();
    }

    @Override
    public MmsSivDetail findallItemcodeInfo2(MmsSivDetail sivDtlEntity) {
        return (MmsSivDetail) sivDetailFacade.findAll();
    }

    /**
     *
     * @param sivid
     * @return
     */
    @Override
    public MmsSivDetail getDetailbySivId(MmsSiv sivid) {
        return sivDetailFacade.getDetailbySivId(sivid);
    }

    @Override
    public MmsSivDetail getlastSivDtlId() {
        return sivDetailFacade.getLastSivDtlId();
    }

    @Override
    public MmsSivDetail findByDetailId(MmsSivDetail sivDtlEntity) {
        return sivDetailFacade.findByDetailId(sivDtlEntity);
    }

    @Override
    public void edit(MmsSivDetail sivDtlEntity) {
        sivDetailFacade.edit(sivDtlEntity);
    }

    @Override
    public MmsSivDetail getItemInfoByItemId(MmsItemRegistration itemRegEntity) {
        return sivDetailFacade.getItemInfoByItemId(itemRegEntity);
    }

    @Override
    public void remove(MmsSivDetail sivDtlEntity) {
         sivDetailFacade.remove(sivDtlEntity);
    }

//    @Override
//    public  List<MmsSivDetail> findItemId() {
//       return sivDetailFacade.findItemId();
//    }

   

}

