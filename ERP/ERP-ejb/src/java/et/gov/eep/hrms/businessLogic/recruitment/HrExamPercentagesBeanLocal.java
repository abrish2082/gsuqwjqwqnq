/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.recruitment;

import et.gov.eep.hrms.entity.recruitment.HrAdvertisements;
import et.gov.eep.hrms.entity.recruitment.HrExamPercentages;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Benin
 */
@Local
public interface HrExamPercentagesBeanLocal {

    public List<HrAdvertisements> batchCodes(String type);

    public HrExamPercentages selectExamPrecentage(HrAdvertisements hrAdvertisements);

    public HrExamPercentages selectExamPrecentageDetail(HrExamPercentages hrExamPercentages);

    public void save(HrExamPercentages hrExamPercentages);

    public void update(HrExamPercentages hrExamPercentages);

}
