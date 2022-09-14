/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.mms.businessLogic;

import et.gov.eep.mms.entity.MmsInspection;
import et.gov.eep.mms.entity.MmsInspectionDetail;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author user
 */
@Local
public interface MmsInspectionDetailBeanLocal {

    /**
     *
     * @param inspection
     * @return
     */
    public List<MmsInspectionDetail> getlistofInspectionDetails(MmsInspection inspection);

    /**
     *
     * @param selectedSivNo
     * @return
     */
    public MmsInspectionDetail getAllDataWiththisInspNo(String selectedSivNo);
}
