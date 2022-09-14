/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.mms.businessLogic;

import et.gov.eep.mms.entity.MmsItemRegistration;
import et.gov.eep.mms.entity.MmsSiv;
import et.gov.eep.mms.entity.MmsSivDetail;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Sadik
 */
@Local
public interface MmsSivDtlBeanLocal {

    /**
     *
     * @param selectedSivNo
     * @return
     */
    public List<MmsSivDetail> getSivInfoBySivNo(String selectedSivNo);

    /**
     *
     * @param mmsSivDtlEntity
     * @return
     */
    public MmsSivDetail getSivDetailInfoByMatCode(MmsSivDetail mmsSivDtlEntity);

    public List<MmsSivDetail> findallItemcodeInfo();

    public List<MmsSivDetail> findallUnitpriceInfo();

    public MmsSivDetail findallItemcodeInfo2(MmsSivDetail sivDtlEntity);

   
    /**
     *
     * @param sivid
     * @return
     */
    public MmsSivDetail getDetailbySivId(MmsSiv sivid);

    public MmsSivDetail getlastSivDtlId();

    public MmsSivDetail findByDetailId(MmsSivDetail sivDtlEntity);

    public void edit(MmsSivDetail sivDtlEntity);

    public MmsSivDetail getItemInfoByItemId(MmsItemRegistration itemRegEntity);
    public void remove(MmsSivDetail sivDtlEntity);

//    public List<MmsSivDetail> findItemId();
    

}